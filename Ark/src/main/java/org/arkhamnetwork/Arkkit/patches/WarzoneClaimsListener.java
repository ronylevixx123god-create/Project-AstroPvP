/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.massivecraft.factions.Board
 *  com.massivecraft.factions.FLocation
 *  com.massivecraft.factions.event.LandClaimEvent
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Chunk
 *  org.bukkit.Location
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.Sound
 *  org.bukkit.World
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.event.LandClaimEvent;
import java.text.DecimalFormat;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.utils.WorldGuardUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

final class WarzoneClaimsListener
implements Listener {
    WarzoneClaimsListener() {
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onLandClaimEvent(LandClaimEvent e) {
        FLocation fl;
        World w = e.getLocation().getWorld();
        int price = this.getClaimPrice(w.getChunkAt((int)(fl = e.getLocation()).getX(), (int)fl.getZ()));
        if (price > 0) {
            Player p = e.getPlayer();
            if (p == null || !p.isOnline()) {
                Bukkit.getLogger().info("[WarzoneClaims] Offline player:" + p.getName() + " attempted to claim warzone chunk at " + (Object)fl);
                e.setCancelled(true);
                return;
            }
            if (p.hasMetadata("warzoneClaim_notEnoughMoney") && System.currentTimeMillis() - ((MetadataValue)p.getMetadata("warzoneClaim_notEnoughMoney").get(0)).asLong() < 2500L) {
                p.sendMessage((Object)ChatColor.RED + "Please wait a moment before attempting to claim this premium chunk again.");
                e.setCancelled(true);
                return;
            }
            DecimalFormat df = new DecimalFormat("#,###");
            try {
                if (!Arkkit.economy.has((OfflinePlayer)p, (double)price)) {
                    e.setCancelled(true);
                    p.sendMessage(ChatColor.RED.toString() + (Object)ChatColor.BOLD + "(!) " + (Object)ChatColor.RED + "You do not have enough $ to claim this premium chunk.");
                    p.sendMessage((Object)ChatColor.GRAY + ChatColor.BOLD.toString() + "COST: " + ChatColor.GRAY.toString() + (Object)ChatColor.UNDERLINE + "$" + df.format(price));
                    p.setMetadata("warzoneClaim_notEnoughMoney", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)System.currentTimeMillis()));
                    return;
                }
                if (p.hasMetadata("warzoneClaim_confirmId") && ((MetadataValue)p.getMetadata("warzoneClaim_confirmId").get(0)).asString().equals(fl.getX() + "," + fl.getZ())) {
                    p.removeMetadata("warzoneClaim_confirmId", (Plugin)Arkkit.get());
                    Arkkit.economy.withdrawPlayer((OfflinePlayer)p, (double)price);
                    p.sendMessage(ChatColor.RED.toString() + (Object)ChatColor.BOLD + "- $" + df.format(price));
                    p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1.0f, 0.6f);
                    Bukkit.getLogger().info("[Arkkit (WarzoneClaims)] Player " + p.getName() + " (" + p.getUniqueId() + ") has claimed premium chunk " + fl.getX() + "," + fl.getZ() + " for " + e.getFactionTag() + " for $" + price);
                    return;
                }
                p.setMetadata("warzoneClaim_confirmId", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)(fl.getX() + "," + fl.getZ())));
                p.sendMessage(ChatColor.RED.toString() + (Object)ChatColor.BOLD + "(!)" + (Object)ChatColor.RED + " Due to this chunk(" + fl.getX() + "," + fl.getZ() + ")'s distance to a Warzone, it will cost $" + price + " to claim.");
                p.sendMessage((Object)ChatColor.GRAY + "To confirm this action, simply re-run your " + (Object)ChatColor.RED + "/f claim" + (Object)ChatColor.GRAY + " command.");
                e.setCancelled(true);
            }
            catch (NullPointerException npe) {
                npe.printStackTrace();
                e.setCancelled(true);
            }
        }
    }

    private int getClaimPrice(Chunk c) {
        String[] s;
        String regionName;
        Chunk ch;
        Location l;
        FLocation fl;
        int z;
        int x;
        for (x = -1; x <= 1; ++x) {
            for (z = -1; z <= 1; ++z) {
                if (x == 0 && z == 0 || !Board.getFactionAt((FLocation)(fl = new FLocation(l = (ch = c.getWorld().getChunkAt(c.getX() + x, c.getZ() + z)).getBlock(8, 8, 8).getLocation()))).isWarZone() || WorldGuardUtils.isPvPDisabled(l) || !(regionName = WorldGuardUtils.getRegionName(l)).contains("_")) continue;
                s = regionName.split("_");
                try {
                    int price = Integer.parseInt(s[s.length - 1]);
                    return price *= 2;
                }
                catch (NumberFormatException nfe) {
                    Bukkit.getLogger().info("[Arkkit (WarzoneClaims)] Failed to resolve price of faction claim for region " + regionName + "!");
                }
            }
        }
        for (x = -2; x <= 2; ++x) {
            for (z = -2; z <= 2; ++z) {
                if (x == 0 && z == 0 || !Board.getFactionAt((FLocation)(fl = new FLocation(l = (ch = c.getWorld().getChunkAt(c.getX() + x, c.getZ() + z)).getBlock(8, 8, 8).getLocation()))).isWarZone() || WorldGuardUtils.isPvPDisabled(l) || !(regionName = WorldGuardUtils.getRegionName(l)).contains("_")) continue;
                s = regionName.split("_");
                try {
                    int price = Integer.parseInt(s[s.length - 1]);
                    price = (int)((double)price * 1.5);
                    return price;
                }
                catch (NumberFormatException nfe) {
                    Bukkit.getLogger().info("[Arkkit (WarzoneClaims)] Failed to resolve price of faction claim for region " + regionName + "!");
                }
            }
        }
        return 0;
    }
}

