/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangtl.models;

import java.io.Serializable;
import java.util.List;
import khangtl.daos.RequestStatusDAO;
import khangtl.dtos.RequestStatusDTO;

/**
 *
 * @author Peter
 */
public class RequestStatusBean implements Serializable {

    private String name;
    private RequestStatusDTO dto;
    private RequestStatusDAO dao;

    public RequestStatusBean() {
        dao = new RequestStatusDAO();
    }
    
    public List<RequestStatusDTO> getAllRequestStatus() throws Exception {
        return dao.getAllRequestStatus();
    }

    public RequestStatusBean(String name, RequestStatusDTO dto) {
        this.name = name;
        this.dto = dto;
    }

}
