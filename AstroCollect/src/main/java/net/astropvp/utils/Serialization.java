package net.astropvp.utils;

import net.astropvp.struct.CollectCategory;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Serialization {
    public static String serialize(List<ItemStack> items) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            BukkitObjectOutputStream output = new BukkitObjectOutputStream(os);
            output.writeInt(items.size());
            for(ItemStack item : items) {
                output.writeObject(item);
            }
            output.close();
            return Base64Coder.encodeLines(os.toByteArray());
        } catch(IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<ItemStack> deserialize(String string) {
        ByteArrayInputStream is = new ByteArrayInputStream(Base64Coder.decodeLines(string));
        try {
            BukkitObjectInputStream in = new BukkitObjectInputStream(is);
            List<ItemStack> list = new ArrayList<>();
            for (int i = 0; i < in.readInt(); i++) {
                list.add((ItemStack) in.readObject());
            }
            in.close();
            return list;
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
