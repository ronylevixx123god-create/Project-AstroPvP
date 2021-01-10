/*
 * Decompiled with CFR 0.150.
 */
package com.compilex.CompilexPermissions.database;

import com.compilex.CompilexPermissions.database.DatabaseInformation;
import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    private static Connection con = null;

    public static Connection getConnection() throws Exception {
        if (con == null || con.isClosed()) {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(DatabaseInformation.SQL_URL, DatabaseInformation.USER, DatabaseInformation.PASS);
        }
        return con;
    }

    public static void closeConnection() throws Exception {
        if (con != null) {
            con.close();
        }
    }
}

