package gg.sense.anticheat.packet.client;

import com.google.gson.annotations.SerializedName;

public class CResolutionPacket
extends ClientPacket {
    @SerializedName(value="x")
    private int x;
    @SerializedName(value="y")
    private int y;

    public int b() {
        return this.x;
    }

    public int a() {
        return this.y;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof CResolutionPacket)) {
            return false;
        }
        CResolutionPacket cResolutionPacket = (CResolutionPacket)object;
        if (!cResolutionPacket.a(this)) {
            return false;
        }
        if (this.b() != cResolutionPacket.b()) {
            return false;
        }
        return this.a() == cResolutionPacket.a();
    }

    @Override
    protected boolean a(Object object) {
        return object instanceof CResolutionPacket;
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
        return "CResolutionPacket(x=" + this.b() + ", y=" + this.a() + ")";
    }

    public CResolutionPacket(int n, int n2) {
        this.x = n;
        this.y = n2;
    }
}