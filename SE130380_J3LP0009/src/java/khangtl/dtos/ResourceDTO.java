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
public class ResourceDTO implements Serializable {

    private int id, balance, amount;
    private String name, usingDate, category;

    public ResourceDTO() {
    }

    /**
     *
     * @param id
     * @param balance
     * @param name
     * @param usingDate
     * @param category
     */
    public ResourceDTO(int id, int balance, String name, String usingDate, String category) {
        this.id = id;
        this.balance = balance;
        this.name = name;
        this.usingDate = usingDate;
        this.category = category;
    }

    public ResourceDTO(int id, String name, int balance, int amount, String usingDate, String category) {
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.amount = amount;
        this.usingDate = usingDate;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsingDate() {
        return usingDate;
    }

    public void setUsingDate(String usingDate) {
        this.usingDate = usingDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
