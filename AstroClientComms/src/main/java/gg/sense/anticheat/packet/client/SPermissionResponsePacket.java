package gg.sense.anticheat.packet.client;

import com.google.gson.annotations.SerializedName;

public class SPermissionResponsePacket
extends ClientPacket {
    @SerializedName(value="permission")
    private String permission;
    @SerializedName(value="value")
    private boolean value;

    public String a() {
        return this.permission;
    }

    public boolean b() {
        return this.value;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof SPermissionResponsePacket)) {
            return false;
        }
        SPermissionResponsePacket sPermissionResponsePacket = (SPermissionResponsePacket)object;
        if (!sPermissionResponsePacket.a(this)) {
            return false;
        }
        String string = this.a();
        String string2 = sPermissionResponsePacket.a();
        if (string == null ? string2 != null : !string.equals(string2)) {
            return false;
        }
        return this.b() == sPermissionResponsePacket.b();
    }

    @Override
    protected boolean a(Object object) {
        return object instanceof SPermissionResponsePacket;
    }

    @Override
    public int hashCode() {
        int n = 1;
        String string = this.a();
        n = n * 59 + (string == null ? 43 : string.hashCode());
        n = n * 59 + (this.b() ? 79 : 97);
        return n;
    }

    @Override
    public String toString() {
        return "SPermissionResponsePacket(permission=" + this.a() + ", value=" + this.b() + ")";
    }
}
 