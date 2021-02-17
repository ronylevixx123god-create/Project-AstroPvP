package org.arkhamnetwork.enchantments.enchantments.heroic;

import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;

public class HeroicEnlighted extends CustomEnchantment {
    public HeroicEnlighted() {
        super("Divine Enlighted", GeneralUtil.armor, 2);
        this.max = 3;
        this.base = 15.0D;
        this.interval = 8.0D;
        Bukkit.getScheduler().runTaskLater((Plugin) Enchantments.getInstance(), () -> {
            this.heroicEnchantment = true;
            this.nonHeroicVersion = EnchantmentAPI.getEnchantment("Enlighted");
        }, 2L);
    }

    public void applyDefenseEffect(LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {
        if (player.hasMetadata("neutralized"))
            return;
        double random = Math.random();
        double chance = 0.008D * level;
        //if (random < chance)
            //Bukkit.getScheduler().runTaskLater((Plugin) Enchantments.getInstance(), () -> GeneralUtil.setPlayerHealth(player, player.getHealth() + 2.0D), 1L);
    }
}
