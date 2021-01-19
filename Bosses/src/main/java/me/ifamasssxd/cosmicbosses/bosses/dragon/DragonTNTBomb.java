package me.ifamasssxd.cosmicbosses.bosses.dragon;

import me.ifamasssxd.cosmicbosses.struct.*;
import org.bukkit.craftbukkit.v1_8_R3.*;
import org.arkhamnetwork.Arkkit.libs.*;
import org.bukkit.event.entity.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import me.ifamasssxd.cosmicbosses.*;
import org.bukkit.plugin.*;
import org.bukkit.metadata.*;
import net.minecraft.server.v1_8_R3.*;
import java.util.concurrent.*;

public class DragonTNTBomb extends BossAbility<EndDragon>
{
    public DragonTNTBomb(final EndDragon boss) {
        super(boss);
    }
    
    @Override
    public void onAbility() {
        final WorldServer server = ((CraftWorld)((EndDragon)this.boss).getEntity().getWorld()).getHandle();
        final double bossHealthPercent = ((EndDragon)this.boss).getHealthPercent();
        int tntToSpawn = 4;
        final int extra = (int)Math.floor(bossHealthPercent * 0.01 * 8.0);
        tntToSpawn = Math.min(extra + tntToSpawn, 16);
        final Location spawnLoc = ((EndDragon)this.boss).getEntity().getLocation().subtract(0.0, 1.0, 0.0);
        for (int i = 0; i < tntToSpawn && ((EndDragon)this.boss).getMinions(EntityType.PRIMED_TNT).size() < 30; ++i) {
            final EntityTNTPrimed tntPrimed = new EntityTNTPrimed(new Location(server.getWorld(), spawnLoc.getX() + ThreadLocalRandom.current().nextGaussian() * (i * 2), spawnLoc.getY(), spawnLoc.getZ() + ThreadLocalRandom.current().nextGaussian() * (i * 2)), ((CraftWorld)server.getWorld()).getHandle(), spawnLoc.getX() + ThreadLocalRandom.current().nextGaussian() * (i * 2), spawnLoc.getY(), spawnLoc.getZ() + ThreadLocalRandom.current().nextGaussian() * (i * 2), ((EndDragon)this.boss).getNMSEntity()) {
                public void h() {
                    final boolean onGround = this.onGround;
                    super.h();
                    if (((onGround != this.onGround && this.onGround) || this.onGround) && this.ticksLived > 40) {
                        this.fuseTicks = 0;
                    }
                    else if (this.ticksLived % 3 == 0) {
                        ParticleEffect.sendToLocation(ParticleEffect.LARGE_SMOKE, new Location((org.bukkit.World)this.world.getWorld(), this.locX + this.random.nextGaussian() * 0.30000001192092896, this.locY + this.random.nextGaussian() * 0.30000001192092896, this.locZ + this.random.nextGaussian() * 0.30000001192092896), (float)this.random.nextGaussian(), (float)this.random.nextGaussian(), (float)this.random.nextGaussian(), 0.01f, 5);
                    }
                }
            };
            tntPrimed.fuseTicks = (int)(60.0 + ThreadLocalRandom.current().nextGaussian() * 5.0);
            server.addEntity(tntPrimed, CreatureSpawnEvent.SpawnReason.CUSTOM);
            spawnLoc.getWorld().playSound(spawnLoc, Sound.FIRE_IGNITE, 10.0f, 2.0f);
            final TNTPrimed primed = (TNTPrimed)tntPrimed.getBukkitEntity();
            primed.setMetadata("dragon_bomb", (MetadataValue)new FixedMetadataValue((Plugin)CosmicBosses.get(), (Object)System.currentTimeMillis()));
            primed.setMetadata("minion", (MetadataValue)new FixedMetadataValue((Plugin)CosmicBosses.get(), (Object)((EndDragon)this.boss).getUuid()));
            ((EndDragon)this.boss).getMinions().add((org.bukkit.entity.Entity)primed);
        }
    }
    
    @Override
    public long getCooldown() {
        return TimeUnit.SECONDS.toMillis(6L) + ThreadLocalRandom.current().nextInt(5000);
    }
    
    @Override
    public String getAbilityName() {
        return "TNT Bomber";
    }
}
