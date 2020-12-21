/*
 * Decompiled with CFR 0.150.
 */
package org.arkhamnetwork.Arkkit.patches.marriage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import org.arkhamnetwork.Arkkit.patches.marriage.MA_SQLConnectionThread;
import org.arkhamnetwork.Arkkit.patches.marriage.Marriage;

public class MA_SQLQueryThread
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
            Iterator<String> iterator = Marriage.sql_query.iterator();
            while (true) {
                if (!iterator.hasNext()) continue block13;
                String query = iterator.next();
                Connection con = null;
                PreparedStatement pst = null;
                try {
                    pst = MA_SQLConnectionThread.getConnection().prepareStatement(query);
                    pst.executeUpdate();
                }
                catch (Exception ex) {
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
                Marriage.sql_query.remove(query);
            }
            break;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void doWork() {
        for (String query : Marriage.sql_query) {
            Connection con = null;
            PreparedStatement pst = null;
            try {
                pst = MA_SQLConnectionThread.getConnection().prepareStatement(query);
                pst.executeUpdate();
            }
            catch (Exception ex) {
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
            Marriage.sql_query.remove(query);
        }
    }
}

