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
import khangtl.dtos.PlaceDTO;
import khangtl.utils.MyConnection;

/**
 *
 * @author Peter
 */
public class PlaceDAO implements Serializable {

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

    public ArrayList<PlaceDTO> getAllPlaces() throws Exception {
        ArrayList<PlaceDTO> result = null;
        String name;
        String sql = "SELECT Name FROM Places";
        try {
            conn = MyConnection.getConnection();
            preStm = conn.prepareStatement(sql);
            rs = preStm.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {
                name = rs.getString("Name");
                result.add(new PlaceDTO(name));
            }
        } finally {
            closeConnection();
        }
        return result;
    }
}
