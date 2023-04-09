package com.example.fibonacci.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "known_values")
public class KnownNumberModel {
  
  @Id
  private Integer value;
  private Long result;
  private Integer frecuency;

  public KnownNumberModel() {}

  public KnownNumberModel(Integer value, Long result, Integer frecuency) {
    this.value = value;
    this.result = result;
    this.frecuency = frecuency;
  }

  public Integer getValue() {
    return value;
  }

  public void setValue(Integer value) {
    this.value = value;
  }

  public Long getResult() {
    return result;
  }

  public void setResult(Long result) {
    this.result = result;
  }

  public Integer getFrecuency() {
    return frecuency;
  }

  public void setFrecuency(Integer frecuency) {
    this.frecuency = frecuency;
  }

}
