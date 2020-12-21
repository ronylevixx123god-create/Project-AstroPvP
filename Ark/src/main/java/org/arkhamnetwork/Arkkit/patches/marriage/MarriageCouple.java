/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.entity.Player
 */
package org.arkhamnetwork.Arkkit.patches.marriage;

import java.util.UUID;
import org.arkhamnetwork.Arkkit.patches.marriage.Marriage;
import org.arkhamnetwork.Arkkit.patches.marriage.MarriageDataManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MarriageCouple {
    private UUID p1;
    private UUID p2;

    public MarriageCouple(UUID partner1, UUID partner2) {
        this.p1 = partner1;
        this.p2 = partner2;
        if (this.marriagePossible()) {
            MarriageDataManager.createMarriage(this.p1, this.p2);
            Bukkit.getServer().broadcastMessage(Marriage.prefix + (Object)ChatColor.YELLOW + (Object)ChatColor.UNDERLINE + Bukkit.getPlayer((UUID)this.p1).getName() + (Object)ChatColor.GRAY + " and " + (Object)ChatColor.YELLOW + (Object)ChatColor.UNDERLINE + Bukkit.getPlayer((UUID)this.p2).getName() + (Object)ChatColor.GRAY + " are now married!");
        }
    }

    public boolean marriagePossible() {
        if (this.p1 == null || this.p2 == null) {
            return false;
        }
        if (!MarriageDataManager.canMarry(this.p1) || !MarriageDataManager.canMarry(this.p2)) {
            if (Bukkit.getPlayer((UUID)this.p1) != null) {
                Player pl1 = Bukkit.getPlayer((UUID)this.p1);
                pl1.sendMessage(Marriage.prefix + (Object)ChatColor.GRAY + "You and your partner " + (Object)ChatColor.RED + (Object)ChatColor.UNDERLINE + "cannot" + (Object)ChatColor.GRAY + " marry. You, your partner, or both of you do not have a donation rank.");
                pl1.sendMessage(Marriage.prefix + (Object)ChatColor.GRAY + "In order to marry, you and/or your partner must buy a global rank at " + (Object)ChatColor.AQUA + (Object)ChatColor.UNDERLINE + "buy.arkhamnetwork.org");
            }
            if (Bukkit.getPlayer((UUID)this.p2) != null) {
                Player pl2 = Bukkit.getPlayer((UUID)this.p2);
                pl2.sendMessage(Marriage.prefix + (Object)ChatColor.GRAY + "You and your partner " + (Object)ChatColor.RED + (Object)ChatColor.UNDERLINE + "cannot" + (Object)ChatColor.GRAY + " marry. You, your partner, or both of you do not have a donation rank.");
                pl2.sendMessage(Marriage.prefix + (Object)ChatColor.GRAY + "In order to marry, you and/or your partner must buy a global rank at " + (Object)ChatColor.AQUA + (Object)ChatColor.UNDERLINE + "buy.arkhamnetwork.org");
            }
            return false;
        }
        if (MarriageDataManager.getPartnerUUID(this.p1) != null) {
            if (Bukkit.getPlayer((UUID)this.p1) != null) {
                Bukkit.getPlayer((UUID)this.p1).sendMessage(Marriage.prefix + (Object)ChatColor.GRAY + "You " + (Object)ChatColor.UNDERLINE + "cannot" + (Object)ChatColor.GRAY + " marry someone if you are already married. Do " + (Object)ChatColor.YELLOW + "/marry divorce" + (Object)ChatColor.GRAY + " if you wish to divorce with your current partner.");
            }
            return false;
        }
        if (MarriageDataManager.getPartnerUUID(this.p2) != null) {
            if (Bukkit.getPlayer((UUID)this.p2) != null) {
                Bukkit.getPlayer((UUID)this.p2).sendMessage(Marriage.prefix + (Object)ChatColor.GRAY + "You " + (Object)ChatColor.UNDERLINE + "cannot" + (Object)ChatColor.GRAY + " marry someone if you are already married. Do " + (Object)ChatColor.YELLOW + "/marry divorce" + (Object)ChatColor.GRAY + " if you wish to divorce with your current partner.");
            }
            return false;
        }
        if (this.p1 == this.p2) {
            Bukkit.getPlayer((UUID)this.p1).sendMessage(Marriage.prefix + (Object)ChatColor.GRAY + "You " + (Object)ChatColor.RED + (Object)ChatColor.UNDERLINE + "cannot" + (Object)ChatColor.GRAY + " marry yourself! Are you really that lonely?");
            return false;
        }
        return true;
    }
}

