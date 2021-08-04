package gg.sense.anticheat.packet.client;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SEnchantListPacket
extends ClientPacket {

    public class EnchantData
            implements Serializable {
        @SerializedName(value="name")
        private String name;
        @SerializedName(value="description")
        private String description;
        @SerializedName(value="tier")
        private String tier;
        @SerializedName(value="color")
        private String color;
        @SerializedName(value="maxLevel")
        private int maxLevel;
        @SerializedName(value="itemIDs")
        private List<Integer> itemIDs;

        public String b() {
            return this.name;
        }

        public String f() {
            return this.description;
        }

        public String c() {
            return this.tier;
        }

        public String d() {
            return this.color;
        }

        public int e() {
            return this.maxLevel;
        }

        public List a() {
            return this.itemIDs;
        }

        public boolean equals(Object object) {
            if (object == this) {
                return true;
            }
            if (!(object instanceof EnchantData)) {
                return false;
            }
            EnchantData kP2 = (EnchantData)object;
            if (!kP2.a(this)) {
                return false;
            }
            String string = this.b();
            String string2 = kP2.b();
            if (string == null ? string2 != null : !string.equals(string2)) {
                return false;
            }
            String string3 = this.f();
            String string4 = kP2.f();
            if (string3 == null ? string4 != null : !string3.equals(string4)) {
                return false;
            }
            String string5 = this.c();
            String string6 = kP2.c();
            if (string5 == null ? string6 != null : !string5.equals(string6)) {
                return false;
            }
            String string7 = this.d();
            String string8 = kP2.d();
            if (string7 == null ? string8 != null : !string7.equals(string8)) {
                return false;
            }
            if (this.e() != kP2.e()) {
                return false;
            }
            List list = this.a();
            List list2 = kP2.a();
            return !(list == null ? list2 != null : !((Object)list).equals(list2));
        }

        protected boolean a(Object object) {
            return object instanceof EnchantData;
        }

        public int hashCode() {
            int n = 1;
            String string = this.b();
            n = n * 59 + (string == null ? 43 : string.hashCode());
            String string2 = this.f();
            n = n * 59 + (string2 == null ? 43 : string2.hashCode());
            String string3 = this.c();
            n = n * 59 + (string3 == null ? 43 : string3.hashCode());
            String string4 = this.d();
            n = n * 59 + (string4 == null ? 43 : string4.hashCode());
            n = n * 59 + this.e();
            List list = this.a();
            n = n * 59 + (list == null ? 43 : ((Object)list).hashCode());
            return n;
        }

        public String toString() {
            return "SEnchantListPacket.EnchantData(name=" + this.b() + ", description=" + this.f() + ", tier=" + this.c() + ", color=" + this.d() + ", maxLevel=" + this.e() + ", itemIDs=" + this.a() + ")";
        }
    }

    @SerializedName(value="enchantData")
    private List<EnchantData> enchantData;

    public List a() {
        return this.enchantData;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof SEnchantListPacket)) {
            return false;
        }
        SEnchantListPacket sEnchantListPacket = (SEnchantListPacket)object;
        if (!sEnchantListPacket.a(this)) {
            return false;
        }
        List list = this.a();
        List list2 = sEnchantListPacket.a();
        return !(list == null ? list2 != null : !((Object)list).equals(list2));
    }

    @Override
    protected boolean a(Object object) {
        return object instanceof SEnchantListPacket;
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
        return "SEnchantListPacket(enchantData=" + this.a() + ")";
    }
}