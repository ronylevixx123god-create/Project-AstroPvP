/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.arkhamnetwork.Arkkit.libs.ParticleEffect
 *  org.bukkit.Location
 *  org.bukkit.Sound
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitRunnable
 */
package me.ifamasssxd.cosmicbosses.bosses.skeletonhorse;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import me.ifamasssxd.cosmicbosses.CosmicBosses;
import me.ifamasssxd.cosmicbosses.struct.Boss;
import me.ifamasssxd.cosmicbosses.struct.BossAbility;
import me.ifamasssxd.cosmicbosses.struct.Phase;
import org.arkhamnetwork.Arkkit.libs.ParticleEffect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class EmpoweredArrow
extends BossAbility {
    boolean isEmpowered = false;
    Random random = new Random();

    public EmpoweredArrow(Boss boss) {
        super(boss);
    }

    @Override
    public void onAbility() {
        if (!((Boss)this.getBoss()).getEntity().hasMetadata("empower_arrow")) {
            new BukkitRunnable(){
                int counter = 140;

                public void run() {
                    --this.counter;
                    if (EmpoweredArrow.this.getBoss() == null || ((Boss)EmpoweredArrow.this.getBoss()).getEntity().isDead() || ((Boss)EmpoweredArrow.this.getBoss()).getPhase() == Phase.TWO) {
                        this.cancel();
                        return;
                    }
                    if (((Boss)EmpoweredArrow.this.getBoss()).getEntity().hasMetadata("empower_arrow")) {
                        ParticleEffect.playEffect((ParticleEffect)ParticleEffect.LAVA, (Location)((Boss)EmpoweredArrow.this.getBoss()).getEntity().getLocation(), (float)0.6f, (int)50);
                        ParticleEffect.playEffect((ParticleEffect)ParticleEffect.PORTAL, (Location)((Boss)EmpoweredArrow.this.getBoss()).getEntity().getLocation(), (float)0.6f, (int)10);
                        return;
                    }
                    if (EmpoweredArrow.this.isEmpowered) {
                        this.cancel();
                        EmpoweredArrow.this.isEmpowered = false;
                        return;
                    }
                    if (this.counter <= 0) {
                        ((Boss)EmpoweredArrow.this.getBoss()).getEntity().setMetadata("empower_arrow", (MetadataValue)new FixedMetadataValue((Plugin)CosmicBosses.getInstance(), (Object)""));
                        ((Boss)EmpoweredArrow.this.getBoss()).getEntity().getWorld().playSound(((Boss)EmpoweredArrow.this.getBoss()).getEntity().getLocation(), Sound.SKELETON_IDLE, 10.0f, 0.5f);
                        EmpoweredArrow.this.isEmpowered = true;
                    } else {
                        ParticleEffect.playEffect((ParticleEffect)ParticleEffect.PORTAL, (Location)((Boss)EmpoweredArrow.this.getBoss()).getEntity().getLocation(), (float)0.6f, (int)50);
                    }
                }
            }.runTaskTimer((Plugin)CosmicBosses.getInstance(), 20L, 1L);
        }
        this.activateCooldown();
    }

    @Override
    public long getCooldown() {
        return TimeUnit.SECONDS.toMillis(10L);
    }

    @Override
    public String getAbilityName() {
        return "Empowered Arrow";
    }
}

