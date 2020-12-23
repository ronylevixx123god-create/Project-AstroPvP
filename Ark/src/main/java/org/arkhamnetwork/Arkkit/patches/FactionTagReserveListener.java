/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.massivecraft.factions.event.FactionCreateEvent
 *  com.massivecraft.factions.event.FactionRenameEvent
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 */
package org.arkhamnetwork.Arkkit.patches;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.massivecraft.factions.event.FactionCreateEvent;
import com.massivecraft.factions.event.FactionRenameEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.UUID;

final class FactionTagReserveListener
        implements Listener {
    FactionTagReserveListener() {
    }

    @EventHandler
    public void onFactionCreateEvent(FactionCreateEvent event) {
        UUID tagOwner = FactionTagReserve.reservedTags.getOrDefault(event.getFactionTag().toLowerCase(), null);
        if (tagOwner != null && !event.getFPlayer().getPlayer().getUniqueId().equals(tagOwner)) {
            event.setCancelled(true);
            event.getFPlayer().getPlayer().sendMessage(CC.RedB + "(!) " + CC.Red + "The faction tag \"" + event.getFactionTag() + "\" is reserved and cannot be claimed by you.");
        }
    }

    @EventHandler
    public void onFactionRenameEvent(FactionRenameEvent event) {
        UUID tagOwner = FactionTagReserve.reservedTags.getOrDefault(event.getFactionTag().toLowerCase(), null);
        if (tagOwner != null && !event.getFPlayer().getPlayer().getUniqueId().equals(tagOwner)) {
            event.setCancelled(true);
            event.getFPlayer().getPlayer().sendMessage(CC.RedB + "(!) " + CC.Red + "The faction tag \"" + event.getFactionTag() + "\" is reserved and cannot be claimed by you.");
        }
    }
}

