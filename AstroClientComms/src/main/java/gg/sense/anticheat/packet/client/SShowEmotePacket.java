package gg.sense.anticheat.packet.client;

import com.google.gson.annotations.SerializedName;

public class SShowEmotePacket
extends ClientPacket {
    @SerializedName(value="entityID")
    private int entityID;
    @SerializedName(value="emote")
    private String emote;

    public int a() {
        return this.entityID;
    }

    public String b() {
        return this.emote;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof SShowEmotePacket)) {
            return false;
        }
        SShowEmotePacket sShowEmotePacket = (SShowEmotePacket)object;
        if (!sShowEmotePacket.a(this)) {
            return false;
        }
        if (this.a() != sShowEmotePacket.a()) {
            return false;
        }
        String string = this.b();
        String string2 = sShowEmotePacket.b();
        return !(string == null ? string2 != null : !string.equals(string2));
    }

    @Override
    protected boolean a(Object object) {
        return object instanceof SShowEmotePacket;
    }

    @Override
    public int hashCode() {
        int n = 1;
        n = n * 59 + this.a();
        String string = this.b();
        n = n * 59 + (string == null ? 43 : string.hashCode());
        return n;
    }

    @Override
    public String toString() {
        return "SShowEmotePacket(entityID=" + this.a() + ", emote=" + this.b() + ")";
    }
}