package me.astropvp.astropvpskins.objects.skins.helmets;

import com.rit.sucy.EnchantmentAPI;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import me.astropvp.astropvpskins.objects.ItemSkin;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class GrimHoodSkin extends ItemSkin {

    @Override
    public void onAttacked(Player damaged, Entity damager, EntityDamageByEntityEvent event) {

    }

    public void onEquip(Player player) {
        EnchantmentAPI.getEnchantment("Alien Implants").applyEquipEffect(player, 3);
    }

    public void onUnequip(Player player) {
        EnchantmentAPI.getEnchantment("Alien Implants").applyUnequipEffect(player, 3);
    }

    @Override
    public void onAttack(Player damager, Entity damaged, EntityDamageByEntityEvent event) {
        if (!(damaged instanceof Player)) return;
        if (AstroItemSkinsAPI.hasSkinEquipped((Player) damaged, SkinType.HALLOWEEN_2022_HELMET) || AstroItemSkinsAPI.hasSkinEquipped((Player) damaged, SkinType.HALLOWEEN_2022_BACKPACK) || AstroItemSkinsAPI.hasSkinEquipped((Player) damaged, SkinType.HALLOWEEN_2022_AMULET)) {
            event.setDamage(event.getDamage() * 1.1);

        }
    }
}