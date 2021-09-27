// 
// Decompiled by Procyon v0.5.36
// 

package com.compilex.CosmicCrate.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Iterator;
import java.sql.SQLException;
import java.io.EOFException;
import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;
import java.net.SocketException;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import com.compilex.CosmicCrate.CosmicCrate;

public class SQLQueryThread extends Thread
{
    public SQLQueryThread() {
        SQLManager.initializeSQL((Plugin)CosmicCrate.get());
    }
    
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(250L);
            }
            catch (InterruptedException ex5) {}
            for (final String query : CosmicCrate.sql_query) {
                PreparedStatement pst = null;
                Label_0205: {
                    try {
                        pst = SQLConnectionThread.getConnection().prepareStatement(query);
                        pst.executeUpdate();
                    }
                    catch (Exception ex) {
                        Bukkit.getLogger().info("query = " + query);
                        ex.printStackTrace();
                        if (!(ex instanceof SocketException) && !(ex instanceof CommunicationsException)) {
                            if (!(ex instanceof EOFException)) {
                                try {
                                    if (pst != null) {
                                        pst.close();
                                    }
                                }
                                catch (SQLException ex2) {
                                    ex2.printStackTrace();
                                }
                                break Label_0205;
                            }
                        }
                        try {
                            if (pst == null) {
                                continue;
                            }
                            pst.close();
                        }
                        catch (SQLException ex3) {
                            ex3.printStackTrace();
                        }
                        continue;
                    }
                    finally {
                        try {
                            if (pst != null) {
                                pst.close();
                            }
                        }
                        catch (SQLException ex4) {
                            ex4.printStackTrace();
                        }
                    }
                }
                CosmicCrate.sql_query.remove(query);
            }
        }
    }
    
    public void executeQuery(final String query) {
        final Connection con = null;
        PreparedStatement pst = null;
        try {
            pst = SQLConnectionThread.getConnection().prepareStatement(query);
            pst.executeUpdate();
        }
        catch (Exception ex) {
            Bukkit.getLogger().info("query = " + query);
            ex.printStackTrace();
            try {
                if (pst != null) {
                    pst.close();
                }
                if (con != null) {
                    con.close();
                }
            }
            catch (SQLException ex2) {
                ex2.printStackTrace();
            }
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
            catch (SQLException ex3) {
                ex3.printStackTrace();
            }
        }
    }
    
    public void doWork() {
        for (final String query : CosmicCrate.sql_query) {
            final Connection con = null;
            PreparedStatement pst = null;
            try {
                pst = SQLConnectionThread.getConnection().prepareStatement(query);
                pst.executeUpdate();
            }
            catch (Exception ex) {
                Bukkit.getLogger().info("query = " + query);
                ex.printStackTrace();
                try {
                    if (pst != null) {
                        pst.close();
                    }
                    if (con != null) {
                        con.close();
                    }
                }
                catch (SQLException ex2) {
                    ex2.printStackTrace();
                }
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
                catch (SQLException ex3) {
                    ex3.printStackTrace();
                }
            }
            CosmicCrate.sql_query.remove(query);
        }
    }
}
