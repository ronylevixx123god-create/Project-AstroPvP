/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.v1_7_R4.Packet
 *  net.minecraft.server.v1_7_R4.PacketPlayOutWorldEvent
 *  org.arkhamnetwork.Arkkit.patches.Christmas
 *  org.arkhamnetwork.Arkkit.patches.Drinks
 *  org.arkhamnetwork.enchantments.util.ParticleEffect
 *  org.bukkit.ChatColor
 *  org.bukkit.Color
 *  org.bukkit.FireworkEffect
 *  org.bukkit.FireworkEffect$Type
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.block.Block
 *  org.bukkit.block.Chest
 *  org.bukkit.craftbukkit.v1_7_R4.CraftServer
 *  org.bukkit.craftbukkit.v1_7_R4.CraftWorld
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.FallingBlock
 *  org.bukkit.entity.Fireball
 *  org.bukkit.entity.Firework
 *  org.bukkit.entity.Ghast
 *  org.bukkit.entity.IronGolem
 *  org.bukkit.entity.PigZombie
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Skeleton
 *  org.bukkit.entity.Skeleton$SkeletonType
 *  org.bukkit.event.Event$Result
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.entity.EntityChangeBlockEvent
 *  org.bukkit.event.entity.EntityDeathEvent
 *  org.bukkit.event.entity.ExplosionPrimeEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.FireworkMeta
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 */
package com.cosmicpvp.CosmicSpaceChests.listeners.holiday;

import com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsAPI;
import com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsPlugin;
import com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity;
import java.util.Random;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldEvent;
import org.arkhamnetwork.Arkkit.patches.Christmas;
import org.arkhamnetwork.Arkkit.patches.Drinks;
import org.arkhamnetwork.enchantments.util.ParticleEffect;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class HeroicEnvoyListener
implements Listener {
    CosmicSpaceChestsPlugin plugin;

    public HeroicEnvoyListener(CosmicSpaceChestsPlugin plugi) {
        this.plugin = plugi;
    }

    @EventHandler
    public void noFireballFire(ExplosionPrimeEvent e) {
        if (e.getEntity() instanceof Fireball && e.getEntity().getWorld().getName().equals("world_arena")) {
            e.setFire(false);
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        if (e.getEntity().hasMetadata("christmasMonster")) {
            e.getDrops().clear();
        }
    }

    @EventHandler
    public void onEntityBlockChange(EntityChangeBlockEvent e) {
        if (e.getEntity() instanceof FallingBlock && ((FallingBlock)e.getEntity()).getMaterial() == Material.CHEST && e.getEntity().getLocation().getWorld().getName().equals("world_arena")) {
            ItemStack[] items;
            Block b;
            Location hit_loc = e.getEntity().getLocation();
            ItemRarity ir = this.getRandomItemRarity();
            e.getEntity().remove();
            e.setCancelled(true);
            final Block f_b = b = hit_loc.getBlock();
            b.setType(Material.CHEST);
            b.getWorld().playSound(b.getLocation(), Sound.ZOMBIE_WOODBREAK, 1.0f, 1.0f);
            Chest chest = (Chest)b.getState();
            if (ir == ItemRarity.LEGENDARY) {
                this.spawnChristmasMonster(b.getLocation().add(0.0, 1.0, 0.0), EntityType.PIG_ZOMBIE);
            }
            CosmicSpaceChestsPlugin.chest_locations.putChest(b.getLocation(), ir);
            for (ItemStack is : items = CosmicSpaceChestsAPI.getItems(ir, 3 + new Random().nextInt(2))) {
                chest.getBlockInventory().addItem(new ItemStack[]{is});
            }
            chest.update();
            Firework fw = (Firework)hit_loc.getWorld().spawnEntity(hit_loc, EntityType.FIREWORK);
            FireworkMeta fwm = fw.getFireworkMeta();
            Color c = null;
            if (ir == ItemRarity.ELITE) {
                c = Color.GREEN;
            }
            if (ir == ItemRarity.ULTIMATE) {
                c = Color.RED;
            }
            if (ir == ItemRarity.LEGENDARY) {
                c = Color.YELLOW;
            }
            FireworkEffect effect = FireworkEffect.builder().flicker(true).withColor(c).withFade(c).with(FireworkEffect.Type.BALL_LARGE).trail(true).build();
            fwm.addEffect(effect);
            fwm.setPower(1);
            fw.setFireworkMeta(fwm);
            Location particle_loc = b.getLocation().subtract(0.0, 2.0, 0.0);
            final int block_id = particle_loc.getBlock().getTypeId();
            this.plugin.getServer().getScheduler().runTaskAsynchronously((Plugin)this.plugin, new Runnable(){

                @Override
                public void run() {
                    PacketPlayOutWorldEvent particles = new PacketPlayOutWorldEvent(2001, f_b.getX(), f_b.getY(), f_b.getZ(), block_id, false);
                    ((CraftServer)HeroicEnvoyListener.this.plugin.getServer()).getServer().getPlayerList().sendPacketNearby((double)f_b.getX(), (double)f_b.getY(), (double)f_b.getZ(), 32.0, ((CraftWorld)f_b.getWorld()).getHandle().dimension, (Packet)particles);
                }
            });
        }
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=false)
    public void onPlayerInteract(PlayerInteractEvent e) {
        Block b;
        if (e.hasBlock() && e.getAction() == Action.LEFT_CLICK_BLOCK && (b = e.getClickedBlock()).getType() == Material.CHEST && b.getWorld().getName().equals("world_arena") && CosmicSpaceChestsPlugin.chest_locations.containsKey((Object)b.getLocation())) {
            Player pl = e.getPlayer();
            final Location loc = b.getLocation();
            ItemRarity ir = CosmicSpaceChestsPlugin.chest_locations.getChestRarity(b.getLocation());
            if (CosmicSpaceChestsPlugin.chest_locations.getChestRarity(loc) == ItemRarity.LEGENDARY && !pl.hasPermission("cosmicpvp.vip")) {
                pl.sendMessage((Object)ChatColor.RED + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.RED + "You are not worthy to open this Legendary Chest...");
                pl.sendMessage((Object)ChatColor.GRAY + "Unlock the potentional to open this chest at " + (Object)ChatColor.UNDERLINE + "buy.cosmicpvp.me");
                e.setCancelled(true);
                return;
            }
            CosmicSpaceChestsPlugin.chest_locations.removeChest(loc);
            b.setType(Material.AIR);
            pl.playSound(pl.getLocation(), Sound.ZOMBIE_WOODBREAK, 0.5f, 1.2f);
            pl.playSound(pl.getLocation(), Sound.GHAST_MOAN, 0.8f, 0.4f);
            e.setCancelled(true);
            e.setUseItemInHand(Event.Result.DENY);
            this.plugin.getServer().getScheduler().runTaskAsynchronously((Plugin)this.plugin, new Runnable(){

                @Override
                public void run() {
                    PacketPlayOutWorldEvent particles = new PacketPlayOutWorldEvent(2001, (int)Math.round(loc.getX()), (int)Math.round(loc.getY()), (int)Math.round(loc.getZ()), Material.PUMPKIN.getId(), false);
                    ((CraftServer)HeroicEnvoyListener.this.plugin.getServer()).getServer().getPlayerList().sendPacketNearby(loc.getX(), loc.getY(), loc.getZ(), 24.0, ((CraftWorld)loc.getWorld()).getHandle().dimension, (Packet)particles);
                }
            });
            if (ir == ItemRarity.LEGENDARY) {
                this.spawnChristmasMonster(b.getLocation().add(0.0, 1.0, 0.0), EntityType.GHAST);
                this.spawnChristmasMonster(b.getLocation().add(0.0, 1.0, 0.0), EntityType.BLAZE);
                this.spawnChristmasMonster(b.getLocation().add(0.0, 1.0, 0.0), EntityType.PIG_ZOMBIE);
                this.spawnChristmasMonster(b.getLocation().add(0.0, 1.0, 0.0), EntityType.PIG_ZOMBIE);
                this.spawnChristmasMonster(b.getLocation().add(0.0, 1.0, 0.0), EntityType.SKELETON);
                this.spawnChristmasMonster(b.getLocation().add(0.0, 1.0, 0.0), EntityType.SKELETON);
                this.spawnChristmasMonster(b.getLocation().add(0.0, 1.0, 0.0), EntityType.SKELETON);
            } else {
                if (ir == ItemRarity.ULTIMATE) {
                    if (new Random().nextBoolean()) {
                        this.spawnChristmasMonster(b.getLocation().add(0.0, 1.0, 0.0), EntityType.SKELETON);
                    } else {
                        this.spawnChristmasMonster(b.getLocation().add(0.0, 1.0, 0.0), EntityType.BLAZE);
                    }
                }
                this.spawnChristmasMonster(b.getLocation().add(0.0, 1.0, 0.0), EntityType.PIG_ZOMBIE);
                this.spawnChristmasMonster(b.getLocation().add(0.0, 1.0, 0.0), EntityType.SKELETON);
            }
            if (new Random().nextBoolean()) {
                ItemStack candy = Christmas.getChristmasCandy();
                candy.setAmount(new Random().nextInt(64) + 1);
                b.getWorld().dropItem(b.getLocation(), candy);
            } else {
                ItemStack eggnog = Drinks.getChristmasDrink();
                eggnog.setAmount(new Random().nextInt(32) + 1);
            }
            return;
        }
    }

    private void spawnChristmasMonster(Location l, EntityType et) {
        PigZombie le = null;
        if (et == EntityType.PIG_ZOMBIE) {
            PigZombie b = (PigZombie)l.getWorld().spawnEntity(l, EntityType.PIG_ZOMBIE);
            b.setMaxHealth(b.getMaxHealth() * 2.0);
            b.setHealth(b.getMaxHealth());
            b.setCustomName(ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + "Christmas " + ChatColor.RED.toString() + ChatColor.BOLD.toString() + "Elf");
            b.setCustomNameVisible(true);
            b.setBaby(true);
            b.setAngry(true);
            le = b;
        } else if (et == EntityType.IRON_GOLEM) {
            IronGolem b = (IronGolem)l.getWorld().spawnEntity(l, EntityType.IRON_GOLEM);
            b.setMaxHealth(b.getMaxHealth() * 2.0);
            b.setHealth(b.getMaxHealth());
            b.setPlayerCreated(false);
            b.setCustomName(ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + "Christmas " + ChatColor.RED.toString() + ChatColor.BOLD.toString() + "Golem");
            b.setCustomNameVisible(true);
            le = b;
        } else if (et == EntityType.SKELETON) {
            Skeleton b = (Skeleton)l.getWorld().spawnEntity(l, EntityType.SKELETON);
            b.setSkeletonType(Skeleton.SkeletonType.WITHER);
            b.getEquipment().setItemInHand(new ItemStack(Material.DIAMOND_SWORD));
            b.setMaxHealth(b.getMaxHealth() * 2.0);
            b.setHealth(b.getMaxHealth());
            b.setCustomName(ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + "Christmas " + ChatColor.RED.toString() + ChatColor.BOLD.toString() + "Assassin");
            b.setCustomNameVisible(true);
            le = b;
        } else if (et == EntityType.GHAST) {
            Ghast b = (Ghast)l.getWorld().spawnEntity(l, EntityType.GHAST);
            b.setMaxHealth(b.getMaxHealth() * 5.0);
            b.setHealth(b.getMaxHealth());
            b.setCustomName(ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + "Christmas " + ChatColor.RED.toString() + ChatColor.BOLD.toString() + "Angel");
            b.setCustomNameVisible(true);
            le = b;
        }
        le.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 0));
        le.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, new Random().nextInt(3)));
        le.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, new Random().nextInt(2)));
        if (new Random().nextBoolean()) {
            le.getEquipment().setItemInHand(new ItemStack(Material.DIAMOND_SWORD));
        } else {
            le.getEquipment().setItemInHand(new ItemStack(Material.IRON_SWORD));
        }
        le.getEquipment().setHelmet(new ItemStack(new Random().nextBoolean() ? Material.IRON_HELMET : Material.DIAMOND_HELMET));
        le.getEquipment().setChestplate(new ItemStack(new Random().nextBoolean() ? Material.IRON_CHESTPLATE : Material.DIAMOND_CHESTPLATE));
        le.getEquipment().setLeggings(new ItemStack(new Random().nextBoolean() ? Material.IRON_LEGGINGS : Material.DIAMOND_LEGGINGS));
        le.getEquipment().setBoots(new ItemStack(new Random().nextBoolean() ? Material.IRON_BOOTS : Material.DIAMOND_BOOTS));
        le.setCanPickupItems(false);
        le.setMetadata("christmasMonster", (MetadataValue)new FixedMetadataValue((Plugin)CosmicSpaceChestsPlugin.plugin, (Object)true));
        ParticleEffect.playEffect((ParticleEffect)ParticleEffect.FLAME, (Location)le.getLocation().add(0.0, 1.0, 0.0), (float)0.15f, (int)25);
    }

    private ItemRarity getRandomItemRarity() {
        int x = new Random().nextInt(1000);
        if (x <= 400) {
            return ItemRarity.ELITE;
        }
        if (x <= 750) {
            return ItemRarity.ULTIMATE;
        }
        if (x <= 950) {
            return ItemRarity.LEGENDARY;
        }
        return ItemRarity.ELITE;
    }
}

