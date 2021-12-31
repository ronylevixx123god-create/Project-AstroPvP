/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.rit.sucy.CustomEnchantment
 *  com.rit.sucy.EnchantmentAPI
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
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

public class Diminish
extends CustomEnchantment {
    private DecimalFormat df = new DecimalFormat("#.##");

    public Diminish() {
        super("Diminish", GeneralUtil.chestplates, 2);
        this.max = 6;
        this.base = 15.0;
        this.interval = 3.0;
        Bukkit.getScheduler().runTaskLater((Plugin)Enchantments.getInstance(), new Runnable(){

            @Override
            public void run() {
                Diminish.this.heroicVersion = EnchantmentAPI.getEnchantment((String)"Vengeful Diminish");
            }
        }, 2L);
    }

    public void applyDefenseEffect(LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {
        if (player.hasMetadata("neutralized"))
            return;
        double procChance;
        double random;
/*        if (player.hasMetadata("diminishEffect")) {
            double maxDmg = ((MetadataValue)player.getMetadata("diminishEffect").get(0)).asDouble();
            if (e.getFinalDamage() > maxDmg) {
                e.setDamage(maxDmg);
                player.removeMetadata("diminishEffect", (Plugin)Enchantments.getInstance());
                return;
            }
        }*/
        if ((random = Math.random()) < (procChance = 0.015 * (double)level) && e.getFinalDamage() > 0.0) {
//            player.setMetadata("diminishEffect", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)e.getFinalDamage()));
            ((Player)player).sendMessage((Object)ChatColor.YELLOW + "" + (Object)ChatColor.BOLD + "* DIMINISH [" + (Object)ChatColor.YELLOW + "DMG: " + this.df.format(Math.max(0.0, e.getFinalDamage())) + (Object)ChatColor.BOLD + "] *");
        }
    }
}

