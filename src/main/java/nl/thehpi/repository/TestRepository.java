package nl.thehpi.repository;

import nl.thehpi.entity.TestEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.Optional;

@Stateless
public class TestRepository {
  @PersistenceContext(unitName = "testUnit")
  EntityManager em;

  public TestRepository() {}

  public TestRepository(EntityManager em) {
    this.em = em;
  }

  public Collection<TestEntity> getTests() {
    return em.createQuery("select d from TestEntity d", TestEntity.class).getResultList();
  }

  public Optional<TestEntity> getTest(String id) {
    return Optional.ofNullable(em.find(TestEntity.class, id));
  }

  public TestEntity createTest(String name) {
    TestEntity entity = new TestEntity();
    entity.setName(name);
    em.persist(entity);
    return entity;
  }
}
