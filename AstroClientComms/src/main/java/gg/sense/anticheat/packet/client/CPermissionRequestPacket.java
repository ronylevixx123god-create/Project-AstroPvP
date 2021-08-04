package gg.sense.anticheat.packet.client;

import com.google.gson.annotations.SerializedName;

public class CPermissionRequestPacket
extends ClientPacket {
    @SerializedName(value="permission")
    private String permission;

    public String a() {
        return this.permission;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof CPermissionRequestPacket)) {
            return false;
        }
        CPermissionRequestPacket cPermissionRequestPacket = (CPermissionRequestPacket)object;
        if (!cPermissionRequestPacket.a(this)) {
            return false;
        }
        String string = this.a();
        String string2 = cPermissionRequestPacket.a();
        return !(string == null ? string2 != null : !string.equals(string2));
    }

    @Override
    protected boolean a(Object object) {
        return object instanceof CPermissionRequestPacket;
    }

    @Override
    public int hashCode() {
        int n = 1;
        String string = this.a();
        n = n * 59 + (string == null ? 43 : string.hashCode());
        return n;
    }

    @Override
    public String toString() {
        return "CPermissionRequestPacket(permission=" + this.a() + ")";
    }

    public CPermissionRequestPacket(String string) {
        this.permission = string;
    }
}