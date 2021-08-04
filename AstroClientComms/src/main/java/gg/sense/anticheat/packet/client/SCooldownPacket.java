package gg.sense.anticheat.packet.client;

import com.google.gson.annotations.SerializedName;

public class SCooldownPacket
extends ClientPacket {
    @SerializedName(value="key")
    private String key;
    @SerializedName(value="category")
    private String category;
    @SerializedName(value="infinite")
    private boolean infinite;
    @SerializedName(value="color")
    private int color;
    @SerializedName(value="name")
    private String name;
    @SerializedName(value="duration")
    private int duration;
    @SerializedName(value="max")
    private int max;
    @SerializedName(value="item")
    private String item;
    @SerializedName(value="itemID")
    private int itemID;

    public String i() {
        return this.key;
    }

    public String e() {
        return this.category;
    }

    public boolean g() {
        return this.infinite;
    }

    public int d() {
        return this.color;
    }

    public String b() {
        return this.name;
    }

    public int a() {
        return this.duration;
    }

    public int h() {
        return this.max;
    }

    public String c() {
        return this.item;
    }

    public int f() {
        return this.itemID;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof SCooldownPacket)) {
            return false;
        }
        SCooldownPacket sCooldownPacket = (SCooldownPacket)object;
        if (!sCooldownPacket.a(this)) {
            return false;
        }
        String string = this.i();
        String string2 = sCooldownPacket.i();
        if (string == null ? string2 != null : !string.equals(string2)) {
            return false;
        }
        String string3 = this.e();
        String string4 = sCooldownPacket.e();
        if (string3 == null ? string4 != null : !string3.equals(string4)) {
            return false;
        }
        if (this.g() != sCooldownPacket.g()) {
            return false;
        }
        if (this.d() != sCooldownPacket.d()) {
            return false;
        }
        String string5 = this.b();
        String string6 = sCooldownPacket.b();
        if (string5 == null ? string6 != null : !string5.equals(string6)) {
            return false;
        }
        if (this.a() != sCooldownPacket.a()) {
            return false;
        }
        if (this.h() != sCooldownPacket.h()) {
            return false;
        }
        String string7 = this.c();
        String string8 = sCooldownPacket.c();
        if (string7 == null ? string8 != null : !string7.equals(string8)) {
            return false;
        }
        return this.f() == sCooldownPacket.f();
    }

    @Override
    protected boolean a(Object object) {
        return object instanceof SCooldownPacket;
    }

    @Override
    public int hashCode() {
        int n = 1;
        String string = this.i();
        n = n * 59 + (string == null ? 43 : string.hashCode());
        String string2 = this.e();
        n = n * 59 + (string2 == null ? 43 : string2.hashCode());
        n = n * 59 + (this.g() ? 79 : 97);
        n = n * 59 + this.d();
        String string3 = this.b();
        n = n * 59 + (string3 == null ? 43 : string3.hashCode());
        n = n * 59 + this.a();
        n = n * 59 + this.h();
        String string4 = this.c();
        n = n * 59 + (string4 == null ? 43 : string4.hashCode());
        n = n * 59 + this.f();
        return n;
    }

    @Override
    public String toString() {
        return "SCooldownPacket(key=" + this.i() + ", category=" + this.e() + ", infinite=" + this.g() + ", color=" + this.d() + ", name=" + this.b() + ", duration=" + this.a() + ", max=" + this.h() + ", item=" + this.c() + ", itemID=" + this.f() + ")";
    }
}