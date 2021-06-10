package com.drtshock.playervaults.vaultmanagement;

import org.yaml.snakeyaml.external.biz.base64Coder.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
import org.bukkit.util.io.*;
import java.io.*;

public class Base64Serialization
{
    public static String toBase64(final Inventory inventory) {
        try {
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            final BukkitObjectOutputStream bukkitObjectOutputStream = new BukkitObjectOutputStream((OutputStream)byteArrayOutputStream);
            bukkitObjectOutputStream.writeInt(inventory.getSize());
            for (int i = 0; i < inventory.getSize(); ++i) {
                bukkitObjectOutputStream.writeObject((Object)inventory.getItem(i));
            }
            bukkitObjectOutputStream.close();
            return Base64Coder.encodeLines(byteArrayOutputStream.toByteArray());
        }
        catch (Exception cause) {
            throw new IllegalStateException("Cannot into itemstacksz!", cause);
        }
    }
    
    public static String toBase64(final ItemStack[] contents, final int n) {
        final Inventory inventory = Bukkit.createInventory((InventoryHolder)null, n);
        inventory.setContents(contents);
        return toBase64(inventory);
    }
    
    public static Inventory fromBase64(final String s) {
        try {
            final BukkitObjectInputStream bukkitObjectInputStream = new BukkitObjectInputStream((InputStream)new ByteArrayInputStream(Base64Coder.decodeLines(s)));
            final Inventory inventory = Bukkit.getServer().createInventory((InventoryHolder)null, bukkitObjectInputStream.readInt());
            for (int i = 0; i < inventory.getSize(); ++i) {
                inventory.setItem(i, (ItemStack)bukkitObjectInputStream.readObject());
            }
            bukkitObjectInputStream.close();
            return inventory;
        }
        catch (Exception ex) {
            return null;
        }
    }
}
