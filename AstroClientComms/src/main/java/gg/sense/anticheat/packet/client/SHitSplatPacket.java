package gg.sense.anticheat.packet.client;

import com.google.gson.annotations.SerializedName;
import gg.sense.anticheat.object.dI;

public class SHitSplatPacket
extends ClientPacket {
    @SerializedName(value="hitsplat")
    private dI hitsplat;

    public dI a() {
        return this.hitsplat;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof SHitSplatPacket)) {
            return false;
        }
        SHitSplatPacket sHitSplatPacket = (SHitSplatPacket)object;
        if (!sHitSplatPacket.a(this)) {
            return false;
        }
        dI dI2 = this.a();
        dI dI3 = sHitSplatPacket.a();
        return !(dI2 == null ? dI3 != null : !((Object)dI2).equals(dI3));
    }

    @Override
    protected boolean a(Object object) {
        return object instanceof SHitSplatPacket;
    }

    @Override
    public int hashCode() {
        int n = 1;
        dI dI2 = this.a();
        n = n * 59 + (dI2 == null ? 43 : ((Object)dI2).hashCode());
        return n;
    }

    @Override
    public String toString() {
        return "SHitSplatPacket(hitsplat=" + this.a() + ")";
    }
}