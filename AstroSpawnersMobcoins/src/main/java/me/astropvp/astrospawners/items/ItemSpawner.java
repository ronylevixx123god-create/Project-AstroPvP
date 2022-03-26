package me.astropvp.astrospawners.items;

import java.util.Arrays;
import java.util.UUID;

import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import me.astropvp.astrospawners.utils.EntityUtils;
import me.ily2bb.slotbot.CosmicSlotBotAPI;
import me.ily2bb.slotbot.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class ItemSpawner {

    public static ItemStack getItem(EntityType creatureType, int amount) {
        ItemStack item = new ItemStack(Material.MOB_SPAWNER);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(CC.translate("&a&l") + EntityUtils.getEntityName(creatureType) + CC.translate("&f Spawner"));
        meta.setLore(Arrays.asList(CC.translate("&7This is a stacked spawner block."),
                CC.translate(""),
                CC.translate("&a&lINFO"),
                CC.translate("&a&l * &fAmount: &a" + amount),
                CC.translate("&a&l * &fType: &a" + EntityUtils.getEntityName(creatureType)),
                CC.translate(""),
                CC.translate("&7&nRight-Click&7 on a block to place this spawner down.")
                ));
        item.setItemMeta(meta);
        NBTWrapper wrapper = new NBTWrapper(item);
        NBTTagCompoundBuilder builder = new NBTTagCompoundBuilder();
        builder.setString("spawnerType", creatureType.name());
        builder.setInt("amount", amount);
        builder.setString("uuid", UUID.randomUUID().toString());
        wrapper.setNBTBase("spawnerData", builder.get());
        item = wrapper.build();
        return Utils.addSkin(creatureType.name().toUpperCase()+"_SPAWNER", item);
    }

    public static EntityType getEntityTypeFromItem(ItemStack item) {
        if (item == null || item.getType() != Material.MOB_SPAWNER) {
            return null;
        }
        NBTWrapper wrapper = new NBTWrapper(item);
        if (!wrapper.hasKey("spawnerData")) {
            return null;
        }
        NBTTagCompoundBuilder compound = new NBTTagCompoundBuilder(wrapper.getNBTBase("spawnerData"));
        return EntityType.valueOf(compound.getString("spawnerType"));
    }

    public static int getSpawnerAmount(ItemStack item) {
        if (item == null || item.getType() != Material.MOB_SPAWNER) {
            return -1;
        }
        NBTWrapper wrapper = new NBTWrapper(item);
        if (!wrapper.hasKey("spawnerData")) {
            return -1;
        }
        NBTTagCompoundBuilder compound = new NBTTagCompoundBuilder(wrapper.getNBTBase("spawnerData"));
        return compound.getInt("amount");
    }

    public static int getSpawnerLimit() {
        return 500000;
    }
}
