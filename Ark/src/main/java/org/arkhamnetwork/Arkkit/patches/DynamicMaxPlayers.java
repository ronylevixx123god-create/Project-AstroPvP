/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  net.minecraft.server.v1_7_R4.MinecraftServer
 *  net.minecraft.server.v1_7_R4.PlayerList
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import com.earth2me.essentials.PlayerList;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.lang.reflect.Field;

public class DynamicMaxPlayers
        extends ArkPlugin {
    public DynamicMaxPlayers(Plugin p) {
        super(p);
    }

    public static void setMaxPlayers(int x) {
        try {
            Field f = PlayerList.class.getDeclaredField("maxPlayers");
            f.setAccessible(true);
            f.set(MinecraftServer.getServer().getPlayerList(), x);
            DynamicMaxPlayers.writeMaxPlayers(x);
            Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "Max players set to " + x + "!");
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void writeMaxPlayers(int newMax) {
        try {
            String line;
            BufferedReader file = new BufferedReader(new FileReader("server.properties"));
            String input = "";
            while ((line = file.readLine()) != null) {
                if (line.startsWith("max-players=")) {
                    line = "max-players=" + newMax;
                }
                input = input + line + '\n';
            }
            file.close();
            FileOutputStream fileOut = new FileOutputStream("server.properties");
            fileOut.write(input.getBytes());
            fileOut.close();
        } catch (Exception e) {
            System.out.println("Problem reading file.");
        }
    }

    @Override
    public void enable() {
        this.registerCommand(new SetMaxPlayersCommand("setmaxplayers", "/<command>", "Sets max allowed players on the server."));
    }

    @Override
    public void disable() {
    }
}

