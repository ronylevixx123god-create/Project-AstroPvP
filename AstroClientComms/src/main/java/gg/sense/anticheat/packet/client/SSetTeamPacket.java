package gg.sense.anticheat.packet.client;

import com.google.gson.annotations.SerializedName;

import java.util.Set;

public class SSetTeamPacket
extends ClientPacket {
    @SerializedName(value="members")
    private Set<String> members;
    @SerializedName(value="voiceEnabled")
    private Set<String> voiceEnabled;

    public Set b() {
        return this.members;
    }

    public Set a() {
        return this.voiceEnabled;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof SSetTeamPacket)) {
            return false;
        }
        SSetTeamPacket sSetTeamPacket = (SSetTeamPacket)object;
        if (!sSetTeamPacket.a(this)) {
            return false;
        }
        Set set = this.b();
        Set set2 = sSetTeamPacket.b();
        if (set == null ? set2 != null : !((Object)set).equals(set2)) {
            return false;
        }
        Set set3 = this.a();
        Set set4 = sSetTeamPacket.a();
        return !(set3 == null ? set4 != null : !((Object)set3).equals(set4));
    }

    @Override
    protected boolean a(Object object) {
        return object instanceof SSetTeamPacket;
    }

    @Override
    public int hashCode() {
        int n = 1;
        Set set = this.b();
        n = n * 59 + (set == null ? 43 : ((Object)set).hashCode());
        Set set2 = this.a();
        n = n * 59 + (set2 == null ? 43 : ((Object)set2).hashCode());
        return n;
    }

    @Override
    public String toString() {
        return "SSetTeamPacket(members=" + this.b() + ", voiceEnabled=" + this.a() + ")";
    }
}