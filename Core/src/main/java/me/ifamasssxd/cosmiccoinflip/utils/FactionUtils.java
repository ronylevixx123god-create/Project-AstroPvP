/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.massivecraft.factions.FPlayer
 *  com.massivecraft.factions.FPlayers
 *  com.massivecraft.factions.iface.RelationParticipator
 *  org.bukkit.ChatColor
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.entity.Player
 */
package me.ifamasssxd.cosmiccoinflip.utils;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.iface.RelationParticipator;
import java.util.UUID;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class FactionUtils {
    public static ChatColor getRelationColor(Player player, UUID uuid) {
        try {
            FPlayer fPlayer = FPlayers.i.get(player);
            FPlayer them = FPlayers.i.get(uuid.toString());
            if (fPlayer != null) {
                return fPlayer.getRelationTo(them).getColor();
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return ChatColor.GREEN;
    }

    public static String getFactionTag(Player player, UUID uuid) {
        try {
            FPlayer fPlayer = FPlayers.i.get(player);
            FPlayer them = FPlayers.i.get(uuid.toString());
            if (fPlayer != null && them.getFaction().isNormal()) {
                return them.getFaction().getTag(fPlayer.getFaction()) + " ";
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return ChatColor.WHITE + "";
    }
}

