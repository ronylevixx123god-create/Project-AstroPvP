/*
 * Decompiled with CFR 0.150.
 */
package me.ifamasssxd.cosmicbosses.boss.type;

import me.ifamasssxd.cosmicbosses.CosmicBossEggsPlugin;
import me.ifamasssxd.cosmicbosses.boss.rarity.BossRarity;
import me.ifamasssxd.cosmicbosses.utils.ParticleEffect;
import java.util.HashSet;
import java.util.Random;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Wither;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class WitherBoss
extends me.ifamasssxd.cosmicbosses.boss.type.Boss {
    public HashSet<Entity> minions = new HashSet();
    int height_limit_task_id;
    int spawn_y;

    protected WitherBoss() {
        this.type = EntityType.WITHER;
        this.rarity = BossRarity.ULTRA_RARE;
        this.max_hp = 2000.0;
        this.custom_name = ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + "Evil Space Wither";
    }

    @Override
    public void spawnBoss(Location l) {
        final Wither w = (Wither)l.getWorld().spawnEntity(l, EntityType.WITHER);
        w.setMaxHealth(this.max_hp);
        w.setHealth(this.max_hp);
        w.setCustomName(this.custom_name);
        w.setCustomNameVisible(true);
        l.getWorld().playSound(l, Sound.WITHER_SPAWN, 2.0f, 0.5f);
        this.spawn_y = l.getBlockY();
        this.entity = w;
        w.setMetadata("cbe_boss", new FixedMetadataValue((Plugin)CosmicBossEggsPlugin.getPlugin(), this.type.name()));
        me.ifamasssxd.cosmicbosses.boss.type.Boss.boss_map.put(w, this);
        this.height_limit_task_id = CosmicBossEggsPlugin.getPlugin().getServer().getScheduler().runTaskTimer((Plugin)CosmicBossEggsPlugin.getPlugin(), new Runnable(){
            final int final_spawn_y;
            {
                this.final_spawn_y = WitherBoss.this.spawn_y;
            }

            @Override
            public void run() {
                if (w.getLocation().getY() > (double)(this.final_spawn_y + 25)) {
                    w.setVelocity(new Vector(0.0, -1.25, 0.0));
                }
            }
        }, 20L, 20L).getTaskId();
    }

    @Override
    public void onBossDamaged(EntityDamageEvent e) {
        if (e instanceof EntityDamageByEntityEvent && ((EntityDamageByEntityEvent)e).getDamager() instanceof Player) {
            this.playWitherEffect(((EntityDamageByEntityEvent)e).getDamager().getLocation(), 10);
            ((Player)((EntityDamageByEntityEvent)e).getDamager()).addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 60, 0));
            ((Player)((EntityDamageByEntityEvent)e).getDamager()).addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 80, 0));
            if (new Random().nextInt(5) == 0) {
                ((Player)((EntityDamageByEntityEvent)e).getDamager()).addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 0));
            }
        }
    }

    @Override
    public void onBossDeath(EntityDeathEvent e) {
        if (this.minions != null && this.minions.size() > 0) {
            for (Entity en : this.minions) {
                if (en == null || en.isDead() || !(en instanceof LivingEntity)) continue;
                LivingEntity len = (LivingEntity)en;
                this.playWitherEffect(en.getLocation(), 10);
                len.damage(Double.MAX_VALUE);
            }
            this.minions.clear();
            this.minions = null;
        }
        this.playWitherEffect(e.getEntity().getLocation(), 50);
        CosmicBossEggsPlugin.getPlugin().getServer().getScheduler().cancelTask(this.height_limit_task_id);
        Boss.boss_map.remove(this.entity);
    }

    @Override
    public void onBossDamage(EntityDamageEvent e) {
        if (e instanceof EntityDamageByEntityEvent && ((EntityDamageByEntityEvent)e).getDamager() instanceof WitherSkull) {
            WitherSkull ws = (WitherSkull)((EntityDamageByEntityEvent)e).getDamager();
            final Location loc = ws.getLocation();
            if (new Random().nextBoolean()) {
                CosmicBossEggsPlugin.getPlugin().getServer().getScheduler().runTaskLater((Plugin)CosmicBossEggsPlugin.getPlugin(), new Runnable(){

                    @Override
                    public void run() {
                        Skeleton s = (Skeleton)loc.getWorld().spawnEntity(loc, EntityType.SKELETON);
                        s.setSkeletonType(Skeleton.SkeletonType.WITHER);
                        s.setCustomName("Space Alien");
                        s.setCustomNameVisible(true);
                        s.setMaxHealth(s.getMaxHealth() * 2.0);
                        s.setHealth(s.getMaxHealth());
                        WitherBoss.this.playWitherEffect(loc.add(0.0, 1.0, 0.0), 10);
                    }
                }, 5L);
            }
        }
    }

    private void playWitherEffect(final Location l, final int c) {
        CosmicBossEggsPlugin.getPlugin().getServer().getScheduler().runTaskAsynchronously((Plugin)CosmicBossEggsPlugin.getPlugin(), new Runnable(){

            @Override
            public void run() {
                try {
                    ParticleEffect.sendToLocation(ParticleEffect.WITCH_MAGIC, l, new Random().nextFloat(), new Random().nextFloat(), new Random().nextFloat(), 0.1f, c);
                }
                catch (Exception err) {
                    err.printStackTrace();
                }
            }
        });
    }
}

