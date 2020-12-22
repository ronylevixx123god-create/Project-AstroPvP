/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.sk89q.worldguard.protection.flags.DefaultFlag
 *  net.citizensnpcs.npc.entity.EntityHumanNPC
 *  net.minecraft.server.v1_7_R4.Block
 *  net.minecraft.server.v1_7_R4.Entity
 *  net.minecraft.server.v1_7_R4.EntityFallingBlock
 *  net.minecraft.server.v1_7_R4.EntityHuman
 *  net.minecraft.server.v1_7_R4.World
 *  org.bukkit.Bukkit
 *  org.bukkit.GameMode
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.block.Block
 *  org.bukkit.craftbukkit.v1_7_R4.CraftWorld
 *  org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.FallingBlock
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.WitherSkull
 *  org.bukkit.event.Event
 *  org.bukkit.event.entity.CreatureSpawnEvent$SpawnReason
 *  org.bukkit.event.entity.EntityChangeBlockEvent
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.ProjectileHitEvent
 *  org.bukkit.event.entity.ProjectileLaunchEvent
 *  org.bukkit.inventory.InventoryHolder
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.util.Vector
 */
package me.ifamasssxd.cosmicbosses.dungeonboss.abilities;

import com.sk89q.worldguard.protection.flags.DefaultFlag;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import me.ifamasssxd.cosmicbosses.CosmicBosses;
import me.ifamasssxd.cosmicbosses.dungeonboss.DungeonAbility;
import me.ifamasssxd.cosmicbosses.dungeonboss.destroyedoutpost.MotherOfYijki;
import me.ifamasssxd.cosmicbosses.struct.Boss;
import me.ifamasssxd.cosmicbosses.struct.NewBlockData;
import me.ifamasssxd.cosmicbosses.utils.BossUtils;
import me.ifamasssxd.cosmicbosses.utils.WorldGuardUtils;
import net.citizensnpcs.npc.entity.EntityHumanNPC;
import net.minecraft.server.v1_7_R4.Block;
import net.minecraft.server.v1_7_R4.Entity;
import net.minecraft.server.v1_7_R4.EntityFallingBlock;
import net.minecraft.server.v1_7_R4.EntityHuman;
import net.minecraft.server.v1_7_R4.World;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.Event;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

public class ChainReaction
extends DungeonAbility {
    private int shotsFired = 0;
    private int landed = 0;

    public ChainReaction(Boss boss) {
        super(boss);
    }

    @Override
    public void onTick() {
    }

    @Override
    public long getCooldown() {
        return TimeUnit.SECONDS.toMillis(5L);
    }

    @Override
    public String getAbilityName() {
        return "Chain Reaction";
    }

    @Override
    public boolean shouldStopTicking() {
        long initTimeDistance = System.currentTimeMillis() - this.initTime;
        return initTimeDistance >= this.getCooldown() && this.shotsFired >= 3 && (this.landed >= 3 || initTimeDistance >= this.getCooldown() + 5000L);
    }

    @Override
    public void onAbilityEnable() {
        this.initAbility();
        this.boss.sendMessage("Let it rain the world around you!");
    }

    @Override
    public void onAbilityDisable() {
    }

    @Override
    public void onAbilityEvent(Event event) {
        EntityDamageByEntityEvent damageEvent;
        if (event instanceof ProjectileLaunchEvent) {
            ProjectileLaunchEvent e = (ProjectileLaunchEvent)event;
            if (this.shotsFired >= 5) {
                return;
            }
            Bukkit.getLogger().info("(Dungeon Yijki) Tracking Projectile fired for chainReaction..");
            e.getEntity().setMetadata("chainReaction", (MetadataValue)new FixedMetadataValue((Plugin)CosmicBosses.getInstance(), (Object)""));
            if (e.getEntity() instanceof WitherSkull) {
                ((WitherSkull)e.getEntity()).setCharged(true);
            }
            ++this.shotsFired;
        } else if (event instanceof ProjectileHitEvent) {
            ProjectileHitEvent e = (ProjectileHitEvent)event;
            if (e.getEntity().hasMetadata("chainReaction")) {
                ++this.landed;
                if (e.getEntity().getLocation().getY() > 2.0) {
                    int radius = 5;
                    if (this.boss.getEntity().getHealth() < (double)this.boss.getMaxHealth() / 2.0) {
                        --radius;
                    }
                    int depth = 1;
                    int exploded = 0;
                    Location entLocation = e.getEntity().getLocation();
                    Bukkit.getLogger().info("Chain reaction exploding!");
                    for (int x = -radius; x < radius; ++x) {
                        for (int z = -radius; z < radius; ++z) {
                            for (int y = -depth; y < depth; ++y) {
                                org.bukkit.block.Block toExplode = entLocation.clone().add((double)x, (double)y, (double)z).getBlock();
                                if (toExplode.getY() <= 1 || toExplode.getType() == Material.BEDROCK || toExplode.getType() == Material.AIR) continue;
                                String regionName = WorldGuardUtils.getRegionName(toExplode.getLocation());
                                if (toExplode.getWorld().getName().startsWith("dungeon") && !(this.boss instanceof MotherOfYijki) && !regionName.equals("hanger") && !regionName.equals("bridge") || WorldGuardUtils.hasFlagDisabled(toExplode.getLocation(), DefaultFlag.OTHER_EXPLOSION) || toExplode.getState() instanceof InventoryHolder || !BossUtils.isWarzone(toExplode.getLocation())) continue;
                                NewBlockData data = this.boss.getModifier().removeBlockAndStore(this, toExplode.getLocation());
                                Location spawn = toExplode.getLocation().add(0.5, 1.1, 0.5);
                                EntityFallingBlock block = new EntityFallingBlock((World)((CraftWorld)toExplode.getWorld()).getHandle(), spawn.getX(), spawn.getY(), spawn.getZ(), Block.getById((int)data.getMaterial().getId()), data.getData()){

                                    public void h() {
                                        if (this.ticksLived >= 8) {
                                            try {
                                                List arraylist = this.world.getEntities((Entity)this, this.boundingBox, entity -> entity instanceof EntityHuman && !(entity instanceof EntityHumanNPC));
                                                int killed = 0;
                                                for (Entity ent : arraylist) {
                                                    CraftPlayer player;
                                                    if (!(ent instanceof EntityHuman) || (player = (CraftPlayer)ent.getBukkitEntity()).getGameMode() == GameMode.CREATIVE || player.isDead() || BossUtils.isInGodMode((Player)player)) continue;
                                                    player.getWorld().playSound(player.getLocation(), Sound.EXPLODE, 1.0f, 1.1f);
                                                    player.setMetadata("lastWitherDamage", (MetadataValue)new FixedMetadataValue((Plugin)CosmicBosses.get(), (Object)(System.currentTimeMillis() + 1000L)));
                                                    player.damage(0.0, (org.bukkit.entity.Entity)this.getBukkitEntity());
                                                    double damage = Math.max(5.0, player.getHealth() / 2.0);
                                                    if (damage > player.getHealth()) {
                                                        player.setHealth(0.0);
                                                    } else {
                                                        player.setHealth(player.getHealth() - damage);
                                                    }
                                                    ++killed;
                                                }
                                                if (killed > 0) {
                                                    this.die();
                                                    Bukkit.getLogger().info("Damaging " + killed + " players damage from falling block!");
                                                    return;
                                                }
                                            }
                                            catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        super.h();
                                    }
                                };
                                block.ticksLived = 1;
                                ((CraftWorld)toExplode.getWorld()).getHandle().addEntity((Entity)block, CreatureSpawnEvent.SpawnReason.CUSTOM);
                                FallingBlock fallingBlock = (FallingBlock)block.getBukkitEntity();
                                fallingBlock.setVelocity(new Vector((double)(ThreadLocalRandom.current().nextBoolean() ? -1 : 1) * Math.random(), 0.8 + Math.random(), (double)(ThreadLocalRandom.current().nextBoolean() ? -1 : 1) * Math.random()));
                                fallingBlock.setMetadata("chainReactionBlock", (MetadataValue)new FixedMetadataValue((Plugin)CosmicBosses.getInstance(), (Object)this.boss.getUuid()));
                                fallingBlock.setDropItem(false);
                                ++exploded;
                            }
                        }
                    }
                    Bukkit.getLogger().info("(Dungeon Yijki) Exploded " + exploded + " blocks.");
                }
            }
        } else if (event instanceof EntityChangeBlockEvent) {
            EntityChangeBlockEvent changeBlockEvent = (EntityChangeBlockEvent)event;
            changeBlockEvent.setCancelled(true);
        } else if (event instanceof EntityDamageByEntityEvent && (damageEvent = (EntityDamageByEntityEvent)event).getDamager().hasMetadata("chainReactionBlock")) {
            damageEvent.setDamage(300.0);
            Bukkit.getLogger().info("Setting damage to: " + damageEvent.getDamage());
        }
    }
}

