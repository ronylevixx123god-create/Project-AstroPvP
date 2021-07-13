package io.apice.clientcomms.sql;

import io.apice.clientcomms.sql.SQLManager;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SQLConnectionThread {
    private static Connection con = null;
    public static int query_count = 0;

    public static Connection getConnection() {
        try {
            if (query_count >= 1000) {
                if (con != null) {
                    con.close();
                }
                con = DriverManager.getConnection(SQLManager.sqlurl, SQLManager.user, SQLManager.password);
                query_count = 0;
            }
            if (con == null || con.isClosed()) {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection(SQLManager.sqlurl, SQLManager.user, SQLManager.password);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            try {
                con = DriverManager.getConnection(SQLManager.sqlurl, SQLManager.user, SQLManager.password);
            }
            catch (Exception err) {
                err.printStackTrace();
            }
        }
        ++query_count;
        return con;
    }

    public static ResultSet getResultSet(String query) {
        PreparedStatement pst = null;
        try {
            pst = SQLConnectionThread.getConnection().prepareStatement(query);
            pst.execute();
            ResultSet rs = pst.getResultSet();
            return rs;
        }
        catch (Exception err) {
            err.printStackTrace();
            return null;
        }
    }
}

