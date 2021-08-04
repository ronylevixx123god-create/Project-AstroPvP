package gg.sense.anticheat.packet.client;

import com.google.gson.annotations.SerializedName;

public class SSetMapImagePacket
extends ClientPacket {
    @SerializedName(value="imageURL")
    private String imageURL;
    @SerializedName(value="width")
    private int width;
    @SerializedName(value="height")
    private int height;
    @SerializedName(value="topLeftX")
    private int topLeftX;
    @SerializedName(value="topLeftZ")
    private int topLeftZ;

    public String d() {
        return this.imageURL;
    }

    public int e() {
        return this.width;
    }

    public int b() {
        return this.height;
    }

    public int c() {
        return this.topLeftX;
    }

    public int a() {
        return this.topLeftZ;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof SSetMapImagePacket)) {
            return false;
        }
        SSetMapImagePacket sSetMapImagePacket = (SSetMapImagePacket)object;
        if (!sSetMapImagePacket.a(this)) {
            return false;
        }
        String string = this.d();
        String string2 = sSetMapImagePacket.d();
        if (string == null ? string2 != null : !string.equals(string2)) {
            return false;
        }
        if (this.e() != sSetMapImagePacket.e()) {
            return false;
        }
        if (this.b() != sSetMapImagePacket.b()) {
            return false;
        }
        if (this.c() != sSetMapImagePacket.c()) {
            return false;
        }
        return this.a() == sSetMapImagePacket.a();
    }

    @Override
    protected boolean a(Object object) {
        return object instanceof SSetMapImagePacket;
    }

    @Override
    public int hashCode() {
        int n = 1;
        String string = this.d();
        n = n * 59 + (string == null ? 43 : string.hashCode());
        n = n * 59 + this.e();
        n = n * 59 + this.b();
        n = n * 59 + this.c();
        n = n * 59 + this.a();
        return n;
    }

    @Override
    public String toString() {
        return "SSetMapImagePacket(imageURL=" + this.d() + ", width=" + this.e() + ", height=" + this.b() + ", topLeftX=" + this.c() + ", topLeftZ=" + this.a() + ")";
    }
}