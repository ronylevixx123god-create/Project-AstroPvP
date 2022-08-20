package me.ily2bb.slotbot;

import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import de.tr7zw.nbtapi.NBTItem;
import de.tr7zw.nbtapi.plugin.NBTAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import me.ily2bb.slotbot.utils.Utils;
import net.astropvp.loottable.LootTableAPI;
import net.astropvp.loottable.struct.LootTableType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CosmicSlotBotAPI {
    public static ItemStack createSlotBotItem() {
        final ItemStack item = new ItemStack(Material.PAPER);
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(CC.GoldB + "Astro" + CC.GoldB + " Slot Bot " + CC.WhiteB + "Ticket");
        final List<String> lore = Arrays.asList(CC.Gray + "Right-Click the Slot Bot located", String.valueOf(CC.Gray) + "at " + CC.White + "/spawn " + CC.Gray + "or use /bot then", String.valueOf(CC.Gray) + "insert (Left-Click) this ticket", String.valueOf(CC.Gray) + "into the bot");
        meta.setLore((List) lore);
        item.setItemMeta(meta);
        return Utils.addSkin("SLOT_BOT_TICKET", item);
    }


    public static ItemStack getRandomSlotBotLootItem() {
        return LootTableAPI.getRandomLootItem(LootTableType.SLOTS);
    }



    public static boolean isSlotbotTicket(ItemStack item) {
        List<String> skins = new ArrayList<>();
        if (item == null || item.getType() == Material.AIR || !item.hasItemMeta() || item.getType() != Material.PAPER)
            return false;
        NBTWrapper wrapper = new NBTWrapper(item);
        NBTTagCompoundBuilder cosmicData = wrapper.hasKey("cosmicData") ? new NBTTagCompoundBuilder(wrapper.getNBTBase("cosmicData")) : new NBTTagCompoundBuilder();
        if (!cosmicData.hasKey("itemSkinType"))
            return false;
        List<String> stringSkins = new ArrayList<>(Arrays.asList(cosmicData.getString("itemSkinType").split(",")));
        for (String string : stringSkins) {
            string = string.replace("[", "");
            string = string.replace("]", "");
            string = string.replace(" ", "");
            try {
                skins.add(string);
            } catch (Exception ignored) {

            }
        }
        return skins.contains("SLOT_BOT_TICKET");
    }

    public static List<ItemStack> getSlotBotItems() {
        return LootTableAPI.getAllLootFromLootTable(LootTableType.SLOTS);

    }


    public static int getSlotsInInventory(Player p) {
        int i=0;
        for (ItemStack itemStack : p.getInventory()) {
            if (isSlotbotTicket(itemStack)) {
                i+=itemStack.getAmount();
            }
        }
        return i;
    }
}
