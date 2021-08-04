package gg.sense.anticheat.packet.client;

import com.google.gson.annotations.SerializedName;
import org.bukkit.Material;

public class SBarAPIPacket
extends ClientPacket {
    @SerializedName(value="barState")
    private qf barState;
    @SerializedName(value="title")
    private String title;
    @SerializedName(value="item")
    private String item;
    @SerializedName(value="minValue")
    private long min;
    @SerializedName(value="maxValue")
    private long max;
    @SerializedName(value="value")
    private long value;

    public Material f() {
        return Material.getMaterial(this.item);
    }

    public String c() {
        return this.item;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof SBarAPIPacket)) {
            return false;
        }
        SBarAPIPacket sBarAPIPacket = (SBarAPIPacket)object;
        if (!sBarAPIPacket.a(this)) {
            return false;
        }
        qf qf2 = this.b();
        qf qf3 = sBarAPIPacket.b();
        if (qf2 == null ? qf3 != null : !((Object)((Object)qf2)).equals((Object)qf3)) {
            return false;
        }
        String string = this.a();
        String string2 = sBarAPIPacket.a();
        if (string == null ? string2 != null : !string.equals(string2)) {
            return false;
        }
        String string3 = this.c();
        String string4 = sBarAPIPacket.c();
        if (string3 == null ? string4 != null : !string3.equals(string4)) {
            return false;
        }
        if (this.d() != sBarAPIPacket.d()) {
            return false;
        }
        if (this.e() != sBarAPIPacket.e()) {
            return false;
        }
        if (this.g() != sBarAPIPacket.g()) {
            return false;
        }
        Material n92 = this.f();
        Material n93 = sBarAPIPacket.f();
        return !(n92 == null ? n93 != null : !n92.equals(n93));
    }

    @Override
    protected boolean a(Object object) {
        return object instanceof SBarAPIPacket;
    }

    @Override
    public int hashCode() {
        int n = 1;
        qf qf2 = this.b();
        n = n * 59 + (qf2 == null ? 43 : ((Object)((Object)qf2)).hashCode());
        String string = this.a();
        n = n * 59 + (string == null ? 43 : string.hashCode());
        String string2 = this.c();
        n = n * 59 + (string2 == null ? 43 : string2.hashCode());
        long l = this.d();
        n = n * 59 + (int)(l >>> 32 ^ l);
        long l2 = this.e();
        n = n * 59 + (int)(l2 >>> 32 ^ l2);
        long l3 = this.g();
        n = n * 59 + (int)(l3 >>> 32 ^ l3);
        Material n92 = this.f();
        n = n * 59 + (n92 == null ? 43 : n92.hashCode());
        return n;
    }

    @Override
    public String toString() {
        return "SBarAPIPacket(barState=" + (Object)((Object)this.b()) + ", title=" + this.a() + ", item=" + this.c() + ", min=" + this.d() + ", max=" + this.e() + ", value=" + this.g() + ", itemStack=" + this.f() + ")";
    }

    public qf b() {
        return this.barState;
    }

    public String a() {
        return this.title;
    }

    public long d() {
        return this.min;
    }

    public long e() {
        return this.max;
    }

    public long g() {
        return this.value;
    }

    public enum qf {
        ON,
        OFF;

    }
}