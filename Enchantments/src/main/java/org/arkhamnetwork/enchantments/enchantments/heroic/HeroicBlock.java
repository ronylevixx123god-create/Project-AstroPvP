package org.arkhamnetwork.enchantments.enchantments.heroic;

import org.arkhamnetwork.enchantments.util.*;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import com.cosmicpvp.cosmicutils.utils.*;
import org.bukkit.*;

public class HeroicBlock extends HeroicCustomEnchantment
{
    public HeroicBlock() {
        super("Reflective Block", "Block", false);
        this.setNaturalMaterials(GeneralUtil.swords);
        this.max = 3;
        this.base = 17.0;
        this.interval = 4.0;
    }

    public void applyDefenseEffect(final LivingEntity damaged, final LivingEntity attacker, final int level, final EntityDamageEvent e) {
        if (damaged instanceof Player) {
            if (damaged.hasMetadata("neutralized")) {
                return;
            }
            if (damaged.hasMetadata("noDefenseProcs")) {
                return;
            }
            if (damaged.hasMetadata("heroicCorruptTaskID") && Math.random() < 0.2) {
                return;
            }
            if (damaged.hasMetadata("noReflectiveBlock")) {
                return;
            }
            final Player player = (Player)damaged;
            double random = Math.random();
            double chance = (player.isBlocking() ? 0.001D : 0.001D) * level;
            if (random < chance) {
                final double reduceDamage = 0.5;
                final double finalDamage = Math.max(0.0, e.getFinalDamage());
                if (finalDamage <= 0.0) {
                    return;
                }
                double damageToReflect = finalDamage * 0.5;
                if (damageToReflect > 20.0) {
                    damageToReflect = 20.0;
                }
                final double reflectChance = 0.08 * level + (player.isBlocking() ? 0.05 : 0.0);
                if (Math.random() < reflectChance && attacker instanceof Player) {
                    player.sendMessage(CC.GreenB + "* BLOCKED [" + NumberUtils.formatSeconds(finalDamage) + " DMG] *");
                    if (damageToReflect > 0.0) {
                        final Player attacking = (Player)attacker;
                        player.getWorld().playSound(player.getLocation(), Sound.ZOMBIE_METAL, 10.0f, 2.0f);
                        attacking.sendMessage(CC.RedB + "* " + player.getName() + " REFLECTIVE BLOCK [" + CC.Gray + 50 + "% DMG" + CC.RedB + "]");
                    }
                }
            }
        }
    }
}
