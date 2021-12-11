package com.compilex.CosmicArmorSets;

import com.compilex.CosmicArmorSets.cmds.CommandArmorSet;
import com.compilex.CosmicArmorSets.cmds.CommandHeroicArmor;
import com.compilex.CosmicArmorSets.cmds.CommandHeroicUpgrade;
import com.compilex.CosmicArmorSets.cmds.CommandMasteryShard;
import com.compilex.CosmicArmorSets.enums.AbilityArmorSet;
import com.compilex.CosmicArmorSets.enums.CachedArmorSet;
import com.compilex.CosmicArmorSets.enums.GoblinSetManager;
import com.compilex.CosmicArmorSets.items.AntiMKitCrystalItem;
import com.compilex.CosmicArmorSets.items.ArmorSetBonusCrystalItem;
import com.compilex.CosmicArmorSets.items.CrystalExtractionItem;
import com.compilex.CosmicArmorSets.items.OmniCrystal;
import com.compilex.CosmicArmorSets.listeners.*;
import lombok.Getter;
import me.ifamasssxd.cosmicskycustoms.CosmicSkyCustomItems;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class CosmicArmorSetsPlugin
        extends JavaPlugin {
    private static CosmicArmorSetsPlugin p;
    private final Map<Player, CachedArmorSet<AbilityArmorSet>> playerAbilityArmorSets = new ConcurrentHashMap<Player, CachedArmorSet<AbilityArmorSet>>();
    private final boolean debugging = false;
    private GoblinSetManager goblinSetManager;

    public static CosmicArmorSetsPlugin get_() {
        return p;
    }

    public void onEnable() {
        p = this;
        p.registerListeners();
        this.registerCommands();
        Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin)this, () -> {
            for (Player pl : Bukkit.getServer().getOnlinePlayers())
                CosmicArmorSetsAPI.getArmorSet(pl);
        },80L, 80);
        Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin)this, () -> {
            for (Player pl : Bukkit.getServer().getOnlinePlayers())
                CosmicArmorSetsAPI.getAbilityArmorSet(pl);
        },80, 80);
        if (Bukkit.getPluginManager().isPluginEnabled("CosmicSkyCustomItems")) {
            CosmicSkyCustomItems.get().getItemManager().registerCustomItem(new CrystalExtractionItem());
            CosmicSkyCustomItems.get().getItemManager().registerCustomItem(new AntiMKitCrystalItem());
            CosmicSkyCustomItems.get().getItemManager().registerCustomItem(new ArmorSetBonusCrystalItem());
            CosmicSkyCustomItems.get().getItemManager().registerCustomItem(new OmniCrystal());
        }
        this.goblinSetManager = new GoblinSetManager(this);
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
        getServer().getPluginManager().registerEvents(new ArmorEquipListener(), this);
        this.getServer().getPluginManager().registerEvents(new HeroicArmorListener(), this);
        this.getServer().getPluginManager().registerEvents(new ArmorSetUpgradeListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new SetBonusListener(), this);
        this.getServer().getPluginManager().registerEvents(new MasteryShardListener(), this);
        this.getServer().getPluginManager().registerEvents(new ArmorSetCrystalTinkererListener(), this);
        getServer().getPluginManager().registerEvents(new NebulaSetListener(), this);
    }

    public Map<Player, CachedArmorSet<AbilityArmorSet>> getPlayerAbilityArmorSets() {
        return this.playerAbilityArmorSets;
    }

    public boolean isDebugging() {
        return this.debugging;
    }
}

