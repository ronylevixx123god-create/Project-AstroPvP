package org.arkhamnetwork.enchantments.enchantments.bow;

import com.cosmicpvp.cosmicutils.utils.WorldGuardUtils;
import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import com.rit.sucy.enchanting.EListener;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class Teleportation extends CustomEnchantment {
    public Teleportation() {
        super("Teleportation", new Material[]{Material.BOW}, 3);
        this.max = 5;
        this.base = 20.0;
        this.interval = 10.0;
        Bukkit.getPluginManager().registerEvents((Listener) new TeleportationEvents(), (Plugin) Enchantments.getInstance());
    }

    private static class TeleportationEvents implements Listener {
        @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = false)
        public void onArrowHit(final EntityDamageByEntityEvent e) throws Exception {
            if (e.getDamager() instanceof Projectile && e.getDamager().hasMetadata("teleportation_level")) {
                Player player = null;
                Player playerHit = null;
                if (((Projectile) e.getDamager()).getShooter() instanceof Player) {
                    player = (Player) ((Projectile) e.getDamager()).getShooter();
                }
                if (e.getEntity() instanceof Player) {
                    playerHit = (Player) e.getEntity();
                }
                if (player == null || playerHit == null) {
                    return;
                }
                final int level = e.getDamager().getMetadata("teleportation_level").get(0).asInt();
                if (player.equals(playerHit)) {
                    e.setCancelled(true);
                    e.setDamage(0.0);
                    if (player instanceof Player) {
                        player.playSound(player.getLocation(), Sound.CAT_HISS, 0.85f, 0.2f);
                    }
                    return;
                }
                if (!(player instanceof Player) || !(playerHit instanceof Player)) {
                    return;
                }
                if (GeneralUtil.isAlly(player, playerHit)) {
                    if (player.getWorld().getName().equals("cosmic-station-1")) {
                        return;
                    }
                    e.setCancelled(true);
                    e.setDamage(0.0);
                    if (e.getDamager() instanceof Projectile) {
                        e.getDamager().remove();
                    }
                    final Location tp = playerHit.getLocation();
                    tp.setPitch(player.getLocation().getPitch());
                    tp.setYaw(player.getLocation().getYaw());
                    if (tp.distanceSquared(player.getLocation()) > Math.pow(level * 6, 2.0)) {
                        player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!) " + ChatColor.RED + "Your ally is too far away to teleport to with this level of Teleporation.");
                        return;
                    }
                    if ((player.getWorld().getEnvironment() == World.Environment.THE_END || player.getWorld().getName().equals("world_koth")) && !WorldGuardUtils.isPvPDisabled(player.getLocation()) && WorldGuardUtils.isPvPDisabled(tp)) {
                        player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "(!) " + ChatColor.RED + "You cannot teleport from PvP-enabled to PvP-disabled with Teleportation in The End or KOTH.");
                        return;
                    }
                    player.setMetadata("ignore_combat_tag", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), (Object) tp));
                    player.teleport(tp, PlayerTeleportEvent.TeleportCause.UNKNOWN);
                    player.removeMetadata("ignore_combat_tag", (Plugin) Enchantments.getInstance());
                    if (playerHit instanceof Player) {
                        player.playSound(player.getLocation(), Sound.ORB_PICKUP, 0.75f, 0.341f);
                    }
                    final Location loc = playerHit.getLocation().add(0.0, 0.5, 0.0);
                    de.inventivegames.particle.ParticleEffect.WITCH_MAGIC.sendToPlayers(Bukkit.getOnlinePlayers(), loc, (float) Math.random(), (float) Math.random(), (float) Math.random(), 0.5f, 35);

                }
            }
        }

        @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
        public void onPlayerShootBow(final EntityShootBowEvent e) throws Exception {
            if (e.getEntity() instanceof Player) {
                final Player p = (Player) e.getEntity();
                if (e.getBow() != null && EListener.getValidEnchantments(e.getBow(), p).containsKey((Object) EnchantmentAPI.getEnchantment("Teleportation"))) {
                    final Location loc = e.getProjectile().getLocation();
                    e.getProjectile().setMetadata("teleportation_level", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), EnchantmentAPI.getEnchantments(e.getBow()).get(EnchantmentAPI.getEnchantment("Teleportation"))));
                    de.inventivegames.particle.ParticleEffect.WITCH_MAGIC.sendToPlayers(Bukkit.getOnlinePlayers(), loc, (float) Math.random(), (float) Math.random(), (float) Math.random(), 0.5f, 35);

                }
            }
        }
    }
}
