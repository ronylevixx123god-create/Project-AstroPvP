/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.massivecraft.factions.event.LandClaimEvent
 *  org.arkhamnetwork.Arkkit.libs.SpawnerLocation
 *  org.arkhamnetwork.Arkkit.patches.SpawnerFee
 *  org.bukkit.Bukkit
 *  org.bukkit.Chunk
 *  org.bukkit.Material
 *  org.bukkit.block.BlockState
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerCommandPreprocessEvent
 */
package me.ifamasssxd.factionstop.listeners;

import com.massivecraft.factions.P;
import com.massivecraft.factions.event.LandClaimEvent;
import org.arkhamnetwork.Arkkit.libs.SpawnerLocation;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandListener
implements Listener {
    @EventHandler(priority=EventPriority.LOWEST)
    public void onPlayerCommandPreProcess(PlayerCommandPreprocessEvent event) {
        String lower = event.getMessage().toLowerCase();
        if (event.getMessage().toLowerCase().startsWith("/f top")) {
            event.setMessage(event.getMessage().replace("/f top", "/ftop"));
        } else if (lower.startsWith("/f wealth")) {
            event.setMessage(lower.replace("/f wealth", "/ftop wealth"));
        }
    }

    @EventHandler
    public void onLandClaim(LandClaimEvent event) {
        if (P.getP().isGradualSpawnerValues()) {
            Chunk chunk = event.getLocation().getWorld().getChunkAt((int)event.getLocation().getX(), (int)event.getLocation().getZ());
            for (BlockState state : chunk.getTileEntities()) {
                SpawnerLocation location;
                String name;
                if (state.getType() != Material.MOB_SPAWNER || !SpawnerFee.playerSpawners.containsKey(name = chunk.getWorld().getName() + "," + state.getLocation().getBlockX() + "," + state.getLocation().getBlockY() + "," + state.getLocation().getBlockZ()) || (location = (SpawnerLocation)SpawnerFee.playerSpawners.get(name)) == null) continue;
                location.setPlaceTime(System.currentTimeMillis());
                Bukkit.getLogger().info("Resetting place time for spawner: " + name);
            }
        }
    }
}

