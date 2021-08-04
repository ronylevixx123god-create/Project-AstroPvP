package gg.sense.anticheat.packet.client;

import com.google.gson.annotations.SerializedName;

public class SPingAlliesPacket
extends ClientPacket {
    @SerializedName(value="name")
    private String name;
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

    public String a() {
        return this.name;
    }

    public double b() {
        return this.x;
    }

    public double e() {
        return this.y;
    }

    public double f() {
        return this.z;
    }

    public int c() {
        return this.dimension;
    }

    public boolean d() {
        return this.allies;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof SPingAlliesPacket)) {
            return false;
        }
        SPingAlliesPacket sPingAlliesPacket = (SPingAlliesPacket)object;
        if (!sPingAlliesPacket.a(this)) {
            return false;
        }
        String string = this.a();
        String string2 = sPingAlliesPacket.a();
        if (string == null ? string2 != null : !string.equals(string2)) {
            return false;
        }
        if (Double.compare(this.b(), sPingAlliesPacket.b()) != 0) {
            return false;
        }
        if (Double.compare(this.e(), sPingAlliesPacket.e()) != 0) {
            return false;
        }
        if (Double.compare(this.f(), sPingAlliesPacket.f()) != 0) {
            return false;
        }
        if (this.c() != sPingAlliesPacket.c()) {
            return false;
        }
        return this.d() == sPingAlliesPacket.d();
    }

    @Override
    protected boolean a(Object object) {
        return object instanceof SPingAlliesPacket;
    }

    @Override
    public int hashCode() {
        int n = 1;
        String string = this.a();
        n = n * 59 + (string == null ? 43 : string.hashCode());
        long l = Double.doubleToLongBits(this.b());
        n = n * 59 + (int)(l >>> 32 ^ l);
        long l2 = Double.doubleToLongBits(this.e());
        n = n * 59 + (int)(l2 >>> 32 ^ l2);
        long l3 = Double.doubleToLongBits(this.f());
        n = n * 59 + (int)(l3 >>> 32 ^ l3);
        n = n * 59 + this.c();
        n = n * 59 + (this.d() ? 79 : 97);
        return n;
    }

    @Override
    public String toString() {
        return "SPingAlliesPacket(name=" + this.a() + ", x=" + this.b() + ", y=" + this.e() + ", z=" + this.f() + ", dimension=" + this.c() + ", allies=" + this.d() + ")";
    }
}