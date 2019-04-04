package com.studiomanager.bookingapi.controllers;

import com.studiomanager.bookingapi.domain.Booking;
import org.springframework.http.ResponseEntity;

public interface BookingController {
    public ResponseEntity<Object> getBookings();
    public ResponseEntity<Object> getBooking(int id);
    public ResponseEntity<Object> createBooking(Booking booking);
    public ResponseEntity<Object> updateBooking(int id, Booking booking);
    public ResponseEntity<Object> deleteBooking(int id);
}
