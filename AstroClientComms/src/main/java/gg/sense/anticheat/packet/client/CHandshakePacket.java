package gg.sense.anticheat.packet.client;

import com.google.gson.annotations.SerializedName;

public class CHandshakePacket
extends ClientPacket {
    @SerializedName(value="handshake")
    private String handshake;
    @SerializedName(value="version")
    private String version;

    public String a() {
        return this.handshake;
    }

    public String b() {
        return this.version;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof CHandshakePacket)) {
            return false;
        }
        CHandshakePacket cHandshakePacket = (CHandshakePacket)object;
        if (!cHandshakePacket.a(this)) {
            return false;
        }
        String string = this.a();
        String string2 = cHandshakePacket.a();
        if (string == null ? string2 != null : !string.equals(string2)) {
            return false;
        }
        String string3 = this.b();
        String string4 = cHandshakePacket.b();
        return !(string3 == null ? string4 != null : !string3.equals(string4));
    }

    @Override
    protected boolean a(Object object) {
        return object instanceof CHandshakePacket;
    }

    @Override
    public int hashCode() {
        int n = 1;
        String string = this.a();
        n = n * 59 + (string == null ? 43 : string.hashCode());
        String string2 = this.b();
        n = n * 59 + (string2 == null ? 43 : string2.hashCode());
        return n;
    }

    @Override
    public String toString() {
        return "CHandshakePacket(handshake=" + this.a() + ", version=" + this.b() + ")";
    }

    public CHandshakePacket(String string, String string2) {
        this.handshake = string;
        this.version = string2;
    }
}