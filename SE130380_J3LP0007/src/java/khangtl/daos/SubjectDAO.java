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
import java.util.ArrayList;
import khangtl.db.MyConnection;
import khangtl.dtos.SubjectDTO;

/**
 *
 * @author Peter
 */
public class SubjectDAO implements Serializable {

    private static Connection conn;
    private static PreparedStatement preStm;
    private static ResultSet rs;

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

    public ArrayList<SubjectDTO> getAllSubjects() throws Exception {
        ArrayList<SubjectDTO> result = null;
        String id, name;
        String sql = "SELECT Id, Name FROM Subjects";
        try {
            conn = MyConnection.getConnection();
            preStm = conn.prepareStatement(sql);
            rs = preStm.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {
                id = rs.getString("Id");
                name = rs.getString("Name");
                result.add(new SubjectDTO(id, name));
            }
        } finally {
            closeConnection();
        }
        return result;
    }
}
