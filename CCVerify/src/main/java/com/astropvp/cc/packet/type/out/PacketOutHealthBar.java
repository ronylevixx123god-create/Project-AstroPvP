package com.astropvp.cc.packet.type.out;

import com.astropvp.cc.packet.type.Packet;
import com.google.gson.annotations.SerializedName;
import org.bukkit.entity.Player;

public class PacketOutHealthBar implements Packet {

    @SerializedName(value = "currentHealth")
    private long currentHealth;

    @SerializedName(value = "maxHealth")
    private long maxHealth;

    @SerializedName(value = "name")
    private String name;

    public PacketOutHealthBar(long currentHealth, long maxHealth, String name) {
        this.currentHealth = currentHealth;
        this.maxHealth = maxHealth;
        this.name = name;
    }

    @Override
    public short getID() {
        return 72;
    }
}
