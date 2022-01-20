package org.arkhamnetwork.enchantments.enchantments.swords;

import net.astropvp.damagehandler.AstroDamageHandler;
import org.arkhamnetwork.enchantments.util.*;
import org.bukkit.*;
import org.arkhamnetwork.enchantments.*;
import com.rit.sucy.*;
import org.bukkit.plugin.*;
import org.bukkit.entity.*;
import org.bukkit.event.entity.*;

public class Assassin extends CustomEnchantment
{
    public Assassin() {
        super("Assassin", GeneralUtil.swords, 5);
        this.max = 5;
        this.base = 15.0;
        this.interval = 6.0;
        Bukkit.getScheduler().runTaskLater((Plugin)Enchantments.getInstance(), () -> this.heroicVersion = EnchantmentAPI.getEnchantment("Shadow Assassin"), 2L);
    }

    public void applyEffect(final LivingEntity user, final LivingEntity target, final int enchantLevel, final EntityDamageByEntityEvent event) {
        if (!user.getWorld().equals(target.getWorld())) {
            return;
        }
        final double distance = user.getLocation().distance(target.getLocation());
        if (distance < 0.25) {
            AstroDamageHandler.getInstance().getDamageHandler().increaseDamage(25.0, event, "&e&lAssassin");
        }
        if (distance < 0.5) {
            AstroDamageHandler.getInstance().getDamageHandler().increaseDamage(15.0, event, "&e&lAssassin");
        }
        if (distance < 0.75) {
            AstroDamageHandler.getInstance().getDamageHandler().increaseDamage(10.0, event, "&e&lAssassin");
        }
        if (distance < 1.0) {
            AstroDamageHandler.getInstance().getDamageHandler().increaseDamage(8.0, event, "&e&lAssassin");
        }
        if (distance < 2.0) {
            AstroDamageHandler.getInstance().getDamageHandler().increaseDamage(5.0, event, "&e&lAssassin");
        }
    }
}
