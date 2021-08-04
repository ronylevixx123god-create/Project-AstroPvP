package gg.sense.anticheat.packet.client;

import com.google.gson.annotations.SerializedName;
import gg.sense.anticheat.object.cw;

public class SSetEntityProperties
extends ClientPacket {

    public class pH {
        @SerializedName(value="scale")
        private float scale = 1.0f;
        @SerializedName(value="boss")
        private boolean boss = false;
        @SerializedName(value="spooky")
        private boolean spooky = false;
        @SerializedName(value="showNametags")
        private boolean showNametags = true;
        @SerializedName(value="customNametag")
        private String customNametag;
        @SerializedName(value="golemType")
        private byte golemType = 0;
        @SerializedName(value="type")
        private byte type = 0;
        private transient boolean a;
        @SerializedName(value="key")
        private String key = "";

        public boolean a(cw cw2) {
            return this.key != null && this.key.equals(cw2.name());
        }

        public float e() {
            return this.scale;
        }

        public boolean c() {
            return this.boss;
        }

        public boolean h() {
            return this.spooky;
        }

        public boolean f() {
            return this.showNametags;
        }

        public String b() {
            return this.customNametag;
        }

        public byte a() {
            return this.golemType;
        }

        public byte i() {
            return this.type;
        }

        public boolean d() {
            return this.a;
        }

        public String g() {
            return this.key;
        }

        public void b(boolean bl) {
            this.boss = bl;
        }

        public void a(boolean bl) {
            this.a = bl;
        }

        public boolean equals(Object object) {
            if (object == this) {
                return true;
            }
            if (!(object instanceof pH)) {
                return false;
            }
            pH pH2 = (pH)object;
            if (!pH2.a(this)) {
                return false;
            }
            if (Float.compare(this.e(), pH2.e()) != 0) {
                return false;
            }
            if (this.c() != pH2.c()) {
                return false;
            }
            if (this.h() != pH2.h()) {
                return false;
            }
            if (this.f() != pH2.f()) {
                return false;
            }
            String string = this.b();
            String string2 = pH2.b();
            if (string == null ? string2 != null : !string.equals(string2)) {
                return false;
            }
            if (this.a() != pH2.a()) {
                return false;
            }
            if (this.i() != pH2.i()) {
                return false;
            }
            String string3 = this.g();
            String string4 = pH2.g();
            return !(string3 == null ? string4 != null : !string3.equals(string4));
        }

        protected boolean a(Object object) {
            return object instanceof pH;
        }

        public int hashCode() {
            int n = 1;
            n = n * 59 + Float.floatToIntBits(this.e());
            n = n * 59 + (this.c() ? 79 : 97);
            n = n * 59 + (this.h() ? 79 : 97);
            n = n * 59 + (this.f() ? 79 : 97);
            String string = this.b();
            n = n * 59 + (string == null ? 43 : string.hashCode());
            n = n * 59 + this.a();
            n = n * 59 + this.i();
            String string2 = this.g();
            n = n * 59 + (string2 == null ? 43 : string2.hashCode());
            return n;
        }

        public String toString() {
            return "SSetEntityProperties.EntityProperties(scale=" + this.e() + ", boss=" + this.c() + ", spooky=" + this.h() + ", showNametags=" + this.f() + ", customNametag=" + this.b() + ", golemType=" + this.a() + ", type=" + this.i() + ", variant=" + this.d() + ", key=" + this.g() + ")";
        }
    }

    @SerializedName(value="entityID")
    private int entityID;
    @SerializedName(value="properties")
    private pH entityProperties;

    public int d() {
        return this.entityID;
    }

    public pH a() {
        return this.entityProperties;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof SSetEntityProperties)) {
            return false;
        }
        SSetEntityProperties sSetEntityProperties = (SSetEntityProperties)object;
        if (!sSetEntityProperties.a(this)) {
            return false;
        }
        if (this.d() != sSetEntityProperties.d()) {
            return false;
        }
        pH pH2 = this.a();
        pH pH3 = sSetEntityProperties.a();
        return !(pH2 == null ? pH3 != null : !((Object)pH2).equals(pH3));
    }

    @Override
    protected boolean a(Object object) {
        return object instanceof SSetEntityProperties;
    }

    @Override
    public int hashCode() {
        int n = 1;
        n = n * 59 + this.d();
        pH pH2 = this.a();
        n = n * 59 + (pH2 == null ? 43 : ((Object)pH2).hashCode());
        return n;
    }

    @Override
    public String toString() {
        return "SSetEntityProperties(entityID=" + this.d() + ", entityProperties=" + this.a() + ")";
    }
}