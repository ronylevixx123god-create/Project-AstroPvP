/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.earth2me.essentials.Essentials
 *  lilypad.client.connect.api.event.EventListener
 *  lilypad.client.connect.api.event.MessageEvent
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Listener
 *  org.bukkit.scheduler.BukkitRunnable
 */
package org.arkhamnetwork.Arkkit.patches.networkevents;

import com.earth2me.essentials.Essentials;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import lilypad.client.connect.api.event.EventListener;
import lilypad.client.connect.api.event.MessageEvent;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.networkevents.NetworkEvents;
import org.arkhamnetwork.Arkkit.patches.networkevents.NetworkEventsListener;
import org.arkhamnetwork.Arkkit.patches.networkevents.NetworkPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

public class SimpleServerQueryTask
extends BukkitRunnable
implements Listener {
    private Map<String, Map<String, NetworkPlayer>> serverPlayers = new HashMap<String, Map<String, NetworkPlayer>>();
    private Map<String, Map<String, NetworkPlayer>> staffPlayers = new HashMap<String, Map<String, NetworkPlayer>>();
    private Map<String, Map<String, NetworkPlayer>> ultraHiddenStaff = new HashMap<String, Map<String, NetworkPlayer>>();
    private Set<UUID> isHidden = new HashSet<UUID>();
    public static Map<String, Long> lastReply = new HashMap<String, Long>();
    private Essentials ess = null;

    public SimpleServerQueryTask() {
        if (Bukkit.getPluginManager().isPluginEnabled("Essentials")) {
            this.ess = (Essentials)Bukkit.getPluginManager().getPlugin("Essentials");
        }
    }

    public void run() {
        Iterator<Map.Entry<String, Long>> replys = lastReply.entrySet().iterator();
        while (replys.hasNext()) {
            Map.Entry<String, Long> reply = replys.next();
            if (reply.getValue() + 15000L >= System.currentTimeMillis()) continue;
            replys.remove();
            this.serverPlayers.remove(reply.getKey());
        }
        HashMap<String, String> normalPlayersToUpdate = new HashMap<String, String>();
        HashMap<String, String> ultraHidden = new HashMap<String, String>();
        HashMap<String, String> staffPlayersToUpdate = new HashMap<String, String>();
        for (Map.Entry<String, Player> entry : Arkkit.get().getAsyncPlayerMap().entrySet()) {
            Player pl = entry.getValue();
            if (pl == null || pl.getAddress() == null || pl.getAddress().getAddress() == null) {
                Arkkit.get().getAsyncPlayerMap().remove(entry.getKey());
                continue;
            }
            boolean hidden = this.isPlayerHidden(pl);
            if (hidden) {
                ultraHidden.put(pl.getName(), pl.getAddress().getAddress().getHostAddress());
            }
            if (NetworkEventsListener.staffMembers.contains(pl.getName())) {
                if (hidden) continue;
                staffPlayersToUpdate.put(pl.getName(), pl.getAddress().getAddress().getHostAddress());
            }
            if (hidden) continue;
            normalPlayersToUpdate.put(pl.getName(), pl.getAddress().getAddress().getHostAddress());
        }
        try {
            NetworkEvents.requestAll("NE.SQU", this.convertMapStringToByArray(normalPlayersToUpdate));
            NetworkEvents.requestAll("NE.SQSU", this.convertMapStringToByArray(staffPlayersToUpdate));
            NetworkEvents.requestAll("NE.ULTRA", this.convertMapStringToByArray(ultraHidden));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private byte[] convertMapStringToByArray(Map<String, String> map) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream outputStream = new DataOutputStream(byteArrayOutputStream);
        outputStream.writeInt(map.size());
        map.forEach((key, value) -> {
            try {
                outputStream.writeUTF((String)key);
                outputStream.writeUTF((String)value);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        });
        outputStream.flush();
        return byteArrayOutputStream.toByteArray();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Map<String, String> convertByteArrayToMap(byte[] array) {
        HashMap<String, String> retr = new HashMap<String, String>();
        ByteArrayInputStream input = new ByteArrayInputStream(array);
        DataInputStream stream = new DataInputStream(input);
        try {
            int howManyToRead = stream.readInt();
            if (howManyToRead > 0) {
                for (int i = 0; i < howManyToRead; ++i) {
                    retr.put(stream.readUTF(), stream.readUTF());
                }
            }
        }
        catch (Exception e) {
            Bukkit.getLogger().info("UNABLE TO READ INCOMING STAFF QUERY BYTE ARRAY: " + array);
            try {
                Bukkit.getLogger().info("String value: " + new String(array));
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        finally {
            try {
                stream.close();
                input.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return retr;
    }

    private Map<String, NetworkPlayer> convertMapToNetworkPlayer(String sender, Map<String, String> message) {
        HashMap<String, NetworkPlayer> onlinePlayers = new HashMap<String, NetworkPlayer>();
        message.forEach((username, ipAddress) -> onlinePlayers.put(username.toLowerCase(), new NetworkPlayer(null, (String)username, sender, (String)ipAddress)));
        return onlinePlayers;
    }

    @EventListener
    public void onSync(MessageEvent event) {
        String sender = event.getSender();
        if (event.getChannel().startsWith("NE.")) {
            lastReply.put(sender, System.currentTimeMillis());
        }
        if (event.getChannel().equals("NE.SQU")) {
            this.serverPlayers.put(sender, this.convertMapToNetworkPlayer(sender, this.convertByteArrayToMap(event.getMessage())));
            return;
        }
        if (event.getChannel().equals("NE.SQSU")) {
            this.staffPlayers.put(sender, this.convertMapToNetworkPlayer(sender, this.convertByteArrayToMap(event.getMessage())));
            return;
        }
        if (event.getChannel().equals("NE.ULTRA")) {
            this.ultraHiddenStaff.put(sender, this.convertMapToNetworkPlayer(sender, this.convertByteArrayToMap(event.getMessage())));
            return;
        }
        try {
            NetworkPlayer player;
            String server;
            String[] split;
            if (event.getChannel().equals("NetworkEvents.server_query_task")) {
                split = event.getMessageAsString().split(",");
                server = split[0];
                HashMap<String, NetworkPlayer> networkPlayers = new HashMap<String, NetworkPlayer>();
                for (String playerInfo : split[1].split(";")) {
                    player = new NetworkPlayer(null, playerInfo, server, null);
                    networkPlayers.put(playerInfo.toLowerCase(), player);
                }
                this.serverPlayers.put(server, networkPlayers);
                lastReply.put(server, System.currentTimeMillis());
            } else if (event.getChannel().equals("NetworkEvents.server_query_task_extra")) {
                this.deserializeExtraPlayerData(event.getMessageAsString(), this.serverPlayers);
            }
            if (event.getChannel().equals("NetworkEvents.staff_query_task")) {
                split = event.getMessageAsString().split(",");
                server = split[0];
                HashMap<String, NetworkPlayer> staffPlayers = new HashMap<String, NetworkPlayer>();
                for (String playerInfo : split[1].split(";")) {
                    player = new NetworkPlayer(null, playerInfo, server, null);
                    staffPlayers.put(playerInfo.toLowerCase(), player);
                }
                this.staffPlayers.put(server, staffPlayers);
                lastReply.put(server, System.currentTimeMillis());
            } else if (event.getChannel().equals("NetworkEvents.staff_query_task_extra")) {
                this.deserializeExtraPlayerData(event.getMessageAsString(), this.staffPlayers);
            }
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public Set<NetworkPlayer> getMatchingPlayersFromIP(String ip) {
        return this.getMatchingPlayersFromIP(ip, this.serverPlayers);
    }

    public Set<NetworkPlayer> getAllMatchingPlayersFromIP(String ip) {
        Set<NetworkPlayer> players = this.getMatchingPlayersFromIP(ip, this.serverPlayers);
        players.addAll(this.getMatchingPlayersFromIP(ip, this.staffPlayers));
        players.addAll(this.getMatchingPlayersFromIP(ip, this.ultraHiddenStaff));
        return players;
    }

    public NetworkPlayer getNetworkPlayerFromUsername(String name) {
        NetworkPlayer retr = this.getNetworkPlayerFromUsername(name, this.serverPlayers);
        if (retr != null) {
            return retr;
        }
        retr = this.getNetworkPlayerFromUsername(name, this.staffPlayers);
        if (retr != null) {
            return retr;
        }
        return this.getNetworkPlayerFromUsername(name, this.ultraHiddenStaff);
    }

    public NetworkPlayer getNetworkPlayerFromUsername(String name, Map<String, Map<String, NetworkPlayer>> maps) {
        for (Map.Entry<String, Map<String, NetworkPlayer>> entry : maps.entrySet()) {
            NetworkPlayer player = entry.getValue().get(name.toLowerCase());
            if (player == null) continue;
            return player;
        }
        return null;
    }

    public Set<NetworkPlayer> getMatchingPlayersFromIP(String ip, Map<String, Map<String, NetworkPlayer>> maps) {
        HashSet<NetworkPlayer> retr = new HashSet<NetworkPlayer>();
        boolean wildcardLookup = ip.contains("*");
        maps.values().forEach(playerMap -> playerMap.forEach((name, networkPlayer) -> {
            if (networkPlayer.getIp() != null && (wildcardLookup && networkPlayer.getIp().startsWith(ip.replace("*", "")) || networkPlayer.getIp().equals(ip))) {
                retr.add((NetworkPlayer)networkPlayer);
            }
        }));
        return retr;
    }

    private void deserializeExtraPlayerData(String message, Map<String, Map<String, NetworkPlayer>> map) {
        String[] split = message.split(",");
        String server = split[0];
        Map<String, NetworkPlayer> networkPlayers = map.get(server);
        for (String playerInfo : split[1].split(";")) {
            String[] data = playerInfo.split("@");
            String pName = data[0];
            NetworkPlayer player = networkPlayers.get(pName.toLowerCase());
            if (player == null) continue;
            player.setIp(data[1]);
        }
    }

    public Collection<NetworkPlayer> getStaffMembers(String server) {
        return this.staffPlayers.get(server).values();
    }

    public String lookupPlayer(String player) {
        for (Map.Entry<String, Map<String, NetworkPlayer>> server : this.serverPlayers.entrySet()) {
            if (!server.getValue().containsKey(player.toLowerCase())) continue;
            return server.getKey();
        }
        for (Map.Entry<String, Map<String, NetworkPlayer>> server : this.staffPlayers.entrySet()) {
            if (!server.getValue().containsKey(player.toLowerCase())) continue;
            return server.getKey();
        }
        return null;
    }

    public Set<NetworkPlayer> getAllStaffMembers() {
        HashSet<NetworkPlayer> players = new HashSet<NetworkPlayer>();
        for (Map<String, NetworkPlayer> staffMembers : this.staffPlayers.values()) {
            players.addAll(staffMembers.values());
        }
        return players;
    }

    public Set<String> getServers() {
        return this.serverPlayers.keySet();
    }

    public void setPlayerHidden(Player player, boolean state) {
        if (state) {
            this.isHidden.add(player.getUniqueId());
        } else {
            this.isHidden.remove(player.getUniqueId());
        }
    }

    public boolean isPlayerHidden(Player player) {
        return this.isHidden.contains(player.getUniqueId());
    }

    public boolean toggleHidden(Player player) {
        if (this.isPlayerHidden(player)) {
            this.setPlayerHidden(player, false);
            return false;
        }
        this.setPlayerHidden(player, true);
        return true;
    }

    public Map<String, Map<String, NetworkPlayer>> getUltraHiddenStaff() {
        return this.ultraHiddenStaff;
    }
}

