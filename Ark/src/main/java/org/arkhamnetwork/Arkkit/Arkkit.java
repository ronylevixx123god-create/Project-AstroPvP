package org.arkhamnetwork.Arkkit;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import net.milkbowl.vault.economy.Economy;
import org.apache.commons.io.FileUtils;
import org.arkhamnetwork.Arkkit.commands.CommandArkConfig;
import org.arkhamnetwork.Arkkit.commands.CommandArkList;
import org.arkhamnetwork.Arkkit.commands.CommandArkReboot;
import org.arkhamnetwork.Arkkit.commands.CommandArkRegister;
import org.arkhamnetwork.Arkkit.commands.CommandArkReload;
import org.arkhamnetwork.Arkkit.commands.CommandArkUnregister;
import org.arkhamnetwork.Arkkit.commands.CommandArkUpdate;
import org.arkhamnetwork.Arkkit.patches.AntiCrash;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.arkhamnetwork.Arkkit.utils.WorldGuardUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.FileUtil;

public class Arkkit
extends JavaPlugin {
    private HashMap<String, ArkPlugin> loaded_patches;
    private static Arkkit me = null;
    public static Economy economy = null;
    public long enable_time = 0L;
    public boolean shutting_down = false;
    private ConcurrentHashMap<String, Player> asyncPlayerMap;
    private ConcurrentHashMap<String, Location> playerLocations;

    public void onEnable() {
        this.shutting_down = false;
        me = this;
        this.loaded_patches = new HashMap();
        this.asyncPlayerMap = new ConcurrentHashMap();
        this.playerLocations = new ConcurrentHashMap();
        this.deleteOldVersions();
        if (!new File("plugins/Arkkit/config.yml").exists()) {
            Bukkit.getConsoleSender().sendMessage((Object)ChatColor.YELLOW + "Copying default config.yml ...");
            this.saveDefaultConfig();
            this.getConfig().options().copyDefaults(true);
            this.saveConfig();
        }
        this.registerCommands();
        this.setupEconomy();
        WorldGuardUtils.hook();
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + "Loading Patches ...");
        Bukkit.getScheduler().runTaskLater(this, () -> {
            try {
                Arkkit.this.convertOldVersions();
            }
            catch (Exception err) {
                Bukkit.getLogger().info("Failed to execute convertOldVersions()!");
                err.printStackTrace();
            }
            Arkkit.this.registerPatches();
            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage(ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + "  *** Compilex Patch Complete! *** ");
            Bukkit.broadcastMessage(ChatColor.GRAY.toString() + ChatColor.ITALIC.toString() + "     Thank you for your patience!");
            Bukkit.broadcastMessage("");
        }, 20L);
        this.getServer().getPluginManager().registerEvents((Listener)new ArkkitListener(), this);
        for (Player pl : Bukkit.getOnlinePlayers()) {
            this.asyncPlayerMap.put(pl.getName(), pl);
            this.playerLocations.put(pl.getName(), pl.getLocation());
        }
        Bukkit.getScheduler().runTaskTimer(this, new Runnable(){

            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    Arkkit.this.playerLocations.put(p.getName(), p.getLocation());
                }
            }
        }, 0L, 40L);
        this.enable_time = System.currentTimeMillis();
    }

    public void onDisable() {
        this.shutting_down = true;
        try {
            Bukkit.broadcastMessage((String)"");
            Bukkit.broadcastMessage((String)(ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + "   *** A Compilex Patch is loading. Please wait... *** "));
            Bukkit.broadcastMessage((String)(ChatColor.GRAY.toString() + ChatColor.ITALIC.toString() + "  This process occurs when Compilex applies a dynamic update."));
            Bukkit.broadcastMessage((String)"");
        }
        catch (Exception err) {
            err.printStackTrace();
        }
        this.saveConfig();
        this.unregisterPatches();
    }

    private void registerCommands() {
        this.getCommand("arkhamreload").setExecutor(new CommandArkReload(this));
        this.getCommand("arkregister").setExecutor(new CommandArkRegister(this));
        this.getCommand("arkunregister").setExecutor(new CommandArkUnregister(this));
        this.getCommand("arklist").setExecutor(new CommandArkList());
        this.getCommand("arkconfig").setExecutor(new CommandArkConfig(this));
        this.getCommand("arkupdate").setExecutor(new CommandArkUpdate(this));
        this.getCommand("arkreboot").setExecutor(new CommandArkReboot());
    }

    private boolean setupEconomy() {
        RegisteredServiceProvider economyProvider = this.getServer().getServicesManager().getRegistration(Economy.class);
        if (economyProvider != null) {
            economy = (Economy)economyProvider.getProvider();
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
        ArkPlugin arkPlugin = ap = this.loaded_patches.containsKey(config_path) ? this.loaded_patches.get(config_path) : null;
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
                this.getConfig().set("patches." + aps.getConfigName(), (Object)false);
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

    public static Arkkit get() {
        return me;
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
                this.getConfig().set("patches." + ArkPlugins.NO_DUPE_EXPLOIT.getConfigName(), (Object)true);
                this.registerPatch(ArkPlugins.NO_DUPE_EXPLOIT.getArkPlugin());
            }
            catch (Exception err) {
                err.printStackTrace();
            }
        }
        if (new File("plugins/Essentials.jar").exists()) {
            try {
                this.getConfig().set("patches." + ArkPlugins.CLEAR_INVENTORY_CONFIRM.getConfigName(), (Object)true);
                this.registerPatch(ArkPlugins.CLEAR_INVENTORY_CONFIRM.getArkPlugin());
            }
            catch (Exception err) {
                err.printStackTrace();
            }
        }
        if (new File("plugins/ArkhamUtils.jar").exists()) {
            try {
                if (Bukkit.getPluginManager().isPluginEnabled("ArkhamUtils")) {
                    Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("ArkhamUtils"));
                }
                new File("plugins/ArkhamUtils.jar").delete();
                FileUtils.deleteDirectory((File)new File("plugins/ArkhamUtils"));
                this.getConfig().set("patches." + ArkPlugins.NO_VEHICLE_TELEPORT.getConfigName(), (Object)true);
                this.registerPatch(ArkPlugins.NO_VEHICLE_TELEPORT.getArkPlugin());
                this.getConfig().set("patches." + ArkPlugins.AUTO_ENTITY_CLEAR.getConfigName(), (Object)true);
                this.registerPatch(ArkPlugins.AUTO_ENTITY_CLEAR.getArkPlugin());
                Bukkit.getScheduler().runTaskLater(Arkkit.get(), new Runnable(){

                    @Override
                    public void run() {
                        Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)"arkreload auto_entity_clear");
                    }
                }, 20L);
            }
            catch (Exception err) {
                err.printStackTrace();
            }
        }
        if (new File("plugins/ArkhamTabTitle.jar").exists()) {
            try {
                if (Bukkit.getPluginManager().isPluginEnabled("ArkhamTabList")) {
                    Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("ArkhamTabList"));
                }
                new File("plugins/ArkhamTabTitle.jar").delete();
                FileUtils.deleteDirectory((File)new File("plugins/ArkhamTabList"));
                this.getConfig().set("patches." + ArkPlugins.TAB_LIST.getConfigName(), (Object)true);
                this.registerPatch(ArkPlugins.TAB_LIST.getArkPlugin());
            }
            catch (Exception err) {
                err.printStackTrace();
            }
        }
        if (new File("plugins/EssentialsKitViewer.jar").exists()) {
            try {
                if (Bukkit.getPluginManager().isPluginEnabled("EssentialsKitViewer")) {
                    Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("EssentialsKitViewer"));
                }
                new File("plugins/EssentialsKitViewer.jar").delete();
                FileUtils.copyFile((File)new File("plugins/EssentialsKitViewer/config.dat"), (File)new File("plugins/Arkkit/kitViewer.dat"));
                FileUtils.deleteDirectory((File)new File("plugins/EssentialsKitViewer"));
                this.getConfig().set("patches." + ArkPlugins.ESSENTIALS_KIT_VIEWER.getConfigName(), (Object)true);
                this.registerPatch(ArkPlugins.ESSENTIALS_KIT_VIEWER.getArkPlugin());
            }
            catch (Exception err) {
                err.printStackTrace();
            }
            Bukkit.getScheduler().runTaskLater(this, new Runnable(){

                @Override
                public void run() {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "SERVER IS REBOOTING TO CLEAR CACHED COMMANDS!");
                    Bukkit.shutdown();
                }
            }, 21L);
        }
        if (new File("plugins/redeemMCMMO.jar").exists()) {
            try {
                if (Bukkit.getPluginManager().isPluginEnabled("RedeemMCMMO")) {
                    Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("RedeemMCMMO"));
                }
                new File("plugins/redeemMCMMO.jar").delete();
                FileUtils.copyFile((File)new File("plugins/RedeemMCMMO/config.yml"), (File)new File("plugins/Arkkit/redeemMCMMO.yml"));
                FileUtils.deleteDirectory((File)new File("plugins/RedeemMCMMO"));
                this.getConfig().set("patches." + ArkPlugins.REDEEM_MCMMO.getConfigName(), (Object)true);
                this.registerPatch(ArkPlugins.REDEEM_MCMMO.getArkPlugin());
            }
            catch (Exception err) {
                err.printStackTrace();
            }
        }
        if (new File("plugins/TempEnchantments.jar").exists()) {
            try {
                if (Bukkit.getPluginManager().isPluginEnabled("TempEnchantments")) {
                    Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("TempEnchantments"));
                }
                new File("plugins/TempEnchantments.jar").delete();
                this.getConfig().set("patches." + ArkPlugins.XP_SANITY.getConfigName(), (Object)true);
                this.registerPatch(ArkPlugins.XP_SANITY.getArkPlugin());
            }
            catch (Exception err) {
                err.printStackTrace();
            }
        }
        if (new File("plugins/DynamicMaxPlayers.jar").exists()) {
            try {
                if (Bukkit.getPluginManager().isPluginEnabled("DynamicMaxPlayers")) {
                    Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("DynamicMaxPlayers"));
                }
                new File("plugins/DynamicMaxPlayers.jar").delete();
                FileUtils.deleteDirectory((File)new File("plugins/DynamicMaxPlayers"));
                this.getConfig().set("patches." + ArkPlugins.DYNAMIC_MAX_PLAYERS.getConfigName(), (Object)true);
                this.registerPatch(ArkPlugins.DYNAMIC_MAX_PLAYERS.getArkPlugin());
            }
            catch (Exception err) {
                err.printStackTrace();
            }
        }
        if (new File("plugins/HotbarArmorSwap.jar").exists()) {
            try {
                if (Bukkit.getPluginManager().isPluginEnabled("HotbarArmorSwap")) {
                    Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("HotbarArmorSwap"));
                }
                new File("plugins/HotbarArmorSwap.jar").delete();
                FileUtils.deleteDirectory((File)new File("plugins/HotbarArmorSwap"));
                this.getConfig().set("patches." + ArkPlugins.HOTBAR_ARMOR_SWAP.getConfigName(), (Object)true);
                this.registerPatch(ArkPlugins.HOTBAR_ARMOR_SWAP.getArkPlugin());
            }
            catch (Exception err) {
                err.printStackTrace();
            }
        }
        if (new File("plugins/AntiLootSteal.jar").exists()) {
            try {
                if (Bukkit.getPluginManager().isPluginEnabled("AntiLootSteal")) {
                    Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("AntiLootSteal"));
                }
                new File("plugins/AntiLootSteal.jar").delete();
                FileUtils.deleteDirectory((File)new File("plugins/AntiLootSteal"));
                this.getConfig().set("patches." + ArkPlugins.ANTI_LOOT_STEAL.getConfigName(), (Object)true);
                this.registerPatch(ArkPlugins.ANTI_LOOT_STEAL.getArkPlugin());
            }
            catch (Exception err) {
                err.printStackTrace();
            }
        }
        if (new File("plugins/PotionFix.jar").exists()) {
            try {
                if (Bukkit.getPluginManager().isPluginEnabled("PotionFix")) {
                    Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("PotionFix"));
                }
                new File("plugins/PotionFix.jar").delete();
                this.getConfig().set("patches." + ArkPlugins.POTION_FIX.getConfigName(), (Object)true);
                this.registerPatch(ArkPlugins.POTION_FIX.getArkPlugin());
            }
            catch (Exception err) {
                err.printStackTrace();
            }
        }
        if (new File("plugins/HideStream.jar").exists()) {
            try {
                if (Bukkit.getPluginManager().isPluginEnabled("HideStream")) {
                    Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("HideStream"));
                }
                new File("plugins/HideStream.jar").delete();
                this.getConfig().set("patches." + ArkPlugins.HIDE_STREAM.getConfigName(), (Object)true);
                this.registerPatch(ArkPlugins.HIDE_STREAM.getArkPlugin());
            }
            catch (Exception err) {
                err.printStackTrace();
            }
        }
        if (new File("plugins/NetherGlitchFix.jar").exists()) {
            try {
                if (Bukkit.getPluginManager().isPluginEnabled("NetherGlitchFixPlugin")) {
                    Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("NetherGlitchFixPlugin"));
                }
                new File("plugins/NetherGlitchFix.jar").delete();
                this.getConfig().set("patches." + ArkPlugins.NETHER_GLITCH_FIX.getConfigName(), (Object)true);
                this.registerPatch(ArkPlugins.NETHER_GLITCH_FIX.getArkPlugin());
            }
            catch (Exception err) {
                err.printStackTrace();
            }
        }
        if (new File("plugins/AsyncMonsters.jar").exists()) {
            try {
                if (Bukkit.getPluginManager().isPluginEnabled("AsyncMonsters")) {
                    Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("AsyncMonsters"));
                }
                new File("plugins/AsyncMonsters.jar").delete();
                this.getConfig().set("patches." + ArkPlugins.ASYNC_MONSTERS.getConfigName(), (Object)true);
                this.registerPatch(ArkPlugins.ASYNC_MONSTERS.getArkPlugin());
                Bukkit.getScheduler().runTaskLater(this, new Runnable(){

                    @Override
                    public void run() {
                        Bukkit.getConsoleSender().sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "SERVER IS REBOOTING TO CLEAR ASYNC MONSTERS CACHED LISTENERS!");
                        Bukkit.shutdown();
                    }
                }, 21L);
            }
            catch (Exception err) {
                err.printStackTrace();
            }
        }
        if (new File("plugins/AntiGodAppleCraft.jar").exists()) {
            try {
                if (Bukkit.getPluginManager().isPluginEnabled("AntiGodAppleCraftPlugin")) {
                    Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("AntiGodAppleCraftPlugin"));
                }
                new File("plugins/AntiGodAppleCraft.jar").delete();
                this.getConfig().set("patches." + ArkPlugins.ANTI_GOD_APPLE_CRAFT.getConfigName(), (Object)true);
                this.registerPatch(ArkPlugins.ANTI_GOD_APPLE_CRAFT.getArkPlugin());
            }
            catch (Exception err) {
                err.printStackTrace();
            }
        }
        if (new File("plugins/ArkhamShops.jar").exists()) {
            try {
                if (Bukkit.getPluginManager().isPluginEnabled("ArkhamShops")) {
                    Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("ArkhamShops"));
                }
                new File("plugins/ArkhamShops.jar").delete();
                this.getConfig().set("patches." + ArkPlugins.SHOP_COMMAND.getConfigName(), (Object)true);
                this.registerPatch(ArkPlugins.SHOP_COMMAND.getArkPlugin());
            }
            catch (Exception err) {
                err.printStackTrace();
            }
        }
        if (new File("plugins/AntiClip.jar").exists()) {
            try {
                if (Bukkit.getPluginManager().isPluginEnabled("AntiClip")) {
                    Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("AntiClip"));
                }
                new File("plugins/AntiClip.jar").delete();
                this.getConfig().set("patches." + ArkPlugins.ANTI_HACK.getConfigName(), (Object)true);
                this.registerPatch(ArkPlugins.ANTI_HACK.getArkPlugin());
            }
            catch (Exception err) {
                err.printStackTrace();
            }
        }
        if (new File("plugins/ArkhamCooldowns.jar").exists()) {
            try {
                if (Bukkit.getPluginManager().isPluginEnabled("ArkhamCooldowns")) {
                    Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("ArkhamCooldowns"));
                }
                new File("plugins/ArkhamCooldowns.jar").delete();
                FileUtil.copy((File)new File("plugins/ArkhamCooldowns/config.dat"), (File)new File("plugins/Arkkit/command-cooldowns.yml"));
                this.getConfig().set("patches." + ArkPlugins.COMMAND_COOLDOWNS.getConfigName(), (Object)true);
                this.registerPatch(ArkPlugins.COMMAND_COOLDOWNS.getArkPlugin());
            }
            catch (Exception err) {
                err.printStackTrace();
            }
        }
        if (new File("plugins/ArkhamTrade.jar").exists()) {
            try {
                if (Bukkit.getPluginManager().isPluginEnabled("ArkhamTrade")) {
                    Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("ArkhamTrade"));
                }
                new File("plugins/ArkhamTrade.jar").delete();
                this.getConfig().set("patches." + ArkPlugins.TRADING.getConfigName(), (Object)true);
                this.registerPatch(ArkPlugins.TRADING.getArkPlugin());
            }
            catch (Exception err) {
                err.printStackTrace();
            }
        }
        if (new File("plugins/Marriage.jar").exists()) {
            try {
                if (Bukkit.getPluginManager().isPluginEnabled("Marriage")) {
                    Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("Marriage"));
                }
                new File("plugins/Marriage.jar").delete();
                this.getConfig().set("patches." + ArkPlugins.MARRIAGE.getConfigName(), (Object)true);
                this.registerPatch(ArkPlugins.MARRIAGE.getArkPlugin());
            }
            catch (Exception err) {
                err.printStackTrace();
            }
        }
        if (new File("plugins/VotingSystems.jar").exists()) {
            try {
                if (Bukkit.getPluginManager().isPluginEnabled("VotingSystems")) {
                    Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("VotingSystems"));
                }
                new File("plugins/VotingSystems.jar").delete();
                this.getConfig().set("patches." + ArkPlugins.VOTING.getConfigName(), (Object)true);
                this.registerPatch(ArkPlugins.VOTING.getArkPlugin());
            }
            catch (Exception err) {
                err.printStackTrace();
            }
        }
        if (new File("plugins/ArkhamChatBubbles.jar").exists()) {
            try {
                if (Bukkit.getPluginManager().isPluginEnabled("ArkhamChatBubbles")) {
                    Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("ArkhamChatBubbles"));
                }
                new File("plugins/ArkhamChatBubbles.jar").delete();
                this.getConfig().set("patches." + ArkPlugins.CHAT_BUBBLES.getConfigName(), (Object)true);
                this.registerPatch(ArkPlugins.CHAT_BUBBLES.getArkPlugin());
            }
            catch (Exception err) {
                err.printStackTrace();
            }
        }
        if (new File("plugins/ArkhamSyncAccount.jar").exists() && new File("plugins/Arkkit/XF_sqlconfig.yml").exists()) {
            try {
                if (Bukkit.getPluginManager().isPluginEnabled("ArkhamSyncAccount")) {
                    Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("ArkhamSyncAccount"));
                }
                new File("plugins/ArkhamSyncAccount.jar").delete();
                this.getConfig().set("patches." + ArkPlugins.XENFORO_SYNC_ACCOUNT.getConfigName(), (Object)true);
                this.registerPatch(ArkPlugins.XENFORO_SYNC_ACCOUNT.getArkPlugin());
            }
            catch (Exception err) {
                err.printStackTrace();
            }
        }
        if (new File("plugins/ArkhamTitleAPI.jar").exists()) {
            try {
                this.getConfig().set("patches." + ArkPlugins.CREATE_TITLE.getConfigName(), (Object)true);
                this.registerPatch(ArkPlugins.CREATE_TITLE.getArkPlugin());
            }
            catch (Exception err) {
                err.printStackTrace();
            }
        }
        if (new File("plugins/PlotMe.jar").exists()) {
            this.getConfig().set("patches." + ArkPlugins.PLOTME_PATCHES.getConfigName(), (Object)true);
            this.registerPatch(ArkPlugins.PLOTME_PATCHES.getArkPlugin());
        }
        if (new File("plugins/SimpleChat.jar").exists()) {
            try {
                if (Bukkit.getPluginManager().isPluginEnabled("SimpleChat")) {
                    Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("SimpleChat"));
                }
                this.getConfig().set("patches." + ArkPlugins.CHAT_FILTER.getConfigName(), (Object)true);
                this.registerPatch(ArkPlugins.CHAT_FILTER.getArkPlugin());
            }
            catch (Exception err) {
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
                this.getConfig().set("patches." + ArkPlugins.NO_NEW_CHUNKS.getConfigName(), (Object)true);
                this.registerPatch(ArkPlugins.NO_NEW_CHUNKS.getArkPlugin());
            }
            catch (Exception err) {
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
                this.getConfig().set("patches." + ArkPlugins.PLUGIN_HIDER.getConfigName(), (Object)true);
                this.registerPatch(ArkPlugins.PLUGIN_HIDER.getArkPlugin());
            }
            catch (Exception err) {
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
            this.getConfig().set("patches." + ArkPlugins.CHAT_LIMITER.getConfigName(), (Object)true);
            this.registerPatch(ArkPlugins.CHAT_LIMITER.getArkPlugin());
        }
        if (new File("plugins/GiveAll.jar").exists()) {
            if (Bukkit.getPluginManager().isPluginEnabled("GiveAll")) {
                Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("GiveAll"));
            }
            new File("plugins/GiveAll.jar").delete();
            this.getConfig().set("patches." + ArkPlugins.GIVE_ALL.getConfigName(), (Object)true);
            this.registerPatch(ArkPlugins.GIVE_ALL.getArkPlugin());
        }
        if (new File("plugins/EssentialsWarpUI.jar").exists()) {
            if (Bukkit.getPluginManager().isPluginEnabled("EssentialsWarpUI")) {
                Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("EssentialsWarpUI"));
            }
            new File("plugins/EssentialsWarpUI.jar").delete();
            this.getConfig().set("patches." + ArkPlugins.ESSENTIALS_WARP_UI.getConfigName(), (Object)true);
            this.registerPatch(ArkPlugins.ESSENTIALS_WARP_UI.getArkPlugin());
        }
        if (new File("plugins/ArkhamWarpAliases.jar").exists()) {
            if (Bukkit.getPluginManager().isPluginEnabled("ArkhamWarpAliases")) {
                Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("ArkhamWarpAliases"));
            }
            new File("plugins/ArkhamWarpAliases.jar").delete();
            this.getConfig().set("patches." + ArkPlugins.ESSENTIALS_WARP_ALIASES.getConfigName(), (Object)true);
            this.registerPatch(ArkPlugins.ESSENTIALS_WARP_ALIASES.getArkPlugin());
        }
        if (new File("plugins/AntiCrash.jar").exists()) {
            if (Bukkit.getPluginManager().isPluginEnabled("AntiCrash")) {
                Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("AntiCrash"));
            }
            new File("plugins/AntiCrash.jar").delete();
            this.getConfig().set("patches." + ArkPlugins.ANTI_CRASH.getConfigName(), (Object)true);
            this.registerPatch(ArkPlugins.ANTI_CRASH.getArkPlugin());
        }
        if (new File("plugins/ArkhamMOTD.jar").exists()) {
            try {
                new File("plugins/ArkhamMOTD/motd.txt").renameTo(new File("plugins/Arkkit/motd.txt"));
                new File("plugins/ArkhamMOTD.jar").delete();
                new File("plugins/ArkhamMOTD").delete();
            }
            catch (Exception err) {
                err.printStackTrace();
            }
            this.getConfig().set("patches." + ArkPlugins.CUSTOM_MOTD.getConfigName(), (Object)true);
            this.registerPatch(ArkPlugins.CUSTOM_MOTD.getArkPlugin());
        }
        if (new File("plugins/ArkhamHubCommand.jar").exists() || new File("plugins/CosmicHubCommand.jar").exists() || new File("plugins/ExtronusHubCommand.jar").exists()) {
            if (Bukkit.getPluginManager().isPluginEnabled("ArkhamHubCommand")) {
                Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("ArkhamHubCommand"));
            }
            new File("plugins/ArkhamHubCommand.jar").delete();
            new File("plugins/CosmicHubCommand.jar").delete();
            new File("plugins/ExtronusHubCommand.jar").delete();
            this.getConfig().set("patches." + ArkPlugins.HUB_COMMAND.getConfigName(), (Object)true);
            this.registerPatch(ArkPlugins.HUB_COMMAND.getArkPlugin());
        }
        if (new File("plugins/LilyEssentials.jar").exists()) {
            if (Bukkit.getPluginManager().isPluginEnabled("LilyEssentials")) {
                Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("LilyEssentials"));
            }
            new File("plugins/LilyEssentials.jar").delete();
            new File("plugins/LilyEssentials").delete();
            this.getConfig().set("patches." + ArkPlugins.ALERT_SYSTEM.getConfigName(), (Object)true);
            this.registerPatch(ArkPlugins.ALERT_SYSTEM.getArkPlugin());
            this.getConfig().set("patches." + ArkPlugins.NETWORK_EVENTS.getConfigName(), (Object)true);
            this.registerPatch(ArkPlugins.NETWORK_EVENTS.getArkPlugin());
            Bukkit.getScheduler().runTaskLater(this, new Runnable(){

                @Override
                public void run() {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "SERVER IS REBOOTING TO CLEAR LILY-ESSENTIALS CACHED LISTENERS!");
                    Arkkit.get().saveConfig();
                    Bukkit.shutdown();
                }
            }, 21L);
            return;
        }
        if (new File("plugins/FactionAntiDupe-EXTRONUS.jar").exists()) {
            new File("plugins/FactionAntiDupe-EXTRONUS.jar").delete();
        }
        if (new File("plugins/ChunkGC.jar").exists() || new File("plugins/ChunkGCTask.jar").exists() || new File("plugins/ExtronusChunkGC.jar").exists()) {
            if (new File("gc_force_save").exists()) {
                new File("gc_force_save").delete();
                this.getConfig().set("patches." + ArkPlugins.BULK_SAVING.getConfigName(), (Object)true);
                this.registerPatch(ArkPlugins.BULK_SAVING.getArkPlugin());
            }
            if (Bukkit.getPluginManager().isPluginEnabled("ChunkGC")) {
                Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("ChunkGC"));
            }
            new File("plugins/ChunkGC.jar").delete();
            new File("plugins/ChunkGCTask.jar").delete();
            new File("plugins/ExtronusChunkGC.jar").delete();
            this.getConfig().set("patches." + ArkPlugins.CHUNK_GC.getConfigName(), (Object)true);
            this.registerPatch(ArkPlugins.CHUNK_GC.getArkPlugin());
            if (!new File("no_adjust").exists()) {
                this.getConfig().set("patches." + ArkPlugins.WORLD_TICK_OPTIMIZER.getConfigName(), (Object)true);
                this.registerPatch(ArkPlugins.WORLD_TICK_OPTIMIZER.getArkPlugin());
                this.getConfig().set("patches." + ArkPlugins.NO_NATURAL_MOBS.getConfigName(), (Object)true);
                this.registerPatch(ArkPlugins.NO_NATURAL_MOBS.getArkPlugin());
            }
        }
        this.saveConfig();
    }

    private void deleteOldVersions() {
        if (new File("plugins/ArkhamNetworkAI.jar").exists()) {
            if (Bukkit.getPluginManager().isPluginEnabled("ArkhamNetworkAI")) {
                Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("ArkhamNetworkAI"));
            }
            new File("plugins/ArkhamNetworkAI.jar").delete();
        }
    }

    public ConcurrentHashMap<String, Player> getAsyncPlayerMap() {
        return this.asyncPlayerMap;
    }

    public ConcurrentHashMap<String, Location> getPlayerLocations() {
        return this.playerLocations;
    }
}

