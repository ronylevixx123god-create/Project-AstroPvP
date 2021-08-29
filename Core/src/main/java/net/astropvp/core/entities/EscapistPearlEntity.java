package net.astropvp.core.entities;

import java.util.stream.IntStream;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerTeleportEvent;

public class EscapistPearlEntity extends EntityEnderPearl {
    private boolean passedThroughSlabOrStair = false;

    private boolean passedThroughFence = false;

    public EscapistPearlEntity(Player player) {
        super((((CraftPlayer)player).getHandle()).world, (EntityLiving)((CraftPlayer)player).getHandle());
    }

    protected void a(MovingObjectPosition movingObjectPosition) {
    try {
        if (movingObjectPosition == null) return;
        if (movingObjectPosition.a() == null) return;
        Block block = this.world.getType(new BlockPosition(movingObjectPosition.a())).getBlock();
        if (block == Blocks.TRIPWIRE)
            return;
        if (block == Blocks.WEB)
            return;
        if (getShooter() == null || !(getShooter() instanceof EntityPlayer)) {
            this.dead = true;
            return;
        }
        if ((!((EntityPlayer) getShooter()).playerConnection.isDisconnected()) && (getShooter()).world == this.world) {
            EntityPlayer entityplayer = (EntityPlayer) getShooter();
            CraftPlayer player = entityplayer.getBukkitEntity();
            Location location = getBukkitEntity().getLocation();
            location.setPitch(player.getLocation().getPitch());
            location.setYaw(player.getLocation().getYaw());
            Location toCheck = getCheckableLocation(location);
            if (toCheck.add(player.getEyeLocation().getDirection().multiply(1.5D)).getBlock().getType().isSolid() && this.passedThroughSlabOrStair)
                if (location.getBlock().getType().isSolid())
                    if (getClosestSafeLocation(location) != null) {
                        location = getClosestSafeLocation(location);
                    } else {
                        this.dead = true;
                        return;
                    }
            assert location != null;
            toCheck = getCheckableLocation(location);
            if (location.getBlock().getType().isSolid() && !toCheck.add(0.0D, 1.0D, 0.0D).getBlock().getType().isSolid())
                location.setY(location.getY() + 1.0D);
            toCheck = getCheckableLocation(location);
            /*for (int i = 1; i < 2; i++) {
                if (toCheck.add(i, i, i).getBlock().getType().isSolid() && !passableBlock(block, movingObjectPosition) && !this.passedThroughFence && !this.passedThroughSlabOrStair) {
                    this.dead = true;
                    return;
                }
            }*/
            if (location.getBlock().getType().isSolid()) {
                this.dead = true;
                return;
            }
            PlayerTeleportEvent event = new PlayerTeleportEvent((Player) player, player.getLocation(), location, PlayerTeleportEvent.TeleportCause.ENDER_PEARL);
            Bukkit.getPluginManager().callEvent((Event) event);
            if (!event.isCancelled() && !entityplayer.playerConnection.isDisconnected()) {
                entityplayer.playerConnection.teleport(event.getTo());
                IntStream.range(0, 32).forEach(i -> this.world.addParticle(EnumParticle.PORTAL, this.locX, this.locY + this.random.nextDouble() * 2.0D, this.locZ, this.random.nextGaussian(), 0.0D, this.random.nextGaussian()));
                (getShooter()).fallDistance = 0.0F;
                CraftEventFactory.entityDamage = (Entity) this;
                getShooter().damageEntity(DamageSource.FALL, 5.0F);
                CraftEventFactory.entityDamage = null;
            }
        }
        this.dead = true;
    } catch (Exception e) {}
    }

    private Location getClosestSafeLocation(Location location1) {
        for (int i = 0; i < 3; i++) {
            if (!location1.add(location1.getDirection().multiply(i)).getBlock().getType().isSolid())
                return location1.add(location1.getDirection().multiply(1));
        }
        return null;
    }

    private Location getCheckableLocation(Location originalLocation) {
        Location location = new Location(originalLocation.getWorld(), originalLocation.getBlockX(), originalLocation.getBlockY(), originalLocation.getBlockZ());
        location.setDirection(originalLocation.getDirection());
        return location;
    }

    private boolean passableBlock(Block block, MovingObjectPosition movingObjectPosition) {
        return block.equals(Blocks.TRIPWIRE);
    }
}
