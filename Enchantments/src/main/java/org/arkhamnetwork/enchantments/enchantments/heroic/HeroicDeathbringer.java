package org.arkhamnetwork.enchantments.enchantments.heroic;

import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import java.util.HashMap;
import java.util.HashSet;
import org.arkhamnetwork.enchantments.Enchantments;
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

public class HeroicDeathbringer extends CustomEnchantment {
    public HeroicDeathbringer() {
        super("Planetary Deathbringer", GeneralUtil.armor, 9);
        this.max = 3;
        this.base = 25.0D;
        this.interval = 7.0D;
        Bukkit.getScheduler().runTaskLater((Plugin)Enchantments.getInstance(), new Runnable() {
            public void run() {
                HeroicDeathbringer.this.heroicEnchantment = true;
                HeroicDeathbringer.this.nonHeroicVersion = EnchantmentAPI.getEnchantment("Deathbringer");
            }
        },  2L);
        Bukkit.getPluginManager().registerEvents(new DeathbringEvents(), (Plugin)Enchantments.getInstance());
    }

    public void applyEquipEffect(Player player, int enchantLevel) {
        DeathbringEvents.doubleDamage.add(player);
        DeathbringEvents.playerLevels.put(player, Integer.valueOf(enchantLevel));
    }

    public void applyUnequipEffect(Player player, int enchantLevel) {
        DeathbringEvents.doubleDamage.remove(player);
        DeathbringEvents.playerLevels.remove(player);
    }

    private static class DeathbringEvents implements Listener {
        private DeathbringEvents() {}

        @EventHandler
        public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
            Entity damage = e.getDamager();
            if (doubleDamage.contains(damage)) {
                double random = Math.random();
                double chance = 0.02D * playerLevels.get((damage instanceof Projectile) ? ((Projectile) damage).getShooter() : damage);
                chance *= 0.5D;
                if (random < chance) {
                    e.setDamage(e.getDamage(EntityDamageEvent.DamageModifier.BASE) * 1.2D);
                }

            }
        }

        @EventHandler
        public void onPlayerQuit(PlayerQuitEvent e) {
            doubleDamage.remove(e.getPlayer());
            playerLevels.remove(e.getPlayer());
        }

        private static HashSet<Player> doubleDamage = new HashSet<>();

        private static HashMap<Player, Integer> playerLevels = new HashMap<>();
    }
}
