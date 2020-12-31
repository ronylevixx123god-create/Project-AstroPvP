package me.ily2bb.slotbot;

import com.cosmicpvp.cosmicutils.utils.CC;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class CosmicSlotBotAPI {
    public static ItemStack createSlotBotItem() {
        final ItemStack item = new ItemStack(Material.PAPER);
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(CC.GreenB + "Astro" + CC.YellowB + "-" + CC.GreenB + "Slot Bot " + CC.YellowB + "Ticket");
        final List<String> lore = Arrays.asList(CC.Gray + "Right-Click the Slot Bot located", String.valueOf(CC.Gray) + "at " + CC.White + "/spawn " + CC.Gray + "or use /bot then", String.valueOf(CC.Gray) + "insert (Left-Click) this ticket", String.valueOf(CC.Gray) + "into the bot");
        meta.setLore((List) lore);
        item.setItemMeta(meta);
        return item;
    }
}
