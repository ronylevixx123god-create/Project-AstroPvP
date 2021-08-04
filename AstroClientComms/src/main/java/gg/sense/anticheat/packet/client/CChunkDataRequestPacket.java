package gg.sense.anticheat.packet.client;

import com.google.gson.annotations.SerializedName;

public class CChunkDataRequestPacket extends ClientPacket {
    @SerializedName(value = "x")
    private int x;
    @SerializedName(value = "z")
    private int z;
    @SerializedName(value = "bulk")
    private boolean bulk;

    public int c() {
        return this.x;
    }

    public int b() {
        return this.z;
    }

    public boolean a() {
        return this.bulk;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof CChunkDataRequestPacket)) {
            return false;
        }
        CChunkDataRequestPacket cChunkDataRequestPacket = (CChunkDataRequestPacket) object;
        if (!cChunkDataRequestPacket.a(this)) {
            return false;
        }
        if (this.c() != cChunkDataRequestPacket.c()) {
            return false;
        }
        if (this.b() != cChunkDataRequestPacket.b()) {
            return false;
        }
        return this.a() == cChunkDataRequestPacket.a();
    }

    @Override
    protected boolean a(Object object) {
        return object instanceof CChunkDataRequestPacket;
    }

    @Override
    public int hashCode() {
        int n = 1;
        n = n * 59 + this.c();
        n = n * 59 + this.b();
        n = n * 59 + (this.a() ? 79 : 97);
        return n;
    }

    @Override
    public String toString() {
        return "CChunkDataRequestPacket(x=" + this.c() + ", z=" + this.b() + ", bulk=" + this.a() + ")";
    }

    public CChunkDataRequestPacket(int n, int n2, boolean bl) {
        this.x = n;
        this.z = n2;
        this.bulk = bl;
    }
}