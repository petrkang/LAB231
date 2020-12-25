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
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Date;
import khangtl.dtos.BookingDTO;
import khangtl.dtos.BookingDetailsDTO;
import khangtl.utils.MyConnection;
import khangtl.utils.Utility;

/**
 *
 * @author Peter
 */
public class BookingDAO implements Serializable {
    
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
    
    public boolean insertBookingTour(BookingDTO dto) throws Exception {
        boolean isInserted = false;
        String insertBookingSQL = "INSERT INTO Bookings(Id, UserBook, TotalPrice, BookingDate, DiscountCode) VALUES(?,?,?,?,?)";
        try {
            conn = MyConnection.getConnection();
            preStm = conn.prepareStatement(insertBookingSQL);
            preStm.setString(1, dto.getId());
            preStm.setString(2, dto.getUserId());
            preStm.setInt(3, dto.getTotalPrice());
            preStm.setTimestamp(4, new Timestamp(dto.getBookingDate().getTime()));
            if (dto.getDiscount() == null) {
                preStm.setNull(5, Types.VARCHAR);
            } else {
                preStm.setString(5, dto.getDiscount().getCode());
            }
            isInserted = preStm.executeUpdate() > 0;
            if (isInserted) {
                String insertBookingDetailsSQL = "INSERT INTO BookingDetails(BookingId, TourId, Amount, Price) VALUES(?,?,?,?)";
                conn.setAutoCommit(false);
                preStm = conn.prepareStatement(insertBookingDetailsSQL);
                for (BookingDetailsDTO detail : dto.getDetails()) {
                    preStm.setString(1, detail.getBookingId());
                    preStm.setInt(2, detail.getTourId());
                    preStm.setInt(3, detail.getAmount());
                    preStm.setInt(4, detail.getPrice());
                    isInserted = preStm.executeUpdate() > 0;
                }
                conn.commit();
            }
        } catch (SQLException e) {
            isInserted = false;
            conn.rollback();
        } finally {
            closeConnection();
        }
        return isInserted;
    }
}
