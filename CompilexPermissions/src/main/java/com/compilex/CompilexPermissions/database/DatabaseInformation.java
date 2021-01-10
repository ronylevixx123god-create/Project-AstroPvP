/*
 * Decompiled with CFR 0.150.
 */
package com.compilex.CompilexPermissions.database;

public class DatabaseInformation {
    protected static String HOST;
    protected static int PORT;
    protected static String USER;
    protected static String PASS;
    protected static String DATABASENAME;
    protected static String SQL_URL;

    public DatabaseInformation(String HOST, int PORT, String USER, String PASS, String DATABASENAME) {
        DatabaseInformation.HOST = HOST;
        DatabaseInformation.PORT = PORT;
        DatabaseInformation.USER = USER;
        DatabaseInformation.PASS = PASS;
        DatabaseInformation.DATABASENAME = DATABASENAME;
        SQL_URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASENAME + "?autoReconnect=true";
    }
}

