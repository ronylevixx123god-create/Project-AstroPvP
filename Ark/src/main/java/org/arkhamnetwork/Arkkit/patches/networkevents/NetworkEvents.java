/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.earth2me.essentials.Essentials
 *  com.google.common.collect.Lists
 *  lilypad.client.connect.api.Connect
 *  lilypad.client.connect.api.request.Request
 *  lilypad.client.connect.api.request.RequestException
 *  lilypad.client.connect.api.request.impl.MessageRequest
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.command.Command
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches.networkevents;

import com.earth2me.essentials.Essentials;
import com.google.common.collect.Lists;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import lilypad.client.connect.api.Connect;
import lilypad.client.connect.api.request.Request;
import lilypad.client.connect.api.request.RequestException;
import lilypad.client.connect.api.request.impl.MessageRequest;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.networkevents.CommandFind;
import org.arkhamnetwork.Arkkit.patches.networkevents.CommandHide;
import org.arkhamnetwork.Arkkit.patches.networkevents.CommandIgnore;
import org.arkhamnetwork.Arkkit.patches.networkevents.CommandMessage;
import org.arkhamnetwork.Arkkit.patches.networkevents.CommandMsgToggle;
import org.arkhamnetwork.Arkkit.patches.networkevents.CommandReply;
import org.arkhamnetwork.Arkkit.patches.networkevents.CommandStaff;
import org.arkhamnetwork.Arkkit.patches.networkevents.NE_SQLManager;
import org.arkhamnetwork.Arkkit.patches.networkevents.NE_SQLQueryThread;
import org.arkhamnetwork.Arkkit.patches.networkevents.NetworkEventsListener;
import org.arkhamnetwork.Arkkit.patches.networkevents.NetworkPrivateMessagesListener;
import org.arkhamnetwork.Arkkit.patches.networkevents.SimpleServerQueryTask;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.arkhamnetwork.Arkkit.utils.CommandUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.plugin.Plugin;

public class NetworkEvents
extends ArkPlugin {
    protected static Connect lily_connect;
    protected static List<String> recipients;
    protected static HashMap<String, String> lastMessaged;
    protected static String packet_split;
    protected static HashMap<String, List<String>> ignored_players;
    private static SimpleServerQueryTask server_query_task;
    private static NetworkPrivateMessagesListener network_private_messages;
    private static NE_SQLQueryThread sql_worker;
    List<String> message_aliases;
    List<String> reply_aliases;
    List<String> find_aliases;
    List<String> ignore_aliases;

    public NetworkEvents(Plugin p) {
        super(p);
        ignored_players = new HashMap();
        this.message_aliases = new ArrayList<String>(Arrays.asList("message", "tell", "whisper", "m", "emsg", "w", "t", "etell", "ewhisper"));
        this.reply_aliases = new ArrayList<String>(Arrays.asList("r", "repl"));
        this.find_aliases = new ArrayList<String>(Arrays.asList("whereis", "locate"));
        this.ignore_aliases = new ArrayList<String>(Arrays.asList("block", "unblock", "ignore", "unignore", "arkignore", "arkunignore", "blockf"));
    }

    protected static NE_SQLQueryThread getSQL() {
        return sql_worker;
    }

    public static void initSQL() {
        NE_SQLManager.initializeSQL((Plugin)Arkkit.get());
        sql_worker = new NE_SQLQueryThread();
        sql_worker.start();
    }

    public static void killSQL() {
        sql_worker.interrupt();
        sql_worker.doWork();
        sql_worker = null;
    }

    @Override
    public void enable() {
        if (!this.getBukkitPlugin().getServer().getPluginManager().isPluginEnabled("LilyPad-Connect")) {
            Bukkit.getConsoleSender().sendMessage((Object)ChatColor.RED + "(NetworkPrivateMessages) Could not enable patch due to lack of Bukkit-Connect.jar!");
            this.kill();
            return;
        }
        if (new File("plugins/Arkkit/NE_sqlconfig.yml").exists()) {
            NetworkEvents.initSQL();
            NE_SQLManager.sql_query.add("CREATE TABLE IF NOT EXISTS arkham_chat(player_uuid VARCHAR(36) PRIMARY KEY NOT NULL, ignore_list TEXT)");
            NE_SQLManager.sql_query.add("CREATE TABLE IF NOT EXISTS 5zig_users(player_uuid VARCHAR(36) PRIMARY KEY NOT NULL, player_name VARCHAR(16), protocol SMALLINT)");
            NE_SQLManager.sql_query.add("DELETE FROM arkham_chat WHERE ignore_list=''");
            this.registerListener(new NetworkEventsListener());
        } else {
            Bukkit.getLogger().info("NE_sqlconfig.yml not found, /ignore'd players will not save between sessions.");
        }
        lily_connect = (Connect)Bukkit.getServer().getServicesManager().getRegistration(Connect.class).getProvider();
        server_query_task = new SimpleServerQueryTask();
        network_private_messages = new NetworkPrivateMessagesListener();
        lily_connect.registerEvents((Object)server_query_task);
        lily_connect.registerEvents((Object)network_private_messages);
        this.registerTask(server_query_task.runTaskTimerAsynchronously(this.getBukkitPlugin(), 20L, 100L), this.getClass());
        if (this.getBukkitPlugin().getServer().getPluginManager().isPluginEnabled("Essentials")) {
            CommandUtils.unRegisterBukkitCommand(this.getBukkitPlugin().getServer().getPluginManager().getPlugin("Essentials"), (Command)((Essentials)this.getBukkitPlugin().getServer().getPluginManager().getPlugin("Essentials")).getCommand("msg"));
            CommandUtils.unRegisterBukkitCommand(this.getBukkitPlugin().getServer().getPluginManager().getPlugin("Essentials"), (Command)((Essentials)this.getBukkitPlugin().getServer().getPluginManager().getPlugin("Essentials")).getCommand("reply"));
            Bukkit.getLogger().info("(NetworkEvents) Unregistered Essential's /msg, /reply command.");
        }
        Bukkit.getScheduler().runTaskLater((Plugin)Arkkit.get(), new Runnable(){

            @Override
            public void run() {
                NetworkEvents.this.registerCommand(new CommandMessage("msg", "/<command> [player] [msg]", "Private-message a player.", NetworkEvents.this.message_aliases));
                NetworkEvents.this.registerCommand(new CommandReply("reply", "/<command> [msg]", "Reply to a private-message from a player.", NetworkEvents.this.reply_aliases));
                NetworkEvents.this.registerCommand(new CommandFind("find", "/<command> [player/IP] [msg]", "Find what server a player is on.", NetworkEvents.this.find_aliases));
                NetworkEvents.this.registerCommand(new CommandIgnore("ignore", "/<command> [player]", "Find what server a player is on.", NetworkEvents.this.ignore_aliases));
                NetworkEvents.this.registerCommand(new CommandHide("hide", "/<command>", "Toggle being able to be found with /find cross-server."));
                NetworkEvents.this.registerCommand(new CommandStaff("staff", "/<command>", "Display all online staff members and their respective servers."));
                NetworkEvents.this.registerCommand(new CommandMsgToggle("msgtoggle", "/<command>", "Toggle messages off", Lists.newArrayList((Object[])new String[]{"togglemsg", "toggletells"})));
            }
        }, 20L);
        if (new File("plugins/CosmicLobby.jar").exists()) {
            // empty if block
        }
    }

    @Override
    public void disable() {
        lily_connect.unregisterEvents((Object)server_query_task);
        lily_connect.unregisterEvents((Object)network_private_messages);
        sql_worker.interrupt();
        sql_worker.doWork();
        sql_worker = null;
    }

    protected static void request(String target_server, String channel, String message) {
        try {
            MessageRequest request = new MessageRequest(new ArrayList<String>(Arrays.asList(target_server)), channel, message);
            lily_connect.request((Request)request);
        }
        catch (UnsupportedEncodingException | RequestException e) {
            e.printStackTrace();
        }
    }

    protected static void requestAll(String channel, String message) {
        try {
            MessageRequest request = new MessageRequest(new ArrayList(), channel, message);
            lily_connect.request((Request)request);
        }
        catch (UnsupportedEncodingException | RequestException e) {
            e.printStackTrace();
        }
    }

    protected static void requestAll(String channel, byte[] message) {
        try {
            MessageRequest request = new MessageRequest(new ArrayList(), channel, message);
            lily_connect.request((Request)request);
        }
        catch (RequestException e) {
            e.printStackTrace();
        }
    }

    public static String getUsername() {
        return lily_connect.getSettings().getUsername();
    }

    public static SimpleServerQueryTask getServerSync() {
        return server_query_task;
    }

    static {
        recipients = new ArrayList<String>();
        lastMessaged = new HashMap();
        packet_split = "@@SPLIT@@";
    }
}

