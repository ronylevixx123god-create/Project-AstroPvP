package me.astropvp.astrospawners.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class CreeperEggFix implements Listener {
    @EventHandler
    public void onSpawnerChangeWithEgg(PlayerInteractEvent e) {
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (e.getClickedBlock().getType() != Material.MOB_SPAWNER) return;
        if (e.getItem() == null || e.getItem().getType() == Material.AIR) return;
        if (e.getItem().getType() != Material.MONSTER_EGG) return;
        e.setCancelled(true);
        ItemStack itemStack = e.getItem();
        if (itemStack.hasItemMeta()) return;
        Block block = e.getClickedBlock();
        block.getWorld().spawnEntity(block.getLocation().add(e.getBlockFace().getModX(), e.getBlockFace().getModY(), e.getBlockFace().getModZ()), EntityType.CREEPER);
        int amount = itemStack.getAmount();
        Player p = e.getPlayer();
        if (amount == 1) {
            p.setItemInHand(null);
            return;
        }
        itemStack.setAmount(amount - 1);
        p.setItemInHand(itemStack);
    }
}
