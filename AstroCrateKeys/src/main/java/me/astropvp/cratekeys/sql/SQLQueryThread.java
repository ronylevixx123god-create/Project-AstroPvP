package me.astropvp.cratekeys.sql;

import me.astropvp.cratekeys.AstroCrateKeysPlugin;
import me.ifamasssxd.cosmicmysterychest.CosmicMysteryChest;
import org.bukkit.Bukkit;

import java.sql.PreparedStatement;
import java.util.Collections;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class SQLQueryThread implements Runnable {
    private volatile CopyOnWriteArrayList<String> querys = new CopyOnWriteArrayList();

    public void addQuery(String query) {
        this.querys.add(query);
    }

    public void run() {
        if (!this.querys.isEmpty()) {
            StringBuilder executed = new StringBuilder();

            try {
                PreparedStatement statement = AstroCrateKeysPlugin.getInstance().getSqlDatabase().getConnection().prepareStatement("");

                try {
                    Iterator var3 = this.querys.iterator();

                    while(var3.hasNext()) {
                        String query = (String)var3.next();
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

