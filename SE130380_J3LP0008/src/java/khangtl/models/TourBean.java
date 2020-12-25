/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangtl.models;

import java.io.Serializable;
import java.util.ArrayList;
import khangtl.daos.TourDAO;
import khangtl.dtos.TourDTO;

/**
 *
 * @author Peter
 */
public class TourBean implements Serializable {

    private String departure, destination, fromDate, toDate;
    private int minPrice, maxPrice, currentPage, recordsPerPage;
    private TourDAO dao;
    private TourDTO dto;

    public int getNumberOfRows() throws Exception {
        return dao.getNumberOfRows();
    } 
    
    public boolean insertTour() throws Exception {
        return dao.insertTour(dto);
    }
    
    public boolean checkExistTour() throws Exception {
        return dao.checkExistTour(dto);
    }
    
    public ArrayList<TourDTO> searchTourWithCondition() throws Exception {
        return dao.searchTourWithCondition(departure, destination, fromDate, toDate, minPrice, maxPrice, currentPage, recordsPerPage);
    }

    public TourBean() {
        dao = new TourDAO();
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

    public int getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(int minPrice) {
        this.minPrice = minPrice;
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
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

    public TourDTO getDto() {
        return dto;
    }

    public void setDto(TourDTO dto) {
        this.dto = dto;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getRecordsPerPage() {
        return recordsPerPage;
    }

    public void setRecordsPerPage(int records) {
        this.recordsPerPage = records;
    }

    
}
