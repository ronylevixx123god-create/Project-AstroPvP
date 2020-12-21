/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.v1_7_R4.ChatSerializer
 *  net.minecraft.server.v1_7_R4.IChatBaseComponent
 *  net.minecraft.server.v1_7_R4.Packet
 *  net.minecraft.server.v1_7_R4.PacketPlayOutChat
 *  org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer
 *  org.spigotmc.ProtocolInjector$PacketTitle
 *  org.spigotmc.ProtocolInjector$PacketTitle$Action
 */
package com.cosmicpvp.cosmicutils.utils;

import com.cosmicpvp.cosmicutils.utils.ReflectionUtils;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class TitleManager {
    private static Class<?> packetClass = ReflectionUtils.getNMSClass("Packet");
    private static Class<?> nmsChatSerializer = ReflectionUtils.getNMSClass("ChatSerializer");
    private static int VERSION = 47;

    public static void sendTitle(Player p, String title, TitleColor color) {
        if (TitleManager.getVersion(p) < VERSION) {
            return;
        }
        String raw = String.format("{\"text\":\"\",\"extra\":[{\"text\":\"%s\",\"color\":\"%s\"}]}", title, color.getName());
        try {
            Object handle = ReflectionUtils.getNMSHandle(p);
            Object connection = ReflectionUtils.getField(handle.getClass(), "playerConnection").get(handle);
            Object serialized = ReflectionUtils.getMethod(nmsChatSerializer, "a", String.class).invoke(null, raw);
            Object packet = ProtocolInjector.PacketTitle.class.getConstructor(ProtocolInjector.PacketTitle.Action.class, ReflectionUtils.getNMSClass("IChatBaseComponent")).newInstance(new Object[]{ProtocolInjector.PacketTitle.Action.TITLE, serialized});
            ReflectionUtils.getMethod(connection.getClass(), "sendPacket", packetClass).invoke(connection, packet);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendSubTitle(Player p, String subtitle, TitleColor color) {
        if (TitleManager.getVersion(p) < VERSION) {
            return;
        }
        String raw = String.format("{\"text\":\"\",\"extra\":[{\"text\":\"%s\",\"color\":\"%s\"}]}", subtitle, color.getName());
        try {
            Object handle = ReflectionUtils.getNMSHandle(p);
            Object connection = ReflectionUtils.getField(handle.getClass(), "playerConnection").get(handle);
            Object serialized = ReflectionUtils.getMethod(nmsChatSerializer, "a", String.class).invoke(null, raw);
            Object packet = ProtocolInjector.PacketTitle.class.getConstructor(ProtocolInjector.PacketTitle.Action.class, ReflectionUtils.getNMSClass("IChatBaseComponent")).newInstance(new Object[]{ProtocolInjector.PacketTitle.Action.SUBTITLE, serialized});
            ReflectionUtils.getMethod(connection.getClass(), "sendPacket", packetClass).invoke(connection, packet);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendTimings(Player p, int fadeIn, int stay, int fadeOut) {
        if (TitleManager.getVersion(p) < VERSION) {
            return;
        }
        try {
            Object handle = ReflectionUtils.getNMSHandle(p);
            Object connection = ReflectionUtils.getField(handle.getClass(), "playerConnection").get(handle);
            Object packet = ProtocolInjector.PacketTitle.class.getConstructor(ProtocolInjector.PacketTitle.Action.class, Integer.TYPE, Integer.TYPE, Integer.TYPE).newInstance(new Object[]{ProtocolInjector.PacketTitle.Action.TIMES, fadeIn, stay, fadeOut});
            ReflectionUtils.getMethod(connection.getClass(), "sendPacket", packetClass).invoke(connection, packet);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendActionBar(Player player, String message) {
        CraftPlayer p = (CraftPlayer)player;
        if (p.getHandle().playerConnection.networkManager.getVersion() != 47) {
            return;
        }
        IChatBaseComponent cbc = IChatBaseComponent.ChatSerializer.a((String)("{\"text\": \"" + message + "\"}"));
        PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, true);
        p.getHandle().playerConnection.sendPacket((Packet)ppoc);
    }

    public static void reset(Player p) {
        if (TitleManager.getVersion(p) < VERSION) {
            return;
        }
        try {
            Object handle = ReflectionUtils.getNMSHandle(p);
            Object connection = ReflectionUtils.getField(handle.getClass(), "playerConnection").get(handle);
            Object packet = ProtocolInjector.PacketTitle.class.getConstructor(ProtocolInjector.PacketTitle.Action.class).newInstance(new Object[]{ProtocolInjector.PacketTitle.Action.RESET});
            ReflectionUtils.getMethod(connection.getClass(), "sendPacket", packetClass).invoke(connection, packet);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void clear(Player p) {
        if (TitleManager.getVersion(p) < VERSION) {
            return;
        }
        try {
            Object handle = ReflectionUtils.getNMSHandle(p);
            Object connection = ReflectionUtils.getField(handle.getClass(), "playerConnection").get(handle);
            Object packet = ProtocolInjector.PacketTitle.class.getConstructor(ProtocolInjector.PacketTitle.Action.class).newInstance(new Object[]{ProtocolInjector.PacketTitle.Action.CLEAR});
            ReflectionUtils.getMethod(connection.getClass(), "sendPacket", packetClass).invoke(connection, packet);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getVersion(Player p) {
        try {
            Object handle = ReflectionUtils.getNMSHandle(p);
            Object connection = ReflectionUtils.getField(handle.getClass(), "playerConnection").get(handle);
            Object network = ReflectionUtils.getField(connection.getClass(), "networkManager").get(connection);
            Object version = ReflectionUtils.getMethod(network.getClass(), "getVersion").invoke(network, new Object[0]);
            return (Integer)version;
        }
        catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static enum TitleColor {
        RED,
        GREEN,
        BLUE,
        YELLOW,
        GOLD,
        AQUA,
        GRAY;


        public String getName() {
            return this.name().toLowerCase();
        }
    }
}

