package gg.sense.anticheat.packet.client;

import com.google.gson.annotations.SerializedName;

public class CPollResponsePacket
extends ClientPacket {
    @SerializedName(value="pollId")
    private int pollId;
    @SerializedName(value="responseIndex")
    private int responseIndex;

    public CPollResponsePacket(int n, int n2) {
        this.pollId = n;
        this.responseIndex = n2;
    }

    public int b() {
        return this.pollId;
    }

    public int a() {
        return this.responseIndex;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof CPollResponsePacket)) {
            return false;
        }
        CPollResponsePacket cPollResponsePacket = (CPollResponsePacket)object;
        if (!cPollResponsePacket.a(this)) {
            return false;
        }
        if (this.b() != cPollResponsePacket.b()) {
            return false;
        }
        return this.a() == cPollResponsePacket.a();
    }

    @Override
    protected boolean a(Object object) {
        return object instanceof CPollResponsePacket;
    }

    @Override
    public int hashCode() {
        int n = 1;
        n = n * 59 + this.b();
        n = n * 59 + this.a();
        return n;
    }

    @Override
    public String toString() {
        return "CPollResponsePacket(pollId=" + this.b() + ", responseIndex=" + this.a() + ")";
    }
}
 