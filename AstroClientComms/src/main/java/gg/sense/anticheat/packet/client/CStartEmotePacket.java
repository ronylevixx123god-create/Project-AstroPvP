package gg.sense.anticheat.packet.client;

import com.google.gson.annotations.SerializedName;

public class CStartEmotePacket extends ClientPacket {
    @SerializedName(value="emote")
    private String emote;

    public String a() {
        return this.emote;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof CStartEmotePacket)) {
            return false;
        }
        CStartEmotePacket cStartEmotePacket = (CStartEmotePacket)object;
        if (!cStartEmotePacket.a(this)) {
            return false;
        }
        String string = this.a();
        String string2 = cStartEmotePacket.a();
        return !(string == null ? string2 != null : !string.equals(string2));
    }

    @Override
    protected boolean a(Object object) {
        return object instanceof CStartEmotePacket;
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
        return "CStartEmotePacket(emote=" + this.a() + ")";
    }

    public CStartEmotePacket(String string) {
        this.emote = string;
    }
}
 