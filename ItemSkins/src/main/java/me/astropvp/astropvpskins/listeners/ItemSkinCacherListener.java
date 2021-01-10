package me.astropvp.astropvpskins.listeners;

import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.AstroItemSkinsPlugin;
import me.astropvp.astropvpskins.objects.skins.ItemSkinCacher;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Objects;


public class ItemSkinCacherListener
        implements Listener {
    public boolean isArmor(final Material m) {
        final String n = m.toString().toLowerCase();
        return m != null && m != Material.AIR && (n.contains("helmet") || n.contains("chestplate") || n.contains("leggings") || n.contains("boots"));
    }
    @EventHandler
    public void removeCache(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        ItemSkinCacher.removePlayer(player);
    }

    @EventHandler
    public void deathEvent(PlayerDeathEvent event) {
        Player player = event.getEntity();
        ItemSkinCacher.removePlayer(player);
    }

    @EventHandler(ignoreCancelled = true)
    public void armorEquipEvent(PlayerInteractEvent e) {
        if (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        if (e.getPlayer().getItemInHand() == null || !this.isArmor(e.getPlayer().getItemInHand().getType())) {
            return;
        }
        if (e.hasBlock() && (e.getClickedBlock().getState() instanceof Chest || e.getClickedBlock().getState() instanceof Dispenser || e.getClickedBlock().getState() instanceof Dropper || e.getClickedBlock().getState() instanceof Furnace || e.getClickedBlock().getState() instanceof Hopper || e.getClickedBlock().getType() == Material.GOLD_BLOCK || e.getClickedBlock().getType() == Material.IRON_BLOCK)) {
            return;
        }
        Bukkit.getScheduler().runTaskAsynchronously(AstroItemSkinsPlugin.getInstance(), () -> {
            if (e.getPlayer().hasMetadata("lastArmorSwap")) {
                new ItemSkinCacher(e.getPlayer());

            }
        });

    }


}


