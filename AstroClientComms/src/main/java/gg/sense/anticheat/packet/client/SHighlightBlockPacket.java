package gg.sense.anticheat.packet.client;

import com.google.gson.annotations.SerializedName;

public class SHighlightBlockPacket
extends ClientPacket {
    @SerializedName(value="operation")
    private int operation;
    @SerializedName(value="color")
    private String color;
    @SerializedName(value="colorCode")
    private int colorCode;
    @SerializedName(value="x")
    private int x;
    @SerializedName(value="y")
    private int y;
    @SerializedName(value="z")
    private int z;
    @SerializedName(value="minimumIntensity")
    private double minimumIntensity;
    @SerializedName(value="maximumIntensity")
    private double maximumIntensity;

    public int f() {
        return this.operation;
    }

    public String e() {
        return this.color;
    }

    public int a() {
        return this.colorCode;
    }

    public int d() {
        return this.x;
    }

    public int c() {
        return this.y;
    }

    public int g() {
        return this.z;
    }

    public double h() {
        return this.minimumIntensity;
    }

    public double b() {
        return this.maximumIntensity;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof SHighlightBlockPacket)) {
            return false;
        }
        SHighlightBlockPacket sHighlightBlockPacket = (SHighlightBlockPacket)object;
        if (!sHighlightBlockPacket.a(this)) {
            return false;
        }
        if (this.f() != sHighlightBlockPacket.f()) {
            return false;
        }
        String string = this.e();
        String string2 = sHighlightBlockPacket.e();
        if (string == null ? string2 != null : !string.equals(string2)) {
            return false;
        }
        if (this.a() != sHighlightBlockPacket.a()) {
            return false;
        }
        if (this.d() != sHighlightBlockPacket.d()) {
            return false;
        }
        if (this.c() != sHighlightBlockPacket.c()) {
            return false;
        }
        if (this.g() != sHighlightBlockPacket.g()) {
            return false;
        }
        if (Double.compare(this.h(), sHighlightBlockPacket.h()) != 0) {
            return false;
        }
        return Double.compare(this.b(), sHighlightBlockPacket.b()) == 0;
    }

    @Override
    protected boolean a(Object object) {
        return object instanceof SHighlightBlockPacket;
    }

    @Override
    public int hashCode() {
        int n = 1;
        n = n * 59 + this.f();
        String string = this.e();
        n = n * 59 + (string == null ? 43 : string.hashCode());
        n = n * 59 + this.a();
        n = n * 59 + this.d();
        n = n * 59 + this.c();
        n = n * 59 + this.g();
        long l = Double.doubleToLongBits(this.h());
        n = n * 59 + (int)(l >>> 32 ^ l);
        long l2 = Double.doubleToLongBits(this.b());
        n = n * 59 + (int)(l2 >>> 32 ^ l2);
        return n;
    }

    @Override
    public String toString() {
        return "SHighlightBlockPacket(operation=" + this.f() + ", color=" + this.e() + ", colorCode=" + this.a() + ", x=" + this.d() + ", y=" + this.c() + ", z=" + this.g() + ", minimumIntensity=" + this.h() + ", maximumIntensity=" + this.b() + ")";
    }
}