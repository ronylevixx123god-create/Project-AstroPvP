package gg.sense.anticheat.packet.client;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SScoreboardPacket
extends ClientPacket {

    public class pX {
        @SerializedName(value="name")
        private String name;
        @SerializedName(value="score")
        private int score;
        @SerializedName(value="place")
        private int place;
        @SerializedName(value="color")
        private String color;

        public String d() {
            return this.name;
        }

        public int c() {
            return this.score;
        }

        public int b() {
            return this.place;
        }

        public String a() {
            return this.color;
        }

        public void a(String string) {
            this.name = string;
        }

        public boolean equals(Object object) {
            if (object == this) {
                return true;
            }
            if (!(object instanceof pX)) {
                return false;
            }
            pX pX2 = (pX)object;
            if (!pX2.a(this)) {
                return false;
            }
            String string = this.d();
            String string2 = pX2.d();
            if (string == null ? string2 != null : !string.equals(string2)) {
                return false;
            }
            if (this.c() != pX2.c()) {
                return false;
            }
            if (this.b() != pX2.b()) {
                return false;
            }
            String string3 = this.a();
            String string4 = pX2.a();
            return !(string3 == null ? string4 != null : !string3.equals(string4));
        }

        protected boolean a(Object object) {
            return object instanceof pX;
        }

        public int hashCode() {
            int n = 1;
            String string = this.d();
            n = n * 59 + (string == null ? 43 : string.hashCode());
            n = n * 59 + this.c();
            n = n * 59 + this.b();
            String string2 = this.a();
            n = n * 59 + (string2 == null ? 43 : string2.hashCode());
            return n;
        }

        public String toString() {
            return "SScoreboardPacket.Participant(name=" + this.d() + ", score=" + this.c() + ", place=" + this.b() + ", color=" + this.a() + ")";
        }

        public pX(String string, int n, int n2, String string2) {
            this.name = string;
            this.score = n;
            this.place = n2;
            this.color = string2;
        }
    }

    @SerializedName(value="enabled")
    private boolean enabled;
    @SerializedName(value="title")
    private String title;
    @SerializedName(value="subtitle")
    private String subtitle;
    @SerializedName(value="view")
    private byte view;
    @SerializedName(value="threshold")
    private int threshold;
    @SerializedName(value="remainingWinners")
    private int remainingWinners;
    @SerializedName(value="remainingMs")
    private long remainingMs;
    @SerializedName(value="item")
    private String item;
    @SerializedName(value="itemId")
    private int itemId;
    @SerializedName(value="itemMeta")
    private int itemMeta;
    @SerializedName(value="participants")
    private List<pX> participantList;

    public boolean e() {
        return this.enabled;
    }

    public String g() {
        return this.title;
    }

    public String i() {
        return this.subtitle;
    }

    public byte h() {
        return this.view;
    }

    public int c() {
        return this.threshold;
    }

    public int d() {
        return this.remainingWinners;
    }

    public long k() {
        return this.remainingMs;
    }

    public String f() {
        return this.item;
    }

    public int j() {
        return this.itemId;
    }

    public int b() {
        return this.itemMeta;
    }

    public List a() {
        return this.participantList;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof SScoreboardPacket)) {
            return false;
        }
        SScoreboardPacket sScoreboardPacket = (SScoreboardPacket)object;
        if (!sScoreboardPacket.a(this)) {
            return false;
        }
        if (this.e() != sScoreboardPacket.e()) {
            return false;
        }
        String string = this.g();
        String string2 = sScoreboardPacket.g();
        if (string == null ? string2 != null : !string.equals(string2)) {
            return false;
        }
        String string3 = this.i();
        String string4 = sScoreboardPacket.i();
        if (string3 == null ? string4 != null : !string3.equals(string4)) {
            return false;
        }
        if (this.h() != sScoreboardPacket.h()) {
            return false;
        }
        if (this.c() != sScoreboardPacket.c()) {
            return false;
        }
        if (this.d() != sScoreboardPacket.d()) {
            return false;
        }
        if (this.k() != sScoreboardPacket.k()) {
            return false;
        }
        String string5 = this.f();
        String string6 = sScoreboardPacket.f();
        if (string5 == null ? string6 != null : !string5.equals(string6)) {
            return false;
        }
        if (this.j() != sScoreboardPacket.j()) {
            return false;
        }
        if (this.b() != sScoreboardPacket.b()) {
            return false;
        }
        List list = this.a();
        List list2 = sScoreboardPacket.a();
        return !(list == null ? list2 != null : !((Object)list).equals(list2));
    }

    @Override
    protected boolean a(Object object) {
        return object instanceof SScoreboardPacket;
    }

    @Override
    public int hashCode() {
        int n = 1;
        n = n * 59 + (this.e() ? 79 : 97);
        String string = this.g();
        n = n * 59 + (string == null ? 43 : string.hashCode());
        String string2 = this.i();
        n = n * 59 + (string2 == null ? 43 : string2.hashCode());
        n = n * 59 + this.h();
        n = n * 59 + this.c();
        n = n * 59 + this.d();
        long l = this.k();
        n = n * 59 + (int)(l >>> 32 ^ l);
        String string3 = this.f();
        n = n * 59 + (string3 == null ? 43 : string3.hashCode());
        n = n * 59 + this.j();
        n = n * 59 + this.b();
        List list = this.a();
        n = n * 59 + (list == null ? 43 : ((Object)list).hashCode());
        return n;
    }

    @Override
    public String toString() {
        return "SScoreboardPacket(enabled=" + this.e() + ", title=" + this.g() + ", subtitle=" + this.i() + ", view=" + this.h() + ", threshold=" + this.c() + ", remainingWinners=" + this.d() + ", remainingMs=" + this.k() + ", item=" + this.f() + ", itemId=" + this.j() + ", itemMeta=" + this.b() + ", participantList=" + this.a() + ")";
    }
}