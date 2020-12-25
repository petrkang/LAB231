/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangtl.dtos;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Peter
 */
public class RequestDTO implements Serializable {

    private String id, userRequest, status;
    private Date requestDate;
    private List<RequestDetailsDTO> details;
    private List<ResourceDTO> resources;

    public RequestDTO() {
    }
    public RequestDTO(String id, String userRequest, String status, Date requestDate) {
        this.id = id;
        this.userRequest = userRequest;
        this.status = status;
        this.requestDate = requestDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserRequest() {
        return userRequest;
    }

    public void setUserRequest(String userRequest) {
        this.userRequest = userRequest;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public List<RequestDetailsDTO> getDetails() {
        return details;
    }

    public void setDetails(List<RequestDetailsDTO> details) {
        this.details = details;
    }

    public List<ResourceDTO> getResources() {
        return resources;
    }

    public void setResources(List<ResourceDTO> resources) {
        this.resources = resources;
    }

}
