package io.apice.clientcomms.sql;

import io.apice.clientcomms.ClientComms;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLQueryThread extends Thread {
    public void run() {
        while (true) {
            try {
                Thread.sleep(250L);
            } catch (InterruptedException interruptedException) {}
            for (String query : ClientComms.getQueryQueue()) {
                Connection con = null;
                PreparedStatement pst = null;
                try {
                    pst = SQLConnectionThread.getConnection().prepareStatement(query);
                    pst.executeUpdate();
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    try {
                        if (pst != null)
                            pst.close();
                        if (con != null)
                            con.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
                ClientComms.getQueryQueue().remove(query);
            }
        }
    }

    public void doWork() {
        for (String query : ClientComms.getQueryQueue()) {
            Connection con = null;
            PreparedStatement pst = null;
            try {
                pst = SQLConnectionThread.getConnection().prepareStatement(query);
                pst.executeUpdate();
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                try {
                    if (pst != null)
                        pst.close();
                    if (con != null)
                        con.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            ClientComms.getQueryQueue().remove(query);
        }
    }
}
