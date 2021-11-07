package me.astropvp.astrospawners.utils;

import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import com.cosmicpvp.cosmicutils.utils.NumberUtils;
import me.recolance.auction.util.Item;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class MobCoinUtils {
    public static ItemStack createCoinsNote(String signer, int amount) {
        ItemBuilder itemBuilder = new ItemBuilder(Material.DOUBLE_PLANT);
        itemBuilder.addLoreLine("&6Value " + NumberUtils.formatMoney(amount));
        itemBuilder.addLoreLine("&6Signer " + signer);

        NBTWrapper wrapper = new NBTWrapper(itemBuilder.build());
        NBTTagCompoundBuilder builder = new NBTTagCompoundBuilder();
        builder.setInt("coinsAmount", amount);
        wrapper.setNBTBase("coinsData", builder.get());
        return wrapper.build();

    }

    public static boolean isCoinsNote(ItemStack itemStack) {
        if (itemStack.getType() == null || itemStack.getType() != Material.DOUBLE_PLANT) {
            NBTWrapper wrapper = new NBTWrapper(itemStack);
            if (!wrapper.hasKey("coinsData")) {
                return false;
            }
        }
        return true;

    }

    public static int getCoinsAmountFromNote(ItemStack itemStack) {
        if (itemStack.getType() == null || itemStack.getType() != Material.DOUBLE_PLANT) {
            NBTWrapper wrapper = new NBTWrapper(itemStack);
            if (!wrapper.hasKey("coinsData")) {
                return 0;
            }
            NBTTagCompoundBuilder compound = new NBTTagCompoundBuilder(wrapper.getNBTBase("coinsData"));
            return compound.getInt("coinsAmount");
        }
        return 0;
    }
}
