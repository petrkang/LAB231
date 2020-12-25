/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangtl.models;

import java.io.Serializable;
import java.util.ArrayList;
import khangtl.daos.PlaceDAO;
import khangtl.dtos.PlaceDTO;

/**
 *
 * @author Peter
 */
public class PlaceBean implements Serializable {

    private String name;
    private PlaceDAO dao;
    private PlaceDTO dto;

    public ArrayList<PlaceDTO> getAllPlaces() throws Exception {
        return dao.getAllPlaces();
    }
    
    public PlaceBean() {
        dao = new PlaceDAO();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PlaceDTO getDto() {
        return dto;
    }

    public void setDto(PlaceDTO dto) {
        this.dto = dto;
    }
}
