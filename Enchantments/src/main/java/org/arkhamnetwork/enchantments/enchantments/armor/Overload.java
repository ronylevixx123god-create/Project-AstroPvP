package org.arkhamnetwork.enchantments.enchantments.armor;

import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.libs.PlayerPotionEffects;
import org.arkhamnetwork.enchantments.libs.SpecificCustomEnchantment;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Overload extends CustomEnchantment {
    public Overload() {
        super("Overload", GeneralUtil.armor, 2);
        this.max = 3;
        this.base = 20.0;
        this.interval = 5.0;
        Bukkit.getScheduler().runTaskLater((Plugin) Enchantments.getInstance(), (Runnable) new Runnable() {
            @Override
            public void run() {
                Overload.this.heroicVersion = EnchantmentAPI.getEnchantment("Godly Overload");
            }
        }, 2L);
    }

    public void applyEquipEffect(final Player player, final int level) {
        PlayerPotionEffects.trackPotionEffect(player, new SpecificCustomEnchantment(this, level), new PotionEffect(PotionEffectType.HEALTH_BOOST, Integer.MAX_VALUE, level - 1));
    }

    public void applyUnequipEffect(final Player player, final int level) {
        PlayerPotionEffects.untrackPotionEffect(player, new SpecificCustomEnchantment(this, level));
    }
}
