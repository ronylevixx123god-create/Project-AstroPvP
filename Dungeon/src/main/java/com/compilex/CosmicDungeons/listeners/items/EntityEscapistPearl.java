/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.v1_8_R3.Blocks
 *  net.minecraft.server.v1_8_R3.DamageSource
 *  net.minecraft.server.v1_8_R3.EntityEnderPearl
 *  net.minecraft.server.v1_8_R3.EntityLiving
 *  net.minecraft.server.v1_8_R3.EntityPlayer
 *  net.minecraft.server.v1_8_R3.EnumParticle
 *  net.minecraft.server.v1_8_R3.MovingObjectPosition
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 *  org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer
 *  org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.event.player.PlayerTeleportEvent
 *  org.bukkit.event.player.PlayerTeleportEvent$TeleportCause
 *  org.bukkit.util.BlockIterator
 *  org.bukkit.util.Vector
 */
package com.compilex.CosmicDungeons.listeners.items;

import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.DamageSource;
import net.minecraft.server.v1_8_R3.EntityEnderPearl;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.MovingObjectPosition;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

public class EntityEscapistPearl
extends EntityEnderPearl {
    private boolean passedThroughSlabOrStair = false;
    private boolean passedThroughFence = false;
    private boolean passedThroughWeb = false;

    public EntityEscapistPearl(Player player) {
        super(((CraftPlayer)player).getHandle().world, (EntityLiving)((CraftPlayer)player).getHandle());
    }

    protected void a(MovingObjectPosition movingObjectPosition) {
        EntityLiving entityliving = this.getShooter();
        for (int i = 0; i < 32; ++i) {
            this.world.addParticle(EnumParticle.PORTAL, this.locX, this.locY + this.random.nextDouble() * 2.0, this.locZ, this.random.nextGaussian(), 0.0, this.random.nextGaussian(), new int[0]);
        }
        if (!this.world.isClientSide) {
            Location entityLocation = this.getBukkitEntity().getLocation();
            Vector vector = this.getBukkitEntity().getVelocity();
            BlockIterator iterator = new BlockIterator(entityLocation.getWorld(), entityLocation.toVector(), vector.normalize(), 0.0, 4);
            Block hitBlock = null;
            while (iterator.hasNext() && (hitBlock = iterator.next()).getType() == Material.AIR) {
            }
            if (hitBlock != null && (hitBlock.getType() == Material.WEB || hitBlock.getType() == Material.TRIPWIRE)) {
                return;
            }
            if (entityliving instanceof EntityPlayer) {
                EntityPlayer entityplayer = (EntityPlayer)entityliving;
                if (this.isConnected(entityplayer) && entityplayer.world == this.world) {
                    CraftPlayer player = entityplayer.getBukkitEntity();
                    Location location = this.getBukkitEntity().getLocation();
                    location.setPitch(player.getLocation().getPitch());
                    location.setYaw(player.getLocation().getYaw());
                    PlayerTeleportEvent teleEvent = new PlayerTeleportEvent((Player)player, player.getLocation(), location, PlayerTeleportEvent.TeleportCause.ENDER_PEARL);
                    Bukkit.getPluginManager().callEvent((Event)teleEvent);
                    if (!teleEvent.isCancelled() && !entityplayer.playerConnection.isDisconnected()) {
                        entityplayer.playerConnection.teleport(teleEvent.getTo());
                        entityliving.fallDistance = 0.0f;
                        CraftEventFactory.entityDamage = this;
                        entityliving.damageEntity(DamageSource.FALL, 5.0f);
                        CraftEventFactory.entityDamage = null;
                    }
                }
            } else if (entityliving != null) {
                entityliving.enderTeleportTo(this.locX, this.locY, this.locZ);
                entityliving.fallDistance = 0.0f;
            }
            this.die();
        }
    }

    private boolean isConnected(EntityPlayer player) {
        return player.playerConnection.networkManager.channel != null && player.playerConnection.networkManager.channel.isOpen();
    }

    private Location getClosestSafeLocation(Location location1) {
        for (int i = 0; i < 3; ++i) {
            if (location1.add(location1.getDirection().multiply(i)).getBlock().getType().isSolid()) continue;
            return location1.add(location1.getDirection().multiply(1));
        }
        return null;
    }

    private Location getCheckableLocation(Location originalLocation) {
        Location location = new Location(originalLocation.getWorld(), (double)originalLocation.getBlockX(), (double)originalLocation.getBlockY(), (double)originalLocation.getBlockZ());
        location.setDirection(originalLocation.getDirection());
        return location;
    }

    private boolean passableBlock(Block block, MovingObjectPosition movingObjectPosition) {
        return block.equals((Object)Blocks.TRIPWIRE) && block.equals((Object)Blocks.WEB);
    }
}

