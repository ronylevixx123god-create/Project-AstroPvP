package gg.sense.anticheat.packet.client;

import com.google.gson.annotations.SerializedName;

public class SSetWaypointPacket
extends ClientPacket {
    @SerializedName(value="id")
    private String id;
    @SerializedName(value="name")
    private String name;
    @SerializedName(value="color")
    private int color;
    @SerializedName(value="x")
    private double x;
    @SerializedName(value="y")
    private double y;
    @SerializedName(value="z")
    private double z;

    public String b() {
        return this.id;
    }

    public String e() {
        return this.name;
    }

    public int f() {
        return this.color;
    }

    public double c() {
        return this.x;
    }

    public double d() {
        return this.y;
    }

    public double a() {
        return this.z;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof SSetWaypointPacket)) {
            return false;
        }
        SSetWaypointPacket sSetWaypointPacket = (SSetWaypointPacket)object;
        if (!sSetWaypointPacket.a(this)) {
            return false;
        }
        String string = this.b();
        String string2 = sSetWaypointPacket.b();
        if (string == null ? string2 != null : !string.equals(string2)) {
            return false;
        }
        String string3 = this.e();
        String string4 = sSetWaypointPacket.e();
        if (string3 == null ? string4 != null : !string3.equals(string4)) {
            return false;
        }
        if (this.f() != sSetWaypointPacket.f()) {
            return false;
        }
        if (Double.compare(this.c(), sSetWaypointPacket.c()) != 0) {
            return false;
        }
        if (Double.compare(this.d(), sSetWaypointPacket.d()) != 0) {
            return false;
        }
        return Double.compare(this.a(), sSetWaypointPacket.a()) == 0;
    }

    @Override
    protected boolean a(Object object) {
        return object instanceof SSetWaypointPacket;
    }

    @Override
    public int hashCode() {
        int n = 1;
        String string = this.b();
        n = n * 59 + (string == null ? 43 : string.hashCode());
        String string2 = this.e();
        n = n * 59 + (string2 == null ? 43 : string2.hashCode());
        n = n * 59 + this.f();
        long l = Double.doubleToLongBits(this.c());
        n = n * 59 + (int)(l >>> 32 ^ l);
        long l2 = Double.doubleToLongBits(this.d());
        n = n * 59 + (int)(l2 >>> 32 ^ l2);
        long l3 = Double.doubleToLongBits(this.a());
        n = n * 59 + (int)(l3 >>> 32 ^ l3);
        return n;
    }

    @Override
    public String toString() {
        return "SSetWaypointPacket(id=" + this.b() + ", name=" + this.e() + ", color=" + this.f() + ", x=" + this.c() + ", y=" + this.d() + ", z=" + this.a() + ")";
    }
}