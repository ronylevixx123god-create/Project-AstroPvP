/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.gmail.nossr50.events.experience.McMMOPlayerXpGainEvent
 *  com.massivecraft.factions.Faction
 *  org.bukkit.ChatColor
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package com.conaxgames.factions.boosters.listeners;

import com.conaxgames.factions.boosters.enums.BoosterType;
import com.conaxgames.factions.boosters.struct.ActiveBooster;
import com.conaxgames.factions.boosters.struct.BoosterManager;
import com.conaxgames.factions.boosters.struct.FactionBoosters;
import com.conaxgames.factions.boosters.utils.FactionUtils;
import com.gmail.nossr50.events.experience.McMMOPlayerXpGainEvent;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.P;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class mcMMOListener
implements Listener {
    @EventHandler
    public void onmcMMOChange(McMMOPlayerXpGainEvent event) {
        Faction faction = FactionUtils.getFaction(event.getPlayer());
        if (faction == null || !faction.isNormal()) {
            return;
        }
        Player player = event.getPlayer();
        BoosterManager manager = P.getP().getBoosterManager();
        FactionBoosters boosters = manager.getFactionBooster(faction);
        if (boosters != null && boosters.isBoosterActive(BoosterType.MCMMO)) {
            ActiveBooster booster = boosters.get(BoosterType.MCMMO);
            event.setRawXpGained((float)((double)event.getRawXpGained() * booster.getMultiplier()));
            int notify = 0;
            if (player.hasMetadata("mcmmoBoosterNotify") && player.getMetadata("mcmmoBoosterNotify").size() > 0 && (notify = player.getMetadata("mcmmoBoosterNotify").get(0).asInt()) >= 15) {
                player.sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "+ " + booster.getMultiplier() + "x mcMMO " + ChatColor.GREEN + "(" + booster.getWhoApplied() + "'s Faction Booster " + ChatColor.GRAY + "[" + booster.getFormattedTimeLeft() + "]" + ChatColor.GREEN + ")");
                notify = 0;
            }
            player.setMetadata("mcmmoBoosterNotify", new FixedMetadataValue(P.getP(), ++notify));
        }
    }
}

