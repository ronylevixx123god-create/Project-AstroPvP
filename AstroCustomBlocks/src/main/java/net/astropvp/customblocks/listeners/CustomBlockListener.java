package net.astropvp.customblocks.listeners;

import net.astropvp.customblocks.CustomBlocks;
import net.astropvp.customblocks.data.CustomBlockData;
import net.astropvp.customblocks.data.CustomBlockManager;
import net.astropvp.customblocks.struct.CustomBlock;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class CustomBlockListener implements Listener {
    private CustomBlockManager customBlockManager;
    private CustomBlocks customBlocks;

    public CustomBlockListener(CustomBlocks plugin) {
        this.customBlocks = plugin;
        this.customBlockManager = plugin.getCustomBlockManager();
    }
    public void onInteract(PlayerInteractEvent event) {
        if (customBlockManager.getCustomBlockData(event.getClickedBlock().getLocation()) != null) {
            CustomBlockData customBlockData = customBlockManager.getCustomBlockData(event.getClickedBlock().getLocation());
            CustomBlock customBlock = new CustomBlock(customBlockData);
            customBlock.onClick();
        }
    }
}
