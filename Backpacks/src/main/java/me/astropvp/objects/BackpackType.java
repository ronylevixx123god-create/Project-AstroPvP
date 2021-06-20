package me.astropvp.objects;

import com.cosmicpvp.cosmicutils.utils.CC;
import me.astropvp.objects.skins.*;

public enum BackpackType {
    BUG_OUT_BAG(CC.GreenB + "Bug-Out Bag", "BUG_OUT_BAG", new BugOutBagSkin()),
    RAINBOW_BACKPACK(CC.GreenB + "Rainbow Backpack", "RAINBOW_BACKPACK", new RainbowBackpackSkin()),
    DEEP_SPACE_BACKPACK(CC.DarkPurpleB + "Deep Space Backpack", "DEEP_SPACE_BACKPACK", new DeepSpaceBackpackSkin()),
    CURSED_WINGS(CC.GoldB + "Cursed Wings", "CURSED_WINGS", new CursedWingsSkin()),
    TEDDY_BACKPACK(CC.RedB + "Teddy Backpack", "TEDDY_BACKPACK", new TeddyBackpack()),
    PORTABLE_PICNIC(CC.YellowB + "Portable Picnic", "PORTABLE_PICNIC", new PortablePicnicSkin());


    String name;
    String id;
    Backpack instance;

    BackpackType(String name, String id, Backpack instance) {
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
