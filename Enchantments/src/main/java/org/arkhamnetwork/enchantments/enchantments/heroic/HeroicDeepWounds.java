package org.arkhamnetwork.enchantments.enchantments.heroic;

import org.arkhamnetwork.enchantments.util.*;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import org.arkhamnetwork.enchantments.*;
import org.bukkit.plugin.*;
import org.bukkit.metadata.*;

public class HeroicDeepWounds extends HeroicCustomEnchantment
{
    public HeroicDeepWounds() {
        super("Bloody Deep Wounds", "Deep Wounds", GeneralUtil.weapons, 2);
        this.max = 3;
        this.base = 15.0;
        this.interval = 15.0;
    }

    public void applyEffect(final LivingEntity player, final LivingEntity playerHit, final int level, final EntityDamageByEntityEvent e) {
        if (playerHit instanceof Player) {
            playerHit.setMetadata("deepWounds", new FixedMetadataValue((Plugin)Enchantments.getInstance(), System.currentTimeMillis() + level * 1500));
        }
    }
}
