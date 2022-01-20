package org.arkhamnetwork.enchantments.enchantments.heroic;

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

public class HeroicDeathbringer extends CustomEnchantment
{
    public HeroicDeathbringer() {
        super("Planetary Deathbringer", GeneralUtil.armor, 9);
        this.max = 3;
        this.base = 25.0;
        this.interval = 7.0;
        Bukkit.getScheduler().runTaskLater((Plugin)Enchantments.getInstance(), new Runnable() {
            @Override
            public void run() {
                HeroicDeathbringer.this.heroicEnchantment = true;
                HeroicDeathbringer.this.nonHeroicVersion = EnchantmentAPI.getEnchantment("Deathbringer");
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
            if (!(e.getEntity() instanceof Player)) {
                return;
            }
            final Player player = (Player)e.getEntity();
            if (DeathbringEvents.doubleDamage.contains(damage)) {

                final double random = Math.random();
                double chance = 0.1 * DeathbringEvents.playerLevels.get((damage instanceof Projectile) ? ((Projectile)damage).getShooter() : damage);
                chance *= 1.5;
                if (random < chance) {
                    AstroDamageHandler.getInstance().getDamageHandler().increaseDamage(250.0, e, "&d&lPlanetary Deathbringer");
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
