package me.ily2bb.slotbot.utils;

import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.yaml.snakeyaml.external.biz.base64Coder.*;
import org.bukkit.inventory.*;
import org.bukkit.util.io.*;
import java.io.*;
import com.cosmicpvp.cosmicutils.utils.*;
import com.cosmicpvp.cosmicutils.nbt.*;
import net.minecraft.server.v1_7_R4.*;
import org.bukkit.entity.*;
import me.ily2bb.slotbot.*;
import net.minecraft.util.io.netty.util.internal.*;
import java.util.*;
import me.ily2bb.slotbot.objects.*;
import org.bukkit.enchantments.*;
import org.bukkit.inventory.meta.*;
import org.apache.commons.lang.*;
import org.bukkit.*;

public class Utils
{
    public static String toBase64(final Inventory inventory, final int size) {
        try {
            final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            final BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream((OutputStream)outputStream);
            dataOutput.writeInt(size);
            for (int i = 0; i < inventory.getSize(); ++i) {
                dataOutput.writeObject((Object)inventory.getItem(i));
            }
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        }
        catch (Exception e) {
            throw new IllegalStateException("Cannot into itemstacksz!", e);
        }
    }
    
    public static String toBase64(final ItemStack[] is, final int size) {
        final Inventory inventory = Bukkit.createInventory((InventoryHolder)null, size);
        inventory.setContents(is);
        return toBase64(inventory, size);
    }
    
    public static Inventory fromBase64(final String data) {
        try {
            final ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            final BukkitObjectInputStream dataInput = new BukkitObjectInputStream((InputStream)inputStream);
            final Inventory inventory = Bukkit.getServer().createInventory((InventoryHolder)null, dataInput.readInt());
            for (int i = 0; i < inventory.getSize(); ++i) {
                inventory.setItem(i, (ItemStack)dataInput.readObject());
            }
            dataInput.close();
            return inventory;
        }
        catch (Exception ex) {
            return null;
        }
    }
    
    public static ItemStack addSkin(final String line, final ItemStack item) {
        ItemStack finalItem = item;
        final NBTWrapper wrapper = new NBTWrapper(item);
        final NBTTagCompoundBuilder compoundBuilder = wrapper.hasKey("cosmicData") ? new NBTTagCompoundBuilder(wrapper.getNBTBase("cosmicData")) : new NBTTagCompoundBuilder();
        compoundBuilder.setString("itemSkinType", line);
        finalItem = wrapper.setNBTBase("cosmicData", (NBTBase)compoundBuilder.get()).build();
        return finalItem;
    }
    
    public static boolean isInventoryFull(final Player player) {
        ItemStack[] contents;
        for (int length = (contents = player.getInventory().getContents()).length, i = 0; i < length; ++i) {
            final ItemStack item = contents[i];
            if (item == null) {
                return false;
            }
        }
        return true;
    }
    
    public static ItemStack getItem(final String line) {
        return fromBase64(line.split(":")[0]).getContents()[0];
    }
    
    public static int getPercentage(final String line) {
        final String[] split = line.split(":");
        return Integer.valueOf(split[split.length - 1]);
    }
    
    public static boolean isShowing(final String line) {
        final String[] split = line.split(":");
        String[] array;
        for (int length = (array = split).length, i = 0; i < length; ++i) {
            final String l = array[i];
            if (l.contentEquals("show")) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isBroadcasting(final String line) {
        final String[] split = line.split(":");
        String[] array;
        for (int length = (array = split).length, i = 0; i < length; ++i) {
            final String l = array[i];
            if (l.contentEquals("broadcast")) {
                return true;
            }
        }
        return false;
    }
    
    public static int getTotalPercentage() {
        final List<String> list = (List<String>)SlotBotPlugin.getInstance().getConfig().get("items");
        int total = 0;
        for (final String l : list) {
            total += getPercentage(l);
        }
        return total;
    }
    
    public static Object[] getMap() {
        final HashMap<Integer[], ItemStack> items = new HashMap<Integer[], ItemStack>();
        final List<String> list = (List<String>)SlotBotPlugin.getInstance().getConfig().get("items");
        int start = 0;
        for (final String l : list) {
            final int percentage = getPercentage(l);
            final ItemStack x = getItem(l);
            items.put(new Integer[] { start + 1, start + percentage }, x);
            start += percentage;
        }
        return new Object[] { items, start + 1 };
    }
    
    public static ItemStack getRandomItemFromLootTable(final Object[] objects) {
        final HashMap<Integer[], ItemStack> map = (HashMap<Integer[], ItemStack>)objects[0];
        final int random = ThreadLocalRandom.current().nextInt(1, (int)objects[1]);
        for (final Map.Entry<Integer[], ItemStack> entry : map.entrySet()) {
            final Integer[] values = entry.getKey();
            final int from = values[0];
            final int to = values[1];
            if (from <= random && to >= random) {
                return entry.getValue();
            }
        }
        return null;
    }
    
    public static ItemStack addGlow(final ItemStack item) {
        final ItemMeta meta = item.getItemMeta();
        meta.addEnchant((Enchantment)new Glow(70), 1, true);
        item.setItemMeta(meta);
        return item;
    }
    
    public static ItemStack removeGlow(final ItemStack item) {
        final ItemMeta meta = item.getItemMeta();
        if (meta.hasEnchant((Enchantment)new Glow(70))) {
            meta.removeEnchant((Enchantment)new Glow(70));
            item.setItemMeta(meta);
        }
        return item;
    }
    
    public static String getDisplayName(final ItemStack item) {
        String itemName = (item.hasItemMeta() && item.getItemMeta().hasDisplayName()) ? item.getItemMeta().getDisplayName() : StringUtils.capitaliseAllWords(item.getType().name().toLowerCase().replace("_", " "));
        if (item.getType() == Material.ENCHANTED_BOOK) {
            itemName = "Depth Strider Enchantment Book";
        }
        return itemName;
    }
}
