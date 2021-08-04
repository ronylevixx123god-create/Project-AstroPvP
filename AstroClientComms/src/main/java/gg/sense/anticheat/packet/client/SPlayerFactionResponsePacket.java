package gg.sense.anticheat.packet.client;

import com.google.gson.annotations.SerializedName;

public class SPlayerFactionResponsePacket
extends ClientPacket {
    @SerializedName(value="uuid")
    private String uuid;
    @SerializedName(value="factionID")
    private String factionID;

    public String a() {
        return this.uuid;
    }

    public String b() {
        return this.factionID;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof SPlayerFactionResponsePacket)) {
            return false;
        }
        SPlayerFactionResponsePacket sPlayerFactionResponsePacket = (SPlayerFactionResponsePacket)object;
        if (!sPlayerFactionResponsePacket.a(this)) {
            return false;
        }
        String string = this.a();
        String string2 = sPlayerFactionResponsePacket.a();
        if (string == null ? string2 != null : !string.equals(string2)) {
            return false;
        }
        String string3 = this.b();
        String string4 = sPlayerFactionResponsePacket.b();
        return !(string3 == null ? string4 != null : !string3.equals(string4));
    }

    @Override
    protected boolean a(Object object) {
        return object instanceof SPlayerFactionResponsePacket;
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
        return "SPlayerFactionResponsePacket(uuid=" + this.a() + ", factionID=" + this.b() + ")";
    }
}
 