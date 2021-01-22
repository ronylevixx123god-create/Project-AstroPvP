package org.arkhamnetwork.enchantments.enchantments.armor;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.iface.RelationParticipator;
import com.massivecraft.factions.struct.Relation;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.arkhamnetwork.enchantments.util.WorldGuardUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

class SelfDestructListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockExplode(final EntityExplodeEvent e) throws Exception {
        if (e.getEntity() != null && e.getEntity().hasMetadata("SelfDestructTNT")) {
            e.setCancelled(true);
            e.setYield(0.0f);
            e.blockList().clear();
            if (WorldGuardUtils.isPvPDisabled(e.getLocation())) {
                return;
            }
            final Location loc = e.getLocation();
            loc.getWorld().playSound(loc, Sound.EXPLODE, 1.0f, 1.0f);
            de.inventivegames.particle.ParticleEffect.LARGE_EXPLODE.sendToPlayers(Bukkit.getOnlinePlayers(), loc, (float) Math.random(), (float) Math.random(), (float) Math.random(), 0.5f, 3);

            final String exploder = e.getEntity().getMetadata("SelfDestructTNT").get(0).asString();
            final Player pExploder = Bukkit.getPlayer(exploder);
            final FPlayer fp = (pExploder != null) ? ((FPlayer) FPlayers.i.get((OfflinePlayer) pExploder)) : null;
            for (final Entity ent : e.getEntity().getNearbyEntities(4.0, 4.0, 4.0)) {
                if (ent instanceof LivingEntity && !ent.hasMetadata("spectator")) {
                    if (ent instanceof Player && fp != null) {
                        final FPlayer fpEnt = (FPlayer) FPlayers.i.get((OfflinePlayer) ent);
                        if (fp.getRelationTo((RelationParticipator) fpEnt).isAtLeast(Relation.TRUCE)) {
                            continue;
                        }
                    }
                    if (WorldGuardUtils.isPvPDisabled(ent.getLocation())) {
                        continue;
                    }
                    ((LivingEntity) ent).damage(16.0);
                    ((LivingEntity) ent).setFireTicks(40);
                    GeneralUtil.pushAwayEntity((LivingEntity) ent, e.getEntity(), 1.7000000476837158);
                }
            }
            e.getEntity().remove();
        }
    }
}
