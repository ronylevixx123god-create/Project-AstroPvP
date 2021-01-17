/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.CosmicUtils
 *  com.google.common.collect.Lists
 *  org.arkhamnetwork.Arkkit.plugins.ArkPlugins
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.entity.Player
 */
package me.ifamasssxd.help.commands;

import com.cosmicpvp.cosmicutils.CosmicUtils;
import com.google.common.collect.Lists;
import me.ifamasssxd.help.HelpItem;
import me.ifamasssxd.help.json.JSONMessage;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CommandHelp {
    private List<HelpItem> helpList = new ArrayList<HelpItem>();
    boolean castle = false;
    boolean hardCoreFactions = false;
    boolean factionPointsEnabled = false;
    boolean classesEnabled = false;
    boolean hcfCrateEnabled = false;
    boolean hcfOutpostsEnabled = false;
    boolean playerQuestsEnabled = false;
    boolean masksEnabled = false;
    boolean armorSetsEnabled = false;
    boolean dungeonsEnabled = false;
    boolean lootboxesEnabled = false;
    boolean jackpotEnabled = false;
    boolean enchantsEnabled = false;

    public CommandHelp() {
        if (new File("plugins/CosmicGlobalKits_Vanilla.jar").exists()) {
            this.castle = true;
        }
        if (Bukkit.getPluginManager().isPluginEnabled("EnchantmentAPI")) {
            this.enchantsEnabled = true;
        }
        if (new File("plugins/HCFactions.jar").exists()) {
            this.hardCoreFactions = true;
        }
        if (new File("plugins/CosmicFactionPoints.jar").exists()) {
            this.factionPointsEnabled = true;
        }
        if (new File("plugins/CosmicClasses.jar").exists()) {
            this.classesEnabled = true;
        }
        if (new File("plugins/CosmicHCFCrate.jar").exists()) {
            this.hcfCrateEnabled = true;
        }
        if (new File("plugins/CosmicHCFOutposts.jar").exists()) {
            this.hcfOutpostsEnabled = true;
        }
        if (new File("plugins/CosmicPlayerQuests.jar").exists()) {
            this.playerQuestsEnabled = true;
        }
        if (new File("plugins/CosmicMasks.jar").exists()) {
            this.masksEnabled = true;
        }
        if (new File("plugins/CosmicArmorSets.jar").exists()) {
            this.armorSetsEnabled = true;
        }
        if (new File("plugins/CosmicDungeons.jar").exists()) {
            this.dungeonsEnabled = true;
        }
        if (new File("plugins/CosmicMysteryChest.jar").exists()) {
            this.lootboxesEnabled = true;
        }
        if (new File("plugins/CosmicJackpot.jar").exists()) {
            this.jackpotEnabled = true;
        }
        if (this.hardCoreFactions) {
            this.helpList.add(new HelpItem("HCF", "/help hcf"));
            this.helpList.add(new HelpItem("Factions", "/f help"));
            this.helpList.add(new HelpItem("Kits", "/kit"));
            this.helpList.add(new HelpItem("Classes", "/help classes"));
            this.helpList.add(new HelpItem("Crates", "/help crates"));
            this.helpList.add(new HelpItem("PvP Timer", "/pvptimer help"));
            this.helpList.add(new HelpItem("Deathban", "/deathban"));
            this.helpList.add(new HelpItem("Challenges", "/challenges info"));
            this.helpList.add(new HelpItem("Claiming", "/help claiming"));
            this.helpList.add(new HelpItem("Nether", "/help nether"));
            this.helpList.add(new HelpItem("The End", "/help end"));
            this.helpList.add(new HelpItem("Outposts", "/outposts menu"));
            this.helpList.add(new HelpItem("KOTH", "/koth menu"));
            this.helpList.add(new HelpItem("Stronghold", "/help stronghold"));
            this.helpList.add(new HelpItem("Treasure Drops", "/help treasuredrops"));
            this.helpList.add(new HelpItem("Event Trophies", "/help trophies"));
            this.helpList.add(new HelpItem("Reporting", "/report"));
            this.helpList.add(new HelpItem("Wiki", "/wiki"));
            this.helpList.add(new HelpItem("Rules", "/rules"));
            this.helpList.add(new HelpItem("Forums", "/forums"));
            this.helpList.add(new HelpItem("TeamSpeak", "/help teamspeak"));
        } else {
            this.helpList.add(new HelpItem("Factions", "/f help"));
            if (this.factionPointsEnabled) {
                this.helpList.add(new HelpItem("Faction Points", "/fpoints help"));
            }
            this.helpList.add(new HelpItem("mcMMO", "/mcmmo help"));
            this.helpList.add(new HelpItem("Essentials", "/help essentials"));
            this.helpList.add(new HelpItem("Wiki", "/wiki"));
            this.helpList.add(new HelpItem("Warps", "/warp"));
            if (this.enchantsEnabled) {
                this.helpList.add(new HelpItem("Enchantments", "/help enchants 1"));
            }
            if (this.classesEnabled) {
                this.helpList.add(new HelpItem("Classes", "/help classes"));
            }
            if (this.hcfCrateEnabled) {
                this.helpList.add(new HelpItem("Crates", "/help crates"));
            }
            if (this.hcfOutpostsEnabled) {
                this.helpList.add(new HelpItem("Strongholds", "/help strongholds"));
            }
            this.helpList.add(new HelpItem("Kits", "/kit"));
            if (this.lootboxesEnabled) {
                this.helpList.add(new HelpItem("Lootboxes", "/help lootbox"));
            }
            if (this.armorSetsEnabled) {
                this.helpList.add(new HelpItem("Armor Sets", "/help armorset"));
            }
            this.helpList.add(new HelpItem("Trading", "/trade"));
            this.helpList.add(new HelpItem("Reporting", "/report"));
            this.helpList.add(new HelpItem("Shop", "/shop"));
            this.helpList.add(new HelpItem("Auction", "/ah help"));
            this.helpList.add(new HelpItem("Vaults", "/pv"));
            if (!this.castle) {
                this.helpList.add(new HelpItem("Envoys", "/envoy"));
            }
            this.helpList.add(new HelpItem("KOTH", "/koth"));
            /*if (CosmicUtils.get().isArkkitEnabled() && ArkPlugins.END_GRINDING.getArkPlugin().isRunning()) {
                this.helpList.add(new HelpItem("Ender", "/help end"));
            }*/
            this.helpList.add(new HelpItem("Wild PvP", "/wildpvp"));
            if (CosmicUtils.get().isArkkitEnabled() && ArkPlugins.WARZONE_MONEY.getArkPlugin().isRunning()) {
                this.helpList.add(new HelpItem("LMS", "/lms"));
            }
            this.helpList.add(new HelpItem("Outposts", "/outposts"));
            if (!this.castle) {
                this.helpList.add(new HelpItem("Conquests", "/conquests"));
            }
            if (this.dungeonsEnabled) {
                this.helpList.add(new HelpItem("Dungeons", "/dungeon"));
            }
            this.helpList.add(new HelpItem("Duels", "/duel help"));
            this.helpList.add(new HelpItem("Bounty", "/bounty"));
            if (this.playerQuestsEnabled) {
                this.helpList.add(new HelpItem("Player Quests", "/help quests"));
            }
            this.helpList.add(new HelpItem("Faction Quests", "/f quests"));
            if (this.jackpotEnabled) {
                this.helpList.add(new HelpItem("Jackpot", "/jackpot"));
            }
            this.helpList.add(new HelpItem("Challenges", "/challenge"));
            this.helpList.add(new HelpItem("Disguises", "/disguises"));
            if (this.masksEnabled) {
                this.helpList.add(new HelpItem("Masks", "/help mask"));
            }
            this.helpList.add(new HelpItem("Inventory Pets", "/pet help"));
            this.helpList.add(new HelpItem("Titles", "/title"));
            this.helpList.add(new HelpItem("Item Filter", "/filter"));
            this.helpList.add(new HelpItem("Showcase", "/showcase"));
            if (!this.castle) {
                this.helpList.add(new HelpItem("Crates", "/cc"));
                this.helpList.add(new HelpItem("Tinkerer", "/tinkerer"));
                this.helpList.add(new HelpItem("Enchanter", "/enchanter"));
                this.helpList.add(new HelpItem("Alchemist", "/alchemist"));
            }
            this.helpList.add(new HelpItem("Blocking", "/block"));
            this.helpList.add(new HelpItem("Toggles", "/toggles"));
            this.helpList.add(new HelpItem("Item Linking", "/help itemlinking"));
            this.helpList.add(new HelpItem("Playtime", "/playtime"));
            this.helpList.add(new HelpItem("Staff", "/staff"));
            this.helpList.add(new HelpItem("Store", "/buylink"));
            this.helpList.add(new HelpItem("Forums", "/forums"));
        }
    }

    public boolean onCommand(Player player, int page) {
        int startIndex;
        ArrayList rainbow = Lists.newArrayList((Object[])new ChatColor[]{ChatColor.RED, ChatColor.GOLD, ChatColor.YELLOW, ChatColor.GREEN, ChatColor.AQUA, ChatColor.DARK_AQUA, ChatColor.BLUE, ChatColor.DARK_PURPLE, ChatColor.LIGHT_PURPLE});
        int perPage = this.castle ? 8 : 9;
        int maxPages = (int)Math.ceil(Double.valueOf(this.helpList.size()) / Double.valueOf(perPage));
        if (page > maxPages) {
            page = maxPages;
        }
        int n = startIndex = page == 1 ? 0 : (page - 1) * perPage;
        if (startIndex > this.helpList.size()) {
            startIndex = 0;
        }
        player.sendMessage("");
        player.sendMessage((Object)ChatColor.AQUA + ChatColor.BOLD.toString() + "Astro" + (this.hardCoreFactions ? "HCF" : "PvP") + " Tutorial " + (Object)ChatColor.AQUA + ChatColor.BOLD.toString() + "(" + (Object)ChatColor.LIGHT_PURPLE + ChatColor.BOLD.toString() + page + (Object)ChatColor.AQUA + ChatColor.BOLD.toString() + "/" + (Object)ChatColor.LIGHT_PURPLE + ChatColor.BOLD.toString() + maxPages + (Object)ChatColor.AQUA + ChatColor.BOLD.toString() + ")");
        int colorIndex = 0;
        for (int index = startIndex; index < startIndex + perPage; ++index) {
            if (colorIndex > rainbow.size()) {
                colorIndex = 0;
            }
            if (index >= this.helpList.size()) continue;
            HelpItem item = this.helpList.get(index);
            JSONMessage message = new JSONMessage("  ");
            ChatColor color = (ChatColor)rainbow.get(colorIndex++);
            if (item.getHoverCommand().startsWith("/")) {
                message.addRunCommand((Object)color + ChatColor.BOLD.toString() + item.getName(), color, item.getHoverCommand(), (Object)color + item.getHoverCommand());
            } else {
                message.addHoverData((Object)color + ChatColor.BOLD.toString() + item.getName(), item.getHoverCommand());
            }
            message.sendToPlayer(player);
        }
        player.sendMessage((Object)ChatColor.YELLOW + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.YELLOW + "Click a category to get more information.");
        player.sendMessage("");
        return false;
    }

    public boolean isCastle() {
        return this.castle;
    }

    public boolean isHardCoreFactions() {
        return this.hardCoreFactions;
    }

    public boolean isFactionPointsEnabled() {
        return this.factionPointsEnabled;
    }

    public boolean isClassesEnabled() {
        return this.classesEnabled;
    }

    public boolean isHcfCrateEnabled() {
        return this.hcfCrateEnabled;
    }

    public boolean isHcfOutpostsEnabled() {
        return this.hcfOutpostsEnabled;
    }

    public boolean isPlayerQuestsEnabled() {
        return this.playerQuestsEnabled;
    }

    public boolean isMasksEnabled() {
        return this.masksEnabled;
    }

    public boolean isArmorSetsEnabled() {
        return this.armorSetsEnabled;
    }

    public boolean isDungeonsEnabled() {
        return this.dungeonsEnabled;
    }

    public boolean isLootboxesEnabled() {
        return this.lootboxesEnabled;
    }

    public boolean isJackpotEnabled() {
        return this.jackpotEnabled;
    }

    public boolean isEnchantsEnabled() {
        return this.enchantsEnabled;
    }
}

