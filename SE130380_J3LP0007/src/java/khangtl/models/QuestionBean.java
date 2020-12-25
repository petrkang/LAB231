/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangtl.models;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import khangtl.daos.QuestionDAO;
import khangtl.dtos.AnswerDTO;
import khangtl.dtos.QuestionDTO;

/**
 *
 * @author Peter
 */
public class QuestionBean implements Serializable {

    private int id;
    private String cont, status, subject;
    private Date createDate;
    private ArrayList<AnswerDTO> answerList;
    private QuestionDAO dao;
    private QuestionDTO dto;

    public ArrayList<QuestionDTO> searchQuestionWithCondition() throws Exception {
        return dao.searchQuestionWithCondition(cont, subject, status);
    }
    
    public boolean checkExistQuestion() throws Exception {
        return dao.checkExistQuestion(cont, subject);
    }
    
    public boolean insertQuestion() throws Exception {
        return dao.insertQuestion(dto);
    }
    
    public boolean updateQuestion() throws Exception {
        return dao.updateQuestion(id, dto);
    }
    
    public boolean deleteQuestionById() throws Exception {
        return dao.deleteQuestionById(id);
    }
    
    public ArrayList<QuestionDTO> getListQuestionToTest() throws Exception {
        return dao.getListQuestionToTest(subject);
    }
    
    public int getTimeToTest() throws Exception {
        return dao.getTimeToTest(subject);
    }

    public QuestionBean() {
        dao = new QuestionDAO();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCont() {
        return cont;
    }

    public void setCont(String cont) {
        this.cont = cont;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public ArrayList<AnswerDTO> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(ArrayList<AnswerDTO> answerList) {
        this.answerList = answerList;
    }

    public QuestionDTO getDto() {
        return dto;
    }

    public void setDto(QuestionDTO dto) {
        this.dto = dto;
    }

}
