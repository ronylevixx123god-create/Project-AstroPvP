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

import org.arkhamnetwork.Arkkit.patches.PartyListener;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Party
extends ArkPlugin {
    public Party(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            Party.sendPartyMessage(p);
        }
        this.registerListener(new PartyListener());
    }

    @Override
    public void disable() {
    }

    protected static void sendPartyMessage(Player p) {
        if (p.getName().equals("ypperin")) {
            return;
        }
        p.sendMessage("");
        p.sendMessage("");
        p.sendMessage(ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + "(!) SHHHHHHHHHHH!!");
        p.sendMessage((Object)ChatColor.LIGHT_PURPLE + "Today, January 20th, is our Community Manager's, " + ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + "ypperin's, " + ChatColor.AQUA.toString() + ChatColor.UNDERLINE.toString() + "ONE YEAR ANNIVERSARY" + (Object)ChatColor.LIGHT_PURPLE + " with CosmicPvP!");
        p.sendMessage("");
        p.sendMessage((Object)ChatColor.LIGHT_PURPLE + "Do " + ChatColor.AQUA.toString() + "NOT" + (Object)ChatColor.LIGHT_PURPLE + " tell her you saw this message, instead, just " + (Object)ChatColor.UNDERLINE + "spam her" + (Object)ChatColor.LIGHT_PURPLE + "  (and the chat when she's online) with: " + ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + "#Happy1YearYpperin");
        p.sendMessage(ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + "(!) SHHHHHHHHHHH!!");
        p.sendMessage("");
        p.sendMessage("");
    }
}

