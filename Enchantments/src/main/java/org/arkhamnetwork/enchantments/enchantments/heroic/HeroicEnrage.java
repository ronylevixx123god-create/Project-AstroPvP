package org.arkhamnetwork.enchantments.enchantments.heroic;

import org.arkhamnetwork.enchantments.util.*;
import org.bukkit.entity.*;
import org.bukkit.event.entity.*;

public class HeroicEnrage extends HeroicCustomEnchantment
{
    public HeroicEnrage() {
        super("Unrestrained Enrage", "Enrage", GeneralUtil.swords, 5);
        this.max = 3;
        this.base = 15.0;
        this.interval = 6.0;
    }

    public void applyEffect(final LivingEntity user, final LivingEntity target, final int enchantLevel, final EntityDamageByEntityEvent event) {
    }
}
