package gg.sense.anticheat.object;

import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Since;

import java.io.Serializable;

public class dI
implements Serializable {
    @SerializedName(value="entityId")
    private int entityId;
    @SerializedName(value="text")
    private String text;
    @SerializedName(value="type")
    @Since(value=1.1)
    private String type;
    @SerializedName(value="x")
    @Since(value=1.2)
    private double x;
    @SerializedName(value="y")
    @Since(value=1.2)
    private double y;
    @SerializedName(value="z")
    @Since(value=1.2)
    private double z;
    private long a;

    public int f() {
        return this.entityId;
    }

    public String c() {
        return this.text;
    }

    public String e() {
        return this.type;
    }

    public double b() {
        return this.x;
    }

    public double d() {
        return this.y;
    }

    public double g() {
        return this.z;
    }

    public long a() {
        return this.a;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof dI)) {
            return false;
        }
        dI dI2 = (dI)object;
        if (!dI2.a(this)) {
            return false;
        }
        if (this.f() != dI2.f()) {
            return false;
        }
        String string = this.c();
        String string2 = dI2.c();
        if (string == null ? string2 != null : !string.equals(string2)) {
            return false;
        }
        String string3 = this.e();
        String string4 = dI2.e();
        if (string3 == null ? string4 != null : !string3.equals(string4)) {
            return false;
        }
        if (Double.compare(this.b(), dI2.b()) != 0) {
            return false;
        }
        if (Double.compare(this.d(), dI2.d()) != 0) {
            return false;
        }
        if (Double.compare(this.g(), dI2.g()) != 0) {
            return false;
        }
        return this.a() == dI2.a();
    }

    protected boolean a(Object object) {
        return object instanceof dI;
    }

    public int hashCode() {
        int n = 1;
        n = n * 59 + this.f();
        String string = this.c();
        n = n * 59 + (string == null ? 43 : string.hashCode());
        String string2 = this.e();
        n = n * 59 + (string2 == null ? 43 : string2.hashCode());
        long l = Double.doubleToLongBits(this.b());
        n = n * 59 + (int)(l >>> 32 ^ l);
        long l2 = Double.doubleToLongBits(this.d());
        n = n * 59 + (int)(l2 >>> 32 ^ l2);
        long l3 = Double.doubleToLongBits(this.g());
        n = n * 59 + (int)(l3 >>> 32 ^ l3);
        long l4 = this.a();
        n = n * 59 + (int)(l4 >>> 32 ^ l4);
        return n;
    }

    public String toString() {
        return "HitSplat(entityId=" + this.f() + ", text=" + this.c() + ", type=" + this.e() + ", x=" + this.b() + ", y=" + this.d() + ", z=" + this.g() + ", time=" + this.a() + ")";
    }
}