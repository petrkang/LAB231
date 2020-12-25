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
public class TourDTO implements Serializable {

    private String name, image, departure, destination, status, fromDate, toDate;
    private int id, price, quota;

    public TourDTO() {
    }

    public TourDTO(int id, String name, String image, String departure, String destination, int price, int quota, String fromDate, String toDate) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.departure = departure;
        this.destination = destination;
        this.price = price;
        this.quota = quota;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }
    
    public TourDTO(String name, String image, String departure, String destination, int price, int quota, String fromDate, String toDate) {
        this.name = name;
        this.image = image;
        this.departure = departure;
        this.destination = destination;
        this.price = price;
        this.quota = quota;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public TourDTO(String name, String image, String departure, String destination, String status, String fromDate, String toDate, int id, int price, int quota) {
        this.name = name;
        this.image = image;
        this.departure = departure;
        this.destination = destination;
        this.status = status;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.id = id;
        this.price = price;
        this.quota = quota;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuota() {
        return quota;
    }

    public void setQuota(int quota) {
        this.quota = quota;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

}
