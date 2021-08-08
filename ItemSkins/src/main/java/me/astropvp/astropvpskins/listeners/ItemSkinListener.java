package me.astropvp.astropvpskins.listeners;

import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.armorequip.ArmorEquipEvent;
import me.astropvp.astropvpskins.enums.SkinType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ItemSkinListener implements Listener {

    @EventHandler
    private void onClick(InventoryClickEvent event) {
        ItemStack cursor = event.getCursor();
        ItemStack clicked = event.getCurrentItem();
        if (event.getClickedInventory() != event.getView().getBottomInventory())
            return;
        if (clicked == null || clicked.getType() == Material.AIR)
            return;
        if (AstroItemSkinsAPI.isItemSkin(cursor) && event.getClick().isLeftClick() && !AstroItemSkinsAPI.hasItemSkin(clicked, cursor)) {
            SkinType type = AstroItemSkinsAPI.getItemSkin(cursor);
            if (type == null || !AstroItemSkinsAPI.isCorrectType(type, clicked))
                return;
            if (event.getSlotType() == InventoryType.SlotType.ARMOR) {
                type.getItemSkin().onEquip((Player) event.getWhoClicked());
            }
            event.setCurrentItem(type.apply(clicked));
            event.setCursor(null);
            event.setCancelled(true);
            ((Player) event.getWhoClicked()).playSound(event.getWhoClicked().getLocation(), Sound.BAT_TAKEOFF, 1.0F, 10.0F);
        } else if (event.getClick().isRightClick() && AstroItemSkinsAPI.hasItemSkin(clicked)) {
            List<SkinType> skinTypes = AstroItemSkinsAPI.getItemSkinByItem(clicked);
            Bukkit.broadcastMessage(skinTypes.toString());
            if (skinTypes.get(0) == null)
                return;
            if (event.getSlotType() == InventoryType.SlotType.ARMOR) {
                skinTypes.get(0).getItemSkin().onUnequip((Player) event.getWhoClicked());
            }
            event.setCurrentItem(AstroItemSkinsAPI.removeItemSkinFromItem(clicked));
            event.setCursor(skinTypes.get(0).getItem());
            event.setCancelled(true);
            ((Player) event.getWhoClicked()).playSound(event.getWhoClicked().getLocation(), Sound.BAT_TAKEOFF, 1.0F, 10.0F);

        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        Entity damaged = event.getEntity();
        if (damager instanceof Player) {
            List<SkinType> damagerSkins = AstroItemSkinsAPI.getItemSkinsOnPlayer((Player) damaged);
            for (SkinType skin : damagerSkins)
                skin.getItemSkin().onAttack((Player) damager, damaged, event);
        }
        if (damaged instanceof Player) {
            List<SkinType> damagedSkins = AstroItemSkinsAPI.getItemSkinsOnPlayer((Player) damaged);
            for (SkinType skin : damagedSkins)
                skin.getItemSkin().onAttacked((Player) damaged, damager, event);

        }
    }
    @EventHandler
    private void onInteract(PlayerInteractEvent event) {
        ItemStack item = event.getPlayer().getItemInHand();
        if (item == null || item.getType() == Material.AIR)
            return;
        if (AstroItemSkinsAPI.isItemSkin(item)) {
            event.setCancelled(true);
            event.getPlayer().updateInventory();
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        AstroItemSkinsAPI.updateSkins(event.getPlayer());
    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent event) {
        AstroItemSkinsAPI.updateSkins(event.getPlayer());
    }

    @EventHandler
    public void onArmorChange(ArmorEquipEvent event) {
        if (event.getOldArmorPiece() != null && event.getOldArmorPiece().getType() != Material.AIR && AstroItemSkinsAPI.hasItemSkin(event.getOldArmorPiece())) {
            List<SkinType> skins = AstroItemSkinsAPI.getItemSkinByItem(event.getOldArmorPiece());
            skins.forEach(skin -> {skin.getItemSkin().onEquip(event.getPlayer());});
        }
        if (event.getNewArmorPiece() != null && event.getNewArmorPiece().getType() != Material.AIR && AstroItemSkinsAPI.hasItemSkin(event.getNewArmorPiece())) {
            List<SkinType> skins = AstroItemSkinsAPI.getItemSkinByItem(event.getNewArmorPiece());
            skins.forEach(skin -> {skin.getItemSkin().onUnequip(event.getPlayer());});
        }
    }
}
