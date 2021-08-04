package gg.sense.anticheat.packet.client;

import com.google.gson.annotations.SerializedName;

public class CFactionInfoRequestPacket extends ClientPacket {
    @SerializedName(value="name")
    private String name;
    @SerializedName(value="formatted")
    private boolean formatted;

    public String a() {
        return this.name;
    }

    public boolean b() {
        return this.formatted;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof CFactionInfoRequestPacket)) {
            return false;
        }
        CFactionInfoRequestPacket cFactionInfoRequestPacket = (CFactionInfoRequestPacket)object;
        if (!cFactionInfoRequestPacket.a(this)) {
            return false;
        }
        String string = this.a();
        String string2 = cFactionInfoRequestPacket.a();
        if (string == null ? string2 != null : !string.equals(string2)) {
            return false;
        }
        return this.b() == cFactionInfoRequestPacket.b();
    }

    @Override
    protected boolean a(Object object) {
        return object instanceof CFactionInfoRequestPacket;
    }

    @Override
    public int hashCode() {
        int n = 1;
        String string = this.a();
        n = n * 59 + (string == null ? 43 : string.hashCode());
        n = n * 59 + (this.b() ? 79 : 97);
        return n;
    }

    @Override
    public String toString() {
        return "CFactionInfoRequestPacket(name=" + this.a() + ", formatted=" + this.b() + ")";
    }

    public CFactionInfoRequestPacket(String string, boolean bl) {
        this.name = string;
        this.formatted = bl;
    }
}