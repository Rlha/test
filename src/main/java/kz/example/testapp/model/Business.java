package kz.example.testapp.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Data
public class Business implements Serializable {

  @Id
  @GeneratedValue(generator = "business_seq", strategy = GenerationType.AUTO)
  @SequenceGenerator(sequenceName = "business_seq_id", name = "business_seq", allocationSize = 1)
  private Integer id;

  @Column
  private String description;

  @Enumerated(EnumType.STRING)
  @Column(length = 30)
  private BusinessStatus businessStatus;

  public enum BusinessStatus {
    IN_PROGRESS,
    DONE,
    CANCELLED,
    PAUSED
  }

}
