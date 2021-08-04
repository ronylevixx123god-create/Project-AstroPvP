package gg.sense.anticheat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import gg.sense.anticheat.packet.client.ClientPacket;
import io.netty.buffer.ByteBuf;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRegisterChannelEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentLinkedDeque;

public class CosmicAPI extends JavaPlugin implements Listener {

    static String channelName = "cosmic";
    static Gson gson = new GsonBuilder().create();



    public void onEnable() {
        Logger.printBox(
                ChatColor.GREEN,
                "Enabled Cosmic API", String.format("Version %s", getDescription().getVersion()),
                "Developed By: Decencies x FreddieJLH"
        );
        // REGISTER cosmic
        Bukkit.getServer().getMessenger()
                .registerOutgoingPluginChannel(this, channelName);
        Bukkit.getServer().getMessenger()
                .registerIncomingPluginChannel(this, channelName, this::incomingCosmic);

        Bukkit.getPluginManager().registerEvents(this, this);
        this.getServer().getScheduler().runTaskTimer(this, this::processIncoming, 1L, 1L);

    }

    public void incomingCosmic(String channel, Player player, byte[] message) {
        //System.out.println(channel);
        //new String(message, StandardCharsets.UTF_8);

        queue.add(new QueuedMessage(message));
    }

    private void processIncoming() {
        while (!queue.isEmpty()) {
            QueuedMessage queuedMessage = queue.poll();
            if (queuedMessage == null) {
                return;
            }
            ClientPacket message = queuedMessage.getMessage();
            /*if (!bypassUsers.contains(queuedMessage.getUuid()) && !allowedVersions.contains(message.getVersion())) {
                if (alertedOfOld.contains(queuedMessage.getUuid())) {
                    return;
                }
                alertedOfOld.add(queuedMessage.getUuid());
                Player player = this.asyncPlayerProvider.getPlayer(queuedMessage.getUuid());
                if (player != null) {
                    handler.sendNotification(player, (Object)ChatColor.RED + "You are running an old version of the Cosmic Client! Restart your client to update!", 10000);
                }
                return;
            }*/
            try {

                ClientPacket clientPacket = gson.fromJson(new String(queuedMessage.getData(), StandardCharsets.UTF_8), ClientPacket.class);
                System.out.println(clientPacket);

                /*Bukkit.getConsoleSender().sendMessage("[ClientComms] " + message + " " + queuedMessage.getUuid());
                Bukkit.getConsoleSender().sendMessage("[ClientComms] " + message + " " + queuedMessage.getUuid());
                Bukkit.getConsoleSender().sendMessage("[ClientComms] " + message + " " + queuedMessage.getUuid());
                handler.handleMessage(queuedMessage.getUuid(), message);*/
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private ConcurrentLinkedDeque<QueuedMessage> queue = new ConcurrentLinkedDeque<>();


    @EventHandler
    private void onChannel(PlayerRegisterChannelEvent event) {
        System.out.println("registered" + event.getChannel());
    }

    @EventHandler
    public void onPre(PlayerJoinEvent event) {
    }

    public static CosmicAPI getInstance() {
        return CosmicAPI.getPlugin(CosmicAPI.class);
    }

    public void onDisable() {
    }

}
