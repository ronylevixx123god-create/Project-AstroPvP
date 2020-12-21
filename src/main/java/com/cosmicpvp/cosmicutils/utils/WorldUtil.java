/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.v1_7_R4.BlockEnderPortal
 *  net.minecraft.server.v1_7_R4.Entity
 *  net.minecraft.server.v1_7_R4.ExceptionWorldConflict
 *  net.minecraft.server.v1_7_R4.MinecraftServer
 *  net.minecraft.server.v1_7_R4.Packet
 *  net.minecraft.server.v1_7_R4.PacketPlayOutBlockBreakAnimation
 *  net.minecraft.server.v1_7_R4.PacketPlayOutWorldEvent
 *  net.minecraft.server.v1_7_R4.RegionFile
 *  net.minecraft.server.v1_7_R4.RegionFileCache
 *  net.minecraft.server.v1_7_R4.WorldServer
 *  org.arkhamnetwork.Arkkit.libs.ParticleEffect
 *  org.bukkit.craftbukkit.v1_7_R4.CraftServer
 *  org.bukkit.craftbukkit.v1_7_R4.CraftWorld
 *  org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity
 *  org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer
 */
package com.cosmicpvp.cosmicutils.utils;

import com.cosmicpvp.cosmicutils.utils.ReflectionUtils;
import com.google.common.collect.Lists;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import net.minecraft.server.v1_7_R4.BlockEnderPortal;
import net.minecraft.server.v1_7_R4.Entity;
import net.minecraft.server.v1_7_R4.ExceptionWorldConflict;
import net.minecraft.server.v1_7_R4.MinecraftServer;
import net.minecraft.server.v1_7_R4.Packet;
import net.minecraft.server.v1_7_R4.PacketPlayOutBlockBreakAnimation;
import net.minecraft.server.v1_7_R4.PacketPlayOutWorldEvent;
import net.minecraft.server.v1_7_R4.RegionFile;
import net.minecraft.server.v1_7_R4.RegionFileCache;
import net.minecraft.server.v1_7_R4.WorldServer;
import org.arkhamnetwork.Arkkit.libs.ParticleEffect;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.world.WorldSaveEvent;
import org.bukkit.event.world.WorldUnloadEvent;

public class WorldUtil {
    public static void sendWorldEffect(Block b, int effectID) {
        PacketPlayOutWorldEvent particles = new PacketPlayOutWorldEvent(effectID, b.getX(), b.getY(), b.getZ(), b.getTypeId(), false);
        ParticleEffect.sendWorldEventPacket((PacketPlayOutWorldEvent)particles, (Location)b.getLocation());
    }

    public static void sendWorldEffect(Block b, int materialID, int effectID) {
        PacketPlayOutWorldEvent particles = new PacketPlayOutWorldEvent(effectID, b.getX(), b.getY(), b.getZ(), materialID, false);
        ParticleEffect.sendWorldEventPacket((PacketPlayOutWorldEvent)particles, (Location)b.getLocation());
    }

    public static void sendBlockBreakAnimation(Block block, int breaks, List<Player> players) {
        PacketPlayOutBlockBreakAnimation animation = new PacketPlayOutBlockBreakAnimation(0, block.getX(), block.getY(), block.getZ(), breaks);
        for (Player pl : players) {
            ((CraftPlayer)pl).getHandle().playerConnection.sendPacket((Packet)animation);
        }
    }

    public static void sendWorldBreakPacket(Block b) {
        PacketPlayOutWorldEvent particles = new PacketPlayOutWorldEvent(2001, b.getX(), b.getY(), b.getZ(), b.getTypeId(), false);
        ParticleEffect.sendWorldEventPacket((PacketPlayOutWorldEvent)particles, (Location)b.getLocation());
    }

    public static boolean allowEnderPortalOutsideOverworld(boolean allowed) {
        BlockEnderPortal.a = allowed;
        return allowed;
    }

    public static List<org.bukkit.entity.Entity> getNearbyEntities(org.bukkit.entity.Entity entity, double x, double y, double z, String ... filter) {
        CraftWorld world = (CraftWorld)entity.getWorld();
        Entity nmsEntity = ((CraftEntity)entity).getHandle();
        ArrayList<org.bukkit.entity.Entity> retr = Lists.newArrayList();
        ArrayList<String> filters = Lists.newArrayList(filter);
        for (Object ent2 : world.getHandle().getEntities(nmsEntity, nmsEntity.boundingBox.grow(x, y, z), ent -> {
            for (String filt : filters) {
                if (!ent.getClass().getName().endsWith(filt)) continue;
                return true;
            }
            return false;
        })) {
            Entity nms = (Entity)ent2;
            retr.add((org.bukkit.entity.Entity)nms.getBukkitEntity());
        }
        return retr;
    }

    public static boolean forceUnloadWorld(World world, boolean save) {
        if (world == null) {
            return false;
        }
        WorldServer handle = ((CraftWorld)world).getHandle();
        LinkedHashMap worlds = (LinkedHashMap)ReflectionUtils.getObject(CraftServer.class, (Object)handle.getServer(), "worlds");
        MinecraftServer console = handle.getMinecraftServer();
        WorldUnloadEvent e = new WorldUnloadEvent((World)handle.getWorld());
        Bukkit.getPluginManager().callEvent(e);
        if (e.isCancelled()) {
            return false;
        }
        if (save) {
            try {
                handle.save(true, null);
                handle.saveLevel();
                WorldSaveEvent event = new WorldSaveEvent((World)handle.getWorld());
                Bukkit.getPluginManager().callEvent(event);
            }
            catch (ExceptionWorldConflict ex) {
                Bukkit.getLogger().log(Level.SEVERE, null, ex);
            }
        }
        worlds.remove(world.getName().toLowerCase());
        console.worlds.remove((Object)handle);
        WorldUtil.unloadRegionFolder(world, null);
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void unloadRegionFolder(World world, String region) {
        File parentFolder = world.getWorldFolder();
        Class<RegionFileCache> class_ = RegionFileCache.class;
        synchronized (RegionFileCache.class) {
            WorldUtil.removeFiles(parentFolder);
            Iterator i = RegionFileCache.a.entrySet().iterator();
            block5: while (i.hasNext()) {
                Map.Entry entry = i.next();
                for (File child = ((File)entry.getKey()).getAbsoluteFile(); child != null; child = child.getParentFile()) {
                    if (!child.equals(parentFolder)) continue;
                    i.remove();
                    try {
                        ((RegionFile)entry.getValue()).c();
                    }
                    catch (IOException ex) {
                        Bukkit.getLogger().log(Level.SEVERE, null, ex);
                    }
                    continue block5;
                }
            }
            // ** MonitorExit[var3_3] (shouldn't be in output)
            return;
        }
    }

    private static void removeFiles(File file) {
        if (file.isDirectory()) {
            for (File fil : file.listFiles()) {
                WorldUtil.removeFiles(fil);
            }
        }
        WorldUtil.removeFromCache(file);
    }

    public static void removeFromCache(File file) {
        RegionFile regionFile = (RegionFile)RegionFileCache.a.remove(file);
        if (regionFile == null && (regionFile = (RegionFile)RegionFileCache.a.remove(file.getAbsoluteFile())) != null) {
            Bukkit.getLogger().info("found match in absolute instead: " + file.getAbsoluteFile());
        }
        if (regionFile != null) {
            try {
                Bukkit.getLogger().info("Deleting cached regionFile: " + file.toString());
                regionFile.c();
            }
            catch (IOException ex) {
                Bukkit.getLogger().log(Level.SEVERE, null, ex);
            }
        }
    }
}

