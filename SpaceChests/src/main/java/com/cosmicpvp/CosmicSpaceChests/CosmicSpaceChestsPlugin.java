/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.sk89q.worldguard.bukkit.WorldGuardPlugin
 *  lilypad.client.connect.api.Connect
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.block.Chest
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.event.Listener
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.java.JavaPlugin
 */
package com.cosmicpvp.CosmicSpaceChests;

import com.cosmicpvp.CosmicSpaceChests.cmds.CommandFDP;
import com.cosmicpvp.CosmicSpaceChests.cmds.CommandFlare;
import com.cosmicpvp.CosmicSpaceChests.cmds.CommandGiveDp;
import com.cosmicpvp.CosmicSpaceChests.libs.LootGenerator;
import com.cosmicpvp.CosmicSpaceChests.libs.RankItems;
import com.cosmicpvp.CosmicSpaceChests.listeners.ChestLootSelectorListener;
import com.cosmicpvp.CosmicSpaceChests.listeners.EnvoySummonItemListener;
import com.cosmicpvp.CosmicSpaceChests.listeners.PlayerInteractChestListener;
import com.cosmicpvp.CosmicSpaceChests.listeners.SpaceFlareListener;
import com.cosmicpvp.CosmicSpaceChests.listeners.items.EvolutionKitItemListener;
import com.cosmicpvp.CosmicSpaceChests.listeners.items.KitItemListener;
import com.cosmicpvp.CosmicSpaceChests.listeners.items.RankItemListener;
import com.cosmicpvp.CosmicSpaceChests.listeners.items.TitleItemListener;
import com.cosmicpvp.CosmicSpaceChests.tasks.CosmicEnvoyChestPrunerTask;
import com.cosmicpvp.CosmicSpaceChests.tasks.CosmicEnvoyPingerTask;
import com.cosmicpvp.CosmicSpaceChests.tasks.CosmicEnvoyTask;
import com.cosmicpvp.CosmicSpaceChests.tasks.HologramUpdateTask;
import com.cosmicpvp.CosmicSpaceChests.tasks.holiday.Christmas2018Task;
import com.cosmicpvp.CosmicSpaceChests.tasks.holiday.Halloween2018Task;
import com.cosmicpvp.CosmicSpaceChests.utils.ChestMap;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import java.io.File;
import lilypad.client.connect.api.Connect;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class CosmicSpaceChestsPlugin
extends JavaPlugin {
    public static Logger log = new Logger();
    public static ChestMap chest_locations = new ChestMap();
    public static WorldGuardPlugin wg = null;
    public static Connect lilyConnect = null;
    public static String serverName = "";
    public static CosmicSpaceChestsPlugin plugin = null;
    public static boolean worldEventsEnabled = false;
    private static HologramUpdateTask hologramTask;
    private Halloween2018Task halloweenTask;
    private Christmas2018Task christmasTask;

    public void onEnable() {
        plugin = this;
        this.saveDefaultConfig();
        wg = this.getWorldGuard();
        if (Bukkit.getPluginManager().isPluginEnabled("LilyPad-Connect")) {
            lilyConnect = (Connect)Bukkit.getServer().getServicesManager().getRegistration(Connect.class).getProvider();
            serverName = lilyConnect.getSettings().getUsername();
            if (serverName.equals("forgottenplanet")) {
                serverName = "ancientplanet";
            }
        } else {
            serverName = "";
        }
        RankItems.findDonorRanks();
        Bukkit.getScheduler().runTaskLater((Plugin)this, new Runnable(){

            @Override
            public void run() {
                LootGenerator.generateLootTables();
                CosmicSpaceChestsPlugin.this.registerListeners();
                CosmicSpaceChestsPlugin.this.registerTasks();
                CosmicSpaceChestsPlugin.this.registerCommands();
            }
        }, 40L);
        this.registerHolidayListeners();
        this.registerHolidayTasks();
        worldEventsEnabled = new File("plugins/CosmicWorldEvents.jar").exists();
    }

    public void onDisable() {
        this.removeAllDropPackages();
    }

    private void registerListeners() {
        this.getServer().getPluginManager().registerEvents((Listener)new PlayerInteractChestListener(this), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new ChestLootSelectorListener(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new EnvoySummonItemListener(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new TitleItemListener(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new RankItemListener(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new KitItemListener(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new EvolutionKitItemListener(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new SpaceFlareListener(), (Plugin)this);
    }

    private void registerCommands() {
        this.getCommand("fdp").setExecutor((CommandExecutor)new CommandFDP());
        this.getCommand("givedp").setExecutor((CommandExecutor)new CommandGiveDp());
        this.getCommand("envoy").setExecutor((CommandExecutor)new CommandEnvoy());
        this.getCommand("flare").setExecutor((CommandExecutor)new CommandFlare());
    }

    private void registerTasks() {
        if (this.getConfig().getBoolean("cosmic_envoy.enabled")) {
            this.getServer().getScheduler().runTaskTimer((Plugin)this, (Runnable)new CosmicEnvoyTask(this), (long)this.getConfig().getInt("cosmic_envoy.min_delay") * 1200L, (long)this.getConfig().getInt("cosmic_envoy.min_delay") * 1200L);
            if (this.getServer().getPluginManager().isPluginEnabled("Arkkit")) {
                this.getServer().getScheduler().runTaskTimer((Plugin)this, (Runnable)new CosmicEnvoyPingerTask(this), 300L, 300L);
            } else {
                log.error("Failed to launch DropPackagePingerTask() due to no ChunkGC plugin detected.", ((Object)((Object)this)).getClass());
            }
            this.getServer().getScheduler().runTaskTimerAsynchronously((Plugin)this, (Runnable)new CosmicEnvoyChestPrunerTask(this), 300L, 300L);
            hologramTask = new HologramUpdateTask();
            hologramTask.runTaskTimer((Plugin)this, 200L, 200L);
        } else {
            log.debug("Cosmic Envoys are DISABLED!", ((Object)((Object)this)).getClass());
        }
    }

    public void removeAllDropPackages() {
        for (Location loc : chest_locations.keySet()) {
            if (loc.getBlock().getType() == Material.CHEST) {
                Chest c = (Chest)loc.getBlock().getState();
                c.getBlockInventory().clear();
                c.update();
                loc.getBlock().setType(Material.AIR);
                continue;
            }
            if (loc.getBlock().getType() != Material.PUMPKIN && loc.getBlock().getType() != Material.JACK_O_LANTERN) continue;
            loc.getBlock().setType(Material.AIR);
        }
        chest_locations.clear();
    }

    private WorldGuardPlugin getWorldGuard() {
        Plugin plugin = this.getServer().getPluginManager().getPlugin("WorldGuard");
        if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
            return null;
        }
        return (WorldGuardPlugin)plugin;
    }

    private void registerHolidayListeners() {
    }

    private void registerHolidayTasks() {
        if (!this.getServer().getPluginManager().isPluginEnabled("Arkkit")) {
            log.error("Failed to launch DropPackagePingerTask() due to no ChunkGC plugin detected.", ((Object)((Object)this)).getClass());
        }
    }

    public static HologramUpdateTask getHologramTask() {
        return hologramTask;
    }

    public Halloween2018Task getHalloweenTask() {
        return this.halloweenTask;
    }

    public Christmas2018Task getChristmasTask() {
        return this.christmasTask;
    }
}

