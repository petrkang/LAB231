/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangtl.daos;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Base64;
import khangtl.db.MyConnection;
import khangtl.dtos.UserDTO;

/**
 *
 * @author Peter
 */
public class UserDAO implements Serializable {

    private Connection conn = null;
    private PreparedStatement preStm = null;
    private ResultSet rs = null;

    private void closeConnection() throws Exception {
        if (rs != null) {
            rs.close();
        }
        if (preStm != null) {
            preStm.close();
        }
        if (conn != null) {
            conn.close();
        }
    }

    private String hashStringSHA256(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        password = Base64.getEncoder().encodeToString(hash);
        return password;
    }

    public UserDTO checkLogin(String email, String password) throws Exception {
        String firstname, lastname, role, status;
        UserDTO dto = null;
        password = hashStringSHA256(password);
        String sql = "SELECT FirstName, LastName, Role, Status FROM Users WHERE Email=? AND Password=?";
        try {
            conn = MyConnection.getConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, email);
            preStm.setString(2, password);
            rs = preStm.executeQuery();
            if (rs.next()) {
                firstname = rs.getString("FirstName");
                lastname = rs.getString("LastName");
                role = rs.getString("Role");
                status = rs.getString("Status");
                dto = new UserDTO(email, firstname, lastname, role, status);
            }
        } finally {
            closeConnection();
        }
        return dto;
    }
    
    public boolean checkExistAccount(String email) throws Exception {
        boolean isExisted = false;
        String sql = "SELECT Email FROM Users WHERE Email=?";
        try {
            conn = MyConnection.getConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, email);
            rs = preStm.executeQuery();
            if (rs.next()) {
                isExisted = true;
            }
        } finally {
            closeConnection();
        }
        return isExisted;
    }

    public boolean registerAccount(String firstname, String lastname, String email, String password, String role) throws Exception {
        boolean isRegistered = false;
        password = hashStringSHA256(password);
        String sql = "INSERT INTO Users(FirstName, LastName, Email, Password, Role, Status) VALUES(?,?,?,?,?,?)";
        try {
            conn = MyConnection.getConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, firstname);
            preStm.setString(2, lastname);
            preStm.setString(3, email);
            preStm.setString(4, password);
            preStm.setString(5, role);
            preStm.setString(6, "New");
            isRegistered = preStm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return isRegistered;
    }
}
