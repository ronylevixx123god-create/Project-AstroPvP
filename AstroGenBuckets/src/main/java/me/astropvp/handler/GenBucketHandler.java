package me.astropvp.handler;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;

import me.astropvp.GenBucketPlugin;
import me.astropvp.struct.GenBucket;
import me.astropvp.utils.KrizzUtil;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

public class GenBucketHandler {
    private List<GenBucket> genBuckets = Lists.newArrayList();
    private GenBucketPlugin plugin;

    public GenBucketHandler(GenBucketPlugin plugin) {
        this.plugin = plugin;
        this.loadGenBuckets();
    }

    public List<GenBucket> getGenBuckets() {
        return this.genBuckets;
    }

    public GenBucket getGenBucket(String name) {
        return (GenBucket)this.genBuckets.stream().filter((genBucket) -> {
            return genBucket.getName().equalsIgnoreCase(name);
        }).findFirst().orElse(null);
    }

    public GenBucket getGenBucket(ItemStack itemStack) {
        for(GenBucket genBucket : this.genBuckets) {
            if(genBucket.isItem(itemStack))
                return genBucket;
        }
        return null;
    }

    public void loadGenBuckets() {
        FileConfiguration config = this.plugin.getConfig();
        ConfigurationSection allBuckets = config.getConfigurationSection("buckets");
        Iterator var3 = allBuckets.getKeys(false).iterator();

        while(var3.hasNext()) {
            String bucket = (String)var3.next();
            ConfigurationSection thisBucket = allBuckets.getConfigurationSection(bucket);
            String displayName = thisBucket.getString("name");
            long speed = thisBucket.getLong("speed");
            Material generate = KrizzUtil.findEssItem(thisBucket.getString("generate")).getType();
            Material itemType = KrizzUtil.findEssItem(thisBucket.getString("item")).getType();
            GenBucket genBucket = new GenBucket(bucket, displayName, generate, itemType);
            genBucket.setSpeed(speed);
            genBucket.setPlacedMaterial(generate);
            this.genBuckets.add(genBucket);
        }

    }

    public GenBucketPlugin getPlugin() {
        return this.plugin;
    }
}
