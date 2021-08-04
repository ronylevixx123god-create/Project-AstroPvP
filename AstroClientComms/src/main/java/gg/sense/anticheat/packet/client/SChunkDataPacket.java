package gg.sense.anticheat.packet.client;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SChunkDataPacket
extends ClientPacket {

    public class ChunkData implements Serializable {
        @SerializedName(value="x")
        private int x;
        @SerializedName(value="z")
        private int z;
        @SerializedName(value="uuid")
        private String uuid;
        @SerializedName(value="name")
        private String name;
        @SerializedName(value="color")
        private String color;

        public int d() {
            return this.x;
        }

        public int e() {
            return this.z;
        }

        public String c() {
            return this.uuid;
        }

        public String a() {
            return this.name;
        }

        public String b() {
            return this.color;
        }

        public boolean equals(Object object) {
            if (object == this) {
                return true;
            }
            if (!(object instanceof ChunkData)) {
                return false;
            }
            ChunkData cm2 = (ChunkData)object;
            if (!cm2.a(this)) {
                return false;
            }
            if (this.d() != cm2.d()) {
                return false;
            }
            if (this.e() != cm2.e()) {
                return false;
            }
            String string = this.c();
            String string2 = cm2.c();
            if (string == null ? string2 != null : !string.equals(string2)) {
                return false;
            }
            String string3 = this.a();
            String string4 = cm2.a();
            if (string3 == null ? string4 != null : !string3.equals(string4)) {
                return false;
            }
            String string5 = this.b();
            String string6 = cm2.b();
            return !(string5 == null ? string6 != null : !string5.equals(string6));
        }

        protected boolean a(Object object) {
            return object instanceof ChunkData;
        }

        public int hashCode() {
            int n = 1;
            n = n * 59 + this.d();
            n = n * 59 + this.e();
            String string = this.c();
            n = n * 59 + (string == null ? 43 : string.hashCode());
            String string2 = this.a();
            n = n * 59 + (string2 == null ? 43 : string2.hashCode());
            String string3 = this.b();
            n = n * 59 + (string3 == null ? 43 : string3.hashCode());
            return n;
        }

        public String toString() {
            return "SChunkDataPacket.ChunkData(x=" + this.d() + ", z=" + this.e() + ", uuid=" + this.c() + ", name=" + this.a() + ", color=" + this.b() + ")";
        }
    }

    @SerializedName(value="chunkDataList")
    private List<ChunkData> chunkDataList;

    public List a() {
        return this.chunkDataList;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof SChunkDataPacket)) {
            return false;
        }
        SChunkDataPacket sChunkDataPacket = (SChunkDataPacket)object;
        if (!sChunkDataPacket.a(this)) {
            return false;
        }
        List list = this.a();
        List list2 = sChunkDataPacket.a();
        return !(list == null ? list2 != null : !((Object)list).equals(list2));
    }

    @Override
    protected boolean a(Object object) {
        return object instanceof SChunkDataPacket;
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
        return "SChunkDataPacket(chunkDataList=" + this.a() + ")";
    }
}