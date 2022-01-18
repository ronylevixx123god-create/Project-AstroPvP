package net.astropvp.armorsets;

import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.nbt.NBTTagListBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import com.google.common.collect.Lists;

import me.ifamasssxd.cosmicskycustoms.struct.CustomItemBuilder;
import net.astropvp.armorsets.struct.ArmorSetType;
import net.astropvp.armorsets.struct.cache.ArmorSetCache;
import net.astropvp.core.AstroCore;
import net.astropvp.core.utils.cache.CacheManager;
import net.astropvp.core.utils.cache.CachedPlayer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class AstroArmorSetsAPI {
    public static ArmorSetType getSetTypeOnItem(ItemStack item) {
        if (item != null && item.getType() != Material.AIR) {
            NBTWrapper wrapper = new NBTWrapper(item);
            NBTTagCompoundBuilder cosmicData = wrapper.getCompoundOrCreate("cosmicData");
            return cosmicData.hasKey("armorSetType") ? ArmorSetType.valueOf(cosmicData.getString("armorSetType")) : null;
        } else {
            return null;
        }
    }

    public static boolean hasSetTypeOnItem(ItemStack item) {
        if (item != null && item.getType() != Material.AIR) {
            NBTWrapper wrapper = new NBTWrapper(item);
            NBTTagCompoundBuilder cosmicData = wrapper.getCompoundOrCreate("cosmicData");
            return cosmicData.hasKey("armorSetType");
        } else {
            return false;
        }
    }

    public static ArmorSetType getCurrentArmorSet(Player player) {
        CachedPlayer cachedPlayer = CacheManager.getInstance().getCachedPlayer(player);
        ArmorSetCache cache = (ArmorSetCache) cachedPlayer.getCache("armor_set");
        return cache.getCurrentArmorSet();
    }

    public static Map<ArmorSetType, Integer> getCurrentCrystals(Player player) {
        CachedPlayer cachedPlayer = CacheManager.getInstance().getCachedPlayer(player);
        ArmorSetCache cache = (ArmorSetCache) cachedPlayer.getCache("armor_set");
        return cache.getCurrentSetCrystals();
    }

    public static void refreshArmorSets(Player player) {
        CachedPlayer armorSetPlayer = AstroCore.getInstance().getCacheManager().getCachedPlayer(player);
        ArmorSetCache cache = (ArmorSetCache) ((ArmorSetCache) (armorSetPlayer.hasCache("armor_set") ? armorSetPlayer.getCache("armor_set") : armorSetPlayer.registerCache(new ArmorSetCache(player))));
        ItemStack[] armor = player.getInventory().getArmorContents();

        for (ItemStack item : armor) {
            if (hasSetTypeOnItem(item)) {
                cache.addArmorSet(getSetTypeOnItem(item));
            }
        }
    }

    public static List<ArmorSetType> getArmorCrystalsFromCrystalItem(NBTTagCompoundBuilder wrapper) {
        if (wrapper.hasKey("bonus")) {
            ArmorSetType set = AstroArmorSetsAPI.armorSetTypeFromName(wrapper.getString("bonus"));
            if (set != null) {
                return Collections.singletonList(set);
            }
        } else if (wrapper.hasKey("bonuses")) {
            ArrayList retr2 = Lists.newArrayList();
            NBTTagListBuilder list = new NBTTagListBuilder(wrapper.getNBTBase("bonuses"));
            for (int i = 0; i < list.size(); ++i) {
                ArmorSetType from;
                String bonus = list.getStringAt(i);
                if (bonus == null || bonus.isEmpty() || (from = armorSetTypeFromName(bonus)) == null) continue;
                retr2.add(from);
            }
            return retr2;
        }
        return Collections.emptyList();
    }

    public static List<ArmorSetType> getFromArmorBonusCrystal(NBTWrapper wrapper) {
        NBTTagCompoundBuilder cosmicData = wrapper.getCompound("cosmicData");
        if (cosmicData == null) {
            return null;
        }
        List<ArmorSetType> retr = new ArrayList<>();
        if (cosmicData.hasKey("crystalBonus")) {
            String upgrade = cosmicData.getString("crystalBonus");
            if (upgrade == null) {
                return null;
            }
            ArmorSetType from = AstroArmorSetsAPI.armorSetTypeFromName(upgrade);
            if (from != null) {
                retr.add(from);
            }
        } else if (cosmicData.hasKey("crystalBonuses")) {
            NBTTagListBuilder list = new NBTTagListBuilder(cosmicData.getNBTBase("crystalBonuses"));
            for (int i = 0; i < list.size(); ++i) {
                ArmorSetType from2;
                String bonus = list.getStringAt(i);
                if (bonus == null || bonus.isEmpty() || (from2 = AstroArmorSetsAPI.armorSetTypeFromName(bonus)) == null) continue;
                retr.add(from2);
            }
        }
        return retr;
    }

    public static ItemStack applyCrystalToItem(List<ArmorSetType> set, ItemStack item) {
        NBTWrapper wrapper = new NBTWrapper(item);
        NBTTagCompoundBuilder data = wrapper.getCompoundOrCreate("cosmicData");
        NBTTagListBuilder nbtTagListBuilder = new NBTTagListBuilder();
        StringBuilder sb = new StringBuilder();
        set.forEach(s -> {
            nbtTagListBuilder.addString(s.name());
            sb.append(s.getDisplay()).append(CC.Gray).append(", ");
        });
        data.setNBTBase("crystalBonuses", nbtTagListBuilder.build());
        wrapper.setNBTBase("cosmicData", data.get());
        item = wrapper.build();
        ItemMeta im = item.getItemMeta();
        List<String> lore = im.getLore() == null ? Lists.newArrayList() : im.getLore();
        String str = sb.toString();
        if (str.endsWith(", ")) {
            str = str.substring(0, str.length() - 2);
        }
        lore.add(CC.GoldB + "Armor Crystal (" + str + CC.GoldB + ")");
        im.setLore(lore);
        item.setItemMeta(im);
        return item;
    }

    public static ItemStack removeCrystalFromItem(NBTWrapper wrapper) {
        NBTTagCompoundBuilder data = wrapper.getCompoundOrCreate("cosmicData");
        data.remove("crystalBonus");
        data.remove("crystalBonuses");
        wrapper.setNBTBase("cosmicData", data.get());
        ItemStack item = wrapper.build();
        ItemMeta im = item.getItemMeta();
        List<String> lore = im.getLore();
        if (lore != null) {
            lore.removeIf(s -> s.startsWith(CC.GoldB + "Armor Crystal ("));
            im.setLore(lore);
        }
        item.setItemMeta(im);
        return item;
    }

    public static ItemStack removeCrystalFromItem(ItemStack item) {
        return removeCrystalFromItem(new NBTWrapper(item));
    }

    public static boolean isHeroic(ItemStack item) {
        if (item == null)
            return false;
        if (!item.getType().name().contains("LEATHER"))
            return false;

        NBTWrapper wrapper = new NBTWrapper(item);
        NBTTagCompoundBuilder cosmicData = wrapper.getCompoundOrCreate("cosmicData");
        return cosmicData.hasKey("heroicArmor");
    }

    public static boolean hasCrystalsApplied(ItemStack item) {
        return getCrystalsFromItem(item) != null && !getCrystalsFromItem(item).isEmpty();
    }

    public static Map<ArmorSetType, Integer> getBonusCrystalsEquipped(Player player) {
        HashMap<ArmorSetType, Integer> found = null;
        for (ItemStack armor : player.getInventory().getArmorContents()) {
            List<ArmorSetType> sets;
            if (armor == null || armor.getType() == Material.AIR || (sets = getCrystalsFromItem(armor)) == null || sets.isEmpty())
                continue;
            if (found == null) {
                found = new HashMap<>();
            }
            for (ArmorSetType type : sets) {
                ArmorSetType set;
                Integer current = found.get(set = type);
                found.put(set, current == null ? 1 : current + 1);
            }
        }
        return found == null ? Collections.emptyMap() : found;
    }

    public static List<ArmorSetType> getCrystalsFromItem(ItemStack item) {
        if (item == null) {
            return null;
        }
        NBTWrapper wrapper = new NBTWrapper(item);
        NBTTagCompoundBuilder cosmicData = wrapper.getCompound("cosmicData");
        if (cosmicData == null) {
            return null;
        }
        List<ArmorSetType> crystals = new ArrayList<>();
        if (cosmicData.hasKey("crystalBonus")) {
            String upgrade = cosmicData.getString("crystalBonus");
            if (upgrade == null) {
                return null;
            }
            ArmorSetType from = armorSetTypeFromName(upgrade);
            if (from != null) {
                crystals.add(from);
            }
        } else if (cosmicData.hasKey("crystalBonuses")) {
            NBTTagListBuilder list = new NBTTagListBuilder(cosmicData.getNBTBase("crystalBonuses"));
            for (int i = 0; i < list.size(); ++i) {
                ArmorSetType from2;
                String bonus = list.getStringAt(i);
                if (bonus == null || bonus.isEmpty() || (from2 = armorSetTypeFromName(bonus)) == null) continue;
                crystals.add(from2);
            }
        }
        return crystals;
    }


    public static ItemStack createCrystal(List<ArmorSetType> armorSet, double applyChance) {
        return (new CustomItemBuilder("armorCrystal", armorSet, applyChance)).build();
    }



    public static ArmorSetType armorSetTypeFromName(String name) {
        for (ArmorSetType set : ArmorSetType.values()) {
            if (!set.name().equalsIgnoreCase(name)) continue;
            return set;
        }
        return null;
    }
}
