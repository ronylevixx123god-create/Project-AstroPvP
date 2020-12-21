/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import java.util.Arrays;
import org.arkhamnetwork.Arkkit.patches.BucketType;
import org.arkhamnetwork.Arkkit.patches.CommandMultiBucket;
import org.arkhamnetwork.Arkkit.patches.MoreBucketsListener;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.arkhamnetwork.Arkkit.utils.ItemUtils;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class MoreBuckets
extends ArkPlugin {
    public MoreBuckets(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        this.registerCommand(new CommandMultiBucket("multibucket", "/<command>", "MoreBuckets command.", Arrays.asList("multibuckets, morebuckets")));
        this.registerListener(new MoreBucketsListener());
    }

    @Override
    public void disable() {
    }

    public static ItemStack getMultiBucket(BucketType bucket, int uses) {
        return ItemUtils.buildItem(bucket.m, (short)0, bucket.getItemPrefix() + " [" + (Object)ChatColor.WHITE + uses + (Object)bucket.color + (Object)ChatColor.BOLD + "]", (Object)ChatColor.GRAY + "An enchanted bucket that contains", (Object)ChatColor.GRAY + "more " + bucket.name().toLowerCase() + " than normally possible", (Object)ChatColor.GRAY + "and leaves no empty buckets behind.");
    }

    public static int getBucketUses(ItemStack i) {
        if (!MoreBuckets.isMultiBucket(i)) {
            return 0;
        }
        return Integer.parseInt(ChatColor.stripColor((String)i.getItemMeta().getDisplayName()).split("\\[")[1].split("\\]")[0]);
    }

    public static void setBucketUses(ItemStack i, int uses) {
        if (!MoreBuckets.isMultiBucket(i)) {
            return;
        }
        BucketType type = MoreBuckets.getBucketType(i);
        if (type != null) {
            ItemMeta im = i.getItemMeta();
            im.setDisplayName(type.getItemPrefix() + " [" + (Object)ChatColor.WHITE + uses + (Object)type.color + (Object)ChatColor.BOLD + "]");
            i.setItemMeta(im);
        }
    }

    public static BucketType getBucketType(ItemStack i) {
        if (!MoreBuckets.isMultiBucket(i)) {
            return null;
        }
        for (BucketType type : BucketType.values()) {
            if (type.m != i.getType()) continue;
            return type;
        }
        return null;
    }

    public static boolean isMultiBucket(ItemStack i) {
        return i.getType().name().toLowerCase().contains("_bucket") && i.hasItemMeta() && i.getItemMeta().hasLore() && ((String)i.getItemMeta().getLore().get(0)).equals((Object)ChatColor.GRAY + "An enchanted bucket that contains");
    }
}

