package org.arkhamnetwork.enchantments.enchantments.armor;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.WorldUtil;
import com.rit.sucy.CustomEnchantment;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import org.arkhamnetwork.enchantments.util.DamageIndicatorUtils;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Frozen extends CustomEnchantment {
    public Frozen() {
        super("Frozen", GeneralUtil.armor, 2);
        this.max = 3;
        this.base = 15.0;
        this.interval = 3.0;
    }

    public void applyDefenseEffect(final Player player, final LivingEntity attacker, final int level, final EntityDamageEvent e) {
        final double random = Math.random();
        final double chance = 0.04 * level;
        final int time = (int) (Math.random() * level) + 1 * level;
        final int amp = (level > 2) ? 1 : 0;

        if (random < chance && e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK && attacker != null) {

            if (AstroItemSkinsAPI.hasSkinEquipped((Player) attacker, SkinType.VALENTINES_2021_HELMET)) {
                attacker.sendMessage(CC.translate("&d&l* NEON EMOTIONS &7Blocked Slow Effect &d&l*"));
                return;
            }

            if (AstroItemSkinsAPI.hasSkinEquipped((Player) attacker, SkinType.CHRISTMAS_BOOTS_SLIPPERS)) {
                attacker.sendMessage(CC.translate("&c&l* SNOWFLAKE FLIPPERS &7Blocked Slow Effect &c&l*"));
                return;
            }
            DamageIndicatorUtils.sendDamageIndicator((Player) attacker, CC.YellowB + "* FROZEN " + CC.Gray + "[" + time + "s]" + CC.YellowB + " *" + CC.YellowB +  "*", "SABOTAGE");

            attacker.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, time * 20, amp));
            final Location l = player.getLocation();
            WorldUtil.sendWorldEffect(l.getBlock(), 57, 2001);
        }
    }
}
