package me.ifamasssxd.cosmicbosses.struct;

import java.util.*;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;

public class DamageTracker extends HashMap<UUID, Integer>
{
    private static final long serialVersionUID = 1L;
    
    public void trackDamage(Entity e, final EntityDamageByEntityEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (e instanceof Projectile) {
            final Projectile proj = (Projectile)e;
            if (proj.getShooter() instanceof Player) {
                e = (Entity)proj.getShooter();
            }
        }
        if (!(e instanceof Player)) {
            return;
        }
        int damage = this.containsKey(e.getUniqueId()) ? (this).get(e.getUniqueId()) : 0;
        damage += (int)event.getFinalDamage();
        this.put(e.getUniqueId(), damage);
    }
    
    public void trackDamage(Entity e, final int newDamage) {
        if (e instanceof Projectile) {
            final Projectile proj = (Projectile)e;
            if (proj.getShooter() instanceof Player) {
                e = (Entity)proj.getShooter();
            }
        }
        if (!(e instanceof Player)) {
            return;
        }
        int damage = this.containsKey(e.getUniqueId()) ? (this).get(e.getUniqueId()) : 0;
        damage += newDamage;
        this.put(e.getUniqueId(), damage);
    }
}
