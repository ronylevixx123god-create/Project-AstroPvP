package me.astropvp.astropvpskins.utils;

import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;

import java.util.ArrayList;
import java.util.List;

import me.astropvp.astropvpskins.enums.SkinType;
import net.minecraft.server.v1_8_R3.NBTBase;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Utils {
    public static boolean isInventoryFull(Player player) {
        byte b;
        int i;
        ItemStack[] arrayOfItemStack;
        for (i = (arrayOfItemStack = player.getInventory().getContents()).length, b = 0; b < i; ) {
            ItemStack x = arrayOfItemStack[b];
            if (x == null)
                return false;
            b++;
        }

        return true;
    }

    public static ItemStack addSkinNBT(String line, ItemStack item) {
        ItemStack finalItem = item;
        NBTWrapper wrapper = new NBTWrapper(item);
        NBTTagCompoundBuilder compoundBuilder = wrapper.hasKey("cosmicData") ?
                new NBTTagCompoundBuilder(wrapper.getNBTBase("cosmicData")) :
                new NBTTagCompoundBuilder();
        compoundBuilder.setString("itemSkinType", line);
        finalItem = wrapper.setNBTBase("cosmicData", (NBTBase) compoundBuilder.get()).build();
        return finalItem;
    }

    public static ItemStack addSkinLore(SkinType type, ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<>();
        lore.add(type.getAppliedLore());
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack removeSkinNBT(String line, ItemStack item) {
        ItemStack finalItem = item;
        NBTWrapper wrapper = new NBTWrapper(item);
        if (wrapper.hasKey("cosmicData")) {
            NBTTagCompoundBuilder builder = new NBTTagCompoundBuilder(wrapper.getNBTBase("cosmicData"));
            builder.remove("itemSkinType");
            finalItem = wrapper.setNBTBase("cosmicData", (NBTBase) builder.get()).build();
        }
        return finalItem;
    }

    public static ItemStack removeSkinLore(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<>();
        for (int i = lore.size() - 1; i >= 0; i--) {
            String l = lore.get(i);
            if (l.startsWith(String.valueOf(CC.WhiteB) + "ITEM SKIN:")) {
                lore.remove(i);
            }
        }
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
}
