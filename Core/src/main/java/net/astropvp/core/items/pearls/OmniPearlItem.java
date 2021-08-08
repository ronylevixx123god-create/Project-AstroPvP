package net.astropvp.core.items.pearls;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import com.google.common.collect.Lists;
import net.astropvp.core.utils.ItemUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class OmniPearlItem {
    public static ItemStack getItem() {
        ItemStack item = new ItemStack(Material.ENDER_PEARL);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(CC.DarkAquaB + "Omni Pearl");
        List<String> lore = Lists.newArrayList();
        lore.add(String.valueOf(CC.Gray) + "An exclusive teleportation pearl");
        lore.add(String.valueOf(CC.Gray) + "which combines the following pearl");
        lore.add(String.valueOf(CC.Gray) + "abilities into one:");
        lore.add(String.valueOf(CC.DarkAqua) + " * Soul Pearl");
        lore.add(CC.DarkAqua + " * Escapist Pearl");
        lore.add(CC.DarkAqua + " * Super Pearl");
        meta.setLore(lore);
        item.setItemMeta(meta);
        ItemUtils.addGlow(item);
        NBTWrapper wrapper = new NBTWrapper(item);
        wrapper.setString("cosmicType", "omni_pearl");
        return wrapper.build();
    }

    public static boolean isOmniPearl(ItemStack item) {
        if (item.getType() != Material.ENDER_PEARL || !item.hasItemMeta())
            return false;
        return new NBTWrapper(item).getString("cosmicType").equals("omni_pearl");
    }
}
