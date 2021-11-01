/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.arkhamnetwork.Arkkit.Arkkit
 *  org.bukkit.Material
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.MagmaCube
 *  org.bukkit.entity.MushroomCow
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.CreatureSpawnEvent
 *  org.bukkit.event.entity.EntityCombustEvent
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageCause
 *  org.bukkit.event.entity.EntityDeathEvent
 *  org.bukkit.event.entity.SlimeSplitEvent
 *  org.bukkit.event.player.PlayerShearEntityEvent
 *  org.bukkit.me.astropvp.cratekeys.inventory.ItemStack
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package com.conaxgames.factions.utils.listeners;

import java.util.ArrayList;
import java.util.Random;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.MushroomCow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.SlimeSplitEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class NetherMobSpawnerListener
implements Listener {
    @EventHandler(priority=EventPriority.LOWEST)
    public void onCreatureSpawn(CreatureSpawnEvent e) {
        if (e.getEntity() instanceof MagmaCube) {
            MagmaCube mc = (MagmaCube)e.getEntity();
            mc.setSize(3);
            mc.setMaxHealth(18.0);
            mc.setHealth(18.0);
            mc.setMetadata("monsterAmount", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)2));
        } else if (e.getEntity() instanceof MushroomCow) {
            MushroomCow cow = (MushroomCow)e.getEntity();
            cow.setMaxHealth(55.0);
            cow.setHealth(55.0);
        }
    }

    @EventHandler
    public void onSlimeSplit(SlimeSplitEvent e) {
        e.setCount(0);
        e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerShearEntityEvent(PlayerShearEntityEvent e) {
        e.setCancelled(true);
    }

    @EventHandler(priority=EventPriority.HIGH)
    public void onEntityDeathMagma(EntityDeathEvent e) {
        if (e.getEntityType() == EntityType.MAGMA_CUBE) {
            for (ItemStack i : e.getDrops()) {
                if (i.getType() != Material.SLIME_BALL) continue;
                e.getDrops().remove((Object)i);
            }
        }
    }

    @EventHandler(priority=EventPriority.NORMAL)
    public void onEntityDeath(EntityDeathEvent e) {
        EntityType type = e.getEntityType();
        if (type == EntityType.MUSHROOM_COW) {
            e.getDrops().clear();
            int mushroomDrop = new Random().nextInt(3);
            if (mushroomDrop > 0) {
                e.getDrops().add(new ItemStack(Material.RED_MUSHROOM, mushroomDrop));
            }
            e.getDrops().add(new ItemStack(Material.GOLD_INGOT, new Random().nextInt(2) + new Random().nextInt(2) + new Random().nextInt(2) + new Random().nextInt(2)));
        } else if (type == EntityType.MAGMA_CUBE) {
            e.getDrops().clear();
            if (Math.random() < 0.2) {
                Player le;
                ItemStack weapon;
                int minDrops = 0;
                int maxDrops = 2;
                if (e.getEntity().getKiller() instanceof LivingEntity && (weapon = (le = e.getEntity().getKiller()).getEquipment().getItemInHand()) != null && weapon.containsEnchantment(Enchantment.LOOT_BONUS_MOBS)) {
                    int lootingEnchant = weapon.getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS);
                    if (lootingEnchant >= 2) {
                        ++maxDrops;
                    }
                    if (lootingEnchant >= 3) {
                        ++minDrops;
                    }
                    if (lootingEnchant >= 4) {
                        ++maxDrops;
                    }
                    if (lootingEnchant >= 5) {
                        ++minDrops;
                    }
                }
                e.getDrops().add(new ItemStack(Material.DIAMOND, minDrops + new Random().nextInt(maxDrops - minDrops)));
            }
            e.setDroppedExp(20);
        } else if (type == EntityType.GHAST) {
            e.getDrops().clear();
            if (Math.random() <= 0.8) {
                e.getDrops().add(new ItemStack(Material.EMERALD, new Random().nextInt(3) + 2));
            }
        }
    }

    @EventHandler
    public void onEntityCombust(EntityCombustEvent e) {
        if (e.getEntityType() == EntityType.MUSHROOM_COW) {
            e.setDuration(Integer.MAX_VALUE);
        }
    }

    @EventHandler
    public void onEntityDamageEvent(EntityDamageEvent e) {
        EntityType type = e.getEntityType();
        if (type == EntityType.MUSHROOM_COW) {
            if (e.getCause() == EntityDamageEvent.DamageCause.FALL) {
                e.setDamage(e.getDamage() * 3.0);
            } else if (e.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK) {
                e.setDamage(4.0);
            }
        } else if (type == EntityType.MAGMA_CUBE && e.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK && e.getCause() != EntityDamageEvent.DamageCause.CUSTOM && e.getCause() != EntityDamageEvent.DamageCause.VOID) {
            e.setCancelled(true);
            e.setDamage(0.0);
        }
    }
}

