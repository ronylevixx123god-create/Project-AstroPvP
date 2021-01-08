/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  me.ifamasssxd.cosmicbosses.struct.Boss
 *  me.ifamasssxd.cosmicbosses.struct.enums.BossType
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package com.compilex.CosmicOutposts.cmds;

import com.compilex.CosmicKOTH.CosmicKOTHPlugin;
import com.compilex.CosmicOutposts.outpost.Outpost;
import com.compilex.CosmicOutposts.outpost.enums.OutpostTier;
import com.compilex.CosmicOutposts.utils.LocationUtils;
import me.ifamasssxd.cosmicbosses.struct.Boss;
import me.ifamasssxd.cosmicbosses.struct.enums.BossType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandAoutposts
implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.isOp()) {
            return true;
        }
        if (args[0].equals("set")) {
            Player p = (Player)sender;
            OutpostTier tier = OutpostTier.valueOf(args[1].toUpperCase());
            String configKey = args[2];
            if (configKey.contains("Spawn")) {
                String locationString = LocationUtils.convertLocationToString(p.getLocation());
                CosmicKOTHPlugin.get_().getConfig().set("mainoutposts." + tier.name().toLowerCase() + "." + configKey, locationString);
                sender.sendMessage("[CosmicOutposts] Set outposts." + tier.name().toLowerCase() + "." + configKey + " => " + locationString);
            } else if (configKey.equals("world")) {
                CosmicKOTHPlugin.get_().getConfig().set("mainoutposts." + tier.name().toLowerCase() + "." + configKey, p.getWorld().getName());
            }
            CosmicKOTHPlugin.get_().saveConfig();
        }
        if (args[0].equals("reaper")) {
            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + "(!) " + ChatColor.LIGHT_PURPLE + "A " + ChatColor.UNDERLINE + "Soul Reaper" + ChatColor.LIGHT_PURPLE + " boss is attacking the Cosmonaut /outpost, defeat it for treasure, souls, and soul dust!");
            Bukkit.broadcastMessage("");
            Boss b = BossType.SOUL_REAPER.createInstance(LocationUtils.getNearbyLocation(CosmicKOTHPlugin.get_().getCosmonautOutpost().getDefenderSpawn().clone().subtract(0.0, 8.0, 0.0), 10, 6, 2));
            b.onBossSpawn();
        }
        if (args[0].equals("clear")) {
            OutpostTier tier2 = OutpostTier.valueOf(args[1].toUpperCase());
            Outpost o = null;
            for (Outpost obj : CosmicKOTHPlugin.get_().getOutposts()) {
                if (obj.getOutpostTier() != tier2) continue;
                o = obj;
                break;
            }
            if (o != null) {
                if (o.getControllingFaction() != null) {
                    o.setControllingFaction(null);
                    o.getCapTask().setCapPercent(0.0f);
                    o.setCapTime(System.currentTimeMillis());
                    sender.sendMessage("Cleared controlling faction from the " + tier2.name() + " outpost!");
                } else {
                    sender.sendMessage("No faction is actively controlling the " + tier2.name() + " outpost!");
                }
            }
        }
        return true;
    }
}

