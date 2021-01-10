/*
 * Decompiled with CFR 0.150.
 */
package com.compilex.CosmicCrate.database;

import com.compilex.CosmicCrate.database.SQLManager;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SQLConnectionThread {
    private static Connection con = null;

    public static Connection getConnection() {
        try {
            if (con == null || con.isClosed() || !con.isValid(1000)) {
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

