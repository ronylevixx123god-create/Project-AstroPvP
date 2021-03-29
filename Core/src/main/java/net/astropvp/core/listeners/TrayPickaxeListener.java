package net.astropvp.core.listeners;

import com.cosmicpvp.cosmicutils.utils.CC;
import net.astropvp.core.AstroCore;
import net.md_5.bungee.api.ChatColor;
import org.arkhamnetwork.Arkkit.utils.WorldGuardUtils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;


public class TrayPickaxeListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onMine(BlockBreakEvent e) {
        if (e.isCancelled()) {
            return;
        }
        Player p = e.getPlayer();
        if (p.getItemInHand() == null) {
            return;
        }
        if (!p.getItemInHand().getType().toString().contains("PICKAXE")) {

            return;
        }
        if (p.getItemInHand().getItemMeta().getDisplayName() == null) {

            return;
        }
        if (p.getItemInHand().getItemMeta() == null) {

            return;
        }
        if (p.getItemInHand().getItemMeta().getDisplayName().equals(
                CC.translate("&c&lTray Pickaxe &7(8x8)"))) {
            Location l = e.getBlock().getLocation();
            int range = 16;
            int minX = l.getBlockX() - range / 2;
            int minY = l.getBlockY() - range / 2;
            int minZ = l.getBlockZ() - range / 2;
            for (int x = minX; x < minX + range; x++) {
                for (int y = minY; y < minY + range; y++) {
                    for (int z = minZ; z < minZ + range; z++) {
                        if (e.getBlock().getWorld().getBlockAt(x, y, z).getType() == Material.DIRT ||
                                e.getBlock().getWorld().getBlockAt(x, y, z).getType() == Material.NETHERRACK) {
                            Block block = e.getBlock().getWorld().getBlockAt(x, y, z);
                            if (WorldGuardUtils.getWorldGuard().canBuild(p, e.getBlock().getWorld().getBlockAt(x, y, z))) {
                                block.setType(Material.AIR);
                            }
                        }
                        double i = 0.5D;
                        ItemStack item = p.getItemInHand();
                        item.setDurability((short)(int)(item.getDurability() + i));
                        if (item.getDurability() >= item.getType().getMaxDurability()) {
                            p.setItemInHand(new ItemStack(Material.AIR));
                            p.playSound(p.getLocation(), Sound.ITEM_BREAK, 1.0F, 1.0F);
                        } else {
                            p.setItemInHand(item);
                        }
                    }
                }
            }
            e.setCancelled(true);
        }
    }
}
