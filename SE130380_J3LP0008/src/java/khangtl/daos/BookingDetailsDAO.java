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
import khangtl.utils.MyConnection;

/**
 *
 * @author Peter
 */
public class BookingDetailsDAO implements Serializable {

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

    public int getAmountTourBooked(int id) throws Exception {
        int amountBooked = 0;
        String sql = "SELECT COUNT(bd.TourId) as AmountBooked FROM BookingDetails bd";
        try {
            conn = MyConnection.getConnection();
            preStm = conn.prepareStatement(sql);
            rs = preStm.executeQuery();
            if (rs.next()) {
                amountBooked = rs.getInt("AmountBooked");
            }
        } finally {
            closeConnection();
        }
        return amountBooked;
    }
}
