package me.astropvp;

import com.cosmicpvp.cosmicutils.utils.CC;
import lombok.Getter;
import me.astropvp.commands.CommandAdminTournament;
import me.astropvp.commands.CommandTournament;
import me.astropvp.listeners.PlayerListener;
import me.astropvp.sql.Database;
import me.astropvp.sql.SQLQueryCustomThread;
import me.astropvp.struct.PlayerDataManager;
import me.astropvp.struct.Tournament;
import me.astropvp.struct.TournamentManager;
import me.matthawkk.config.BaseConfig;
import me.matthawkk.config.configs.SqlConfig;
import me.matthawkk.plugin.BasePlugin;
import me.matthawkk.plugin.Plugins;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class CosmicTournamentsPlugin extends BasePlugin {
    private static CosmicTournamentsPlugin instance;
    private Database sqlDatabase;
    private SQLQueryCustomThread sqlQueryThread = new SQLQueryCustomThread();
    private PlayerDataManager playerDataManager;
    private Tournament activeTournament;
    @Getter private TournamentManager tournamentManager;

    public void load() {
        instance = this;
        registerCommands();
        this.registerConfigs(new BaseConfig("spawn_locations", this), new SqlConfig(this));
        this.activeTournament = null;


        this.pluginPrefix = CC.DarkAquaB + "(" + CC.AquaB + "Tournaments" + CC.DarkAquaB + ")";
        this.defaultColor = ChatColor.AQUA;


        Plugins.registerListeners(this, new PlayerListener());
        this.sqlDatabase = new Database(this);
        if(this.sqlDatabase.isConnected())
            Bukkit.getConsoleSender().sendMessage("SQL Database for Cosmic Tournaments Connected");
        if (!this.sqlDatabase.isConnected()) {
            Bukkit.getLogger().severe("Unable to connect to sql database! Please ensure the sql credentials are correct");
            Bukkit.getPluginManager().disablePlugin(this);
        } else {
            this.sqlDatabase.executeUpdate("CREATE TABLE IF NOT EXISTS tournament_userdata(player_uuid varchar(64) PRIMARY KEY, inventory varchar(4000), tournamentwins int, tournamentlosses int, matchwins int, matchlosses int, kills int, deaths int, elo int, points int, helmet varchar(64), chestplate varchar(64), leggings varchar(64), boots varchar(64), sword varchar(64), axe varchar(64));", false);
            this.sqlQueryThread = new SQLQueryCustomThread();
            this.sqlQueryThread.start();
        }
        this.playerDataManager = new PlayerDataManager();
        this.tournamentManager = new TournamentManager();
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
