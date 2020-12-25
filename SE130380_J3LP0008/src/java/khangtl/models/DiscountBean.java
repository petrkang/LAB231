/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangtl.models;

import java.io.Serializable;
import khangtl.daos.DiscountDAO;
import khangtl.dtos.DiscountDTO;

/**
 *
 * @author Peter
 */
public class DiscountBean implements Serializable {
    private String code;
    private DiscountDAO dao;
    private DiscountDTO dto;

    public DiscountDTO checkAvailableDiscount() throws Exception {
        return dao.checkAvailableDiscount(code);
    }
    
    public DiscountBean() {
        dao = new DiscountDAO();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    public DiscountDTO getDto() {
        return dto;
    }

    public void setDto(DiscountDTO dto) {
        this.dto = dto;
    }
    
    
}
