/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangtl.dtos;

import java.io.Serializable;

/**
 *
 * @author Peter
 */
public class BookingDetailsDTO implements Serializable {

    private String bookingId, tourName;
    private int tourId, amount, price;

    public BookingDetailsDTO() {
    }

    public BookingDetailsDTO(String bookingId, int tourId, int amount, int price) {
        this.bookingId = bookingId;
        this.tourId = tourId;
        this.amount = amount;
        this.price = price;
    }

    public BookingDetailsDTO(String bookingId, String tourName, int tourId, int amount, int price) {
        this.bookingId = bookingId;
        this.tourName = tourName;
        this.tourId = tourId;
        this.amount = amount;
        this.price = price;
    }

    public String getTourName() {
        return tourName;
    }

    public void setTourName(String tourName) {
        this.tourName = tourName;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public int getTourId() {
        return tourId;
    }

    public void setTourId(int tourId) {
        this.tourId = tourId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}
