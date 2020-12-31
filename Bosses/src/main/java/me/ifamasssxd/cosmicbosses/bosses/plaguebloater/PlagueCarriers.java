/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.LargeFireball
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.TNTPrimed
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.util.Vector
 */
package me.ifamasssxd.cosmicbosses.bosses.plaguebloater;

import java.util.Collections;
import java.util.Random;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;
import me.ifamasssxd.cosmicbosses.CosmicBosses;
import me.ifamasssxd.cosmicbosses.bosses.plaguebloater.PlagueBloater;
import me.ifamasssxd.cosmicbosses.entities.CustomEntity;
import me.ifamasssxd.cosmicbosses.struct.Boss;
import me.ifamasssxd.cosmicbosses.struct.BossAbility;
import me.ifamasssxd.cosmicbosses.struct.EntityContainer;
import me.ifamasssxd.cosmicbosses.utils.BossUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LargeFireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

public class PlagueCarriers
extends BossAbility
implements EntityContainer {
    Set<Entity> ents = Collections.newSetFromMap(new WeakHashMap());
    Random random = new Random();

    public PlagueCarriers(Boss boss) {
        super(boss);
    }

    @Override
    public Set<Entity> getEntities() {
        return this.ents;
    }

    @Override
    public void clearEntities() {
        for (Entity ent : this.getEntities()) {
            ent.remove();
        }
    }

    @Override
    public void onAbility() {
        if (this.getEntities().size() >= 3) {
            return;
        }
        ((Boss)this.getBoss()).sendMessage((Object)ChatColor.ITALIC + "Go forth my Plague Carriers, defile this land with the power of the plague.");
        int amount = this.random.nextInt(3) + 1;
        LivingEntity e = ((Boss)this.getBoss()).getEntity();
        for (int i = 0; i < amount && this.ents.size() < 10; ++i) {
            Location loc = BossUtils.getRandomLocationNearby(e.getLocation(), 20, 10, 15);
            LivingEntity ghast = (LivingEntity)BossUtils.createCustomEntity(CustomEntity.GHAST, loc);
            ghast.setCustomName((Object)ChatColor.GREEN + "Plague Carrier");
            ghast.setCustomNameVisible(true);
            ghast.setMaxHealth(100.0);
            ghast.setHealth(100.0);
            ghast.setMetadata("no_drops", (MetadataValue)new FixedMetadataValue((Plugin)CosmicBosses.getInstance(), (Object)""));
            ghast.setMetadata("do_not_clear", (MetadataValue)new FixedMetadataValue((Plugin)CosmicBosses.getInstance(), (Object)""));
            ghast.setMetadata("plague_carrier", (MetadataValue)new FixedMetadataValue((Plugin)CosmicBosses.getInstance(), (Object)e.getUniqueId()));
            this.ents.add((Entity)ghast);
        }
        this.activateCooldown();
    }

    @Override
    public long getCooldown() {
        return TimeUnit.SECONDS.toMillis(30L);
    }

    @Override
    public String getAbilityName() {
        return "Plague Carrier";
    }

    public void onCarrierShoot(LargeFireball fireball) {
        PlagueBloater bloater = (PlagueBloater)this.getBoss();
        Vector v = fireball.getVelocity();
        Location l = fireball.getLocation();
        fireball.remove();
        TNTPrimed primed = bloater.getPlagueBomb().createPlagueBomb(l, 200);
        primed.setVelocity(v.multiply(2));
    }
}

