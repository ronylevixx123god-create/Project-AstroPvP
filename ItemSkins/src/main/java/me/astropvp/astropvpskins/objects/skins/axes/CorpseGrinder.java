package me.astropvp.astropvpskins.objects.skins.axes;

import com.cosmicpvp.cosmicutils.utils.WorldGuardUtils;
import me.astropvp.astropvpskins.objects.ItemSkin;
import org.arkhamnetwork.enchantments.enchantments.swords.DoubleStrike;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class CorpseGrinder extends ItemSkin {
    @Override
    public void onAttack(Player damager, Entity damaged, EntityDamageByEntityEvent event) {
        if (!WorldGuardUtils.isPvPEnabled(damager.getLocation())) return;

        new DoubleStrike().applyEffect(damager, (LivingEntity) damaged, 2, event);


        if (Math.random() < 0.01) {
            event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE)*1.25);
        }
    }
}
