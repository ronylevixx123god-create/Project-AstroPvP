package gg.sense.anticheat.packet.client;

import com.google.gson.annotations.SerializedName;

public class CPingAlliesPacket extends ClientPacket {
    @SerializedName(value="x")
    private double x;
    @SerializedName(value="y")
    private double y;
    @SerializedName(value="z")
    private double z;
    @SerializedName(value="dimension")
    private int dimension;
    @SerializedName(value="allies")
    private boolean allies;

    public double a() {
        return this.x;
    }

    public double d() {
        return this.y;
    }

    public double c() {
        return this.z;
    }

    public int b() {
        return this.dimension;
    }

    public boolean e() {
        return this.allies;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof CPingAlliesPacket)) {
            return false;
        }
        CPingAlliesPacket cPingAlliesPacket = (CPingAlliesPacket)object;
        if (!cPingAlliesPacket.a(this)) {
            return false;
        }
        if (Double.compare(this.a(), cPingAlliesPacket.a()) != 0) {
            return false;
        }
        if (Double.compare(this.d(), cPingAlliesPacket.d()) != 0) {
            return false;
        }
        if (Double.compare(this.c(), cPingAlliesPacket.c()) != 0) {
            return false;
        }
        if (this.b() != cPingAlliesPacket.b()) {
            return false;
        }
        return this.e() == cPingAlliesPacket.e();
    }

    @Override
    protected boolean a(Object object) {
        return object instanceof CPingAlliesPacket;
    }

    @Override
    public int hashCode() {
        int n = 1;
        long l = Double.doubleToLongBits(this.a());
        n = n * 59 + (int)(l >>> 32 ^ l);
        long l2 = Double.doubleToLongBits(this.d());
        n = n * 59 + (int)(l2 >>> 32 ^ l2);
        long l3 = Double.doubleToLongBits(this.c());
        n = n * 59 + (int)(l3 >>> 32 ^ l3);
        n = n * 59 + this.b();
        n = n * 59 + (this.e() ? 79 : 97);
        return n;
    }

    @Override
    public String toString() {
        return "CPingAlliesPacket(x=" + this.a() + ", y=" + this.d() + ", z=" + this.c() + ", dimension=" + this.b() + ", allies=" + this.e() + ")";
    }

    public CPingAlliesPacket(double d, double d2, double d3, int n, boolean bl) {
        this.x = d;
        this.y = d2;
        this.z = d3;
        this.dimension = n;
        this.allies = bl;
    }
}
 