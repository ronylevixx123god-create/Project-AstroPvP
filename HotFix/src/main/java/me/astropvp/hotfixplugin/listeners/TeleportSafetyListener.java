package me.astropvp.hotfixplugin.listeners;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import java.util.EnumSet;
import java.util.Set;
import me.ifamasssxd.cosmicduel.DuelAPI;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

public class TeleportSafetyListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        if (event.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL) {
            if (event.getTo().getY() < 1.0D)
                event.setCancelled(true);
            if (event.getTo().getBlock().getLocation().add(0.0D, 1.0D, 0.0D).getBlock().getType().isSolid() && event.getTo().getBlock().getLocation().add(0.0D, -1.0D, 0.0D).getBlock().getType().isSolid())
                event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerTeleportPearl(PlayerTeleportEvent event) {
        if (event.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL &&
                event.getTo().getY() < 1.0D)
            event.setCancelled(true);
    }

    @EventHandler
    public void playerDamageEvent(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            if (!e.getEntity().getWorld().getName().equalsIgnoreCase("world") && !e.getEntity().getWorld().getName().equalsIgnoreCase("flat")) return;
            if (e.getCause() != EntityDamageEvent.DamageCause.VOID) return;
            Player p = (Player) e.getEntity();
            if (p.getLocation().getY() < -1.0) {
                p.teleport(Bukkit.getWorld("world").getSpawnLocation(), PlayerTeleportEvent.TeleportCause.UNKNOWN);
                p.sendMessage(CC.translate("&cPrevented you from dying to void."));
            }
        }
    }


    private static final Set<Material> PASSABLE_MATERIALS = EnumSet.of(Material.ENCHANTMENT_TABLE, Material.STONE_SLAB2, Material.STEP, Material.WOOD_STEP, Material.FENCE_GATE, Material.ACACIA_FENCE_GATE, Material.BIRCH_FENCE_GATE, Material.DARK_OAK_FENCE_GATE, Material.JUNGLE_FENCE_GATE, Material.SPRUCE_FENCE_GATE, Material.DAYLIGHT_DETECTOR,
            Material.DAYLIGHT_DETECTOR_INVERTED);

    @EventHandler
    public void on(ProjectileHitEvent event) {
        if (!(event.getEntity() instanceof org.bukkit.entity.EnderPearl))
            return;
        if (!(event.getEntity().getShooter() instanceof Player))
            return;
        Player player = (Player)event.getEntity().getShooter();
        Block hitBlock = getBlockHitting(event.getEntity().getLocation(), event.getEntity().getVelocity());
        if (hitBlock == null)
            return;
        if (PASSABLE_MATERIALS.contains(hitBlock.getType())) {
            if (DuelAPI.hasDuel(player))
                return;
            if (player.getWorld().getName().equalsIgnoreCase("dungeon_planet_nul") || player
                    .getWorld().getName().equalsIgnoreCase("dungeon_destroyed_outpost") || player
                    .getWorld().getName().equalsIgnoreCase("dungeon_yijki_a"))
                return;
            Material upOneBlock = hitBlock.getLocation().add(0.0D, 1.0D, 0.0D).getBlock().getType();
            if (upOneBlock == Material.AIR) {
                Location location = hitBlock.getLocation().clone().add(0.0D, 0.5D, 0.0D);
                location.setYaw(player.getLocation().getYaw());
                location.setPitch(player.getLocation().getPitch());
                player.teleport(location, PlayerTeleportEvent.TeleportCause.UNKNOWN);
                return;
            }
            Location blockBehindLocation = getLocationBehind(event.getEntity().getLocation(), event.getEntity().getVelocity(), 2.1D);
            blockBehindLocation.setYaw(player.getLocation().getYaw());
            blockBehindLocation.setPitch(player.getLocation().getPitch());
            Block blockBehind = blockBehindLocation.getBlock();
            Material type = (blockBehind == null) ? null : blockBehind.getType();
            if (blockBehind == null || type == Material.AIR || isPermeable(type)) {
                Material upperMaterial = blockBehind.getLocation().add(0.0D, 1.0D, 0.0D).getBlock().getType();
                if (upperMaterial == null || upperMaterial == Material.AIR)
                    player.teleport(blockBehindLocation, PlayerTeleportEvent.TeleportCause.UNKNOWN);
            }
        }
    }
    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockPlaceEnder(BlockPlaceEvent event) {
        if (event.getBlock().getType() == Material.ENDER_PORTAL_FRAME) {
            event.getPlayer().sendMessage(CC.Red + "You cannot place this block!");
            event.setCancelled(true);
        }
    }

    private Location getLocationBehind(Location location, Vector vector, double multiply) {
        Location behind = location.clone().add(vector.clone().setY(0).normalize().multiply(multiply));
        if (behind.getBlock() == location.getBlock())
            return getLocationBehind(location, vector, multiply + 0.1D);
        if (behind.getBlock().getType() == Material.AIR && isPermeable(behind.getBlock().getRelative(BlockFace.DOWN).getType()))
            behind.subtract(0.0D, 1.0D, 0.0D);
        return behind;
    }

    private Block getBlockHitting(Location location, Vector vector) {
        BlockIterator iterator = new BlockIterator(location.getWorld(), location.toVector(), vector.normalize(), 0.0D, 2);
        Block hitBlock = null;
        while (iterator.hasNext()) {
            hitBlock = iterator.next();
            if (hitBlock.getType() != Material.AIR && !isPermeable(hitBlock.getType()))
                break;
        }
        return hitBlock;
    }

    private boolean isPermeable(Material material) {
        switch (material) {
            case AIR:
            case LAVA:
            case WATER:
            case STATIONARY_LAVA:
            case STATIONARY_WATER:
                return true;
        }
        return false;
    }
}


