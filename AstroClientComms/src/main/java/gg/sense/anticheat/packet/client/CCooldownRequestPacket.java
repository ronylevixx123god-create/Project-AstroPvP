package gg.sense.anticheat.packet.client;

import com.google.gson.annotations.SerializedName;

public class CCooldownRequestPacket extends ClientPacket {
    @SerializedName(value="name")
    private String name;

    public String a() {
        return this.name;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof CCooldownRequestPacket)) {
            return false;
        }
        CCooldownRequestPacket cCooldownRequestPacket = (CCooldownRequestPacket)object;
        if (!cCooldownRequestPacket.a(this)) {
            return false;
        }
        String string = this.a();
        String string2 = cCooldownRequestPacket.a();
        return !(string == null ? string2 != null : !string.equals(string2));
    }

    @Override
    protected boolean a(Object object) {
        return object instanceof CCooldownRequestPacket;
    }

    @Override
    public int hashCode() {
        int n = 1;
        String string = this.a();
        n = n * 59 + (string == null ? 43 : string.hashCode());
        return n;
    }

    @Override
    public String toString() {
        return "CCooldownRequestPacket(name=" + this.a() + ")";
    }
}