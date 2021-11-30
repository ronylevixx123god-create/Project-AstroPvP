package com.astropvp.cc.player;

import com.astropvp.cc.packet.PacketIO;
import com.astropvp.cc.packet.type.Packet;
import com.astropvp.cc.packet.type.in.PacketIn;
import com.astropvp.cc.packet.type.out.PacketOutNotification;
import com.astropvp.cc.voice.EnumChannel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CosmicPlayer {

    @Getter @Setter
    private volatile EnumChannel currentChannel;

    @Getter
    private Player player;

    @Getter
    @Setter
    private volatile boolean onCosmicClient;

    @Getter
    @Setter
    private long lastErrorSpeaking;

    @Getter
    @Setter
    private volatile List<CosmicPlayer> currentlySpeakingTo = new ArrayList<>();

    @Getter
    @Setter
    private String focusedPlayer = "";

    @Getter
    @Setter
    private Player currentHealthBar = null;

    public void notify(String message, int displayTime){
        if(!this.isOnCosmicClient() || !getPlayer().isOnline())return;

        PacketIO.get().queuePacket(getPlayer(), new PacketOutNotification(message, displayTime));
    }

    public void sendPacket(Packet packet){
        if(!isOnCosmicClient() || !getPlayer().isOnline()){
            return;
        }

        if(packet instanceof PacketIn){
            throw new IllegalArgumentException("Can't send an inbound packet!");
        }

        PacketIO.get().queuePacket(getPlayer(), packet);
    }

    public CosmicPlayer(Player player){
        this.player = player;
    }

}
