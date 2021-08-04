package gg.sense.anticheat.packet.client;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SPollSendPacket
extends ClientPacket {
    @SerializedName(value="pollId")
    private int pollId;
    @SerializedName(value="question")
    private String question;
    @SerializedName(value="responses")
    private List<String> responses;
    @SerializedName(value="force")
    private boolean force;

    public int c() {
        return this.pollId;
    }

    public String d() {
        return this.question;
    }

    public List b() {
        return this.responses;
    }

    public boolean a() {
        return this.force;
    }

    public void a(boolean bl) {
        this.force = bl;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof SPollSendPacket)) {
            return false;
        }
        SPollSendPacket sPollSendPacket = (SPollSendPacket)object;
        if (!sPollSendPacket.a(this)) {
            return false;
        }
        if (this.c() != sPollSendPacket.c()) {
            return false;
        }
        String string = this.d();
        String string2 = sPollSendPacket.d();
        if (string == null ? string2 != null : !string.equals(string2)) {
            return false;
        }
        List list = this.b();
        List list2 = sPollSendPacket.b();
        if (list == null ? list2 != null : !((Object)list).equals(list2)) {
            return false;
        }
        return this.a() == sPollSendPacket.a();
    }

    @Override
    protected boolean a(Object object) {
        return object instanceof SPollSendPacket;
    }

    @Override
    public int hashCode() {
        int n = 1;
        n = n * 59 + this.c();
        String string = this.d();
        n = n * 59 + (string == null ? 43 : string.hashCode());
        List list = this.b();
        n = n * 59 + (list == null ? 43 : ((Object)list).hashCode());
        n = n * 59 + (this.a() ? 79 : 97);
        return n;
    }

    @Override
    public String toString() {
        return "SPollSendPacket(pollId=" + this.c() + ", question=" + this.d() + ", responses=" + this.b() + ", force=" + this.a() + ")";
    }
}