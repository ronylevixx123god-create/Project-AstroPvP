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
package org.arkhamnetwork.enchantments.enchantments.heroic;

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

public class HeroicDiminish
extends CustomEnchantment {
    private DecimalFormat df = new DecimalFormat("#.##");

    public HeroicDiminish() {
        super("Vengeful Diminish", GeneralUtil.chestplates, 2);
        this.max = 6;
        this.base = 15.0;
        this.interval = 3.0;
        Bukkit.getScheduler().runTaskLater((Plugin)Enchantments.getInstance(), new Runnable(){

            @Override
            public void run() {
                HeroicDiminish.this.heroicEnchantment = true;
                HeroicDiminish.this.nonHeroicVersion = EnchantmentAPI.getEnchantment((String)"Diminish");
            }
        }, 2L);
    }

    public void applyDefenseEffect(LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {
        if (player.hasMetadata("neutralized"))
            return;
        double procChance;
        double random;
        if (player.hasMetadata("diminishEffect")) {
            double maxDmg = ((MetadataValue)player.getMetadata("diminishEffect").get(0)).asDouble();
            double dmgToDeal = maxDmg;
            if (dmgToDeal > 0.0 && attacker instanceof Player) {
                attacker.damage(dmgToDeal);
                Player attackerPlayer = (Player)attacker;
                Bukkit.getLogger().info("[Heroic Diminish] Damaging attacker " + attackerPlayer.getName() + " for " + dmgToDeal + " due to being then diminishEffect: " + maxDmg + " maxDmg: " + e.getFinalDamage());
            }
            if (e.getFinalDamage() > maxDmg) {
                e.setDamage(maxDmg);
                player.removeMetadata("diminishEffect", (Plugin)Enchantments.getInstance());
                return;
            }
        }
        if ((random = Math.random()) < (procChance = 0.05 * (double)level)) {
            e.setDamage(e.getDamage() / 2.0);
            player.setMetadata("diminishEffect", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)(e.getFinalDamage() / 2.0)));
            ((Player)player).sendMessage((Object)ChatColor.YELLOW + "" + (Object)ChatColor.BOLD + "* DIMINISH [" + (Object)ChatColor.YELLOW + "MAX DMG: " + this.df.format(e.getFinalDamage() / 2.0) + (Object)ChatColor.BOLD + "] *");
        }
    }
}

