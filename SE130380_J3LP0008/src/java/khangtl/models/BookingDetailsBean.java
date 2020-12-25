/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangtl.models;

import java.io.Serializable;
import khangtl.daos.BookingDetailsDAO;
import khangtl.dtos.BookingDetailsDTO;

/**
 *
 * @author Peter
 */
public class BookingDetailsBean implements Serializable {

    private String bookingId;
    private int tourId, amount, price;
    private BookingDetailsDAO dao;
    private BookingDetailsDTO dto;

    public int getAmountTourBooked() throws Exception {
        return dao.getAmountTourBooked(tourId);
    }

    public BookingDetailsBean() {
        dao = new BookingDetailsDAO();
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

    public BookingDetailsDTO getDto() {
        return dto;
    }

    public void setDto(BookingDetailsDTO dto) {
        this.dto = dto;
    }

}
