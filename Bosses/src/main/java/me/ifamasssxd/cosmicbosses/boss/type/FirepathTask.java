/*
 * Decompiled with CFR 0.150.
 */
package me.ifamasssxd.cosmicbosses.boss.type;

import me.ifamasssxd.cosmicbosses.utils.Line;
import me.ifamasssxd.cosmicbosses.utils.ParticleEffect;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;

class FirepathTask
implements Runnable {
    LivingEntity boss;
    Location target_loc;
    protected int iteration = 0;

    public FirepathTask(LivingEntity boss, Location target_loc) {
        this.boss = boss;
        this.target_loc = target_loc;
    }

    @Override
    public void run() {
        ++this.iteration;
        if (this.iteration != 6 && this.iteration > 6) {
            return;
        }
        int failover = 0;
        for (Block b : new Line(this.boss.getLocation(), this.target_loc, 0.0)) {
            if (++failover > 100) {
                Bukkit.getLogger().warning("Force exited FirepathTask due to failover > 100...");
                break;
            }
            try {
                ParticleEffect.sendToLocation(ParticleEffect.FLAME, b.getLocation(), new Random().nextFloat(), new Random().nextFloat(), new Random().nextFloat(), 0.05f, 10);
            }
            catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
}

