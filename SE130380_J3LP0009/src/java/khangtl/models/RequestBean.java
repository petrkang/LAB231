/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangtl.models;

import java.io.Serializable;
import java.util.List;
import khangtl.daos.RequestDAO;
import khangtl.dtos.RequestDTO;

/**
 *
 * @author Peter
 */
public class RequestBean implements Serializable {

    private String id, resourceName, user, status, handleStatus, requestDate;
    private int currentPage, recordsPerPage;
    private RequestDAO dao;
    private RequestDTO dto;

    public List<RequestDTO> getAllRequests() throws Exception {
        return dao.getAllRequests();
    }

    public List<RequestDTO> searchHistoryRequestWithCondition() throws Exception {
        return dao.searchHistoryRequestWithCondition(user, requestDate);
    }

    public boolean handleRequest() throws Exception {
        return dao.handleRequest(id, handleStatus);
    }

    public boolean deleteRequestById() throws Exception {
        return dao.deleteRequestById(id);
    }

    public List<RequestDTO> searchRequestWithCondition() throws Exception {
        return dao.searchRequestWithCondition(requestDate, resourceName, user, status, currentPage, recordsPerPage);
    }

    public int getNumberOfRows() throws Exception {
        return dao.getNumberOfRows(requestDate, resourceName, user, status);
    }

    public boolean sendRequest() throws Exception {
        return dao.sendRequest(dto);
    }

    public RequestBean() {
        dao = new RequestDAO();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getHandleStatus() {
        return handleStatus;
    }

    public void setHandleStatus(String handleStatus) {
        this.handleStatus = handleStatus;
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

    public RequestDTO getDto() {
        return dto;
    }

    public void setDto(RequestDTO dto) {
        this.dto = dto;
    }

}
