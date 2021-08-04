package gg.sense.anticheat.packet.client;

import com.google.gson.annotations.SerializedName;

public class SPlayerOutlineColor
extends ClientPacket {
    @SerializedName(value="entityID")
    private int entityID;
    @SerializedName(value="color")
    private int color;

    public int a() {
        return this.entityID;
    }

    public int b() {
        return this.color;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof SPlayerOutlineColor)) {
            return false;
        }
        SPlayerOutlineColor sPlayerOutlineColor = (SPlayerOutlineColor)object;
        if (!sPlayerOutlineColor.a(this)) {
            return false;
        }
        if (this.a() != sPlayerOutlineColor.a()) {
            return false;
        }
        return this.b() == sPlayerOutlineColor.b();
    }

    @Override
    protected boolean a(Object object) {
        return object instanceof SPlayerOutlineColor;
    }

    @Override
    public int hashCode() {
        int n = 1;
        n = n * 59 + this.a();
        n = n * 59 + this.b();
        return n;
    }

    @Override
    public String toString() {
        return "SPlayerOutlineColor(entityID=" + this.a() + ", color=" + this.b() + ")";
    }
}