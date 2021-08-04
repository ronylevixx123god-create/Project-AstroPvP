package gg.sense.anticheat.packet.client;

import com.google.gson.annotations.SerializedName;

public class SPurchaseFeedPacket
extends ClientPacket {
    @SerializedName(value="name")
    private String name;
    @SerializedName(value="text")
    private String text;
    @SerializedName(value="iconURL")
    private String iconURL;

    public String a() {
        return this.name;
    }

    public String c() {
        return this.text;
    }

    public String b() {
        return this.iconURL;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof SPurchaseFeedPacket)) {
            return false;
        }
        SPurchaseFeedPacket sPurchaseFeedPacket = (SPurchaseFeedPacket)object;
        if (!sPurchaseFeedPacket.a(this)) {
            return false;
        }
        String string = this.a();
        String string2 = sPurchaseFeedPacket.a();
        if (string == null ? string2 != null : !string.equals(string2)) {
            return false;
        }
        String string3 = this.c();
        String string4 = sPurchaseFeedPacket.c();
        if (string3 == null ? string4 != null : !string3.equals(string4)) {
            return false;
        }
        String string5 = this.b();
        String string6 = sPurchaseFeedPacket.b();
        return !(string5 == null ? string6 != null : !string5.equals(string6));
    }

    @Override
    protected boolean a(Object object) {
        return object instanceof SPurchaseFeedPacket;
    }

    @Override
    public int hashCode() {
        int n = 1;
        String string = this.a();
        n = n * 59 + (string == null ? 43 : string.hashCode());
        String string2 = this.c();
        n = n * 59 + (string2 == null ? 43 : string2.hashCode());
        String string3 = this.b();
        n = n * 59 + (string3 == null ? 43 : string3.hashCode());
        return n;
    }

    @Override
    public String toString() {
        return "SPurchaseFeedPacket(name=" + this.a() + ", text=" + this.c() + ", iconURL=" + this.b() + ")";
    }
}