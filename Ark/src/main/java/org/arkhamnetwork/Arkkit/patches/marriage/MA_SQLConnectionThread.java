/*
 * Decompiled with CFR 0.150.
 */
package org.arkhamnetwork.Arkkit.patches.marriage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.arkhamnetwork.Arkkit.patches.marriage.MA_SQLManager;

public class MA_SQLConnectionThread {
    private static Connection con = null;
    public static int query_count = 0;

    public static Connection getConnection() {
        try {
            if (query_count >= 1000) {
                if (con != null) {
                    con.close();
                }
                con = DriverManager.getConnection(MA_SQLManager.sqlurl, MA_SQLManager.user, MA_SQLManager.password);
                query_count = 0;
            }
            if (con == null || con.isClosed()) {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection(MA_SQLManager.sqlurl, MA_SQLManager.user, MA_SQLManager.password);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            try {
                con = DriverManager.getConnection(MA_SQLManager.sqlurl, MA_SQLManager.user, MA_SQLManager.password);
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
            pst = MA_SQLConnectionThread.getConnection().prepareStatement(query);
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

