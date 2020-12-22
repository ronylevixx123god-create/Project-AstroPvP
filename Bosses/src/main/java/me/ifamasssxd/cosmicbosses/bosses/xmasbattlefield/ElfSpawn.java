/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.entity.EntityType
 */
package me.ifamasssxd.cosmicbosses.bosses.xmasbattlefield;

import java.util.concurrent.TimeUnit;
import me.ifamasssxd.cosmicbosses.bosses.xmasbattlefield.BattlefieldSnowman;
import me.ifamasssxd.cosmicbosses.struct.BossAbility;
import me.ifamasssxd.cosmicbosses.utils.BossUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

public class ElfSpawn
extends BossAbility<BattlefieldSnowman> {
    public ElfSpawn(BattlefieldSnowman boss) {
        super(boss);
    }

    @Override
    public void onAbility() {
        if (((BattlefieldSnowman)this.boss).getMinions().size() > 15) {
            return;
        }
        Location location = BossUtils.getRandomLocationNearby(((BattlefieldSnowman)this.boss).getEntity().getLocation(), 5, 2, 3, true);
        if (location != null) {
            ((BattlefieldSnowman)this.boss).spawnMinion(EntityType.ZOMBIE, new Location(((BattlefieldSnowman)this.boss).getLocation().getWorld(), (double)location.getBlockX(), (double)(location.getBlockY() + 1), (double)location.getBlockZ()));
        } else {
            Bukkit.getLogger().info("[BattlefieldSnowman] Unable to find location near snowman for Elf!");
        }
    }

    @Override
    public long getCooldown() {
        return TimeUnit.SECONDS.toMillis(10L);
    }

    @Override
    public String getAbilityName() {
        return "Elf";
    }
}

