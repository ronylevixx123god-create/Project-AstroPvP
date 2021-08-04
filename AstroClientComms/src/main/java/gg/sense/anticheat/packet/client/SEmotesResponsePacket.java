package gg.sense.anticheat.packet.client;

import com.google.gson.annotations.SerializedName;

import java.util.Set;

public class SEmotesResponsePacket
extends ClientPacket {
    @SerializedName(value="allowedEmotes")
    private Set<String> allowedEmotes;

    public Set a() {
        return this.allowedEmotes;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof SEmotesResponsePacket)) {
            return false;
        }
        SEmotesResponsePacket sEmotesResponsePacket = (SEmotesResponsePacket)object;
        if (!sEmotesResponsePacket.a(this)) {
            return false;
        }
        Set set = this.a();
        Set set2 = sEmotesResponsePacket.a();
        return !(set == null ? set2 != null : !((Object)set).equals(set2));
    }

    @Override
    protected boolean a(Object object) {
        return object instanceof SEmotesResponsePacket;
    }

    @Override
    public int hashCode() {
        int n = 1;
        Set set = this.a();
        n = n * 59 + (set == null ? 43 : ((Object)set).hashCode());
        return n;
    }

    @Override
    public String toString() {
        return "SEmotesResponsePacket(allowedEmotes=" + this.a() + ")";
    }
}