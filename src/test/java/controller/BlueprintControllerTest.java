package controller;

import static org.mockito.Mockito.when;

import com.alexgaas.testcontainersblueprints.blueprint.controller.BlueprintController;
import com.alexgaas.testcontainersblueprints.blueprint.model.Blueprint;
import com.alexgaas.testcontainersblueprints.blueprint.service.BlueprintService;
import commons.UnitTestBase;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@RunWith(MockitoJUnitRunner.class)
public class BlueprintControllerTest extends UnitTestBase {
  @Mock private BlueprintService service;

  @InjectMocks private BlueprintController controller;

  @Test
  public void validateEmptyBlueprints() {
    when(service.getBlueprints()).thenReturn(Flux.empty());
    StepVerifier.create(controller.getBlueprints()).expectNextCount(0).verifyComplete();
  }

  @Test
  public void validateBlueprints() {
    Blueprint blueprint1 = new Blueprint("id1", "name1");
    Blueprint blueprint2 = new Blueprint("id2", "name2");
    when(service.getBlueprints()).thenReturn(Flux.just(blueprint1, blueprint2));

    StepVerifier.create(controller.getBlueprints())
        .consumeNextWith(result1 -> this.responseEntityThat(CoreMatchers.is(blueprint1)))
        .consumeNextWith(result2 -> this.responseEntityThat(CoreMatchers.is(blueprint2)))
        .verifyComplete();
  }
}
