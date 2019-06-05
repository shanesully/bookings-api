package com.studiomanager.bookingapi.services;

import com.studiomanager.bookingapi.domain.Booking;
import java.util.Map;

public interface BookingMockService {
  public Map<Integer, Booking> getMockBookings();
  public void removeBookingById(int id);
}
