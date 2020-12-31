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

            if (helmet.getType() == Material.LEATHER_HELMET) {
                helmet.setDurability((short) 0);
            }

            if (chestplate.getType() == Material.LEATHER_CHESTPLATE) {
                chestplate.setDurability((short) 0);
            }

            if (leggings.getType() == Material.LEATHER_LEGGINGS) {
                leggings.setDurability((short) 0);
            }

            if (boots.getType() == Material.LEATHER_BOOTS) {
                boots.setDurability((short) 0);
            }
        } catch (Exception ex) {

        }


    }
}
