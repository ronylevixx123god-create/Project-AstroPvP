package net.astropvp;

import net.astropvp.commands.CollectCommand;
import net.astropvp.sql.Database;
import net.astropvp.sql.SQLQueryCustomThread;
import net.astropvp.struct.CollectManager;
import net.astropvp.struct.PlayerCollect;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class CollectPlugin extends JavaPlugin {
    private static CollectPlugin instance;
    private Database sqlDatabase;
    private SQLQueryCustomThread sqlQueryThread = new SQLQueryCustomThread();
    private CollectManager collectManager;

    public void onEnable() {
        instance = this;
        registerCommands();
        this.sqlDatabase = new Database(this);
        if(this.sqlDatabase.isConnected())
            Bukkit.getConsoleSender().sendMessage("SQL Database for Cosmic Tournaments Connected");
        if (!this.sqlDatabase.isConnected()) {
            Bukkit.getLogger().severe("Unable to connect to sql database! Please ensure the sql credentials are correct");
            Bukkit.getPluginManager().disablePlugin(this);
        } else {
            this.sqlDatabase.executeUpdate("CREATE TABLE IF NOT EXISTS collect_userdata(player_uuid varchar(64) PRIMARY KEY, items char);", false);
            this.sqlQueryThread = new SQLQueryCustomThread();
            this.sqlQueryThread.start();
        }
        this.collectManager = new CollectManager();
    }

    public void onDisable() {
        instance = null;
    }

    public static CollectPlugin getInstance() {
        return instance;
    }

    public Database getSqlDatabase() {
        return this.sqlDatabase;
    }


    private void registerCommands() {
        getCommand("collect").setExecutor(new CollectCommand());
    }

}
