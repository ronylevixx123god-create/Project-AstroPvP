package org.arkhamnetwork.enchantments.enchantments.heroic;

import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
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
        double chance = 0.01D * level;
        double amount = 2;


        if (AstroItemSkinsAPI.hasSkinEquipped((Player) player, SkinType.VALENTINES_2021_HELMET)) {
            amount = amount * 1.2;
        }

        if (AstroItemSkinsAPI.hasSkinEquipped((Player) player, SkinType.THANKSGIVING_AMULET_AUTUMN)) {
            amount = amount * 1.33;
        }



        if (random < chance) {
            if (player.getHealth() + amount > player.getMaxHealth()) {
                return;
            }
            player.setHealth(player.getHealth() + amount);
        }

    }
}
