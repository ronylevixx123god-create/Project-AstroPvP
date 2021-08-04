/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.sk89q.worldedit.bukkit.WorldEditPlugin
 *  lilypad.client.connect.api.Connect
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerChangedWorldEvent
 *  org.bukkit.event.player.PlayerJoinEvent
 *  org.bukkit.event.player.PlayerQuitEvent
 *  org.bukkit.event.player.PlayerRespawnEvent
 *  org.bukkit.event.player.PlayerTeleportEvent
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.java.JavaPlugin
 *  org.bukkit.plugin.messaging.PluginMessageListener
 */
package io.apice.clientcomms;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import io.apice.clientcomms.compat.CastleHandler;
import io.apice.clientcomms.compat.ClientHandler;
import io.apice.clientcomms.compat.FactionsHandler;
import io.apice.clientcomms.handshake.HandshakeContainer;
import io.apice.clientcomms.reporting.MessageType;
import io.apice.clientcomms.reporting.ReportMessage;
import io.apice.clientcomms.sql.SQLManager;
import io.apice.clientcomms.struct.AsyncPlayerProvider;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import javax.crypto.spec.SecretKeySpec;

import net.minecraft.server.v1_8_R3.EntityPlayer;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class ClientComms
extends JavaPlugin
implements Listener,
PluginMessageListener {
    public static boolean DEBUGGING = true;
    private static Random random = new Random();
    private static final String CHANNEL = "cosmic";
    private static final int VERSION = 1;
    private static ClientComms instance;
    private static ConcurrentHashMap<Player, OCMCUser> registeredPlayers;
    private static WorldEditPlugin worldEdit;
    private static volatile CopyOnWriteArrayList<String> queryQueue;
    private static String serverName;
    private static Map<Player, AtomicInteger> packets;
    private static Queue<QueuedMessage> rawDataQueue;
    private static Queue<QueuedMessage> messageQueue;
    private static volatile Queue<QueuedMessage> sendQueue;
    private static Queue<ReportMessage> reportQueue;
    private static boolean reportQueueIsProcessing;
    private static boolean ioInputQueueIsProcessing;
    private static boolean ioOutputQueueIsProcessing;
    private static Set<String> allowedVersions;
    private static Set<UUID> bypassUsers;
    private static Set<UUID> alertedOfOld;
    private static Set<UUID> badStandingReported;
    private static SecretKeySpec HMAC_KEY;
    private static ClientHandler handler;
    private static ServerType serverType;
    private AsyncPlayerProvider asyncPlayerProvider;
    public static final Gson gson;
    private static int MAX_PPS;
    private static Set<Player> recentlyWarned;

    public void onEnable() {
        byte[] hmacKeyBytes;
        instance = this;
        String hmacKey = "32114520F5E90BE185F3A1BF540BD1EE9965700C55A6DF8C50E441AB6280F40C250BEBA4F059D0C09B5731F8061FC1B47376D208D63842E09271F831C126A49D";
        if (hmacKey == null) {
            hmacKey = "";
        }
        try {
            hmacKeyBytes = Hex.decodeHex(hmacKey.toCharArray());
            if (hmacKeyBytes.length == 0) {
                hmacKeyBytes = new byte[1];
            }
        }
        catch (DecoderException x) {
            x.printStackTrace();
            hmacKeyBytes = new byte[1];
        }
        HMAC_KEY = HandshakeContainer.getKeySpec(hmacKeyBytes);
        this.saveDefaultConfig();
        // oh makes sense
        // so there's no packet io between the server and client at all right? it's just recieving
        // that's true
        ServerType serverType = ClientComms.serverType = ServerType.valueOf(this.getConfig().getString("serverType", "UNKNOWN")) == null ? ServerType.UNKNOWN : ServerType.valueOf(this.getConfig().getString("serverType", "UNKNOWN"));

        //Todo: CONAX: Replaced PrisonsHandler() and HCFactionsHandler() with FactionsHandler() as we don't have it
        /*handler = ClientComms.serverType == ServerType.COSMICPVP ? new FactionsHandler(ServerType.COSMICPVP) :
                (ClientComms.serverType == ServerType.COSMICPRISONS ? new PrisonsHandler(ServerType.COSMICPRISONS)
                        : (ClientComms.sereverType == ServerType.COSMIC_CASTLE ? new CastleHandler(ServerType.COSMIC_CASTLE)
                        : (ClientComms.serverType == ServerType.LOBBY ? new ClientHandler(ServerType.LOBBY)
                        : new ClientHandler(ServerType.UNKNOWN)))));*/

        /*handler = ClientComms.serverType == ServerType.COSMICPVP ? new FactionsHandler(ServerType.COSMICPVP) :
                (ClientComms.serverType == ServerType.COSMICPRISONS ? new FactionsHandler(ServerType.COSMICPRISONS)
                        : (ClientComms.serverType == ServerType.COSMICPVP_HCF ? new FactionsHandler(ServerType.COSMICPVP_HCF)
                        : (ClientComms.serverType == ServerType.COSMIC_CASTLE ? new CastleHandler(ServerType.COSMIC_CASTLE)
                        : (ClientComms.serverType == ServerType.LOBBY ? new ClientHandler(ServerType.LOBBY)
                        : new ClientHandler(ServerType.UNKNOWN)))));*/

        handler = new ClientHandler(ServerType.UNKNOWN);

        DEBUGGING = this.getConfig().getBoolean("debug", true);
        if (new File("plugins/ClientComms/sqlconfig.yml").exists()) {
            SQLManager.sqlEnabled = SQLManager.initializeSQL((Plugin)this);
        }
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, CHANNEL);
        Bukkit.getMessenger().registerIncomingPluginChannel(this, CHANNEL, this);
//        REMOVED LILYPAD
//        if (Bukkit.getPluginManager().isPluginEnabled("LilyPad-Connect")) {
//            Connect c = (Connect)Bukkit.getServer().getServicesManager().getRegistration(Connect.class).getProvider();
//            serverName = c.getSettings().getUsername();
//        }
        this.getServer().getPluginManager().registerEvents((Listener)handler, (Plugin)this);
        for (Player p : Bukkit.getOnlinePlayers()) {
            packets.put(p, new AtomicInteger());
        }
        this.getServer().getScheduler().runTaskTimer((Plugin)this, () -> packets.values().forEach(v -> v.set(0)), 20L, 20L);
        this.getServer().getScheduler().runTaskTimerAsynchronously((Plugin)this, () -> {
            while (!rawDataQueue.isEmpty()) {
                QueuedMessage queuedMessage = rawDataQueue.poll();
                try {
                    if (queuedMessage == null) continue;
                    OCMCMessage message = OCMCMessage.fromBytes(queuedMessage.getData());
                    queuedMessage.setOcmcMessage(message);
                    messageQueue.add(queuedMessage);
                }
                catch (SecurityException e) {
                    this.getLogger().log(Level.WARNING, "Error while processing packet from " + queuedMessage.getUsername(), e);
                }
                catch (Exception e) {
                    if (!DEBUGGING) continue;
                    e.printStackTrace();
                }
            }
        }, 1L, 1L);
        this.getServer().getScheduler().runTaskTimerAsynchronously(this, this::tickReportQueue, 1L, 1L);
        this.getServer().getScheduler().runTaskTimer(this, this::tickIncomingIOQueue, 1L, 1L);
        this.getServer().getScheduler().runTaskTimerAsynchronously(this, this::tickOutgoingIOQueue, 1L, 1L);
        allowedVersions.clear();
        // -Dcom.cosmicpvp.jarhash3=00000000000000000000000000000000
        // add that to runtime args
        // how did you launch it
        if (DEBUGGING) {
            allowedVersions.add("d5b4a4da90021cb2648d1ea4c5cd69e4");
        }

        this.getServer().getScheduler().runTaskTimerAsynchronously(this, () -> {
            try {
                if (this.getHash_1_8() == null) {
                    return;
                }
                String[] hashes = this.getHash_1_8().split(":");
                String siteHash = hashes[0];
                if (allowedVersions.add(siteHash)) {
                    this.getLogger().info("New allowed Cosmic Client version found: " + siteHash);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            try {
                for (String siteHash : this.getHash_Joe()) {
                    if (!allowedVersions.add(siteHash)) continue;
                    // might be somethingto do with this, idk tho there's a "JarHash" env var in the deobf jar if you take a look
                    this.getLogger().info("New allowed Cosmic Client version found: " + siteHash);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }, 0L, 600L);

        this.asyncPlayerProvider = new AsyncPlayerProvider();
        this.getServer().getPluginManager().registerEvents((Listener)this.asyncPlayerProvider, (Plugin)this);
        for (Player online : Bukkit.getOnlinePlayers()) {
            this.asyncPlayerProvider.onPlayerLogin(new PlayerJoinEvent(online, null));
        }
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.isOp() && cmd.getName().equals("clientcomms")) {
            sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "ConaxComms Stats " + this.getDescription().getVersion());
            sender.sendMessage(String.format(ChatColor.YELLOW + "Allowed Versions: " + ChatColor.WHITE + "%s", String.join(",", allowedVersions)));
            int onlineCount = 0;
            for (Player p : Bukkit.getOnlinePlayers()) {
                ++onlineCount;
            }
            sender.sendMessage(String.format(ChatColor.YELLOW + "Online Users: " + ChatColor.WHITE + "%d (%d%%)", ClientComms.getRegisteredPlayers().size(), (int) ((double) ClientComms.getRegisteredPlayers().size() / (double) onlineCount * 100.0)));
            sender.sendMessage(String.format(ChatColor.YELLOW + "Raw Queue:" + ChatColor.WHITE + " %d", ClientComms.getRawDataQueue().size()));
            sender.sendMessage(String.format(ChatColor.YELLOW + "Message Queue:" + ChatColor.WHITE + " %d", ClientComms.getMessageQueue().size()));
            sender.sendMessage(String.format(ChatColor.YELLOW + "Send Queue:" + ChatColor.WHITE + " %d", ClientComms.getSendQueue().size()));
            sender.sendMessage(String.format(ChatColor.YELLOW + "Report Queue:" + ChatColor.WHITE + " %d", ClientComms.getReportQueue().size()));

            Player player = (Player) sender;
            if (player.hasMetadata("cosmicClientVersion")) {
                player.sendMessage("y! " + player.getMetadata("cosmicClientVersion"));
            } else {
                player.sendMessage("n!");
            }

            HashMap<String, Object> data = new HashMap<>();
            data.put("permission", "cosmicclient.freeroam");
            data.put("value", player.hasPermission("cosmicclient.freeroam"));
            data.put("status", 0);
            player.sendPluginMessage(ClientComms.getInstance(), CHANNEL, new OCMCMessage("getfactioninfo", data).toBytes());
            player.sendMessage("roaming data");
            return true;
        }
        return false;
    }

    private void tickReportQueue() {
        if (reportQueueIsProcessing) {
            return;
        }
        reportQueueIsProcessing = true;
        try {
            while (!reportQueue.isEmpty()) {
                ReportMessage reportMessage = reportQueue.poll();
                if (reportMessage == null) continue;
                reportMessage.postData();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        reportQueueIsProcessing = false;
    }

    private void tickIncomingIOQueue() {
        if (ioInputQueueIsProcessing) {
            return;
        }
        ioInputQueueIsProcessing = true;
        try {
            this.processIncoming();
        }
        catch (Exception e) {
            ClientComms.getInstance().getLogger().log(Level.WARNING, "Error while processing Incoming I/O Queue", e);
        }
        ioInputQueueIsProcessing = false;
    }

    private void tickOutgoingIOQueue() {
        if (ioOutputQueueIsProcessing) {
            return;
        }
        ioOutputQueueIsProcessing = true;
        try {
            this.processOutgoing();
        }
        catch (Exception e) {
            ClientComms.getInstance().getLogger().log(Level.WARNING, "Error while processing Outgoing I/O Queue", e);
        }
        ioOutputQueueIsProcessing = false;
    }

    private void processIncoming() {
        while (!messageQueue.isEmpty()) {
            QueuedMessage queuedMessage = messageQueue.poll();
            if (queuedMessage == null) {
                return;
            }
            OCMCMessage message = queuedMessage.getOcmcMessage();
            if (!bypassUsers.contains(queuedMessage.getUuid()) && !allowedVersions.contains(message.getVersion())) {
                if (alertedOfOld.contains(queuedMessage.getUuid())) {
                    return;
                }
                alertedOfOld.add(queuedMessage.getUuid());
                Player player = this.asyncPlayerProvider.getPlayer(queuedMessage.getUuid());
                if (player != null) {
                    handler.sendNotification(player, (Object)ChatColor.RED + "You are running an old version of the Cosmic Client! Restart your client to update!", 10000);
                }
                return;
            }
            try {
                Bukkit.getConsoleSender().sendMessage("[ClientComms] " + message + " " + queuedMessage.getUuid());
                Bukkit.getConsoleSender().sendMessage("[ClientComms] " + message + " " + queuedMessage.getUuid());
                Bukkit.getConsoleSender().sendMessage("[ClientComms] " + message + " " + queuedMessage.getUuid());
                handler.handleMessage(queuedMessage.getUuid(), message);
            }
            catch (Exception e) {
                if (!DEBUGGING) continue;
                e.printStackTrace();
            }
        }
    }

    private void processOutgoing() {
        while (!sendQueue.isEmpty()) {
            QueuedMessage queuedMessage = sendQueue.poll();
            try {
                if (queuedMessage == null) {
                    return;
                }
                Player player = ClientComms.getInstance().getAsyncPlayerProvider().getPlayer(queuedMessage.getUuid());
                if (DEBUGGING) {
                    ClientComms.getInstance().getLogger().log(Level.INFO, "Sending packet to " + player.getName() + ": " + queuedMessage.getOcmcMessage().getOperation() + " : " + queuedMessage.getOcmcMessage().getData());
                }
                player.sendPluginMessage(ClientComms.getInstance(), CHANNEL, queuedMessage.getOcmcMessage().toBytes());
            }
            catch (Exception e) {
                ClientComms.getInstance().getLogger().log(Level.WARNING, "Error sending Cosmic Client message to " + queuedMessage.getUsername() + " (uuid: " + queuedMessage.getUuid() + ")", e);
            }
        }
    }

    public String getHash_1_8() {
        return null;
        //Todo: CONAX: Removed as URL doesn't work
        /*try {
            URL url = new URL("http://cosmicclient.com/versions");
            HttpURLConnection c = openConnection((URL)null, url, url);
            try (Scanner s = new Scanner(c.getInputStream())) {
                return s.nextLine();
            } finally {
                c.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }*/
    }


    public static byte[] readFully(InputStream in) throws IOException {
        int count;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        while ((count = in.read(buffer)) >= 0) {
            out.write(buffer, 0, count);
        }
        return out.toByteArray();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Set<String> getHash_Joe() {
        return Collections.emptySet();
        //Todo: CONAX: Removed due to URL not working
        /*try {
            byte[] data;
            URL url = new URL("http://cosmicclient.com/versions_internal.json");
            HttpURLConnection c = ClientComms.openConnection(null, url, url);
            try (InputStream in = c.getInputStream();){
                data = ClientComms.readFully(in);
            }
            finally {
                c.disconnect();
            }
            String text = new String(data, StandardCharsets.UTF_8);
            return (Set)gson.fromJson(text, new TypeToken<LinkedHashSet<String>>(){}.getType());
        }
        catch (Exception e) {
            e.printStackTrace();
            return Collections.emptySet();
        }*/
    }

    public static HttpURLConnection openConnection(URL prevPage, URL url, URL realURL) throws IOException {
        HttpURLConnection c = (HttpURLConnection)url.openConnection();
        c.setAllowUserInteraction(false);
        c.setConnectTimeout(30000);
        c.setDoInput(true);
        c.setDoOutput(false);
        c.setInstanceFollowRedirects(false);
        c.setReadTimeout(30000);
        c.setRequestMethod("GET");
        c.setUseCaches(false);
        c.setRequestProperty("Accept", "*/*");
        c.setRequestProperty("Accept-Language", "en-US,en;q=0.8");
        c.setRequestProperty("Connection", "close");
        if (realURL != null) {
            String h = realURL.getHost();
            if (realURL.getPort() >= 0 && realURL.getPort() != 80) {
                h = h + ":" + realURL.getPort();
            }
            c.setRequestProperty("Host", h);
        }
        if (prevPage != null) {
            c.setRequestProperty("Referer", prevPage.toString());
        }
        c.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36");
        return c;
    }

    public void onDisable() {
        this.tickIncomingIOQueue();
        this.tickOutgoingIOQueue();
        this.tickReportQueue();
        Bukkit.getMessenger().unregisterIncomingPluginChannel((Plugin)this, CHANNEL);
        Bukkit.getMessenger().unregisterOutgoingPluginChannel((Plugin)this, CHANNEL);
        if (SQLManager.queryThread != null) {
            SQLManager.queryThread.doWork();
            SQLManager.queryThread.interrupt();
        }
    }

    public synchronized void onPluginMessageReceived(String channel, Player player, byte[] bytes) {
        int numPackets;
        AtomicInteger atomicInteger = packets.get((Object)player);
        if (atomicInteger == null) {
            atomicInteger = new AtomicInteger(0);
            packets.put(player, atomicInteger);
        }
        if ((numPackets = atomicInteger.incrementAndGet()) > MAX_PPS) {
            if (!recentlyWarned.contains((Object)player)) {
                this.getLogger().log(Level.WARNING, player.getName() + " has passed MAX_PPS(" + MAX_PPS + ")");
                recentlyWarned.add(player);
            }
            return;
        }
        rawDataQueue.add(new QueuedMessage(channel, player, bytes));
    }

    public static void sendMessage(Player player, OCMCMessage message) {
        if (DEBUGGING) {
            ClientComms.getInstance().getLogger().info("Sending " + player.getName() + " " + message.getOperation());
        }
        if (DEBUGGING) {
            ClientComms.dumpData(message);
        }
        sendQueue.add(new QueuedMessage(CHANNEL, player, message));
    }

    public static void dumpData(OCMCMessage ocmcMessage) {
        if (ocmcMessage.getData() == null) {
            ClientComms.getInstance().getLogger().info("NULL data");
        } else {
            ocmcMessage.getData().entrySet().forEach(entry -> ClientComms.getInstance().getLogger().info((entry.getKey() == null ? "NULL" : (String)entry.getKey()) + ":" + (entry.getValue() == null ? "NULL" : entry.getValue())));
        }
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        packets.remove((Object)event.getPlayer());
        OCMCUser ocmcUser = registeredPlayers.remove((Object)player);
        if (ocmcUser != null) {
            ClientComms.getReportQueue().add(new ReportMessage(MessageType.EXIT, player.getUniqueId().toString(), player.getName(), player.getAddress().getAddress().getHostAddress(), serverType.name(), ClientComms.getServerName(), ocmcUser.getSystem(), ocmcUser.getVersion(), null));
        }
        event.getPlayer().removeMetadata("cosmicClientVersion", (Plugin)this);
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onTeleport(PlayerTeleportEvent event) {
        if (!registeredPlayers.containsKey((Object)event.getPlayer())) {
            return;
        }
        handler.handleTeleport(event);
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onWorldChange(PlayerChangedWorldEvent event) {
        if (!registeredPlayers.containsKey((Object)event.getPlayer())) {
            return;
        }
        handler.handleWorldChange(event);
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onRespawn(PlayerRespawnEvent event) {
        if (!registeredPlayers.containsKey((Object)event.getPlayer())) {
            return;
        }
        handler.handleRespawn(event);
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onJoin(PlayerJoinEvent event) {
        // Todo: manually setting
        event.getPlayer().setMetadata("cosmicClientVersion", new FixedMetadataValue(this, "d5b4a4da90021cb2648d1ea4c5cd69e4"));
        Bukkit.getConsoleSender().sendMessage("[BOOF]" + event.getPlayer().getName());
        Bukkit.getConsoleSender().sendMessage("[BOOF]" + event.getPlayer().getName());
        Bukkit.getConsoleSender().sendMessage("[BOOF]" + event.getPlayer().getName());
        Bukkit.getConsoleSender().sendMessage("[BOOF]" + event.getPlayer().getName());
        Bukkit.getConsoleSender().sendMessage("[BOOF]" + event.getPlayer().getName());
        Bukkit.getConsoleSender().sendMessage("[BOOF]" + event.getPlayer().getName());
        Bukkit.getConsoleSender().sendMessage("[BOOF]" + event.getPlayer().getName());
        if (event.getPlayer().hasPermission("clientcomms.bypass")) {
            bypassUsers.add(event.getPlayer().getUniqueId());
        }
        if (!registeredPlayers.containsKey(event.getPlayer())) {
            return;
        }
        handler.handleJoin(event);
    }

    public static WorldEditPlugin getWorldEdit() {
        if (worldEdit == null) {
            worldEdit = (WorldEditPlugin)Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
        }
        return worldEdit;
    }

    public String getJarHash() {
        return allowedVersions.iterator().next();
    }

    @EventHandler(priority=EventPriority.NORMAL, ignoreCancelled=true)
    public void onPlayerLogin(PlayerJoinEvent event) {
    }

    public static Random getRandom() {
        return random;
    }

    public static String getCHANNEL() {
        return CHANNEL;
    }

    public static int getVERSION() {
        return 1;
    }

    public static ClientComms getInstance() {
        return instance;
    }

    public static ConcurrentHashMap<Player, OCMCUser> getRegisteredPlayers() {
        return registeredPlayers;
    }

    public static CopyOnWriteArrayList<String> getQueryQueue() {
        return queryQueue;
    }

    public static String getServerName() {
        return serverName;
    }

    public static Map<Player, AtomicInteger> getPackets() {
        return packets;
    }

    public static Queue<QueuedMessage> getRawDataQueue() {
        return rawDataQueue;
    }

    public static Queue<QueuedMessage> getMessageQueue() {
        return messageQueue;
    }

    public static Queue<QueuedMessage> getSendQueue() {
        return sendQueue;
    }

    public static Queue<ReportMessage> getReportQueue() {
        return reportQueue;
    }

    public static boolean isReportQueueIsProcessing() {
        return reportQueueIsProcessing;
    }

    public static boolean isIoInputQueueIsProcessing() {
        return ioInputQueueIsProcessing;
    }

    public static boolean isIoOutputQueueIsProcessing() {
        return ioOutputQueueIsProcessing;
    }

    public static Set<String> getAllowedVersions() {
        return allowedVersions;
    }

    public static Set<UUID> getBypassUsers() {
        return bypassUsers;
    }

    public static Set<UUID> getAlertedOfOld() {
        return alertedOfOld;
    }

    public static Set<UUID> getBadStandingReported() {
        return badStandingReported;
    }

    public static SecretKeySpec getHMAC_KEY() {
        return HMAC_KEY;
    }

    public static ClientHandler getHandler() {
        return handler;
    }

    public static ServerType getServerType() {
        return serverType;
    }

    public AsyncPlayerProvider getAsyncPlayerProvider() {
        return this.asyncPlayerProvider;
    }

    static {
        registeredPlayers = new ConcurrentHashMap();
        queryQueue = new CopyOnWriteArrayList();
        serverName = null;
        packets = new ConcurrentHashMap<Player, AtomicInteger>();
        rawDataQueue = new ConcurrentLinkedQueue<QueuedMessage>();
        messageQueue = new ConcurrentLinkedQueue<QueuedMessage>();
        sendQueue = new ConcurrentLinkedQueue<QueuedMessage>();
        reportQueue = new ConcurrentLinkedQueue<ReportMessage>();
        reportQueueIsProcessing = false;
        ioInputQueueIsProcessing = false;
        ioOutputQueueIsProcessing = false;
        allowedVersions = new HashSet<String>();
        bypassUsers = new HashSet<UUID>();
        alertedOfOld = new ExpiringLruSet<UUID>(30000L);
        badStandingReported = new ExpiringLruSet<UUID>(30000L);
        HMAC_KEY = HandshakeContainer.getKeySpec(new byte[1]);
        handler = null;
        serverType = null;
        GsonBuilder builder = new GsonBuilder();
        builder.serializeSpecialFloatingPointValues();
        builder.enableComplexMapKeySerialization();
        builder.disableHtmlEscaping();
        builder.setPrettyPrinting();
        gson = builder.create();
        MAX_PPS = 100;
        recentlyWarned = new ExpiringLruSet<Player>(5000L);
    }
}

