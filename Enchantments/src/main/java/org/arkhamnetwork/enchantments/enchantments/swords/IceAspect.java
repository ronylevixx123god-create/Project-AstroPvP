package org.arkhamnetwork.enchantments.enchantments.swords;

import com.cosmicpvp.cosmicutils.compat.ASound;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.WorldUtil;
import com.rit.sucy.CustomEnchantment;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import org.arkhamnetwork.enchantments.events.CustomEnchantProcOnEntityEvent;
import org.arkhamnetwork.enchantments.util.DamageIndicatorUtils;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.text.DecimalFormat;

public class IceAspect extends CustomEnchantment {
    public IceAspect() {
        super("IceAspect", GeneralUtil.swords, 2);
        this.max = 3;
        this.base = 15.0;
        this.interval = 3.0;
    }

    public void applyEffect(final LivingEntity player, final LivingEntity playerHit, final int level, final EntityDamageByEntityEvent e) {
        if (playerHit.hasPotionEffect(PotionEffectType.SLOW)) {
            return;
        }
        final double random = Math.random();
        final double chance = 0.035;
        final int time = 2 * level-1;
        if (random < chance && playerHit instanceof Player) {
            final Location l = playerHit.getLocation();
            if (!new CustomEnchantProcOnEntityEvent(player, playerHit, (Event) e, this, level).callEvent()) {
                return;
            }
            if (playerHit.hasMetadata("immune_freeze")) {
                ((Player) playerHit).sendMessage(CC.DarkGrayB + "* DRAGON SLAYER [" + ChatColor.GRAY + "Ice Aspect blocked!" + CC.DarkGrayB + "] *");
                return;
            }



            if (AstroItemSkinsAPI.hasSkinEquipped((Player) playerHit, SkinType.CHRISTMAS_BOOTS_SLIPPERS)) {
                playerHit.sendMessage(CC.translate("&c&l* SNOWFLAKE FLIPPERS &7Blocked Slow Effect &c&l*"));
                return;
            }

            if (AstroItemSkinsAPI.hasSkinEquipped((Player) playerHit, SkinType.VALENTINES_2021_HELMET)) {
                playerHit.sendMessage(CC.translate("&d&l* NEON EMOTIONS &7Blocked Slow Effect &d&l*"));
                return;
            }

            if (AstroItemSkinsAPI.hasSkinEquipped((Player) playerHit, SkinType.VIRUS_HELMET_HAZMAT)) {
                ((Player) playerHit).sendMessage(CC.translate("&2&L* PRISON BREAK &7(Hazmat Helmet) &2&L*"));
                ASound.ANVIL_LAND.play((Player) playerHit, 2.0f, 1.4f);
                return;
            }
            DamageIndicatorUtils.sendDamageIndicator((Player) playerHit, CC.DarkPurpleB + "* ICE ASPECT " + CC.Gray + "[" + time + "]" + CC.DarkPurpleB + "*", "ICE_ASPECT");
            WorldUtil.sendWorldEffect(l.getBlock(), 57, 2001);
        }
    }
}
