package com.conaxgames.customskulls.util;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.WorldServer;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftChunk;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Player;

public class ChunkUtil {
    public static boolean isLocationAInSameChunkAsLocationB(Location aLocation, Location bLocation) {
        double aX = Math.floor(aLocation.getBlockX() >> 4);
        double aZ = Math.floor(aLocation.getBlockZ() >> 4);
        double bX = Math.floor(bLocation.getBlockX() >> 4);
        double bZ = Math.floor(bLocation.getBlockZ() >> 4);

        return aX == bX && aZ == bZ;

    }

    public static Chunk getIfLoaded(final int chunkX, final int chunkZ, final World bWorld) {
        final net.minecraft.server.v1_8_R3.World world = (net.minecraft.server.v1_8_R3.World) ((CraftWorld) bWorld).getHandle();
        final net.minecraft.server.v1_8_R3.Chunk theChunk = world.getChunkIfLoaded(chunkX, chunkZ);
        return (theChunk == null) ? null : theChunk.bukkitChunk;
    }

    public static List<CraftEntity> getEntities(Chunk chunk) {
        final net.minecraft.server.v1_8_R3.Chunk nmsChunk = ((CraftChunk) chunk).getHandle();
        final List<CraftEntity> toReturn = new ArrayList<>();
        for (final List<net.minecraft.server.v1_8_R3.Entity> entities : nmsChunk.entitySlices) {
            for (final net.minecraft.server.v1_8_R3.Entity entity : entities) {
                toReturn.add(entity.getBukkitEntity());
            }
        }
        return toReturn;
    }
}
