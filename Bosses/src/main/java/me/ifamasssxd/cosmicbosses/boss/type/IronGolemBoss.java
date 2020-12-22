/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.v1_7_R4.Packet
 *  net.minecraft.server.v1_7_R4.PacketPlayOutWorldEvent
 *  org.bukkit.craftbukkit.v1_7_R4.CraftServer
 *  org.bukkit.craftbukkit.v1_7_R4.CraftWorld
 */
package me.ifamasssxd.cosmicbosses.boss.type;

import me.ifamasssxd.cosmicbosses.CosmicBossEggsPlugin;

import java.util.Random;

import me.ifamasssxd.cosmicbosses.boss.rarity.BossRarity;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldEvent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class IronGolemBoss
extends me.ifamasssxd.cosmicbosses.boss.type.Boss {
    int target_task_id;
    int anti_water_task_id;
    int jump_task_id;

    protected IronGolemBoss() {
        this.type = EntityType.IRON_GOLEM;
        this.rarity = BossRarity.UNCOMMON;
        this.max_hp = 1000.0;
        this.custom_name = ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + "King Irongolem";
    }

    @Override
    public void spawnBoss(Location l) {
        final IronGolem ig = (IronGolem)l.getWorld().spawnEntity(l, EntityType.IRON_GOLEM);
        ig.setMaxHealth(this.max_hp);
        ig.setHealth(this.max_hp);
        ig.setCustomName(this.custom_name);
        ig.setCustomNameVisible(true);
        ig.setPlayerCreated(false);
        ig.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));
        ig.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, Integer.MAX_VALUE, 1));
        ig.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 0));
        this.entity = ig;
        ig.setMetadata("cbe_boss", new FixedMetadataValue((Plugin)CosmicBossEggsPlugin.getPlugin(), this.type.name()));
        me.ifamasssxd.cosmicbosses.boss.type.Boss.boss_map.put(ig, this);
        this.target_task_id = CosmicBossEggsPlugin.getPlugin().getServer().getScheduler().runTaskTimer((Plugin)CosmicBossEggsPlugin.getPlugin(), new Runnable(){

            @Override
            public void run() {
                Player ptarg;
                if (!(ig.getTarget() != null && ig.getTarget() instanceof Player || (ptarg = IronGolemBoss.this.getClosestPlayer(ig)) == null)) {
                    ig.setTarget(ptarg);
                }
            }
        }, 20L, 20L).getTaskId();
        this.anti_water_task_id = CosmicBossEggsPlugin.getPlugin().getServer().getScheduler().runTaskTimer((Plugin)CosmicBossEggsPlugin.getPlugin(), new Runnable(){

            @Override
            public void run() {
                Player new_target;
                if ((ig.getLocation().getBlock().getType() == Material.WATER || ig.getLocation().getBlock().getType() == Material.STATIONARY_WATER) && (new_target = IronGolemBoss.this.getFurthestPlayer(ig)) != null) {
                    IronGolemBoss.this.pullEntityToLocation(ig, new_target.getLocation().add(0.0, 2.0, 0.0));
                }
            }
        }, 20L, 20L).getTaskId();
        this.jump_task_id = CosmicBossEggsPlugin.getPlugin().getServer().getScheduler().runTaskTimer((Plugin)CosmicBossEggsPlugin.getPlugin(), new Runnable(){

            @Override
            public void run() {
                Player new_target;
                if (ig.isOnGround() && new Random().nextInt(10) == 0 && (new_target = IronGolemBoss.this.getFurthestPlayer(ig)) != null) {
                    IronGolemBoss.this.pullEntityToLocation(ig, new_target.getLocation().add(0.0, 3.0, 0.0));
                }
            }
        }, 20L, 20L).getTaskId();
    }

    @Override
    public void onBossDamaged(EntityDamageEvent e) {
        if (e.getCause() == EntityDamageEvent.DamageCause.FALL) {
            final Location loc = e.getEntity().getLocation().subtract(0.0, 1.0, 0.0);
            final Block b = loc.getBlock();
            loc.add(0.0, 2.0, 0.0);
            CosmicBossEggsPlugin.getPlugin().getServer().getScheduler().runTaskAsynchronously((Plugin)CosmicBossEggsPlugin.getPlugin(), new Runnable(){

                @Override
                public void run() {
                    PacketPlayOutWorldEvent particles = new PacketPlayOutWorldEvent(2001, Math.round(loc.getBlockX()), Math.round(loc.getBlockY()), Math.round(loc.getBlockZ()), b.getTypeId(), false);
                    ((CraftServer)CosmicBossEggsPlugin.getPlugin().getServer()).getServer().getPlayerList().sendPacketNearby((double)loc.getBlockX(), (double)loc.getBlockY(), (double)loc.getBlockZ(), 16.0, ((CraftWorld)loc.getWorld()).getHandle().dimension, (Packet) particles);
                }
            });
            for (Entity ent : e.getEntity().getNearbyEntities(8.0, 8.0, 8.0)) {
                if (!(ent instanceof Player)) continue;
                ent.setVelocity(new Vector(0.0, 1.3, 0.0));
            }
        }
    }

    @Override
    public void onBossDeath(EntityDeathEvent e) {
        CosmicBossEggsPlugin.getPlugin().getServer().getScheduler().cancelTask(this.target_task_id);
        CosmicBossEggsPlugin.getPlugin().getServer().getScheduler().cancelTask(this.anti_water_task_id);
        CosmicBossEggsPlugin.getPlugin().getServer().getScheduler().cancelTask(this.jump_task_id);
        Boss.boss_map.remove(this.entity);
    }

    @Override
    public void onBossDamage(EntityDamageEvent e) {
        if (!e.isCancelled() && e.getDamage() > 0.0 && e instanceof EntityDamageByEntityEvent) {
            Entity boss = ((EntityDamageByEntityEvent)e).getDamager();
            for (Entity ent : boss.getNearbyEntities(8.0, 8.0, 8.0)) {
                if (!(ent instanceof Player)) continue;
                this.flingEntity((Player)ent, boss, 1.1);
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
            if (!(ent instanceof Player)) continue;
            return (Player)ent;
        }
        return null;
    }

    private Player getFurthestPlayer(Entity boss) {
        double furthest_dist = 0.0;
        Player furthest_player = null;
        for (Entity ent : boss.getNearbyEntities(16.0, 32.0, 16.0)) {
            if (!(ent instanceof Player)) continue;
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

