package gg.sense.anticheat.packet.client;

import com.google.gson.annotations.SerializedName;
import gg.sense.anticheat.object.PopUp;

public class SPopUpPacket
extends ClientPacket {
    @SerializedName(value="popUp")
    private PopUp popUp;

    public PopUp a() {
        return this.popUp;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof SPopUpPacket)) {
            return false;
        }
        SPopUpPacket sPopUpPacket = (SPopUpPacket)object;
        if (!sPopUpPacket.a(this)) {
            return false;
        }
        PopUp mB2 = this.a();
        PopUp mB3 = sPopUpPacket.a();
        return !(mB2 == null ? mB3 != null : !((Object)mB2).equals(mB3));
    }

    @Override
    protected boolean a(Object object) {
        return object instanceof SPopUpPacket;
    }

    @Override
    public int hashCode() {
        int n = 1;
        PopUp mB2 = this.a();
        n = n * 59 + (mB2 == null ? 43 : ((Object)mB2).hashCode());
        return n;
    }

    @Override
    public String toString() {
        return "SPopUpPacket(popUp=" + this.a() + ")";
    }
}