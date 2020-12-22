/*
 * Decompiled with CFR 0.150.
 */
package me.ifamasssxd.cosmicbosses.boss.type;

import me.ifamasssxd.cosmicbosses.CosmicBossEggsPlugin;
import me.ifamasssxd.cosmicbosses.utils.ParticleEffect;
import me.ifamasssxd.cosmicbosses.utils.WorldGuardUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import me.ifamasssxd.cosmicbosses.boss.rarity.BossRarity;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowman;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class HeadlessKingBoss
extends me.ifamasssxd.cosmicbosses.boss.type.Boss {
    int target_task_id;
    int anti_water_task_id;
    int exploding_pumpkin_task_id;
    int player_charge_task_id;
    int particle_task_id;
    FirepathTask player_charge_task;

    protected HeadlessKingBoss() {
        this.type = EntityType.SNOWMAN;
        this.rarity = BossRarity.LEGENDARY;
        this.max_hp = 2048.0;
        this.custom_name = ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "The Headless King";
    }

    @Override
    public void spawnBoss(Location l) {
        final Snowman s = (Snowman)l.getWorld().spawnEntity(l, EntityType.SNOWMAN);
        s.setMaxHealth(this.max_hp);
        s.setHealth(this.max_hp);
        s.setCustomName(this.custom_name);
        s.setCustomNameVisible(true);
        s.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));
        s.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 0));
        s.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, Integer.MAX_VALUE, 1));
        s.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 1));
        s.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 0));
        final Horse h = (Horse)l.getWorld().spawnEntity(l, EntityType.HORSE);
        h.setVariant(Horse.Variant.SKELETON_HORSE);
        h.setPassenger(s);
        h.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));
        s.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 0));
        h.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, Integer.MAX_VALUE, 1));
        h.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 1));
        h.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 0));
        h.setMaxHealth(this.max_hp);
        h.setHealth(this.max_hp);
        h.setTamed(true);
        this.entity = s;
        h.setMetadata("cbe_boss_mount", new FixedMetadataValue((Plugin)CosmicBossEggsPlugin.getPlugin(), this.entity));
        s.setMetadata("cbe_boss", new FixedMetadataValue((Plugin)CosmicBossEggsPlugin.getPlugin(), this.type.name()));
        me.ifamasssxd.cosmicbosses.boss.type.Boss.boss_map.put(s, this);
        this.particle_task_id = CosmicBossEggsPlugin.getPlugin().getServer().getScheduler().runTaskTimerAsynchronously((Plugin)CosmicBossEggsPlugin.getPlugin(), new Runnable(){

            @Override
            public void run() {
                if (!HeadlessKingBoss.this.isBossEntityAlive()) {
                    HeadlessKingBoss.this.cancelTaskSync(HeadlessKingBoss.this.particle_task_id);
                    return;
                }
                try {
                    ParticleEffect.sendToLocation(ParticleEffect.PORTAL, HeadlessKingBoss.this.entity.getLocation(), new Random().nextFloat(), new Random().nextFloat(), new Random().nextFloat(), 0.5f, 20);
                }
                catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }, 20L, 20L).getTaskId();
        this.target_task_id = CosmicBossEggsPlugin.getPlugin().getServer().getScheduler().runTaskTimer((Plugin)CosmicBossEggsPlugin.getPlugin(), new Runnable(){

            @Override
            public void run() {
                Player ptarg;
                if (!HeadlessKingBoss.this.isBossEntityAlive()) {
                    HeadlessKingBoss.this.cancelTaskSync(HeadlessKingBoss.this.target_task_id);
                    return;
                }
                if (!(s.getTarget() != null && s.getTarget() instanceof Player || (ptarg = HeadlessKingBoss.this.getClosestPlayer(s)) == null)) {
                    s.setTarget(ptarg);
                    h.setTarget(ptarg);
                }
            }
        }, 20L, 20L).getTaskId();
        this.anti_water_task_id = CosmicBossEggsPlugin.getPlugin().getServer().getScheduler().runTaskTimer((Plugin)CosmicBossEggsPlugin.getPlugin(), new Runnable(){

            @Override
            public void run() {
                Player new_target;
                if (!HeadlessKingBoss.this.isBossEntityAlive()) {
                    HeadlessKingBoss.this.cancelTaskSync(HeadlessKingBoss.this.anti_water_task_id);
                    return;
                }
                if ((s.getLocation().getBlock().getType() == Material.WATER || s.getLocation().getBlock().getType() == Material.STATIONARY_WATER || h.getLocation().getBlock().getType() == Material.WATER || h.getLocation().getBlock().getType() == Material.STATIONARY_WATER) && (new_target = HeadlessKingBoss.this.getFurthestPlayer(s)) != null) {
                    HeadlessKingBoss.this.pullEntityToLocation(s, new_target.getLocation().add(0.0, 4.0, 0.0));
                }
            }
        }, 20L, 20L).getTaskId();
        this.exploding_pumpkin_task_id = CosmicBossEggsPlugin.getPlugin().getServer().getScheduler().runTaskTimerAsynchronously((Plugin)CosmicBossEggsPlugin.getPlugin(), new Runnable(){
            LivingEntity le;
            {
                this.le = (LivingEntity)HeadlessKingBoss.this.entity;
            }

            @Override
            public void run() {
                if (!HeadlessKingBoss.this.isBossEntityAlive()) {
                    HeadlessKingBoss.this.cancelTaskSync(HeadlessKingBoss.this.exploding_pumpkin_task_id);
                    return;
                }
                final Player victim = HeadlessKingBoss.this.getClosestPlayer(this.le);
                if (victim == null) {
                    return;
                }
                double chance = 100.0 - this.le.getHealth() / 5000.0;
                if (chance > 75.0) {
                    chance = 75.0;
                }
                Random random = new Random();
                if ((double)random.nextInt(100) <= chance) {
                    Bukkit.getScheduler().runTask((Plugin)CosmicBossEggsPlugin.getPlugin(), new Runnable(){

                        @Override
                        public void run() {
                            TNTPrimed tnt = (TNTPrimed)le.getWorld().spawnEntity(le.getLocation(), EntityType.PRIMED_TNT);
                            tnt.setFuseTicks(60);
                            if (Math.random() <= 0.5) {
                                tnt.setIsIncendiary(true);
                            }
                            tnt.setMetadata("undead_tnt", new FixedMetadataValue((Plugin)CosmicBossEggsPlugin.getPlugin(), true));
                            HeadlessKingBoss.this.pullEntityToLocation(tnt, victim.getLocation());
                        }
                    });
                }
            }
        }, 35L, 35L).getTaskId();
        this.player_charge_task_id = CosmicBossEggsPlugin.getPlugin().getServer().getScheduler().runTaskTimerAsynchronously((Plugin)CosmicBossEggsPlugin.getPlugin(), new Runnable(){
            Location nuke_location = null;
            List<Player> nuke_list = new ArrayList<Player>();

            @Override
            public void run() {
                if (!HeadlessKingBoss.this.isBossEntityAlive()) {
                    HeadlessKingBoss.this.cancelTaskSync(HeadlessKingBoss.this.player_charge_task_id);
                    return;
                }
                if (new Random().nextInt(3) == 0) {
                    Player victim = HeadlessKingBoss.this.getFurthestPlayer(HeadlessKingBoss.this.entity);
                    if (victim == null || this.nuke_location != null) {
                        return;
                    }
                    if (HeadlessKingBoss.this.player_charge_task != null) {
                        if (HeadlessKingBoss.this.player_charge_task.iteration >= 10) {
                            Bukkit.getScheduler().cancelTask(HeadlessKingBoss.this.player_charge_task_id);
                            Location f_nuke_location = this.nuke_location;
                            for (Player player : CosmicBossEggsPlugin.getPlugin().getServer().getOnlinePlayers()) {
                                if (player == null || player.getWorld() == null || this.nuke_location == null || !player.getWorld().equals(this.nuke_location.getWorld()) || !(player.getLocation().distanceSquared(this.nuke_location) <= 25.0)) continue;
                                this.nuke_list.add(player);
                            }
                            final Player[] f_nuke_list = this.nuke_list.toArray(new Player[this.nuke_list.size()]);
                            Bukkit.getScheduler().runTask((Plugin)CosmicBossEggsPlugin.getPlugin(), new Runnable(){

                                @Override
                                public void run() {
                                    for (Player pl : f_nuke_list) {
                                        pl.getWorld().playSound(pl.getLocation(), Sound.ZOMBIE_PIG_HURT, 1.0f, 3.0f);
                                        pl.damage(999.0);
                                        pl.sendMessage("");
                                        pl.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!) " + (Object)((Object)ChatColor.RED) + "You were caught in the Headless King's Gaze of Destruction!");
                                        pl.sendMessage("");
                                    }
                                }
                            });
                            try {
                                ParticleEffect.sendToLocation(ParticleEffect.HUGE_EXPLOSION, f_nuke_location.add(0.0, 2.0, 0.0), new Random().nextFloat(), new Random().nextFloat(), new Random().nextFloat(), 0.5f, 10);
                            }
                            catch (Exception exception) {
                                exception.printStackTrace();
                            }
                            this.nuke_location = null;
                        }
                        return;
                    }
                    this.nuke_location = victim.getLocation();
                    this.nuke_list.clear();
                    HeadlessKingBoss.this.player_charge_task = new FirepathTask((LivingEntity)HeadlessKingBoss.this.entity, victim.getLocation());
                    HeadlessKingBoss.this.player_charge_task_id = Bukkit.getScheduler().runTaskTimer((Plugin)CosmicBossEggsPlugin.getPlugin(), HeadlessKingBoss.this.player_charge_task, 10L, 10L).getTaskId();
                }
            }
        }, 300L, 300L).getTaskId();
    }

    public boolean isBossEntityAlive() {
        return this.entity != null && this.entity.isValid() && !this.entity.isDead();
    }

    private void cancelTaskSync(final int taskID) {
        Bukkit.getScheduler().runTask((Plugin)CosmicBossEggsPlugin.getPlugin(), new Runnable(){

            @Override
            public void run() {
                Bukkit.getScheduler().cancelTask(taskID);
            }
        });
    }

    @Override
    public void onBossDamaged(EntityDamageEvent e) {
        Entity attacker;
        if (e.getCause() == EntityDamageEvent.DamageCause.FALL || e.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
            e.setCancelled(true);
            return;
        }
        if (e.getCause() == EntityDamageEvent.DamageCause.MELTING) {
            e.setCancelled(true);
            return;
        }
        if (e.getCause() == EntityDamageEvent.DamageCause.FIRE || e.getCause() == EntityDamageEvent.DamageCause.LAVA || e.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK) {
            double dmg = e.getDamage();
            double hp = ((LivingEntity)this.entity).getHealth();
            if (hp + dmg >= ((LivingEntity)this.entity).getMaxHealth()) {
                ((LivingEntity)this.entity).setHealth(((LivingEntity)this.entity).getMaxHealth());
            } else {
                ((LivingEntity)this.entity).setHealth(dmg + hp);
            }
            this.entity.getWorld().playSound(this.entity.getLocation(), Sound.GHAST_MOAN, 1.0f, 3.0f);
            Bukkit.getScheduler().runTaskAsynchronously((Plugin)CosmicBossEggsPlugin.getPlugin(), new Runnable(){

                @Override
                public void run() {
                    try {
                        ParticleEffect.sendToLocation(ParticleEffect.HAPPY_VILLAGER, HeadlessKingBoss.this.entity.getLocation(), new Random().nextFloat(), new Random().nextFloat(), new Random().nextFloat(), 0.03f, 20);
                    }
                    catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            });
            return;
        }
        if (e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK && (attacker = ((EntityDamageByEntityEvent)e).getDamager()) instanceof LivingEntity) {
            LivingEntity le_attacker = (LivingEntity)attacker;
            le_attacker.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 60, 0));
            if (!le_attacker.hasMetadata("undead_wither")) {
                le_attacker.setMetadata("undead_wither", new FixedMetadataValue((Plugin)CosmicBossEggsPlugin.getPlugin(), System.currentTimeMillis()));
                le_attacker.playEffect(EntityEffect.WITCH_MAGIC);
                if (le_attacker instanceof Player) {
                    ((Player)le_attacker).sendMessage("");
                    ((Player)le_attacker).sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!) " + ChatColor.RED.toString() + "You have been afflicted with the Headless King curse!");
                    ((Player)le_attacker).sendMessage((Object)((Object)ChatColor.GRAY) + "All wither damage will now bypass all armor!");
                    ((Player)le_attacker).sendMessage("");
                }
            }
            this.entity.setFireTicks(40);
            Bukkit.getScheduler().runTaskAsynchronously((Plugin)CosmicBossEggsPlugin.getPlugin(), new Runnable(){

                @Override
                public void run() {
                    try {
                        ParticleEffect.sendToLocation(ParticleEffect.FLAME, HeadlessKingBoss.this.entity.getLocation(), new Random().nextFloat(), new Random().nextFloat(), new Random().nextFloat(), 0.5f, 20);
                    }
                    catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            });
            ((Player)le_attacker).playSound(le_attacker.getLocation(), Sound.GHAST_SCREAM, 1.0f, 0.35f);
            this.flingEntity((Player)le_attacker, this.entity, 1.15);
        }
    }

    @Override
    public void onBossDeath(EntityDeathEvent e) {
        CosmicBossEggsPlugin.getPlugin().getServer().getScheduler().cancelTask(this.target_task_id);
        CosmicBossEggsPlugin.getPlugin().getServer().getScheduler().cancelTask(this.anti_water_task_id);
        CosmicBossEggsPlugin.getPlugin().getServer().getScheduler().cancelTask(this.exploding_pumpkin_task_id);
        CosmicBossEggsPlugin.getPlugin().getServer().getScheduler().cancelTask(this.player_charge_task_id);
        CosmicBossEggsPlugin.getPlugin().getServer().getScheduler().cancelTask(this.particle_task_id);
        ArrayList<Player> heroes = new ArrayList<Player>();
        for (Entity ent : e.getEntity().getNearbyEntities(16.0, 16.0, 16.0)) {
            if (!(ent instanceof Player)) continue;
            heroes.add((Player)ent);
        }
        if (this.entity.getVehicle() != null) {
            this.entity.getVehicle().remove();
        }
        this.entity.remove();
        Boss.boss_map.remove(this.entity);
    }

    @Override
    public void onBossDamage(EntityDamageEvent e) {
        if (!e.isCancelled() && e.getDamage() > 0.0 && e instanceof EntityDamageByEntityEvent) {
            Entity boss = ((EntityDamageByEntityEvent)e).getDamager();
            for (Entity ent : boss.getNearbyEntities(6.0, 6.0, 6.0)) {
                if (!(ent instanceof Player)) continue;
                this.flingEntity((Player)ent, boss, 1.25);
            }
        }
    }

    private void flingEntity(Player pushed, Entity pusher, double speed) {
        Vector unitVector = pushed.getLocation().toVector().subtract(pusher.getLocation().toVector()).normalize();
        unitVector.setY(1.25);
        pushed.setVelocity(unitVector.multiply(speed));
    }

    public void pullEntityToLocation(Entity e, Location loc) {
        double d;
        Location entityLoc = e.getLocation();
        entityLoc.setY(entityLoc.getY() + 0.5);
        e.teleport(entityLoc);
        double g = -0.08;
        double t = d = loc.distance(entityLoc);
        double v_x = (1.0 + 0.12 * t) * (loc.getX() - entityLoc.getX()) / t;
        double v_y = (1.0 + 0.06 * t) * (loc.getY() - entityLoc.getY()) / t - 0.5 * g * t;
        double v_z = (1.0 + 0.12 * t) * (loc.getZ() - entityLoc.getZ()) / t;
        Vector v = e.getVelocity();
        v.setX(v_x);
        v.setY(v_y);
        v.setZ(v_z);
        e.setVelocity(v);
    }

    private Player getClosestPlayer(Entity boss) {
        for (Entity ent : boss.getNearbyEntities(16.0, 16.0, 16.0)) {
            if (!(ent instanceof Player) || WorldGuardUtil.isPvPDisabled(ent.getLocation()) || WorldGuardUtil.getRegionName(ent.getLocation()).startsWith("spaw")) continue;
            return (Player)ent;
        }
        return null;
    }

    private Player getFurthestPlayer(Entity boss) {
        double furthest_dist = 0.0;
        Player furthest_player = null;
        for (Entity ent : boss.getNearbyEntities(16.0, 32.0, 16.0)) {
            if (!(ent instanceof Player) || WorldGuardUtil.isPvPDisabled(ent.getLocation()) || WorldGuardUtil.getRegionName(ent.getLocation()).startsWith("spaw")) continue;
            if (furthest_player == null) {
                furthest_player = (Player)ent;
                continue;
            }
            furthest_dist = ent.getLocation().distanceSquared(furthest_player.getLocation());
            if (!(ent.getLocation().distanceSquared(ent.getLocation()) > furthest_dist)) continue;
            furthest_player = (Player)ent;
        }
        return furthest_player;
    }
}

