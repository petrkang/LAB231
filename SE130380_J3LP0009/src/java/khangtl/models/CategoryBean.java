/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangtl.models;

import java.io.Serializable;
import java.util.List;
import khangtl.daos.CategoryDAO;
import khangtl.dtos.CategoryDTO;

/**
 *
 * @author Peter
 */
public class CategoryBean implements Serializable {

    private int id;
    private String name;
    private CategoryDAO dao;
    private CategoryDTO dto;

    public CategoryBean() {
        dao = new CategoryDAO();
    }
    
    public List<CategoryDTO> getAllCategory() throws Exception {
        return dao.getAllCategory();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategoryDTO getDto() {
        return dto;
    }

    public void setDto(CategoryDTO dto) {
        this.dto = dto;
    }

}
