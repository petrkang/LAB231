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

    private String email, firstname, lastname, password, role, code;
    private UserDAO dao;
    private UserDTO dto;

    public UserDTO checkLogin() throws Exception {
        return dao.checkLogin(email, password);
    }
    
    public boolean checkActiveAccount() throws Exception {
        return dao.checkActiveAccount(email);
    }

    public boolean checkExistAccount() throws Exception {
        return dao.checkExistAccount(email);
    }

    public boolean registerAccount() throws Exception {
        return dao.registerAccount(firstname, lastname, email, password, code);
    }
    
    public boolean checkVerifyCode() throws Exception {
        return dao.checkVerifyCode(email, code);
    }
    
    public boolean changeAccountStatus() throws Exception {
        return dao.changeAccountStatus(email);
    }

    public UserBean() {
        dao = new UserDAO();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
