/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsAPI
 *  com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity
 *  net.minecraft.server.v1_7_R4.Packet
 *  net.minecraft.server.v1_7_R4.PacketPlayOutWorldEvent
 *  org.bukkit.craftbukkit.v1_7_R4.CraftServer
 *  org.bukkit.craftbukkit.v1_7_R4.CraftWorld
 */
package me.ifamasssxd.cosmicbosses.boss.type;

import me.ifamasssxd.cosmicbosses.CosmicBosses;
import me.ifamasssxd.cosmicbosses.utils.ParticleEffect;
import com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsAPI;
import com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity;
import java.util.HashSet;
import java.util.Random;

import me.ifamasssxd.cosmicbosses.boss.rarity.BossRarity;
import me.ifamasssxd.cosmicfactionboosters.CosmicFactionBoosters;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class TreasureGoblinBoss
extends me.ifamasssxd.cosmicbosses.boss.type.Boss {
    public HashSet<Entity> minions = new HashSet();
    int height_limit_task_id;
    int spawn_y;

    protected TreasureGoblinBoss() {
        this.type = EntityType.ZOMBIE;
        this.rarity = BossRarity.RARE;
        this.max_hp = 10.0;
        this.custom_name = ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + "Treasure Goblin";
    }

    @Override
    public void spawnBoss(Location l) {
        Zombie pz = (Zombie)l.getWorld().spawnEntity(l, EntityType.ZOMBIE);
        pz.setMaxHealth(this.max_hp);
        pz.setHealth(this.max_hp);
        pz.setCustomName(this.custom_name);
        pz.setCustomNameVisible(true);
        pz.setBaby(true);
        pz.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2));
        pz.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, Integer.MAX_VALUE, 1));
        l.getWorld().playSound(l, Sound.ZOMBIE_PIG_HURT, 2.0f, 0.5f);
        this.entity = pz;
        pz.setMetadata("cbe_boss", new FixedMetadataValue((Plugin) CosmicBosses.getInstance(), this.type.name()));
        me.ifamasssxd.cosmicbosses.boss.type.Boss.boss_map.put(pz, this);
    }

    @Override
    public void onBossDamaged(EntityDamageEvent e) {
        if (e.getDamage() <= 0.0 || e.isCancelled()) {
            return;
        }
        final Location loc = e.getEntity().getLocation().add(0.0, 1.0, 0.0);
        Bukkit.getScheduler().runTaskAsynchronously((Plugin)CosmicBosses.getInstance(), new Runnable(){

            @Override
            public void run() {
                try {
                    ParticleEffect.sendToLocation(ParticleEffect.SPELL, loc, new Random().nextFloat(), new Random().nextFloat(), new Random().nextFloat(), 0.1f, 15);
                }
                catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        for (int i = 0; i < new Random().nextInt(4) + 1; ++i) {
            loc.getWorld().dropItemNaturally(loc, CosmicSpaceChestsAPI.getItems((ItemRarity)CosmicSpaceChestsAPI.getRandomItemRarity(), (int)1)[0]);
        }
        int reaction = new Random().nextInt(10);
        if (reaction <= 10) {
            CosmicBosses.getInstance().getServer().getScheduler().runTaskAsynchronously((Plugin)CosmicBosses.getInstance(), new Runnable(){

                @Override
                public void run() {
                    PacketPlayOutWorldEvent particles = new PacketPlayOutWorldEvent(2001, Math.round(loc.getBlockX()), Math.round(loc.getBlockY()), Math.round(loc.getBlockZ()), loc.getWorld().getHighestBlockAt(loc).getTypeId(), false);
                    ((CraftServer)CosmicBosses.getInstance().getServer()).getServer().getPlayerList().sendPacketNearby((double)loc.getBlockX(), (double)loc.getBlockY(), (double)loc.getBlockZ(), 16.0, ((CraftWorld)loc.getWorld()).getHandle().dimension, (Packet) particles);
                }
            });
            Location new_loc = loc.add(new Random().nextInt(20), new Random().nextInt(10), new Random().nextInt(20));
            this.pullEntityToLocation(this.entity, new_loc);
        }
    }

    @Override
    public void onBossDeath(EntityDeathEvent e) {
        final Location loc = e.getEntity().getLocation().add(0.0, 1.0, 0.0);
        Bukkit.getScheduler().runTaskAsynchronously((Plugin)CosmicBosses.getInstance(), new Runnable(){

            @Override
            public void run() {
                try {
                    ParticleEffect.sendToLocation(ParticleEffect.SPELL, loc, new Random().nextFloat(), new Random().nextFloat(), new Random().nextFloat(), 0.1f, 15);
                }
                catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        for (ItemStack is : CosmicSpaceChestsAPI.getItems((ItemRarity)CosmicSpaceChestsAPI.getRandomItemRarity(), (int)5)) {
            loc.getWorld().dropItemNaturally(loc, is);
        }
        Boss.boss_map.remove(this.entity);
    }

    @Override
    public void onBossDamage(EntityDamageEvent e) {
        if (!e.isCancelled() && e.getDamage() > 0.0 && e instanceof EntityDamageByEntityEvent) {
            Entity boss = ((EntityDamageByEntityEvent)e).getDamager();
            for (Entity ent : boss.getNearbyEntities(8.0, 8.0, 8.0)) {
                if (!(ent instanceof Player)) continue;
                this.flingEntity((Player)ent, boss, 1.05);
                ((Player)ent).addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 120, 0));
                ((Player)ent).sendMessage((Object)((Object)ChatColor.RED) + "A nearby treasure goblin has thrown an ink sac!");
            }
        }
    }

    private void flingEntity(Player pushed, Entity pusher, double speed) {
        Vector unitVector = pushed.getLocation().toVector().subtract(pusher.getLocation().toVector()).normalize();
        unitVector.setY(1.25);
        pushed.setVelocity(unitVector.multiply(speed));
    }

    private void pullEntityToLocation(Entity e, Location loc) {
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
}

