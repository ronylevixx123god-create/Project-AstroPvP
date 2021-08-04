package gg.sense.anticheat.packet.client;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class SPlayerHeadersPacket
extends ClientPacket {
    @SerializedName(value="playerHeaders")
    private Map<String, List<String>> playerHeaders;
    @SerializedName(value="patch")
    private boolean patch;

    public Map a() {
        return this.playerHeaders;
    }

    public boolean b() {
        return this.patch;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof SPlayerHeadersPacket)) {
            return false;
        }
        SPlayerHeadersPacket sPlayerHeadersPacket = (SPlayerHeadersPacket)object;
        if (!sPlayerHeadersPacket.a(this)) {
            return false;
        }
        Map map = this.a();
        Map map2 = sPlayerHeadersPacket.a();
        if (map == null ? map2 != null : !((Object)map).equals(map2)) {
            return false;
        }
        return this.b() == sPlayerHeadersPacket.b();
    }

    @Override
    protected boolean a(Object object) {
        return object instanceof SPlayerHeadersPacket;
    }

    @Override
    public int hashCode() {
        int n = 1;
        Map map = this.a();
        n = n * 59 + (map == null ? 43 : ((Object)map).hashCode());
        n = n * 59 + (this.b() ? 79 : 97);
        return n;
    }

    @Override
    public String toString() {
        return "SPlayerHeadersPacket(playerHeaders=" + this.a() + ", patch=" + this.b() + ")";
    }
}
 