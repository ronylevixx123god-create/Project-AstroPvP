/*
 * Decompiled with CFR 0.150.
 */
package org.bukkit;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import org.bukkit.Color;

public enum DyeColor {
    WHITE(0, 15, Color.WHITE, Color.fromRGB(0xF0F0F0)),
    ORANGE(1, 14, Color.fromRGB(14188339), Color.fromRGB(15435844)),
    MAGENTA(2, 13, Color.fromRGB(11685080), Color.fromRGB(12801229)),
    LIGHT_BLUE(3, 12, Color.fromRGB(6724056), Color.fromRGB(6719955)),
    YELLOW(4, 11, Color.fromRGB(0xE5E533), Color.fromRGB(14602026)),
    LIME(5, 10, Color.fromRGB(8375321), Color.fromRGB(4312372)),
    PINK(6, 9, Color.fromRGB(15892389), Color.fromRGB(14188952)),
    GRAY(7, 8, Color.fromRGB(0x4C4C4C), Color.fromRGB(0x434343)),
    SILVER(8, 7, Color.fromRGB(0x999999), Color.fromRGB(0xABABAB)),
    CYAN(9, 6, Color.fromRGB(5013401), Color.fromRGB(2651799)),
    PURPLE(10, 5, Color.fromRGB(8339378), Color.fromRGB(8073150)),
    BLUE(11, 4, Color.fromRGB(3361970), Color.fromRGB(2437522)),
    BROWN(12, 3, Color.fromRGB(6704179), Color.fromRGB(5320730)),
    GREEN(13, 2, Color.fromRGB(6717235), Color.fromRGB(3887386)),
    RED(14, 1, Color.fromRGB(0x993333), Color.fromRGB(11743532)),
    BLACK(15, 0, Color.fromRGB(0x191919), Color.fromRGB(0x1E1B1B));

    private final byte woolData;
    private final byte dyeData;
    private final Color color;
    private final Color firework;
    private static final DyeColor[] BY_WOOL_DATA;
    private static final DyeColor[] BY_DYE_DATA;
    private static final Map<Color, DyeColor> BY_COLOR;
    private static final Map<Color, DyeColor> BY_FIREWORK;

    private DyeColor(int woolData, int dyeData, Color color, Color firework) {
        this.woolData = (byte)woolData;
        this.dyeData = (byte)dyeData;
        this.color = color;
        this.firework = firework;
    }

    @Deprecated
    public byte getData() {
        return this.getWoolData();
    }

    @Deprecated
    public byte getWoolData() {
        return this.woolData;
    }

    @Deprecated
    public byte getDyeData() {
        return this.dyeData;
    }

    public Color getColor() {
        return this.color;
    }

    public Color getFireworkColor() {
        return this.firework;
    }

    @Deprecated
    public static DyeColor getByData(byte data) {
        return DyeColor.getByWoolData(data);
    }

    @Deprecated
    public static DyeColor getByWoolData(byte data) {
        int i = 0xFF & data;
        if (i >= BY_WOOL_DATA.length) {
            return null;
        }
        return BY_WOOL_DATA[i];
    }

    @Deprecated
    public static DyeColor getByDyeData(byte data) {
        int i = 0xFF & data;
        if (i >= BY_DYE_DATA.length) {
            return null;
        }
        return BY_DYE_DATA[i];
    }

    public static DyeColor getByColor(Color color) {
        return BY_COLOR.get(color);
    }

    public static DyeColor getByFireworkColor(Color color) {
        return BY_FIREWORK.get(color);
    }

    static {
        BY_WOOL_DATA = DyeColor.values();
        BY_DYE_DATA = DyeColor.values();
        ImmutableMap.Builder<Color, DyeColor> byColor = ImmutableMap.builder();
        ImmutableMap.Builder<Color, DyeColor> byFirework = ImmutableMap.builder();
        DyeColor[] arr$ = DyeColor.values();
        int len$ = arr$.length;
        for (int i$ = 0; i$ < len$; ++i$) {
            DyeColor color;
            DyeColor.BY_WOOL_DATA[color.woolData & 255] = color = arr$[i$];
            DyeColor.BY_DYE_DATA[color.dyeData & 255] = color;
            byColor.put(color.getColor(), color);
            byFirework.put(color.getFireworkColor(), color);
        }
        BY_COLOR = byColor.build();
        BY_FIREWORK = byFirework.build();
    }
}

