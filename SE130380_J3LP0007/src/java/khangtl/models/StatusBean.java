/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangtl.models;

import java.io.Serializable;
import java.util.ArrayList;
import khangtl.daos.StatusDAO;
import khangtl.dtos.StatusDTO;

/**
 *
 * @author Peter
 */
public class StatusBean implements Serializable {

    private String name;
    private StatusDAO dao;
    private StatusDTO dto;

    public ArrayList<StatusDTO> getAllStatus() throws Exception {
        return dao.getAllStatus();
    }

    public StatusBean() {
        dao = new StatusDAO();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StatusDTO getDto() {
        return dto;
    }

    public void setDto(StatusDTO dto) {
        this.dto = dto;
    }

}
