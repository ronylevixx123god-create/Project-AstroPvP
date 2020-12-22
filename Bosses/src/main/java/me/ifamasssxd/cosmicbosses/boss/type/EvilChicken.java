/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils
 *  net.minecraft.server.v1_7_R4.Packet
 *  net.minecraft.server.v1_7_R4.PacketPlayOutWorldEvent
 *  org.arkhamnetwork.Arkkit.patches.Easter
 *  org.arkhamnetwork.Arkkit.utils.ItemUtils
 *  org.bukkit.craftbukkit.v1_7_R4.CraftServer
 *  org.bukkit.craftbukkit.v1_7_R4.CraftWorld
 */
package me.ifamasssxd.cosmicbosses.boss.type;

import me.ifamasssxd.cosmicbosses.CosmicBosses;
import me.ifamasssxd.cosmicbosses.utils.ParticleEffect;
import java.util.HashSet;
import java.util.Random;

import me.ifamasssxd.cosmicbosses.boss.rarity.BossRarity;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldEvent;
import org.arkhamnetwork.Arkkit.patches.Easter;
import org.arkhamnetwork.Arkkit.utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.entity.Witch;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class EvilChicken
extends me.ifamasssxd.cosmicbosses.boss.type.Boss {
    public HashSet<Entity> minions = new HashSet();
    int height_limit_task_id;
    int spawn_y;

    protected EvilChicken() {
        this.type = EntityType.CHICKEN;
        this.rarity = BossRarity.RARE;
        this.max_hp = 100.0;
        this.custom_name = ChatColor.RED.toString() + ChatColor.BOLD.toString() + "Evil Easter Chicken";
    }

    @Override
    public void spawnBoss(Location l) {
        Chicken pz = (Chicken)l.getWorld().spawnEntity(l, EntityType.CHICKEN);
        pz.setMaxHealth(this.max_hp);
        pz.setHealth(this.max_hp);
        pz.setCustomName(this.custom_name);
        pz.setCustomNameVisible(true);
        pz.setAdult();
        pz.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));
        pz.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, Integer.MAX_VALUE, 1));
        pz.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 1));
        l.getWorld().playSound(l, Sound.CHICKEN_EGG_POP, 2.0f, 0.75f);
        this.entity = pz;
        pz.setMetadata("cbe_boss", new FixedMetadataValue((Plugin) CosmicBosses.getInstance(), this.type.name()));
        me.ifamasssxd.cosmicbosses.boss.type.Boss.boss_map.put(pz, this);
    }

    @Override
    public void onBossDamaged(EntityDamageEvent e) {
        final Location loc = e.getEntity().getLocation().add(0.0, 1.0, 0.0);
        if (e instanceof EntityDamageByEntityEvent && (((EntityDamageByEntityEvent)e).getDamager() instanceof Witch || ((EntityDamageByEntityEvent)e).getDamager() instanceof ThrownPotion)) {
            e.setCancelled(true);
            e.setDamage(0.0);
            return;
        }
        Bukkit.getScheduler().runTaskAsynchronously((Plugin)CosmicBosses.getInstance(), new Runnable(){

            @Override
            public void run() {
                try {
                    ParticleEffect.sendToLocation(ParticleEffect.CLOUD, loc, new Random().nextFloat() * 2.0f, new Random().nextFloat() * 2.0f, new Random().nextFloat() * 2.0f, 0.25f, 6);
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
            loc.getWorld().dropItemNaturally(loc, Easter.getEasterEgg().clone());
        } else if (x == 1) {
            int i = new Random().nextInt(3);
            if (i == 0) {
                ItemStack apple = com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils.buildItem((Material)Material.GOLDEN_APPLE, (short)0, (String)((Object)((Object)ChatColor.WHITE) + "White Chocolate Apple"), (String[])new String[]{(Object)((Object)ChatColor.GRAY) + "Rich chocolate cut with sour apple."});
                apple.setAmount(8);
                loc.getWorld().dropItemNaturally(loc, apple);
            } else if (i == 1) {
                ItemStack cake = com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils.buildItem((Material)Material.CAKE, (short)0, (String)((Object)((Object)ChatColor.WHITE) + "Easter Icecream Cake"), (String[])new String[]{(Object)((Object)ChatColor.GRAY) + "A cold, marbeled ice cream cake!"});
                loc.getWorld().dropItemNaturally(loc, cake);
            }
        } else if (x == 2) {
            // empty if block
        }
        int reaction = new Random().nextInt(10);
        if (reaction <= 5) {
            CosmicBosses.getInstance().getServer().getScheduler().runTaskAsynchronously((Plugin)CosmicBosses.getInstance(), new Runnable(){

                @Override
                public void run() {
                    PacketPlayOutWorldEvent particles = new PacketPlayOutWorldEvent(2001, Math.round(loc.getBlockX()), Math.round(loc.getBlockY()), Math.round(loc.getBlockZ()), loc.getWorld().getHighestBlockAt(loc).getTypeId(), false);
                    ((CraftServer)CosmicBosses.getInstance().getServer()).getServer().getPlayerList().sendPacketNearby((double)loc.getBlockX(), (double)loc.getBlockY(), (double)loc.getBlockZ(), 16.0, ((CraftWorld)loc.getWorld()).getHandle().dimension, (Packet)particles);
                }
            });
            Location new_loc = loc.add(new Random().nextInt(50), new Random().nextInt(10), new Random().nextInt(50));
            this.pullEntityToLocation(this.entity, new_loc);
        } else if (reaction == 9) {
            Bukkit.getScheduler().runTaskAsynchronously((Plugin)CosmicBosses.getInstance(), new Runnable(){

                @Override
                public void run() {
                    try {
                        ParticleEffect.sendToLocation(ParticleEffect.CLOUD, loc, new Random().nextFloat(), new Random().nextFloat(), new Random().nextFloat(), 0.001f, 50);
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
                            ParticleEffect.sendToLocation(ParticleEffect.CLOUD, loc, new Random().nextFloat(), new Random().nextFloat(), new Random().nextFloat(), 0.001f, 50);
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
                this.entity.getWorld().spawnEntity(this.entity.getLocation().add(0.0, 1.0, 0.0), EntityType.WITCH);
                CosmicBossEggsPlugin.getPlugin().getServer().getScheduler().runTaskAsynchronously((Plugin)CosmicBosses.getInstance(), new Runnable(){

                    @Override
                    public void run() {
                        PacketPlayOutWorldEvent particles = new PacketPlayOutWorldEvent(2001, Math.round(loc.getBlockX()), Math.round(loc.getBlockY()), Math.round(loc.getBlockZ()), loc.getWorld().getHighestBlockAt(loc).getTypeId(), false);
                        ((CraftServer)CosmicBosses.getInstance().getServer()).getServer().getPlayerList().sendPacketNearby((double)loc.getBlockX(), (double)loc.getBlockY(), (double)loc.getBlockZ(), 16.0, ((CraftWorld)loc.getWorld()).getHandle().dimension, (Packet)particles);
                    }
                });
            }
        }
    }

    @Override
    public void onBossDeath(EntityDeathEvent e) {
        e.getDrops().clear();
        e.getDrops().add(ItemUtils.buildItem((Material)Material.RAW_CHICKEN, (short)0, (String)((Object)((Object)ChatColor.WHITE) + "Evil Raw Chicken"), (String[])new String[]{(Object)((Object)ChatColor.GRAY) + "Being bad never tasted so good."}));
        final Location loc = e.getEntity().getLocation().add(0.0, 1.0, 0.0);
        Bukkit.getScheduler().runTaskAsynchronously((Plugin)CosmicBosses.getPlugin(), new Runnable(){

            @Override
            public void run() {
                try {
                    ParticleEffect.sendToLocation(ParticleEffect.CLOUD, loc, new Random().nextFloat(), new Random().nextFloat(), new Random().nextFloat(), 0.1f, 15);
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
        int x = new Random().nextInt(5);
        if (x == 0 || x == 1) {
            loc.getWorld().dropItemNaturally(loc, Easter.getEasterEgg().clone());
            loc.getWorld().dropItemNaturally(loc, Easter.getEasterEgg().clone());
        } else if (x == 2 || x == 3) {
            int i = new Random().nextInt(3);
            if (i == 0) {
                ItemStack apple = com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils.buildItem((Material)Material.GOLDEN_APPLE, (short)0, (String)((Object)((Object)ChatColor.WHITE) + "White Chocolate Apple"), (String[])new String[]{(Object)((Object)ChatColor.GRAY) + "Rich chocolate cut with sour apple."});
                apple.setAmount(8);
                loc.getWorld().dropItemNaturally(loc, apple);
            } else if (i == 1) {
                ItemStack cake = com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils.buildItem((Material)Material.CAKE, (short)0, (String)((Object)((Object)ChatColor.WHITE) + "Easter Icecream Cake"), (String[])new String[]{(Object)((Object)ChatColor.GRAY) + "A cold, marbeled ice cream cake!"});
                loc.getWorld().dropItemNaturally(loc, cake);
            } else if (i == 2) {
                loc.getWorld().dropItemNaturally(loc, Easter.getEasterEgg().clone());
            }
        } else if (x == 4) {
            int n = new Random().nextInt(9);
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
                ((Player)ent).sendMessage((Object)((Object)ChatColor.RED) + "An evil easter chicken bit you!");
                ((Player)ent).sendMessage((Object)((Object)ChatColor.GRAY) + "You have a mild case of chocolate sickness.");
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

