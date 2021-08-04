package gg.sense.anticheat.packet.client;

import com.google.gson.annotations.SerializedName;

public class SClearTooltipPacket
extends ClientPacket {
    @SerializedName(value="title")
    private String title;

    public String a() {
        return this.title;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof SClearTooltipPacket)) {
            return false;
        }
        SClearTooltipPacket sClearTooltipPacket = (SClearTooltipPacket)object;
        if (!sClearTooltipPacket.a(this)) {
            return false;
        }
        String string = this.a();
        String string2 = sClearTooltipPacket.a();
        return !(string == null ? string2 != null : !string.equals(string2));
    }

    @Override
    protected boolean a(Object object) {
        return object instanceof SClearTooltipPacket;
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
        return "SClearTooltipPacket(title=" + this.a() + ")";
    }
}