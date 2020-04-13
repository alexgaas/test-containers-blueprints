package integration;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;

import com.alexgaas.testcontainersblueprints.BlueprintApplication;
import com.alexgaas.testcontainersblueprints.blueprint.model.Blueprint;
import com.alexgaas.testcontainersblueprints.blueprint.repository.BlueprintRepository;
import com.google.gson.Gson;
import commons.AbstractContainerDatabaseTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ResponseBodyExtractionOptions;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import org.flywaydb.core.Flyway;
import org.junit.ClassRule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.shaded.com.google.common.reflect.TypeToken;

@ActiveProfiles("testing")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(
    classes = BlueprintApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BlueprintTest extends AbstractContainerDatabaseTest {
  @LocalServerPort private int port = -1;

  static class Initializer
      implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
      TestPropertyValues.of(
              "spring.datasource.url=" + mariaDBContainer.getJdbcUrl(),
              "spring.datasource.username=" + mariaDBContainer.getUsername(),
              "spring.datasource.password=" + mariaDBContainer.getPassword())
          .applyTo(configurableApplicationContext.getEnvironment());
    }
  }

  @ClassRule private static MariaDBContainer mariaDBContainer = new MariaDBContainer();

  @Autowired private BlueprintRepository blueprintRepository;

  @BeforeAll
  public void setup() {
    if (!mariaDBContainer.isRunning()) mariaDBContainer.start();
    Flyway.configure()
        .dataSource(
            mariaDBContainer.getJdbcUrl(),
            mariaDBContainer.getUsername(),
            mariaDBContainer.getPassword())
        .load()
        .migrate();

    // Rest Assured config
    RestAssured.baseURI = "http://localhost";
    RestAssured.port = port;
  }

  @BeforeEach
  public void setupEach() {
    // clean up repo
    blueprintRepository.deleteAll();
  }

  @Test
  public void validateBlueprintRepository() {
    Blueprint blueprint = new Blueprint("id", "name");
    blueprintRepository.save(blueprint);

    long count = blueprintRepository.count();
    assertEquals(count, 1);
  }

  @Test
  void validateGetBlueprint() {
    Blueprint blueprint = new Blueprint("id1", "name1");
    blueprintRepository.save(blueprint);

    /*
     * use
     * .getBody()
     * .prettyPrint();
     *
     * to get full body print
     */
    given()
        .log()
        .everything()
        .contentType(ContentType.JSON)
        .pathParam("id", "id1")
        .get("/v1/blueprint/{id}")
        .then()
        .log()
        .body()
        .statusCode(200)
        .body(
            "id", equalTo("id1"),
            "name", equalTo("name1"));
  }

  @Test
  void validateGetBlueprints() {
    Blueprint blueprint1 = new Blueprint("id1", "name1");
    blueprintRepository.save(blueprint1);
    Blueprint blueprint2 = new Blueprint("id2", "name2");
    blueprintRepository.save(blueprint2);

    /*
     * use
     * .getBody()
     * .prettyPrint();
     *
     * to get full body print
     */
    ResponseBodyExtractionOptions response =
        given()
            .log()
            .everything()
            .contentType(ContentType.JSON)
            .get("/v1/blueprints")
            .then()
            .extract()
            .body();
    assertEquals(response.jsonPath().getList("body").size(), 2);

    Type listType = new TypeToken<ArrayList<Blueprint>>() {}.getType();
    List<Blueprint> blueprints =
        new Gson().fromJson(response.jsonPath().getList("body").toString(), listType);

    assertEquals(blueprints.get(0).getId(), "id1");
    assertEquals(blueprints.get(0).getName(), "name1");
    assertEquals(blueprints.get(1).getId(), "id2");
    assertEquals(blueprints.get(1).getName(), "name2");
  }
}
