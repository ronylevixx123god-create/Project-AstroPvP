package gg.sense.anticheat.packet.client;

import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Since;

public class fps
extends ClientPacket {
    @SerializedName(value="fps")
    @Since(value=1.3)
    private double fps;
    @SerializedName(value="status")
    @Since(value=1.3)
    private int statusArray;

    public fps(double d, int n) {
        this.fps = d;
        this.statusArray = n;
    }
}
