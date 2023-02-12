package me.astropvp.astropvpskins.objects.skins.helmets;

import com.cosmicpvp.cosmicutils.utils.ItemUtils;
import me.astropvp.astropvpskins.objects.ItemSkin;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class FrostMonsterSkin extends ItemSkin {

    @Override
    public void onAttacked(Player damaged, Entity damager, EntityDamageByEntityEvent event) {
        if (damager instanceof Player && !ItemUtils.isAirOrNull(((Player) damager).getItemInHand())) {
            if (((Player) damager).getItemInHand().getType().name().contains("AXE")) {
                event.setDamage(event.getDamage() * 0.85);
            }
        }
    }

    @Override
    public void onAttack(Player damager, Entity damaged, EntityDamageByEntityEvent event) {
        if (damaged instanceof Player && !ItemUtils.isAirOrNull((ItemStack) ((Player) damaged).getItemInHand())) {
            if (((Player) damaged).getItemInHand().getType().name().contains("SWORD")) {
                event.setDamage(event.getDamage() * 1.05);
            }
        }
    }
}