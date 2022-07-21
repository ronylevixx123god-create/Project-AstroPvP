package org.arkhamnetwork.enchantments.enchantments.heroic;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.GeneralCooldown;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.HashSet;

public class HeroicDeathbringer extends CustomEnchantment {
    public HeroicDeathbringer() {
        super("Planetary Deathbringer", GeneralUtil.armor, 9);
        this.max = 3;
        this.base = 25.0D;
        this.interval = 10.0D;
        Bukkit.getScheduler().runTaskLater(Enchantments.getInstance(), new Runnable() {
            public void run() {
                HeroicDeathbringer.this.heroicEnchantment = true;
                HeroicDeathbringer.this.nonHeroicVersion = EnchantmentAPI.getEnchantment("Deathbringer");
            }
        }, 2L);
        Bukkit.getPluginManager().registerEvents(new HeroicDeathbringer.DeathbringEvents(), Enchantments.getInstance());
    }

    public void applyEquipEffect(Player player, int enchantLevel) {
        HeroicDeathbringer.DeathbringEvents.doubleDamage.add(player);
        HeroicDeathbringer.DeathbringEvents.playerLevels.put(player, enchantLevel);
    }

    public void applyUnequipEffect(Player player, int enchantLevel) {
        HeroicDeathbringer.DeathbringEvents.doubleDamage.remove(player);
        HeroicDeathbringer.DeathbringEvents.playerLevels.remove(player);
    }

    private static class DeathbringEvents implements Listener {
        private static HashSet<Player> doubleDamage = new HashSet();
        private static HashMap<Player, Integer> playerLevels = new HashMap();

        private DeathbringEvents() {
        }

        @EventHandler
        public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
            Entity damage = e.getDamager();
            if (damage instanceof Projectile && doubleDamage.contains(((Projectile)damage).getShooter()) || doubleDamage.contains(damage)) {
                double random = Math.random();
                double chance = 0.1D * (double)(playerLevels.containsKey(damage instanceof Projectile ? ((Projectile)damage).getShooter() : damage) ? (Integer)playerLevels.get(damage instanceof Projectile ? ((Projectile)damage).getShooter() : damage) : 1);
                chance *= 1.8D;
                if (random < chance) {
                    e.setDamage(e.getDamage(EntityDamageEvent.DamageModifier.BASE) * 1.75D);
                }
            }

        }

        @EventHandler
        public void onPlayerQuit(PlayerQuitEvent e) {
            doubleDamage.remove(e.getPlayer());
            playerLevels.remove(e.getPlayer());
        }

        // $FF: synthetic method
        DeathbringEvents(Object x0) {
            this();
        }
    }
}
