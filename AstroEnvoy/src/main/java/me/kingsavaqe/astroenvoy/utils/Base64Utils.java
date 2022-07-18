package me.kingsavaqe.astroenvoy.utils;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.*;
import java.math.BigInteger;

public class Base64Utils {
    public static ItemStack deserialize(String base64) throws IOException, ClassNotFoundException {
        byte[] data = Base64Coder.decodeLines(base64);
        ByteArrayInputStream bytesIn = new ByteArrayInputStream(data);
        BukkitObjectInputStream in = new BukkitObjectInputStream(bytesIn);
        ItemStack itemStack = (ItemStack) in.readObject();
        in.close();
        return itemStack;
    }

    public static String serialize(ItemStack itemStack) throws IOException {
        ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
        BukkitObjectOutputStream out = new BukkitObjectOutputStream(bytesOut);
        out.writeObject(itemStack);
        out.flush();
        out.close();
        return Base64Coder.encodeLines(bytesOut.toByteArray());
    }
}

