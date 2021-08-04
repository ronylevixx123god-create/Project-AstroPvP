package gg.sense.anticheat.packet.client;

import com.google.gson.annotations.SerializedName;

public class CPlayerFactionRequestPacket extends ClientPacket {
    @SerializedName(value="uuid")
    private String uuid;

    public String a() {
        return this.uuid;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof CPlayerFactionRequestPacket)) {
            return false;
        }
        CPlayerFactionRequestPacket cPlayerFactionRequestPacket = (CPlayerFactionRequestPacket)object;
        if (!cPlayerFactionRequestPacket.a(this)) {
            return false;
        }
        String string = this.a();
        String string2 = cPlayerFactionRequestPacket.a();
        return !(string == null ? string2 != null : !string.equals(string2));
    }

    @Override
    protected boolean a(Object object) {
        return object instanceof CPlayerFactionRequestPacket;
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
        return "CPlayerFactionRequestPacket(uuid=" + this.a() + ")";
    }
}