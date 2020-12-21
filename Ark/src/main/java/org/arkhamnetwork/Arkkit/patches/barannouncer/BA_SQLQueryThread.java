/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 */
package org.arkhamnetwork.Arkkit.patches.barannouncer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import org.arkhamnetwork.Arkkit.patches.barannouncer.BA_SQLConnection;
import org.arkhamnetwork.Arkkit.patches.barannouncer.BA_SQLManager;
import org.bukkit.Bukkit;

public class BA_SQLQueryThread
extends Thread {
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void run() {
        block13: while (true) {
            try {
                Thread.sleep(250L);
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }
            Iterator<String> iterator = BA_SQLManager.sql_query.iterator();
            while (true) {
                if (!iterator.hasNext()) continue block13;
                String query = iterator.next();
                Connection con = null;
                PreparedStatement pst = null;
                try {
                    pst = BA_SQLConnection.getConnection().prepareStatement(query);
                    pst.executeUpdate();
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                    Bukkit.getLogger().info("query = " + query);
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
                BA_SQLManager.sql_query.remove(query);
            }
            break;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void doWork() {
        for (String query : BA_SQLManager.sql_query) {
            Connection con = null;
            PreparedStatement pst = null;
            try {
                pst = BA_SQLConnection.getConnection().prepareStatement(query);
                pst.executeUpdate();
            }
            catch (Exception ex) {
                ex.printStackTrace();
                Bukkit.getLogger().info("query = " + query);
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
            BA_SQLManager.sql_query.remove(query);
        }
    }
}

