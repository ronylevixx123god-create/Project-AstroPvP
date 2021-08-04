package gg.sense.anticheat.packet.client;

import com.google.gson.annotations.SerializedName;
import gg.sense.anticheat.object.k5;

import java.util.List;

public class SWorldBorderResponsePacket
extends ClientPacket {
    @SerializedName(value="radius")
    private int radius;
    @SerializedName(value="radiusX")
    private int radiusX;
    @SerializedName(value="radiusZ")
    private int radiusZ;
    @SerializedName(value="centerX")
    private int centerX;
    @SerializedName(value="centerZ")
    private int centerZ;
    @SerializedName(value="pois_new")
    private List<k5> pois_new;

    public int a() {
        return this.radius;
    }

    public int f() {
        return this.radiusX;
    }

    public int b() {
        return this.radiusZ;
    }

    public int e() {
        return this.centerX;
    }

    public int d() {
        return this.centerZ;
    }

    public List c() {
        return this.pois_new;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof SWorldBorderResponsePacket)) {
            return false;
        }
        SWorldBorderResponsePacket sWorldBorderResponsePacket = (SWorldBorderResponsePacket)object;
        if (!sWorldBorderResponsePacket.a(this)) {
            return false;
        }
        if (this.a() != sWorldBorderResponsePacket.a()) {
            return false;
        }
        if (this.f() != sWorldBorderResponsePacket.f()) {
            return false;
        }
        if (this.b() != sWorldBorderResponsePacket.b()) {
            return false;
        }
        if (this.e() != sWorldBorderResponsePacket.e()) {
            return false;
        }
        if (this.d() != sWorldBorderResponsePacket.d()) {
            return false;
        }
        List list = this.c();
        List list2 = sWorldBorderResponsePacket.c();
        return !(list == null ? list2 != null : !((Object)list).equals(list2));
    }

    @Override
    protected boolean a(Object object) {
        return object instanceof SWorldBorderResponsePacket;
    }

    @Override
    public int hashCode() {
        int n = 1;
        n = n * 59 + this.a();
        n = n * 59 + this.f();
        n = n * 59 + this.b();
        n = n * 59 + this.e();
        n = n * 59 + this.d();
        List list = this.c();
        n = n * 59 + (list == null ? 43 : ((Object)list).hashCode());
        return n;
    }

    @Override
    public String toString() {
        return "SWorldBorderResponsePacket(radius=" + this.a() + ", radiusX=" + this.f() + ", radiusZ=" + this.b() + ", centerX=" + this.e() + ", centerZ=" + this.d() + ", pois_new=" + this.c() + ")";
    }
}