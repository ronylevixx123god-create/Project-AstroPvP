package gg.sense.anticheat.packet.client;

import com.google.gson.annotations.SerializedName;

public class CInfoPacket extends ClientPacket {
    @SerializedName(value="system")
    private String system;

    public String a() {
        return this.system;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof CInfoPacket)) {
            return false;
        }
        CInfoPacket cInfoPacket = (CInfoPacket)object;
        if (!cInfoPacket.a(this)) {
            return false;
        }
        String string = this.a();
        String string2 = cInfoPacket.a();
        return !(string == null ? string2 != null : !string.equals(string2));
    }

    @Override
    protected boolean a(Object object) {
        return object instanceof CInfoPacket;
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
        return "CInfoPacket(system=" + this.a() + ")";
    }
}