package gg.sense.anticheat.packet.client;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class SItemCooldownsListPacket
extends ClientPacket {
    @SerializedName(value="cooldowns")
    private Map<String, List<Double>> cooldowns;

    public Map a() {
        return this.cooldowns;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof SItemCooldownsListPacket)) {
            return false;
        }
        SItemCooldownsListPacket sItemCooldownsListPacket = (SItemCooldownsListPacket)object;
        if (!sItemCooldownsListPacket.a(this)) {
            return false;
        }
        Map map = this.a();
        Map map2 = sItemCooldownsListPacket.a();
        return !(map == null ? map2 != null : !((Object)map).equals(map2));
    }

    @Override
    protected boolean a(Object object) {
        return object instanceof SItemCooldownsListPacket;
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
        return "SItemCooldownsListPacket(cooldowns=" + this.a() + ")";
    }
}