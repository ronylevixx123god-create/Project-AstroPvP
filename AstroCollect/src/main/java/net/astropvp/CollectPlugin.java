package net.astropvp;

import net.astropvp.commands.CollectCommand;
import net.astropvp.listeners.PlayerListener;
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
    private CollectManager collectManager;
    private SQLQueryCustomThread sqlQueryCustomThread = new SQLQueryCustomThread();

    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        registerCommands();
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        this.sqlDatabase = new Database(this);
        if(this.sqlDatabase.isConnected())
            Bukkit.getConsoleSender().sendMessage("SQL Database for Collect Connected");
        if (!this.sqlDatabase.isConnected()) {
            Bukkit.getLogger().severe("Unable to connect to sql database! Please ensure the sql credentials are correct");
            Bukkit.getPluginManager().disablePlugin(this);
        } else {
            this.sqlDatabase.executeUpdate("CREATE TABLE IF NOT EXISTS collect_userdata(uuid varchar(64), items longblob, name varchar(64), type varchar(32));", false);
            this.sqlQueryCustomThread = new SQLQueryCustomThread();
            this.sqlQueryCustomThread.start();
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

    public CollectManager getCollectManager()  { return this.collectManager; }

    private void registerCommands() {
        getCommand("collect").setExecutor(new CollectCommand());
    }
}
