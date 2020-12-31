package net.astropvp.core.listeners;

import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import net.astropvp.core.utils.ChunkBusterUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class ChunkBusterListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockPlace(BlockPlaceEvent event) {
        ItemStack item = event.getItemInHand();
        if (item.isSimilar(ChunkBusterUtils.getItem())) {
            if (event.getPlayer().getWorld().getEnvironment() != World.Environment.NORMAL) {
                event.getPlayer().sendMessage(ChatColor.RED + "You can only use chunk busters in the overworld.");
                event.setCancelled(true);
                return;
            }
            if ((FactionUtils.getFaction(event.getPlayer()) == null) || (FactionUtils.getFaction(event.getPlayer()).getId().equals("0"))) {
                event.getPlayer().sendMessage(ChatColor.RED + "You can only use chunk busters in your territory.");
                event.setCancelled(true);
                return;
            }
            if (!FactionUtils.getFactionAt(event.getBlock().getLocation()).getId().equals(FactionUtils.getFaction(event.getPlayer()).getId())) {
                event.getPlayer().sendMessage(ChatColor.RED + "You can only use chunk busters in your territory.");
                event.setCancelled(true);
                return;
            }
            int X = event.getBlock().getChunk().getX() * 16;
            int Z = event.getBlock().getChunk().getZ() * 16;
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    for (int y = 0; y < 256; y++) {
                        Block block = event.getBlock().getWorld().getBlockAt(X + x, y, Z + z);
                        if ((block.getType() != Material.BEDROCK) && (block.getType() != Material.AIR)) {
                            block.setType(Material.AIR);
                        }
                    }
                }
            }
        }
    }
}
