package gg.sense.anticheat.object;

import com.google.gson.annotations.SerializedName;

/**
 * @author Decencies
 * @since 13/07/2021 17:25
 */
public class PopUp {

    @SerializedName(value="x")
    private int x;
    @SerializedName(value="y")
    private int y;
    @SerializedName(value="imageURL")
    private String imageURL;
    @SerializedName(value="hoverURL")
    private String hoverURL;
    @SerializedName(value="URL")
    private String URL;
    @SerializedName(value="duration")
    private int duration;
    @SerializedName(value="started")
    private long started;
    @SerializedName(value="width")
    private int width;
    @SerializedName(value="height")
    private int height;
    @SerializedName(value="position")
    private Position position;

}
