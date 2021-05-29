package me.astropvp.astrospawners;

import com.earth2me.essentials.Essentials;
import me.astropvp.astrospawners.commands.*;
import me.astropvp.astrospawners.customitems.SpawnerCustomItem;
import me.astropvp.astrospawners.utils.PlayerData;
import me.astropvp.astrospawners.utils.PlayerDataManager;
import me.astropvp.astrospawners.data.SpawnerDataHelper;
import me.astropvp.astrospawners.listeners.*;
import me.astropvp.astrospawners.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class AstroSpawners extends JavaPlugin {
    public static AstroSpawners astroSpawners;
    private PlayerDataManager manager;
    private Essentials essentials;
    private MobStackerUtils mobStackerUtils;
    private static PlayerDataManager manager2;

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

        System.out.println("Started Saving mobcoins data...");
        for (Player p : Bukkit.getOnlinePlayers()) {
            PlayerData data = manager.getPlayerData(p);
            manager.savePlayerData(data);
        }
        Bukkit.getScheduler().runTaskLater(AstroSpawners.getInstance(), (Runnable) new BukkitRunnable() {
            @Override
            public void run() {
                EntityUtils.clearAllEntities();
            }
        }, 100L);
        System.out.println("Done saving mobcoins data!");
        EntityUtils.onEnable();
        SpawnerDataHelper.init();
        new SpawnerCustomItem("spawner", "<amount> <entity>");
        this.manager2 = new PlayerDataManager(this);

        Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, SpawnerDataHelper::onDisable, 1000L, 6000L
        );
    }

    public void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    public Essentials getEssentials() {
        return essentials;
    }

    @Override
    public void onDisable() {
        MobStackerUtils.onDisable();
        SpawnerDataHelper.onDisable();
        EntityUtils.clearAllEntities();

        astroSpawners = null;
    }

    public static AstroSpawners getInstance() {
        return astroSpawners;
    }


    public void registerListeners() {
        registerListener(new SpawnerBreakListener());
        registerListener(new CombineSpawnersListener());
        registerListener(new EntityStackerListener(this));
        registerListener(new MobSpawnerListener());
        registerListener(new MobCoinShopListener());
        registerListener(new PlayerDataOnJoin());
        registerListener(new CreeperEggFix());
    }

    public void registerCommands() {
        getCommand("splitspawners").setExecutor(new CommandSplitSpawners());
        getCommand("givespawner").setExecutor(new CommandGiveSpawner());
        getCommand("clearmobs").setExecutor(new CommandClearHashMap());
        getCommand("checkmobs").setExecutor(new CommandCheckHashMap());


        getCommand("mobcoinshop").setExecutor((CommandExecutor) new CommandMobCoinShop());
        getCommand("mobcoins").setExecutor((CommandExecutor) new CommandMobCoins());
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
