package me.astropvp.struct;

import me.astropvp.GenBucketPlugin;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

public class ActiveBucket {
    private GenBucketDirection direction;
    private double price;
    private GenBucket bucket;

    public ActiveBucket(GenBucketDirection direction, double price, GenBucket bucket) {
        this.direction = direction;
        this.price = price;
        this.bucket = bucket;
    }

    public GenBucketDirection getDirection() {
        return this.direction;
    }

    public double getPrice() {
        return this.price;
    }

    public GenBucket getBucket() {
        return this.bucket;
    }

    public static ActiveBucket of(ItemStack item) {
        try {
            GenBucket genBucket = GenBucketPlugin.getInstance().getBucketHandler().getGenBucket(item);
            GenBucketDirection direction = GenBucketDirection.valueOf(ChatColor.stripColor(((String)item.getItemMeta().getLore().get(1)).split(" ")[3].toUpperCase()));
            double price = Double.parseDouble(ChatColor.stripColor(((String)item.getItemMeta().getLore().get(3)).split("\\$")[1]));
            return new ActiveBucket(direction, price, genBucket);
        } catch (Exception var5) {
            return null;
        }
    }
}
