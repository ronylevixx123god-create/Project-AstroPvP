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
    public static String serializeItemStack(ItemStack itemStack) {
        net.minecraft.server.v1_8_R3.ItemStack netItemStack = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound tag = new NBTTagCompound();
        netItemStack.save(tag);
        return tag.toString();
    }

    public static ItemStack deserializeItemStack(String rawItem) throws MojangsonParseException {
        if (rawItem == null || rawItem.equals("empty")) {
            return null;
        }
        NBTTagCompound compound = MojangsonParser.parse(rawItem);
        net.minecraft.server.v1_8_R3.ItemStack netItemStack = net.minecraft.server.v1_8_R3.ItemStack.createStack(compound);
        return CraftItemStack.asBukkitCopy(netItemStack);
    }
}

