/*
 * Decompiled with CFR 0.150.
 */
package org.arkhamnetwork.Arkkit.patches.antimacro;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.arkhamnetwork.Arkkit.patches.antimacro.AA_SQLManager;

public class AA_SQLConnectionThread {
    private static Connection con = null;
    public static int query_count = 0;

    public static Connection getConnection() {
        try {
            if (query_count >= 1000) {
                if (con != null) {
                    con.close();
                }
                con = DriverManager.getConnection(AA_SQLManager.sqlurl, AA_SQLManager.user, AA_SQLManager.password);
                query_count = 0;
            }
            if (con == null || con.isClosed()) {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection(AA_SQLManager.sqlurl, AA_SQLManager.user, AA_SQLManager.password);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            try {
                con = DriverManager.getConnection(AA_SQLManager.sqlurl, AA_SQLManager.user, AA_SQLManager.password);
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
            pst = AA_SQLConnectionThread.getConnection().prepareStatement(query);
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

