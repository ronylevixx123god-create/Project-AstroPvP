package net.astropvp.sql;

import net.astropvp.CollectPlugin;
import org.bukkit.Bukkit;

import java.sql.PreparedStatement;
import java.util.Collections;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class SQLQueryCustomThread extends Thread {
    private volatile CopyOnWriteArrayList<String> querys = new CopyOnWriteArrayList();

    public void addQuery(String query) {
        this.querys.add(query);
    }

    public void run() {
        while(true) {
            try {
                Thread.sleep(250L);
            } catch (Exception ignored) {
            }

            this.execute();
        }
    }

    public void execute() {
        if (!this.querys.isEmpty()) {
            StringBuilder executed = new StringBuilder();
            try {
                PreparedStatement statement = CollectPlugin.getInstance().getSqlDatabase().getConnection().prepareStatement("");
                try {
                    for (String query : this.querys) {
                        statement.addBatch(query);
                        executed.append(query).append(", ");
                        this.querys.remove(query);
                    }
                    statement.executeBatch();
                } finally {
                    if (Collections.singletonList(statement).get(0) != null) {
                        statement.close();
                    }
                }
            } catch (Exception var9) {
                Bukkit.getLogger().info("Error while executing queries: " + executed.toString());
                var9.printStackTrace();
            }

        }
    }
}

