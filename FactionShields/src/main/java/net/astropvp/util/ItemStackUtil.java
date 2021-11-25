// 
// Decompiled by Procyon v0.5.36
// 

package net.astropvp.util;

import org.bukkit.Bukkit;
import java.util.HashMap;
import java.lang.reflect.InvocationTargetException;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.ItemStack;
import java.util.Map;
import java.lang.reflect.Method;

public class ItemStackUtil
{
    private static final Class ITEM_META_DESERIALIZATOR;
    private static final Method DESERIALIZE;
    
    public static ItemStack deserialize(final Map<String, Object> map) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        final ItemStack i = ItemStack.deserialize((Map)map);
        if (map.containsKey("meta")) {
            try {
                if (ItemStackUtil.ITEM_META_DESERIALIZATOR != null) {
                    final ItemMeta im = (ItemMeta)ItemStackUtil.DESERIALIZE.invoke(i, map.get("meta"));
                    i.setItemMeta(im);
                }
            }
            catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                throw e;
            }
        }
        return i;
    }
    
    public static Map<String, Object> serialize(final ItemStack item) {
        final HashMap<String, Object> itemDocument = new HashMap<String, Object>(item.serialize());
        if (item.hasItemMeta()) {
            itemDocument.put("meta", new HashMap<>(item.getItemMeta().serialize()));
        }
        return itemDocument;
    }
    
    public static Class<?> getOBCClass(final String className) {
        final String fullName = "org.bukkit.craftbukkit." + getVersion() + className;
        Class<?> clazz = null;
        try {
            clazz = Class.forName(fullName);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return clazz;
    }
    
    private static String getVersion() {
        final String name = Bukkit.getServer().getClass().getPackage().getName();
        final String version = name.substring(name.lastIndexOf(46) + 1) + ".";
        return version;
    }
    
    private static Method getDeserialize() {
        try {
            return ItemStackUtil.ITEM_META_DESERIALIZATOR.getMethod("deserialize", Map.class);
        }
        catch (NoSuchMethodException | SecurityException ex3) {
            return null;
        }
    }
    
    static {
        ITEM_META_DESERIALIZATOR = getOBCClass("inventory.CraftMetaItem").getClasses()[0];
        DESERIALIZE = getDeserialize();
    }
}
