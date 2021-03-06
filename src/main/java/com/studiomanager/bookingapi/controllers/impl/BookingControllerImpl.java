package com.studiomanager.bookingapi.controllers.impl;

import com.studiomanager.bookingapi.controllers.BookingController;
import com.studiomanager.bookingapi.domain.Booking;
import com.studiomanager.bookingapi.services.BookingService;
import com.studiomanager.bookingapi.services.FitnessClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookingControllerImpl implements BookingController {

    @Autowired
    FitnessClassService fitnessClassService;

    @Autowired
    BookingService bookingService;

    //TODO - Update/create booking
    @RequestMapping(value="/bookings", method= RequestMethod.POST)
    public ResponseEntity<Object> createBooking(@RequestBody Booking booking) {
        if(classWithCapacityExists(booking.getClassId())) {
            bookingService.addBooking(booking);

            return new ResponseEntity<>("Booking Successful", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Booking Failed - No Class with Capacity Exists", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping("/bookings")
    public ResponseEntity<Object> getBookings() {

        return new ResponseEntity<>(bookingService.getBookings(), HttpStatus.OK);
    }

    @RequestMapping(value="/bookings/{id}")
    public ResponseEntity<Object> getBooking(@PathVariable("id") int id) {
        return new ResponseEntity<>(bookingService.getBookings().get(id), HttpStatus.OK);
    }

    @RequestMapping(value="/bookings/{id}", method=RequestMethod.PUT)
    public ResponseEntity<Object> updateBooking(@PathVariable("id") int id, @RequestBody Booking booking) {
        if(classWithCapacityExists(booking.getClassId())) {
            bookingService.updateBookingByIdWithNewBody(id, booking); //TODO Minor - Add logging to this method

            return new ResponseEntity<>("Booking updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Booking updated failed - No Such Fitness Class", HttpStatus.OK);
        }
    }

    @RequestMapping(value="/bookings/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Object> deleteBooking(@PathVariable("id") int id) {
        if(bookingExistsWithId(id)) {
            bookingService.removeBookingById(id);

            return new ResponseEntity<>("Booking deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Booking Deletion Failed", HttpStatus.BAD_REQUEST);
        }
    }

    private boolean classWithCapacityExists(int id) {
        return fitnessClassService.getFitnessClassById(id) != null ? true : false && fitnessClassService.getFitnessClassById(id).getCapacity() > 0;
    }


    private boolean fitnessClassExistsForBooking(int id) {
        return fitnessClassService.getFitnessClassById(id) != null ? true : false;
    }

    private boolean bookingExistsWithId(int id) {
        return bookingService.getBookingById(id) != null ? true : false;
    }

    private boolean classHasCapacity(int id) {
        return fitnessClassService.getFitnessClassById(id).getCapacity() > 0;
    }

    private int getNextBookingId() {
        return bookingService.getBookings().size() + 1;
    }
}
