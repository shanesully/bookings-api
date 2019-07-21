package com.studiomanager.bookingapi.domain;

import java.time.LocalDate;

public class FitnessClass {

  private int id;
  private String className;
  private LocalDate startDate;
  private LocalDate endDate;
  private int capacity;

  private FitnessClass() { }

  public FitnessClass(int capacity) {
    this.capacity = capacity;
  }

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

  public static class Builder {
    private int id;
    private String className;
    private LocalDate startDate;
    private LocalDate endDate;
    private int capacity;

   public Builder(int id) {
     this.id = id;
   }

   public Builder withClassName(String className) {
     this.className = className;

     return this;
   }

   public Builder withStartDate(LocalDate startDate) {
     this.startDate = startDate;

     return this;
   }

   public Builder withEndDate(LocalDate endDate) {
     this.endDate = endDate;

     return this;
   }

   public Builder withCapacity(int capacity) {
     this.capacity = capacity;

     return this;
   }

   public FitnessClass build() {
     FitnessClass fitnessClass = new FitnessClass();

     fitnessClass.id = this.id;
     fitnessClass.className = this.className;
     fitnessClass.startDate = this.startDate;
     fitnessClass.endDate = this.endDate;
     fitnessClass.capacity = this.capacity;

     return fitnessClass;
   }
  }
}