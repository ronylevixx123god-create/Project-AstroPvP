// 
// Decompiled by Procyon v0.5.36
// 

package com.compilex.CosmicCrate.libs;

import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.List;
import com.cosmicpvp.cosmicutils.utils.CC;
import org.bukkit.ChatColor;

public enum CrateCategory
{
    CC_2015("2015", ChatColor.BLUE), 
    CC_2016("2016", ChatColor.AQUA), 
    CC_2017("2017", ChatColor.YELLOW), 
    CC_2018("2018", ChatColor.LIGHT_PURPLE), 
    CC_2019("2019", ChatColor.GREEN);
    
    private String year;
    private ChatColor color;
    private String displayName;
    
    private CrateCategory(final String year, final ChatColor color) {
        this.year = year;
        this.color = color;
        this.displayName = this.color + CC.Bold + CC.Underline + this.year + this.color + CC.Bold + " Cosmic Crates";
    }
    
    public List<Version> getCrates() {
        return Arrays.stream(Version.values()).filter(f -> f.getCategory() == this).collect((Collector<? super Version, ?, List<Version>>)Collectors.toList());
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
