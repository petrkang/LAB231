/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangtl.models;

import java.io.Serializable;
import java.util.List;
import khangtl.daos.ResourceDAO;
import khangtl.dtos.ResourceDTO;

/**
 *
 * @author Peter
 */
public class ResourceBean implements Serializable {

    private int id, balance, currentPage, recordsPerPage;
    private String name, usingDate, category;
    private ResourceDAO dao;
    private ResourceDTO dto;

    public ResourceBean() {
        dao = new ResourceDAO();
    }

    public int getBalanceById() throws Exception {
        return dao.getBalanceById(id);
    }

    public int getNumberOfRows() throws Exception {
        return dao.getNumberOfRows(name, category, usingDate);
    }

    public List<ResourceDTO> searchResourceWithCondition() throws Exception {
        return dao.searchResourceWithCondition(name, category, usingDate, currentPage, recordsPerPage);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsingDate() {
        return usingDate;
    }

    public void setUsingDate(String usingDate) {
        this.usingDate = usingDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ResourceDTO getDto() {
        return dto;
    }

    public void setDto(ResourceDTO dto) {
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

    public void setRecordsPerPage(int recordsPerPage) {
        this.recordsPerPage = recordsPerPage;
    }

}
