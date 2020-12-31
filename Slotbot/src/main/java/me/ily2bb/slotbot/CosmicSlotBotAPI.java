package me.ily2bb.slotbot;

import org.bukkit.inventory.*;
import org.bukkit.*;
import com.cosmicpvp.cosmicutils.utils.*;
import java.util.*;
import org.bukkit.inventory.meta.*;

public class CosmicSlotBotAPI
{
    public static ItemStack createSlotBotItem() {
        final ItemStack item = new ItemStack(Material.PAPER);
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(CC.GreenB + "Astro" + CC.YellowB + "-" + CC.GreenB + "Slot Bot " + CC.YellowB + "Ticket");
        final List<String> lore = Arrays.asList(CC.Gray + "Right-Click the Slot Bot located", String.valueOf(CC.Gray) + "at " + CC.White + "/spawn " + CC.Gray + "or use /bot then", String.valueOf(CC.Gray) + "insert (Left-Click) this ticket", String.valueOf(CC.Gray) + "into the bot");
        meta.setLore((List)lore);
        item.setItemMeta(meta);
        return item;
    }
}
