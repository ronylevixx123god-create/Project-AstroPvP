package me.astropvp.astropvpskins.objects.skins.swords;

import com.cosmicpvp.cosmicutils.utils.WorldGuardUtils;
import me.astropvp.astropvpskins.objects.ItemSkin;
import me.ifamasssxd.cosmicduel.DuelAPI;
import net.astropvp.core.killtracker.KillTrackerAPI;
import net.astropvp.damagehandler.AstroDamageHandler;
import org.arkhamnetwork.enchantments.enchantments.heroic.HeroicTrap;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class OrnamentalCarnageSkin extends ItemSkin {

    public void onAttack(Player damager, Entity damaged, EntityDamageByEntityEvent event) {
        if (DuelAPI.isSpectator(damager)) return;
        if (damager.hasMetadata("frozenSkrrt")) return;

        if (event.isCancelled()) return;
        if (!WorldGuardUtils.isPvPEnabled(damaged.getLocation()) || !WorldGuardUtils.isPvPEnabled(damager.getLocation()))
            return;
        if (damaged.hasMetadata("titantrap"))
            AstroDamageHandler.getInstance().getDamageHandler().increaseDamage(10, event, "Ornamental Carnage");
        if (Math.random() > 0.02) {
            AstroDamageHandler.getInstance().getDamageHandler().increaseDamage(20, event, "Ornamental Carnage");
        }

        if (damaged instanceof Player) {
            new HeroicTrap().applyEffect(damager, (LivingEntity) damaged, 5, event);
        }
    }

    public void onAttacked(Player damaged, Entity damager, EntityDamageByEntityEvent event) {
        if (event.isCancelled()) return;
        if (!WorldGuardUtils.isPvPEnabled(damaged.getLocation()) || !WorldGuardUtils.isPvPEnabled(damager.getLocation()))
            return;
        if (damager instanceof Player) {
            if (KillTrackerAPI.hasGodMode((Player) damager)) {
                AstroDamageHandler.getInstance().getDamageHandler().reduceDamage(15, event, "Ornamental Carnage");
            } else {
                AstroDamageHandler.getInstance().getDamageHandler().reduceDamage(5, event, "Ornamental Carnage");
            }
        }
    }
}
