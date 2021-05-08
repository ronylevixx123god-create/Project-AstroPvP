/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.massivecraft.factions.Faction
 *  me.ifamasssxd.factionstop.FactionsTop
 *  me.ifamasssxd.factionstop.struct.TopFaction
 *  net.minecraft.util.com.google.common.reflect.TypeToken
 *  org.bukkit.Bukkit
 *  org.bukkit.craftbukkit.libs.com.google.gson.Gson
 *  org.bukkit.craftbukkit.libs.com.google.gson.GsonBuilder
 */
package me.ifamasssxd.factionupgrades.struct;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.P;
import org.bukkit.Bukkit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UpgradeManager {
    private Map<String, FactionUpgradeInfo> upgradeInfo = new HashMap<String, FactionUpgradeInfo>();
    private File upgradeFile;
    private Gson gson = new GsonBuilder().create();
    private double upgradePriceMultiplier = 1.0;

    public void loadUpgrades() {
        try {
            if (!P.getP().getDataFolder().exists()) {
                P.getP().getDataFolder().mkdir();
            }
            this.upgradeFile = new File(P.getP().getDataFolder(), "upgrades.json");
            if (!this.upgradeFile.exists()) {
                this.upgradeFile.createNewFile();
            }
            StringBuilder string = new StringBuilder();
            BufferedReader reader = new BufferedReader(new FileReader(this.upgradeFile));
            String line = null;
            while ((line = reader.readLine()) != null) {
                string.append(line);
            }
            this.upgradeInfo = (Map)this.gson.fromJson(string.toString(), new TypeToken<ConcurrentHashMap<String, FactionUpgradeInfo>>(){}.getType());
            if (this.upgradeInfo == null) {
                this.upgradeInfo = new ConcurrentHashMap<String, FactionUpgradeInfo>();
            }
            if (this.upgradeInfo.size() > 0) {
                Bukkit.getLogger().info("Loaded " + this.upgradeInfo.size() + " Upgrades.");
            }
            this.upgradePriceMultiplier = P.getP().getConfig().getDouble("upgradeModifier", 1.0);
            Bukkit.getLogger().info("[FactionUpgrades] Loaded " + this.upgradePriceMultiplier + " Upgrade Cost Multiplier.");
        }
        catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    public Long canAccessHeroic(Faction faction, FactionUpgrade upgrade) {
        if (!(upgrade.getUpgrade() instanceof HeroicUpgrade)) {
            return -1L;
        }

        return -1L;
    }

    public boolean canAccessHeroic(String factionID, FactionUpgrade upgrade) {
        if (!(upgrade.getUpgrade() instanceof HeroicUpgrade)) {
            return false;
        }
        return true;
    }

    public void saveUpgradeInfo() {
        try {
            String map = this.gson.toJson(this.upgradeInfo);
            FileWriter writer = new FileWriter(this.upgradeFile);
            try {
                writer.write(map);
                writer.flush();
            }
            finally {
                if (Collections.singletonList(writer).get(0) != null) {
                    writer.close();
                }
            }
        }
        catch (Throwable $ex) {
            $ex.printStackTrace();
        }
    }

    public FactionUpgradeInfo getFactionUpgradeInfo(Faction faction) {
        return this.upgradeInfo.computeIfAbsent(faction.getId(), u -> new FactionUpgradeInfo(faction.getId()));
    }

    public Map<String, FactionUpgradeInfo> getUpgradeInfo() {
        return this.upgradeInfo;
    }

    public double getUpgradePriceMultiplier() {
        return this.upgradePriceMultiplier;
    }
}

