package com.studiomanager.bookingapi.services;

import com.studiomanager.bookingapi.domain.FitnessClass;

public interface FitnessClassService {
  public String getFitnessClasses();
  public FitnessClass getFitnessClassById(int id);
}
