package gg.sense.anticheat.packet.client;

import com.google.gson.annotations.SerializedName;

public class CSetChannelPacket extends ClientPacket {
    @SerializedName(value="channel")
    private String channel;

    public String a() {
        return this.channel;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof CSetChannelPacket)) {
            return false;
        }
        CSetChannelPacket cSetChannelPacket = (CSetChannelPacket)object;
        if (!cSetChannelPacket.a(this)) {
            return false;
        }
        String string = this.a();
        String string2 = cSetChannelPacket.a();
        return !(string == null ? string2 != null : !string.equals(string2));
    }

    @Override
    protected boolean a(Object object) {
        return object instanceof CSetChannelPacket;
    }

    @Override
    public int hashCode() {
        int n = 1;
        String string = this.a();
        n = n * 59 + (string == null ? 43 : string.hashCode());
        return n;
    }

    @Override
    public String toString() {
        return "CSetChannelPacket(channel=" + this.a() + ")";
    }
}