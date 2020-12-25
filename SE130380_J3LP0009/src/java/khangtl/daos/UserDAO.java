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
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
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
    private static String NUMBER_STR = "123456789";

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

    public UserDTO checkLogin(String email, String password) throws Exception {
        String firstname, lastname, role, status;
        UserDTO dto = null;
        password = Utility.hashStringSHA256(password);
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

    public boolean checkActiveAccount(String email) throws Exception {
        boolean isActive = false;
        String sql = "SELECT Email FROM Users WHERE Email=? AND Status='Active'";
        try {
            conn = MyConnection.getConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, email);
            rs = preStm.executeQuery();
            if (rs.next()) {
                isActive = true;
            }
        } finally {
            closeConnection();
        }
        return isActive;
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
    

    public int sendMail(String from, String to, String subject, String message, String code) {
        try {
            Properties prop = System.getProperties();
            prop.setProperty("mail.transport.protocol", "smtp");
            prop.setProperty("mail.host", "smtp.gmail.com");
            prop.put("mail.smtp.auth", "true");
            prop.put("mail.smtp.port", "465");
            prop.put("mail.debug", "true");
            prop.put("mail.smtp.socketFactory.port", "465");
            prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            prop.put("mail.smtp.socketFactory.fallback", "false");
            Authenticator auth = new SMTPAuthenticator();
            Session session = Session.getInstance(prop, auth);

            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
            msg.setSubject(subject);
            msg.setText(message + "Please enter the following code to verify your email: " + code);

            msg.setHeader("MyMail", "Mr. ABC");
            msg.setSentDate(new Date());
            Transport.send(msg);
            return 0;
        } catch (Exception e) {
            return -1;
        }
    }

    private class SMTPAuthenticator extends Authenticator {

        @Override
        public PasswordAuthentication getPasswordAuthentication() {
            String username = "khangtlse130380@fpt.edu.vn";
            String password = "DAIHOCFPTHOCHIMINHCITY123";
            return new PasswordAuthentication(username, password);
        }
    }
    
    public boolean checkVerifyCode(String email, String code) throws Exception {
        boolean isVerified = false;
        String sql = "SELECT Email, Code FROM Users WHERE Email=? AND Code=?";
        try {
            conn = MyConnection.getConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, email);
            preStm.setString(2, code);
            rs = preStm.executeQuery();
            if (rs.next()) {
                isVerified = true;
            }
        } finally {
            closeConnection();
        }
        return isVerified;
    }
    
    public boolean changeAccountStatus(String email) throws Exception {
        boolean isChanged = false;
        String sql = "UPDATE Users SET Status='Active' WHERE Email=?";
        try {
            conn = MyConnection.getConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, email);
            isChanged = preStm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return isChanged;
    }
    
    public String generateRandomString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sb.append(NUMBER_STR.charAt(new Random().nextInt(NUMBER_STR.length())));
        }
        return sb.toString();
    }

    public boolean registerAccount(String firstname, String lastname, String email, String password, String code) throws Exception {
        boolean isRegistered = false;
        password = Utility.hashStringSHA256(password);
        String sql = "INSERT INTO Users(FirstName, LastName, Email, Password, Role, Status, Code) VALUES(?,?,?,?,?,'New',?)";
        try {
            conn = MyConnection.getConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, firstname);
            preStm.setString(2, lastname);
            preStm.setString(3, email);
            preStm.setString(4, password);
            preStm.setString(5, "Employee");
            preStm.setString(6, code);
            isRegistered = preStm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return isRegistered;
    }
}
