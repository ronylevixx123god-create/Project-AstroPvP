package org.arkhamnetwork.enchantments.enchantments.heroic;

import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import net.astropvp.damagehandler.AstroDamageHandler;
import org.arkhamnetwork.enchantments.util.*;
import org.bukkit.*;
import org.arkhamnetwork.enchantments.*;
import com.rit.sucy.*;
import org.bukkit.plugin.*;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;

public class HeroicEnlighted extends CustomEnchantment
{
    public HeroicEnlighted() {
        super("Divine Enlighted", GeneralUtil.armor, 2);
        this.max = 3;
        this.base = 15.0;
        this.interval = 8.0;
        Bukkit.getScheduler().runTaskLater((Plugin)Enchantments.getInstance(), () -> {
            this.heroicEnchantment = true;
            this.nonHeroicVersion = EnchantmentAPI.getEnchantment("Enlighted");
        }, 2L);
    }

    public void applyDefenseEffect(final LivingEntity player, final LivingEntity attacker, int level, final EntityDamageEvent e) {
        if (player.hasMetadata("neutralized")) {
            return;
        }
        if (player.hasMetadata("noDefenseProcs")) {
            return;
        }
        if (!(player instanceof Player)) {
            return;
        }
        if (AstroItemSkinsAPI.hasSkinEquipped((Player)player, SkinType.CHRISTMAS_HELMET_STAR)) {
            level = 4;
        }
        final double random = Math.random();
        final double chance = 0.05 * level;
        if (random < chance) {
            double toGive = 3.0;
            if (AstroItemSkinsAPI.hasSkinEquipped((Player)player, SkinType.VALENTINES_2021_HELMET)) {
                toGive *= 1.05;
            }
            Bukkit.getScheduler().runTaskLater((Plugin)Enchantments.getInstance(), () -> AstroDamageHandler.getInstance().getDamageHandler().healPlayer(player, 2.0, "&d&lDivine Enlighted"), 1L);
        }
    }
}
