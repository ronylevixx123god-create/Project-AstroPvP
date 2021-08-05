package me.astropvp;

import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import me.astropvp.objects.BackpackType;
import me.astropvp.utils.BackpackUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.stream.Collectors;

public class AstroBackpacksAPI {

    public static boolean itemHasSkin(ItemStack item) {
        NBTWrapper wrapper = new NBTWrapper(item);
        if(!(wrapper.hasKey("cosmicData")))
            return false;
        if(wrapper.hasKey("cosmicType") && wrapper.getString("cosmicType").equals("backpack_skin"))
            return false;
        return(new NBTTagCompoundBuilder(wrapper.getNBTBase("cosmicData")).hasKey("backpackType"));
    }

    public static BackpackType getBackpackByItem(ItemStack item) {
        if(item == null || item.getType() == Material.AIR)
            return null;
        NBTWrapper wrapper = new NBTWrapper(item);
        NBTTagCompoundBuilder cosmicData = wrapper.hasKey("cosmicData") ? new NBTTagCompoundBuilder(wrapper.getNBTBase("cosmicData")) : new NBTTagCompoundBuilder();
        return BackpackUtils.getBackpackTypeFromID(cosmicData.getString("backpackType"));
    }

    public static boolean isBackpack(ItemStack item) {
        if(item == null || item.getType() == Material.AIR)
            return false;
        NBTWrapper wrapper = new NBTWrapper(item);
        if(!(wrapper.hasKey("cosmicType")))
            return false;
        return wrapper.getString("cosmicType").equals("backpack_skin");
    }

    public static ItemStack removeBackpack(ItemStack item, BackpackType type) {
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        lore = lore.stream().filter(s -> !ChatColor.stripColor(s).startsWith("BACKPACK SKIN:")).collect(Collectors.toList());
        meta.setLore(lore);
        item.setItemMeta(meta);
        NBTWrapper wrapper = new NBTWrapper(item);
        NBTTagCompoundBuilder cosmicData = wrapper.hasKey("cosmicData") ? new NBTTagCompoundBuilder(wrapper.getNBTBase("cosmicData")) : new NBTTagCompoundBuilder();
        cosmicData.remove("backpackType");
        wrapper.setNBTBase("cosmicData", cosmicData.get());
        return wrapper.build();
    }

    public static void refreshBackpack(Player player) {
        PlayerInventory inventory = player.getInventory();
        if(inventory.getChestplate() != null && inventory.getChestplate().getType() != Material.AIR && itemHasSkin(inventory.getChestplate())) {
            getBackpackByItem(inventory.getChestplate()).getInstance().onEquip(player);
            player.setMaxHealth(20.0);
        }
    }
}
