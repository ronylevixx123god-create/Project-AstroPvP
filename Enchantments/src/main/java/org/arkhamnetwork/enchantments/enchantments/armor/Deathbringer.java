package org.arkhamnetwork.enchantments.enchantments.armor;

import net.astropvp.damagehandler.AstroDamageHandler;
import org.arkhamnetwork.enchantments.util.*;
import org.bukkit.*;
import org.arkhamnetwork.enchantments.*;
import com.rit.sucy.*;
import org.bukkit.plugin.*;
import java.util.*;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;

public class Deathbringer extends CustomEnchantment
{
    public Deathbringer() {
        super("Deathbringer", GeneralUtil.armor, 9);
        this.max = 3;
        this.base = 25.0;
        this.interval = 7.0;
        Bukkit.getScheduler().runTaskLater((Plugin)Enchantments.getInstance(), new Runnable() {
            @Override
            public void run() {
                Deathbringer.this.heroicVersion = EnchantmentAPI.getEnchantment("Planetary Deathbringer");
            }
        }, 2L);
        Bukkit.getPluginManager().registerEvents(new DeathbringEvents(), (Plugin)Enchantments.getInstance());
    }

    public void applyEquipEffect(final Player player, final int enchantLevel) {
        DeathbringEvents.doubleDamage.add(player);
        DeathbringEvents.playerLevels.put(player, enchantLevel);
    }

    public void applyUnequipEffect(final Player player, final int enchantLevel) {
        DeathbringEvents.doubleDamage.remove(player);
        DeathbringEvents.playerLevels.remove(player);
    }

    private static class DeathbringEvents implements Listener
    {
        private static HashSet<Player> doubleDamage;
        private static HashMap<Player, Integer> playerLevels;

        @EventHandler
        public void onEntityDamageByEntity(final EntityDamageByEntityEvent e) {
            final Entity damage = e.getDamager();
            if ((damage instanceof Projectile && DeathbringEvents.doubleDamage.contains(((Projectile)damage).getShooter())) || DeathbringEvents.doubleDamage.contains(damage)) {
                final double random = Math.random();
                final double chance = 0.1 * (DeathbringEvents.playerLevels.containsKey((damage instanceof Projectile) ? ((Projectile)damage).getShooter() : damage) ? DeathbringEvents.playerLevels.get((damage instanceof Projectile) ? ((Projectile)damage).getShooter() : damage) : 1);
                if (random < chance) {
                    AstroDamageHandler.getInstance().getDamageHandler().increaseDamage(200.0, e, "&6&lDeathbringer");
                }
            }
        }

        @EventHandler
        public void onPlayerQuit(final PlayerQuitEvent e) {
            DeathbringEvents.doubleDamage.remove(e.getPlayer());
            DeathbringEvents.playerLevels.remove(e.getPlayer());
        }

        static {
            DeathbringEvents.doubleDamage = new HashSet<Player>();
            DeathbringEvents.playerLevels = new HashMap<Player, Integer>();
        }
    }
}
