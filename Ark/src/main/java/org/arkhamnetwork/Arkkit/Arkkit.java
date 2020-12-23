package org.arkhamnetwork.Arkkit;

import com.cosmicpvp.cosmicutils.utils.CC;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.apache.commons.io.FileUtils;
import org.arkhamnetwork.Arkkit.commands.*;
import org.arkhamnetwork.Arkkit.patches.AntiCrash;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.arkhamnetwork.Arkkit.utils.WorldGuardUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.FileUtil;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class Arkkit
        extends JavaPlugin {
    public static Economy economy = null;
    private static Arkkit me = null;
    public long enable_time = 0L;
    public boolean shutting_down = false;
    @Getter
    public Arkkit instance;
    private HashMap<String, ArkPlugin> loaded_patches;
    private ConcurrentHashMap<String, Player> asyncPlayerMap;
    private ConcurrentHashMap<String, Location> playerLocations;

    public static Arkkit get() {
        return me;
    }

    public void onEnable() {
        instance = this;
        this.shutting_down = false;
        me = this;
        this.loaded_patches = new HashMap();
        this.asyncPlayerMap = new ConcurrentHashMap();
        this.playerLocations = new ConcurrentHashMap();
        if (!new File("plugins/Arkkit/config.yml").exists()) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "Copying default config.yml ...");
            this.saveDefaultConfig();
            this.getConfig().options().copyDefaults(true);
            this.saveConfig();
        }
        registerCommands();
        setupEconomy();
        WorldGuardUtils.hook();
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + "Loading Patches ...");
        Bukkit.getScheduler().runTaskLater(this, () -> {
            try {
                Arkkit.this.convertOldVersions();
            } catch (Exception err) {
                Bukkit.getLogger().info("Failed to execute convertOldVersions()!");
                err.printStackTrace();
            }
            Arkkit.this.registerPatches();
            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage(ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + "  *** Compilex Patch Complete! *** ");
            Bukkit.broadcastMessage(ChatColor.GRAY.toString() + ChatColor.ITALIC.toString() + "     Thank you for your patience!");
            Bukkit.broadcastMessage("");
        }, 20L);
        this.getServer().getPluginManager().registerEvents(new ArkkitListener(), this);
        for (Player pl : Bukkit.getOnlinePlayers()) {
            this.asyncPlayerMap.put(pl.getName(), pl);
            this.playerLocations.put(pl.getName(), pl.getLocation());
        }
        Bukkit.getScheduler().runTaskTimer(this, () -> {
            for (Player p : Bukkit.getOnlinePlayers()) {
                Arkkit.this.playerLocations.put(p.getName(), p.getLocation());
            }
        }, 0L, 40L);
        this.enable_time = System.currentTimeMillis();
    }

    public void onDisable() {
        this.shutting_down = true;
        try {
            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage(ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + "   *** A Compilex Patch is loading. Please wait... *** ");
            Bukkit.broadcastMessage(ChatColor.GRAY.toString() + ChatColor.ITALIC.toString() + "  This process occurs when Compilex applies a dynamic update.");
            Bukkit.broadcastMessage("");
        } catch (Exception err) {
            err.printStackTrace();
        }
        this.saveConfig();
        this.unregisterPatches();
    }

    private void registerCommands() {
        Bukkit.getConsoleSender().sendMessage(CC.Gold + "[Arkkit] Beginning loading commands...");
        getCommand("arkhamreload").setExecutor(new CommandArkReload(this));
        Bukkit.getConsoleSender().sendMessage(CC.Green + "[Arkkit] ArkReload registered");
        getCommand("arkregister").setExecutor(new CommandArkRegister(this));
        Bukkit.getConsoleSender().sendMessage(CC.Green + "[Arkkit] ArkRegister registered");
        getCommand("arkunregister").setExecutor(new CommandArkUnregister(this));
        Bukkit.getConsoleSender().sendMessage(CC.Green + "[Arkkit] ArkUnregister registered");
        getCommand("arklist").setExecutor(new CommandArkList());
        Bukkit.getConsoleSender().sendMessage(CC.Green + "[Arkkit] ArkList registered");
        getCommand("arkconfig").setExecutor(new CommandArkConfig(this));
        Bukkit.getConsoleSender().sendMessage(CC.Green + "[Arkkit] ArkConfig registered");
        getCommand("arkreboot").setExecutor(new CommandArkReboot());
        Bukkit.getConsoleSender().sendMessage(CC.Green + "[Arkkit] ArkReboot registered");
        getCommand("arkdebug").setExecutor(new CommandArkDebug());
        Bukkit.getConsoleSender().sendMessage(CC.Green + "[Arkkit] ArkDebug registered");
        Bukkit.getConsoleSender().sendMessage(CC.Gold + "[Arkkit] Finished loading commands...");
    }

    private boolean setupEconomy() {
        RegisteredServiceProvider economyProvider = this.getServer().getServicesManager().getRegistration(Economy.class);
        if (economyProvider != null) {
            economy = (Economy) economyProvider.getProvider();
        }
        return economy != null;
    }

    public boolean registerPatch(ArkPlugin ap) {
        ArkPlugins aps = ArkPlugins.reflectArkPlugin(ap);
        if (aps == null || this.loaded_patches.containsKey(aps.getConfigName())) {
            return false;
        }
        ap.inject();
        this.loaded_patches.put(aps.getConfigName(), ap);
        return true;
    }

    public boolean unregisterPatch(String config_path) {
        ArkPlugin ap;
        ArkPlugin arkPlugin = ap = this.loaded_patches.getOrDefault(config_path, null);
        if (ap == null) {
            return false;
        }
        ap.kill();
        this.loaded_patches.remove(config_path);
        return true;
    }

    public void registerPatches() {
        for (ArkPlugins aps : ArkPlugins.values()) {
            if (!this.getConfig().contains("patches." + aps.getConfigName())) {
                this.getConfig().set("patches." + aps.getConfigName(), false);
                continue;
            }
            if (!this.getConfig().getBoolean("patches." + aps.getConfigName())) continue;
            this.registerPatch(aps.getArkPlugin());
        }
        this.saveConfig();
    }

    public void unregisterPatches() {
        for (ArkPlugin ap : this.loaded_patches.values()) {
            ap.kill();
        }
        this.loaded_patches.clear();
    }

    public boolean isRebooting() {
        return Arkkit.get().shutting_down || AntiCrash.crash || CommandArkReboot.restart_task_exists || Bukkit.getPluginManager().getPlugin("NetworkManager") != null;
    }

    private void convertOldVersions() {
        new File("no_clean_tile_entities").delete();
        new File("no_clean_entities").delete();
        if (new File("plugins/NoDupeExploit.jar").exists()) {
            try {
                if (Bukkit.getPluginManager().isPluginEnabled("NoDupeExploit")) {
                    Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("NoDupeExploit"));
                }
                new File("plugins/NoDupeExploit.jar").delete();
                this.getConfig().set("patches." + ArkPlugins.NO_DUPE_EXPLOIT.getConfigName(), true);
                this.registerPatch(ArkPlugins.NO_DUPE_EXPLOIT.getArkPlugin());
            } catch (Exception err) {
                err.printStackTrace();
            }
        }
        if (new File("plugins/Essentials.jar").exists()) {
            try {
                this.getConfig().set("patches." + ArkPlugins.CLEAR_INVENTORY_CONFIRM.getConfigName(), true);
                this.registerPatch(ArkPlugins.CLEAR_INVENTORY_CONFIRM.getArkPlugin());
            } catch (Exception err) {
                err.printStackTrace();
            }
        }
        if (new File("plugins/ArkhamUtils.jar").exists()) {
            try {
                if (Bukkit.getPluginManager().isPluginEnabled("ArkhamUtils")) {
                    Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("ArkhamUtils"));
                }
                new File("plugins/ArkhamUtils.jar").delete();
                FileUtils.deleteDirectory(new File("plugins/ArkhamUtils"));
                this.getConfig().set("patches." + ArkPlugins.NO_VEHICLE_TELEPORT.getConfigName(), true);
                this.registerPatch(ArkPlugins.NO_VEHICLE_TELEPORT.getArkPlugin());
                Bukkit.getScheduler().runTaskLater(Arkkit.get(), new Runnable() {

                    @Override
                    public void run() {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "arkreload auto_entity_clear");
                    }
                }, 20L);
            } catch (Exception err) {
                err.printStackTrace();
            }
        }
        if (new File("plugins/EssentialsKitViewer.jar").exists()) {
            try {
                if (Bukkit.getPluginManager().isPluginEnabled("EssentialsKitViewer")) {
                    Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("EssentialsKitViewer"));
                }
                new File("plugins/EssentialsKitViewer.jar").delete();
                FileUtils.copyFile(new File("plugins/EssentialsKitViewer/config.dat"), new File("plugins/Arkkit/kitViewer.dat"));
                FileUtils.deleteDirectory(new File("plugins/EssentialsKitViewer"));
                this.getConfig().set("patches." + ArkPlugins.ESSENTIALS_KIT_VIEWER.getConfigName(), true);
                this.registerPatch(ArkPlugins.ESSENTIALS_KIT_VIEWER.getArkPlugin());
            } catch (Exception err) {
                err.printStackTrace();
            }
            Bukkit.getScheduler().runTaskLater(this, () -> {
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "SERVER IS REBOOTING TO CLEAR CACHED COMMANDS!");
                Bukkit.shutdown();
            }, 21L);
        }
        if (new File("plugins/redeemMCMMO.jar").exists()) {
            try {
                if (Bukkit.getPluginManager().isPluginEnabled("RedeemMCMMO")) {
                    Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("RedeemMCMMO"));
                }
                new File("plugins/redeemMCMMO.jar").delete();
                FileUtils.copyFile(new File("plugins/RedeemMCMMO/config.yml"), new File("plugins/Arkkit/redeemMCMMO.yml"));
                FileUtils.deleteDirectory(new File("plugins/RedeemMCMMO"));
                this.getConfig().set("patches." + ArkPlugins.REDEEM_MCMMO.getConfigName(), true);
                this.registerPatch(ArkPlugins.REDEEM_MCMMO.getArkPlugin());
            } catch (Exception err) {
                err.printStackTrace();
            }
        }
        if (new File("plugins/TempEnchantments.jar").exists()) {
            try {
                if (Bukkit.getPluginManager().isPluginEnabled("TempEnchantments")) {
                    Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("TempEnchantments"));
                }
                new File("plugins/TempEnchantments.jar").delete();
                this.getConfig().set("patches." + ArkPlugins.XP_SANITY.getConfigName(), true);
                this.registerPatch(ArkPlugins.XP_SANITY.getArkPlugin());
            } catch (Exception err) {
                err.printStackTrace();
            }
        }
        if (new File("plugins/DynamicMaxPlayers.jar").exists()) {
            try {
                if (Bukkit.getPluginManager().isPluginEnabled("DynamicMaxPlayers")) {
                    Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("DynamicMaxPlayers"));
                }
                new File("plugins/DynamicMaxPlayers.jar").delete();
                FileUtils.deleteDirectory(new File("plugins/DynamicMaxPlayers"));
                this.getConfig().set("patches." + ArkPlugins.DYNAMIC_MAX_PLAYERS.getConfigName(), true);
                this.registerPatch(ArkPlugins.DYNAMIC_MAX_PLAYERS.getArkPlugin());
            } catch (Exception err) {
                err.printStackTrace();
            }
        }
        if (new File("plugins/HotbarArmorSwap.jar").exists()) {
            try {
                if (Bukkit.getPluginManager().isPluginEnabled("HotbarArmorSwap")) {
                    Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("HotbarArmorSwap"));
                }
                new File("plugins/HotbarArmorSwap.jar").delete();
                FileUtils.deleteDirectory(new File("plugins/HotbarArmorSwap"));
                this.getConfig().set("patches." + ArkPlugins.HOTBAR_ARMOR_SWAP.getConfigName(), true);
                this.registerPatch(ArkPlugins.HOTBAR_ARMOR_SWAP.getArkPlugin());
            } catch (Exception err) {
                err.printStackTrace();
            }
        }
        if (new File("plugins/AntiLootSteal.jar").exists()) {
            try {
                if (Bukkit.getPluginManager().isPluginEnabled("AntiLootSteal")) {
                    Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("AntiLootSteal"));
                }
                new File("plugins/AntiLootSteal.jar").delete();
                FileUtils.deleteDirectory(new File("plugins/AntiLootSteal"));
                this.getConfig().set("patches." + ArkPlugins.ANTI_LOOT_STEAL.getConfigName(), true);
                this.registerPatch(ArkPlugins.ANTI_LOOT_STEAL.getArkPlugin());
            } catch (Exception err) {
                err.printStackTrace();
            }
        }
        if (new File("plugins/PotionFix.jar").exists()) {
            try {
                if (Bukkit.getPluginManager().isPluginEnabled("PotionFix")) {
                    Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("PotionFix"));
                }
                new File("plugins/PotionFix.jar").delete();
                this.getConfig().set("patches." + ArkPlugins.POTION_FIX.getConfigName(), true);
                this.registerPatch(ArkPlugins.POTION_FIX.getArkPlugin());
            } catch (Exception err) {
                err.printStackTrace();
            }
        }
        if (new File("plugins/NetherGlitchFix.jar").exists()) {
            try {
                if (Bukkit.getPluginManager().isPluginEnabled("NetherGlitchFixPlugin")) {
                    Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("NetherGlitchFixPlugin"));
                }
                new File("plugins/NetherGlitchFix.jar").delete();
                this.getConfig().set("patches." + ArkPlugins.NETHER_GLITCH_FIX.getConfigName(), true);
                this.registerPatch(ArkPlugins.NETHER_GLITCH_FIX.getArkPlugin());
            } catch (Exception err) {
                err.printStackTrace();
            }
        }
        if (new File("plugins/AsyncMonsters.jar").exists()) {
            try {
                if (Bukkit.getPluginManager().isPluginEnabled("AsyncMonsters")) {
                    Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("AsyncMonsters"));
                }
                new File("plugins/AsyncMonsters.jar").delete();
                this.getConfig().set("patches." + ArkPlugins.ASYNC_MONSTERS.getConfigName(), true);
                this.registerPatch(ArkPlugins.ASYNC_MONSTERS.getArkPlugin());
                Bukkit.getScheduler().runTaskLater(this, new Runnable() {

                    @Override
                    public void run() {
                        Bukkit.getConsoleSender().sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "SERVER IS REBOOTING TO CLEAR ASYNC MONSTERS CACHED LISTENERS!");
                        Bukkit.shutdown();
                    }
                }, 21L);
            } catch (Exception err) {
                err.printStackTrace();
            }
        }
        if (new File("plugins/AntiGodAppleCraft.jar").exists()) {
            try {
                if (Bukkit.getPluginManager().isPluginEnabled("AntiGodAppleCraftPlugin")) {
                    Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("AntiGodAppleCraftPlugin"));
                }
                new File("plugins/AntiGodAppleCraft.jar").delete();
                this.getConfig().set("patches." + ArkPlugins.ANTI_GOD_APPLE_CRAFT.getConfigName(), true);
                this.registerPatch(ArkPlugins.ANTI_GOD_APPLE_CRAFT.getArkPlugin());
            } catch (Exception err) {
                err.printStackTrace();
            }
        }
        if (new File("plugins/ArkhamShops.jar").exists()) {
            try {
                if (Bukkit.getPluginManager().isPluginEnabled("ArkhamShops")) {
                    Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("ArkhamShops"));
                }
                new File("plugins/ArkhamShops.jar").delete();
                this.getConfig().set("patches." + ArkPlugins.SHOP_COMMAND.getConfigName(), true);
                this.registerPatch(ArkPlugins.SHOP_COMMAND.getArkPlugin());
            } catch (Exception err) {
                err.printStackTrace();
            }
        }
        if (new File("plugins/AntiClip.jar").exists()) {
            try {
                if (Bukkit.getPluginManager().isPluginEnabled("AntiClip")) {
                    Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("AntiClip"));
                }
                new File("plugins/AntiClip.jar").delete();
                this.getConfig().set("patches." + ArkPlugins.ANTI_HACK.getConfigName(), true);
                this.registerPatch(ArkPlugins.ANTI_HACK.getArkPlugin());
            } catch (Exception err) {
                err.printStackTrace();
            }
        }
        if (new File("plugins/ArkhamCooldowns.jar").exists()) {
            try {
                if (Bukkit.getPluginManager().isPluginEnabled("ArkhamCooldowns")) {
                    Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("ArkhamCooldowns"));
                }
                new File("plugins/ArkhamCooldowns.jar").delete();
                FileUtil.copy(new File("plugins/ArkhamCooldowns/config.dat"), new File("plugins/Arkkit/command-cooldowns.yml"));
                this.getConfig().set("patches." + ArkPlugins.COMMAND_COOLDOWNS.getConfigName(), true);
                this.registerPatch(ArkPlugins.COMMAND_COOLDOWNS.getArkPlugin());
            } catch (Exception err) {
                err.printStackTrace();
            }
        }
        if (new File("plugins/ArkhamTrade.jar").exists()) {
            try {
                if (Bukkit.getPluginManager().isPluginEnabled("ArkhamTrade")) {
                    Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("ArkhamTrade"));
                }
                new File("plugins/ArkhamTrade.jar").delete();
                this.getConfig().set("patches." + ArkPlugins.TRADING.getConfigName(), true);
                this.registerPatch(ArkPlugins.TRADING.getArkPlugin());
            } catch (Exception err) {
                err.printStackTrace();
            }
        }
        if (new File("plugins/VotingSystems.jar").exists()) {
            try {
                if (Bukkit.getPluginManager().isPluginEnabled("VotingSystems")) {
                    Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("VotingSystems"));
                }
                new File("plugins/VotingSystems.jar").delete();
                this.getConfig().set("patches." + ArkPlugins.VOTING.getConfigName(), true);
                this.registerPatch(ArkPlugins.VOTING.getArkPlugin());
            } catch (Exception err) {
                err.printStackTrace();
            }
        }
        if (new File("plugins/ArkhamChatBubbles.jar").exists()) {
            try {
                if (Bukkit.getPluginManager().isPluginEnabled("ArkhamChatBubbles")) {
                    Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("ArkhamChatBubbles"));
                }
                new File("plugins/ArkhamChatBubbles.jar").delete();
                this.getConfig().set("patches." + ArkPlugins.CHAT_BUBBLES.getConfigName(), true);
                this.registerPatch(ArkPlugins.CHAT_BUBBLES.getArkPlugin());
            } catch (Exception err) {
                err.printStackTrace();
            }
        }
        if (new File("plugins/ArkhamTitleAPI.jar").exists()) {
            try {
                this.getConfig().set("patches." + ArkPlugins.CREATE_TITLE.getConfigName(), true);
                this.registerPatch(ArkPlugins.CREATE_TITLE.getArkPlugin());
            } catch (Exception err) {
                err.printStackTrace();
            }
        }
        if (new File("plugins/SimpleChat.jar").exists()) {
            try {
                if (Bukkit.getPluginManager().isPluginEnabled("SimpleChat")) {
                    Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("SimpleChat"));
                }
                this.getConfig().set("patches." + ArkPlugins.CHAT_FILTER.getConfigName(), true);
                this.registerPatch(ArkPlugins.CHAT_FILTER.getArkPlugin());
            } catch (Exception err) {
                err.printStackTrace();
            }
            new File("plugins/SimpleChat.jar").delete();
        }
        if (new File("plugins/NoNewChunks.jar").exists()) {
            try {
                if (Bukkit.getPluginManager().isPluginEnabled("NoNewChunks")) {
                    this.getConfig().set("settings.no_new_chunks.worlds", Bukkit.getPluginManager().getPlugin("NoNewChunk").getConfig().get("Worlds"));
                    Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("NoNewChunks"));
                }
                this.getConfig().set("patches." + ArkPlugins.NO_NEW_CHUNKS.getConfigName(), true);
                this.registerPatch(ArkPlugins.NO_NEW_CHUNKS.getArkPlugin());
            } catch (Exception err) {
                err.printStackTrace();
            }
            new File("plugins/NoNewChunks.jar").delete();
        }
        if (new File("plugins/NoBukkitCommands.jar").exists() || new File("plugins/AntiPluginCommand.jar").exists()) {
            try {
                if (Bukkit.getPluginManager().isPluginEnabled("NoBukkitCommands")) {
                    Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("NoBukkitCommands"));
                }
                if (Bukkit.getPluginManager().isPluginEnabled("AntiPluginCommand")) {
                    Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("AntiPluginCommand"));
                }
                this.getConfig().set("patches." + ArkPlugins.PLUGIN_HIDER.getConfigName(), true);
                this.registerPatch(ArkPlugins.PLUGIN_HIDER.getArkPlugin());
            } catch (Exception err) {
                err.printStackTrace();
            }
            new File("plugins/NoBukkitCommands.jar").delete();
            new File("plugins/AntiPluginCommand.jar").delete();
        }
        if (new File("plugins/ArkhamChatLimiter.jar").exists()) {
            if (Bukkit.getPluginManager().isPluginEnabled("ArkhamChatLimiter")) {
                Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("ArkhamChatLimiter"));
            }
            new File("plugins/ArkhamChatLimiter.jar").delete();
            this.getConfig().set("patches." + ArkPlugins.CHAT_LIMITER.getConfigName(), true);
            this.registerPatch(ArkPlugins.CHAT_LIMITER.getArkPlugin());
        }
        if (new File("plugins/GiveAll.jar").exists()) {
            if (Bukkit.getPluginManager().isPluginEnabled("GiveAll")) {
                Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("GiveAll"));
            }
            new File("plugins/GiveAll.jar").delete();
            this.getConfig().set("patches." + ArkPlugins.GIVE_ALL.getConfigName(), true);
            this.registerPatch(ArkPlugins.GIVE_ALL.getArkPlugin());
        }
        if (new File("plugins/EssentialsWarpUI.jar").exists()) {
            if (Bukkit.getPluginManager().isPluginEnabled("EssentialsWarpUI")) {
                Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("EssentialsWarpUI"));
            }
            new File("plugins/EssentialsWarpUI.jar").delete();
            this.getConfig().set("patches." + ArkPlugins.ESSENTIALS_WARP_UI.getConfigName(), true);
            this.registerPatch(ArkPlugins.ESSENTIALS_WARP_UI.getArkPlugin());
        }
        if (new File("plugins/ArkhamWarpAliases.jar").exists()) {
            if (Bukkit.getPluginManager().isPluginEnabled("ArkhamWarpAliases")) {
                Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("ArkhamWarpAliases"));
            }
            new File("plugins/ArkhamWarpAliases.jar").delete();
            this.getConfig().set("patches." + ArkPlugins.ESSENTIALS_WARP_ALIASES.getConfigName(), true);
            this.registerPatch(ArkPlugins.ESSENTIALS_WARP_ALIASES.getArkPlugin());
        }
        if (new File("plugins/AntiCrash.jar").exists()) {
            if (Bukkit.getPluginManager().isPluginEnabled("AntiCrash")) {
                Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("AntiCrash"));
            }
            new File("plugins/AntiCrash.jar").delete();
            this.getConfig().set("patches." + ArkPlugins.ANTI_CRASH.getConfigName(), true);
            this.registerPatch(ArkPlugins.ANTI_CRASH.getArkPlugin());
        }
        if (new File("plugins/FactionAntiDupe-EXTRONUS.jar").exists()) {
            new File("plugins/FactionAntiDupe-EXTRONUS.jar").delete();
        }
        if (new File("plugins/ChunkGC.jar").exists() || new File("plugins/ChunkGCTask.jar").exists() || new File("plugins/ExtronusChunkGC.jar").exists()) {
            if (new File("gc_force_save").exists()) {
                new File("gc_force_save").delete();
                this.getConfig().set("patches." + ArkPlugins.BULK_SAVING.getConfigName(), true);
                this.registerPatch(ArkPlugins.BULK_SAVING.getArkPlugin());
            }
            if (Bukkit.getPluginManager().isPluginEnabled("ChunkGC")) {
                Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("ChunkGC"));
            }
            new File("plugins/ChunkGC.jar").delete();
            new File("plugins/ChunkGCTask.jar").delete();
            new File("plugins/ExtronusChunkGC.jar").delete();
            //this.getConfig().set("patches." + ArkPlugins.CHUNK_GC.getConfigName(), true);
            //this.registerPatch(ArkPlugins.CHUNK_GC.getArkPlugin());
            if (!new File("no_adjust").exists()) {
                this.getConfig().set("patches." + ArkPlugins.WORLD_TICK_OPTIMIZER.getConfigName(), true);
                this.registerPatch(ArkPlugins.WORLD_TICK_OPTIMIZER.getArkPlugin());
                this.getConfig().set("patches." + ArkPlugins.NO_NATURAL_MOBS.getConfigName(), true);
                this.registerPatch(ArkPlugins.NO_NATURAL_MOBS.getArkPlugin());
            }
        }
        this.saveConfig();
    }

    public ConcurrentHashMap<String, Player> getAsyncPlayerMap() {
        return this.asyncPlayerMap;
    }

    public ConcurrentHashMap<String, Location> getPlayerLocations() {
        return this.playerLocations;
    }
}

