package gg.sense.anticheat.packet.client;

import com.google.gson.annotations.SerializedName;

public class CClaimCoordPacket extends ClientPacket {
    @SerializedName(value="x")
    private int x;
    @SerializedName(value="z")
    private int z;

    public int b() {
        return this.x;
    }

    public int a() {
        return this.z;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof CClaimCoordPacket)) {
            return false;
        }
        CClaimCoordPacket cClaimCoordPacket = (CClaimCoordPacket)object;
        if (!cClaimCoordPacket.a(this)) {
            return false;
        }
        if (this.b() != cClaimCoordPacket.b()) {
            return false;
        }
        return this.a() == cClaimCoordPacket.a();
    }

    @Override
    protected boolean a(Object object) {
        return object instanceof CClaimCoordPacket;
    }

    @Override
    public int hashCode() {
        int n = 1;
        n = n * 59 + this.b();
        n = n * 59 + this.a();
        return n;
    }

    @Override
    public String toString() {
        return "CClaimCoordPacket(x=" + this.b() + ", z=" + this.a() + ")";
    }

    public CClaimCoordPacket(int n, int n2) {
        this.x = n;
        this.z = n2;
    }
}