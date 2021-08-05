package me.bryside.weaponrunes.listeners;

import com.cosmicpvp.cosmicutils.utils.CC;
import me.bryside.weaponrunes.enums.WeaponBonusType;
import me.bryside.weaponrunes.utils.WeaponBonusUtils;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class WeaponBonusListener implements Listener {
    @EventHandler
    public void onHit(EntityDamageByEntityEvent e) {
        Entity damager = e.getDamager();
        Entity damaged = e.getEntity();

        if (!(damager instanceof Player)) return;
        if (!(damaged instanceof Player)) return;
        if (damager == null) return;
        if (damaged == null) return;
        if (((Player) damager).getItemInHand() == null && ((Player) damager).getItemInHand().getType() == Material.AIR) return;

        if (e.isCancelled()) return;

        if (damager instanceof Player) {
            WeaponBonusType weaponBonusType = WeaponBonusUtils.getWeaponBonusByItem(((Player)damager).getItemInHand());
            if (weaponBonusType == null) return;
            weaponBonusType.getInstance().onAttack((Player)damager, damaged, e);
        }
        if (damaged instanceof Player) {
            WeaponBonusType weaponBonusType = WeaponBonusUtils.getWeaponBonusByItem(((Player)damaged).getItemInHand());
            if (weaponBonusType == null) return;
            weaponBonusType.getInstance().onAttacked((Player)damaged, damager, e);
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        ItemStack cursor = e.getCursor();
        ItemStack clicked = e.getCurrentItem();
        if (e.getClickedInventory() != e.getView().getBottomInventory())
            return;
        if (clicked == null || clicked.getType() == Material.AIR)
            return;
        if (!WeaponBonusUtils.hasWeaponBonus(clicked) && WeaponBonusUtils.isWeaponBonus(cursor) && e.getClick().isLeftClick()) {
            WeaponBonusType weaponBonusType = WeaponBonusUtils.getWeaponBonusByItem(cursor);
            if (weaponBonusType == null || !WeaponBonusUtils.isHeroicWeapon(clicked))
                return;
            e.setCurrentItem(weaponBonusType.getInstance().applyBonus(clicked, weaponBonusType));
            e.getWhoClicked().sendMessage(CC.translate("&a&l(!)&a Successfully Applied " + weaponBonusType.getName() + " Weapon Rune&a to your sword!"));
            e.setCursor(null);
            e.setCancelled(true);
            ((Player)e.getWhoClicked()).playSound(e.getWhoClicked().getLocation(), Sound.LEVEL_UP, 1.0F, 10.0F);
        }
    }
}
