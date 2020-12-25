/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangtl.dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Peter
 */
public class BookingDTO implements Serializable {

    private String id, userId;
    private int totalPrice;
    private Date bookingDate;
    private ArrayList<BookingDetailsDTO> details;
    private DiscountDTO discount;

    public BookingDTO() {
    }

    public BookingDTO(String id, String userId, int totalPrice, Date bookingDate, ArrayList<BookingDetailsDTO> details) {
        this.id = id;
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.bookingDate = bookingDate;
        this.details = details;
    }
    
    public BookingDTO(String id, String userId, int totalPrice, Date bookingDate, ArrayList<BookingDetailsDTO> details, DiscountDTO discount) {
        this.id = id;
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.bookingDate = bookingDate;
        this.details = details;
        this.discount = discount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public ArrayList<BookingDetailsDTO> getDetails() {
        return details;
    }

    public void setDetails(ArrayList<BookingDetailsDTO> details) {
        this.details = details;
    }

    public DiscountDTO getDiscount() {
        return discount;
    }

    public void setDiscount(DiscountDTO discount) {
        this.discount = discount;
    }

}
