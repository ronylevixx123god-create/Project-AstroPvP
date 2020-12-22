/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.LocationUtils
 *  com.cosmicpvp.cosmicutils.utils.NumberUtils
 *  me.ifamasssxd.cosmicheroicenvoys.CosmicHeroicEnvoys
 *  org.arkhamnetwork.Arkkit.utils.TimeUtils
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Sound
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package com.cosmicpvp.CosmicSpaceChests.cmds;

import com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsAPI;
import com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsPlugin;
import com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity;
import com.cosmicpvp.CosmicSpaceChests.tasks.CosmicEnvoyTask;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.LocationUtils;
import com.cosmicpvp.cosmicutils.utils.NumberUtils;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import me.ifamasssxd.cosmicheroicenvoys.CosmicHeroicEnvoys;
import org.arkhamnetwork.Arkkit.utils.TimeUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandEnvoy
implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.isOp() && args.length >= 1 && args[0].equalsIgnoreCase("summon")) {
            if (args.length >= 2) {
                Location loc = LocationUtils.getLocationFromString((String)args[1], (boolean)false);
                if (loc == null) {
                    sender.sendMessage(CC.Red + "Invalid location given! Format: world:x:y:z");
                    return true;
                }
                ItemRarity rarity = null;
                if (args.length >= 3) {
                    try {
                        rarity = ItemRarity.valueOf(args[2]);
                    }
                    catch (Exception e) {
                        sender.sendMessage(CC.Red + "Invalid rarity given: " + Arrays.toString((Object[])ItemRarity.values()));
                        return true;
                    }
                }
                Integer toSpawn = ThreadLocalRandom.current().nextInt(7) + 3;
                if (args.length >= 4 && (toSpawn = NumberUtils.parseIntOrNull((String)args[3])) == null) {
                    sender.sendMessage(CC.Red + "Invalid spawn amount entered: " + args[3]);
                    return true;
                }
                Integer radius = 8;
                if (args.length >= 5 && (radius = NumberUtils.parseIntOrNull((String)args[4])) == null) {
                    sender.sendMessage(CC.Red + "Invalid radius entered: " + args[4]);
                    return true;
                }
                CosmicSpaceChestsAPI.spawnCustomEnvoy(loc, radius, toSpawn, rarity);
                sender.sendMessage(CC.Green + "Spawning envoy at " + args[1] + ", chestRarity=" + radius + " spawnAmount=" + toSpawn + ", radius=" + radius);
                return true;
            }
            sender.sendMessage(CC.Red + "Invalid arguments, EX: /envoy summon world:x:y:z [rarity] [amount] [radiusFromCenter]");
            return true;
        }
        if (cmd.getName().equalsIgnoreCase("envoy")) {
            if (args.length == 1 && args[0].equalsIgnoreCase("help")) {
                sender.sendMessage("");
                sender.sendMessage((Object)ChatColor.AQUA + "" + (Object)ChatColor.BOLD + "COSMIC ENVOY HELP");
                sender.sendMessage((Object)ChatColor.GRAY + "Cosmic Envoys occur every " + (Object)ChatColor.UNDERLINE + "25-40 minutes." + (Object)ChatColor.GRAY + " Chests filled with random tier loot spawn at " + (Object)ChatColor.AQUA + "50-100 random locations" + (Object)ChatColor.GRAY + " throughout the planet's warzone and warps. More chests spawn wherever there is a higher density of players.");
                sender.sendMessage((Object)ChatColor.AQUA + "* " + (Object)ChatColor.ITALIC + "Cosmic Envoys are a great way for new players to get some starting loot.");
                sender.sendMessage("");
                return true;
            }
            if (!sender.hasPermission("cosmicpvp.command.envoy")) {
                sender.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!)" + (Object)ChatColor.RED + " Only the highest tier ranks of each planet may use the /envoy command.");
                sender.sendMessage((Object)ChatColor.GRAY + "Unlock access today with a rank quest at " + ChatColor.RED.toString() + "buy.cosmicpvp.com");
                return true;
            }
            sender.sendMessage(ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + "(!) PREVIOUS COSMIC ENVOY: ");
            sender.sendMessage(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "    " + CosmicEnvoyTask.getPreviousEnvoyCmdSpawnString() + " ago");
            sender.sendMessage(ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + "** PREVIOUS ENVOY BOSS: ");
            sender.sendMessage(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "    " + TimeUtils.formatDifference((long)((System.currentTimeMillis() - CosmicHeroicEnvoys.get().getEnvoyManager().getLastBossSpawn()) / 1000L)) + " ago");
            if (sender instanceof Player) {
                Player p = (Player)sender;
                p.playSound(p.getLocation(), Sound.BAT_TAKEOFF, 0.8f, 1.3f);
            }
            if (args.length != 0) {
                if (sender instanceof Player && !((Player)sender).isOp()) {
                    return true;
                }
                if (args[0].equalsIgnoreCase("hologram")) {
                    Location l = ((Player)sender).getLocation().add(0.0, 2.0, 0.0);
                    CosmicSpaceChestsPlugin.getHologramTask().setHologramLocation(l);
                    return true;
                }
                if (args[0].equalsIgnoreCase("disable")) {
                    CosmicSpaceChestsPlugin.getHologramTask().disableHologram();
                }
                if (args[0].equalsIgnoreCase("enable")) {
                    CosmicSpaceChestsPlugin.getHologramTask().enableHologram();
                }
            }
        }
        return true;
    }
}

