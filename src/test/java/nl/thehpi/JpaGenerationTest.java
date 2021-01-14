package nl.thehpi;

import nl.thehpi.entity.TestEntity;
import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.junit.Test;

import javax.annotation.sql.DataSourceDefinition;
import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JpaGenerationTest {
  public Map<String, String> getProperties() {
    Map<String, String> properties = new HashMap<>();

    properties.put(PersistenceUnitProperties.JDBC_URL, "jdbc:postgresql://db:55432/database");
    properties.put(PersistenceUnitProperties.JDBC_DRIVER, "org.postgresql.Driver");
    properties.put(PersistenceUnitProperties.JDBC_USER, "database");
    properties.put(PersistenceUnitProperties.JDBC_PASSWORD, "database");
    properties.put(
        PersistenceUnitProperties.SCHEMA_GENERATION_DATABASE_ACTION,
        PersistenceUnitProperties.SCHEMA_GENERATION_CREATE_ACTION);
    properties.put(
        PersistenceUnitProperties.SCHEMA_GENERATION_SCRIPTS_ACTION,
        PersistenceUnitProperties.SCHEMA_GENERATION_CREATE_ACTION
    );
    properties.put(
        PersistenceUnitProperties.SCHEMA_GENERATION_SCRIPTS_CREATE_TARGET, "target/schema.sql");

    return properties;
  }

  @Test
  public void generateJpaSchema() {
    Persistence.generateSchema("it", getProperties());
  }

  @Test
  public void test() {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("it", getProperties());
    EntityManager em = emf.createEntityManager();
    TestEntity entity = new TestEntity();
    entity.setName("name1");
    EntityTransaction tx = em.getTransaction();
    tx.begin();
    em.persist(entity);
    em.flush();
    tx.commit();

    em.clear();

    List<TestEntity> tests = em.createQuery("select t from TestEntity t", TestEntity.class).getResultList();
    System.out.println("FOUND NR: " + tests.size());
    tests.forEach(System.out::println);
  }

  @Test
  public void test_drop() {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("it", getProperties());
    EntityManager em = emf.createEntityManager();
    EntityTransaction tx = em.getTransaction();
    tx.begin();
    em.createNativeQuery("drop table testentity").executeUpdate();
    em.createNativeQuery("drop table sequence").executeUpdate();
    em.flush();
    tx.commit();

    em.clear();

    List<TestEntity> tests = em.createQuery("select t from TestEntity t", TestEntity.class).getResultList();
    System.out.println("FOUND NR: " + tests.size());
    tests.forEach(System.out::println);
  }

}
