package me.astropvp.hotfixplugin.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import me.astropvp.hotfixplugin.HotFixPlugin;

import java.util.Locale;

public class ProtocolListener {

    public ProtocolListener() {
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

        protocolManager.addPacketListener(
                new PacketAdapter(HotFixPlugin.getInstance(), ListenerPriority.NORMAL,
                        PacketType.Play.Client.CHAT) {

                    @Override
                    public void onPacketReceiving(PacketEvent event) {
                        PacketContainer packetContainer = event.getPacket();
                        String message = packetContainer.getStrings().read(0).toLowerCase(Locale.ROOT);

                        if (message.contains("${jndi:")) {
                            event.setCancelled(true);

                            // Avoid Log4J Logger
                            System.out.println("[Log4J Fixer] - " + event.getPlayer().getName()
                                    + " tried to exploit using " + message);
                        }
                    }
                });
    }
}
