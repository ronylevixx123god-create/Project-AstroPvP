/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 */
package me.ifamasssxd.cosmicbosses.tasks;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import me.ifamasssxd.cosmicbosses.utils.WorldGuardUtils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class BleedTask
implements Runnable {
    private static ConcurrentHashMap<LivingEntity, Long> bleedingEntities = new ConcurrentHashMap();

    @Override
    public void run() {
        for (Map.Entry<LivingEntity, Long> entry : bleedingEntities.entrySet()) {
            if (entry.getKey() == null || entry.getKey().isDead()) {
                bleedingEntities.remove((Object)entry.getKey());
                continue;
            }
            LivingEntity ent = entry.getKey();
            if (WorldGuardUtils.isPvPDisabled(ent.getLocation())) {
                bleedingEntities.remove((Object)ent);
                continue;
            }
            long time = entry.getValue();
            if (time > System.currentTimeMillis()) {
                int damage = 2;
                if (ent.getHealth() <= 0.0) continue;
                ent.damage(0.0);
                if (ent.getHealth() - (double)damage <= 0.0) {
                    ent.setHealth(0.0);
                    continue;
                }
                ent.setHealth(ent.getHealth() - (double)damage);
                continue;
            }
            bleedingEntities.remove((Object)ent);
        }
    }

    public static void bleedPlayer(Player p, int seconds) {
        bleedingEntities.put((LivingEntity)p, System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(seconds));
    }

    public static boolean isBleeding(Player p) {
        return bleedingEntities.containsKey((Object)p);
    }

    public static void stopBleed(Player p) {
        bleedingEntities.remove((Object)p);
    }
}

