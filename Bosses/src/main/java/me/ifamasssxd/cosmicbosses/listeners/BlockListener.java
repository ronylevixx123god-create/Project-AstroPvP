/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.NBTWrapper
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.BlockFromToEvent
 *  org.bukkit.event.block.BlockPlaceEvent
 *  org.bukkit.event.world.ChunkUnloadEvent
 */
package me.ifamasssxd.cosmicbosses.listeners;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import me.ifamasssxd.cosmicbosses.CosmicBosses;
import me.ifamasssxd.cosmicbosses.struct.Boss;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

public class BlockListener
implements Listener {
    @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
    public void onPlayerPlaceInterdimensional(BlockPlaceEvent event) {
        String s;
        if (event.getItemInHand() != null && event.getItemInHand().getType() == Material.BEACON && (s = new NBTWrapper(event.getItemInHand()).getString("cosmicType")) != null && s.equals("dimensionalCore")) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(CC.RedB + "(!) " + CC.Red + "You cannot place this item.");
        }
    }

    @EventHandler
    public void onWaterMove(BlockFromToEvent e) {
        for (Boss boss : Boss.bosses.values()) {
            if (boss.getModifier() == null || !boss.getModifier().containsLocation(e.getBlock().getLocation())) continue;
            e.setCancelled(true);
            return;
        }
    }

    @EventHandler(ignoreCancelled=false)
    public void onChunkUnload(ChunkUnloadEvent event) {
        CosmicBosses.getInstance().getBlockChangeManager().getStoredBlocks().keySet().stream().filter(loc -> event.getChunk().getX() == loc.getChunk().getX() && event.getChunk().getZ() == loc.getChunk().getZ() && loc.getChunk().getWorld().getName().equals(event.getChunk().getWorld().getName())).forEach(loc -> CosmicBosses.getInstance().getBlockChangeManager().restoreBlock((Location)loc));
    }
}

