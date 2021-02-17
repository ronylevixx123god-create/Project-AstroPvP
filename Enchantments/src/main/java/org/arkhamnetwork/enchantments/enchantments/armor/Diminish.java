package org.arkhamnetwork.enchantments.enchantments.armor;

import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import java.text.DecimalFormat;

public class Diminish extends CustomEnchantment {
    private DecimalFormat df;

    public Diminish() {
        super("Diminish", GeneralUtil.chestplates, 2);
        this.df = new DecimalFormat("#.##");
        this.max = 6;
        this.base = 15.0;
        this.interval = 3.0;
        Bukkit.getScheduler().runTaskLater((Plugin) Enchantments.getInstance(), (Runnable) new Runnable() {
            @Override
            public void run() {
                Diminish.this.heroicVersion = EnchantmentAPI.getEnchantment("Vengeful Diminish");
            }
        }, 2L);
    }

    public void applyDefenseEffect(final LivingEntity player, final LivingEntity attacker, final int level, final EntityDamageEvent e) {
        if (player.hasMetadata("diminishEffect")) {
            final double maxDmg = player.getMetadata("diminishEffect").get(0).asDouble();
            if (e.getDamage(EntityDamageEvent.DamageModifier.BASE) > maxDmg) {
                //e.setDamage(maxDmg);
                player.removeMetadata("diminishEffect", (Plugin) Enchantments.getInstance());
                return;
            }
        }
        final double random = Math.random();
        final double procChance = 0.0015 * level;
        if (random < procChance && e.getDamage(EntityDamageEvent.DamageModifier.BASE) > 0.0) {
            player.setMetadata("diminishEffect", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), (Object) e.getDamage(EntityDamageEvent.DamageModifier.BASE)));
            ((Player) player).sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "* DIMINISH [" + ChatColor.YELLOW + "DMG: " + this.df.format(Math.max(0.0, e.getDamage(EntityDamageEvent.DamageModifier.BASE))) + ChatColor.BOLD + "] *");
        }
    }
}
