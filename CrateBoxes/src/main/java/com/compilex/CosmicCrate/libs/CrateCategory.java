/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  org.bukkit.ChatColor
 */
package com.compilex.CosmicCrate.libs;

import com.compilex.CosmicCrate.libs.Version;
import com.cosmicpvp.cosmicutils.utils.CC;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.bukkit.ChatColor;

public enum CrateCategory {
    CC_2015("2015", ChatColor.BLUE),
    CC_2016("2016", ChatColor.AQUA),
    CC_2017("2017", ChatColor.YELLOW),
    CC_2018("2018", ChatColor.LIGHT_PURPLE),
    CC_2019("2019", ChatColor.GREEN);

    private String year;
    private ChatColor color;
    private String displayName;

    private CrateCategory(String year, ChatColor color) {
        this.year = year;
        this.color = color;
        this.displayName = (Object)this.color + CC.Bold + CC.Underline + this.year + (Object)this.color + CC.Bold + " Cosmic Crates";
    }

    public List<Version> getCrates() {
        return Arrays.stream(Version.values()).filter(f -> f.getCategory() == this).collect(Collectors.toList());
    }

    public String getYear() {
        return this.year;
    }

    public ChatColor getColor() {
        return this.color;
    }

    public String getDisplayName() {
        return this.displayName;
    }
}

