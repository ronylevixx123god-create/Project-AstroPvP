/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsAPI
 *  com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity
 *  com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils
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
import com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils;
import java.util.HashSet;
import java.util.Random;

import me.ifamasssxd.cosmicbosses.boss.rarity.BossRarity;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class LoveImpBoss extends me.ifamasssxd.cosmicbosses.boss.type.Boss {
    public HashSet<Entity> minions = new HashSet();
    int height_limit_task_id;
    int spawn_y;

    protected LoveImpBoss() {
        this.type = EntityType.PIG_ZOMBIE;
        this.rarity = BossRarity.RARE;
        this.max_hp = 100.0;
        this.custom_name = ChatColor.RED.toString() + ChatColor.BOLD.toString() + "Love Imp";
    }

    @Override
    public void spawnBoss(Location l) {
        PigZombie pz = (PigZombie)l.getWorld().spawnEntity(l, EntityType.PIG_ZOMBIE);
        pz.setMaxHealth(this.max_hp);
        pz.setHealth(this.max_hp);
        pz.setCustomName(this.custom_name);
        pz.setCustomNameVisible(true);
        pz.setBaby(true);
        pz.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));
        pz.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, Integer.MAX_VALUE, 1));
        l.getWorld().playSound(l, Sound.ZOMBIE_PIG_HURT, 2.0f, 0.5f);
        this.entity = pz;
        pz.setMetadata("cbe_boss", new FixedMetadataValue((Plugin) CosmicBosses.getInstance(), this.type.name()));
        me.ifamasssxd.cosmicbosses.boss.type.Boss.boss_map.put(pz, this);
    }

    @Override
    public void onBossDamaged(EntityDamageEvent e) {
        int i;
        final Location loc = e.getEntity().getLocation().add(0.0, 1.0, 0.0);
        Bukkit.getScheduler().runTaskAsynchronously((Plugin)CosmicBosses.getInstance(), new Runnable(){

            @Override
            public void run() {
                try {
                    ParticleEffect.sendToLocation(ParticleEffect.HEART, loc, new Random().nextFloat() * 2.0f, new Random().nextFloat() * 2.0f, new Random().nextFloat() * 2.0f, 0.25f, 6);
                }
                catch (Exception e1) {
                    e1.printStackTrace();
                }
                try {
                    ParticleEffect.sendToLocation(ParticleEffect.WITCH_MAGIC, loc, new Random().nextFloat(), new Random().nextFloat(), new Random().nextFloat(), 0.001f, 50);
                }
                catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        int x = new Random().nextInt(3);
        if (x == 0) {
            for (i = 0; i < new Random().nextInt(4) + 1; ++i) {
                loc.getWorld().dropItemNaturally(loc, CosmicSpaceChestsAPI.getItems((ItemRarity)CosmicSpaceChestsAPI.getRandomItemRarity(), (int)1)[0]);
            }
        } else if (x == 1) {
            i = new Random().nextInt(3);
            if (i == 0) {
                ItemStack apple = ItemUtils.buildItem((Material)Material.GOLDEN_APPLE, (short)0, (String)((Object)((Object)ChatColor.WHITE) + "White Chocolate Apple"), (String[])new String[]{(Object)((Object)ChatColor.GRAY) + "Rich chocolate cut with sour apple."});
                apple.setAmount(8);
                loc.getWorld().dropItemNaturally(loc, apple);
            } else if (i == 1) {
                ItemStack diamond = ItemUtils.buildItem((Material)Material.DIAMOND, (short)0, (String)((Object)((Object)ChatColor.WHITE) + "Girls Best Friend"), (String[])new String[]{(Object)((Object)ChatColor.GRAY) + "Are you ugly? No problem!"});
                diamond.setAmount(4);
                loc.getWorld().dropItemNaturally(loc, diamond);
            } else if (i == 2) {
                ItemStack love_potion = ItemUtils.buildItem((Material)Material.POTION, (short)16417, (String)((Object)((Object)ChatColor.RED) + "Love Potion No. 9"), (String[])new String[]{(Object)((Object)ChatColor.GRAY) + "The best scientific achievment."});
                loc.getWorld().dropItemNaturally(loc, love_potion);
            }
        } else if (x == 2) {
            i = new Random().nextInt(3);
        }
        int reaction = new Random().nextInt(10);
        if (reaction <= 5) {
            CosmicBosses.getInstance().getServer().getScheduler().runTaskAsynchronously((Plugin)CosmicBosses.getInstance(), new Runnable(){

                @Override
                public void run() {
                    PacketPlayOutWorldEvent particles = new PacketPlayOutWorldEvent(2001, new BlockPosition(Math.round(loc.getBlockX()), Math.round(loc.getBlockY()), Math.round(loc.getBlockZ())), loc.getWorld().getHighestBlockAt(loc).getTypeId(), false);
                    ((CraftServer)CosmicBosses.getInstance().getServer()).getServer().getPlayerList().sendPacketNearby((double)loc.getBlockX(), (double)loc.getBlockY(), (double)loc.getBlockZ(), 16.0, ((CraftWorld)loc.getWorld()).getHandle().dimension, (Packet) particles);
                }
            });
            Location new_loc = loc.add(new Random().nextInt(50), new Random().nextInt(10), new Random().nextInt(50));
            this.pullEntityToLocation(this.entity, new_loc);
        } else if (reaction == 9) {
            Bukkit.getScheduler().runTaskAsynchronously((Plugin)CosmicBosses.getInstance(), new Runnable(){

                @Override
                public void run() {
                    try {
                        ParticleEffect.sendToLocation(ParticleEffect.HEART, loc, new Random().nextFloat(), new Random().nextFloat(), new Random().nextFloat(), 0.001f, 50);
                    }
                    catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    try {
                        ParticleEffect.sendToLocation(ParticleEffect.WITCH_MAGIC, loc, new Random().nextFloat(), new Random().nextFloat(), new Random().nextFloat(), 0.001f, 50);
                    }
                    catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            });
            this.entity.remove();
        } else if (reaction <= 8 && reaction > 5) {
            if (this.entity.getVehicle() != null) {
                Bukkit.getScheduler().runTaskAsynchronously((Plugin)CosmicBosses.getInstance(), new Runnable(){

                    @Override
                    public void run() {
                        try {
                            ParticleEffect.sendToLocation(ParticleEffect.HEART, loc, new Random().nextFloat(), new Random().nextFloat(), new Random().nextFloat(), 0.001f, 50);
                        }
                        catch (Exception e1) {
                            e1.printStackTrace();
                        }
                        try {
                            ParticleEffect.sendToLocation(ParticleEffect.WITCH_MAGIC, loc, new Random().nextFloat(), new Random().nextFloat(), new Random().nextFloat(), 0.001f, 50);
                        }
                        catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }
                });
                this.entity.remove();
            } else {
                Entity bat = this.entity.getWorld().spawnEntity(this.entity.getLocation().add(0.0, 3.0, 0.0), EntityType.BLAZE);
                bat.setPassenger(this.entity);
                CosmicBosses.getInstance().getServer().getScheduler().runTaskAsynchronously((Plugin)CosmicBosses.getInstance(), new Runnable(){

                    @Override
                    public void run() {
                        PacketPlayOutWorldEvent particles = new PacketPlayOutWorldEvent(2001, new BlockPosition(Math.round(loc.getBlockX()), Math.round(loc.getBlockY()), Math.round(loc.getBlockZ())), loc.getWorld().getHighestBlockAt(loc).getTypeId(), false);
                        ((CraftServer)CosmicBosses.getInstance().getServer()).getServer().getPlayerList().sendPacketNearby((double)loc.getBlockX(), (double)loc.getBlockY(), (double)loc.getBlockZ(), 16.0, ((CraftWorld)loc.getWorld()).getHandle().dimension, (Packet)particles);
                    }
                });
                bat.setVelocity(new Vector(0.0, 1.0, 0.0));
                ((LivingEntity)bat).addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 5));
            }
        }
    }

    @Override
    public void onBossDeath(EntityDeathEvent e) {
        final Location loc = e.getEntity().getLocation().add(0.0, 1.0, 0.0);
        Bukkit.getScheduler().runTaskAsynchronously((Plugin)CosmicBosses.getInstance(), new Runnable(){

            @Override
            public void run() {
                try {
                    ParticleEffect.sendToLocation(ParticleEffect.HEART, loc, new Random().nextFloat(), new Random().nextFloat(), new Random().nextFloat(), 0.1f, 15);
                }
                catch (Exception e1) {
                    e1.printStackTrace();
                }
                try {
                    ParticleEffect.sendToLocation(ParticleEffect.WITCH_MAGIC, loc, new Random().nextFloat(), new Random().nextFloat(), new Random().nextFloat(), 0.001f, 50);
                }
                catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        int x = new Random().nextInt(3);
        if (x == 0) {
            for (ItemStack is : CosmicSpaceChestsAPI.getItems((ItemRarity)CosmicSpaceChestsAPI.getRandomItemRarity(), (int)5)) {
                loc.getWorld().dropItemNaturally(loc, is);
            }
        } else if (x == 1) {
            int i = new Random().nextInt(3);
            if (i == 0) {
                ItemStack apple = ItemUtils.buildItem((Material)Material.GOLDEN_APPLE, (short)0, (String)((Object)((Object)ChatColor.WHITE) + "White Chocolate Apple"), (String[])new String[]{(Object)((Object)ChatColor.GRAY) + "Rich chocolate cut with sour apple."});
                apple.setAmount(8);
                loc.getWorld().dropItemNaturally(loc, apple);
            } else if (i == 1) {
                ItemStack diamond = ItemUtils.buildItem((Material)Material.DIAMOND, (short)0, (String)((Object)((Object)ChatColor.WHITE) + "Girls Best Friend"), (String[])new String[]{(Object)((Object)ChatColor.GRAY) + "Are you ugly? No problem!"});
                diamond.setAmount(4);
                loc.getWorld().dropItemNaturally(loc, diamond);
            } else if (i == 2) {
                ItemStack love_potion = ItemUtils.buildItem((Material)Material.POTION, (short)16417, (String)((Object)((Object)ChatColor.RED) + "Love Potion No. 9"), (String[])new String[]{(Object)((Object)ChatColor.GRAY) + "The best scientific achievment."});
                loc.getWorld().dropItemNaturally(loc, love_potion);
            }
        } else if (x == 2) {
            int n = new Random().nextInt(3);
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
                ((Player)ent).addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 120, 0));
                ((Player)ent).addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 120, 0));
                ((Player)ent).sendMessage((Object)((Object)ChatColor.RED) + "A nearby love imp has thrown a love potion!");
                ((Player)ent).sendMessage((Object)((Object)ChatColor.GRAY) + "You have fallen under a mad love spell.");
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

