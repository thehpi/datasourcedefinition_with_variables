package nl.thehpi.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

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
