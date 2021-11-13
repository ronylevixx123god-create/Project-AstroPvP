package me.astropvp.struct;

import me.astropvp.GenBucketPlugin;
import me.astropvp.utils.KrizzUtil;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GenBucket {
    private String name;
    private String displayName;
    private Material placedMaterial;
    private GenBucketDirection direction;
    private long speed = 10L;

    public GenBucket(String name, String displayName, Material material) {
        this.name = name;
        this.displayName = displayName;
        this.placedMaterial = material;
    }

    public Material getPlacedMaterial() {
        return this.placedMaterial;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlacedMaterial(Material placedMaterial) {
        this.placedMaterial = placedMaterial;
    }

    public void setSpeed(long speed) {
        this.speed = speed;
    }

    public String getName() {
        return this.name;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void startTask(Player player, Block block, BlockFace blockFace, BlockFace original, ActiveBucket bucket) {
        if (GenBucketTask.getTasks(player).isEmpty()) {
            KrizzUtil.sendMessage((LivingEntity)player, (String)"&a&lTIP: &7You can use &a/gentask&7 to view/cancel your &aGen Buckets&7.");
        }

        (new GenBucketTask(player, block, blockFace, original, bucket)).runTaskTimer(GenBucketPlugin.getInstance(), 0L, this.speed);
    }

    public ItemStack getItem(GenBucketDirection direction, double price) {
        Material itemType;
        if (direction == GenBucketDirection.VERTICAL) {
            itemType = Material.LAVA_BUCKET;
        } else {
            itemType = Material.WATER_BUCKET;
        }

        return KrizzUtil.createItem(itemType, "&a&lGen Bucket &7(" + this.displayName + ")", "", " &2&l* &a&lDirection &f" + direction.niceName(), " &2&l* &a&lMaterial &f" + WordUtils.capitalizeFully(this.placedMaterial.name().replace("_", " ")), " &2&l* &a&lPrice &f$" + price, "", "&7Place this bucket to begin generating!");
    }

    public boolean isItem(ItemStack otherItem) {
        if (otherItem != null && otherItem.getType().name().contains("BUCKET") && otherItem.hasItemMeta() && otherItem.getItemMeta().hasDisplayName() && otherItem.getItemMeta().hasLore()) {
            return !otherItem.getItemMeta().getLore().contains(KrizzUtil.color("&7Place this bucket to begin generating!")) ? false : otherItem.getItemMeta().getDisplayName().equalsIgnoreCase(this.getItem(GenBucketDirection.VERTICAL, 0.0D).getItemMeta().getDisplayName());
        } else {
            return false;
        }
    }

    public GenBucketDirection getDirection() {
        return this.direction;
    }

    public long getSpeed() {
        return this.speed;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setDirection(GenBucketDirection direction) {
        this.direction = direction;
    }
}
