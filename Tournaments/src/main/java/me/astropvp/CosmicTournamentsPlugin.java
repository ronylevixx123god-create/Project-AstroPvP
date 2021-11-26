package me.astropvp;

import me.astropvp.commands.CommandAdminTournament;
import me.astropvp.commands.CommandTournament;
import me.astropvp.listeners.PlayerListener;
import me.astropvp.sql.Database;
import me.astropvp.sql.SQLQueryCustomThread;
import me.astropvp.struct.PlayerDataManager;
import me.astropvp.struct.Tournament;
import me.matthawkk.plugin.CustomPlugin;
import me.matthawkk.plugin.Plugins;
import org.bukkit.Bukkit;

public class CosmicTournamentsPlugin extends CustomPlugin {
    private static CosmicTournamentsPlugin instance;
    private Database sqlDatabase;
    private SQLQueryCustomThread sqlQueryThread = new SQLQueryCustomThread();
    private PlayerDataManager playerDataManager = new PlayerDataManager();
    private Tournament activeTournament = null;

    public void load() {
        instance = this;
        Plugins.registerListeners(this, new PlayerListener());
        this.sqlDatabase = new Database(this);
        if(this.sqlDatabase.isConnected())
            Bukkit.getConsoleSender().sendMessage("SQL Database for Cosmic Tournaments Connected");
        if (!this.sqlDatabase.isConnected()) {
            Bukkit.getLogger().severe("Unable to connect to sql database! Please ensure the sql credentials are correct");
            Bukkit.getPluginManager().disablePlugin(this);
        } else {
            this.sqlDatabase.executeUpdate("CREATE TABLE IF NOT EXISTS tournament_userdata(player_uuid varchar(64) PRIMARY KEY, inventory varchar(4000), tournamentwins int, tournamentlosses int, matchwins int, matchlosses int, kills int, deaths int, elo int, points int);", false);
            this.sqlQueryThread = new SQLQueryCustomThread();
            this.sqlQueryThread.start();
        }
    }

    public void unload() {
        instance = null;
    }

    public static CosmicTournamentsPlugin getInstance() {
        return instance;
    }

    public Database getSqlDatabase() {
        return this.sqlDatabase;
    }

    public PlayerDataManager getPlayerDataManager() { return this.playerDataManager; }

    public Tournament getActiveTournament() { return this.activeTournament; }

    public void createTournament() { this.activeTournament = new Tournament(); }

    private void registerCommands() {
        getCommand("tournament").setExecutor(new CommandTournament());
        getCommand("admintournament").setExecutor(new CommandAdminTournament());
    }
 }
