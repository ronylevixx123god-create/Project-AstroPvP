package gg.sense.anticheat.packet.client;

import com.google.gson.annotations.SerializedName;

import java.util.Set;
import java.util.UUID;

public class SDefaultSkinnedPlayersPacket
extends ClientPacket {
    @SerializedName(value="uuids")
    private Set<UUID> uuids;

    public Set a() {
        return this.uuids;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof SDefaultSkinnedPlayersPacket)) {
            return false;
        }
        SDefaultSkinnedPlayersPacket sDefaultSkinnedPlayersPacket = (SDefaultSkinnedPlayersPacket)object;
        if (!sDefaultSkinnedPlayersPacket.a(this)) {
            return false;
        }
        Set set = this.a();
        Set set2 = sDefaultSkinnedPlayersPacket.a();
        return !(set == null ? set2 != null : !((Object)set).equals(set2));
    }

    @Override
    protected boolean a(Object object) {
        return object instanceof SDefaultSkinnedPlayersPacket;
    }

    @Override
    public int hashCode() {
        int n = 1;
        Set set = this.a();
        n = n * 59 + (set == null ? 43 : ((Object)set).hashCode());
        return n;
    }

    @Override
    public String toString() {
        return "SDefaultSkinnedPlayersPacket(uuids=" + this.a() + ")";
    }
}