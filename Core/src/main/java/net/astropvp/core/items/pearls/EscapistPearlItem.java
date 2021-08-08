package net.astropvp.core.items.pearls;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import com.google.common.collect.Lists;
import java.util.List;

import net.astropvp.core.utils.ItemUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EscapistPearlItem {
    public static ItemStack getItem() {
        ItemStack item = new ItemStack(Material.ENDER_PEARL);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(String.valueOf(CC.GoldB) + "Escapist Pearl");
        List<String> lore = Lists.newArrayList();
        lore.add(String.valueOf(CC.Yellow) + "An elusive teleportation pearl");
        lore.add(String.valueOf(CC.Yellow) + "Capable of phasing through:");
        lore.add(String.valueOf(CC.Gray) + " * Cobwebs");
        lore.add(String.valueOf(CC.Gray) + " * String");
        meta.setLore(lore);
        item.setItemMeta(meta);
        ItemUtils.addGlow(item);
        NBTWrapper wrapper = new NBTWrapper(item);
        wrapper.setString("cosmicType", "escapist_pearl");
        return wrapper.build();
    }

    public static boolean isEscapistPearl(ItemStack item) {
        if (item.getType() != Material.ENDER_PEARL || !item.hasItemMeta())
            return false;
        return new NBTWrapper(item).getString("cosmicType").equals("escapist_pearl");
    }
}

