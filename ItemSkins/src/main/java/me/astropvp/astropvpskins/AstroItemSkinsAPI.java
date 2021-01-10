package me.astropvp.astropvpskins;

import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;

import java.util.Map;

import me.astropvp.astropvpskins.objects.skins.ItemSkinCacher;
import me.astropvp.astropvpskins.enums.SkinType;

import me.astropvp.astropvpskins.utils.Utils;
import org.arkhamnetwork.enchantments.util.ArmorSlot;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


public class AstroItemSkinsAPI {
    public static SkinType getSkinTypeFromNBT(ItemStack item) {
        if (item == null || item.getType() == Material.AIR) {
            return null;
        }
        NBTWrapper wrapper = new NBTWrapper(item);
        NBTTagCompoundBuilder compoundBuilder = wrapper.hasKey("conaxData") ?
                new NBTTagCompoundBuilder(wrapper.getNBTBase("conaxData")) :
                new NBTTagCompoundBuilder();
        String l = compoundBuilder.getString("itemSkinType");
        if (l == null)
            return null;
        byte b;
        int i;
        SkinType[] arrayOfSkinType;
        for (i = (arrayOfSkinType = SkinType.values()).length, b = 0; b < i; ) {
            SkinType type = arrayOfSkinType[b];
            if (l.contentEquals(type.name()))
                return type;
            b++;
        }

        return null;
    }

    public static boolean hasSkinEquipped(Player player, SkinType type) {
        ItemSkinCacher cacher = ItemSkinCacher.getItemCacher(player);
        if (cacher == null) {
            return false;
        }
        Map<ArmorSlot, SkinType> types = cacher.getSkinTypes();
        return types.values().contains(type);
    }

    public static ItemStack addSkinToItem(ItemStack item, SkinType type) {
        item = Utils.addSkinNBT(type.name(), item);
        item = Utils.addSkinLore(type, item);
        return item;
    }

    public static ItemStack removeSkinFromItem(SkinType type, ItemStack item) {
        item = Utils.removeSkinLore(item);
        item = Utils.removeSkinNBT(type.name(), item);
        return item;
    }

    public static boolean isInInventory(Player player, SkinType type) {
        for (int i = 0; i < player.getInventory().getSize(); i++) {
            ItemStack item = player.getInventory().getItem(i);
            if (item != null && item.getType() != Material.AIR) {

                if (type == getSkinTypeFromNBT(item))
                    return true;
            }
        }
        return false;
    }

    public static boolean isInHand(Player player, SkinType type) {
        ItemStack item = player.getItemInHand();
        if (item == null || item.getType() == Material.AIR) {
            return false;
        }
        return (type == getSkinTypeFromNBT(item));
    }
}

