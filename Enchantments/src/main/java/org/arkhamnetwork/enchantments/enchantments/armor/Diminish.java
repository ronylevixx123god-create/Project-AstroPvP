package org.arkhamnetwork.enchantments.enchantments.armor;

import java.text.*;
import org.arkhamnetwork.enchantments.util.*;
import org.arkhamnetwork.enchantments.*;
import com.rit.sucy.*;
import org.bukkit.plugin.*;
import org.bukkit.event.entity.*;
import org.bukkit.metadata.*;
import org.bukkit.entity.*;
import org.bukkit.*;

public class Diminish extends CustomEnchantment
{
    private DecimalFormat df;

    public Diminish() {
        super("Diminish", GeneralUtil.chestplates, 2);
        this.df = new DecimalFormat("#.##");
        this.max = 6;
        this.base = 15.0;
        this.interval = 3.0;
        Bukkit.getScheduler().runTaskLater((Plugin)Enchantments.getInstance(), new Runnable() {
            @Override
            public void run() {
                Diminish.this.heroicVersion = EnchantmentAPI.getEnchantment("Vengeful Diminish");
            }
        }, 2L);
    }

    public void applyDefenseEffect(final LivingEntity player, final LivingEntity attacker, final int level, final EntityDamageEvent e) {
        if (player.hasMetadata("neutralized")) {
            return;
        }
        if (player.hasMetadata("lastDiminsh") && System.currentTimeMillis() - player.getMetadata("lastDiminsh").get(0).asLong() <= 10000L) {
            return;
        }
        player.setMetadata("lastDiminsh", new FixedMetadataValue((Plugin)Enchantments.getInstance(), System.currentTimeMillis()));
        if (player.hasMetadata("diminishEffect")) {
            final double maxDmg = player.getMetadata("diminishEffect").get(0).asDouble();
            if (e.getFinalDamage() > maxDmg) {
                player.removeMetadata("diminishEffect", (Plugin)Enchantments.getInstance());
                return;
            }
        }
        final double random = Math.random();
        final double procChance = 0.005 * level;
        if (random < procChance && e.getFinalDamage() > 0.0) {
            player.setMetadata("diminishEffect", new FixedMetadataValue((Plugin)Enchantments.getInstance(), e.getFinalDamage()));
            ((Player)player).sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "* DIMINISH [" + ChatColor.YELLOW + "DMG: " + this.df.format(Math.max(0.0, e.getFinalDamage())) + ChatColor.BOLD + "] *");
        }
    }
}
