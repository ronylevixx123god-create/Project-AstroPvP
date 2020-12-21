/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  lilypad.client.connect.api.Connect
 *  lilypad.client.connect.api.request.Request
 *  lilypad.client.connect.api.request.impl.RedirectRequest
 *  lilypad.client.connect.api.result.FutureResultListener
 *  lilypad.client.connect.api.result.StatusCode
 *  lilypad.client.connect.api.result.impl.RedirectResult
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lilypad.client.connect.api.Connect;
import lilypad.client.connect.api.request.Request;
import lilypad.client.connect.api.request.impl.RedirectRequest;
import lilypad.client.connect.api.result.FutureResultListener;
import lilypad.client.connect.api.result.StatusCode;
import lilypad.client.connect.api.result.impl.RedirectResult;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.CommandHub;
import org.arkhamnetwork.Arkkit.patches.HubCommandListener;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class HubCommand
extends ArkPlugin {
    private static Connect lily_connect;
    protected static List<String> SERVERS;

    public HubCommand(Plugin p) {
        super(p);
        SERVERS = new ArrayList<String>();
    }

    @Override
    public void enable() {
        if (!this.getBukkitPlugin().getServer().getPluginManager().isPluginEnabled("LilyPad-Connect")) {
            Bukkit.getConsoleSender().sendMessage((Object)ChatColor.RED + "(HubCommand) Could not enable patch due to lack of Bukkit-Connect.jar!");
            this.kill();
            return;
        }
        lily_connect = (Connect)Bukkit.getServer().getServicesManager().getRegistration(Connect.class).getProvider();
        if (Arkkit.get().getConfig().contains("settings.fallback_servers")) {
            SERVERS = Arkkit.get().getConfig().getStringList("settings.fallback_servers");
        }
        this.registerListener(new HubCommandListener());
        this.registerCommand(new CommandHub("hub", "/<command>", "Teleports you back to the server's main hub.", new ArrayList<String>(Arrays.asList("lobby", "logout", "quit", "exit", "leave"))));
    }

    @Override
    public void disable() {
        SERVERS.clear();
    }

    protected static void redirect(String server, final Player player) {
        try {
            if (server.equals("hub")) {
                server = "hub1";
            }
            lily_connect.request((Request)new RedirectRequest(server, player.getName())).registerListener((FutureResultListener)new FutureResultListener<RedirectResult>(){

                public void onResult(RedirectResult redirectResult) {
                    if (redirectResult.getStatusCode() != StatusCode.SUCCESS) {
                        player.sendMessage((Object)ChatColor.RED + "Connection lost to server.");
                    }
                }
            });
        }
        catch (Exception exception) {
            player.sendMessage((Object)ChatColor.RED + "Connection lost to server.");
        }
    }
}

