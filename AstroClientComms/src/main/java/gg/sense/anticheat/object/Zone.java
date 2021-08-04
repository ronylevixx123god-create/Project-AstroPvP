package gg.sense.anticheat.object;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Decencies
 * @since 13/07/2021 17:21
 */
public class Zone {

    @SerializedName(value="startX")
    private int startX;
    @SerializedName(value="startY")
    private int startY;
    @SerializedName(value="startZ")
    private int startZ;
    @SerializedName(value="endX")
    private int endX;
    @SerializedName(value="endY")
    private int endY;
    @SerializedName(value="endZ")
    private int endZ;
    @SerializedName(value="color")
    private String color;
    @SerializedName(value="duration")
    private int duration;
    @SerializedName(value="lines")
    private List<String> lines;

}
