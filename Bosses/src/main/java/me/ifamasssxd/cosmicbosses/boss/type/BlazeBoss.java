/*
 * Decompiled with CFR 0.150.
 */
package me.ifamasssxd.cosmicbosses.boss.type;

import me.ifamasssxd.cosmicbosses.CosmicBosses;
import me.ifamasssxd.cosmicbosses.utils.ParticleEffect;
import java.util.HashSet;
import java.util.Random;

import me.ifamasssxd.cosmicbosses.boss.rarity.BossRarity;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

public class BlazeBoss
extends me.ifamasssxd.cosmicbosses.boss.type.Boss {
    public HashSet<Entity> minions = new HashSet();

    protected BlazeBoss() {
        this.type = EntityType.BLAZE;
        this.rarity = BossRarity.RARE;
        this.max_hp = 1500.0;
        this.custom_name = ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + "Prince Blaze";
        this.sys_name = "Blaze";
    }

    @Override
    public void spawnBoss(Location l) {
        Blaze b = (Blaze)l.getWorld().spawnEntity(l, EntityType.BLAZE);
        b.setMaxHealth(this.max_hp);
        b.setHealth(this.max_hp);
        b.setCustomName(this.custom_name);
        b.setCustomNameVisible(true);
        l.getWorld().playSound(l, Sound.BLAZE_BREATH, 2.0f, 0.5f);
        this.entity = b;
        b.setMetadata("cbe_boss", new FixedMetadataValue((Plugin) CosmicBosses.getInstance(), this.type.name()));
        me.ifamasssxd.cosmicbosses.boss.type.Boss.boss_map.put(b, this);
    }

    @Override
    public void onBossDamaged(EntityDamageEvent e) {
        if (e instanceof EntityDamageByEntityEvent && ((EntityDamageByEntityEvent)e).getDamager() instanceof Player) {
            ((Player)((EntityDamageByEntityEvent)e).getDamager()).setFireTicks(60);
        }
        this.playFireEffect(e.getEntity().getLocation(), 20);
    }

    @Override
    public void onBossDeath(EntityDeathEvent e) {
        if (this.minions != null && this.minions.size() > 0) {
            for (Entity en : this.minions) {
                if (en == null || en.isDead() || !(en instanceof LivingEntity)) continue;
                LivingEntity len = (LivingEntity)en;
                this.playFireEffect(en.getLocation(), 10);
                len.damage(Double.MAX_VALUE);
            }
            this.minions.clear();
            this.minions = null;
        }
        this.playFireEffect(e.getEntity().getLocation(), 50);
        Boss.boss_map.remove(this.entity);
    }

    @Override
    public void onBossDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Fireball) {
            if (new Random().nextInt(3) == 0) {
                MagmaCube ms = (MagmaCube)e.getEntity().getWorld().spawnEntity(e.getEntity().getLocation().add(0.0, 1.0, 0.0), EntityType.MAGMA_CUBE);
                ms.setSize(new Random().nextInt(3) + 1);
                ms.setCanPickupItems(false);
                ms.setRemoveWhenFarAway(true);
                ms.setMetadata("no_split", new FixedMetadataValue((Plugin)CosmicBosses.getInstance(), true));
                this.minions.add(ms);
            } else if (new Random().nextInt(5) == 0) {
                Blaze b = (Blaze)e.getEntity().getWorld().spawnEntity(e.getEntity().getLocation().add(0.0, 2.0, 0.0), EntityType.BLAZE);
                b.setCanPickupItems(false);
                b.setRemoveWhenFarAway(true);
                this.minions.add(b);
            }
        }
    }

    private void playFireEffect(final Location l, final int c) {
        CosmicBossEggsPlugin.getPlugin().getServer().getScheduler().runTaskAsynchronously((Plugin)CosmicBosses.getInstance(), new Runnable(){

            @Override
            public void run() {
                try {
                    ParticleEffect.sendToLocation(ParticleEffect.FLAME, l, new Random().nextFloat(), new Random().nextFloat(), new Random().nextFloat(), 0.25f, c);
                }
                catch (Exception err) {
                    err.printStackTrace();
                }
            }
        });
    }
}

