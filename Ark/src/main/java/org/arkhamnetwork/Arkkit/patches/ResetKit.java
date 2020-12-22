/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.itembuilder.BoosterBuilder
 *  com.cosmicpvp.cosmicutils.itembuilder.CustomCrateType
 *  com.cosmicpvp.cosmicutils.itembuilder.CustomItemBuilder
 *  com.cosmicpvp.cosmicutils.itembuilder.CustomItemBuilder$CustomItemType
 *  com.cosmicpvp.cosmicutils.itembuilder.CustomTitleBuilder
 *  com.cosmicpvp.cosmicutils.itembuilder.FatBucketBuilder
 *  com.cosmicpvp.cosmicutils.itembuilder.ItemBuilder
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.ItemBuilder
 *  com.cosmicpvp.cosmicutils.utils.NBTWrapper
 *  com.google.common.collect.Lists
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import com.cosmicpvp.cosmicutils.itembuilder.BoosterBuilder;
import com.cosmicpvp.cosmicutils.itembuilder.CustomItemBuilder;
import com.cosmicpvp.cosmicutils.itembuilder.CustomTitleBuilder;
import com.cosmicpvp.cosmicutils.itembuilder.FatBucketBuilder;
import com.cosmicpvp.cosmicutils.itembuilder.ItemBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import com.google.common.collect.Lists;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class ResetKit
extends ArkPlugin {
    public ResetKit(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        this.registerListener(new ResetKitListener(this));
        this.registerCommand(new ResetKitCommand(this, "resetkit"));
    }

    @Override
    public void disable() throws IOException {
        super.disable();
        this.unregisterListeners();
        this.unregisterCommands();
    }

    public ItemStack createResetKit(boolean applyIID) {
        String color = CC.White;
        String key = CC.GoldB + "* " + color;
        NBTWrapper wrapper = new NBTWrapper(new com.cosmicpvp.cosmicutils.utils.ItemBuilder(Material.CHEST, CC.GoldB + "Reset Kit " + CC.Gray + "(Right Click)", new String[]{"", CC.Gold + "Contains a Mystery Cache of items...", key + "100-200 mcMMO Credits", key + "2-5 Ultimate " + color + CC.Bold + CC.Underline + "OR" + color + " Legendary Keys", key + "1-2 Rare " + color + CC.Bold + CC.Underline + "OR" + color + " Legendary Space Chests", key + "2,500-6,000 EXP", key + "2-4 Item Nametags", key + "1-4x Mystery Mob Spawners", key + "Exclusive Titles", key + "3x Fat Buckets", key + "XP Boosters"}).build()).setString("cosmicType", "RESET_KIT");
        ItemStack item = wrapper.build();
        if (applyIID && ArkPlugins.ITEM_ID.getArkPlugin().isRunning()) {
            item = ItemID.assignItemID(null, item);
        }
        return item;
    }

    public List<ItemStack> getResetKitItems(Player player) {
        ArrayList retr = Lists.newArrayList();
        retr.add(McmmoCredits.buildMcmmoCreditItem(100 + random.nextInt(101)));
        int toGive = random.nextInt(4) + 2;
        for (int i = 0; i < toGive; ++i) {
            //ItemStack key = new ItemBuilder(random.nextDouble() <= 0.65 ? CustomCrateType.ULTIMATE : CustomCrateType.LEGENDARY).build();
            //if (key == null) continue;
            //retr.add(key);
        }
        retr.add(ExpBottles.getXpBottle(2500 + random.nextInt(3501), "Reset Kit"));
        retr.add(new CustomTitleBuilder(random.nextDouble() <= 0.75 ? "#vanilla" : "#comp").build());
        retr.add(new CustomItemBuilder(CustomItemBuilder.CustomItemType.ITEM_NAMETAG, 2 + random.nextInt(3)).build());
        retr.add(new CustomItemBuilder(CustomItemBuilder.CustomItemType.MYSTERY_MOBSPAWNER, 1 + random.nextInt(4)).build());
        retr.add(new FatBucketBuilder().build());
        retr.add(new FatBucketBuilder().build());
        retr.add(new FatBucketBuilder().build());
        if (random.nextDouble() <= 0.85) {
            retr.add(new BoosterBuilder("EXP", 1800, 2.0).build());
        } else {
            retr.add(new BoosterBuilder("EXP", 600, 5.0).build());
        }
        return retr;
    }
}

