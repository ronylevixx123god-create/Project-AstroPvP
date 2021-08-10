package org.arkhamnetwork.enchantments.enchantments.swords;

import com.cosmicpvp.cosmicutils.utils.MaskUtils;
import com.rit.sucy.CustomEnchantment;
import me.ifamasssxd.cosmicmasks.MaskType;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;

public class Vampire extends CustomEnchantment {
    public Vampire() {
        super("Vampire", GeneralUtil.swords, 2);
        this.max = 3;
        this.base = 25.0;
        this.interval = 0.0;
    }

    public void applyEffect(final LivingEntity player, LivingEntity playerHit, int level, EntityDamageByEntityEvent e) {
        double chance = 0.05D * level;
        final double healthGain = level;
        if (Math.random() < chance) {
            if (playerHit instanceof Player && MaskUtils.hasMaskEquipped((Player)playerHit, "NECROMANCER"))
                return;
            Bukkit.getScheduler().runTaskLater((Plugin)Enchantments.getInstance(), new Runnable() {
                public void run() {
                    if (player.isDead() || !player.isValid())
                        return;
                    if (GeneralUtil.healPlayer(player, healthGain))
                        ((Player)player).playSound(player.getLocation(), Sound.DRINK, 0.75F, 2.0F);
                }
            }, 60L);
            ((Player)player).playSound(player.getLocation(), Sound.DRINK, 0.75F, 2.0F);
        }
    }
}
