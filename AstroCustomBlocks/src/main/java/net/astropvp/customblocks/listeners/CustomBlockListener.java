package net.astropvp.customblocks.listeners;

import net.astropvp.customblocks.CustomBlocks;
import net.astropvp.customblocks.data.CustomBlockData;
import net.astropvp.customblocks.data.CustomBlockManager;
import net.astropvp.customblocks.struct.CustomBlock;
import net.astropvp.customblocks.struct.CustomBlockType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class CustomBlockListener implements Listener {
    private CustomBlockManager customBlockManager;
    private CustomBlocks customBlocks;

    public CustomBlockListener(CustomBlocks plugin) {
        this.customBlocks = plugin;
        this.customBlockManager = plugin.getCustomBlockManager();
    }
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && !event.isCancelled() && customBlockManager.getCustomBlockData(event.getClickedBlock().getLocation()) != null) {
            event.setCancelled(true);
            CustomBlockData customBlockData = customBlockManager.getCustomBlockData(event.getClickedBlock().getLocation());
            CustomBlock customBlock = CustomBlockType.valueOf(customBlockData.getType()).getCustomBlock();
            customBlock.onClick(event.getPlayer());
        }
    }
}
