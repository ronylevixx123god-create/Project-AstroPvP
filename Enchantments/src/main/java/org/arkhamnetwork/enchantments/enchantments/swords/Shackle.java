package org.arkhamnetwork.enchantments.enchantments.swords;

import com.rit.sucy.CustomEnchantment;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Shackle extends CustomEnchantment {
    public Shackle() {
        super("Shackle", GeneralUtil.weapons, 2);
        this.max = 3;
        this.base = 15.0;
        this.interval = 6.0;
    }

    public void applyEffect(final LivingEntity user, final LivingEntity target, final int enchantLevel, final EntityDamageByEntityEvent event) {
        if (target.hasMetadata("fromMobSpawner")) {
            if ((target.getType() == EntityType.BLAZE && enchantLevel < 2) || (target.getType() == EntityType.MAGMA_CUBE && enchantLevel < 3)) {
                return;
            }
            new BukkitRunnable() {
                public void run() {
                    if (target.getHealth() > 0.0 && target.isValid() && !target.isDead()) {
                        target.setVelocity(new Vector().zero());
                    }
                }
            }.runTaskLater((Plugin) Enchantments.getInstance(), 1L);
        }
    }
}
