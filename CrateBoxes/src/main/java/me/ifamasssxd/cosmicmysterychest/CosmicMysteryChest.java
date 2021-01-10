/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  me.ifamasssxd.cosmicskycustoms.CosmicSkyCustomItems
 *  me.ifamasssxd.cosmicskycustoms.struct.CosmicItem
 *  org.bukkit.Bukkit
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.event.Listener
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.java.JavaPlugin
 */
package me.ifamasssxd.cosmicmysterychest;

import me.ifamasssxd.cosmicmysterychest.commands.CommandLootboxToggle;
import me.ifamasssxd.cosmicmysterychest.commands.CommandMysteryChest;
import me.ifamasssxd.cosmicmysterychest.items.MysteryLootboxItem;
import me.ifamasssxd.cosmicmysterychest.listeners.MysteryChestListener;
import me.ifamasssxd.cosmicmysterychest.managers.MysteryChestManager;
import me.ifamasssxd.cosmicskycustoms.CosmicSkyCustomItems;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class CosmicMysteryChest
extends JavaPlugin {
    private static CosmicMysteryChest instance;
    private ShardAnimations toggle;
    private MysteryChestManager chestManager;
    private MysteryLootboxItem mysteryLootboxItem;

    public ShardAnimations getShardAnimations() {
        return this.toggle;
    }

    public void onEnable() {
        instance = this;
        instance.saveDefaultConfig();
        this.saveDefaultConfig();
        this.toggle = new ShardAnimations("toggles.yml");
        this.chestManager = new MysteryChestManager();
        this.chestManager.onEnable(this);
        this.getCommand("mysterychest").setExecutor(new CommandMysteryChest(this));
        this.getCommand("lbtoggle").setExecutor(new CommandLootboxToggle());
        this.getServer().getPluginManager().registerEvents(new MysteryChestListener(), this);
        if (Bukkit.getPluginManager().isPluginEnabled("CosmicSkyCustomItems")) {
            this.mysteryLootboxItem = new MysteryLootboxItem();
            CosmicSkyCustomItems.get().getItemManager().registerCustomItem(this.mysteryLootboxItem);
        }
    }

    public void onDisable() {
        this.toggle.saveToggles();
        if (this.chestManager != null) {
            this.chestManager.onDisable(this);
        }
    }

    public static CosmicMysteryChest get() {
        return instance;
    }

    public MysteryChestManager getChestManager() {
        return this.chestManager;
    }

    public MysteryLootboxItem getMysteryLootboxItem() {
        return this.mysteryLootboxItem;
    }
}

