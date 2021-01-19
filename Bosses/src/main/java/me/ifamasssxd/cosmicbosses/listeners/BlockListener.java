package me.ifamasssxd.cosmicbosses.listeners;

import com.cosmicpvp.cosmicutils.utils.*;
import org.bukkit.event.*;
import org.bukkit.event.block.*;
import me.ifamasssxd.cosmicbosses.struct.*;
import java.util.*;
import org.bukkit.event.world.*;
import me.ifamasssxd.cosmicbosses.*;
import org.bukkit.*;

public class BlockListener implements Listener
{
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPlayerPlaceInterdimensional(final BlockPlaceEvent event) {
        if (event.getItemInHand() != null && event.getItemInHand().getType() == Material.BEACON) {
            final String s = new NBTWrapper(event.getItemInHand()).getString("cosmicType");
            if (s != null && s.equals("dimensionalCore")) {
                event.setCancelled(true);
                event.getPlayer().sendMessage(CC.RedB + "(!) " + CC.Red + "You cannot place this item.");
            }
        }
    }
    
    @EventHandler
    public void onWaterMove(final BlockFromToEvent e) {
        for (final Boss boss : Boss.bosses.values()) {
            if (boss.getModifier() != null && boss.getModifier().containsLocation(e.getBlock().getLocation())) {
                e.setCancelled(true);
            }
        }
    }
    
    @EventHandler(ignoreCancelled = false)
    public void onChunkUnload(final ChunkUnloadEvent event) {
        CosmicBosses.getInstance().getBlockChangeManager().getStoredBlocks().keySet().stream().filter(loc -> event.getChunk().getX() == loc.getChunk().getX() && event.getChunk().getZ() == loc.getChunk().getZ() && loc.getChunk().getWorld().getName().equals(event.getChunk().getWorld().getName())).forEach(loc -> CosmicBosses.getInstance().getBlockChangeManager().restoreBlock(loc));
    }
}
