package me.astropvp.astrospawners;

import lombok.Getter;
import me.astropvp.astrospawners.commands.*;
import me.astropvp.astrospawners.configs.LoadSpawner;
import me.astropvp.astrospawners.customitems.SpawnerCustomItem;
import me.astropvp.astrospawners.data.SpawnerData;
import me.astropvp.astrospawners.ftop.CommandAdminFactionsTop;
import me.astropvp.astrospawners.ftop.CommandFactionsTop;
import me.astropvp.astrospawners.ftop.FactionsTopAPI;
import me.astropvp.astrospawners.ftop.RecalculationTask;
import me.astropvp.astrospawners.guis.AntiAFKGrindingGUI;
import me.astropvp.astrospawners.sql.Database;
import me.astropvp.astrospawners.sql.SQLQueryCustomThread;
import me.astropvp.astrospawners.utils.PlayerData;
import me.astropvp.astrospawners.utils.PlayerDataManager;
import me.astropvp.astrospawners.data.SpawnerManager;
import me.astropvp.astrospawners.listeners.*;
import me.astropvp.astrospawners.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.*;

public class AstroSpawners extends JavaPlugin {
    public static AstroSpawners astroSpawners;
    private PlayerDataManager manager;
    private MobStackerUtils mobStackerUtils;
    private static PlayerDataManager manager2;
    @Getter
    private Map<UUID, Integer> afkGrinders;
    @Getter
    private List<UUID> waitingForAfkGrindingVerification;

    private Database sqlDatabase;
    private SpawnerManager spawnerManager;
    private SQLQueryCustomThread sqlQueryCustomThread = new SQLQueryCustomThread();

    @Getter
    private Map<Location, SpawnerData> spawnersMap;

    @Getter
    public static List<Player> coolDownSpawnerPlace = new ArrayList<>();



    public MobStackerUtils getMobStackerUtils() {
        return mobStackerUtils;
    }

    @Override
    public void onEnable() {
        System.out.println("=======================");
        System.out.println("AstroSpawner Loaded!");
        System.out.println("=======================");
        manager = new PlayerDataManager(this);

        mobStackerUtils = new MobStackerUtils();

        astroSpawners = this;
        this.loadConfig();
        registerListeners();
        registerCommands();

        File file = new File(this.getDataFolder() + File.separator + "data");
        if(!file.exists()) {
            file.mkdir();
        }




        Bukkit.getScheduler().runTaskLater(AstroSpawners.getInstance(), (Runnable) new BukkitRunnable() {
            @Override
            public void run() {
                EntityUtils.clearAllEntities();
                Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "killall named world");
                Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "killall all world");
                Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "killall entities world");


            }
        }, 150L);

        EntityUtils.onEnable();
        new SpawnerCustomItem("spawner", "<amount> <entity>");
        //manager2 = new PlayerDataManager(this);


/*
        this.sqlDatabase = new Database(this);
        if(this.sqlDatabase.isConnected())
            Bukkit.getConsoleSender().sendMessage("SQL Database for AstroSpawners Connected");
        if (!this.sqlDatabase.isConnected()) {
            Bukkit.getLogger().severe("Unable to connect to sql database! Please ensure the sql credentials are correct");
            Bukkit.getPluginManager().disablePlugin(this);
        } else {
            this.sqlDatabase.executeUpdate("CREATE TABLE IF NOT EXISTS spawner_data(location varchar(256), type varchar(64), amount int);", false);
            this.sqlQueryCustomThread = new SQLQueryCustomThread();
            this.sqlQueryCustomThread.start();
        }
*/

        this.spawnerManager = new SpawnerManager();


        Bukkit.getConsoleSender().sendMessage("Started Loading Spawner Data");


        this.spawnersMap = new HashMap<>();


        //getSpawnerManager().load();
        LoadSpawner.load();




        Bukkit.getConsoleSender().sendMessage("Finished loading spawners!");

        runTasks();
        Bukkit.getScheduler().runTaskLaterAsynchronously((Plugin)this, FactionsTopAPI::recalculateFactionsTop, 100L);

        long minutes = 10;

        this.afkGrinders = new HashMap<>();
        this.waitingForAfkGrindingVerification = new ArrayList<>();


    }

    public void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("Started Saving Spawner Data");

        //getSpawnerManager().save();



        /*for (Player p : Bukkit.getOnlinePlayers()) {
            PlayerData data = AstroSpawners.getInstance().getManager().getPlayerData(p);
            AstroSpawners.getInstance().getManager().savePlayerData(data);
        }*/
        MobStackerUtils.onDisable();
        EntityUtils.clearAllEntities();

        astroSpawners = null;
    }


    public static AstroSpawners getInstance() {
        return astroSpawners;
    }


    public void registerListeners() {
        registerListener(new SpawnerBreakListener());
        registerListener(new CombineSpawnersInventoryListener());
        registerListener(new EntityStackerListener(this));
        registerListener(new MobSpawnerListener());
        //registerListener(new MobCoinShopListener());
        registerListener(new FtopCommandListener());
        registerListener(new PlayerDataOnJoin());
        //registerListener(new ClickMobcoinsNoteListener());
        registerListener(new GrindingGuiClose());
    }

    public void registerCommands() {
        getCommand("splitspawners").setExecutor(new CommandSplitSpawners());
        getCommand("givespawner").setExecutor(new CommandGiveSpawner());
        getCommand("clearmobs").setExecutor(new CommandClearHashMap());
        getCommand("checkmobs").setExecutor(new CommandCheckHashMap());
        //getCommand("savemobcoins").setExecutor(new SaveMobCoinsCommand());
        //getCommand("addmobcoins").setExecutor(new CommandAddMobcoins());

        getCommand("stopspawnersplace").setExecutor(new CommandStopSpawnersPlace());
        getCommand("stackspawners").setExecutor(new CommandStackSpawners());




        //getCommand("mobcoinshop").setExecutor((CommandExecutor) new CommandMobCoinShop());
        //getCommand("mobcoins").setExecutor((CommandExecutor) new CommandMobCoins());
        getCommand("factionstop").setExecutor((CommandExecutor)new CommandFactionsTop());
        getCommand("adminfactionstop").setExecutor((CommandExecutor)new CommandAdminFactionsTop());
        getCommand("clearafk").setExecutor((CommandExecutor)new CommandClearAfkGrinders());

    }

    public void addPlayerToAfkGrinding(Player p) {
        UUID uuid = p.getUniqueId();

        if (afkGrinders.containsKey(uuid)) {
            int amount = afkGrinders.get(uuid);
            afkGrinders.put(uuid, amount+1);
        } else {
            afkGrinders.put(uuid, 1);
        }

        int amount = afkGrinders.get(uuid);
        if (amount >= 5000) {
            AntiAFKGrindingGUI antiAFKGrindingGUI = new AntiAFKGrindingGUI(p);
            antiAFKGrindingGUI.getAntiAfkGrindingGui().open(p);
        }


    }

    public void runTasks() {
        (new RecalculationTask()).runTaskTimerAsynchronously(this, 18000L, 18000L);
    }


    public void registerListener(Listener listener) {
        Bukkit.getServer().getPluginManager().registerEvents(listener, this);
    }

    public PlayerDataManager getManager() {
        return manager;
    }

    public static PlayerDataManager getPlayerDataManager() {
        return manager2;
    }

    public Database getSqlDatabase() {
        return this.sqlDatabase;
    }

    public SpawnerManager getSpawnerManager()  { return this.spawnerManager; }
}
