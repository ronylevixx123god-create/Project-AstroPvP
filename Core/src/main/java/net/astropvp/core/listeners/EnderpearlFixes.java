package net.astropvp.core.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import java.util.EnumSet;
import java.util.Set;
import me.ifamasssxd.cosmicduel.DuelAPI;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;
public class EnderpearlFixes implements Listener {
    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        if (event.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL && event.getTo().getY() < 1.0) {
            event.setCancelled(true);
        }
    }

    private static final Set<Material> PASSABLE_MATERIALS = EnumSet.of(Material.ENCHANTMENT_TABLE, new Material[]{Material.STONE_SLAB2, Material.STEP, Material.WOOD_STEP, Material.FENCE_GATE, Material.ACACIA_FENCE_GATE, Material.BIRCH_FENCE_GATE, Material.DARK_OAK_FENCE_GATE, Material.JUNGLE_FENCE_GATE, Material.SPRUCE_FENCE_GATE, Material.DAYLIGHT_DETECTOR, Material.DAYLIGHT_DETECTOR_INVERTED});

    @EventHandler
    public void on(ProjectileHitEvent event) {
        if (!(event.getEntity() instanceof EnderPearl)) {
            return;
        }
        if (!(event.getEntity().getShooter() instanceof Player)) {
            return;
        }
        Player player = (Player)((Object)event.getEntity().getShooter());
        Block hitBlock = this.getBlockHitting(event.getEntity().getLocation(), event.getEntity().getVelocity());
        if (hitBlock == null) {
            return;
        }
        if (PASSABLE_MATERIALS.contains((Object)hitBlock.getType())) {
            Material upperMaterial;
            Material type;
            if (DuelAPI.hasDuel(player)) {
                return;
            }
            if (player.getWorld().getName().equalsIgnoreCase("dungeon_planet_nul") || player.getWorld().getName().equalsIgnoreCase("dungeon_destroyed_outpost") || player.getWorld().getName().equalsIgnoreCase("dungeon_yijki_a")) {
                return;
            }
            Material upOneBlock = hitBlock.getLocation().add(0.0, 1.0, 0.0).getBlock().getType();
            if (upOneBlock == Material.AIR) {
                Location location = hitBlock.getLocation().clone().add(0.0, 0.5, 0.0);
                location.setYaw(player.getLocation().getYaw());
                location.setPitch(player.getLocation().getPitch());
                player.teleport(location, PlayerTeleportEvent.TeleportCause.UNKNOWN);
                return;
            }
            Location blockBehindLocation = this.getLocationBehind(event.getEntity().getLocation(), event.getEntity().getVelocity(), 2.1);
            blockBehindLocation.setYaw(player.getLocation().getYaw());
            blockBehindLocation.setPitch(player.getLocation().getPitch());
            Block blockBehind = blockBehindLocation.getBlock();
            Material material = type = blockBehind == null ? null : blockBehind.getType();
            if (!(blockBehind != null && type != Material.AIR && !this.isPermeable(type) || (upperMaterial = blockBehind.getLocation().add(0.0, 1.0, 0.0).getBlock().getType()) != null && upperMaterial != Material.AIR)) {
                player.teleport(blockBehindLocation, PlayerTeleportEvent.TeleportCause.UNKNOWN);
            }
        }
    }

    private Location getLocationBehind(Location location, Vector vector, double multiply) {
        Location behind = location.clone().add(vector.clone().setY(0).normalize().multiply(multiply));
        if (behind.getBlock() == location.getBlock()) {
            return this.getLocationBehind(location, vector, multiply + 0.1);
        }
        if (behind.getBlock().getType() == Material.AIR && this.isPermeable(behind.getBlock().getRelative(BlockFace.DOWN).getType())) {
            behind.subtract(0.0, 1.0, 0.0);
        }
        return behind;
    }

    private Block getBlockHitting(Location location, Vector vector) {
        BlockIterator iterator = new BlockIterator(location.getWorld(), location.toVector(), vector.normalize(), 0.0, 2);
        Block hitBlock = null;
        while (iterator.hasNext() && ((hitBlock = iterator.next()).getType() == Material.AIR || this.isPermeable(hitBlock.getType()))) {
        }
        return hitBlock;
    }

    private boolean isPermeable(Material material) {
        switch (material) {
            case AIR:
            case LAVA:
            case WATER:
            case STATIONARY_LAVA:
            case STATIONARY_WATER: {
                return true;
            }
        }
        return false;
    }
}

