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

import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.arkhamnetwork.Arkkit.utils.ItemUtils;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;

public class MoreBuckets
        extends ArkPlugin {
    public MoreBuckets(Plugin p) {
        super(p);
    }

    public static ItemStack getMultiBucket(BucketType bucket, int uses) {
        return ItemUtils.buildItem(bucket.m, (short) 0, bucket.getItemPrefix() + " [" + ChatColor.WHITE + uses + bucket.color + ChatColor.BOLD + "]", ChatColor.GRAY + "An enchanted bucket that contains", ChatColor.GRAY + "more " + bucket.name().toLowerCase() + " than normally possible", ChatColor.GRAY + "and leaves no empty buckets behind.");
    }

    public static int getBucketUses(ItemStack i) {
        if (!MoreBuckets.isMultiBucket(i)) {
            return 0;
        }
        return Integer.parseInt(ChatColor.stripColor(i.getItemMeta().getDisplayName()).split("\\[")[1].split("\\]")[0]);
    }

    public static void setBucketUses(ItemStack i, int uses) {
        if (!MoreBuckets.isMultiBucket(i)) {
            return;
        }
        BucketType type = MoreBuckets.getBucketType(i);
        if (type != null) {
            ItemMeta im = i.getItemMeta();
            im.setDisplayName(type.getItemPrefix() + " [" + ChatColor.WHITE + uses + type.color + ChatColor.BOLD + "]");
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
        return i.getType().name().toLowerCase().contains("_bucket") && i.hasItemMeta() && i.getItemMeta().hasLore() && i.getItemMeta().getLore().get(0).equals(ChatColor.GRAY + "An enchanted bucket that contains");
    }

    @Override
    public void enable() {
        this.registerCommand(new CommandMultiBucket("multibucket", "/<command>", "MoreBuckets command.", Arrays.asList("multibuckets, morebuckets")));
        this.registerListener(new MoreBucketsListener());
    }

    @Override
    public void disable() {
    }
}

