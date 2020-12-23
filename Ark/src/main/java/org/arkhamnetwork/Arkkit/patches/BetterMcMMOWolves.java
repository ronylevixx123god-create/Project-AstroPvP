/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.massivecraft.factions.FPlayer
 *  com.massivecraft.factions.FPlayers
 *  com.massivecraft.factions.iface.RelationParticipator
 *  com.massivecraft.factions.struct.Relation
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Tameable
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.EntityTargetEvent
 */
package org.arkhamnetwork.Arkkit.patches;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.iface.RelationParticipator;
import com.massivecraft.factions.struct.Relation;
import org.arkhamnetwork.Arkkit.utils.WorldGuardUtils;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;

final class BetterMcMMOWolves
        implements Listener {
    BetterMcMMOWolves() {
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        if (e.getEntity().hasMetadata("mcMMOWolf") && WorldGuardUtils.isPvPDisabled(e.getEntity().getLocation())) {
            e.setCancelled(true);
            e.setDamage(0.0);
            return;
        }
    }

    @EventHandler
    public void onEntityTarget(EntityTargetEvent e) {
        FPlayer fTarget;
        FPlayer fpOwner;
        Tameable tameable;
        Player owner;
        if (e.getEntity().hasMetadata("mcMMOWolf") && e.getTarget() instanceof Player && (owner = (Player) (tameable = (Tameable) e.getEntity()).getOwner()) != null && (fpOwner = FPlayers.i.get(owner)).getRelationTo(fTarget = FPlayers.i.get(e.getTarget())).isAtLeast(Relation.TRUCE)) {
            e.setCancelled(true);
        }
    }
}

