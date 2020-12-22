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
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Slime;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

public class SlimeBoss
extends me.ifamasssxd.cosmicbosses.boss.type.Boss {
    private HashSet<Slime> baby_slimes = new HashSet();

    public SlimeBoss() {
        this.type = EntityType.SLIME;
        this.rarity = BossRarity.COMMON;
        this.max_hp = 400.0;
        this.custom_name = ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + "Queen Slime";
    }

    @Override
    public void spawnBoss(Location l) {
        Slime s = (Slime)l.getWorld().spawnEntity(l, EntityType.SLIME);
        s.setMaxHealth(this.max_hp);
        s.setHealth(this.max_hp);
        s.setSize(6);
        s.setCustomName(this.custom_name);
        s.setCustomNameVisible(true);
        l.getWorld().playSound(l, Sound.SLIME_ATTACK, 2.0f, 0.5f);
        this.entity = s;
        s.setMetadata("cbe_boss", new FixedMetadataValue((Plugin) CosmicBosses.getInstance(), this.type.name()));
        me.ifamasssxd.cosmicbosses.boss.type.Boss.boss_map.put(s, this);
    }

    @Override
    public void onBossDamaged(EntityDamageEvent e) {
        Location loc = e.getEntity().getLocation();
        this.playSlimeEffect(e.getEntity().getLocation(), 20);
        for (int i = 0; i < 1; ++i) {
            Slime s = (Slime)loc.getWorld().spawnEntity(loc, EntityType.SLIME);
            s.setSize(new Random().nextInt(3) + 1);
            s.setCanPickupItems(false);
            s.setRemoveWhenFarAway(true);
            s.setVelocity(new Vector(0.0, 1.1, 0.0));
            s.setMaxHealth(s.getMaxHealth() * 2.0);
            s.setHealth(s.getMaxHealth());
            s.setMetadata("no_split", new FixedMetadataValue((Plugin)CosmicBosses.getInstance(), true));
            if (this.baby_slimes == null) continue;
            this.baby_slimes.add(s);
        }
    }

    @Override
    public void onBossDeath(final EntityDeathEvent e) {
        if (this.baby_slimes != null && this.baby_slimes.size() > 0) {
            for (Slime s : this.baby_slimes) {
                this.playSlimeEffect(s.getLocation(), 30);
                s.remove();
            }
        }
        if (this.baby_slimes != null && this.baby_slimes.size() > 0) {
            this.baby_slimes.clear();
            this.baby_slimes = null;
        }
        this.playSlimeEffect(e.getEntity().getLocation(), 200);
        CosmicBosses.getInstance().getServer().getScheduler().runTaskLater((Plugin)CosmicBosses.getInstance(), new Runnable(){

            @Override
            public void run() {
                SlimeBoss.this.playSlimeEffect(e.getEntity().getLocation(), 200);
            }
        }, 20L);
        Boss.boss_map.remove(this.entity);
    }

    @Override
    public void onBossDamage(EntityDamageEvent e) {
        this.playSlimeEffect(e.getEntity().getLocation(), 10);
    }

    private void playSlimeEffect(final Location l, final int c) {
        CosmicBosses.getInstance().getServer().getScheduler().runTaskAsynchronously((Plugin)CosmicBosses.getInstance(), new Runnable(){

            @Override
            public void run() {
                try {
                    ParticleEffect.sendToLocation(ParticleEffect.SLIME, l, new Random().nextFloat(), new Random().nextFloat(), new Random().nextFloat(), 0.2f, c);
                }
                catch (Exception err) {
                    err.printStackTrace();
                }
            }
        });
    }
}

