/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Projectile
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 */
package me.ifamasssxd.cosmicbosses.struct;

import java.util.HashMap;
import java.util.UUID;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageTracker
extends HashMap<UUID, Integer> {
    private static final long serialVersionUID = 1L;

    public void trackDamage(Entity e, EntityDamageByEntityEvent event) {
        Projectile proj;
        if (event.isCancelled()) {
            return;
        }
        if (e instanceof Projectile && (proj = (Projectile)e).getShooter() instanceof Player) {
            e = (Entity)proj.getShooter();
        }
        if (!(e instanceof Player)) {
            return;
        }
        int damage = this.containsKey(e.getUniqueId()) ? (Integer)this.get(e.getUniqueId()) : 0;
        damage = (int)((double)damage + event.getFinalDamage());
        this.put(e.getUniqueId(), damage);
    }

    public void trackDamage(Entity e, int newDamage) {
        Projectile proj;
        if (e instanceof Projectile && (proj = (Projectile)e).getShooter() instanceof Player) {
            e = (Entity)proj.getShooter();
        }
        if (!(e instanceof Player)) {
            return;
        }
        int damage = this.containsKey(e.getUniqueId()) ? (Integer)this.get(e.getUniqueId()) : 0;
        this.put(e.getUniqueId(), damage += newDamage);
    }
}

