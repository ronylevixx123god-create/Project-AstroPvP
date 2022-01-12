package com.astropvp.cc.packet.type.out;

import com.astropvp.cc.packet.type.Packet;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class PacketOutItemCooldowns implements Packet {
    @Override
    public short getID() {
        return 32;
    }

    @SerializedName("cooldowns")
    private Map<String, List<Double>> cooldowns;

    public PacketOutItemCooldowns(Map<String, List<Double>> cooldowns){
        this.cooldowns = cooldowns;
    }
}
