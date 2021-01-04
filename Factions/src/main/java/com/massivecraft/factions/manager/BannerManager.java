/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.JSONUtils
 *  com.cosmicpvp.cosmicutils.utils.LocationUtils
 *  com.google.common.collect.MapMaker
 *  com.massivecraft.factions.Faction
 *  net.minecraft.util.com.google.gson.reflect.TypeToken
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitRunnable
 */
package com.massivecraft.factions.manager;

import com.cosmicpvp.cosmicutils.utils.JSONUtils;
import com.cosmicpvp.cosmicutils.utils.LocationUtils;
import com.google.common.collect.MapMaker;
import com.google.common.reflect.TypeToken;
import com.massivecraft.factions.Faction;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.massivecraft.factions.P;
import com.massivecraft.factions.struct.BannerDesign;
import com.massivecraft.factions.struct.FactionBanner;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class BannerManager {
    public static Material bannerMaterial = Material.BANNER;
    private Map<String, FactionBanner> factionBannerMap = new MapMaker().concurrencyLevel(32).makeMap();
    private Map<String, BannerDesign> bannerDesignMap = new HashMap<String, BannerDesign>();
    private Type token = new TypeToken<HashMap<String, BannerDesign>>(){}.getType();
    private File bannerFile;

    public void onEnable() throws IOException {
        this.bannerFile = JSONUtils.getOrCreateFile(P.getP().getDataFolder(), (String)"factionBanners.json");
        this.bannerDesignMap = (Map)JSONUtils.fromJson((File)this.bannerFile, (Type)this.token);
        if (this.bannerDesignMap == null) {
            this.bannerDesignMap = new HashMap<String, BannerDesign>();
        }
        new BukkitRunnable(){

            public void run() {
                BannerManager.this.factionBannerMap.forEach((fId, banner) -> {
                    if (banner.hasExpired()) {
                        Location loc = banner.getActiveLocation();
                        Bukkit.getLogger().info("[CosmicFactionBanners] Expiring faction banner for " + fId + " (" + banner.getWhoPlacedUsername() + ") at " + (loc == null ? "N/A" : LocationUtils.getStringFromLocation((Location)loc)));
                        banner.removeBanner();
                        BannerManager.this.factionBannerMap.remove(fId);
                    }
                });
            }
        }.runTaskTimer(P.getP(), 20L, 20L);
    }

    public void onDisable() throws IOException {
        JSONUtils.saveJSONToFile((File)this.bannerFile, this.bannerDesignMap, (Object)this.token);
        this.factionBannerMap.values().forEach(FactionBanner::removeBanner);
    }

    public ItemStack createBanner(Faction faction, Player player, boolean white) {
        return this.createBanner(faction, player, white, null);
    }

    public ItemStack createBanner(Faction faction, Player player, boolean white, String freeReason) {
        return P.getP().getBannerItem().createItem(player, new Object[]{faction, player, white, freeReason});
    }

    public Map<String, FactionBanner> getFactionBannerMap() {
        return this.factionBannerMap;
    }

    public Map<String, BannerDesign> getBannerDesignMap() {
        return this.bannerDesignMap;
    }
}

