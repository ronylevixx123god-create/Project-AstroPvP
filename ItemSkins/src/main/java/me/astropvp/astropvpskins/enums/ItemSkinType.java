/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  org.bukkit.inventory.ItemStack
 */
package me.astropvp.astropvpskins.enums;

import com.cosmicpvp.cosmicutils.utils.CC;
import java.util.Arrays;
import java.util.List;
import org.bukkit.inventory.ItemStack;

public enum ItemSkinType {
    SWORD,
    AXE,
    BOW,
    WEAPON,
    HELMET,
    CHESTPLATE,
    LEGGINGS,
    BOOTS,
    AMULET,
    BELT,
    PICKAXE;


    public List<String> getLore() {
        return Arrays.asList("", String.valueOf(CC.GrayI) + "Attach this skin to any " + CC.WhiteU + this.getModifiedName(), String.valueOf(CC.GrayI) + "to override its visual appeareance.", "", String.valueOf(CC.RedB) + "NOTE: " + CC.Red + "Requires the Cosmic Client ", String.valueOf(CC.Red) + "to render custom item skins.", String.valueOf(CC.Gray) + "* Download at " + CC.Underline + "cosmicclient.com", "", String.valueOf(CC.Gray) + "Drag n' Drop onto item to attach.", String.valueOf(CC.Gray) + "Right-Click item to detach skin.");
    }

    public boolean isType(ItemStack item) {
        if (item == null) {
            return false;
        }
        String name = this.getModifiedName();
        String otherName = item.getType().name();
        return otherName.endsWith(name) || name.contentEquals("WEAPON") && (otherName.endsWith("_AXE") || otherName.endsWith("_SWORD"));
    }

    public String getModifiedName() {
        return this == BELT ? LEGGINGS.name() : (this == AMULET ? CHESTPLATE.name() : this.name());
    }

    public static boolean isSimilarItemSkin(ItemSkinType type, ItemSkinType other) {
        return other == WEAPON && (type == SWORD || type == AXE) || type == other;
    }
}

