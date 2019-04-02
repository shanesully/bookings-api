package com.studiomanager.bookingapi.domain;

import java.time.LocalDate;
import org.springframework.stereotype.Component;

@Component("fitnessClass")
public class FitnessClass {
  private int id;
  private String className;
  private LocalDate startDate;
  private LocalDate endDate;
  private int capacity;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getClassName() {
    return className;
  }

  public void setClassName(String className) {
    this.className = className;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  public int getCapacity() {
    return capacity;
  }

  public void setCapacity(int capacity) {
    this.capacity = capacity;
  }

  public void incrementCapacity() {
    this.capacity += 1;
  }

  public void decrementCapacity() {
    this.capacity -= 1;
  }
}
