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
import java.util.List;
import khangtl.dtos.CategoryDTO;
import khangtl.utils.MyConnection;

/**
 *
 * @author Peter
 */
public class CategoryDAO implements Serializable {
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
    
    public List<CategoryDTO> getAllCategory() throws Exception {
        List<CategoryDTO> result = null;
        int id;
        String name;
        String sql = "SELECT Id, Name FROM Categories";
        try {
            conn = MyConnection.getConnection();
            preStm = conn.prepareStatement(sql);
            rs = preStm.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {
                id = rs.getInt("Id");
                name = rs.getString("Name");
                result.add(new CategoryDTO(id, name));
            }
        } finally {
            closeConnection();
        }
        return result;
    }
}
