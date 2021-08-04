package gg.sense.anticheat.packet.client;

import com.google.gson.annotations.SerializedName;

public class SXPTextPacket
extends ClientPacket {
    @SerializedName(value="text")
    private String text;

    public String a() {
        return this.text;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof SXPTextPacket)) {
            return false;
        }
        SXPTextPacket sXPTextPacket = (SXPTextPacket)object;
        if (!sXPTextPacket.a(this)) {
            return false;
        }
        String string = this.a();
        String string2 = sXPTextPacket.a();
        return !(string == null ? string2 != null : !string.equals(string2));
    }

    @Override
    protected boolean a(Object object) {
        return object instanceof SXPTextPacket;
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
        return "SXPTextPacket(text=" + this.a() + ")";
    }
}