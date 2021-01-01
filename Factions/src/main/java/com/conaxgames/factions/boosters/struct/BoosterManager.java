/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.FactionUtils
 *  com.google.common.collect.Lists
 *  com.massivecraft.factions.Faction
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 */
package com.conaxgames.factions.boosters.struct;


import com.conaxgames.factions.boosters.enums.BoosterType;
import com.conaxgames.factions.boosters.utils.ItemBuilder;
import com.conaxgames.factions.boosters.utils.TimeUtil;
import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import com.google.common.collect.Lists;
import com.massivecraft.factions.Faction;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class BoosterManager {
    private File boosterFile;
    private FileConfiguration config;
    private ConcurrentHashMap<String, FactionBoosters> factionBoosters = new ConcurrentHashMap();
    private final Material boosterMaterial = Material.EMERALD;

    public FactionBoosters getFactionBooster(Faction factio) {
        return this.factionBoosters.get(FactionUtils.getFactionId((Faction)factio));
    }

    public void loadActiveBoosters() throws IOException {
        if (!new File("plugins/CosmicFactionBoosters").exists()) {
            new File("plugins/CosmicFactionBoosters").mkdir();
        }
        this.boosterFile = new File("plugins/CosmicFactionBoosters/booster.yml");
        if (!this.boosterFile.exists()) {
            this.boosterFile.createNewFile();
        }
        this.config = YamlConfiguration.loadConfiguration((File)this.boosterFile);
        for (String boosterString : this.config.getStringList("active-boosters")) {
            FactionBoosters boosters;
            String[] args = boosterString.split(":");
            String factionId = args[0];
            int secondsLeft = Integer.parseInt(args[1]);
            String who = args[2];
            double mult = Double.parseDouble(args[3]);
            long timeApplied = Long.parseLong(args[4]);
            int maxSeconds = Integer.parseInt(args[5]);
            BoosterType type = BoosterType.valueOf(args[6]);
            ActiveBooster booster = new ActiveBooster(who, mult, timeApplied, secondsLeft, maxSeconds, type);
            FactionBoosters factionBoosters = boosters = this.factionBoosters.containsKey(factionId) ? this.factionBoosters.get(factionId) : new FactionBoosters();
            if (!this.factionBoosters.containsKey(factionId)) {
                this.factionBoosters.put(factionId, boosters);
            }
            boosters.put(type, booster);
        }
    }

    public void saveActiveBoosters() {
        ArrayList entries = Lists.newArrayList();
        this.factionBoosters.forEach((factionId, factionBooster) -> factionBooster.forEach((boosterType, activeBooster) -> {
            String string = factionId + ":" + activeBooster.toString();
            entries.add(string);
        }));
        this.config.set("active-boosters", (Object)entries);
        try {
            this.config.save(this.boosterFile);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ItemStack createBooster(BoosterType boosterType, int durationSeconds, double multiplier) {
        ItemStack item = ItemBuilder.buildItem(this.boosterMaterial, (Object) ChatColor.AQUA + ChatColor.BOLD.toString() + "Faction " + (Object)ChatColor.AQUA + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + boosterType.getItemName() + (Object)ChatColor.AQUA + ChatColor.BOLD.toString() + " Booster", (Object)ChatColor.GRAY + boosterType.getItemDescription(), "", (Object)ChatColor.GRAY + "Right-Click to activate.", "", ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "MULTIPLIER", ChatColor.GRAY.toString() + multiplier + "x", "", (Object)ChatColor.LIGHT_PURPLE + ChatColor.BOLD.toString() + "DURATION", (Object)ChatColor.GRAY + TimeUtil.formatDifference(durationSeconds));
        return item;
    }

    public boolean isBoosterItem(ItemStack item) {
        if (item == null || item.getType() == Material.AIR || item.getType() != this.boosterMaterial) {
            return false;
        }
        if (!(item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().hasLore())) {
            return false;
        }
        ItemMeta im = item.getItemMeta();
        return im.getDisplayName().contains("Booster") && im.getDisplayName().startsWith(ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + "Faction ");
    }

    public void showActiveBoosters(Player player, FactionBoosters boosters) {
        player.sendMessage("");
        player.sendMessage((Object)ChatColor.AQUA + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.AQUA + "Currently Activate Faction Boosters:");
        boosters.forEach((type, activeBooster) -> player.sendMessage((Object)ChatColor.AQUA + ChatColor.BOLD.toString() + "* " + (Object)ChatColor.LIGHT_PURPLE + ChatColor.BOLD.toString() + activeBooster.getMultiplier() + "x " + type.getItemName() + (Object)ChatColor.AQUA + " started by " + (Object)ChatColor.LIGHT_PURPLE + ChatColor.BOLD.toString() + activeBooster.getWhoApplied() + (Object)ChatColor.AQUA + " expires in " + (Object)ChatColor.LIGHT_PURPLE + ChatColor.BOLD.toString() + activeBooster.getFormattedTimeLeft() + (Object)ChatColor.AQUA + "!"));
    }

    public BoosterItemInfo getBoosterItemInfo(ItemStack item) {
        ItemMeta im = item.getItemMeta();
        List lore = im.getLore();
        BoosterType boosterType = BoosterType.fromItemName(ChatColor.stripColor((String)im.getDisplayName().split("Faction ")[1].split(" Booster")[0]));
        String multiplierStr = (String)lore.get(6);
        double multiplier = Double.parseDouble(ChatColor.stripColor((String)multiplierStr.split("x")[0]));
        String durationStr = ChatColor.stripColor((String)((String)lore.get(9)));
        if (multiplier > 0.0 && durationStr != null && boosterType != null) {
            return new BoosterItemInfo(boosterType, multiplier, durationStr);
        }
        return null;
    }

    public ConcurrentHashMap<String, FactionBoosters> getFactionBoosters() {
        return this.factionBoosters;
    }
}

