/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.rit.sucy.CustomEnchantment
 *  com.rit.sucy.EnchantmentAPI
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Projectile
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.player.PlayerQuitEvent
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.enchantments.enchantments.heroic;

import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.HashSet;

public class HeroicDeathbringer
extends CustomEnchantment {
    public HeroicDeathbringer() {
        super("Planetary Deathbringer", GeneralUtil.armor, 9);
        this.max = 3;
        this.base = 25.0;
        this.interval = 10.0;
        Bukkit.getScheduler().runTaskLater((Plugin)Enchantments.getInstance(), new Runnable(){

            @Override
            public void run() {
                HeroicDeathbringer.this.heroicEnchantment = true;
                HeroicDeathbringer.this.nonHeroicVersion = EnchantmentAPI.getEnchantment((String)"Deathbringer");
            }
        }, 2L);
        Bukkit.getPluginManager().registerEvents((Listener)new DeathbringEvents(), (Plugin)Enchantments.getInstance());
    }

    public void applyEquipEffect(Player player, int enchantLevel) {
        DeathbringEvents.doubleDamage.add(player);
        DeathbringEvents.playerLevels.put(player, enchantLevel);
    }

    public void applyUnequipEffect(Player player, int enchantLevel) {
        DeathbringEvents.doubleDamage.remove((Object)player);
        DeathbringEvents.playerLevels.remove((Object)player);
    }

    private static class DeathbringEvents
    implements Listener {
        private static HashSet<Player> doubleDamage = new HashSet();
        private static HashMap<Player, Integer> playerLevels = new HashMap();

        private DeathbringEvents() {
        }

        @EventHandler
        public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
            Entity damage = e.getDamager();
            if (damage instanceof Projectile && doubleDamage.contains((Object)((Projectile)damage).getShooter()) || doubleDamage.contains((Object)damage)) {
                double random = Math.random();
                double chance = 0.1 * (double)(playerLevels.containsKey(damage instanceof Projectile ? ((Projectile)damage).getShooter() : damage) ? playerLevels.get(damage instanceof Projectile ? ((Projectile)damage).getShooter() : damage) : 1);
                if (random < (chance *= 1.5)) {
                    e.setDamage(e.getDamage() * 2.0);
                }
            }
        }

        @EventHandler
        public void onPlayerQuit(PlayerQuitEvent e) {
            doubleDamage.remove((Object)e.getPlayer());
            playerLevels.remove((Object)e.getPlayer());
        }
    }
}

