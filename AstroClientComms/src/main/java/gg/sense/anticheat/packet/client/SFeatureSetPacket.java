package gg.sense.anticheat.packet.client;

import com.google.gson.annotations.SerializedName;

public class SFeatureSetPacket
extends ClientPacket {
    @SerializedName(value="featureSet")
    private String featureSet;

    public String a() {
        return this.featureSet;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof SFeatureSetPacket)) {
            return false;
        }
        SFeatureSetPacket sFeatureSetPacket = (SFeatureSetPacket)object;
        if (!sFeatureSetPacket.a(this)) {
            return false;
        }
        String string = this.a();
        String string2 = sFeatureSetPacket.a();
        return !(string == null ? string2 != null : !string.equals(string2));
    }

    @Override
    protected boolean a(Object object) {
        return object instanceof SFeatureSetPacket;
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
        return "SFeatureSetPacket(featureSet=" + this.a() + ")";
    }
}