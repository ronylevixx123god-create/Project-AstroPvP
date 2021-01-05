/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.customevents.CustomCosmicPlayerEvent
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.entity.Arrow
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityDeathEvent
 *  org.bukkit.event.entity.EntityShootBowEvent
 *  org.bukkit.event.entity.PlayerDeathEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitRunnable
 */
package me.ifamasssxd.killtracker.listeners;

import com.cosmicpvp.cosmicutils.customevents.CustomCosmicPlayerEvent;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import me.ifamasssxd.killtracker.PlayerKillTracker;
import me.ifamasssxd.killtracker.utils.ItemUtils;
import me.ifamasssxd.killtracker.utils.TextUtils;
import net.astropvp.core.AstroCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerListener
implements Listener {
    public static HashMap<String, PlayerKillTracker> playerKills = new HashMap();
    public static HashMap<UUID, Long> lastSoulCollected = new HashMap();

    @EventHandler(priority=EventPriority.MONITOR)
    public void onEntityDeath(EntityDeathEvent e) {
        if (!(e.getEntity() instanceof Player) && e.getEntity().getKiller() != null) {
            Player killer = e.getEntity().getKiller();
            ItemStack hand = killer.getItemInHand();
            if (hand == null || !ItemUtils.isTrackable(hand)) {
                return;
            }
            Tracker tracker = Tracker.getTrackerType(hand);
            if (tracker == null || !tracker.trackMobKills()) {
                return;
            }
            int kills = ItemUtils.getTrackedKills(hand);
            if (kills == -1) {
                kills = 0;
            }
            killer.playSound(killer.getLocation(), Sound.ORB_PICKUP, 1.0f, 1.4f);
            ItemUtils.setTrackedKills(hand, tracker, ++kills);
        }
    }

    @EventHandler(priority=EventPriority.MONITOR)
    public void onPlayerKillPlayer(PlayerDeathEvent e) {
        if (e.getEntity().getKiller() != null) {
            long time = 0;
            EntityDamageByEntityEvent damageEvent;
            Player killer = e.getEntity().getKiller();
            if (killer.equals(e.getEntity())) {
                return;
            }
            ItemStack hand = killer.getItemInHand();
            if (e.getEntity().getLastDamageCause() != null && e.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent && (damageEvent = (EntityDamageByEntityEvent)e.getEntity().getLastDamageCause()).getDamager() instanceof Arrow && (hand == null || hand.getType() != Material.BOW)) {
                return;
            }
            if (hand == null || !ItemUtils.isTrackable(hand) || hand.getType() == Material.BOW) {
                return;
            }
            Tracker tracker = Tracker.getTrackerType(hand);
            if (tracker == null) {
                return;
            }
            if (tracker.trackMobKills()) {
                return;
            }
            if (lastSoulCollected.containsKey(killer.getUniqueId()) && (time = lastSoulCollected.get(killer.getUniqueId()).longValue()) > System.currentTimeMillis()) {
                killer.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "(!) " + ChatColor.RED + "You cannot harvest more Souls for another " + TextUtils.getFormattedCooldown(time) + "!");
                return;
            }
            if (!PlayerListener.canPlayerKillPlayer(killer, e.getEntity()) && (time == PlayerListener.getPlayerExpireTime(killer, e.getEntity()))) {
                killer.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "(!) " + ChatColor.RED + "You cannot collect " + e.getEntity().getName() + "'s Soul for another " + TextUtils.getFormattedCooldown(time));
                return;
            }
            if (!PlayerListener.logPlayerKill(killer, e.getEntity())) {
                return;
            }
            int kills = ItemUtils.getTrackedKills(hand);
            if (kills == -1) {
                kills = 0;
            }
            killer.playSound(killer.getLocation(), Sound.ORB_PICKUP, 1.0f, 1.4f);
            ItemUtils.setTrackedKills(hand, tracker, ++kills);
            lastSoulCollected.put(killer.getUniqueId(), System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(2L));
            Bukkit.getPluginManager().callEvent(new CustomCosmicPlayerEvent(killer, "SOUL_HARVEST", new Object[]{e.getEntity()}));
        }
    }

    @EventHandler
    public void onPlayerShootBow(EntityShootBowEvent e) {
        if (e.getEntity() != null && e.getEntity() instanceof Player && Tracker.getTrackerType(e.getBow()) != null) {
            e.getProjectile().setMetadata("bow_used", new FixedMetadataValue(AstroCore.getInstance(), e.getBow()));
        }
    }

    public static Long getPlayerExpireTime(Player p, Player dead) {
        PlayerKillTracker tracker = playerKills.get(p.getName());
        if (tracker == null) {
            return null;
        }
        return tracker.get(dead.getName());
    }

    public static boolean logPlayerKill(Player p, Player dead) {
        long time;
        if (p.hasMetadata("kill_tracker_cooldown") && p.getMetadata("kill_tracker_cooldown").size() > 0 && (time = p.getMetadata("kill_tracker_cooldown").get(0).asLong()) > System.currentTimeMillis()) {
            return false;
        }
        p.setMetadata("kill_tracker_cooldown", new FixedMetadataValue(AstroCore.getInstance(), System.currentTimeMillis() + 100L));
        PlayerKillTracker tracker = null;
        tracker = playerKills.containsKey(p.getName()) ? playerKills.get(p.getName()) : new PlayerKillTracker();
        tracker.put(dead.getName(), System.currentTimeMillis() + 600000L);
        playerKills.put(p.getName(), tracker);
        return true;
    }

    public static boolean canPlayerKillPlayer(Player p, Player dead) {
        PlayerKillTracker tracker = playerKills.get(p.getName());
        if (tracker != null) {
            Long expire = tracker.get(dead.getName());
            if (expire != null && expire <= System.currentTimeMillis()) {
                tracker.remove(dead.getName());
                return true;
            }
            return false;
        }
        return true;
    }

    @EventHandler(priority=EventPriority.MONITOR)
    public void onEntityDamageEvent(EntityDamageByEntityEvent e) {
        Arrow arrow;
        if (e.getDamager() instanceof Arrow && e.getEntity() instanceof Player && e.getDamager().hasMetadata("bow_used") && e.getDamager().getMetadata("bow_used").size() > 0 && (arrow = (Arrow)e.getDamager()).getShooter() instanceof Player) {
            final Player shooter = (Player)arrow.getShooter();
            final Player p = (Player)e.getEntity();
            if (e.getFinalDamage() >= p.getHealth()) {
                new BukkitRunnable(){

                    public void run() {
                        if (p.isDead() || p.getTicksLived() <= 3) {
                            ItemStack bow;
                            ItemStack itemStack = bow = arrow.hasMetadata("bow_used") && arrow.getMetadata("bow_used").size() > 0 ? (ItemStack) arrow.getMetadata("bow_used").get(0).value() : null;
                            if (shooter != null && bow != null && bow.getType() == Material.BOW) {
                                Long time;
                                Tracker tracker = Tracker.getTrackerType(bow);
                                if (tracker == null) {
                                    return;
                                }
                                if (!PlayerListener.canPlayerKillPlayer(shooter, p) && (time = PlayerListener.getPlayerExpireTime(shooter, p)) != null) {
                                    shooter.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "(!) " + ChatColor.RED + "You cannot collect " + p.getName() + "'s Soul for another " + TextUtils.getFormattedCooldown(time));
                                    return;
                                }
                                if (lastSoulCollected.containsKey(shooter.getUniqueId()) && (time = lastSoulCollected.get(shooter.getUniqueId()).longValue()) > System.currentTimeMillis()) {
                                    shooter.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "(!) " + ChatColor.RED + "You cannot harvest more Souls for another " + TextUtils.getFormattedCooldown(time) + "!");
                                    return;
                                }
                                PlayerListener.logPlayerKill(shooter, p);
                                int kills = ItemUtils.getTrackedKills(bow);
                                if (kills == -1) {
                                    kills = 0;
                                }
                                shooter.playSound(shooter.getLocation(), Sound.ORB_PICKUP, 1.0f, 1.4f);
                                ItemUtils.setTrackedKills(bow, tracker, ++kills);
                                lastSoulCollected.put(shooter.getUniqueId(), System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(2L));
                            }
                        }
                    }
                }.runTaskLater(AstroCore.getInstance(), 1L);
            }
        }
    }
}

