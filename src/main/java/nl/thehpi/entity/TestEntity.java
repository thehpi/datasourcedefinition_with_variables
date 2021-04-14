package nl.thehpi.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class TestEntity {
  @GeneratedValue
  @Id private String id;

  @Column(name = "name")
  private String name;

  public String getId() {
    return id;
  }
}
