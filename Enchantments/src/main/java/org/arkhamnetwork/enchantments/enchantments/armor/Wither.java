package org.arkhamnetwork.enchantments.enchantments.armor;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.rit.sucy.CustomEnchantment;
import org.arkhamnetwork.enchantments.util.DamageIndicatorUtils;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Wither extends CustomEnchantment {
    public Wither() {
        super("Wither", GeneralUtil.armor, 2);
        this.max = 5;
        this.base = 15.0;
        this.interval = 5.0;
    }

    public void applyDefenseEffect(final LivingEntity player, final LivingEntity attacker, final int level, final EntityDamageEvent e) {
        final double random = Math.random();
        final double chance = 0.015 * level;
        if (random < chance && attacker != null) {
            DamageIndicatorUtils.sendDamageIndicator((Player) attacker, CC.YellowB + "* WITHER " + CC.Gray + "[" + level + "s]" + CC.YellowB + " *" + CC.YellowB +  "*", "WITHER");

            attacker.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 20 * level, level - 1));
        }
    }
}
