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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import khangtl.dtos.TourDTO;
import khangtl.utils.MyConnection;

/**
 *
 * @author Peter
 */
public class TourDAO implements Serializable {

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

    public ArrayList<TourDTO> searchTourWithCondition(String departure, String destination, String fromDate, String toDate, int minPrice, int maxPrice, int currentPage, int recordsPerPage) throws Exception {
        ArrayList<TourDTO> result = null;
        String name, image, status;
        int id, price, quota;
        boolean isFirst = true;
        HashMap<String, Object> params = new HashMap<>();
        params.put("t.Departure", departure);
        params.put(("t.Destination"), destination);
        params.put("t.FromDate", fromDate);
        params.put("t.ToDate", toDate);
        params.put("minPrice", minPrice);
        params.put("maxPrice", maxPrice);
        String sql = "DECLARE @PageNumber AS INT DECLARE @RowsOfPage AS INT SET @PageNumber=? SET @RowsOfPage=? "
                + "SELECT t.Id, t.Name, t.FromDate, t.ToDate, t.Price, t.Quota, t.Image, t.Departure, t.Destination, t.Status FROM Tours t";
        for (String key : params.keySet()) {
            Object value = params.get(key);
            if (value == null) {
                value = "";
            }
            if (!value.toString().trim().isEmpty() && !value.equals(0)) {
                if (isFirst) {
                    switch (key) {
                        case "t.FromDate":
                            sql += " WHERE " + key + ">= ?";
                            break;
                        case "t.ToDate":
                            sql += " WHERE " + key + "<= ?";
                            break;
                        case "minPrice":
                            sql += " WHERE t.Price >= ?";
                            break;
                        case "maxPrice":
                            sql += " WHERE t.Price <= ?";
                            break;
                        default:
                            sql += " WHERE " + key + "= ?";
                            break;
                    }
                    isFirst = false;
                } else {
                    switch (key) {
                        case "t.FromDate":
                            sql += " AND " + key + ">= ?";
                            break;
                        case "t.ToDate":
                            sql += " AND " + key + "<= ?";
                            break;
                        case "minPrice":
                            sql += " AND t.Price >= ?";
                            break;
                        case "maxPrice":
                            sql += " AND t.Price <= ?";
                            break;
                        default:
                            sql += " AND " + key + "= ?";
                            break;
                    }
                }
            }
        }
        sql += " ORDER BY t.FromDate OFFSET (@PageNumber-1)*@RowsOfPage ROWS FETCH NEXT @RowsOfPage ROWS ONLY";
        try {
            conn = MyConnection.getConnection();
            preStm = conn.prepareStatement(sql);
            int paramsNum = 1;
            preStm.setInt(paramsNum, currentPage);
            paramsNum++;
            preStm.setInt(paramsNum, recordsPerPage);
            paramsNum++;
            for (String key : params.keySet()) {
                Object value = params.get(key);
                if (value == null) {
                    value = "";
                }
                if (!value.toString().trim().isEmpty() && !value.equals(0)) {
                    if (value instanceof String) {
                        preStm.setString(paramsNum, value.toString());
                    } else if (value instanceof Integer) {
                        preStm.setInt(paramsNum, Integer.parseInt(value.toString()));
                    } else if (value instanceof Date) {
                        preStm.setTimestamp(paramsNum, new Timestamp(((Date) value).getTime()));
                    }
                    paramsNum++;
                }
            }
            rs = preStm.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {
                id = rs.getInt("Id");
                name = rs.getString("Name");
                image = rs.getString("Image");
                fromDate = rs.getString("FromDate");
                fromDate = fromDate.substring(0, fromDate.lastIndexOf("."));
                toDate = rs.getString("ToDate");
                toDate = toDate.substring(0, toDate.lastIndexOf("."));
                departure = rs.getString("departure");
                destination = rs.getString("Destination");
                price = rs.getInt("Price");
                quota = rs.getInt("Quota");
                status = rs.getString("Status");
                result.add(new TourDTO(name, image, departure, destination, status, fromDate, toDate, id, price, quota));
            }
        } finally {
            closeConnection();
        }
        return result;
    }

    public boolean checkExistTour(TourDTO dto) throws Exception {
        boolean isExisted = false;
        String sql = "SELECT Id FROM Tours WHERE Name=? AND FromDate=? AND ToDate=? AND Price=? AND Quota=? AND Image=? AND Departure=? AND Destination=?";
        Date start = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(dto.getFromDate());
        Date end = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(dto.getToDate());
        try {
            conn = MyConnection.getConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, dto.getName());
            preStm.setTimestamp(2, new Timestamp(start.getTime()));
            preStm.setTimestamp(3, new Timestamp(end.getTime()));
            preStm.setInt(4, dto.getPrice());
            preStm.setInt(5, dto.getQuota());
            preStm.setString(6, dto.getImage());
            preStm.setString(7, dto.getDeparture());
            preStm.setString(8, dto.getDestination());
            rs = preStm.executeQuery();
            if (rs.next()) {
                isExisted = true;
            }
        } finally {
            closeConnection();
        }
        return isExisted;
    }

    public boolean insertTour(TourDTO dto) throws Exception {
        boolean isInserted = false;
        String sql = "INSERT INTO Tours(Name, FromDate, ToDate, Price, Quota, Image, Departure, Destination, DateImport, Status) VALUES(?,?,?,?,?,?,?,?,?,?)";
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        now = sf.parse(sf.format(now));
        Date start = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(dto.getFromDate());
        Date end = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(dto.getToDate());
        try {
            conn = MyConnection.getConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, dto.getName());
            preStm.setTimestamp(2, new Timestamp(start.getTime()));
            preStm.setTimestamp(3, new Timestamp(end.getTime()));
            preStm.setInt(4, dto.getPrice());
            preStm.setInt(5, dto.getQuota());
            preStm.setString(6, dto.getImage());
            preStm.setString(7, dto.getDeparture());
            preStm.setString(8, dto.getDestination());
            preStm.setTimestamp(9, new Timestamp(now.getTime()));
            preStm.setString(10, "Active");
            isInserted = preStm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return isInserted;
    }

    public int getNumberOfRows() throws Exception {
        int numOfRows = 0;
        String sql = "SELECT COUNT(Id) as numOfRows FROM Tours";
        try {
            conn = MyConnection.getConnection();
            preStm = conn.prepareStatement(sql);
            rs = preStm.executeQuery();
            if (rs.next()) {
                numOfRows = rs.getInt("numOfRows");
            }
        } finally {
            closeConnection();
        }
        return numOfRows;
    }

}
