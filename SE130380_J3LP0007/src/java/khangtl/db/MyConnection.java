/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangtl.db;

import java.io.Serializable;
import java.sql.Connection;
import javax.sql.DataSource;
import javax.naming.Context;
import javax.naming.InitialContext;

/**
 *
 * @author Peter
 */
public class MyConnection implements Serializable {

    public static Connection getConnection() throws Exception {
        Connection conn;
        Context initContext = new InitialContext();
        Context envContext = (Context) initContext.lookup("java:comp/env");
        DataSource ds = (DataSource) envContext.lookup("QuizOnline");
        conn = ds.getConnection();
        return conn;
    }
}
