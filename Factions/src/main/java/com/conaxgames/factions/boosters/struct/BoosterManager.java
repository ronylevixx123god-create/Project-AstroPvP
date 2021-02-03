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

    private ConcurrentHashMap<String, FactionBoosters> factionBoosters = new ConcurrentHashMap<>();

    public ConcurrentHashMap<String, FactionBoosters> getFactionBoosters() {
        return this.factionBoosters;
    }

    public FactionBoosters getFactionBooster(Faction factio) {
        return this.factionBoosters.get(FactionUtils.getFactionId(factio));
    }

    public void loadActiveBoosters() throws IOException {
        try {
            if (!(new File("plugins/CosmicFactionBoosters")).exists())
                (new File("plugins/CosmicFactionBoosters")).mkdir();
            this.boosterFile = new File("plugins/CosmicFactionBoosters/booster.yml");
            if (!this.boosterFile.exists())
                this.boosterFile.createNewFile();
            this.config = (FileConfiguration)YamlConfiguration.loadConfiguration(this.boosterFile);
            for (String boosterString : this.config.getStringList("active-boosters")) {
                String[] args = boosterString.split(":");
                String factionId = args[0];
                int secondsLeft = Integer.parseInt(args[1]);
                String who = args[2];
                double mult = Double.parseDouble(args[3]);
                long timeApplied = Long.parseLong(args[4]);
                int maxSeconds = Integer.parseInt(args[5]);
                BoosterType type = BoosterType.valueOf(args[6]);
                ActiveBooster booster = new ActiveBooster(who, mult, timeApplied, secondsLeft, maxSeconds, type);
                FactionBoosters boosters = this.factionBoosters.containsKey(factionId) ? this.factionBoosters.get(factionId) : new FactionBoosters();
                if (!this.factionBoosters.containsKey(factionId))
                    this.factionBoosters.put(factionId, boosters);
                boosters.put(type, booster);
            }
        } catch (Throwable $ex) {
            throw $ex;
        }
    }

    public void saveActiveBoosters() {

        this.factionBoosters.forEach((factionId, factionBooster) -> factionBooster.forEach((boosterType, activeBooster) -> {
            final List<String> entries = Lists.newArrayList();
            final String string;
            List<String> list = new ArrayList<>();
            string = factionId + ":" + activeBooster.toString();
            list.add(string);
        }));
        try {
            this.config.save(this.boosterFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private final Material boosterMaterial = Material.EMERALD;

    public ItemStack createBooster(final BoosterType boosterType, final int durationSeconds, final double multiplier) {
        final ItemStack item = ItemBuilder.buildItem(this.boosterMaterial, ChatColor.AQUA + ChatColor.BOLD.toString() + "Faction " + ChatColor.AQUA + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + boosterType.getItemName() + ChatColor.AQUA + ChatColor.BOLD.toString() + " Booster", ChatColor.GRAY + boosterType.getItemDescription(), "", ChatColor.GRAY + "Right-Click to activate.", "", ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "MULTIPLIER", ChatColor.GRAY.toString() + multiplier + "x", "", ChatColor.LIGHT_PURPLE + ChatColor.BOLD.toString() + "DURATION", ChatColor.GRAY + TimeUtil.formatDifference(durationSeconds));
        return item;
    }

    public boolean isBoosterItem(ItemStack item) {
        if (item == null || item.getType() == Material.AIR || item.getType() != this.boosterMaterial)
            return false;
        if (!item.hasItemMeta() || !item.getItemMeta().hasDisplayName() || !item.getItemMeta().hasLore())
            return false;
        ItemMeta im = item.getItemMeta();
        if (im.getDisplayName().contains("Booster") && im.getDisplayName().startsWith(ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + "Faction "))
            return true;
        return false;
    }

    public void showActiveBoosters(Player player, FactionBoosters boosters) {
        player.sendMessage("");
        player.sendMessage(ChatColor.AQUA + ChatColor.BOLD.toString() + "(!) " + ChatColor.AQUA + "Currently Activate Faction Boosters:");
        boosters.forEach((type, activeBooster) -> player.sendMessage(ChatColor.AQUA + ChatColor.BOLD.toString() + "* " + ChatColor.LIGHT_PURPLE + ChatColor.BOLD.toString() + activeBooster.getMultiplier() + "x " + type.getItemName() + ChatColor.AQUA + " started by " + ChatColor.LIGHT_PURPLE + ChatColor.BOLD.toString() + activeBooster.getWhoApplied() + ChatColor.AQUA + " expires in " + ChatColor.LIGHT_PURPLE + ChatColor.BOLD.toString() + activeBooster.getFormattedTimeLeft() + ChatColor.AQUA + "!"));
    }

    public BoosterItemInfo getBoosterItemInfo(ItemStack item) {
        ItemMeta im = item.getItemMeta();
        List<String> lore = im.getLore();
        BoosterType boosterType = BoosterType.fromItemName(ChatColor.stripColor(im.getDisplayName().split("Faction ")[1].split(" Booster")[0]));
        String multiplierStr = lore.get(6);
        double multiplier = Double.parseDouble(ChatColor.stripColor(multiplierStr.split("x")[0]));
        String durationStr = ChatColor.stripColor(lore.get(9));
        if (multiplier > 0.0D && durationStr != null && boosterType != null)
            return new BoosterItemInfo(boosterType, multiplier, durationStr);
        return null;
    }
}
