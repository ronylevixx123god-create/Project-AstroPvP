package me.astropvp.astropvpskins.objects.skins.swords;

import me.astropvp.astropvpskins.enums.SkinType;
import me.astropvp.astropvpskins.objects.ItemSkin;
import me.ifamasssxd.killtracker.KillTrackerAPI;
import org.arkhamnetwork.enchantments.cmds.CommandEnchDust;
import org.arkhamnetwork.enchantments.enchantments.heroic.HeroicTank;
import org.arkhamnetwork.enchantments.enchantments.heroic.HeroicTrap;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class OrnamentalCarnageSkin extends ItemSkin {

    public void onAttack(Player damager, Entity damaged, EntityDamageByEntityEvent event) {
        new HeroicTrap().applyEffect(damager, (LivingEntity) damaged, 5, event);
        if(damaged.hasMetadata("titantrap"))
            event.setDamage(event.getDamage() * 1.1);
        if(Math.random() > 0.02) {
            event.setDamage(event.getDamage() * 1.2);
        }
    }

    public void onAttacked(Player damaged, Entity damager, EntityDamageByEntityEvent event) {
        event.setDamage(event.getDamage() * 0.95);
        if (damager instanceof Player && KillTrackerAPI.hasGodMode((Player) damager))
            event.setDamage(event.getDamage() * 0.85);
    }
}
