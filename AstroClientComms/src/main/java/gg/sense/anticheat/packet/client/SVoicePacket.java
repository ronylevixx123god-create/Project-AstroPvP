package gg.sense.anticheat.packet.client;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class SVoicePacket
extends ClientPacket {
    @SerializedName(value="label")
    private String label;
    @SerializedName(value="channel")
    private String channel;
    @SerializedName(value="audio")
    private byte[] audio;

    public String b() {
        return this.label;
    }

    public String c() {
        return this.channel;
    }

    public byte[] a() {
        return this.audio;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof SVoicePacket)) {
            return false;
        }
        SVoicePacket sVoicePacket = (SVoicePacket)object;
        if (!sVoicePacket.a(this)) {
            return false;
        }
        String string = this.b();
        String string2 = sVoicePacket.b();
        if (string == null ? string2 != null : !string.equals(string2)) {
            return false;
        }
        String string3 = this.c();
        String string4 = sVoicePacket.c();
        if (string3 == null ? string4 != null : !string3.equals(string4)) {
            return false;
        }
        return Arrays.equals(this.a(), sVoicePacket.a());
    }

    @Override
    protected boolean a(Object object) {
        return object instanceof SVoicePacket;
    }

    @Override
    public int hashCode() {
        int n = 1;
        String string = this.b();
        n = n * 59 + (string == null ? 43 : string.hashCode());
        String string2 = this.c();
        n = n * 59 + (string2 == null ? 43 : string2.hashCode());
        n = n * 59 + Arrays.hashCode(this.a());
        return n;
    }

    @Override
    public String toString() {
        return "SVoicePacket(label=" + this.b() + ", channel=" + this.c() + ", audio=" + Arrays.toString(this.a()) + ")";
    }
}