package gg.sense.anticheat.packet.client;

import com.google.gson.annotations.SerializedName;
import gg.sense.anticheat.object.k5;

import java.util.List;

public class SSetWorldNamePacket
extends ClientPacket {
    @SerializedName(value="worldName")
    private String worldName;
    @SerializedName(value="pois_new")
    private List<k5> pois_new;
    @SerializedName(value="skybox")
    private byte skybox;
    @SerializedName(value="precipitationType")
    private byte precipitationType;

    public String d() {
        return this.worldName;
    }

    public List c() {
        return this.pois_new;
    }

    public byte b() {
        return this.skybox;
    }

    public byte a() {
        return this.precipitationType;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof SSetWorldNamePacket)) {
            return false;
        }
        SSetWorldNamePacket sSetWorldNamePacket = (SSetWorldNamePacket)object;
        if (!sSetWorldNamePacket.a(this)) {
            return false;
        }
        String string = this.d();
        String string2 = sSetWorldNamePacket.d();
        if (string == null ? string2 != null : !string.equals(string2)) {
            return false;
        }
        List list = this.c();
        List list2 = sSetWorldNamePacket.c();
        if (list == null ? list2 != null : !((Object)list).equals(list2)) {
            return false;
        }
        if (this.b() != sSetWorldNamePacket.b()) {
            return false;
        }
        return this.a() == sSetWorldNamePacket.a();
    }

    @Override
    protected boolean a(Object object) {
        return object instanceof SSetWorldNamePacket;
    }

    @Override
    public int hashCode() {
        int n = 1;
        String string = this.d();
        n = n * 59 + (string == null ? 43 : string.hashCode());
        List list = this.c();
        n = n * 59 + (list == null ? 43 : ((Object)list).hashCode());
        n = n * 59 + this.b();
        n = n * 59 + this.a();
        return n;
    }

    @Override
    public String toString() {
        return "SSetWorldNamePacket(worldName=" + this.d() + ", pois_new=" + this.c() + ", skybox=" + this.b() + ", precipitationType=" + this.a() + ")";
    }
}