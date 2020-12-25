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
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import khangtl.dtos.DiscountDTO;
import khangtl.utils.MyConnection;

/**
 *
 * @author Peter
 */
public class DiscountDAO implements Serializable {

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

    public DiscountDTO checkAvailableDiscount(String code) throws Exception {
        DiscountDTO dto = null;
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        now = sf.parse(sf.format(now));
        String sql = "SELECT Code, DiscountPercent FROM Discounts WHERE Code=? AND ApplyDate <= ? AND ExpiredDate >= ?";
        try {
            conn = MyConnection.getConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, code);
            preStm.setTimestamp(2, new Timestamp(now.getTime()));
            preStm.setTimestamp(3, new Timestamp(now.getTime()));
            rs = preStm.executeQuery();
            if (rs.next()) {
                dto = new DiscountDTO(rs.getString("Code"), rs.getInt("DiscountPercent"));
            }
        } finally {
            closeConnection();
        }
        return dto;
    }
}
