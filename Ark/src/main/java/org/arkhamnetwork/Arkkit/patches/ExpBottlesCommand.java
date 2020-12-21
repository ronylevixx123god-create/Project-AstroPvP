/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.compilex.CosmicOutposts.CosmicOutpostsAPI
 *  com.massivecraft.factions.Board
 *  com.massivecraft.factions.FLocation
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.GameMode
 *  org.bukkit.Sound
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import com.compilex.CosmicOutposts.CosmicOutpostsAPI;
import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.arkhamnetwork.Arkkit.libs.ExpUtils;
import org.arkhamnetwork.Arkkit.patches.ExpBottles;
import org.arkhamnetwork.Arkkit.utils.WorldGuardUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

final class ExpBottlesCommand
extends AbstractCommand {
    public ExpBottlesCommand(String command, String usage, String description, List<String> aliases) {
        super(command, usage, description, aliases);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p;
        Player player = p = sender instanceof Player ? (Player)sender : null;
        if (p == null) {
            return false;
        }
        if (args.length != 1) {
            p.sendMessage((Object)ChatColor.YELLOW + "/xpbottle <amount>");
            p.sendMessage((Object)ChatColor.GRAY + "Converts <amount> xp into bottle form.");
            p.sendMessage((Object)ChatColor.GRAY + "Use " + (Object)ChatColor.YELLOW + "/xp" + (Object)ChatColor.GRAY + " to view your current exp points.");
            return true;
        }
        if (!ExpBottlesCommand.canMakeXPBottle(p)) {
            boolean noLimit = false;
            if (p.hasPermission("arkkit.xpbottles.immune")) {
                if (p.getName().equals("Woofless") || p.getName().equals("TBNRfrags")) {
                    if (p.getGameMode() == GameMode.CREATIVE) {
                        noLimit = true;
                    }
                } else {
                    noLimit = true;
                }
            }
            if (Bukkit.getPluginManager().isPluginEnabled("CosmicOutposts") && CosmicOutpostsAPI.controlsHeroOutpost((Player)p)) {
                p.sendMessage(ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.GREEN + "No XP Bottle Delay " + (Object)ChatColor.GRAY + "[Hero Outpost Perk]");
                noLimit = true;
            }
            if (!noLimit) {
                p.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.RED + "You cannot create another XP Bottle for " + ExpBottlesCommand.getTimeString(((MetadataValue)p.getMetadata("nextExpBottle").get(0)).asLong() - System.currentTimeMillis()));
                if (!p.hasPermission("playervaults.amount.15")) {
                    p.sendMessage((Object)ChatColor.GRAY + "Complete a Rank Quest from buy.cosmicpvp.com to decrease this delay.");
                } else if (!p.hasPermission("cosmicpvp.heroic")) {
                    p.sendMessage((Object)ChatColor.GRAY + "Purchase a Heroic Rank Crystal at buy.cosmicpvp.com to decrease this delay.");
                }
                return true;
            }
        }
        int amount = 0;
        if (args[0].equalsIgnoreCase("all")) {
            amount = ExpUtils.getTotalExperience(p);
        } else {
            try {
                amount = Integer.parseInt(args[0]);
            }
            catch (NumberFormatException nfe) {
                p.sendMessage((Object)ChatColor.RED + "Invalid <amount> specified.");
                return true;
            }
        }
        if (amount < 25) {
            p.sendMessage((Object)ChatColor.RED + "You must withdraw at least 25XP points per bottle.");
            return true;
        }
        int currentXp = ExpUtils.getTotalExperience(p);
        if (currentXp < amount) {
            p.sendMessage((Object)ChatColor.RED + "You only have " + currentXp + " xp points to convert.");
            p.sendMessage((Object)ChatColor.GRAY + "View your exp values with /xp");
            return true;
        }
        if (p.getInventory().firstEmpty() == -1) {
            p.sendMessage((Object)ChatColor.RED + "You do not have any inventory space.");
            return true;
        }
        FLocation pLoc = new FLocation(p.getLocation());
        if (Board.getFactionAt((FLocation)pLoc).isWarZone() && !WorldGuardUtils.getRegionName(p.getLocation()).startsWith("spawn_")) {
            p.sendMessage((Object)ChatColor.RED + "You " + (Object)ChatColor.UNDERLINE + "cannot" + (Object)ChatColor.RED + " bottle EXP in Warzones.");
            return true;
        }
        ExpUtils.setTotalExperience(p, currentXp - amount);
        p.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "- " + amount + " xp");
        p.sendMessage((Object)ChatColor.YELLOW + "You are now afflicted with " + (Object)ChatColor.UNDERLINE + "EXP Exhaustion" + (Object)ChatColor.YELLOW + " for " + this.getExpBottleCreationDelayMinutes(p) + " minute(s).");
        p.sendMessage((Object)ChatColor.YELLOW + "You " + (Object)ChatColor.UNDERLINE + "cannot" + (Object)ChatColor.YELLOW + " use /xpbottle or teleport while EXP Exhausted.");
        p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1.0f, 0.2f);
        p.getInventory().addItem(new ItemStack[]{ExpBottles.getXpBottle(amount, p.getName())});
        p.setMetadata("nextExpBottle", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)(System.currentTimeMillis() + (long)((int)Math.round(this.getExpBottleCreationDelayMinutes(p) * 60.0 * 1000.0)))));
        return true;
    }

    protected static boolean canMakeXPBottle(Player p) {
        return !p.hasMetadata("nextExpBottle") || System.currentTimeMillis() >= ((MetadataValue)p.getMetadata("nextExpBottle").get(0)).asLong();
    }

    private double getExpBottleCreationDelayMinutes(Player p) {
        if (p.hasPermission("cosmicpvp.heroic")) {
            return 3.0;
        }
        if (p.hasPermission("playervaults.amount.15") || p.hasPermission("enchantments.max.20")) {
            return 7.0;
        }
        if (p.hasPermission("playervaults.amount.12")) {
            return 8.0;
        }
        if (p.hasPermission("playervaults.amount.9")) {
            return 11.0;
        }
        if (p.hasPermission("playervaults.amount.6")) {
            return 12.0;
        }
        if (p.hasPermission("playervaults.amount.3")) {
            return 15.0;
        }
        return 17.0;
    }

    protected static String getTimeString(long millis) {
        return String.format("%02dm %02ds", TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
    }
}

