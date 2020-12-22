/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsAPI
 *  com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity
 *  com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils
 *  net.minecraft.server.v1_7_R4.Packet
 *  net.minecraft.server.v1_7_R4.PacketPlayOutWorldEvent
 *  org.arkhamnetwork.Arkkit.Arkkit
 *  org.bukkit.craftbukkit.v1_7_R4.CraftServer
 *  org.bukkit.craftbukkit.v1_7_R4.CraftWorld
 */
package me.ifamasssxd.cosmicbosses.boss.type;

import me.ifamasssxd.cosmicbosses.CosmicBossEggsPlugin;
import me.ifamasssxd.cosmicbosses.utils.MiscUtil;
import me.ifamasssxd.cosmicbosses.utils.ParticleEffect;
import com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsAPI;
import com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity;
import com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils;
import java.util.HashSet;
import java.util.Random;

import me.ifamasssxd.cosmicbosses.boss.rarity.BossRarity;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldEvent;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
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

public class EvilChristmasElf
extends me.ifamasssxd.cosmicbosses.boss.type.Boss {
    public HashSet<Entity> minions = new HashSet();
    int height_limit_task_id;
    int spawn_y;

    protected EvilChristmasElf() {
        this.type = EntityType.ZOMBIE;
        this.rarity = BossRarity.ULTRA_RARE;
        this.max_hp = 100.0;
        this.custom_name = ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + "Evil Christmas Elf";
    }

    @Override
    public void spawnBoss(Location l) {
        Zombie pz = (Zombie)l.getWorld().spawnEntity(l, EntityType.ZOMBIE);
        pz.setMaxHealth(this.max_hp);
        pz.setHealth(this.max_hp);
        pz.setCustomName(this.custom_name);
        pz.setCustomNameVisible(true);
        pz.setBaby(true);
        pz.getEquipment().setHelmet(MiscUtil.getPlayerHead("TheNextPaladin"));
        pz.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));
        pz.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, Integer.MAX_VALUE, 1));
        l.getWorld().playSound(l, Sound.ZOMBIE_HURT, 2.0f, 0.5f);
        this.entity = pz;
        pz.setMetadata("cbe_boss", new FixedMetadataValue((Plugin)CosmicBossEggsPlugin.getPlugin(), this.type.name()));
        me.ifamasssxd.cosmicbosses.boss.type.Boss.boss_map.put(pz, this);
    }

    @Override
    public void onBossDamaged(EntityDamageEvent e) {
        int i;
        final Location loc = e.getEntity().getLocation().add(0.0, 1.0, 0.0);
        Bukkit.getScheduler().runTaskAsynchronously((Plugin)CosmicBossEggsPlugin.getPlugin(), new Runnable(){

            @Override
            public void run() {
                try {
                    ParticleEffect.sendToLocation(ParticleEffect.FIREWORKS_SPARK, loc, new Random().nextFloat() * 2.0f, new Random().nextFloat() * 2.0f, new Random().nextFloat() * 2.0f, 0.25f, 6);
                }
                catch (Exception e1) {
                    e1.printStackTrace();
                }
                try {
                    ParticleEffect.sendToLocation(ParticleEffect.SPELL, loc, new Random().nextFloat(), new Random().nextFloat(), new Random().nextFloat(), 0.001f, 50);
                }
                catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        int x = new Random().nextInt(5);
        if (x == 0 || x == 3 || x == 4) {
            for (i = 0; i < new Random().nextInt(4) + 1; ++i) {
                loc.getWorld().dropItemNaturally(loc, CosmicSpaceChestsAPI.getItems((ItemRarity)this.getRandomItemRarityForChristmasElf(), (int)1)[0]);
            }
        } else if (x == 1) {
            i = new Random().nextInt(3);
            if (i == 0) {
                ItemStack apple = ItemUtils.buildItem((Material)Material.GOLDEN_APPLE, (short)0, (String)((Object)((Object)ChatColor.WHITE) + "White Chocolate Apple"), (String[])new String[]{(Object)((Object)ChatColor.GRAY) + "Rich chocolate cut with sour apple."});
                apple.setAmount(8);
                loc.getWorld().dropItemNaturally(loc, apple);
            } else if (i == 1) {
                ItemStack diamond = ItemUtils.buildItem((Material)Material.COOKIE, (short)0, (String)((Object)((Object)ChatColor.WHITE) + "Christmas Cookie"), (String[])new String[]{(Object)((Object)ChatColor.GRAY) + "A warm, delicious sugary cookie!"});
                diamond.setAmount(4);
                loc.getWorld().dropItemNaturally(loc, diamond);
            } else if (i == 2) {
                ItemStack love_potion = ItemUtils.buildItem((Material)Material.BREAD, (short)0, (String)((Object)((Object)ChatColor.RED) + "Ginger Bread"), (String[])new String[]{(Object)((Object)ChatColor.GRAY) + "Freshly baked, courtesy of Grandma."});
                loc.getWorld().dropItemNaturally(loc, love_potion);
            }
        } else if (x == 2 && Math.random() < 0.25) {
            i = new Random().nextInt(3);
            if (i == 0) {
                loc.getWorld().dropItemNaturally(loc, ItemUtils.getTitleVoucher((String)"#EvilXmasElf"));
            } else if (i == 1) {
                loc.getWorld().dropItemNaturally(loc, ItemUtils.getTitleVoucher((String)"HoHoHo"));
            } else if (i == 2) {
                loc.getWorld().dropItemNaturally(loc, ItemUtils.getTitleVoucher((String)"#Coal4Christmas"));
            }
        }
        int reaction = new Random().nextInt(10);
        if (reaction <= 5) {
            CosmicBossEggsPlugin.getPlugin().getServer().getScheduler().runTaskAsynchronously((Plugin)CosmicBossEggsPlugin.getPlugin(), new Runnable(){

                @Override
                public void run() {
                    PacketPlayOutWorldEvent particles = new PacketPlayOutWorldEvent(2001, Math.round(loc.getBlockX()), Math.round(loc.getBlockY()), Math.round(loc.getBlockZ()), loc.getWorld().getHighestBlockAt(loc).getTypeId(), false);
                    ((CraftServer)CosmicBossEggsPlugin.getPlugin().getServer()).getServer().getPlayerList().sendPacketNearby((double)loc.getBlockX(), (double)loc.getBlockY(), (double)loc.getBlockZ(), 16.0, ((CraftWorld)loc.getWorld()).getHandle().dimension, (Packet) particles);
                }
            });
            Location new_loc = loc.add(new Random().nextInt(50), new Random().nextInt(10), new Random().nextInt(50));
            this.pullEntityToLocation(this.entity, new_loc);
        } else if (reaction == 9) {
            Bukkit.getScheduler().runTaskAsynchronously((Plugin)CosmicBossEggsPlugin.getPlugin(), new Runnable(){

                @Override
                public void run() {
                    try {
                        ParticleEffect.sendToLocation(ParticleEffect.FIREWORKS_SPARK, loc, new Random().nextFloat(), new Random().nextFloat(), new Random().nextFloat(), 0.001f, 50);
                    }
                    catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    try {
                        ParticleEffect.sendToLocation(ParticleEffect.SPELL, loc, new Random().nextFloat(), new Random().nextFloat(), new Random().nextFloat(), 0.001f, 50);
                    }
                    catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            });
            this.entity.remove();
        } else if (this.entity.getVehicle() != null) {
            Bukkit.getScheduler().runTaskAsynchronously((Plugin)CosmicBossEggsPlugin.getPlugin(), new Runnable(){

                @Override
                public void run() {
                    try {
                        ParticleEffect.sendToLocation(ParticleEffect.FIREWORKS_SPARK, loc, new Random().nextFloat(), new Random().nextFloat(), new Random().nextFloat(), 0.001f, 50);
                    }
                    catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    try {
                        ParticleEffect.sendToLocation(ParticleEffect.SPELL, loc, new Random().nextFloat(), new Random().nextFloat(), new Random().nextFloat(), 0.001f, 50);
                    }
                    catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            });
            this.entity.remove();
        }
    }

    @Override
    public void onBossDeath(EntityDeathEvent e) {
        final Location loc = e.getEntity().getLocation().add(0.0, 1.0, 0.0);
        Bukkit.getScheduler().runTaskAsynchronously((Plugin)CosmicBossEggsPlugin.getPlugin(), new Runnable(){

            @Override
            public void run() {
                try {
                    ParticleEffect.sendToLocation(ParticleEffect.FIREWORKS_SPARK, loc, new Random().nextFloat(), new Random().nextFloat(), new Random().nextFloat(), 0.1f, 15);
                }
                catch (Exception e1) {
                    e1.printStackTrace();
                }
                try {
                    ParticleEffect.sendToLocation(ParticleEffect.SPELL, loc, new Random().nextFloat(), new Random().nextFloat(), new Random().nextFloat(), 0.001f, 50);
                }
                catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        int x = new Random().nextInt(5);
        if (x == 0 || x == 3 || x == 4) {
            for (ItemStack is : CosmicSpaceChestsAPI.getItems((ItemRarity)this.getRandomItemRarityForChristmasElf(), (int)5)) {
                loc.getWorld().dropItemNaturally(loc, is);
            }
        } else if (x == 1) {
            int i = new Random().nextInt(3);
            if (i == 0) {
                ItemStack apple = ItemUtils.buildItem((Material)Material.GOLDEN_APPLE, (short)0, (String)((Object)((Object)ChatColor.WHITE) + "White Chocolate Apple"), (String[])new String[]{(Object)((Object)ChatColor.GRAY) + "Rich chocolate cut with sour apple."});
                apple.setAmount(8);
                loc.getWorld().dropItemNaturally(loc, apple);
            } else if (i == 1) {
                ItemStack diamond = ItemUtils.buildItem((Material)Material.COOKIE, (short)0, (String)((Object)((Object)ChatColor.WHITE) + "Christmas Cookie"), (String[])new String[]{(Object)((Object)ChatColor.GRAY) + "A warm, delicious sugary cookie!"});
                diamond.setAmount(4);
                loc.getWorld().dropItemNaturally(loc, diamond);
            } else if (i == 2) {
                ItemStack love_potion = ItemUtils.buildItem((Material)Material.BREAD, (short)0, (String)((Object)((Object)ChatColor.RED) + "Ginger Bread"), (String[])new String[]{(Object)((Object)ChatColor.GRAY) + "Freshly baked, courtesy of Grandma."});
                loc.getWorld().dropItemNaturally(loc, love_potion);
            }
        } else if (x == 2 && Math.random() < 0.25) {
            int i = new Random().nextInt(3);
            if (i == 0) {
                loc.getWorld().dropItemNaturally(loc, ItemUtils.getTitleVoucher((String)"#EvilXmasElf"));
            } else if (i == 1) {
                loc.getWorld().dropItemNaturally(loc, ItemUtils.getTitleVoucher((String)"HoHoHo"));
            } else if (i == 2) {
                loc.getWorld().dropItemNaturally(loc, ItemUtils.getTitleVoucher((String)"#Coal4Christmas"));
            }
        }
        Boss.boss_map.remove(this.entity);
    }

    @Override
    public void onBossDamage(EntityDamageEvent e) {
        if (!e.isCancelled() && e.getDamage() > 0.0) {
            e.setCancelled(true);
            e.setDamage(0.0);
            if (e instanceof EntityDamageByEntityEvent) {
                Entity boss = ((EntityDamageByEntityEvent)e).getDamager();
                for (Entity ent : boss.getNearbyEntities(8.0, 8.0, 8.0)) {
                    if (!(ent instanceof Player) || ent.hasMetadata("lastElfAttack") && System.currentTimeMillis() - ent.getMetadata("lastElfAttack").get(0).asLong() < 5000L) continue;
                    ent.setMetadata("lastElfAttack", new FixedMetadataValue((Plugin)Arkkit.get(), System.currentTimeMillis()));
                    this.flingEntity((Player)ent, boss, 1.1);
                    ((Player)ent).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 120, 3));
                    ((Player)ent).addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 120, 1));
                    ((Player)ent).addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 120, 1));
                    ((Player)ent).sendMessage((Object)((Object)ChatColor.RED) + "A nearby Evil Christmas Elf has thrown a snowball!");
                    ((Player)ent).sendMessage((Object)((Object)ChatColor.GRAY) + "You feel a chill down your spine.");
                }
            }
        }
    }

    private void flingEntity(Player pushed, Entity pusher, double speed) {
        Vector unitVector = pushed.getLocation().toVector().subtract(pusher.getLocation().toVector()).normalize();
        unitVector.setY(1.05);
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

    private ItemRarity getRandomItemRarityForChristmasElf() {
        int x = new Random().nextInt(1000);
        if (x <= 500) {
            return ItemRarity.ELITE;
        }
        if (x <= 750) {
            return ItemRarity.ULTIMATE;
        }
        if (x <= 950) {
            return ItemRarity.LEGENDARY;
        }
        return ItemRarity.LEGENDARY;
    }
}

