/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.massivecraft.factions.Faction
 *  org.bukkit.DyeColor
 *  org.bukkit.Material
 *  org.bukkit.block.banner.Pattern
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.BannerMeta
 *  org.bukkit.inventory.meta.ItemMeta
 */
package com.massivecraft.factions.struct;

import com.google.common.collect.Lists;
import com.massivecraft.factions.Faction;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.massivecraft.factions.P;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class BannerDesign {
    private DyeColor baseColor;
    private LinkedList<Pattern> bannerPatterns = new LinkedList();
    private transient ItemStack cachedItem;

    public void setBanner(ItemStack newBanner) {
        if (newBanner == null || newBanner.getType() != Material.BANNER) {
            this.cachedItem = null;
            this.bannerPatterns.clear();
            return;
        }
        this.cachedItem = null;
        BannerMeta meta = (BannerMeta)newBanner.getItemMeta();
        this.baseColor = DyeColor.getByDyeData((byte)newBanner.getData().getData());
        this.bannerPatterns = new LinkedList(meta.getPatterns());
    }

    public boolean hasBanner() {
        return this.bannerPatterns != null;
    }

    public ItemStack getBannerItem(Faction faction) {
        if (this.cachedItem != null) {
            return this.cachedItem;
        }
        this.cachedItem = P.getP().getBannerManager().createBanner(faction, null, false);
        BannerMeta meta = (BannerMeta)this.cachedItem.getItemMeta();
        ArrayList newLists = Lists.newArrayList();
        newLists.addAll(this.bannerPatterns);
        meta.setPatterns((List)newLists);
        meta.setBaseColor(this.baseColor == null ? DyeColor.WHITE : this.baseColor);
        this.cachedItem.setItemMeta((ItemMeta)meta);
        this.cachedItem.setDurability((short)meta.getBaseColor().getDyeData());
        return this.cachedItem;
    }
}

