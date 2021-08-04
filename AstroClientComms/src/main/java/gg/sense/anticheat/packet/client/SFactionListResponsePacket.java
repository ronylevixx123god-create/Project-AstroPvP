package gg.sense.anticheat.packet.client;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SFactionListResponsePacket
extends ClientPacket {

    public class c7 {
        @SerializedName(value="uuid")
        private String uuid;
        @SerializedName(value="leader")
        private String leader;
        @SerializedName(value="leaderName")
        private String leaderName;
        @SerializedName(value="name")
        private String name;
        @SerializedName(value="size")
        private int size;
        @SerializedName(value="land")
        private int land;
        @SerializedName(value="power")
        private int power;
        @SerializedName(value="powerRounded")
        private int powerRounded;
        @SerializedName(value="balance")
        private double balance;
        @SerializedName(value="wealth")
        private double wealth;

        public String h() {
            return this.uuid;
        }

        public String j() {
            return this.leader;
        }

        public String b() {
            return this.leaderName;
        }

        public String i() {
            return this.name;
        }

        public int d() {
            return this.size;
        }

        public int a() {
            return this.land;
        }

        public int g() {
            return this.power;
        }

        public int c() {
            return this.powerRounded;
        }

        public double e() {
            return this.balance;
        }

        public double f() {
            return this.wealth;
        }

        public boolean equals(Object object) {
            if (object == this) {
                return true;
            }
            if (!(object instanceof c7)) {
                return false;
            }
            c7 c72 = (c7)object;
            if (!c72.a(this)) {
                return false;
            }
            String string = this.h();
            String string2 = c72.h();
            if (string == null ? string2 != null : !string.equals(string2)) {
                return false;
            }
            String string3 = this.j();
            String string4 = c72.j();
            if (string3 == null ? string4 != null : !string3.equals(string4)) {
                return false;
            }
            String string5 = this.b();
            String string6 = c72.b();
            if (string5 == null ? string6 != null : !string5.equals(string6)) {
                return false;
            }
            String string7 = this.i();
            String string8 = c72.i();
            if (string7 == null ? string8 != null : !string7.equals(string8)) {
                return false;
            }
            if (this.d() != c72.d()) {
                return false;
            }
            if (this.a() != c72.a()) {
                return false;
            }
            if (this.g() != c72.g()) {
                return false;
            }
            if (this.c() != c72.c()) {
                return false;
            }
            if (Double.compare(this.e(), c72.e()) != 0) {
                return false;
            }
            return Double.compare(this.f(), c72.f()) == 0;
        }

        protected boolean a(Object object) {
            return object instanceof c7;
        }

        public int hashCode() {
            int n = 1;
            String string = this.h();
            n = n * 59 + (string == null ? 43 : string.hashCode());
            String string2 = this.j();
            n = n * 59 + (string2 == null ? 43 : string2.hashCode());
            String string3 = this.b();
            n = n * 59 + (string3 == null ? 43 : string3.hashCode());
            String string4 = this.i();
            n = n * 59 + (string4 == null ? 43 : string4.hashCode());
            n = n * 59 + this.d();
            n = n * 59 + this.a();
            n = n * 59 + this.g();
            n = n * 59 + this.c();
            long l = Double.doubleToLongBits(this.e());
            n = n * 59 + (int)(l >>> 32 ^ l);
            long l2 = Double.doubleToLongBits(this.f());
            n = n * 59 + (int)(l2 >>> 32 ^ l2);
            return n;
        }

        public String toString() {
            return "SFactionListResponsePacket.FactionInfo(uuid=" + this.h() + ", leader=" + this.j() + ", leaderName=" + this.b() + ", name=" + this.i() + ", size=" + this.d() + ", land=" + this.a() + ", power=" + this.g() + ", powerRounded=" + this.c() + ", balance=" + this.e() + ", wealth=" + this.f() + ")";
        }
    }

    @SerializedName(value="factions")
    private List<c7> factions;

    public List a() {
        return this.factions;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof SFactionListResponsePacket)) {
            return false;
        }
        SFactionListResponsePacket sFactionListResponsePacket = (SFactionListResponsePacket)object;
        if (!sFactionListResponsePacket.a(this)) {
            return false;
        }
        List list = this.a();
        List list2 = sFactionListResponsePacket.a();
        return !(list == null ? list2 != null : !((Object)list).equals(list2));
    }

    @Override
    protected boolean a(Object object) {
        return object instanceof SFactionListResponsePacket;
    }

    @Override
    public int hashCode() {
        int n = 1;
        List list = this.a();
        n = n * 59 + (list == null ? 43 : ((Object)list).hashCode());
        return n;
    }

    @Override
    public String toString() {
        return "SFactionListResponsePacket(factions=" + this.a() + ")";
    }
}