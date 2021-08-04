package gg.sense.anticheat.packet.client;

import com.google.gson.annotations.SerializedName;

public class SKeepAlive
extends ClientPacket {
    @SerializedName(value="sessionKey")
    private String sessionKey;

    public String a() {
        return this.sessionKey;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof SKeepAlive)) {
            return false;
        }
        SKeepAlive sKeepAlive = (SKeepAlive)object;
        if (!sKeepAlive.a(this)) {
            return false;
        }
        String string = this.a();
        String string2 = sKeepAlive.a();
        return !(string == null ? string2 != null : !string.equals(string2));
    }

    @Override
    protected boolean a(Object object) {
        return object instanceof SKeepAlive;
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
        return "SKeepAlive(sessionKey=" + this.a() + ")";
    }
}