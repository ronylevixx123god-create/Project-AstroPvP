package gg.sense.anticheat.packet.client;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class colorMap
extends ClientPacket {
    @SerializedName(value="colorMap")
    private Map<Integer, Integer> colorMap;

    public Map a() {
        return this.colorMap;
    }
}
 