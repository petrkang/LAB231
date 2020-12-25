/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangtl.daos;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import khangtl.dtos.UserDTO;
import khangtl.utils.MyConnection;
import khangtl.utils.Utility;

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

    public UserDTO checkLogin(String id, String password) throws Exception {
        String firstname, lastname, role;
        UserDTO dto = null;
        password = Utility.hashStringSHA256(password);
        String sql = "SELECT FirstName, LastName, Role FROM Users WHERE Id=? AND Password=?";
        try {
            conn = MyConnection.getConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, id);
            preStm.setString(2, password);
            rs = preStm.executeQuery();
            if (rs.next()) {
                firstname = rs.getString("FirstName");
                lastname = rs.getString("LastName");
                role = rs.getString("Role");
                dto = new UserDTO(id, firstname, lastname, role);
            }
        } finally {
            closeConnection();
        }
        return dto;
    }

    public boolean checkActiveAccount(String id) throws Exception {
        boolean isActive = false;
        String sql = "SELECT Id FROM Users WHERE Id=? AND Status='Active'";
        try {
            conn = MyConnection.getConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, id);
            rs = preStm.executeQuery();
            if (rs.next()) {
                isActive = true;
            }
        } finally {
            closeConnection();
        }
        return isActive;
    }

    public boolean checkExistAccount(String id) throws Exception {
        boolean isExisted = false;
        String sql = "SELECT Id FROM Users WHERE Id=?";
        try {
            conn = MyConnection.getConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, id);
            rs = preStm.executeQuery();
            if (rs.next()) {
                isExisted = true;
            }
        } finally {
            closeConnection();
        }
        return isExisted;
    }

    public boolean registerAccount(String firstname, String lastname, String id, String password) throws Exception {
        boolean isRegistered = false;
        password = Utility.hashStringSHA256(password);
        String sql = "INSERT INTO Users(FirstName, LastName, Id, Password, Role, Status) VALUES(?,?,?,?,?,'Active')";
        try {
            conn = MyConnection.getConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, firstname);
            preStm.setString(2, lastname);
            preStm.setString(3, id);
            preStm.setString(4, password);
            isRegistered = preStm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return isRegistered;
    }

    public boolean registerAccountViaFacebook(String firstname, String lastname, String facebookId) throws Exception {
        boolean isRegistered = false;
        String sql = "INSERT INTO Users(Id, FirstName, LastName, FacebookId, Role, Status) VALUES(?,?,?,?,'Customer','Active')";
        try {
            conn = MyConnection.getConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, facebookId);
            preStm.setString(2, firstname);
            preStm.setString(3, lastname);
            preStm.setString(4, facebookId);
            isRegistered = preStm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return isRegistered;
    }
    
    public UserDTO checkLoginViaFacebook(String facebookId) throws Exception {
        UserDTO dto = null;
        String firstname, lastname, role, fbId;
        String sql = "SELECT FirstName, LastName, Role, FacebookId FROM Users WHERE FacebookId=?";
        try {
            conn = MyConnection.getConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, facebookId);
            rs = preStm.executeQuery();
            if (rs.next()) {
                firstname = rs.getString("FirstName");
                lastname = rs.getString("LastName");
                role = rs.getString("Role");
                fbId = rs.getString("FacebookId");
                dto = new UserDTO(fbId, firstname, lastname, role);
            }
        } finally {
            closeConnection();
        }
        return dto;
    }
}
