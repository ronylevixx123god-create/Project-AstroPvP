package gg.sense.anticheat.packet.client;

import com.google.gson.annotations.SerializedName;

public class STooltipPacket
extends ClientPacket {
    @SerializedName(value="title")
    private String title;
    @SerializedName(value="text")
    private String text;
    @SerializedName(value="item")
    private String item;
    @SerializedName(value="itemID")
    private int itemId;
    @SerializedName(value="itemDamage")
    private int itemDamage;

    public String b() {
        return this.title;
    }

    public String a() {
        return this.text;
    }

    public String e() {
        return this.item;
    }

    public int d() {
        return this.itemId;
    }

    public int c() {
        return this.itemDamage;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof STooltipPacket)) {
            return false;
        }
        STooltipPacket sTooltipPacket = (STooltipPacket)object;
        if (!sTooltipPacket.a(this)) {
            return false;
        }
        String string = this.b();
        String string2 = sTooltipPacket.b();
        if (string == null ? string2 != null : !string.equals(string2)) {
            return false;
        }
        String string3 = this.a();
        String string4 = sTooltipPacket.a();
        if (string3 == null ? string4 != null : !string3.equals(string4)) {
            return false;
        }
        String string5 = this.e();
        String string6 = sTooltipPacket.e();
        if (string5 == null ? string6 != null : !string5.equals(string6)) {
            return false;
        }
        if (this.d() != sTooltipPacket.d()) {
            return false;
        }
        return this.c() == sTooltipPacket.c();
    }

    @Override
    protected boolean a(Object object) {
        return object instanceof STooltipPacket;
    }

    @Override
    public int hashCode() {
        int n = 1;
        String string = this.b();
        n = n * 59 + (string == null ? 43 : string.hashCode());
        String string2 = this.a();
        n = n * 59 + (string2 == null ? 43 : string2.hashCode());
        String string3 = this.e();
        n = n * 59 + (string3 == null ? 43 : string3.hashCode());
        n = n * 59 + this.d();
        n = n * 59 + this.c();
        return n;
    }

    @Override
    public String toString() {
        return "STooltipPacket(title=" + this.b() + ", text=" + this.a() + ", item=" + this.e() + ", itemId=" + this.d() + ", itemDamage=" + this.c() + ")";
    }
}