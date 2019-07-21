package com.studiomanager.bookingapi.domain;

import java.time.LocalDate;

public class Booking {
    int id;
    int classId;
    String memberName;
    LocalDate bookingDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    private Booking() {}

    public static class Builder {
        int id;
        int classId;
        String memberName;
        LocalDate bookingDate;

        public Builder(int id) {
            this.id = id;
        }

        public Builder withClassId(int classId) {
            this.classId = classId;

            return this;
        }

        public Builder withMemberName(String memberName) {
            this.memberName = memberName;

            return this;
        }

        public Builder withBookingDate(LocalDate bookingDate) {
            this.bookingDate = bookingDate;

            return this;
        }

        public Booking build() {
            Booking booking = new Booking();

            booking.id = this.id;
            booking.classId = this.classId;
            booking.memberName = this.memberName;
            booking.bookingDate = this.bookingDate;

            return booking;
        }
    }
}