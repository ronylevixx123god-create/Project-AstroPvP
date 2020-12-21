/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import org.arkhamnetwork.Arkkit.patches.CommandCustomMOTD;
import org.arkhamnetwork.Arkkit.patches.CustomMOTDListener;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class CustomMOTD
extends ArkPlugin {
    protected static LinkedList<String> motd_lines = new LinkedList();

    public CustomMOTD(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        CustomMOTD.loadMOTD();
        this.registerListener(new CustomMOTDListener());
        this.registerCommand(new CommandCustomMOTD("cmotd", "/<command>", "Reloads the custom MOTD from file if executed by console, or displays it if executed by a player."));
    }

    @Override
    public void disable() {
        motd_lines.clear();
    }

    protected static void loadMOTD() {
        File f = new File("plugins/Arkkit/motd.txt");
        if (!f.exists()) {
            try {
                new File("plugins/Arkkit/motd.txt").createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            Bukkit.getLogger().warning("(CustomMOTD) No file " + f.getName() + " found -- created blank.");
            return;
        }
        try {
            BufferedReader reader = new BufferedReader(new FileReader(f));
            String line = "";
            while ((line = reader.readLine()) != null) {
                motd_lines.add(ChatColor.translateAlternateColorCodes((char)'&', (String)line));
            }
            reader.close();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
        Bukkit.getLogger().info("(CustomMOTD) Loaded " + motd_lines.size() + " MOTD lines!");
    }

    protected static void reloadMOTD() {
        motd_lines.clear();
        CustomMOTD.loadMOTD();
        Bukkit.getLogger().info("(CustomMOTD) Configuration reloaded!");
    }

    protected static void sendMOTD(Player pl) {
        CustomMOTD.clearChat(pl);
        for (String s : motd_lines) {
            s = s.replace("[PLAYERNAME]", pl.getName());
            pl.sendMessage(s);
        }
    }

    protected static void clearChat(Player pl) {
        for (int x = 0; x < 50; ++x) {
            pl.sendMessage("");
        }
    }
}

