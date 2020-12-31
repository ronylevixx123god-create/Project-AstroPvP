/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.v1_8_R3.Entity
 *  net.minecraft.server.v1_8_R3.EntityLiving
 *  net.minecraft.server.v1_8_R3.EntityTNTPrimed
 *  net.minecraft.server.v1_8_R3.World
 *  net.minecraft.server.v1_8_R3.WorldServer
 *  org.arkhamnetwork.Arkkit.libs.ParticleEffect
 *  org.bukkit.Location
 *  org.bukkit.Sound
 *  org.bukkit.World
 *  org.bukkit.craftbukkit.v1_8_R3.CraftWorld
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.TNTPrimed
 *  org.bukkit.event.entity.CreatureSpawnEvent$SpawnReason
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package me.ifamasssxd.cosmicbosses.bosses.dragon;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import me.ifamasssxd.cosmicbosses.CosmicBosses;
import me.ifamasssxd.cosmicbosses.bosses.dragon.EndDragon;
import me.ifamasssxd.cosmicbosses.struct.BossAbility;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EntityTNTPrimed;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.WorldServer;
import org.arkhamnetwork.Arkkit.libs.ParticleEffect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class DragonTNTBomb
extends BossAbility<EndDragon> {
    public DragonTNTBomb(EndDragon boss) {
        super(boss);
    }

    @Override
    public void onAbility() {
        WorldServer server = ((CraftWorld)((EndDragon)this.boss).getEntity().getWorld()).getHandle();
        double bossHealthPercent = ((EndDragon)this.boss).getHealthPercent();
        int tntToSpawn = 4;
        int extra = (int)Math.floor(bossHealthPercent * 0.01 * 8.0);
        tntToSpawn = Math.min(extra + tntToSpawn, 16);
        Location spawnLoc = ((EndDragon)this.boss).getEntity().getLocation().subtract(0.0, 1.0, 0.0);
        for (int i = 0; i < tntToSpawn && ((EndDragon)this.boss).getMinions(EntityType.PRIMED_TNT).size() < 30; ++i) {
            EntityTNTPrimed tntPrimed = new EntityTNTPrimed((World)server, spawnLoc.getX() + ThreadLocalRandom.current().nextGaussian() * (double)(i * 2), spawnLoc.getY(), spawnLoc.getZ() + ThreadLocalRandom.current().nextGaussian() * (double)(i * 2), (EntityLiving)((EndDragon)this.boss).getNMSEntity()){

                public void h() {
                    boolean onGround = this.onGround;
                    super.h();
                    if ((onGround != this.onGround && this.onGround || this.onGround) && this.ticksLived > 40) {
                        this.fuseTicks = 0;
                    } else if (this.ticksLived % 3 == 0) {
                        ParticleEffect.sendToLocation((ParticleEffect)ParticleEffect.LARGE_SMOKE, (Location)new Location((org.bukkit.World)this.world.getWorld(), this.locX + this.random.nextGaussian() * (double)0.3f, this.locY + this.random.nextGaussian() * (double)0.3f, this.locZ + this.random.nextGaussian() * (double)0.3f), (float)((float)this.random.nextGaussian()), (float)((float)this.random.nextGaussian()), (float)((float)this.random.nextGaussian()), (float)0.01f, (int)5);
                    }
                }
            };
            tntPrimed.fuseTicks = (int)(60.0 + ThreadLocalRandom.current().nextGaussian() * 5.0);
            server.addEntity((Entity)tntPrimed, CreatureSpawnEvent.SpawnReason.CUSTOM);
            spawnLoc.getWorld().playSound(spawnLoc, Sound.FIRE_IGNITE, 10.0f, 2.0f);
            TNTPrimed primed = (TNTPrimed)tntPrimed.getBukkitEntity();
            primed.setMetadata("dragon_bomb", (MetadataValue)new FixedMetadataValue((Plugin)CosmicBosses.get(), (Object)System.currentTimeMillis()));
            primed.setMetadata("minion", (MetadataValue)new FixedMetadataValue((Plugin)CosmicBosses.get(), (Object)((EndDragon)this.boss).getUuid()));
            ((EndDragon)this.boss).getMinions().add((org.bukkit.entity.Entity)primed);
        }
    }

    @Override
    public long getCooldown() {
        return TimeUnit.SECONDS.toMillis(6L) + (long)ThreadLocalRandom.current().nextInt(5000);
    }

    @Override
    public String getAbilityName() {
        return "TNT Bomber";
    }
}

