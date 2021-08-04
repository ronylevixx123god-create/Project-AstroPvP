package gg.sense.anticheat.packet.client;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class CModsPacket
extends ClientPacket {
    @SerializedName(value="mods")
    private Map<String, Boolean> mods;

    public Map a() {
        return this.mods;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof CModsPacket)) {
            return false;
        }
        CModsPacket cModsPacket = (CModsPacket)object;
        if (!cModsPacket.a(this)) {
            return false;
        }
        Map map = this.a();
        Map map2 = cModsPacket.a();
        return !(map == null ? map2 != null : !((Object)map).equals(map2));
    }

    @Override
    protected boolean a(Object object) {
        return object instanceof CModsPacket;
    }

    @Override
    public int hashCode() {
        int n = 1;
        Map map = this.a();
        n = n * 59 + (map == null ? 43 : ((Object)map).hashCode());
        return n;
    }

    @Override
    public String toString() {
        return "CModsPacket(mods=" + this.a() + ")";
    }

    public CModsPacket(Map map) {
        this.mods = map;
    }
}