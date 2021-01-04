/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.FactionUtils
 *  com.cosmicpvp.cosmicutils.utils.LocationUtils
 *  com.massivecraft.factions.Faction
 *  org.arkhamnetwork.Arkkit.patches.CombatLog
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.event.player.PlayerTeleportEvent$TeleportCause
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package com.massivecraft.factions.cmd;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import com.cosmicpvp.cosmicutils.utils.LocationUtils;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.P;
import com.massivecraft.factions.manager.BannerManager;
import com.massivecraft.factions.struct.FactionBanner;
import org.arkhamnetwork.Arkkit.patches.CombatLog;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class CommandRecall
implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player)sender;
        Faction faction = FactionUtils.getFaction((Player)player, (boolean)false);
        if (CombatLog.inCombat((Player)((Player)sender))) {
            player.sendMessage(CC.translate((String)"&cYou Cannot use /f assist on combat"));
            return true;
        }
        if (!FactionUtils.isNormal((Faction)faction)) {
            sender.sendMessage(CC.RedB + "(!) " + CC.Red + "You must be in a faction to teleport to a /f banner!");
            return true;
        }
        BannerManager manager = P.getP().getBannerManager();
        FactionBanner banner = manager.getFactionBannerMap().get(faction.getId());
        if (banner == null || banner.getActiveLocation() == null || banner.getPlaceTime() == 0L) {
            sender.sendMessage(CC.RedB + "(!) " + CC.Red + "Your faction does not have an active /f banner placed!");
            return true;
        }
        if (banner.hasExpired()) {
            sender.sendMessage(CC.RedB + "(!) " + CC.Red + "Your faction does not have an active Faction Banner!");
            sender.sendMessage(CC.Gray + "Your last /f banner was placed > " + FactionBanner.secondCooldown + "s ago!");
            return true;
        }
        player.sendMessage(CC.DarkPurpleB + "(!) " + CC.DarkPurple + "Teleporting to " + banner.getWhoPlacedUsername() + "'s /f banner at: ");
        player.sendMessage(CC.LightPurple + LocationUtils.printPretty((Location)banner.getActiveLocation(), (ChatColor)ChatColor.LIGHT_PURPLE, (boolean)true) + "!");
        player.setMetadata("bannerTp", (MetadataValue)new FixedMetadataValue(P.getP(), (Object)banner.getActiveLocation().getBlock().getLocation().add(0.5, 0.0, 0.5)));
        player.teleport(banner.getActiveLocation().getBlock().getLocation().add(0.5, 0.0, 0.5), PlayerTeleportEvent.TeleportCause.PLUGIN);
        return false;
    }
}

