package org.arkhamnetwork.enchantments.enchantments.bow;

import com.cosmicpvp.cosmicutils.utils.ParticleEffect;
import org.bukkit.event.entity.*;
import org.bukkit.metadata.*;
import org.arkhamnetwork.enchantments.util.*;
import org.bukkit.entity.*;
import org.bukkit.potion.*;
import org.arkhamnetwork.Arkkit.libs.*;
import org.bukkit.*;
import java.util.*;
import org.bukkit.event.*;

final class ExplosiveListener implements Listener
{
    private boolean factions_enabled;
    
    public ExplosiveListener() {
        this.factions_enabled = false;
        this.factions_enabled = Bukkit.getPluginManager().isPluginEnabled("Factions");
    }
    
    @EventHandler
    public void onProjectileHit(final ProjectileHitEvent e) {
        if (e.getEntity().hasMetadata(Explosive.explosiveEncahntmentMetadata)) {
            final Location loc = e.getEntity().getLocation();
            if (e.getEntity().getPassenger() != null) {
                e.getEntity().getPassenger().remove();
            }
            if (WorldGuardUtils.isPvPDisabled(loc)) {
                return;
            }
            final int radius;
            final int level = radius = e.getEntity().getMetadata(Explosive.explosiveEncahntmentMetadata).get(0).asInt();
            final String enchantment_owner = e.getEntity().hasMetadata(Explosive.explosiveEncahntmentOwnerMetadata) ? e.getEntity().getMetadata(Explosive.explosiveEncahntmentOwnerMetadata).get(0).asString() : null;
            for (final Entity ent : e.getEntity().getNearbyEntities((double)radius, (double)radius, (double)radius)) {
                if (ent instanceof LivingEntity) {
                    if (ent.hasMetadata("spectator")) {
                        continue;
                    }
                    if (!GeneralUtil.canEffectEntity(ent)) {
                        continue;
                    }
                    if (enchantment_owner != null && ent instanceof Player) {
                        if (((Player)ent).getName().equals(enchantment_owner)) {
                            continue;
                        }
                        if (this.isAlly((Player)ent, Bukkit.getPlayer(enchantment_owner))) {
                            continue;
                        }
                    }
                    if (!(ent instanceof LivingEntity)) {
                        continue;
                    }
                    ((LivingEntity)ent).addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 20 * level, 1));
                }
            }
            ParticleEffect.sendToLocation(ParticleEffect.LARGE_EXPLODE, loc, (float)Math.random(), (float)Math.random(), (float)Math.random(), 0.5f, 8);
        }
    }
    
    private boolean isAlly(final Player p1, final Player p2) {
        return GeneralUtil.isAlly(p1, p2);
    }
}
