/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  me.ifamasssxd.cosmicskycustoms.CosmicSkyCustomItems
 *  me.ifamasssxd.cosmicskycustoms.struct.CosmicItem
 *  org.bukkit.Bukkit
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Listener
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.java.JavaPlugin
 */
package com.compilex.CosmicArmorSets;

import com.compilex.CosmicArmorSets.cmds.CommandArmorSet;
import com.compilex.CosmicArmorSets.cmds.CommandHeroicArmor;
import com.compilex.CosmicArmorSets.cmds.CommandHeroicUpgrade;
import com.compilex.CosmicArmorSets.cmds.CommandMasteryShard;
import com.compilex.CosmicArmorSets.enums.AbilityArmorSet;
import com.compilex.CosmicArmorSets.enums.CachedArmorSet;
import com.compilex.CosmicArmorSets.items.AntiMKitCrystalItem;
import com.compilex.CosmicArmorSets.items.ArmorSetBonusCrystalItem;
import com.compilex.CosmicArmorSets.items.CrystalExtractionItem;
import com.compilex.CosmicArmorSets.items.OmniCrystal;
import com.compilex.CosmicArmorSets.listeners.*;
import me.ifamasssxd.cosmicskycustoms.CosmicSkyCustomItems;
import me.ifamasssxd.cosmicskycustoms.struct.CosmicItem;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CosmicArmorSetsPlugin
        extends JavaPlugin {
    private static CosmicArmorSetsPlugin p;
    private Map<Player, CachedArmorSet<AbilityArmorSet>> playerAbilityArmorSets = new ConcurrentHashMap<Player, CachedArmorSet<AbilityArmorSet>>();
    private boolean debugging = false;

    public static CosmicArmorSetsPlugin get_() {
        return p;
    }

    public void onEnable() {
        p = this;
        p.registerListeners();
        this.registerCommands();
        Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) this, () -> {
            for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
                CosmicArmorSetsAPI.getArmorSet(pl);
            }
        }, 100L, 100L);
        Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) this, () -> {
            for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
                CosmicArmorSetsAPI.getAbilityArmorSet(pl);
            }
        }, 20L, 20L);
        if (Bukkit.getPluginManager().isPluginEnabled("CosmicSkyCustomItems")) {
            CosmicSkyCustomItems.get().getItemManager().registerCustomItem((CosmicItem) new CrystalExtractionItem());
            CosmicSkyCustomItems.get().getItemManager().registerCustomItem((CosmicItem) new AntiMKitCrystalItem());
            CosmicSkyCustomItems.get().getItemManager().registerCustomItem((CosmicItem) new ArmorSetBonusCrystalItem());
            CosmicSkyCustomItems.get().getItemManager().registerCustomItem((CosmicItem) new OmniCrystal());
        }
    }

    public void onDisable() {
    }

    private void registerCommands() {
        this.getCommand("armorset").setExecutor(new CommandArmorSet());
        this.getCommand("heroicarmor").setExecutor(new CommandHeroicArmor());
        this.getCommand("heroicupgrade").setExecutor(new CommandHeroicUpgrade());
        this.getCommand("masteryshard").setExecutor(new CommandMasteryShard());
    }

    private void registerListeners() {
        this.getServer().getPluginManager().registerEvents(new HeroicArmorListener(), (Plugin) this);
        this.getServer().getPluginManager().registerEvents(new ArmorSetUpgradeListener(), (Plugin) this);
        Bukkit.getServer().getPluginManager().registerEvents(new SetBonusListener(), (Plugin) this);
        this.getServer().getPluginManager().registerEvents(new MasteryShardListener(), (Plugin) this);
        this.getServer().getPluginManager().registerEvents(new ArmorSetCrystalTinkererListener(), (Plugin) this);
    }

    public Map<Player, CachedArmorSet<AbilityArmorSet>> getPlayerAbilityArmorSets() {
        return this.playerAbilityArmorSets;
    }

    public boolean isDebugging() {
        return this.debugging;
    }
}

