package net.astropvp.antiphase;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import me.astropvp.proxypach.ProxyPatch;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.material.Directional;
import org.bukkit.material.Door;
import org.bukkit.material.Gate;
import org.bukkit.material.TrapDoor;
import org.bukkit.scheduler.BukkitRunnable;

public class AntiPhaseTask extends BukkitRunnable {
    private final AntiPhase plugin;
    private final Map<UUID, Location> validLocations = new HashMap();
    private Set<UUID> teleported = new HashSet();

    public void addTeleportedPlayer(UUID playerId) {
        this.teleported.add(playerId);
    }

    AntiPhaseTask(AntiPhase plugin) {
        this.plugin = plugin;
    }

    public void run() {

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (ProxyPatch.playerNames.contains(player.getName())) continue;
            UUID playerId = player.getUniqueId();
            Location loc1 = this.validLocations.containsKey(playerId) ? (Location) this.validLocations.get(playerId) : player.getLocation();
            Location loc2 = player.getLocation();
            if (loc1.getWorld() == loc2.getWorld() && !this.teleported.contains(playerId) && loc1.distance(loc2) > 10.0D) {
                player.teleport((Location) this.validLocations.get(playerId), TeleportCause.PLUGIN);
            } else if (this.isValidMove(playerId, loc1, loc2)) {
                this.validLocations.put(playerId, loc2);
            } else if (player.hasPermission("antiphase.bypass") || this.validLocations.containsKey(playerId)) {
                if (!this.plugin.getSettings().getMessage().isEmpty()) {
                    player.sendMessage(this.plugin.getSettings().getMessage());
                }

                player.teleport((Location) this.validLocations.get(playerId), TeleportCause.UNKNOWN);
            }
        }

    }

    public boolean isValidMove(UUID playerId, Location loc1, Location loc2) {
        if (loc1.getWorld() != loc2.getWorld()) {
            return true;
        } else if (this.teleported.remove(playerId)) {
            return true;
        } else {
            int moveMaxX = Math.max(loc1.getBlockX(), loc2.getBlockX());
            int moveMinX = Math.min(loc1.getBlockX(), loc2.getBlockX());
            int moveMaxY = Math.max(loc1.getBlockY(), loc2.getBlockY()) + 1;
            int moveMinY = Math.min(loc1.getBlockY(), loc2.getBlockY());
            int moveMaxZ = Math.max(loc1.getBlockZ(), loc2.getBlockZ());
            int moveMinZ = Math.min(loc1.getBlockZ(), loc2.getBlockZ());
            if (moveMaxY > 256) {
                moveMaxX = 256;
            }

            if (moveMinY > 256) {
                moveMinY = 256;
            }

            for(int x = moveMinX; x <= moveMaxX; ++x) {
                for(int z = moveMinZ; z <= moveMaxZ; ++z) {
                    for(int y = moveMinY; y <= moveMaxY; ++y) {
                        Block block = loc1.getWorld().getBlockAt(x, y, z);
                        if ((y != moveMinY || loc1.getBlockY() == loc2.getBlockY()) && this.hasPhased(block, loc1, loc2)) {
                            return false;
                        }
                    }
                }
            }

            return true;
        }
    }

    private boolean hasPhased(Block block, Location loc1, Location loc2) {
        if (this.plugin.getSettings().getPassable().contains(block.getType())) {
            return false;
        } else {
            double moveMaxX = Math.max(loc1.getX(), loc2.getX());
            double moveMinX = Math.min(loc1.getX(), loc2.getX());
            double moveMaxY = Math.max(loc1.getY(), loc2.getY()) + 1.8D;
            double moveMinY = Math.min(loc1.getY(), loc2.getY());
            double moveMaxZ = Math.max(loc1.getZ(), loc2.getZ());
            double moveMinZ = Math.min(loc1.getZ(), loc2.getZ());
            double blockMaxX = (double)(block.getLocation().getBlockX() + 1);
            double blockMinX = (double)block.getLocation().getBlockX();
            double blockMaxY = (double)(block.getLocation().getBlockY() + 2);
            double blockMinY = (double)block.getLocation().getBlockY();
            double blockMaxZ = (double)(block.getLocation().getBlockZ() + 1);
            double blockMinZ = (double)block.getLocation().getBlockZ();
            if (blockMinY > moveMinY) {
                --blockMaxY;
            }

            boolean hinge;
            if (this.plugin.getSettings().getDoors().contains(block.getType())) {
                Door door = (Door)block.getType().getNewData(block.getData());
                if (door.isTopHalf()) {
                    return false;
                }

                BlockFace facing = door.getFacing();
                if (door.isOpen()) {
                    Block up = block.getRelative(BlockFace.UP);
                    if (!this.plugin.getSettings().getDoors().contains(up.getType())) {
                        return false;
                    }

                    hinge = (up.getData() & 1) == 1;
                    if (facing == BlockFace.NORTH) {
                        facing = hinge ? BlockFace.WEST : BlockFace.EAST;
                    } else if (facing == BlockFace.EAST) {
                        facing = hinge ? BlockFace.NORTH : BlockFace.SOUTH;
                    } else if (facing == BlockFace.SOUTH) {
                        facing = hinge ? BlockFace.EAST : BlockFace.WEST;
                    } else {
                        facing = hinge ? BlockFace.SOUTH : BlockFace.NORTH;
                    }
                }

                if (facing == BlockFace.WEST) {
                    blockMaxX -= 0.8D;
                }

                if (facing == BlockFace.EAST) {
                    blockMinX += 0.8D;
                }

                if (facing == BlockFace.NORTH) {
                    blockMaxZ -= 0.8D;
                }

                if (facing == BlockFace.SOUTH) {
                    blockMinZ += 0.8D;
                }
            } else if (this.plugin.getSettings().getGates().contains(block.getType())) {
                if (((Gate)block.getType().getNewData(block.getData())).isOpen()) {
                    return false;
                }

                BlockFace face = ((Directional)block.getType().getNewData(block.getData())).getFacing();
                if (face != BlockFace.NORTH && face != BlockFace.SOUTH) {
                    blockMaxZ -= 0.2D;
                    blockMinZ += 0.2D;
                } else {
                    blockMaxX -= 0.2D;
                    blockMinX += 0.2D;
                }
            } else if (this.plugin.getSettings().getTrapdoors().contains(block.getType())) {
                TrapDoor door = (TrapDoor)block.getType().getNewData(block.getData());
                if (door.isOpen()) {
                    return false;
                }

                if (door.isInverted()) {
                    blockMinY += 0.85D;
                } else {
                    blockMaxY -= blockMinY > moveMinY ? 0.85D : 1.85D;
                }
            } else if (this.plugin.getSettings().getFences().contains(block.getType())) {
                blockMaxX -= 0.2D;
                blockMinX += 0.2D;
                blockMaxZ -= 0.2D;
                blockMinZ += 0.2D;
                if (moveMaxX > blockMaxX && moveMinX > blockMaxX && moveMaxZ > blockMaxZ && moveMinZ > blockMaxZ || moveMaxX < blockMinX && moveMinX < blockMinX && moveMaxZ > blockMaxZ && moveMinZ > blockMaxZ || moveMaxX > blockMaxX && moveMinX > blockMaxX && moveMaxZ < blockMinZ && moveMinZ < blockMinZ || moveMaxX < blockMinX && moveMinX < blockMinX && moveMaxZ < blockMinZ && moveMinZ < blockMinZ) {
                    return false;
                }

                if (block.getRelative(BlockFace.EAST).getType() == block.getType()) {
                    blockMaxX += 0.2D;
                }

                if (block.getRelative(BlockFace.WEST).getType() == block.getType()) {
                    blockMinX -= 0.2D;
                }

                if (block.getRelative(BlockFace.SOUTH).getType() == block.getType()) {
                    blockMaxZ += 0.2D;
                }

                if (block.getRelative(BlockFace.NORTH).getType() == block.getType()) {
                    blockMinZ -= 0.2D;
                }
            }

            boolean x = loc1.getX() < loc2.getX();
            boolean y = loc1.getY() < loc2.getY();
            hinge = loc1.getZ() < loc2.getZ();
            return this.plugin.getSettings().lenient() ? moveMinX != moveMaxX && moveMinY <= blockMaxY && moveMaxY >= blockMinY && moveMinZ <= blockMaxZ && moveMaxZ >= blockMinZ && (x && moveMinX <= blockMinX && moveMaxX >= blockMinX || !x && moveMinX <= blockMaxX && moveMaxX >= blockMaxX) || moveMinY != moveMaxY && moveMinX <= blockMaxX && moveMaxX >= blockMinX && moveMinZ <= blockMaxZ && moveMaxZ >= blockMinZ && (y && moveMinY <= blockMinY && moveMaxY >= blockMinY || !y && moveMinY <= blockMaxY && moveMaxY >= blockMaxY) || moveMinZ != moveMaxZ && moveMinX <= blockMaxX && moveMaxX >= blockMinX && moveMinY <= blockMaxY && moveMaxY >= blockMinY && (hinge && moveMinZ <= blockMinZ && moveMaxZ >= blockMinZ || !hinge && moveMinZ <= blockMaxZ && moveMaxZ >= blockMaxZ) : moveMinX <= blockMaxX && moveMaxX >= blockMinX && moveMinY <= blockMaxY && moveMaxY >= blockMinY && moveMinZ <= blockMaxZ && moveMaxZ >= blockMinZ;
        }
    }
}