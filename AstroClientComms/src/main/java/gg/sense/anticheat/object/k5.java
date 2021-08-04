package gg.sense.anticheat.object;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;

public class k5
implements Serializable {
    @SerializedName(value="name")
    public String name;
    @SerializedName(value="nameColor")
    public int nameColor;
    @SerializedName(value="nameShadowColor")
    public int nameShadowColor;
    @SerializedName(value="nameShadow")
    public boolean nameShadow;
    @SerializedName(value="nameScale")
    public double nameScale;
    @SerializedName(value="itemName")
    public String itemName;
    @SerializedName(value="itemAmount")
    public int itemAmount;
    @SerializedName(value="itemMeta")
    public int itemMeta;
    @SerializedName(value="itemColor")
    public int itemColor;
    @SerializedName(value="itemScale")
    public double itemScale;
    @SerializedName(value="minX")
    public double minX;
    @SerializedName(value="minY")
    public double minY;
    @SerializedName(value="minZ")
    public double minZ;
    @SerializedName(value="maxX")
    public double maxX;
    @SerializedName(value="maxY")
    public double maxY;
    @SerializedName(value="maxZ")
    public double maxZ;
    @SerializedName(value="borderThickness")
    public double borderThickness;
    @SerializedName(value="borderColor")
    public int borderColor;
    @SerializedName(value="borderAccentColor")
    public int borderAccentColor;
    //public transient n9 b;
    public static final Gson a;

    public boolean a() {
        return this.minX == this.maxX && this.minY == this.maxY && this.minZ == this.maxZ;
    }

    public String toString() {
        return a.toJson((Object)this);
    }

    public ItemStack b() {
        if (this.itemName == null) {
            return null;
        }
        Material zR2 = Material.getMaterial(this.itemName);
//        if (zR2 == null && (zR2 = Material.(fr.C)) == null) {
//            return null;
//        }
        return new ItemStack(zR2, this.itemAmount, (byte) this.itemMeta);
    }

    static {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeSpecialFloatingPointValues();
        gsonBuilder.enableComplexMapKeySerialization();
        gsonBuilder.disableHtmlEscaping();
        gsonBuilder.setPrettyPrinting();
        a = gsonBuilder.create();
    }
}