package gg.sense.anticheat.packet.client;

import com.google.gson.annotations.SerializedName;

import java.util.Set;

public class SHideNametags
extends ClientPacket {
    @SerializedName(value="hiddenplayers")
    private Set<Integer> hiddenPlayers;

    public Set a() {
        return this.hiddenPlayers;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof SHideNametags)) {
            return false;
        }
        SHideNametags sHideNametags = (SHideNametags)object;
        if (!sHideNametags.a(this)) {
            return false;
        }
        Set set = this.a();
        Set set2 = sHideNametags.a();
        return !(set == null ? set2 != null : !((Object)set).equals(set2));
    }

    @Override
    protected boolean a(Object object) {
        return object instanceof SHideNametags;
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
        return "SHideNametags(hiddenPlayers=" + this.a() + ")";
    }
}