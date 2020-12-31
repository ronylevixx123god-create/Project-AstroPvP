/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.arkhamnetwork.Arkkit.libs.ParticleEffect
 *  org.bukkit.Instrument
 *  org.bukkit.Location
 *  org.bukkit.Note
 *  org.bukkit.Sound
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.TNTPrimed
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitRunnable
 */
package me.ifamasssxd.cosmicbosses.bosses.plaguebloater;

import java.util.Collections;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;
import me.ifamasssxd.cosmicbosses.CosmicBosses;
import me.ifamasssxd.cosmicbosses.struct.Boss;
import me.ifamasssxd.cosmicbosses.struct.BossAbility;
import me.ifamasssxd.cosmicbosses.struct.EntityContainer;
import me.ifamasssxd.cosmicbosses.utils.BossUtils;
import org.arkhamnetwork.Arkkit.libs.ParticleEffect;
import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.Note;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class PlagueBomb
extends BossAbility
implements EntityContainer {
    private Set<Entity> primedTNT = Collections.newSetFromMap(new WeakHashMap());
    Random random = new Random();

    public PlagueBomb(Boss boss) {
        super(boss);
        new BukkitRunnable(){
            int loops = 0;

            public void run() {
                if (PlagueBomb.this.getBoss() == null || !((Boss)PlagueBomb.this.getBoss()).isBossSpawned()) {
                    this.cancel();
                    return;
                }
                ++this.loops;
                if (PlagueBomb.this.primedTNT == null || PlagueBomb.this.primedTNT.isEmpty()) {
                    return;
                }
                Iterator iter = PlagueBomb.this.primedTNT.iterator();
                while (iter.hasNext()) {
                    Entity primed = (Entity)iter.next();
                    if (primed == null || !primed.isValid()) {
                        iter.remove();
                        continue;
                    }
                    Location location = primed.getLocation();
                    if (PlagueBomb.this.random.nextInt(10) <= 2) {
                        ParticleEffect.sendToLocation((ParticleEffect)ParticleEffect.LARGE_SMOKE, (Location)location, (float)3.0f, (float)3.0f, (float)3.0f, (float)0.05f, (int)30);
                    }
                    if (this.loops % 20 != 0) continue;
                    for (Entity ent : primed.getNearbyEntities(10.0, 5.0, 10.0)) {
                        if (!(ent instanceof Player)) continue;
                        Player pl = (Player)ent;
                        pl.playNote(location, Instrument.PIANO, new Note(4));
                    }
                }
            }
        }.runTaskTimer((Plugin)CosmicBosses.getInstance(), 2L, 1L);
    }

    @Override
    public void onAbility() {
        this.primedTNT.forEach(tnt -> tnt.remove());
        this.primedTNT.clear();
        for (int i = 0; i < 3; ++i) {
            this.createPlagueBomb(BossUtils.getRandomLocationNearby(((Boss)this.getBoss()).getEntity().getLocation(), 15, 10), 100);
        }
        ((Boss)this.getBoss()).sendMessage("Plague Bomb Dropped", Sound.FIRE_IGNITE, 0.7f);
        this.activateCooldown();
    }

    public TNTPrimed createPlagueBomb(Location l, int fuse) {
        TNTPrimed primed = (TNTPrimed)l.getWorld().spawnEntity(l, EntityType.PRIMED_TNT);
        primed.setMetadata("do_not_clear", (MetadataValue)new FixedMetadataValue((Plugin)CosmicBosses.getInstance(), (Object)""));
        primed.setMetadata("death_plague", (MetadataValue)new FixedMetadataValue((Plugin)CosmicBosses.getInstance(), (Object)""));
        primed.setFuseTicks(fuse);
        this.primedTNT.add((Entity)primed);
        return primed;
    }

    @Override
    public long getCooldown() {
        return TimeUnit.SECONDS.toMillis(15L);
    }

    @Override
    public String getAbilityName() {
        return "Plague Bomb";
    }

    @Override
    public Set<Entity> getEntities() {
        return this.primedTNT;
    }

    @Override
    public void clearEntities() {
        for (Entity e : this.getEntities()) {
            if (e == null) continue;
            e.remove();
        }
        this.primedTNT.clear();
    }

    public Set<Entity> getPrimedTNT() {
        return this.primedTNT;
    }
}

