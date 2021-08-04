package gg.sense.anticheat.packet.client;

import com.google.gson.annotations.SerializedName;
import gg.sense.anticheat.object.Zone;

public class SCreateZonePacket
extends ClientPacket {
    @SerializedName(value="zone")
    private Zone zone;

    public Zone a() {
        return this.zone;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof SCreateZonePacket)) {
            return false;
        }
        SCreateZonePacket sCreateZonePacket = (SCreateZonePacket)object;
        if (!sCreateZonePacket.a(this)) {
            return false;
        }
        Zone aU2 = this.a();
        Zone aU3 = sCreateZonePacket.a();
        return !(aU2 == null ? aU3 != null : !((Object)aU2).equals(aU3));
    }

    @Override
    protected boolean a(Object object) {
        return object instanceof SCreateZonePacket;
    }

    @Override
    public int hashCode() {
        int n = 1;
        Zone aU2 = this.a();
        n = n * 59 + (aU2 == null ? 43 : ((Object)aU2).hashCode());
        return n;
    }

    @Override
    public String toString() {
        return "SCreateZonePacket(zone=" + this.a() + ")";
    }
}