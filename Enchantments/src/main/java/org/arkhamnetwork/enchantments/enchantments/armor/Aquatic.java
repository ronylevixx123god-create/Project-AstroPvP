package org.arkhamnetwork.enchantments.enchantments.armor;

import com.rit.sucy.*;
import org.arkhamnetwork.enchantments.util.*;
import org.bukkit.entity.*;
import org.bukkit.potion.*;
import org.arkhamnetwork.enchantments.libs.*;

public class Aquatic extends CustomEnchantment
{
    public Aquatic() {
        super("Aquatic", GeneralUtil.helmets, 2);
        this.max = 1;
        this.base = 20.0;
        this.interval = 0.0;
    }
    
    public void applyEquipEffect(final Player player, final int level) {
        PlayerPotionEffects.trackPotionEffect(player, new SpecificCustomEnchantment(this, level), new PotionEffect(PotionEffectType.WATER_BREATHING, Integer.MAX_VALUE, level - 1));
    }
    
    public void applyUnequipEffect(final Player player, final int level) {
        PlayerPotionEffects.untrackPotionEffect(player, new SpecificCustomEnchantment(this, level));
    }
}
