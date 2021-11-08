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
        this.interval = 7.0D;
        Bukkit.getScheduler().runTaskLater((Plugin) Enchantments.getInstance(), new Runnable() {
            public void run() {
                HeroicDeathbringer.this.heroicEnchantment = true;
                HeroicDeathbringer.this.nonHeroicVersion = EnchantmentAPI.getEnchantment("Deathbringer");
            }
        }, 2L);
        Bukkit.getPluginManager().registerEvents(new DeathbringEvents(), (Plugin) Enchantments.getInstance());
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
        private static HashSet<Player> doubleDamage = new HashSet<>();
        private static HashMap<Player, Integer> playerLevels = new HashMap<>();

        private DeathbringEvents() {
        }

        @EventHandler
        public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
            if (!(e.getEntity() instanceof Player)) {
                return;
            }
            if (!(e.getDamager() instanceof Player)) {
                return;
            }
            Entity damage = e.getDamager();

            if (doubleDamage.contains(damage)) {
                double random = Math.random();
                double chance = 0.025D;
                if (random < chance) {
                    e.getEntity().sendMessage(CC.LightPurpleB + "** PLANETARY DEATHBRINGER " + CC.Gray + "[2x DMG] " + CC.LightPurpleB + "**");
                    if(e.getFinalDamage() > 35) {
                        e.setDamage(35);
                        GeneralCooldown.enterCooldown((Player) damage);
                        return;
                    }
                    e.setDamage(e.getDamage(EntityDamageEvent.DamageModifier.BASE) * 2.0);
                    GeneralCooldown.enterCooldown((Player) damage);

                }

            }
        }

        @EventHandler
        public void onPlayerQuit(PlayerQuitEvent e) {
            doubleDamage.remove(e.getPlayer());
            playerLevels.remove(e.getPlayer());
        }
    }
}
