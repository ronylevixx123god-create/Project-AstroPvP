// 
// Decompiled by Procyon v0.5.36
// 

package com.compilex.CosmicCrate.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.Connection;

public class SQLConnectionThread
{
    private static Connection con;
    
    public static Connection getConnection() {
        try {
            if (SQLConnectionThread.con == null || SQLConnectionThread.con.isClosed() || !SQLConnectionThread.con.isValid(1000)) {
                Class.forName("com.mysql.jdbc.Driver");
                SQLConnectionThread.con = DriverManager.getConnection(SQLManager.sqlurl, SQLManager.user, SQLManager.password);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            try {
                SQLConnectionThread.con = DriverManager.getConnection(SQLManager.sqlurl, SQLManager.user, SQLManager.password);
            }
            catch (Exception err) {
                err.printStackTrace();
            }
        }
        return SQLConnectionThread.con;
    }
    
    public static ResultSet getResultSet(final String query) {
        PreparedStatement pst = null;
        try {
            pst = getConnection().prepareStatement(query);
            pst.execute();
            final ResultSet rs = pst.getResultSet();
            return rs;
        }
        catch (Exception err) {
            err.printStackTrace();
            return null;
        }
    }
    
    static {
        SQLConnectionThread.con = null;
    }
}
