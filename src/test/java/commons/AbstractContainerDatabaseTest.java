package commons;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;
import javax.sql.DataSource;
import org.junit.After;
import org.testcontainers.containers.JdbcDatabaseContainer;

public abstract class AbstractContainerDatabaseTest {

  private final Set<HikariDataSource> datasourcesForCleanup = new HashSet<>();

  protected ResultSet performQuery(JdbcDatabaseContainer container, String sql)
      throws SQLException {
    DataSource ds = getDataSource(container);
    Statement statement = ds.getConnection().createStatement();
    statement.execute(sql);
    ResultSet resultSet = statement.getResultSet();

    resultSet.next();
    return resultSet;
  }

  DataSource getDataSource(JdbcDatabaseContainer container) {
    HikariConfig hikariConfig = new HikariConfig();
    hikariConfig.setJdbcUrl(container.getJdbcUrl());
    hikariConfig.setUsername(container.getUsername());
    hikariConfig.setPassword(container.getPassword());
    hikariConfig.setDriverClassName(container.getDriverClassName());

    final HikariDataSource dataSource = new HikariDataSource(hikariConfig);
    datasourcesForCleanup.add(dataSource);
    return dataSource;
  }

  @After
  public void teardown() {
    datasourcesForCleanup.forEach(HikariDataSource::close);
  }
}
