/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.massivecraft.factions.FPlayer
 *  com.massivecraft.factions.FPlayers
 *  com.massivecraft.factions.Faction
 *  org.bukkit.Bukkit
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 */
package com.compilex.CosmicOutposts;

import com.compilex.CosmicOutposts.outpost.Outpost;
import com.compilex.CosmicOutposts.outpost.enums.OutpostTier;
import com.compilex.CosmicOutposts.outpost.libs.OutpostCapTime;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class CosmicOutpostsAPI {
    public static OutpostCapTime getFactionCapTime(Faction faction) {
        return CosmicOutpostsPlugin.get_().getDataManager().getCapTimes(faction);
    }

    public static OutpostCapTime getFactionCapTime(String faction) {
        return CosmicOutpostsPlugin.get_().getDataManager().getCapTimes(faction);
    }

    public static void incrementCapTime(Faction faction, OutpostTier tier, int amount) {
        if (faction == null) {
            return;
        }
        CosmicOutpostsPlugin.get_().getDataManager().incrementCapTime(faction, tier, amount);
        FPlayer admin = faction.getFPlayerAdmin();
        if (admin != null && admin.getCachedUUID() != null) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "contests add " + admin.getCachedUUID() + " opheld " + amount);
        }
    }

    @Deprecated
    public static OutpostTier getOutpostTierOfWorld(LivingEntity le) {
        for (Outpost outpost : CosmicOutpostsPlugin.get_().getOutposts()) {
            if (!le.getWorld().equals(outpost.getWorld())) continue;
            return outpost.getOutpostTier();
        }
        return OutpostTier.NONE;
    }

    public static OutpostTier getOutpostTierOfWorld(Entity le) {
        for (Outpost outpost : CosmicOutpostsPlugin.get_().getOutposts()) {
            if (!le.getWorld().equals(outpost.getWorld())) continue;
            return outpost.getOutpostTier();
        }
        return OutpostTier.NONE;
    }

    public static Outpost getOutpost(Entity le) {
        for (Outpost outpost : CosmicOutpostsPlugin.get_().getOutposts()) {
            if (outpost.getWorld() == null || !outpost.getWorld().equals(le.getWorld())) continue;
            return outpost;
        }
        return null;
    }

    public static Outpost getOutpostPlayerControls(Player p) {
        Faction playerFaction = FPlayers.i.get(p).getFaction();
        if (playerFaction.isNormal()) {
            for (Outpost outpost : CosmicOutpostsPlugin.get_().getOutposts()) {
                if (outpost.getControllingFaction() == null || !outpost.getControllingFaction().equals(playerFaction.getTag())) continue;
                return outpost;
            }
        }
        return null;
    }

    public static boolean isDefender(Player p, Outpost o) {
        Faction playerFaction = FPlayers.i.get(p).getFaction();
        return playerFaction.isNormal() && playerFaction.getTag().equals(o.getControllingFaction());
    }

    public static boolean controlsVanillaOutpost(Player p) {
        Faction playerFaction = FPlayers.i.get(p).getFaction();
        return playerFaction.isNormal() && CosmicOutpostsPlugin.get_().getVanillaOutpost().getControllingFaction() != null && CosmicOutpostsPlugin.get_().getVanillaOutpost().getControllingFaction().equals(playerFaction.getTag());
    }

    public static boolean controlsTraineeOutpost(Player p) {
        Faction playerFaction = FPlayers.i.get(p).getFaction();
        return playerFaction.isNormal() && CosmicOutpostsPlugin.get_().getTraineeOutpost().getControllingFaction() != null && CosmicOutpostsPlugin.get_().getTraineeOutpost().getControllingFaction().equals(playerFaction.getTag());
    }

    public static boolean controlsHeroOutpost(Player p) {
        Faction playerFaction = FPlayers.i.get(p).getFaction();
        return playerFaction.isNormal() && CosmicOutpostsPlugin.get_().getHeroOutpost().getControllingFaction() != null && CosmicOutpostsPlugin.get_().getHeroOutpost().getControllingFaction().equals(playerFaction.getTag());
    }

    public static boolean controlsCosmonautOutpost(Player p) {
        Faction playerFaction = FPlayers.i.get(p).getFaction();
        return playerFaction.isNormal() && CosmicOutpostsPlugin.get_().getCosmonautOutpost().getControllingFaction() != null && CosmicOutpostsPlugin.get_().getCosmonautOutpost().getControllingFaction().equals(playerFaction.getTag());
    }
}

