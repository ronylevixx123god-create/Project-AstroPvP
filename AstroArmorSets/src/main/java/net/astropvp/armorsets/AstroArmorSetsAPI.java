package net.astropvp.armorsets;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;

import net.astropvp.armorsets.struct.ArmorSetType;
import net.astropvp.armorsets.struct.cache.ArmorSetCache;
import net.astropvp.core.AstroCore;
import net.astropvp.core.utils.cache.CacheManager;
import net.astropvp.core.utils.cache.CachedPlayer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class AstroArmorSetsAPI {
    public static ArmorSetType getSetTypeOnItem(ItemStack item) {
        if (item != null && item.getType() != Material.AIR) {
            NBTItem wrapper = new NBTItem(item);
            NBTCompound cosmicData = wrapper.getOrCreateCompound("cosmicData");
            return cosmicData.hasKey("armorSetType") ? ArmorSetType.valueOf(cosmicData.getString("armorSetType")) : null;
        } else {
            return null;
        }
    }

    public static boolean hasSetTypeOnItem(ItemStack item) {
        if (item != null && item.getType() != Material.AIR) {
            NBTItem wrapper = new NBTItem(item);
            NBTCompound cosmicData = wrapper.getOrCreateCompound("cosmicData");
            return cosmicData.hasKey("armorSetType");
        } else {
            return false;
        }
    }

    public static ArmorSetType getCurrentArmorSet(Player player) {
        CachedPlayer cachedPlayer = CacheManager.getInstance().getCachedPlayer(player);
        ArmorSetCache cache = (ArmorSetCache)cachedPlayer.getCache("armor_set");
        return cache.getCurrentArmorSet();
    }

    public static Map<ArmorSetType, Integer> getCurrentCrystals(Player player) {
        CachedPlayer cachedPlayer = CacheManager.getInstance().getCachedPlayer(player);
        ArmorSetCache cache = (ArmorSetCache)cachedPlayer.getCache("armor_set");
        return cache.getCurrentSetCrystals();
    }

    public static void refreshArmorSets(Player player) {
        CachedPlayer armorSetPlayer = AstroCore.getInstance().getCacheManager().getCachedPlayer(player);
        ArmorSetCache cache = (ArmorSetCache)((ArmorSetCache)(armorSetPlayer.hasCache("armor_set") ? armorSetPlayer.getCache("armor_set") : armorSetPlayer.registerCache(new ArmorSetCache(player))));
        ItemStack[] armor = player.getInventory().getArmorContents();

        for (ItemStack item : armor) {
            if (hasSetTypeOnItem(item)) {
                cache.addArmorSet(getSetTypeOnItem(item));
            }
        }

    }
}
