package gg.sense.anticheat.packet.client;

import com.google.gson.annotations.SerializedName;

public class CMobHelperStatusPacket
extends ClientPacket {
    @SerializedName(value="running")
    private boolean running;

    public boolean a() {
        return this.running;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof CMobHelperStatusPacket)) {
            return false;
        }
        CMobHelperStatusPacket cMobHelperStatusPacket = (CMobHelperStatusPacket)object;
        if (!cMobHelperStatusPacket.a(this)) {
            return false;
        }
        return this.a() == cMobHelperStatusPacket.a();
    }

    @Override
    protected boolean a(Object object) {
        return object instanceof CMobHelperStatusPacket;
    }

    @Override
    public int hashCode() {
        int n = 1;
        n = n * 59 + (this.a() ? 79 : 97);
        return n;
    }

    @Override
    public String toString() {
        return "CMobHelperStatusPacket(running=" + this.a() + ")";
    }

    public CMobHelperStatusPacket(boolean bl) {
        this.running = bl;
    }
}