package gg.sense.anticheat.packet.client;

import com.google.gson.annotations.SerializedName;

public class SRoyalePacket
extends ClientPacket {
    @SerializedName(value="radius")
    private int radius;
    @SerializedName(value="centerX")
    private int centerX;
    @SerializedName(value="centerZ")
    private int centerZ;
    @SerializedName(value="dRadius")
    private double dRadius;
    @SerializedName(value="dCenterX")
    private double dCenterX;
    @SerializedName(value="dCenterZ")
    private double dCenterZ;

    public int f() {
        return this.radius;
    }

    public int c() {
        return this.centerX;
    }

    public int d() {
        return this.centerZ;
    }

    public double b() {
        return this.dRadius;
    }

    public double a() {
        return this.dCenterX;
    }

    public double e() {
        return this.dCenterZ;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof SRoyalePacket)) {
            return false;
        }
        SRoyalePacket sRoyalePacket = (SRoyalePacket)object;
        if (!sRoyalePacket.a(this)) {
            return false;
        }
        if (this.f() != sRoyalePacket.f()) {
            return false;
        }
        if (this.c() != sRoyalePacket.c()) {
            return false;
        }
        if (this.d() != sRoyalePacket.d()) {
            return false;
        }
        if (Double.compare(this.b(), sRoyalePacket.b()) != 0) {
            return false;
        }
        if (Double.compare(this.a(), sRoyalePacket.a()) != 0) {
            return false;
        }
        return Double.compare(this.e(), sRoyalePacket.e()) == 0;
    }

    @Override
    protected boolean a(Object object) {
        return object instanceof SRoyalePacket;
    }

    @Override
    public int hashCode() {
        int n = 1;
        n = n * 59 + this.f();
        n = n * 59 + this.c();
        n = n * 59 + this.d();
        long l = Double.doubleToLongBits(this.b());
        n = n * 59 + (int)(l >>> 32 ^ l);
        long l2 = Double.doubleToLongBits(this.a());
        n = n * 59 + (int)(l2 >>> 32 ^ l2);
        long l3 = Double.doubleToLongBits(this.e());
        n = n * 59 + (int)(l3 >>> 32 ^ l3);
        return n;
    }

    @Override
    public String toString() {
        return "SRoyalePacket(radius=" + this.f() + ", centerX=" + this.c() + ", centerZ=" + this.d() + ", dRadius=" + this.b() + ", dCenterX=" + this.a() + ", dCenterZ=" + this.e() + ")";
    }
}