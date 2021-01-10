/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mysql.jdbc.exceptions.jdbc4.CommunicationsException
 *  org.bukkit.Bukkit
 *  org.bukkit.plugin.Plugin
 */
package com.compilex.CosmicCrate.database;

import com.compilex.CosmicCrate.CosmicCrate;
import com.compilex.CosmicCrate.database.SQLConnectionThread;
import com.compilex.CosmicCrate.database.SQLManager;
import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;
import java.io.EOFException;
import java.net.SocketException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class SQLQueryThread
extends Thread {
    public SQLQueryThread() {
        SQLManager.initializeSQL((Plugin)CosmicCrate.get());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void run() {
        block15: while (true) {
            try {
                Thread.sleep(250L);
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }
            Iterator<String> iterator = CosmicCrate.sql_query.iterator();
            while (true) {
                if (!iterator.hasNext()) continue block15;
                String query = iterator.next();
                PreparedStatement pst = null;
                try {
                    pst = SQLConnectionThread.getConnection().prepareStatement(query);
                    pst.executeUpdate();
                }
                catch (Exception ex) {
                    Bukkit.getLogger().info("query = " + query);
                    ex.printStackTrace();
                    if (ex instanceof SocketException || ex instanceof CommunicationsException || ex instanceof EOFException) {
                        continue;
                    }
                }
                finally {
                    try {
                        if (pst == null) continue;
                        pst.close();
                    }
                    catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    continue;
                }
                CosmicCrate.sql_query.remove(query);
            }
            break;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void executeQuery(String query) {
        Connection con = null;
        PreparedStatement pst = null;
        try {
            pst = SQLConnectionThread.getConnection().prepareStatement(query);
            pst.executeUpdate();
        }
        catch (Exception ex) {
            Bukkit.getLogger().info("query = " + query);
            ex.printStackTrace();
        }
        finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (con != null) {
                    con.close();
                }
            }
            catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void doWork() {
        for (String query : CosmicCrate.sql_query) {
            Connection con = null;
            PreparedStatement pst = null;
            try {
                pst = SQLConnectionThread.getConnection().prepareStatement(query);
                pst.executeUpdate();
            }
            catch (Exception ex) {
                Bukkit.getLogger().info("query = " + query);
                ex.printStackTrace();
            }
            finally {
                try {
                    if (pst != null) {
                        pst.close();
                    }
                    if (con != null) {
                        con.close();
                    }
                }
                catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            CosmicCrate.sql_query.remove(query);
        }
    }
}

