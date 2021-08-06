package me.astropvp.objects;

import com.cosmicpvp.cosmicutils.utils.CC;
import me.astropvp.objects.skins.*;

public enum BackpackType {
    DOOMSDAY_BACKPACK_BUGOUT(CC.GreenB + "Bug-Out Bag", "BUG_OUT_BAG", new BugOutBagSkin()),
    DOOMSDAY_BACKPACK_RAINBOW(CC.GreenB + "Rainbow Backpack", "RAINBOW_BACKPACK", new RainbowBackpackSkin()),
    DEEP_SPACE_BACKPACK(CC.DarkPurpleB + "Deep Space Backpack", "DEEP_SPACE_BACKPACK", new DeepSpaceBackpackSkin()),
    HALLOWEEN_BACKPACK(CC.GoldB + "Cursed Wings", "CURSED_WINGS", new CursedWingsSkin()),
    VALENTINES_2021_BACKPACK(CC.RedB + "Teddy Backpack", "TEDDY_BACKPACK", new TeddyBackpack()),
    THANKSGIVING_BACKPACK_PICNIC(CC.YellowB + "Portable Picnic", "PORTABLE_PICNIC", new PortablePicnicSkin());


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
