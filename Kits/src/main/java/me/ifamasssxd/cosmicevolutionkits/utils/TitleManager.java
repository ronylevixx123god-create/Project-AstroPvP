/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.v1_7_R4.ChatSerializer
 *  net.minecraft.server.v1_7_R4.IChatBaseComponent
 *  net.minecraft.server.v1_7_R4.Packet
 *  net.minecraft.server.v1_7_R4.PacketPlayOutChat
 *  net.minecraft.util.io.netty.channel.Channel
 *  org.bukkit.Bukkit
 *  org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer
 *  org.bukkit.entity.Player
 *  org.spigotmc.ProtocolInjector$PacketTitle
 *  org.spigotmc.ProtocolInjector$PacketTitle$Action
 */
package me.ifamasssxd.cosmicevolutionkits.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import net.minecraft.server.v1_7_R4.ChatSerializer;
import net.minecraft.server.v1_7_R4.IChatBaseComponent;
import net.minecraft.server.v1_7_R4.Packet;
import net.minecraft.server.v1_7_R4.PacketPlayOutChat;
import net.minecraft.util.io.netty.channel.Channel;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.spigotmc.ProtocolInjector;

public class TitleManager {
    private static Class<?> nmsChatSerializer = Reflection.getNMSClass("ChatSerializer");
    private static int VERSION = 47;

    public static void sendTitle(Player p, String title, TitleColor color) {
        if (TitleManager.getVersion(p) < VERSION) {
            return;
        }
        String raw = String.format("{\"text\":\"\",\"extra\":[{\"text\":\"%s\",\"color\":\"%s\"}]}", title, color.getName());
        try {
            Object handle = Reflection.getHandle((Object)p);
            Object connection = Reflection.getField(handle.getClass(), "playerConnection").get(handle);
            Object serialized = Reflection.getMethod(nmsChatSerializer, "a", String.class).invoke(null, raw);
            Object packet = ProtocolInjector.PacketTitle.class.getConstructor(ProtocolInjector.PacketTitle.Action.class, Reflection.getNMSClass("IChatBaseComponent")).newInstance(new Object[]{ProtocolInjector.PacketTitle.Action.TITLE, serialized});
            Reflection.getMethod(connection.getClass(), "sendPacket", new Class[0]).invoke(connection, packet);
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
            Object handle = Reflection.getHandle((Object)p);
            Object connection = Reflection.getField(handle.getClass(), "playerConnection").get(handle);
            Object serialized = Reflection.getMethod(nmsChatSerializer, "a", String.class).invoke(null, raw);
            Object packet = ProtocolInjector.PacketTitle.class.getConstructor(ProtocolInjector.PacketTitle.Action.class, Reflection.getNMSClass("IChatBaseComponent")).newInstance(new Object[]{ProtocolInjector.PacketTitle.Action.SUBTITLE, serialized});
            Reflection.getMethod(connection.getClass(), "sendPacket", new Class[0]).invoke(connection, packet);
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
            Object handle = Reflection.getHandle((Object)p);
            Object connection = Reflection.getField(handle.getClass(), "playerConnection").get(handle);
            Object packet = ProtocolInjector.PacketTitle.class.getConstructor(ProtocolInjector.PacketTitle.Action.class, Integer.TYPE, Integer.TYPE, Integer.TYPE).newInstance(new Object[]{ProtocolInjector.PacketTitle.Action.TIMES, fadeIn, stay, fadeOut});
            Reflection.getMethod(connection.getClass(), "sendPacket", new Class[0]).invoke(connection, packet);
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
        IChatBaseComponent cbc = ChatSerializer.a((String)("{\"text\": \"" + message + "\"}"));
        PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, true);
        p.getHandle().playerConnection.sendPacket((Packet)ppoc);
    }

    public static void reset(Player p) {
        if (TitleManager.getVersion(p) < VERSION) {
            return;
        }
        try {
            Object handle = Reflection.getHandle((Object)p);
            Object connection = Reflection.getField(handle.getClass(), "playerConnection").get(handle);
            Object packet = ProtocolInjector.PacketTitle.class.getConstructor(ProtocolInjector.PacketTitle.Action.class).newInstance(new Object[]{ProtocolInjector.PacketTitle.Action.RESET});
            Reflection.getMethod(connection.getClass(), "sendPacket", new Class[0]).invoke(connection, packet);
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
            Object handle = Reflection.getHandle((Object)p);
            Object connection = Reflection.getField(handle.getClass(), "playerConnection").get(handle);
            Object packet = ProtocolInjector.PacketTitle.class.getConstructor(ProtocolInjector.PacketTitle.Action.class).newInstance(new Object[]{ProtocolInjector.PacketTitle.Action.CLEAR});
            Reflection.getMethod(connection.getClass(), "sendPacket", new Class[0]).invoke(connection, packet);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getVersion(Player p) {
        try {
            Object handle = Reflection.getHandle((Object)p);
            Object connection = Reflection.getField(handle.getClass(), "playerConnection").get(handle);
            Object network = Reflection.getField(connection.getClass(), "networkManager").get(connection);
            Object channel = Reflection.getField(network.getClass(), "m").get(network);
            Object version = Reflection.getMethod(network.getClass(), "getVersion", Channel.class).invoke(network, channel);
            return (Integer)version;
        }
        catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static class Reflection {
        public static String getVersion() {
            String name = Bukkit.getServer().getClass().getPackage().getName();
            String version = name.substring(name.lastIndexOf(46) + 1) + ".";
            return version;
        }

        public static Class<?> getNMSClass(String className) {
            String fullName = "net.minecraft.server." + Reflection.getVersion() + className;
            Class<?> clazz = null;
            try {
                clazz = Class.forName(fullName);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return clazz;
        }

        public static Class<?> getOBCClass(String className) {
            String fullName = "org.bukkit.craftbukkit." + Reflection.getVersion() + className;
            Class<?> clazz = null;
            try {
                clazz = Class.forName(fullName);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return clazz;
        }

        public static Object getHandle(Object obj) {
            try {
                return Reflection.getMethod(obj.getClass(), "getHandle", new Class[0]).invoke(obj, new Object[0]);
            }
            catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        public static Field getField(Class<?> clazz, String name) {
            try {
                Field field = clazz.getDeclaredField(name);
                field.setAccessible(true);
                return field;
            }
            catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        public static Method getMethod(Class<?> clazz, String name, Class<?> ... args) {
            for (Method m : clazz.getMethods()) {
                if (!m.getName().equals(name) || args.length != 0 && !Reflection.ClassListEqual(args, m.getParameterTypes())) continue;
                m.setAccessible(true);
                return m;
            }
            return null;
        }

        public static boolean ClassListEqual(Class<?>[] l1, Class<?>[] l2) {
            boolean equal = true;
            if (l1.length != l2.length) {
                return false;
            }
            for (int i = 0; i < l1.length; ++i) {
                if (l1[i] == l2[i]) continue;
                equal = false;
                break;
            }
            return equal;
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

