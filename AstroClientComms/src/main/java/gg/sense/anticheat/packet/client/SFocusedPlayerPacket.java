package gg.sense.anticheat.packet.client;

import com.google.gson.annotations.SerializedName;

public class SFocusedPlayerPacket
extends ClientPacket {
    @SerializedName(value="target")
    private String target;

    public String a() {
        return this.target;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof SFocusedPlayerPacket)) {
            return false;
        }
        SFocusedPlayerPacket sFocusedPlayerPacket = (SFocusedPlayerPacket)object;
        if (!sFocusedPlayerPacket.a(this)) {
            return false;
        }
        String string = this.a();
        String string2 = sFocusedPlayerPacket.a();
        return !(string == null ? string2 != null : !string.equals(string2));
    }

    @Override
    protected boolean a(Object object) {
        return object instanceof SFocusedPlayerPacket;
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
        return "SFocusedPlayerPacket(target=" + this.a() + ")";
    }
}