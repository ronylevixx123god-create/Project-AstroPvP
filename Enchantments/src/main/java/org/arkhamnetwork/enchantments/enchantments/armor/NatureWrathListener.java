package org.arkhamnetwork.enchantments.enchantments.armor;

import org.arkhamnetwork.enchantments.Enchantments;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.Plugin;

final class NatureWrathListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDamageByEntityEvent(final EntityDamageByEntityEvent e) throws Exception {
        if (e.getDamager() instanceof Player && ((Player) e.getDamager()).getWalkSpeed() == 0.0f) {
            e.setCancelled(true);
            de.inventivegames.particle.ParticleEffect.MAGIC_CRIT.sendToPlayers(Bukkit.getOnlinePlayers(), e.getEntity().getLocation(), 0f, 0.5f, 0f, 0.6f, 30);

        }
        if (e.getEntity() instanceof Player) {
            final Player p = (Player) e.getEntity();

            if (p.hasMetadata("natureWrathTask")) {
                final NatureWrathTask nwt = (NatureWrathTask) p.getMetadata("natureWrathTask").get(0).value();
                if (Math.random() < 0.3 - 0.075 * nwt.enchantLevel) {
                    nwt.cancel();
                    p.setWalkSpeed(0.2f);
                    p.removeMetadata("natureWrathTask", (Plugin) Enchantments.getInstance());
                    de.inventivegames.particle.ParticleEffect.LARGE_EXPLODE.sendToPlayers(Bukkit.getOnlinePlayers(), e.getEntity().getLocation(), 0f, 0.5f, 0f, 0.6f, 30);

                }
            }
        }
    }

    @EventHandler
    public void onPlayerShootBow(final EntityShootBowEvent e) throws Exception {
        if (e.getEntity() instanceof Player && ((Player) e.getEntity()).getWalkSpeed() == 0.0f) {
            e.setCancelled(true);
            de.inventivegames.particle.ParticleEffect.MAGIC_CRIT.sendToPlayers(Bukkit.getOnlinePlayers(), e.getEntity().getLocation(), 0f, 0.5f, 0f, 0.6f, 30);

        }
    }

    @EventHandler
    public void onPlayerDeath(final PlayerRespawnEvent e) {
        if (e.getPlayer().hasMetadata("natureWrathTask")) {
            final NatureWrathTask nwt = (NatureWrathTask) e.getPlayer().getMetadata("natureWrathTask").get(0).value();
            nwt.cancel();
            e.getPlayer().removeMetadata("natureWrathTask", (Plugin) Enchantments.getInstance());
        }
    }

    @EventHandler
    public void onPlayerQuit(final PlayerQuitEvent e) {
        if (e.getPlayer().getWalkSpeed() == 0.0f) {
            e.getPlayer().setWalkSpeed(0.2f);
        }
        if (e.getPlayer().hasMetadata("natureWrathTask")) {
            final NatureWrathTask nwt = (NatureWrathTask) e.getPlayer().getMetadata("natureWrathTask").get(0).value();
            nwt.cancel();
            e.getPlayer().removeMetadata("natureWrathTask", (Plugin) Enchantments.getInstance());
        }
    }
}
