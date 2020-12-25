/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangtl.dtos;

import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;

/**
 *
 * @author Peter
 */
public class QuestionDTO implements Serializable {

    private int id;
    private String cont, status, subject;
    private Date createDate;
    private ArrayList<AnswerDTO> answerList;

    public QuestionDTO() {
    }

    public QuestionDTO(int id, String cont, ArrayList<AnswerDTO> answerList) {
        this.id = id;
        this.cont = cont;
        this.answerList = answerList;
    }

    public QuestionDTO(int id, String cont, String subject, ArrayList<AnswerDTO> answerList) {
        this.id = id;
        this.cont = cont;
        this.subject = subject;
        this.answerList = answerList;
    }

    public QuestionDTO(int id, String cont, String status, String subject, Date createDate, ArrayList<AnswerDTO> answerList) {
        this.id = id;
        this.cont = cont;
        this.status = status;
        this.subject = subject;
        this.createDate = createDate;
        this.answerList = answerList;
    }

    public QuestionDTO(String cont, String subject, ArrayList<AnswerDTO> answerList) {
        this.cont = cont;
        this.subject = subject;
        this.answerList = answerList;
    }

    public QuestionDTO(String cont, String status, String subject, ArrayList<AnswerDTO> answerList) {
        this.cont = cont;
        this.status = status;
        this.subject = subject;
        this.answerList = answerList;
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

}
