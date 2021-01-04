/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.JSONMessage
 *  com.cosmicpvp.cosmicutils.utils.LocationUtils
 *  com.cosmicpvp.cosmicutils.utils.StringUtils
 *  com.massivecraft.factions.FPlayer
 *  com.massivecraft.factions.Faction
 *  org.arkhamnetwork.Arkkit.patches.TPWarmup
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 */
package com.massivecraft.factions.struct;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.JSONMessage;
import com.cosmicpvp.cosmicutils.utils.LocationUtils;
import com.cosmicpvp.cosmicutils.utils.StringUtils;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.P;
import org.arkhamnetwork.Arkkit.patches.TPWarmup;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class FactionBanner {
    public static int secondCooldown = 10;
    public static long expired = TimeUnit.SECONDS.toMillis(secondCooldown);
    private UUID whoPlacedBanner;
    private String whoPlacedUsername;
    private Location activeLocation;
    private Faction faction;
    private long placeTime;

    public void removeBanner() {
        if (this.activeLocation == null) {
            return;
        }
        Block block = this.activeLocation.getBlock();
        if (block == null) {
            return;
        }
        Material type = block.getType();
        if (type == Material.WALL_BANNER || type == Material.STANDING_BANNER) {
            Player online;
            block.setType(Material.AIR);
            if (this.whoPlacedBanner != null && (online = Bukkit.getPlayer((UUID)this.whoPlacedBanner)) != null) {
                online.sendMessage(CC.RedB + "(!) " + CC.Red + "Your /f banner has expired or been destroyed!");
                online.playSound(online.getLocation(), Sound.PORTAL, 1.0f, 1.4f);
            }
            for (Player p : this.faction.getOnlinePlayers()) {
                if (!p.hasMetadata("bannerTp")) continue;
                p.removeMetadata("bannerTp", P.getP());
                TPWarmup.cancelQueuedTeleport((Player)p);
                p.sendMessage(CC.DarkPurpleB + "[/f banner]: " + CC.Red + "Your faction banner was destroyed, /f assist teleport cancelled!");
            }
        }
        this.activeLocation = null;
    }

    public int getSecondsLeft() {
        long time = System.currentTimeMillis() - this.placeTime;
        return (int)Math.ceil((expired - time) / 1000L);
    }

    public void placeBanner(Faction faction, Player whoPlaced, Location location) {
        if (this.activeLocation != null) {
            this.removeBanner();
        }
        this.whoPlacedBanner = whoPlaced.getUniqueId();
        this.whoPlacedUsername = whoPlaced.getName();
        this.activeLocation = location;
        this.placeTime = System.currentTimeMillis();
        this.faction = faction;
        JSONMessage msg = new JSONMessage("");
        msg.addText((Player)null, CC.Gray + "          Click ", ChatColor.GRAY);
        msg.addRunCommand(CC.GrayB + CC.Underline + "HERE", ChatColor.GRAY, "/frecall", CC.Gray + "Click to teleport to " + this.whoPlacedUsername + "'s banner!");
        msg.addText((Player)null, CC.Gray + " or use " + CC.DarkPurple + "/f assist" + CC.Gray + " to teleport!", ChatColor.GRAY);
        for (FPlayer player : faction.getFPlayers()) {
            if (!player.isCachedAsOnline()) continue;
            Player pl = player.getPlayer();
            pl.sendMessage("");
            pl.sendMessage(StringUtils.getCenteredMessage((String)(CC.DarkPurpleB + "[/f banner]: " + CC.LightPurple + this.whoPlacedUsername + ", at: " + LocationUtils.printPretty((Location)this.activeLocation, (ChatColor)ChatColor.DARK_PURPLE, (boolean)false))));
            msg.sendToPlayer(pl);
            pl.sendMessage("");
        }
    }

    public boolean hasExpired() {
        return this.placeTime > 0L && System.currentTimeMillis() - this.placeTime >= expired;
    }

    public UUID getWhoPlacedBanner() {
        return this.whoPlacedBanner;
    }

    public String getWhoPlacedUsername() {
        return this.whoPlacedUsername;
    }

    public Location getActiveLocation() {
        return this.activeLocation;
    }

    public Faction getFaction() {
        return this.faction;
    }

    public long getPlaceTime() {
        return this.placeTime;
    }
}

