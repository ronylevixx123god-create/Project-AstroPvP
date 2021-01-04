/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.massivecraft.factions.event.FPlayerLeaveEvent
 *  com.massivecraft.factions.event.FactionCreateEvent
 *  com.massivecraft.factions.event.FactionDisbandEvent
 *  org.bukkit.Bukkit
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerCommandPreprocessEvent
 */
package com.massivecraft.factions.listeners;

import com.massivecraft.factions.P;
import com.massivecraft.factions.event.FPlayerLeaveEvent;
import com.massivecraft.factions.event.FactionCreateEvent;
import com.massivecraft.factions.event.FactionDisbandEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class FactionEventListener
implements Listener {
    @EventHandler
    public void onFactionDisband(FactionDisbandEvent event) {
        int removed = P.getP().getFactionQuestManager().clearFactionData(event.getFaction().getId(), true);
        if (removed > 0) {
            Bukkit.getLogger().info("Removed progress for " + removed + " quests for " + event.getFaction().getTag());
        }
    }

    @EventHandler
    public void onFactionCreate(FactionCreateEvent event) {
        P.getP().getFactionQuestManager().getFactionCreationTime().put(event.getFactionId(), System.currentTimeMillis());
    }

    @EventHandler
    public void onFactionDisband(FPlayerLeaveEvent event) {
        int removed;
        if (event.getFaction().getFPlayers().size() == 1 && event.getFaction().getFPlayers().contains((Object)event.getFPlayer()) && (removed = P.getP().getFactionQuestManager().clearFactionData(event.getFaction().getId(), true)) > 0) {
            Bukkit.getLogger().info("Removed progress for " + removed + " quests for " + event.getFaction().getTag() + " due to Fplayerleave");
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void onPlayerCommandPre(PlayerCommandPreprocessEvent event) {
        String subCommand;
        String msgLower = event.getMessage().toLowerCase();
        if ((msgLower.startsWith("/f ") || msgLower.startsWith("/fac ") || msgLower.startsWith("/factions")) && msgLower.contains(" ") && (subCommand = msgLower.split(" ")[1]) != null && subCommand.startsWith("quest")) {
            event.setMessage("/fquest");
        }
    }
}

