package gg.sense.anticheat.packet.client;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class CVoicePacket
extends ClientPacket {
    @SerializedName(value="channel")
    private String channel;
    @SerializedName(value="audio")
    private byte[] audio;

    public String a() {
        return this.channel;
    }

    public byte[] b() {
        return this.audio;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof CVoicePacket)) {
            return false;
        }
        CVoicePacket cVoicePacket = (CVoicePacket)object;
        if (!cVoicePacket.a(this)) {
            return false;
        }
        String string = this.a();
        String string2 = cVoicePacket.a();
        if (string == null ? string2 != null : !string.equals(string2)) {
            return false;
        }
        return Arrays.equals(this.b(), cVoicePacket.b());
    }

    @Override
    protected boolean a(Object object) {
        return object instanceof CVoicePacket;
    }

    @Override
    public int hashCode() {
        int n = 1;
        String string = this.a();
        n = n * 59 + (string == null ? 43 : string.hashCode());
        n = n * 59 + Arrays.hashCode(this.b());
        return n;
    }

    @Override
    public String toString() {
        return "CVoicePacket(channel=" + this.a() + ", audio=" + Arrays.toString(this.b()) + ")";
    }

    public CVoicePacket(String string, byte[] byArray) {
        this.channel = string;
        this.audio = byArray;
    }
}