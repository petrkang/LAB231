/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangtl.models;

import java.io.Serializable;
import java.util.ArrayList;
import khangtl.daos.SubjectDAO;
import khangtl.dtos.SubjectDTO;

/**
 *
 * @author Peter
 */
public class SubjectBean implements Serializable {

    private String id, name;
    private int questionQuantity, testTime;
    private SubjectDAO dao;
    private SubjectDTO dto;
    
    public ArrayList<SubjectDTO> getAllSubjects() throws Exception {
        return dao.getAllSubjects();
    }

    public SubjectBean() {
        dao = new SubjectDAO();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuestionQuantity() {
        return questionQuantity;
    }

    public void setQuestionQuantity(int questionQuantity) {
        this.questionQuantity = questionQuantity;
    }

    public int getTestTime() {
        return testTime;
    }

    public void setTestTime(int testTime) {
        this.testTime = testTime;
    }

    public SubjectDTO getDto() {
        return dto;
    }

    public void setDto(SubjectDTO dto) {
        this.dto = dto;
    }

}
