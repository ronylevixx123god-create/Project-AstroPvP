package gg.sense.anticheat.packet.client;

import com.google.gson.annotations.SerializedName;

public class SNotificationPacket
extends ClientPacket {
    @SerializedName(value="text")
    private String text;
    @SerializedName(value="timeToDisplay")
    private int timeToDisplay;

    public String b() {
        return this.text;
    }

    public int a() {
        return this.timeToDisplay;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof SNotificationPacket)) {
            return false;
        }
        SNotificationPacket sNotificationPacket = (SNotificationPacket)object;
        if (!sNotificationPacket.a(this)) {
            return false;
        }
        String string = this.b();
        String string2 = sNotificationPacket.b();
        if (string == null ? string2 != null : !string.equals(string2)) {
            return false;
        }
        return this.a() == sNotificationPacket.a();
    }

    @Override
    protected boolean a(Object object) {
        return object instanceof SNotificationPacket;
    }

    @Override
    public int hashCode() {
        int n = 1;
        String string = this.b();
        n = n * 59 + (string == null ? 43 : string.hashCode());
        n = n * 59 + this.a();
        return n;
    }

    @Override
    public String toString() {
        return "SNotificationPacket(text=" + this.b() + ", timeToDisplay=" + this.a() + ")";
    }
}