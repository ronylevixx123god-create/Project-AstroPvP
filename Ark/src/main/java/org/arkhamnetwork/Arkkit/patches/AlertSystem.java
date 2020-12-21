/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  lilypad.client.connect.api.Connect
 *  lilypad.client.connect.api.request.Request
 *  lilypad.client.connect.api.request.RequestException
 *  lilypad.client.connect.api.request.impl.MessageRequest
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lilypad.client.connect.api.Connect;
import lilypad.client.connect.api.request.Request;
import lilypad.client.connect.api.request.RequestException;
import lilypad.client.connect.api.request.impl.MessageRequest;
import org.arkhamnetwork.Arkkit.patches.AlertSystemListener;
import org.arkhamnetwork.Arkkit.patches.CommandArkhamAlert;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;

public class AlertSystem
extends ArkPlugin {
    protected static Connect lily_connect;
    public AlertSystemListener alert_system;
    protected static List<String> recipients;

    public AlertSystem(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        if (!this.getBukkitPlugin().getServer().getPluginManager().isPluginEnabled("LilyPad-Connect")) {
            Bukkit.getConsoleSender().sendMessage((Object)ChatColor.RED + "(AlertSystem) Could not enable patch due to lack of Bukkit-Connect.jar!");
            this.kill();
            return;
        }
        lily_connect = (Connect)Bukkit.getServer().getServicesManager().getRegistration(Connect.class).getProvider();
        this.getBukkitPlugin().reloadConfig();
        if (this.getBukkitPlugin().getConfig().contains("settings.alert_system.broadcast_servers") && this.getBukkitPlugin().getConfig().getStringList("settings.alert_system.broadcast_servers").size() > 0) {
            recipients = this.getBukkitPlugin().getConfig().getStringList("settings.alert_system.broadcast_servers");
            Bukkit.getLogger().info("(AlertSystem) Loaded recipient list: " + recipients);
        }
        Bukkit.getLogger().info("(AlertSystem) No recipient list specified, defaulting to ALL SERVERS.");
        ArrayList<String> aliases = new ArrayList<String>(Arrays.asList("galert", "alert"));
        this.alert_system = new AlertSystemListener();
        lily_connect.registerEvents((Object)this.alert_system);
        this.registerCommand(new CommandArkhamAlert("gbroadcast", "/<command> [args]", "Send a global alert message to all players.", aliases));
    }

    @Override
    public void disable() {
        lily_connect.unregisterEvents((Object)this.alert_system);
    }

    protected static void request(String channel, String message) {
        try {
            MessageRequest request = new MessageRequest(recipients, channel, message);
            lily_connect.request((Request)request);
        }
        catch (UnsupportedEncodingException | RequestException e) {
            e.printStackTrace();
        }
    }

    public static void sendRawMessage(String message) {
        AlertSystem.request("Arkkit.rawMessage", message);
    }

    static {
        recipients = new ArrayList<String>();
    }
}

