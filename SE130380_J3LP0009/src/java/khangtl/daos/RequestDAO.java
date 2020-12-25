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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import khangtl.dtos.RequestDTO;
import khangtl.dtos.RequestDetailsDTO;
import khangtl.dtos.ResourceDTO;
import khangtl.utils.MyConnection;

/**
 *
 * @author Peter
 */
public class RequestDAO implements Serializable {

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

    public List<RequestDTO> getAllRequests() throws Exception {
        List<RequestDTO> result = null;
        List<String> storeId;
        String id, user, resourceName, status, category, usingDate;
        int resourceId, balance, amount;
        Date requestDate;
        String sql = "SELECT rq.Id, rq.UserRequest, rq.RequestDate, rq.Status, r.Id AS ResourceId, (SELECT r.Name FROM Resources "
                + "r WHERE r.Id=rd.ResourceId) AS ResourceName, r.UsingDate, CASE WHEN (SELECT rs.Quota - SUM(rqd.Amount) "
                + "FROM Resources rs INNER JOIN RequestDetails rqd ON rqd.ResourceId=rs.Id INNER JOIN Requests rqu "
                + "ON rqu.Id = rqd.RequestId WHERE rs.Id=r.Id AND rqu.Status='Approved' GROUP BY rs.Quota) IS NOT "
                + "NULL THEN (SELECT rs.Quota - SUM(rqd.Amount) FROM Resources rs INNER JOIN RequestDetails rqd ON "
                + "rqd.ResourceId=rs.Id INNER JOIN Requests rqu ON rqu.Id=rqd.RequestId WHERE rs.Id=r.Id AND rqu.Status"
                + "='Approved' GROUP BY rs.Quota) ELSE r.Quota END AS Balance, rd.Amount, (SELECT Name FROM Categories "
                + "WHERE Id=r.Category) AS Category FROM Requests rq INNER JOIN RequestDetails rd ON rd.RequestId"
                + " = rq.Id INNER JOIN Resources r ON r.Id = rd.ResourceId";
        try {
            conn = MyConnection.getConnection();
            preStm = conn.prepareStatement(sql);
            rs = preStm.executeQuery();
            storeId = new ArrayList<>();
            result = new ArrayList<>();
            while (rs.next()) {
                id = rs.getString("Id");
                if (!storeId.contains(id)) {
                    storeId.add(id);
                    user = rs.getString("UserRequest");
                    requestDate = rs.getDate("RequestDate");
                    status = rs.getString("Status");
                    resourceId = rs.getInt("ResourceId");
                    resourceName = rs.getString("ResourceName");
                    balance = rs.getInt("Balance");
                    amount = rs.getInt("Amount");
                    usingDate = rs.getDate("UsingDate").toString();
                    category = rs.getString("Category");
                    List<ResourceDTO> resources = new ArrayList<>();
                    resources.add(new ResourceDTO(resourceId, resourceName, balance, amount, usingDate, category));
                    RequestDTO dto = new RequestDTO(id, user, status, requestDate);
                    dto.setResources(resources);
                    result.add(dto);
                } else {
                    for (RequestDTO dto : result) {
                        if (dto.getId().equals(id)) {
                            resourceId = rs.getInt("ResourceId");
                            resourceName = rs.getString("ResourceName");
                            balance = rs.getInt("Balance");
                            amount = rs.getInt("Amount");
                            usingDate = rs.getDate("UsingDate").toString();
                            category = rs.getString("Category");
                            dto.getResources().add(new ResourceDTO(resourceId, resourceName, balance, amount, usingDate, category));
                        }
                    }
                }
            }
        } finally {
            closeConnection();
        }
        return result;
    }
    
    public List<RequestDTO> searchHistoryRequestWithCondition(String userRequest, String searchRequestDate) throws Exception {
        List<RequestDTO> result = null;
        List<String> storeId;
        String sql = "SELECT rq.Id, rq.UserRequest, rq.RequestDate, rq.Status, r.Id AS ResourceId, (SELECT r.Name FROM Resources "
                + "r WHERE r.Id=rd.ResourceId) AS ResourceName, r.UsingDate, CASE WHEN (SELECT rs.Quota - SUM(rqd.Amount) "
                + "FROM Resources rs INNER JOIN RequestDetails rqd ON rqd.ResourceId=rs.Id INNER JOIN Requests rqu "
                + "ON rqu.Id = rqd.RequestId WHERE rs.Id=r.Id AND rqu.Status='Approved' GROUP BY rs.Quota) IS NOT "
                + "NULL THEN (SELECT rs.Quota - SUM(rqd.Amount) FROM Resources rs INNER JOIN RequestDetails rqd ON "
                + "rqd.ResourceId=rs.Id INNER JOIN Requests rqu ON rqu.Id=rqd.RequestId WHERE rs.Id=r.Id AND rqu.Status"
                + "='Approved' GROUP BY rs.Quota) ELSE r.Quota END AS Balance, rd.Amount, (SELECT Name FROM Categories "
                + "WHERE Id=r.Category) AS Category FROM Requests rq INNER JOIN RequestDetails rd ON rd.RequestId"
                + " = rq.Id INNER JOIN Resources r ON r.Id = rd.ResourceId WHERE UserRequest=?";
        if (!searchRequestDate.isEmpty()) {
            sql += " AND RequestDate<=?";
        }
        String id, user, resourceName, status, category, usingDate;
        int resourceId, balance, amount;
        Date requestDate;
        try {
            conn = MyConnection.getConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, userRequest);
            if (!searchRequestDate.isEmpty()) {
                preStm.setString(2, searchRequestDate);
            }
            rs = preStm.executeQuery();
            storeId = new ArrayList<>();
            result = new ArrayList<>();
            while (rs.next()) {
                
                id = rs.getString("Id");
                if (!storeId.contains(id)) {
                    storeId.add(id);
                    user = rs.getString("UserRequest");
                    requestDate = rs.getDate("RequestDate");
                    status = rs.getString("Status");
                    resourceId = rs.getInt("ResourceId");
                    resourceName = rs.getString("ResourceName");
                    balance = rs.getInt("Balance");
                    amount = rs.getInt("Amount");
                    usingDate = rs.getDate("UsingDate").toString();
                    category = rs.getString("Category");
                    List<ResourceDTO> resources = new ArrayList<>();
                    resources.add(new ResourceDTO(resourceId, resourceName, balance, amount, usingDate, category));
                    RequestDTO dto = new RequestDTO(id, user, status, requestDate);
                    dto.setResources(resources);
                    result.add(dto);
                } else {
                    for (RequestDTO dto : result) {
                        if (dto.getId().equals(id)) {
                            resourceId = rs.getInt("ResourceId");
                            resourceName = rs.getString("ResourceName");
                            balance = rs.getInt("Balance");
                            amount = rs.getInt("Amount");
                            usingDate = rs.getDate("UsingDate").toString();
                            category = rs.getString("Category");
                            dto.getResources().add(new ResourceDTO(resourceId, resourceName, balance, amount, usingDate, category));
                        }
                    }
                }
            }
        } finally {
            closeConnection();
        }
        return result;
    }
    
    public boolean deleteRequestById(String id) throws Exception {
        boolean isDeleted = false;
        String sql = "UPDATE Requests SET Status='Deactive' WHERE Id=?";
        try {
            conn = MyConnection.getConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, id);
            isDeleted = preStm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return isDeleted;
    }

    public List<RequestDTO> searchRequestWithCondition(String searchRequestDate, String searchResourceName, String searchUser, String searchStatus, int currentPage, int recordsPerPage) throws Exception {
        List<RequestDTO> result = null;
        List<String> storeId;
        String id, user, resourceName, status, category, usingDate;
        int resourceId, balance, amount;
        Date requestDate;
        HashMap<String, Object> params = new HashMap<>();
        params.put("rq.RequestDate", searchRequestDate);
        params.put("ResourceName", searchResourceName);
        params.put("rd.UserRequest", searchUser);
        params.put("rq.Status", searchStatus);
        String sql = "DECLARE @PageNumber AS INT DECLARE @RowsOfPage AS INT SET @PageNumber=? SET @RowsOfPage=? "
                + "SELECT rq.Id, rq.UserRequest, rq.RequestDate, rq.Status, r.Id AS ResourceId, (SELECT r.Name FROM Resources "
                + "r WHERE r.Id=rd.ResourceId) AS ResourceName, r.UsingDate, CASE WHEN (SELECT rs.Quota - SUM(rqd.Amount) "
                + "FROM Resources rs INNER JOIN RequestDetails rqd ON rqd.ResourceId=rs.Id INNER JOIN Requests rqu "
                + "ON rqu.Id = rqd.RequestId WHERE rs.Id=r.Id AND rqu.Status='Approved' GROUP BY rs.Quota) IS NOT "
                + "NULL THEN (SELECT rs.Quota - SUM(rqd.Amount) FROM Resources rs INNER JOIN RequestDetails rqd ON "
                + "rqd.ResourceId=rs.Id INNER JOIN Requests rqu ON rqu.Id=rqd.RequestId WHERE rs.Id=r.Id AND rqu.Status"
                + "='Approved' GROUP BY rs.Quota) ELSE r.Quota END AS Balance, rd.Amount, (SELECT Name FROM Categories "
                + "WHERE Id=r.Category) AS Category FROM Requests rq INNER JOIN RequestDetails rd ON rd.RequestId"
                + " = rq.Id INNER JOIN Resources r ON r.Id = rd.ResourceId WHERE rq.Status!='Deactive'";
        for (String key : params.keySet()) {
            Object value = params.get(key);
            if (!value.toString().trim().isEmpty()) {
                switch (key) {
                    case "rq.RequestDate":
                        sql += " AND " + key + "<=?";
                        break;
                    case "ResourceName":
                        sql += " AND " + key + "LIKE ?";
                        break;
                    case "rd.UserRequest":
                        sql += " AND " + key + "LIKE ?";
                        break;
                    case "rq.Status":
                        sql += " AND " + key + "=?";
                        break;
                }
            }
        }
        sql += " ORDER BY rq.RequestDate ASC OFFSET (@PageNumber-1)*@RowsOfPage ROWS FETCH NEXT @RowsOfPage ROWS ONLY";
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
            storeId = new ArrayList<>();
            while (rs.next()) {
                id = rs.getString("Id");
                if (!storeId.contains(id)) {
                    storeId.add(id);
                    user = rs.getString("UserRequest");
                    requestDate = rs.getDate("RequestDate");
                    status = rs.getString("Status");
                    resourceId = rs.getInt("ResourceId");
                    resourceName = rs.getString("ResourceName");
                    balance = rs.getInt("Balance");
                    amount = rs.getInt("Amount");
                    usingDate = rs.getDate("UsingDate").toString();
                    category = rs.getString("Category");
                    List<ResourceDTO> resources = new ArrayList<>();
                    resources.add(new ResourceDTO(resourceId, resourceName, balance, amount, usingDate, category));
                    RequestDTO dto = new RequestDTO(id, user, status, requestDate);
                    dto.setResources(resources);
                    result.add(dto);
                } else {
                    for (RequestDTO dto : result) {
                        if (dto.getId().equals(id)) {
                            resourceId = rs.getInt("ResourceId");
                            resourceName = rs.getString("ResourceName");
                            balance = rs.getInt("Balance");
                            amount = rs.getInt("Amount");
                            usingDate = rs.getDate("UsingDate").toString();
                            category = rs.getString("Category");
                            dto.getResources().add(new ResourceDTO(resourceId, resourceName, balance, amount, usingDate, category));
                        }
                    }
                }
            }
        } finally {
            closeConnection();
        }
        return result;
    }

    public int getNumberOfRows(String searchRequestDate, String searchResourceName, String searchUser, String searchStatus) throws Exception {
        int result = 0;
        HashMap<String, Object> params = new HashMap<>();
        params.put("rq.RequestDate", searchRequestDate);
        params.put("ResourceName", searchResourceName);
        params.put("rd.UserRequest", searchUser);
        params.put("rq.Status", searchStatus);
        String sql = "SELECT COUNT(DISTINCT rq.Id) AS numOfRows FROM Requests rq INNER JOIN RequestDetails "
                + "rd ON rd.RequestId = rq.Id INNER JOIN Resources r ON r.Id = rd.ResourceId WHERE rq.Status!='Deactive'";
        for (String key : params.keySet()) {
            Object value = params.get(key);
            if (!value.toString().trim().isEmpty()) {
                switch (key) {
                    case "rq.RequestDate":
                        sql += " AND " + key + "<=?";
                        break;
                    case "ResourceName":
                        sql += " AND " + key + "LIKE ?";
                        break;
                    case "rd.UserRequest":
                        sql += " AND " + key + "LIKE ?";
                        break;
                    case "rq.Status":
                        sql += " AND " + key + "=?";
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

    public boolean sendRequest(RequestDTO dto) throws Exception {
        boolean isSended = false;
        String insertRequestSQL = "INSERT INTO Requests(Id, UserRequest, RequestDate, Status) VALUES(?,?,?,?)";
        try {
            conn = MyConnection.getConnection();
            preStm = conn.prepareStatement(insertRequestSQL);
            preStm.setString(1, dto.getId());
            preStm.setString(2, dto.getUserRequest());
            preStm.setTimestamp(3, new Timestamp(dto.getRequestDate().getTime()));
            preStm.setString(4, dto.getStatus());
            isSended = preStm.executeUpdate() > 0;
            if (isSended) {
                String insertRequestDetailsSQL = "INSERT INTO RequestDetails(RequestId, ResourceId, Amount) VALUES(?,?,?)";
                conn.setAutoCommit(false);
                preStm = conn.prepareStatement(insertRequestDetailsSQL);
                for (RequestDetailsDTO detail : dto.getDetails()) {
                    preStm.setString(1, detail.getRequestId());
                    preStm.setInt(2, detail.getResourceId());
                    preStm.setInt(3, detail.getAmount());
                    isSended = preStm.executeUpdate() > 0;
                }
                conn.commit();
            }
        } catch (Exception e) {
            isSended = false;
            conn.rollback();
        } finally {
            closeConnection();
        }
        return isSended;
    }

    public boolean handleRequest(String requestId, String status) throws Exception {
        boolean isHandled;
        if (status.equals("Approve")) {
            status = "Approved";
        } else if (status.equals("Cancel")) {
            status = "Canceled";
        }
        String sql = "UPDATE Requests SET Status=? WHERE Id=?";
        try {
            conn = MyConnection.getConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, status);
            preStm.setString(2, requestId);
            isHandled = preStm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return isHandled;
    }
}
