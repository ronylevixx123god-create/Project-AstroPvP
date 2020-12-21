/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.compilex.CompilexServerSync.CompilexServerSync
 *  com.compilex.CompilexServerSync.libs.GameServerState
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

import com.compilex.CompilexServerSync.CompilexServerSync;
import com.compilex.CompilexServerSync.libs.GameServerState;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lilypad.client.connect.api.Connect;
import lilypad.client.connect.api.request.Request;
import lilypad.client.connect.api.request.impl.RedirectRequest;
import lilypad.client.connect.api.result.FutureResultListener;
import lilypad.client.connect.api.result.StatusCode;
import lilypad.client.connect.api.result.impl.RedirectResult;
import org.arkhamnetwork.Arkkit.patches.FallbackLilyListener;
import org.arkhamnetwork.Arkkit.patches.FallbackServersListener;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class FallbackServers
extends ArkPlugin {
    public static Connect lily_connect;
    public static String lily_username;
    protected static boolean is_fallback_server;
    protected static List<String> SERVERS;
    protected static List<String> ONLINE_SERVERS;
    private FallbackLilyListener listener;
    protected static String KICK_MESSAGE;
    protected static String SERVER_RESTARTING_ALERT;

    public FallbackServers(Plugin p) {
        super(p);
        SERVERS = new ArrayList<String>();
        ONLINE_SERVERS = new ArrayList<String>();
    }

    @Override
    public void enable() {
        if (!this.getBukkitPlugin().getServer().getPluginManager().isPluginEnabled("LilyPad-Connect")) {
            Bukkit.getConsoleSender().sendMessage((Object)ChatColor.RED + "(FallbackServers) Could not enable patch due to lack of Bukkit-Connect.jar!");
            this.kill();
            return;
        }
        this.getBukkitPlugin().reloadConfig();
        lily_connect = (Connect)Bukkit.getServer().getServicesManager().getRegistration(Connect.class).getProvider();
        lily_username = lily_connect.getSettings().getUsername();
        this.listener = new FallbackLilyListener();
        SERVERS = this.getBukkitPlugin().getConfig().getStringList("settings.fallback_servers");
        if (SERVERS.contains("lobby45")) {
            Bukkit.getLogger().info("(FallbackServers) Unregistering 'lobby25-45' fallback servers...");
            SERVERS = new ArrayList<String>();
            for (int i = 1; i <= 24; ++i) {
                SERVERS.add("lobby" + i);
            }
            this.getBukkitPlugin().getConfig().set("settings.fallback_servers", SERVERS);
            this.getBukkitPlugin().saveConfig();
        }
        KICK_MESSAGE = this.getBukkitPlugin().getConfig().getString("language.fallback_servers.kick_message");
        SERVER_RESTARTING_ALERT = this.getBukkitPlugin().getConfig().getString("language.fallback_servers.server_restarting_alert");
        if (SERVERS.contains(lily_username)) {
            is_fallback_server = true;
            SERVERS.remove(lily_username);
        }
        Bukkit.getLogger().info("(FallbackServers) Registered fallback servers => " + SERVERS);
        this.registerListener(new FallbackServersListener());
        try {
            this.registerListener(this.listener);
            lily_connect.registerEvents((Object)this.listener);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        try {
            this.syncWithServerSync();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void syncWithServerSync() {
        for (String hub : SERVERS) {
            GameServerState state = CompilexServerSync.get().getLilypadServerMap().getServerStatus(hub);
            if (state == GameServerState.ONLINE) {
                ONLINE_SERVERS.add(hub);
                continue;
            }
            ONLINE_SERVERS.remove(hub);
        }
    }

    @Override
    public void disable() {
        SERVERS.clear();
        ONLINE_SERVERS.clear();
        if (this.listener != null) {
            lily_connect.unregisterEvents((Object)this.listener);
        }
        this.unregisterListeners();
    }

    public static void redirect(String server, final Player player) {
        try {
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

    protected static void redirect(String server, String player_name) {
        try {
            lily_connect.request((Request)new RedirectRequest(server, player_name)).registerListener((FutureResultListener)new FutureResultListener<RedirectResult>(){

                public void onResult(RedirectResult redirectResult) {
                    if (redirectResult.getStatusCode() != StatusCode.SUCCESS) {
                        // empty if block
                    }
                }
            });
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void setIgnoreDisable(boolean b) {
        FallbackServersListener.ignore_disable = b;
    }

    public static void disconnectAllPlayers() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getName().equals("TBNRfrags") || p.getName().equals("Woofless")) {
                p.kickPlayer("The planet you were on rebooted.");
                continue;
            }
            if (p.getName().equals("ypperin")) {
                FallbackServers.redirect("hacker", p);
                continue;
            }
            FallbackServers.redirect(FallbackServers.getRandomServer(), p);
        }
    }

    public static String getRandomServer() {
        return ONLINE_SERVERS.size() > 1 ? ONLINE_SERVERS.get(new Random().nextInt(ONLINE_SERVERS.size())) : FallbackServers.getRandomServerUnchecked();
    }

    public static String getRandomServerUnchecked() {
        return SERVERS.size() > 1 ? SERVERS.get(new Random().nextInt(SERVERS.size())) : SERVERS.get(0);
    }

    static {
        is_fallback_server = false;
    }
}

