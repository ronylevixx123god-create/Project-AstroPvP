/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.com.google.common.reflect.TypeToken
 *  net.minecraft.util.com.google.gson.Gson
 *  net.minecraft.util.com.google.gson.GsonBuilder
 *  net.minecraft.util.io.netty.util.internal.ConcurrentSet
 *  org.bukkit.ChatColor
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.java.JavaPlugin
 */
package com.conaxgames.factions.utils.cmds;

import java.io.*;
import java.lang.reflect.Type;
import java.util.Set;
import java.util.UUID;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.netty.util.internal.ConcurrentSet;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandToggleSmoke
implements CommandExecutor {
    public static Set<UUID> toggled = new ConcurrentSet();
    private static Gson gsonBuilder = new GsonBuilder().create();
    private static Type token = new TypeToken<ConcurrentSet<UUID>>(){}.getType();
    private static File file;

    public CommandToggleSmoke(JavaPlugin plugin) throws IOException {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }
        if (!(file = new File(plugin.getDataFolder(), "toggles.json")).exists()) {
            file.createNewFile();
        }
        if ((toggled = (Set)gsonBuilder.fromJson((Reader)new FileReader(file), token)) == null) {
            toggled = new ConcurrentSet();
        }
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player)sender;
        if (toggled.add(player.getUniqueId())) {
            player.sendMessage(ChatColor.GREEN.toString() + (Object)ChatColor.BOLD + "(!) " + (Object)ChatColor.GREEN + "Player Flight particles " + (Object)ChatColor.RED + (Object)ChatColor.BOLD + "DISABLED");
        } else {
            toggled.remove(player.getUniqueId());
            player.sendMessage(ChatColor.GREEN.toString() + (Object)ChatColor.BOLD + "(!) " + (Object)ChatColor.GREEN + "Player Flight particles " + (Object)ChatColor.GREEN + (Object)ChatColor.BOLD + "ENABLED");
        }
        return false;
    }

    public static void saveToggles() throws IOException {
        String str = gsonBuilder.toJson(toggled, token);
        FileWriter writer = new FileWriter(file);
        writer.write(str);
        writer.flush();
        writer.close();
    }
}

