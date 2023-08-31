package org.arkhamnetwork.enchantments.enchantments.heroic;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import me.astropvp.astropvpskins.utils.ItemSkinUtils;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.DamageIndicatorUtils;
import org.arkhamnetwork.enchantments.util.GeneralCooldown;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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

public class HeroicDeathbringer
        extends CustomEnchantment {
    public HeroicDeathbringer() {
        super("Planetary Deathbringer", GeneralUtil.armor, 9);
        this.max = 3;
        this.base = 25.0;
        this.interval = 7.0;
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
        DeathbringEvents.doubleDamage.remove(player);
        DeathbringEvents.playerLevels.remove(player);
    }

    private static class DeathbringEvents
            implements Listener {
        private static HashSet<Player> doubleDamage = new HashSet();
        private static HashMap<Player, Integer> playerLevels = new HashMap();

        private DeathbringEvents() {
        }

        @EventHandler
        public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
            if (e.isCancelled()) return;
            Entity damage = e.getDamager();
            if (doubleDamage.contains(damage)) {
                double random = Math.random();
                double chance = 0.02 * (double) playerLevels.get(damage instanceof Projectile ? ((Projectile) damage).getShooter() : damage);
                if (random < chance) {
                    e.setDamage(e.getDamage() * 2);
                    DamageIndicatorUtils.sendDamageIndicator((Player) e.getEntity(), CC.translate("&d&l* PLANETARY DEATHBRINGER *"), "CITADEL");
                    e.getEntity().sendMessage(CC.translate("&d&l* PLANETARY DEATHBRINGER *"));
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
