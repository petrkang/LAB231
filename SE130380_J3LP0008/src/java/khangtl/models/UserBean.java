/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangtl.models;

import java.io.Serializable;
import khangtl.daos.UserDAO;
import khangtl.dtos.UserDTO;

/**
 *
 * @author Peter
 */
public class UserBean implements Serializable {

    private String id, firstname, lastname, password, role;
    private UserDAO dao;
    private UserDTO dto;

    public UserDTO checkLoginViaFacebook() throws Exception {
        return dao.checkLoginViaFacebook(id);
    }

    public UserDTO checkLogin() throws Exception {
        return dao.checkLogin(id, password);
    }

    public boolean checkExistAccount() throws Exception {
        return dao.checkExistAccount(id);
    }

    public boolean registerAccount() throws Exception {
        return dao.registerAccount(firstname, lastname, id, password);
    }

    public boolean registerAccountViaFacebook() throws Exception {
        return dao.registerAccountViaFacebook(firstname, lastname, id);
    }

    public UserBean() {
        dao = new UserDAO();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public UserDTO getDto() {
        return dto;
    }

    public void setDto(UserDTO dto) {
        this.dto = dto;
    }
}
