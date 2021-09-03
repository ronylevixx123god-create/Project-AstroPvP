package me.ily2bb.slotbot;

import com.cosmicpvp.cosmicutils.utils.CC;
import me.ily2bb.slotbot.utils.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
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
        return Utils.addSkin("SLOT_BOT_TICKET", item);
    }


    public static ItemStack getRandomSlotBotLootItem(Player p) {
        final Object[] objects = Utils.getMap();
        final ItemStack item = Utils.getRandomItemFromLootTable(objects);
        return item;
    }
}
