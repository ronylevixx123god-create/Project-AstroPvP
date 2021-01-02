package org.arkhamnetwork.enchantments.enchantments.armor;

import com.rit.sucy.CustomEnchantment;
import org.arkhamnetwork.enchantments.libs.PlayerPotionEffects;
import org.arkhamnetwork.enchantments.libs.SpecificCustomEnchantment;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Glowing extends CustomEnchantment {
    public Glowing() {
        super("Glowing", GeneralUtil.helmets, 2);
        this.max = 1;
        this.base = 15.0;
        this.interval = 0.0;
    }

    public void applyEquipEffect(final Player player, final int level) {
        PlayerPotionEffects.trackPotionEffect(player, new SpecificCustomEnchantment(this, level), new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, level - 1));
    }

    public void applyUnequipEffect(final Player player, final int level) {
        PlayerPotionEffects.untrackPotionEffect(player, new SpecificCustomEnchantment(this, level));
    }
}
