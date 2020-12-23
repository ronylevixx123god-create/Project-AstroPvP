/*
 * Decompiled with CFR 0.150.
 */
package org.arkhamnetwork.Arkkit.patches.barannouncer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BA_SQLConnection {
    public static int query_count = 0;
    private static Connection con = null;

    public static Connection getConnection() {
        try {
            if (query_count >= 1000) {
                if (con != null) {
                    con.close();
                }
                con = DriverManager.getConnection(BA_SQLManager.sqlurl, BA_SQLManager.user, BA_SQLManager.password);
                query_count = 0;
            }
            if (con == null || con.isClosed()) {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection(BA_SQLManager.sqlurl, BA_SQLManager.user, BA_SQLManager.password);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ++query_count;
        return con;
    }

    public static ResultSet getResultSet(String query) {
        PreparedStatement pst = null;
        try {
            pst = BA_SQLConnection.getConnection().prepareStatement(query);
            pst.execute();
            ResultSet rs = pst.getResultSet();
            return rs;
        } catch (Exception err) {
            err.printStackTrace();
            return null;
        }
    }
}

