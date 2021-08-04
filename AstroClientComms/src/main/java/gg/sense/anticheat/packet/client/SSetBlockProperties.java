package gg.sense.anticheat.packet.client;

import com.google.gson.annotations.SerializedName;

public class SSetBlockProperties
extends ClientPacket {

    public class n2 {
        @SerializedName(value="heroic")
        private boolean heroic;

        public boolean a() {
            return this.heroic;
        }

        public boolean equals(Object object) {
            if (object == this) {
                return true;
            }
            if (!(object instanceof n2)) {
                return false;
            }
            n2 n22 = (n2)object;
            if (!n22.a(this)) {
                return false;
            }
            return this.a() == n22.a();
        }

        protected boolean a(Object object) {
            return object instanceof n2;
        }

        public int hashCode() {
            int n = 1;
            n = n * 59 + (this.a() ? 79 : 97);
            return n;
        }

        public String toString() {
            return "SSetBlockProperties.BlockProperties(heroic=" + this.a() + ")";
        }
    }

    @SerializedName(value="x")
    private int x;
    @SerializedName(value="y")
    private int y;
    @SerializedName(value="Z")
    private int z;
    @SerializedName(value="blockProperties")
    private n2 blockProperties;

    public int b() {
        return this.x;
    }

    public int d() {
        return this.y;
    }

    public int a() {
        return this.z;
    }

    public n2 c() {
        return this.blockProperties;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof SSetBlockProperties)) {
            return false;
        }
        SSetBlockProperties sSetBlockProperties = (SSetBlockProperties)object;
        if (!sSetBlockProperties.a(this)) {
            return false;
        }
        if (this.b() != sSetBlockProperties.b()) {
            return false;
        }
        if (this.d() != sSetBlockProperties.d()) {
            return false;
        }
        if (this.a() != sSetBlockProperties.a()) {
            return false;
        }
        n2 n22 = this.c();
        n2 n23 = sSetBlockProperties.c();
        return !(n22 == null ? n23 != null : !((Object)n22).equals(n23));
    }

    @Override
    protected boolean a(Object object) {
        return object instanceof SSetBlockProperties;
    }

    @Override
    public int hashCode() {
        int n = 1;
        n = n * 59 + this.b();
        n = n * 59 + this.d();
        n = n * 59 + this.a();
        n2 n22 = this.c();
        n = n * 59 + (n22 == null ? 43 : ((Object)n22).hashCode());
        return n;
    }

    @Override
    public String toString() {
        return "SSetBlockProperties(x=" + this.b() + ", y=" + this.d() + ", z=" + this.a() + ", blockProperties=" + this.c() + ")";
    }
}