package net.astropvp.items;

import com.cosmicpvp.cosmicutils.utils.CC;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class SandBotItem {
    public static ItemStack get() {
        ItemStack item = new ItemStack(Material.MONSTER_EGG);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(CC.YellowB + "Sand Bot");
        List<String> lore = new ArrayList<>();
        lore.add("aaa");
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
}
