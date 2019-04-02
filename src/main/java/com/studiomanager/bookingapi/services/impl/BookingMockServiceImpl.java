package com.studiomanager.bookingapi.services.impl;

import com.studiomanager.bookingapi.domain.Booking;
import com.studiomanager.bookingapi.services.BookingMockService;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service("bookingMockService")
public class BookingMockServiceImpl implements BookingMockService {
  private static Map<Integer, Booking> bookingsMap = new HashMap<>();

  static {
    Booking sampleBooking = new Booking();
    sampleBooking.setId(1);
    sampleBooking.setClassId(1);
    sampleBooking.setMemberName("Shane O'Sullivan");
    sampleBooking.setBookingDate(LocalDate.now());

    bookingsMap.put(sampleBooking.getId(), sampleBooking);
  }

  public Map<Integer, Booking> getMockBookings() {
    return bookingsMap;
  }

  public Booking getBookingById(int id) {
    return bookingsMap.get(id);
  }
}
