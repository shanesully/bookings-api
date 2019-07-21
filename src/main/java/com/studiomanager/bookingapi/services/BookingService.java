package com.studiomanager.bookingapi.services;

import com.studiomanager.bookingapi.domain.Booking;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingService {

  @Autowired
  FitnessClassService fitnessClassService;

  private static Map<Integer, Booking> bookings = new HashMap<>();
  private static final Logger LOGGER = LoggerFactory.getLogger(SLF4JExample.class);

  static {
    //TODO - Replace with in-memory db
    Booking sampleBooking = new Booking.Builder(1)
        .withClassId(1)
        .withMemberName("Shane O'Sullivan")
        .withBookingDate(LocalDate.now())
        .build();

    bookings.put(sampleBooking.getId(), sampleBooking);
  }

  public Map<Integer, Booking> getBookings() {
    return bookings;
  }

  public Booking getBookingById(int id) {
    return bookings.get(id);
  }

  public void addBooking(Booking booking) {
    bookings.put(bookings.size() + 1, booking);
  }

  public void updateBookingByIdWithNewBody(int id, Booking updatedBody) {

    updatedBody.setId(id);
    removeBookingById(id);

    LOGGER.info("Removed booking: " + id);

    bookings.put(id, updatedBody);
  }

  public void removeBookingById(int id) {
    bookings.remove(id);
  }
}
