package org.arkhamnetwork.enchantments.enchantments.weapons;

import com.rit.sucy.*;
import org.arkhamnetwork.enchantments.util.*;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import org.bukkit.scheduler.*;
import org.bukkit.util.*;
import org.arkhamnetwork.enchantments.*;
import org.bukkit.plugin.*;

public class Shackle extends CustomEnchantment
{
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
            }.runTaskLater((Plugin)Enchantments.getInstance(), 1L);
        }
    }
}
