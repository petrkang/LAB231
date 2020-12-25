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
import khangtl.dtos.StatusDTO;

/**
 *
 * @author Peter
 */
public class StatusDAO implements Serializable {

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

    public ArrayList<StatusDTO> getAllStatus() throws Exception {
        ArrayList<StatusDTO> result = null;
        String name;
        String sql = "SELECT Name FROM Status";
        try {
            conn = MyConnection.getConnection();
            preStm = conn.prepareStatement(sql);
            rs = preStm.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {
                name = rs.getString("Name");
                result.add(new StatusDTO(name));
            }
        } finally {
            closeConnection();
        }
        return result;
    }
}
