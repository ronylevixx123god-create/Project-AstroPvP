package org.arkhamnetwork.enchantments.enchantments.armor;

import com.rit.sucy.*;
import org.arkhamnetwork.enchantments.util.*;
import org.bukkit.entity.*;
import org.bukkit.potion.*;
import org.arkhamnetwork.enchantments.libs.*;

public class Springs extends CustomEnchantment
{
    public Springs() {
        super("Springs", GeneralUtil.boots, 2);
        this.max = 3;
        this.base = 7.0;
        this.interval = 5.0;
    }
    
    public void applyEquipEffect(final Player player, final int level) {
        PlayerPotionEffects.trackPotionEffect(player, new SpecificCustomEnchantment(this, level), new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, level - 1));
    }
    
    public void applyUnequipEffect(final Player player, final int level) {
        PlayerPotionEffects.untrackPotionEffect(player, new SpecificCustomEnchantment(this, level));
    }
}
