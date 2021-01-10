/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 */
package com.compilex.CompilexPermissions.threads;

import com.compilex.CompilexPermissions.CompilexPermissions;
import com.compilex.CompilexPermissions.database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.bukkit.Bukkit;

public class SQLQueryThread
extends Thread {
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(200L);
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }
            for (String query : CompilexPermissions.get().getSqlQueryList()) {
                Connection con = null;
                PreparedStatement pst = null;
                try {
                    pst = DatabaseConnection.getConnection().prepareStatement(query);
                    pst.executeUpdate();
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                    Bukkit.getLogger().info("[CompilexPermissions] BAD QUERY! = " + query);
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
                CompilexPermissions.get().getSqlQueryList().remove(query);
            }
            if (!CompilexPermissions.syncPermissionChanges) continue;
            CompilexPermissions.get().getPermissionSyncThread().syncPendingPermissions();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void executeQuery(String query) {
        Connection con = null;
        PreparedStatement pst = null;
        try {
            pst = DatabaseConnection.getConnection().prepareStatement(query);
            pst.executeUpdate();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            Bukkit.getLogger().info("[CompilexPermissions] BAD QUERY! = " + query);
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
        for (String query : CompilexPermissions.get().getSqlQueryList()) {
            Connection con = null;
            PreparedStatement pst = null;
            try {
                pst = DatabaseConnection.getConnection().prepareStatement(query);
                pst.executeUpdate();
            }
            catch (Exception ex) {
                ex.printStackTrace();
                Bukkit.getLogger().info("BAD QUERY! = " + query);
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
            CompilexPermissions.get().getSqlQueryList().remove(query);
        }
        if (CompilexPermissions.syncPermissionChanges) {
            CompilexPermissions.get().getPermissionSyncThread().syncPendingPermissions();
        }
    }
}

