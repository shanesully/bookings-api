package com.studiomanager.bookingapi.controllers.impl;

import com.studiomanager.bookingapi.controllers.BookingController;
import com.studiomanager.bookingapi.domain.Booking;
import com.studiomanager.bookingapi.services.impl.BookingMockServiceImpl;
import com.studiomanager.bookingapi.services.impl.FitnessClassServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController("bookingController")
public class BookingControllerImpl implements BookingController {

    @Autowired
    FitnessClassServiceImpl fitnessClassService;

    @Autowired
    BookingMockServiceImpl bookingMockService;

    @RequestMapping(value="/bookings", method= RequestMethod.POST)
    public ResponseEntity<Object> createBooking(@RequestBody Booking booking) {
        if(fitnessClassExistsForBooking(booking.getClassId())) {
            if(fitnessClassBookingsAvailable(booking.getClassId())) {
                booking.setId(bookingMockService.getMockBookings().size() + 1);

                bookingMockService.getMockBookings()
                    .put(booking.getId(), booking);

                fitnessClassService.decrementFitnessClassCapacityById(booking.getClassId());

                return new ResponseEntity<>("Booking Successful", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Booking Failed - Fitness Class at Capacity", HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>("Booking Failed - No Existing Fitness Class", HttpStatus.OK);
        }
    }

    @RequestMapping("/bookings")
    public ResponseEntity<Object> getBookings() {

        System.out.println("Classes: " + fitnessClassService.getFitnessClasses());

        System.out.println("Bookings: " + bookingMockService.getMockBookings());

        return new ResponseEntity<>(bookingMockService.getMockBookings(), HttpStatus.OK);
    }

    @RequestMapping(value="/bookings/{id}")
    public ResponseEntity<Object> getBooking(@PathVariable("id") int id) {
        return new ResponseEntity<>(bookingMockService.getMockBookings().get(id), HttpStatus.OK);
    }

    @RequestMapping(value="/bookings/{id}", method=RequestMethod.PUT)
    public ResponseEntity<Object> updateBooking(@PathVariable("id") int id, @RequestBody Booking booking) {
        booking.setId(id);

        // TODO get old class
        Booking previousBooking;

        if(fitnessClassExistsForBooking(booking.getClassId()) && fitnessClassBookingsAvailable(booking.getClassId())) {
            previousBooking = bookingMockService.getBookingById(id);

            bookingMockService.getMockBookings()
                .remove(id); //TODO Minor - Add logging to this method

            fitnessClassService.getFitnessClassById(previousBooking.getClassId())
                .incrementCapacity();

            bookingMockService.getMockBookings()
                .put(id, booking); //TODO Minor - Add logging to this method

            fitnessClassService.getFitnessClassById(booking.getClassId())
                .decrementCapacity();

            return new ResponseEntity<>("Booking updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Booking updated failed - No Such Fitness Class", HttpStatus.OK);
        }
    }

    @RequestMapping(value="/bookings/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Object> deleteBooking(@PathVariable("id") int id) {
        Booking bookingReference = bookingMockService.getBookingById(id);

        bookingMockService.getMockBookings().remove(id); //TODO Minor - Add logging to this method

        fitnessClassService.incrementFitnessClassCapacityById(bookingReference.getClassId());

        return new ResponseEntity<>("Booking deleted", HttpStatus.OK);
    }

    private boolean fitnessClassExistsForBooking(int id) {
        return fitnessClassService.getFitnessClassById(id) != null ? true : false;
    }

    private boolean fitnessClassBookingsAvailable(int id) {
        return fitnessClassService.getFitnessClassById(id).getCapacity() != 0 ? true : false;
    }
}
