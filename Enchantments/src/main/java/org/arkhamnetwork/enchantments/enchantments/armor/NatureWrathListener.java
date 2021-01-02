package org.arkhamnetwork.enchantments.enchantments.armor;

import org.arkhamnetwork.Arkkit.libs.ParticleEffect;
import org.arkhamnetwork.enchantments.Enchantments;
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
    public void onEntityDamageByEntityEvent(final EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player && ((Player) e.getDamager()).getWalkSpeed() == 0.0f) {
            e.setCancelled(true);
            ParticleEffect.playEffect(ParticleEffect.MAGIC_CRIT, e.getEntity().getLocation(), 0.6f, 30);
        }
        if (e.getEntity() instanceof Player) {
            final Player p = (Player) e.getEntity();

            if (p.hasMetadata("natureWrathTask")) {
                final NatureWrathTask nwt = (NatureWrathTask) p.getMetadata("natureWrathTask").get(0).value();
                if (Math.random() < 0.3 - 0.075 * nwt.enchantLevel) {
                    nwt.cancel();
                    p.setWalkSpeed(0.2f);
                    p.removeMetadata("natureWrathTask", (Plugin) Enchantments.getInstance());
                    ParticleEffect.playEffect(ParticleEffect.LARGE_EXPLODE, p.getLocation(), 0.8f, 4);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerShootBow(final EntityShootBowEvent e) {
        if (e.getEntity() instanceof Player && ((Player) e.getEntity()).getWalkSpeed() == 0.0f) {
            e.setCancelled(true);
            ParticleEffect.playEffect(ParticleEffect.MAGIC_CRIT, e.getEntity().getEyeLocation(), 0.6f, 30);
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
