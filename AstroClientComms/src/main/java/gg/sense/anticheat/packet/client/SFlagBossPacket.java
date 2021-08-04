package gg.sense.anticheat.packet.client;

import com.google.gson.annotations.SerializedName;

public class SFlagBossPacket
extends ClientPacket {
    @SerializedName(value="entityId")
    private int entityId;

    public int a() {
        return this.entityId;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof SFlagBossPacket)) {
            return false;
        }
        SFlagBossPacket sFlagBossPacket = (SFlagBossPacket)object;
        if (!sFlagBossPacket.a(this)) {
            return false;
        }
        return this.a() == sFlagBossPacket.a();
    }

    @Override
    protected boolean a(Object object) {
        return object instanceof SFlagBossPacket;
    }

    @Override
    public int hashCode() {
        int n = 1;
        n = n * 59 + this.a();
        return n;
    }

    @Override
    public String toString() {
        return "SFlagBossPacket(entityId=" + this.a() + ")";
    }
}