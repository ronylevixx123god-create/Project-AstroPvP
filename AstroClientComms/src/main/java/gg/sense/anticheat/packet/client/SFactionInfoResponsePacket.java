package gg.sense.anticheat.packet.client;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class SFactionInfoResponsePacket
extends ClientPacket {
    @SerializedName(value="uuid")
    private String uuid;
    @SerializedName(value="name")
    private String name;
    @SerializedName(value="powerString")
    private String powerString;
    @SerializedName(value="relationNames")
    private Map<String, List<String>> relationNames;
    @SerializedName(value="membersOnline")
    private List<String> membersOnline;
    @SerializedName(value="membersOffline")
    private List<String> membersOffline;
    @SerializedName(value="wealth")
    private double wealth;
    @SerializedName(value="spawnerWealth")
    private double spawnerWealth;
    @SerializedName(value="rank")
    private int rank;

    public String g() {
        return this.uuid;
    }

    public String d() {
        return this.name;
    }

    public String c() {
        return this.powerString;
    }

    public Map a() {
        return this.relationNames;
    }

    public List b() {
        return this.membersOnline;
    }

    public List e() {
        return this.membersOffline;
    }

    public double h() {
        return this.wealth;
    }

    public double i() {
        return this.spawnerWealth;
    }

    public int f() {
        return this.rank;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof SFactionInfoResponsePacket)) {
            return false;
        }
        SFactionInfoResponsePacket sFactionInfoResponsePacket = (SFactionInfoResponsePacket)object;
        if (!sFactionInfoResponsePacket.a(this)) {
            return false;
        }
        String string = this.g();
        String string2 = sFactionInfoResponsePacket.g();
        if (string == null ? string2 != null : !string.equals(string2)) {
            return false;
        }
        String string3 = this.d();
        String string4 = sFactionInfoResponsePacket.d();
        if (string3 == null ? string4 != null : !string3.equals(string4)) {
            return false;
        }
        String string5 = this.c();
        String string6 = sFactionInfoResponsePacket.c();
        if (string5 == null ? string6 != null : !string5.equals(string6)) {
            return false;
        }
        Map map = this.a();
        Map map2 = sFactionInfoResponsePacket.a();
        if (map == null ? map2 != null : !((Object)map).equals(map2)) {
            return false;
        }
        List list = this.b();
        List list2 = sFactionInfoResponsePacket.b();
        if (list == null ? list2 != null : !((Object)list).equals(list2)) {
            return false;
        }
        List list3 = this.e();
        List list4 = sFactionInfoResponsePacket.e();
        if (list3 == null ? list4 != null : !((Object)list3).equals(list4)) {
            return false;
        }
        if (Double.compare(this.h(), sFactionInfoResponsePacket.h()) != 0) {
            return false;
        }
        if (Double.compare(this.i(), sFactionInfoResponsePacket.i()) != 0) {
            return false;
        }
        return this.f() == sFactionInfoResponsePacket.f();
    }

    @Override
    protected boolean a(Object object) {
        return object instanceof SFactionInfoResponsePacket;
    }

    @Override
    public int hashCode() {
        int n = 1;
        String string = this.g();
        n = n * 59 + (string == null ? 43 : string.hashCode());
        String string2 = this.d();
        n = n * 59 + (string2 == null ? 43 : string2.hashCode());
        String string3 = this.c();
        n = n * 59 + (string3 == null ? 43 : string3.hashCode());
        Map map = this.a();
        n = n * 59 + (map == null ? 43 : ((Object)map).hashCode());
        List list = this.b();
        n = n * 59 + (list == null ? 43 : ((Object)list).hashCode());
        List list2 = this.e();
        n = n * 59 + (list2 == null ? 43 : ((Object)list2).hashCode());
        long l = Double.doubleToLongBits(this.h());
        n = n * 59 + (int)(l >>> 32 ^ l);
        long l2 = Double.doubleToLongBits(this.i());
        n = n * 59 + (int)(l2 >>> 32 ^ l2);
        n = n * 59 + this.f();
        return n;
    }

    @Override
    public String toString() {
        return "SFactionInfoResponsePacket(uuid=" + this.g() + ", name=" + this.d() + ", powerString=" + this.c() + ", relationNames=" + this.a() + ", membersOnline=" + this.b() + ", membersOffline=" + this.e() + ", wealth=" + this.h() + ", spawnerWealth=" + this.i() + ", rank=" + this.f() + ")";
    }
}