package io.apice.clientcomms.compat;

import io.apice.clientcomms.ClientComms;
import io.apice.clientcomms.EventType;
import io.apice.clientcomms.ExpiringLruSet;
import io.apice.clientcomms.OCMCMessage;
import io.apice.clientcomms.OCMCUser;
import io.apice.clientcomms.ServerType;
import io.apice.clientcomms.handshake.HandshakeContainer;
import io.apice.clientcomms.reporting.MessageType;
import io.apice.clientcomms.reporting.ReportMessage;
import io.apice.clientcomms.sql.SQLManager;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.logging.Level;
import javax.crypto.Mac;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class ClientHandler
implements Listener {
    protected final ServerType serverType;
    protected Map<String, ExpiringLruSet<UUID>> rateLimiters;
    protected Map<String, BiConsumer<Player, OCMCMessage>> channels;

    public ClientHandler(ServerType serverType) {
        this.serverType = serverType;
        this.rateLimiters = new HashMap<String, ExpiringLruSet<UUID>>();
        this.channels = new HashMap<String, BiConsumer<Player, OCMCMessage>>();
        this.channels.put("handshake", (player, incoming) -> this.handshake((Player)player, (OCMCMessage)incoming));
        this.channels.put("keepalive", (player, incoming) -> this.handleKeepAlive((Player)player, (OCMCMessage)incoming));
        this.channels.put("getpermission", (player, incoming) -> this.handlePermission((Player)player, (OCMCMessage)incoming));
        this.channels.put("setinfo", (player, incoming) -> this.handleInfo((Player)player, (OCMCMessage)incoming));
        this.channels.put("getworldborder", (player, incoming) -> this.handleWorldBorder((Player)player, (OCMCMessage)incoming));
        ClientComms.getInstance().getLogger().info("Setting up as " + (Object)((Object)serverType));
        ClientComms.getInstance().getLogger().info("Setting up as " + (Object)((Object)serverType));
        ClientComms.getInstance().getLogger().info("Setting up as " + (Object)((Object)serverType));
        ClientComms.getInstance().getLogger().info("Setting up as " + (Object)((Object)serverType));
        ClientComms.getInstance().getServer().getPluginManager().registerEvents(this, ClientComms.getInstance());
    }

    private void handshake(Player player, OCMCMessage msg) {
        if (ClientComms.getRegisteredPlayers().containsKey(player)) {
            return;
        }
        if (ClientComms.getBypassUsers().contains(player.getUniqueId())) {
            this.onHandshake(player, msg);
            return;
        }
        try {
            String handshakeJson = (String)msg.getData().get("handshake");
            if (handshakeJson == null) {
                throw new IllegalStateException("Missing handshake in data");
            }
            HandshakeContainer hs = HandshakeContainer.gson.fromJson(handshakeJson, HandshakeContainer.class);
            if (ClientComms.DEBUGGING) {
                ClientComms.getInstance().getLogger().info("handshake_encoded = " + hs.encode());
            }
            if (ClientComms.DEBUGGING) {
                ClientComms.getInstance().getLogger().info("checkSanity_client()");
            }
            hs.checkSanity_client();
            if (ClientComms.DEBUGGING) {
                ClientComms.getInstance().getLogger().info("checkSanity_server(true)");
            }
            hs.checkSanity_server(true);
            if (!hs.name.equals(player.getName())) {
                throw new IllegalStateException("Name mismatch " + hs.name + " != " + player.getName());
            }
            String player_uuid = player.getUniqueId().toString().replace("-", "");
            if (!hs.uuid.equals(player_uuid)) {
                throw new IllegalStateException("UUID mismatch " + hs.uuid + " != " + player_uuid);
            }
            if (!hs.version.equals(msg.getVersion())) {
                throw new IllegalStateException("Version mismatch " + hs.version + " != " + msg.getVersion());
            }
            if (!ClientComms.getAllowedVersions().contains(hs.version)) {
                throw new IllegalStateException("Client out of date: " + hs.version + ", allowed: " + ClientComms.getAllowedVersions());
            }
            if (!hs.isValid()) {
                throw new IllegalStateException("Expired handshake: " + hs.server_time + " + " + hs.lifespan);
            }
            Mac mac = HandshakeContainer.getMACInstance();
            mac.init(ClientComms.getHMAC_KEY());
            String hmac = hs.computeHMAC(mac);
            if (ClientComms.DEBUGGING) {
                ClientComms.getInstance().getLogger().info("computeHMAC = " + hmac);
            }
            if (!hs.hmac.equals(hmac)) {
                ClientComms.getInstance().getLogger().info("gson1 = " + handshakeJson);
                ClientComms.getInstance().getLogger().info("gson2 = " + hs.encodeWithoutHMAC());
                throw new IllegalStateException("HMAC mismatch " + hs.hmac + " != " + hmac);
            }
        }
        catch (Exception x) {
            ClientComms.getInstance().getLogger().log(Level.WARNING, "Error completing handshake", x);
            return;
        }
        this.onHandshake(player, msg);
    }

    protected void onHandshake(Player player, OCMCMessage msg) {
        OCMCUser ocmcUser = new OCMCUser(player, msg.getVersion());
        ClientComms.getRegisteredPlayers().put(player, ocmcUser);
        this.sendKeepAlive(ocmcUser);
        ClientComms.getInstance().getLogger().info(String.format("%s has logged in with CosmicClient %s", player.getName(), msg.getVersion()));
        SQLManager.updatePlayerClientVersion(player, msg);
        player.setMetadata("cosmicClientVersion", new FixedMetadataValue(ClientComms.getInstance(), msg.getVersion()));
        Bukkit.getScheduler().runTaskLater(ClientComms.getInstance(), () -> {
            if (player.isOnline()) {
                ClientComms.getReportQueue().add(new ReportMessage(MessageType.JOIN, player.getUniqueId().toString(), player.getName(), player.getAddress().getAddress().getHostAddress(), this.serverType.name(), ClientComms.getServerName(), ocmcUser.getSystem(), msg.getVersion(), null));
            }
        }, 20L);
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("featureset", this.serverType == ServerType.COSMIC_CASTLE ? ServerType.COSMICPVP.toString() : this.serverType.toString());
        data.put("status", 0);
        ClientComms.sendMessage(player, new OCMCMessage("setfeatureset", data));
        data = new HashMap();
        data.put("status", 0);
        ClientComms.sendMessage(player, new OCMCMessage("getworldborder", data));
        this.onUserRegister(ocmcUser);
    }

    public void handleMessage(UUID playerUUID, OCMCMessage incoming) throws Exception {
        if (incoming == null) {
            Bukkit.getConsoleSender().sendMessage("[ClientComms] incoming == null");
            return;
        }
        Bukkit.getConsoleSender().sendMessage("[ClientComms] incomming?? " + playerUUID + " " + incoming.getOperation());
        if (this.channels.containsKey(incoming.getOperation())) {
            String op;
            Player player = ClientComms.getInstance().getAsyncPlayerProvider().getPlayer(playerUUID);
            if (player == null) {
                return;
            }
            if (ClientComms.DEBUGGING) {
                ClientComms.getInstance().getLogger().info(player.getName() + " sent " + incoming.getOperation() + " packet.");
            }
            if (ClientComms.DEBUGGING) {
                ClientComms.dumpData(incoming);
            }
            if (!(op = incoming.getOperation()).equals("handshake") && !ClientComms.getRegisteredPlayers().containsKey(player)) {
                return;
            }
            this.channels.get(incoming.getOperation()).accept(player, incoming);
        }
    }

    protected void handleWorldBorder(Player player, OCMCMessage incoming) {
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("status", 0);
        ClientComms.sendMessage(player, new OCMCMessage("getworldborder", data));
    }

    public void sendEvent(Player player, String name, EventType eventType, long start, long duration) {
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("name", name);
        data.put("type", eventType.name());
        data.put("start", start);
        data.put("duration", duration);
        OCMCMessage message = new OCMCMessage("event", data);
        ClientComms.sendMessage(player, message);
    }

    protected void handleInfo(Player player, OCMCMessage incoming) {
        if (ClientComms.getRegisteredPlayers().containsKey((Object)player)) {
            OCMCUser user = ClientComms.getRegisteredPlayers().get((Object)player);
            user.setSystem((String)incoming.getData().get("system"));
        }
    }

    protected void onUserRegister(OCMCUser user) {
    }

    protected void handleKeepAlive(Player player, OCMCMessage incoming) {
    }

    protected void sendKeepAlive(OCMCUser user) {
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("status", 0);
        ClientComms.sendMessage(user.getPlayer(), new OCMCMessage("keepalive", data));
    }

    protected void handlePermission(Player player, OCMCMessage incoming) {
        String permission = (String)incoming.getData().get("permission");
        HashMap<String, Object> data = new HashMap<String, Object>();
        switch (permission) {
            case "printer": {
                data.put("permission", permission);
                data.put("value", player.hasPermission("cosmicclient.printer"));
                data.put("status", 0);
                ClientComms.sendMessage(player, new OCMCMessage("getpermission", data));
                break;
            }
            case "freeroam": {
                data.put("permission", permission);
                data.put("value", player.hasPermission("cosmicclient.freeroam"));
                data.put("status", 0);
                ClientComms.sendMessage(player, new OCMCMessage("getpermission", data));
                break;
            }
            case "feed": {
                data.put("permission", permission);
                data.put("value", player.hasPermission("essentials.feed"));
                data.put("status", 0);
                ClientComms.sendMessage(player, new OCMCMessage("getpermission", data));
                break;
            }
            case "breadcrumbs": {
                data.put("permission", permission);
                data.put("value", player.hasPermission("cosmicclient.breadcrumbs"));
                data.put("status", 0);
                ClientComms.sendMessage(player, new OCMCMessage("getpermission", data));
            }
        }
    }

    public void sendNotification(Player player, String text, int timeToDisplay) {
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("message", text);
        data.put("time", timeToDisplay);
        data.put("status", 0);
        OCMCMessage message = new OCMCMessage("notification", data);
        ClientComms.sendMessage(player, message);
    }

    protected boolean isRateLimited(Player player, OCMCMessage incoming, long rate) {
        try {
            ExpiringLruSet<UUID> players = this.rateLimiters.get(incoming.getOperation());
            if (players != null && players.contains(player.getUniqueId())) {
                return true;
            }
            if (players == null) {
                this.rateLimiters.put(incoming.getOperation(), new ExpiringLruSet(rate));
            }
        }
        catch (Exception e) {
            return true;
        }
        return false;
    }

    public void handleTeleport(PlayerTeleportEvent event) {
    }

    public void handleRespawn(PlayerRespawnEvent event) {
    }

    public void handleJoin(PlayerJoinEvent event) {
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("status", 0);
        ClientComms.sendMessage(event.getPlayer(), new OCMCMessage("getworldborder", data));
    }

    public void handleWorldChange(PlayerChangedWorldEvent event) {
    }
}

