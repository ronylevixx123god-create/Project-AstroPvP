package net.astropvp.core.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class HeroicArmorFix implements Listener {
    @EventHandler
    public void onHit(PlayerItemDamageEvent e) {
        if (e.getPlayer() == null) {
            return;
        }
        ItemStack helmet = e.getPlayer().getInventory().getHelmet();
        ItemStack chestplate = e.getPlayer().getInventory().getChestplate();
        ItemStack leggings = e.getPlayer().getInventory().getLeggings();
        ItemStack boots = e.getPlayer().getInventory().getBoots();

        if (helmet == null || chestplate == null || leggings == null || boots == null) {
            return;
        }

        try {


            if (e.getPlayer().getItemInHand().getType() == Material.GOLD_SWORD || e.getPlayer().getItemInHand().getType() == Material.GOLD_AXE) {
                //e.getPlayer().getItemInHand().setDurability((short) 0);

                ItemMeta meta = e.getPlayer().getItemInHand().getItemMeta();
                meta.spigot().setUnbreakable(true);
                e.getPlayer().getItemInHand().setItemMeta(meta);
            }

            if (helmet.getType() == Material.LEATHER_HELMET) {
                //helmet.setDurability((short) 0);
                ItemMeta meta = helmet.getItemMeta();
                meta.spigot().setUnbreakable(true);
                helmet.setItemMeta(meta);
            }

            if (chestplate.getType() == Material.LEATHER_CHESTPLATE) {
                //chestplate.setDurability((short) 0);
                ItemMeta meta = chestplate.getItemMeta();
                meta.spigot().setUnbreakable(true);
                chestplate.setItemMeta(meta);
            }

            if (leggings.getType() == Material.LEATHER_LEGGINGS) {
                //leggings.setDurability((short) 0);
                ItemMeta meta = leggings.getItemMeta();
                meta.spigot().setUnbreakable(true);
                leggings.setItemMeta(meta);
            }

            if (boots.getType() == Material.LEATHER_BOOTS) {
                //boots.setDurability((short) 0);
                ItemMeta meta = boots.getItemMeta();
                meta.spigot().setUnbreakable(true);
                boots.setItemMeta(meta);
            }
        } catch (Exception ex) {

        }


    }
}
