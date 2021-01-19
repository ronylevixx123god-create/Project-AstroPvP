package me.ifamasssxd.cosmicbosses.tasks;

import me.ifamasssxd.cosmicbosses.utils.*;
import java.util.*;
import org.bukkit.entity.*;
import java.util.concurrent.*;

public class BleedTask implements Runnable
{
    private static ConcurrentHashMap<LivingEntity, Long> bleedingEntities;
    
    @Override
    public void run() {
        for (final Map.Entry<LivingEntity, Long> entry : BleedTask.bleedingEntities.entrySet()) {
            if (entry.getKey() == null || entry.getKey().isDead()) {
                BleedTask.bleedingEntities.remove(entry.getKey());
            }
            else {
                final LivingEntity ent = entry.getKey();
                if (WorldGuardUtils.isPvPDisabled(ent.getLocation())) {
                    BleedTask.bleedingEntities.remove(ent);
                }
                else {
                    final long time = entry.getValue();
                    if (time > System.currentTimeMillis()) {
                        final int damage = 2;
                        if (ent.getHealth() <= 0.0) {
                            continue;
                        }
                        ent.damage(0.0);
                        if (ent.getHealth() - damage <= 0.0) {
                            ent.setHealth(0.0);
                        }
                        else {
                            ent.setHealth(ent.getHealth() - damage);
                        }
                    }
                    else {
                        BleedTask.bleedingEntities.remove(ent);
                    }
                }
            }
        }
    }
    
    public static void bleedPlayer(final Player p, final int seconds) {
        BleedTask.bleedingEntities.put((LivingEntity)p, System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(seconds));
    }
    
    public static boolean isBleeding(final Player p) {
        return BleedTask.bleedingEntities.containsKey(p);
    }
    
    public static void stopBleed(final Player p) {
        BleedTask.bleedingEntities.remove(p);
    }
    
    static {
        BleedTask.bleedingEntities = new ConcurrentHashMap<LivingEntity, Long>();
    }
}
