package org.arkhamnetwork.enchantments.enchantments.swords;

import org.arkhamnetwork.enchantments.util.*;
import org.bukkit.*;
import org.arkhamnetwork.enchantments.*;
import com.rit.sucy.*;
import org.bukkit.plugin.*;
import org.bukkit.entity.*;
import org.bukkit.event.entity.*;

public class Assassin extends CustomEnchantment {
    public Assassin() {
        super("Assassin", GeneralUtil.swords, 5);
        this.max = 5;
        this.base = 15.0D;
        this.interval = 6.0D;
        Bukkit.getScheduler().runTaskLaterAsynchronously(Enchantments.getInstance(), () -> {
            this.heroicVersion = EnchantmentAPI.getEnchantment("Shadow Assassin");
        }, 2L);
    }

    public void applyEffect(LivingEntity user, LivingEntity target, int enchantLevel, EntityDamageByEntityEvent event) {
        if (user.getWorld().equals(target.getWorld())) {
            double distance = user.getLocation().distance(target.getLocation());
            double dmgMod = 1.0D;

            if (distance < 0.25D) {
                dmgMod = 1.04;
            }

            if (distance < 0.5D) {
                dmgMod = 1.03;
            }

            if (distance < 0.75D) {
                dmgMod = 1.025;
            }

            if (distance < 1.0D) {
                dmgMod = 1.02;
            }

            if (distance < 2.0D) {
                dmgMod = 1.001;
            }
            event.setDamage(event.getDamage() * dmgMod);

        }
    }
}
