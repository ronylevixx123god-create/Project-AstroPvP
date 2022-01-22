package net.astropvp.armorsets;

import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.nbt.NBTTagListBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.GeneralUtil;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import com.google.common.collect.Lists;
import me.ifamasssxd.cosmicskycustoms.struct.CustomItemBuilder;
import net.astropvp.armorsets.struct.ArmorSetType;
import net.astropvp.armorsets.struct.cache.ArmorSetCache;
import net.astropvp.armorsets.struct.heroicArmor.HeroicArmor;
import net.astropvp.core.AstroCore;
import net.astropvp.core.utils.cache.CacheManager;
import net.astropvp.core.utils.cache.CachedPlayer;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

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

    public static ItemStack convertItemToHeroic(ItemStack itemStack) {
        if (GeneralUtil.isWeapon(itemStack)) {
            return convertWeaponToHeroic(Objects.requireNonNull(HeroicArmor.getFromMaterial(itemStack.getType())), itemStack);
        } else {
            if (GeneralUtil.isArmor(itemStack)) {
                return convertArmorToHeroic(itemStack, Objects.requireNonNull(HeroicArmor.getFromMaterial(itemStack.getType())));

            }
        }
        return null;
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

            if (hasCrystalsApplied(item)) {
                List<ArmorSetType> crystals = AstroArmorSetsAPI.getCrystalsFromItem(item);
                for (ArmorSetType type : crystals) {
                    cache.addArmorCrystal(type);
                }
            }

            if (isHeroic(item)) {
                cache.addHeroicArmor(item);
            }
        }
    }

    public static void refreshArmorPiece(ItemStack newArmorPiece, ItemStack oldArmorPiece, Player player) {
        CachedPlayer armorSetPlayer = AstroCore.getInstance().getCacheManager().getCachedPlayer(player);
        ArmorSetCache cache = (ArmorSetCache) ((ArmorSetCache) (armorSetPlayer.hasCache("armor_set") ? armorSetPlayer.getCache("armor_set") : armorSetPlayer.registerCache(new ArmorSetCache(player))));
        if (hasSetTypeOnItem(oldArmorPiece)) {
            cache.removeArmorSet(getSetTypeOnItem(oldArmorPiece));
        }
        if (hasSetTypeOnItem(newArmorPiece)) {
            cache.addArmorSet(getSetTypeOnItem(newArmorPiece));
        }
        if (hasCrystalsApplied(oldArmorPiece)) {
            List<ArmorSetType> crystals = AstroArmorSetsAPI.getCrystalsFromItem(oldArmorPiece);
            for (ArmorSetType type : crystals) {
                cache.removeArmorCrystal(type);
            }
        }
        if (hasCrystalsApplied(newArmorPiece)) {
            List<ArmorSetType> crystals = AstroArmorSetsAPI.getCrystalsFromItem(newArmorPiece);
            for (ArmorSetType type : crystals) {
                cache.addArmorCrystal(type);
            }
        }


        if (isHeroic(oldArmorPiece)) {
            cache.removeHeroicArmor(oldArmorPiece);
        }
        if (isHeroic(newArmorPiece)) {
            cache.addHeroicArmor(newArmorPiece);
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
                if (bonus == null || bonus.isEmpty() || (from2 = AstroArmorSetsAPI.armorSetTypeFromName(bonus)) == null)
                    continue;
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
        NBTTagCompoundBuilder cosmicData = wrapper.getCompound("cosmicData");
        if (cosmicData == null) return false;
        return cosmicData.hasKey("heroicArmor");

    }

    public static boolean hasCrystalsApplied(ItemStack item) {
        if (item == null) return false;
        if (getCrystalsFromItem(item) != null) {
            return !getCrystalsFromItem(item).isEmpty();
        }
        return false;
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

    public static ItemStack convertArmorToHeroic(ItemStack item, HeroicArmor type) {
        item.setType(type.getType());
        double modifier = type.getReduction();

        NBTWrapper wrapper = new NBTWrapper(item);
        NBTTagCompoundBuilder cosmicData = wrapper.getCompoundOrCreate("cosmicData");
        cosmicData.setBoolean("heroicArmor", true);

        ItemStack finished = wrapper.build();
        ItemMeta im = finished.getItemMeta();
        String strongerLine;
        List<String> lore = im.hasLore() ? im.getLore() : new ArrayList<>();
        if (!lore.contains(strongerLine = CC.DarkRed + "This armor is stronger than diamond.")) {
            lore.add("");
            lore.add(CC.Gray + "+" + modifier + " Armor Value");
            lore.add(strongerLine);
        }
        im.setLore(lore);

        if (im instanceof LeatherArmorMeta) {
            ((LeatherArmorMeta) im).setColor(Color.RED);
        }
        item.setItemMeta(im);
        return item;
    }

    public static ItemStack convertWeaponToHeroic(HeroicArmor type, ItemStack item) {
        item.setType(type.getType());
        ItemMeta im = item.getItemMeta();
        List<String> existing = im.hasLore() ? im.getLore() : new ArrayList<>();
        String strongerLine;

        if (!existing.contains(strongerLine = CC.DarkRed + "This weapon is stronger than diamond.")) {
            existing.add("");
            existing.add(CC.Gray + "+" + type.getIncrease() + " Bonus Attack Damage");
            existing.add(strongerLine);
        }
        im.setLore(existing);
        item.setItemMeta(im);

        return item;
    }

    public static ItemStack createArmorSetUpgrade(ArmorSetType armorset, double successRate) {

        String armorLore = armorset == null ? CC.Gray + "armor set" : armorset.getDisplay() + CC.Gray + " armor";

        return new NBTWrapper(new ItemBuilder(Material.INK_SACK, CC.GoldB + "Heroic " + (armorset != null ? CC.Gold + "(" + armorset.getDisplay() + CC.Gold + ") " + CC.GoldB : "Armor") + "Upgrade", "", CC.Gray + "Apply to any " + armorLore, CC.Gray + "for a " + CC.Gold + (int) successRate + "%" + CC.Gray + " chance to imbue it", CC.Gray + "with the power of Heroic Armor!", "", CC.Gray + "This will increase the armor's base stats.").data(DyeColor.YELLOW.getDyeData()).build()).setString("cosmicType", "HEROIC_ARMOR_UPGRADE").setNBTBase("cosmicData", new NBTTagCompoundBuilder().setDouble("success", successRate).setString("setType", armorset == null ? "ANY" : armorset.name()).get()).build();
    }


    public static ArmorSetType armorSetTypeFromName(String name) {
        for (ArmorSetType set : ArmorSetType.values()) {
            if (!set.name().equalsIgnoreCase(name)) continue;
            return set;
        }
        return null;
    }
}
