package org.arkhamnetwork.enchantments.enchantments.heroic;

import net.astropvp.damagehandler.AstroDamageHandler;
import org.arkhamnetwork.enchantments.util.*;
import org.bukkit.*;
import org.arkhamnetwork.enchantments.*;
import com.rit.sucy.*;
import org.bukkit.plugin.*;
import org.bukkit.entity.*;
import org.bukkit.event.entity.*;

public class HeroicAssassin extends CustomEnchantment
{
    public HeroicAssassin() {
        super("Shadow Assassin", GeneralUtil.swords, 5);
        this.max = 5;
        this.base = 15.0;
        this.interval = 6.0;
        Bukkit.getScheduler().runTaskLater((Plugin)Enchantments.getInstance(), () -> {
            this.heroicEnchantment = true;
            this.nonHeroicVersion = EnchantmentAPI.getEnchantment("Assassin");
        }, 2L);
    }

    public void applyEffect(final LivingEntity user, final LivingEntity target, final int enchantLevel, final EntityDamageByEntityEvent event) {
        if (!user.getWorld().equals(target.getWorld())) {
            return;
        }
        final double distance = user.getLocation().distance(target.getLocation());
        if (distance < 0.25) {
            AstroDamageHandler.getInstance().getDamageHandler().increaseDamage(87.5, event, "&d&lShadow Assassin");
        }
        else if (distance < 0.5 && distance > 0.25) {
            AstroDamageHandler.getInstance().getDamageHandler().increaseDamage(50.0, event, "&d&lShadow Assassin");
        }
        else if (distance < 0.75 && distance > 0.5) {
            AstroDamageHandler.getInstance().getDamageHandler().increaseDamage(25.0, event, "&d&lShadow Assassin");
        }
        else if (distance < 1.0 && distance > 0.75) {
            AstroDamageHandler.getInstance().getDamageHandler().increaseDamage(10.0, event, "&d&lShadow Assassin");
        }
        else if (distance < 2.0 && distance > 1.0) {
            AstroDamageHandler.getInstance().getDamageHandler().increaseDamage(5.0, event, "&d&lShadow Assassin");
        }
    }
}
