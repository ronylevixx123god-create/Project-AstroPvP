package net.astropvp.customblocks.listeners;

import net.astropvp.customblocks.CustomBlocks;
import net.astropvp.customblocks.CustomBlocksAPI;
import net.astropvp.customblocks.data.CustomBlockData;
import net.astropvp.customblocks.data.CustomBlockManager;
import net.astropvp.customblocks.struct.CustomBlock;
import net.astropvp.customblocks.struct.CustomBlockType;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class CustomBlockBreakListener implements Listener {
    private CustomBlockManager customBlockManager;
    private CustomBlocks customBlocks;

    public CustomBlockBreakListener(CustomBlocks plugin) {
        this.customBlocks = plugin;
        this.customBlockManager = plugin.getCustomBlockManager();
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (CustomBlocksAPI.isCustomBlock(event.getBlock().getLocation())) {
            CustomBlockData customBlockData = CustomBlocksAPI.getCustomBlockData(event.getBlock().getLocation());
            //check if chunk alrdy has the cblock
            event.setCancelled(true);
            event.getBlock().setType(Material.AIR);
            event.getBlock().getLocation().getWorld().dropItemNaturally(event.getBlock().getLocation(), CustomBlockType.valueOf(customBlockData.getType()).getCustomBlock().getCustomBlockItem(customBlockData));
            customBlockManager.removeFromCache(customBlockData);
        }
    }
}
