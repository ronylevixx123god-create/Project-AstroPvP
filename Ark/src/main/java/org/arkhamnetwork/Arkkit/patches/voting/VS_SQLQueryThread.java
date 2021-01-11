/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 */
package org.arkhamnetwork.Arkkit.patches.voting;

import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;

public class VS_SQLQueryThread
        extends Thread {
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void run() {
        block13:
        while (true) {
            try {
                Thread.sleep(250L);
            } catch (InterruptedException ignored) {
            }
            Iterator<String> iterator = VS_SQLManager.sql_query.iterator();
            while (true) {
                if (!iterator.hasNext()) continue block13;
                String query = iterator.next();
                Connection con = null;
                PreparedStatement pst = null;
                try {
                    pst = VS_SQLConnection.getConnection().prepareStatement(query);
                    pst.executeUpdate();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Bukkit.getLogger().info("query = " + query);
                } finally {
                    try {
                        if (pst != null) {
                            pst.close();
                        }
                        if (con != null) {
                            con.close();
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
                VS_SQLManager.sql_query.remove(query);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void doWork() {
        for (String query : VS_SQLManager.sql_query) {
            Connection con = null;
            PreparedStatement pst = null;
            try {
                pst = VS_SQLConnection.getConnection().prepareStatement(query);
                pst.executeUpdate();
            } catch (Exception ex) {
                ex.printStackTrace();
                Bukkit.getLogger().info("query = " + query);
            } finally {
                try {
                    if (pst != null) {
                        pst.close();
                    }
                    if (con != null) {
                        con.close();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            VS_SQLManager.sql_query.remove(query);
        }
    }
}

