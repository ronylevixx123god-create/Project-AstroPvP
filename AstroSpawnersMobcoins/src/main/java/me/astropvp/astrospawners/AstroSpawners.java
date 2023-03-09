package me.astropvp.astrospawners;

import lombok.Getter;
import me.astropvp.astrospawners.commands.*;
import me.astropvp.astrospawners.customitems.SpawnerCustomItem;
import me.astropvp.astrospawners.data.SpawnerData;
import me.astropvp.astrospawners.data.SpawnerManager;
import me.astropvp.astrospawners.ftop.CommandAdminFactionsTop;
import me.astropvp.astrospawners.ftop.CommandFactionsTop;
import me.astropvp.astrospawners.ftop.FactionsTopAPI;
import me.astropvp.astrospawners.ftop.RecalculationTask;
import me.astropvp.astrospawners.guis.AntiAFKGrindingGUI;
import me.astropvp.astrospawners.utils.PlayerDataManager;
import me.astropvp.astrospawners.listeners.*;
import me.astropvp.astrospawners.utils.*;
import me.astropvp.astrospawners.utils.config.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.WorldCreator;
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

    @Getter
    private ConfigManager configManager;

    @Getter
    private SpawnerManager spawnerManager;


    @Getter
    public static List<Player> coolDownSpawnerPlace = new ArrayList<>();



    public MobStackerUtils getMobStackerUtils() {
        return mobStackerUtils;
    }

    @Override
    public void onEnable() {
        astroSpawners = this;
        System.out.println("=======================");
        System.out.println("AstroSpawner Loaded!");
        System.out.println("=======================");
        manager = new PlayerDataManager(this);
        this.spawnerManager = new SpawnerManager(this);
        spawnerManager.loadAll();
        mobStackerUtils = new MobStackerUtils();
        this.loadConfig();
        registerListeners();
        registerCommands();


        Bukkit.getConsoleSender().sendMessage("Finished loading spawners!");

        Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, ()-> {
            spawnerManager.saveAll();
        }, 60L, 20*60);



        Bukkit.getScheduler().runTaskLater(AstroSpawners.getInstance(), (Runnable) new BukkitRunnable() {
            @Override
            public void run() {
                EntityUtils.clearAllEntities();

            }
        }, 150L);

        EntityUtils.onEnable();
        new SpawnerCustomItem("spawner", "<amount> <entity>");

        runTasks();
        Bukkit.getScheduler().runTaskLaterAsynchronously((Plugin)this, FactionsTopAPI::recalculateFactionsTop, 100L);

        this.afkGrinders = new HashMap<>();
        this.waitingForAfkGrindingVerification = new ArrayList<>();

        this.configManager = new ConfigManager(this);
        this.configManager.setupFiles();

    }

    public void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("Purging Spawner Data");
        spawnerManager.purge();
        Bukkit.getConsoleSender().sendMessage("Started Saving Spawner Data");
        spawnerManager.saveAll();
        MobStackerUtils.onEnable();
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
        registerListener(new FtopCommandListener());
        registerListener(new GrindingGuiClose());
    }

    public void registerCommands() {
        getCommand("splitspawners").setExecutor(new CommandSplitSpawners());
        getCommand("givespawner").setExecutor(new CommandGiveSpawner());
        getCommand("clearmobs").setExecutor(new CommandClearHashMap());
        getCommand("checkmobs").setExecutor(new CommandCheckHashMap());
        getCommand("savespawners").setExecutor(new SaveSpawnersCommand());

        getCommand("stopspawnersplace").setExecutor(new CommandStopSpawnersPlace());
        getCommand("stackspawners").setExecutor(new CommandStackSpawners());
        getCommand("aspawner").setExecutor(new CommandAdminSpawner());





        //getCommand("mobcoinshop").setExecutor((CommandExecutor) new CommandMobCoinShop());
        //getCommand("mobcoins").setExecutor((CommandExecutor) new CommandMobCoins());
        getCommand("factionstop").setExecutor((CommandExecutor)new CommandFactionsTop());
        getCommand("adminfactionstop").setExecutor((CommandExecutor)new CommandAdminFactionsTop());
        getCommand("clearafk").setExecutor((CommandExecutor)new CommandClearAfkGrinders());

    }

    public void addPlayerToAfkGrinding(Player p) {
        /*UUID uuid = p.getUniqueId();

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
        }*/


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

}
