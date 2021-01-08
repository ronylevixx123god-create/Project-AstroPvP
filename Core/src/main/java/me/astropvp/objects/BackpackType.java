/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 */
package me.astropvp.objects;

import com.cosmicpvp.cosmicutils.utils.CC;
import me.astropvp.objects.skins.BugOutBagSkin;
import me.astropvp.objects.skins.CursedWingsSkin;
import me.astropvp.objects.skins.DeepSpaceBackpackSkin;
import me.astropvp.objects.skins.PortablePicnicSkin;
import me.astropvp.objects.skins.RainbowBackpackSkin;

public enum BackpackType {
    BUG_OUT_BAG(CC.GreenB + "Bug-Out Bag", "BUG_OUT_BAG", new BugOutBagSkin()),
    RAINBOW_BACKPACK(CC.GreenB + "Rainbow Backpack", "RAINBOW_BACKPACK", new RainbowBackpackSkin()),
    DEEP_SPACE_BACKPACK(CC.DarkPurpleB + "Deep Space Backpack", "DEEP_SPACE_BACKPACK", new DeepSpaceBackpackSkin()),
    CURSED_WINGS(CC.GoldB + "Cursed Wings", "CURSED_WINGS", new CursedWingsSkin()),
    PORTABLE_PICNIC(CC.YellowB + "Portable Picnic", "PORTABLE_PICNIC", new PortablePicnicSkin());

    String name;
    String id;
    Backpack instance;

    private BackpackType(String name, String id, Backpack instance) {
        this.name = name;
        this.id = id;
        this.instance = instance;
    }

    public String getName() {
        return this.name;
    }

    public String getId() {
        return this.id;
    }

    public Backpack getInstance() {
        return this.instance;
    }
}

