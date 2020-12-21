/*
 * Decompiled with CFR 0.150.
 */
package org.arkhamnetwork.Arkkit.patches.networkevents;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.arkhamnetwork.Arkkit.patches.networkevents.NE_SQLManager;

public class NE_SQLConnection {
    private static Connection con = null;
    public static int query_count = 0;

    public static Connection getConnection() {
        try {
            if (query_count >= 1000) {
                if (con != null) {
                    con.close();
                }
                con = DriverManager.getConnection(NE_SQLManager.sqlurl, NE_SQLManager.user, NE_SQLManager.password);
                query_count = 0;
            }
            if (con == null || con.isClosed()) {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection(NE_SQLManager.sqlurl, NE_SQLManager.user, NE_SQLManager.password);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        ++query_count;
        return con;
    }

    public static ResultSet getResultSet(String query) {
        PreparedStatement pst = null;
        try {
            pst = NE_SQLConnection.getConnection().prepareStatement(query);
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

