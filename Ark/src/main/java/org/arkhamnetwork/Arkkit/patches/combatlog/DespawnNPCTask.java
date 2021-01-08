/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  net.citizensnpcs.api.CitizensAPI
 *  net.citizensnpcs.api.npc.NPC
 *  org.bukkit.entity.Entity
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.scheduler.BukkitRunnable
 */
package org.arkhamnetwork.Arkkit.patches.combatlog;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

public class DespawnNPCTask
        extends BukkitRunnable {
    public void run() {
        for (NPC n : CombatLogLogoutListener.combatNPCs.values()) {
            Entity e = n.getEntity();
            if (!e.hasMetadata("combatNPC_TimeoutTime")) continue;
            long timeout = e.getMetadata("combatNPC_TimeoutTime").get(0).asLong();
            String playerName = e.getMetadata("combatNPC_PlayerName").get(0).asString();
            String playerUUID = e.getMetadata("combatNPC_PlayerUUID").get(0).asString();
            if (System.currentTimeMillis() <= timeout) continue;
            if (n.isSpawned()) {
                n.despawn();
                CombatLogLogoutListener.combatNPCs.remove(playerUUID);
            }
            n.destroy();
            CitizensAPI.getNPCRegistry().deregister(n);
        }
    }
}

