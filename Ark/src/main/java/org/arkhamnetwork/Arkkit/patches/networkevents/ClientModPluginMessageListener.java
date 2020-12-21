/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.io.netty.buffer.ByteBuf
 *  net.minecraft.util.io.netty.buffer.Unpooled
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.messaging.PluginMessageListener
 */
package org.arkhamnetwork.Arkkit.patches.networkevents;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.util.io.netty.buffer.ByteBuf;
import net.minecraft.util.io.netty.buffer.Unpooled;
import org.arkhamnetwork.Arkkit.patches.networkevents.NE_SQLManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class ClientModPluginMessageListener
implements PluginMessageListener {
    public static final String CHANNEL = "5zig";
    public static List<String> _5zigUsers;

    public ClientModPluginMessageListener() {
        _5zigUsers = new ArrayList<String>();
    }

    public void onPluginMessageReceived(String channel, Player player, byte[] bytes) {
        if (channel.equals(CHANNEL)) {
            ByteBuf byteBuf = Unpooled.buffer().writeBytes(bytes).resetReaderIndex();
            this.handlePluginMessage(player, byteBuf);
        }
    }

    private void handlePluginMessage(Player player, ByteBuf message) {
        String uuid;
        if (message.readableBytes() == 1 && !_5zigUsers.contains(uuid = player.getUniqueId().toString())) {
            byte protocolId = message.readByte();
            Bukkit.getLogger().info("Player " + player.getName() + " connected using the 5zig Mod on protocol:" + protocolId);
            _5zigUsers.add(player.getUniqueId().toString());
            NE_SQLManager.sql_query.add("INSERT INTO 5zig_users(player_uuid, player_name, protocol) VALUES('" + uuid + "', '" + player.getName() + "', " + protocolId + ") ON DUPLICATE KEY UPDATE protocol=" + protocolId);
        }
    }
}

