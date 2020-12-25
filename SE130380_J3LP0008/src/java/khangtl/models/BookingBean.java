/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangtl.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import khangtl.daos.BookingDAO;
import khangtl.dtos.BookingDTO;
import khangtl.dtos.BookingDetailsDTO;

/**
 *
 * @author Peter
 */
public class BookingBean implements Serializable {

    private String id, userId;
    private int totalPrice;
    private Date bookingDate;
    private ArrayList<BookingDetailsDTO> details;
    private BookingDTO dto;
    private BookingDAO dao;

    public boolean insertBookingTour() throws Exception {
        return dao.insertBookingTour(dto);
    }

    public BookingBean() {
        dao = new BookingDAO();
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

    public BookingDTO getDto() {
        return dto;
    }

    public void setDto(BookingDTO dto) {
        this.dto = dto;
    }

}
