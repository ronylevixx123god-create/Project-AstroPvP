package me.ifamasssxd.cosmicbosses.bosses.plaguebloater;

import me.ifamasssxd.cosmicbosses.struct.*;
import java.util.*;
import me.ifamasssxd.cosmicbosses.utils.*;
import me.ifamasssxd.cosmicbosses.entities.*;
import me.ifamasssxd.cosmicbosses.*;
import org.bukkit.plugin.*;
import org.bukkit.metadata.*;
import org.bukkit.*;
import java.util.concurrent.*;
import org.bukkit.util.*;
import org.bukkit.entity.*;
import org.bukkit.util.Vector;

public class PlagueCarriers extends BossAbility implements EntityContainer
{
    Set<Entity> ents;
    Random random;
    
    public PlagueCarriers(final Boss boss) {
        super(boss);
        this.ents = Collections.newSetFromMap(new WeakHashMap<Entity, Boolean>());
        this.random = new Random();
    }
    
    @Override
    public Set<Entity> getEntities() {
        return this.ents;
    }
    
    @Override
    public void clearEntities() {
        for (final Entity ent : this.getEntities()) {
            ent.remove();
        }
    }
    
    @Override
    public void onAbility() {
        if (this.getEntities().size() >= 3) {
            return;
        }
        this.getBoss().sendMessage(ChatColor.ITALIC + "Go forth my Plague Carriers, defile this land with the power of the plague.");
        final int amount = this.random.nextInt(3) + 1;
        final Entity e = (Entity)this.getBoss().getEntity();
        for (int i = 0; i < amount && this.ents.size() < 10; ++i) {
            final Location loc = BossUtils.getRandomLocationNearby(e.getLocation(), 20, 10, 15);
            final LivingEntity ghast = (LivingEntity)BossUtils.createCustomEntity(CustomEntity.GHAST, loc);
            ghast.setCustomName(ChatColor.GREEN + "Plague Carrier");
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
    
    public void onCarrierShoot(final LargeFireball fireball) {
        final PlagueBloater bloater = (PlagueBloater)this.getBoss();
        final Vector v = fireball.getVelocity();
        final Location l = fireball.getLocation();
        fireball.remove();
        final TNTPrimed primed = bloater.getPlagueBomb().createPlagueBomb(l, 200);
        primed.setVelocity(v.multiply(2));
    }
}
