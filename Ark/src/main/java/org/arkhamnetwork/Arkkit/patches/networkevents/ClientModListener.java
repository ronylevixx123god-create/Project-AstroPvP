/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.v1_7_R4.Packet
 *  net.minecraft.server.v1_7_R4.PacketDataSerializer
 *  net.minecraft.server.v1_7_R4.PacketPlayOutCustomPayload
 *  net.minecraft.util.io.netty.buffer.ByteBuf
 *  net.minecraft.util.io.netty.buffer.Unpooled
 *  org.bukkit.Bukkit
 *  org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerJoinEvent
 *  org.bukkit.event.player.PlayerQuitEvent
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches.networkevents;

import net.minecraft.server.v1_7_R4.Packet;
import net.minecraft.server.v1_7_R4.PacketDataSerializer;
import net.minecraft.server.v1_7_R4.PacketPlayOutCustomPayload;
import net.minecraft.util.io.netty.buffer.ByteBuf;
import net.minecraft.util.io.netty.buffer.Unpooled;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.networkevents.ClientModPluginMessageListener;
import org.arkhamnetwork.Arkkit.patches.networkevents.NE_SQLManager;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

public class ClientModListener
implements Listener {
    public static final String CHANNEL_REGISTER = "5zig_REG";

    @EventHandler(priority=EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent e) {
        final Player p = e.getPlayer();
        Bukkit.getScheduler().runTaskAsynchronously((Plugin)Arkkit.get(), new Runnable(){

            @Override
            public void run() {
                ClientModListener.this.requestRegister(p);
            }
        });
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (!ClientModPluginMessageListener._5zigUsers.contains(p.getUniqueId().toString())) {
            NE_SQLManager.sql_query.add("INSERT INTO 5zig_users(player_uuid, player_name, protocol) VALUES('" + p.getUniqueId().toString() + "', '" + p.getName() + "', " + 0 + ") ON DUPLICATE KEY UPDATE protocol=" + 0);
        }
    }

    public void requestRegister(Player player) {
        PacketDataSerializer packetData = new PacketDataSerializer(Unpooled.buffer());
        packetData.writeInt(3);
        this.send(packetData, player, CHANNEL_REGISTER);
    }

    public void send(PacketDataSerializer packetData, Player player, String channel) {
        PacketPlayOutCustomPayload packet = new PacketPlayOutCustomPayload(channel, (ByteBuf)packetData);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket((Packet)packet);
    }
}

