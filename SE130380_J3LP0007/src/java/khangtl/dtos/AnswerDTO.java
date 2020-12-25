/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangtl.dtos;

import java.io.Serializable;

/**
 *
 * @author Peter
 */
public class AnswerDTO implements Serializable {

    private int id;
    private String cont;
    private boolean isCorrect;

    public AnswerDTO() {
    }

    public AnswerDTO(int id, String cont) {
        this.id = id;
        this.cont = cont;
    }

    public AnswerDTO(String cont, boolean isCorrect) {
        this.cont = cont;
        this.isCorrect = isCorrect;
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

    public boolean isIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }
}
