package net.astropvp.customblocks.listeners;

import net.astropvp.customblocks.CustomBlocks;
import net.astropvp.customblocks.CustomBlocksAPI;
import net.astropvp.customblocks.data.CustomBlockData;
import net.astropvp.customblocks.data.CustomBlockManager;
import net.astropvp.customblocks.struct.CustomBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class CustomBlockPlaceListener implements Listener {
    private CustomBlockManager customBlockManager;
    private CustomBlocks customBlocks;

    public CustomBlockPlaceListener(CustomBlocks plugin) {
        this.customBlocks = plugin;
        this.customBlockManager = plugin.getCustomBlockManager();
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        ItemStack item = event.getItemInHand();
        if (CustomBlocksAPI.isCustomBlockItem(item)) {
            //check if chunk alrdy has the cblock
            customBlockManager.addToCache(new CustomBlockData(event.getBlock().getLocation(), CustomBlocksAPI.getLevelFromItem(item), CustomBlocksAPI.getTypeFromItem(item).name()));
        }
    }
}
