package net.astropvp.core.listeners;

import com.massivecraft.factions.zcore.persist.Entity;
import net.astropvp.core.AstroCore;
import org.bukkit.Material;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

public class onDeathDropListener implements Listener {

    @EventHandler
    public void onDeathDrop(PlayerDeathEvent e) {
        for (ItemStack item : e.getDrops()) {
            if (item.getType() == Material.DIAMOND_CHESTPLATE ||
                    item.getType() == Material.DIAMOND_HELMET ||
                    item.getType() == Material.DIAMOND_LEGGINGS ||
                    item.getType() == Material.DIAMOND_BOOTS ||
                    item.getType() == Material.DIAMOND_SWORD ||
                    item.getType() == Material.SKULL ||
                    item.getType() == Material.SKULL_ITEM ||
                    item.getType() == Material.DIAMOND_AXE ||
                    item.getType() == Material.EMERALD ||
                    item.getType() == Material.GOLD_SWORD ||
                    item.getType() == Material.LEATHER_BOOTS ||
                    item.getType() == Material.LEATHER_CHESTPLATE ||
                    item.getType() == Material.LEATHER_HELMET ||
                    item.getType() == Material.LEATHER_LEGGINGS ||
                    item.getType() == Material.ENDER_PEARL) {
                Item entity = e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), item);
                entity.setMetadata("do_not_clear", new FixedMetadataValue(AstroCore.getInstance(), ""));
            }

        }
    }

    @EventHandler
    public void onDropDrop(PlayerDropItemEvent e) {
        ItemStack item = e.getItemDrop().getItemStack();
        if (item.getType() == Material.DIAMOND_CHESTPLATE ||
                item.getType() == Material.DIAMOND_HELMET ||
                item.getType() == Material.DIAMOND_LEGGINGS ||
                item.getType() == Material.DIAMOND_BOOTS ||
                item.getType() == Material.DIAMOND_SWORD ||
                item.getType() == Material.SKULL ||
                item.getType() == Material.SKULL_ITEM ||
                item.getType() == Material.DIAMOND_AXE ||
                item.getType() == Material.EMERALD ||
                item.getType() == Material.GOLD_SWORD ||
                item.getType() == Material.LEATHER_BOOTS ||
                item.getType() == Material.LEATHER_CHESTPLATE ||
                item.getType() == Material.LEATHER_HELMET ||
                item.getType() == Material.LEATHER_LEGGINGS ||
                item.getType() == Material.ENDER_PEARL) {
            e.getItemDrop().setMetadata("do_not_clear", new FixedMetadataValue(AstroCore.getInstance(), ""));
        }

    }

    @EventHandler
    public void onThrow(ProjectileLaunchEvent e) {

        if (e.getEntity().getType() == EntityType.ENDER_PEARL) {
            e.getEntity().setMetadata("do_not_clear", new FixedMetadataValue(AstroCore.getInstance(), ""));

        }

    }

}
