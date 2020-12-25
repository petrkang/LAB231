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
import java.util.HashMap;
import java.util.List;
import khangtl.dtos.ResourceDTO;
import khangtl.utils.MyConnection;

/**
 *
 * @author Peter
 */
public class ResourceDAO implements Serializable {

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

    public List<ResourceDTO> searchResourceWithCondition(String searchName, String searchCategory, String searchUsingDate, int currentPage, int recordsPerPage) throws Exception {
        List<ResourceDTO> result = null;
        int id, balance;
        String name, usingDate, category;
        HashMap<String, Object> params = new HashMap<>();
        params.put("cte.Name", searchName);
        params.put("cte.Category", searchCategory);
        params.put("cte.UsingDate", searchUsingDate);
        String sql = "DECLARE @PageNumber AS INT DECLARE @RowsOfPage AS INT SET @PageNumber=? SET @RowsOfPage=? "
                + "SELECT cte.Id, cte.Name, cte.Balance, cte.UsingDate, cte.Category FROM "
                + "(SELECT ROW_NUMBER() OVER(PARTITION BY ID ORDER BY CASE WHEN ISNULL(nr.Status,'')='Approved' "
                + "THEN 0 ELSE 1 END) AS ST, nr.Id, nr.Name, nr.Balance, nr.UsingDate, nr.Category "
                + "FROM (SELECT r.Id, r.Name, r.UsingDate, CASE WHEN rq.Status='Approved' "
                + "THEN (r.Quota - rd.Amount) ELSE r.Quota END AS Balance, (SELECT c.Name FROM "
                + "Categories c WHERE c.Id = r.Category) AS Category, rq.Status FROM RequestDetails "
                + "rd FULL OUTER JOIN Requests rq ON rd.RequestId = rq.Id FULL OUTER JOIN Resources "
                + "r ON rd.ResourceId = r.Id) nr) cte WHERE cte.ST = 1 AND cte.Balance > 0";
        for (String key : params.keySet()) {
            Object value = params.get(key);
            if (!value.toString().trim().isEmpty()) {
                switch (key) {
                    case "cte.Name":
                        sql += " AND " + key + " LIKE ?";
                        break;
                    case "cte.Category":
                        sql += " AND " + key + "= ?";
                        break;
                    case "cte.UsingDate":
                        sql += " AND " + key + "<= ?";
                        break;
                }
            }
        }
        sql += " ORDER BY cte.Name OFFSET (@PageNumber-1)*@RowsOfPage ROWS FETCH NEXT @RowsOfPage ROWS ONLY";
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
                if (!value.toString().trim().isEmpty()) {
                    preStm.setString(paramsNum, value.toString());
                    paramsNum++;
                }
            }
            rs = preStm.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {
                id = rs.getInt("Id");
                name = rs.getString("Name");
                balance = rs.getInt("Balance");
                usingDate = rs.getDate("UsingDate").toString();
                category = rs.getString("Category");
                result.add(new ResourceDTO(id, balance, name, usingDate, category));
            }
        } finally {
            closeConnection();
        }
        return result;
    }

    public int getNumberOfRows(String searchName, String searchCategory, String searchUsingDate) throws Exception {
        int result = 0;
        HashMap<String, Object> params = new HashMap<>();
        params.put("cte.Name", searchName);
        params.put("cte.Category", searchCategory);
        params.put("cte.UsingDate", searchUsingDate);
        String sql = "SELECT COUNT(cte.Id) AS numOfRows FROM "
                + "(SELECT ROW_NUMBER() OVER(PARTITION BY ID ORDER BY CASE WHEN ISNULL(nr.Status,'')='Approved' "
                + "THEN 0 ELSE 1 END) AS ST, nr.Id, nr.Name, nr.Balance, nr.UsingDate, nr.Category "
                + "FROM (SELECT r.Id, r.Name, r.UsingDate, CASE WHEN rq.Status='Approved' "
                + "THEN (r.Quota - rd.Amount) ELSE r.Quota END AS Balance, (SELECT c.Name FROM "
                + "Categories c WHERE c.Id = r.Category) AS Category, rq.Status FROM RequestDetails "
                + "rd FULL OUTER JOIN Requests rq ON rd.RequestId = rq.Id  FULL OUTER JOIN Resources "
                + "r ON rd.ResourceId = r.Id) nr) cte WHERE cte.ST = 1 AND cte.Balance > 0";
        for (String key : params.keySet()) {
            Object value = params.get(key);
            if (!value.toString().trim().isEmpty()) {
                switch (key) {
                    case "cte.Name":
                        sql += " AND " + key + " LIKE ?";
                        break;
                    case "cte.Category":
                        sql += " AND " + key + "= ?";
                        break;
                    case "cte.UsingDate":
                        sql += " AND " + key + "<= ?";
                        break;
                }
            }
        }
        try {
            conn = MyConnection.getConnection();
            preStm = conn.prepareStatement(sql);
            int paramsNum = 1;
            for (String key : params.keySet()) {
                Object value = params.get(key);
                if (!value.toString().trim().isEmpty()) {
                    preStm.setString(paramsNum, value.toString());
                    paramsNum++;
                }
            }
            rs = preStm.executeQuery();
            if (rs.next()) {
                result = rs.getInt("numOfRows");
            }
        } finally {
            closeConnection();
        }
        return result;
    }

    public int getBalanceById(int id) throws Exception {
        int result = 0;
        String sql = "SELECT cte.Id, cte.Name, cte.Balance, cte.UsingDate, cte.Category FROM "
                + "(SELECT ROW_NUMBER() OVER(PARTITION BY ID ORDER BY  CASE WHEN ISNULL(nr.Status,'')='Approved' "
                + "THEN 0 ELSE 1 END) AS ST, nr.Id, nr.Name, nr.Balance, nr.UsingDate, nr.Category "
                + "FROM (SELECT r.Id, r.Name, r.UsingDate, CASE WHEN rq.Status='Approved' "
                + "THEN (r.Quota - rd.Amount) ELSE r.Quota END AS Balance, (SELECT c.Name FROM "
                + "Categories c WHERE c.Id = r.Category) AS Category, rq.Status FROM RequestDetails "
                + "rd FULL OUTER JOIN Requests rq ON rd.RequestId = rq.Id  FULL OUTER JOIN Resources "
                + "r ON rd.ResourceId = r.Id) nr) cte WHERE cte.ST = 1 AND cte.Balance > 0 AND cte.Id=?";
        try {
            conn = MyConnection.getConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setInt(1, id);
            rs = preStm.executeQuery();
            if (rs.next()) {
                result = rs.getInt("Balance");
            }
        } finally {
            closeConnection();
        }
        return result;
    }
}
