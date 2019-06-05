package com.studiomanager.bookingapi.services.impl;

import com.studiomanager.bookingapi.domain.Booking;
import com.studiomanager.bookingapi.services.BookingMockService;
import com.studiomanager.bookingapi.services.FitnessClassService;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("bookingMockService")
public class BookingMockServiceImpl implements BookingMockService {

  @Autowired
  FitnessClassServiceImpl fitnessClassService;

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

  public void addNewBooking(Booking booking) {
    fitnessClassService.decrementFitnessClassCapacityById(booking.getClassId());

    this.getMockBookings().put(getNextBookingId(), booking);
  }

  private int getNextBookingId() {
    return this.getMockBookings().size() + 1;
  }

  public void updateBookingByIdWithNewBody(int id, Booking updatedBody) {
    updatedBody.setId(id);

    Booking previousBooking = this.getBookingById(id);

    this.removeBookingById(id); //TODO Minor - Add logging to this method

    if(previousBooking.getClassId() != updatedBody.getClassId())
      fitnessClassService.getFitnessClassById(id)
        .decrementCapacity();

    this.getMockBookings()
        .put(id, updatedBody);
  }

  public void removeBookingById(int id) {
    fitnessClassService.incrementFitnessClassCapacityById(id);

    this.getMockBookings().remove(id);
  }
}
