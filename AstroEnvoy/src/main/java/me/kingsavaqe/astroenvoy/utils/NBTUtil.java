package me.kingsavaqe.astroenvoy.utils;


import java.lang.reflect.Field;
import java.lang.reflect.Method;

import me.kingsavaqe.astroenvoy.utils.version.NMSUtil;
import org.bukkit.inventory.ItemStack;

public class NBTUtil {
    private static final Class<?> CraftItemStackClass = NMSUtil.getCraftClass("inventory.CraftItemStack");

    private static final Class<?> NBTTagCompoundClass = NMSUtil.getNMSClass("NBTTagCompound");

    private static final Class<?> NBTTagStringClass = NMSUtil.getNMSClass("NBTTagString");

    private static final Class<?> NBTBaseClass = NMSUtil.getNMSClass("NBTBase");

    public static ItemStack addTag(ItemStack item, String tag, String data) {
        try {
            Object NMSItemStack = CraftItemStackClass.getMethod("asNMSCopy", new Class[] { ItemStack.class }).invoke(null, item);
            Field tagField = NMSItemStack.getClass().getDeclaredField("tag");
            tagField.setAccessible(true);
            Object NBTTagCompound = tagField.get(NMSItemStack);
            if (NBTTagCompound == null)
                NBTTagCompound = NBTTagCompoundClass.newInstance();
            Object NBTTagString = NBTTagStringClass.getConstructor(new Class[] { String.class }).newInstance(data);
            Method setMethod = NBTTagCompoundClass.getMethod("set", String.class, NBTBaseClass);
            setMethod.setAccessible(true);
            setMethod.invoke(NBTTagCompound, tag, NBTTagString);
            tagField.set(NMSItemStack, NBTTagCompound);
            Object object1 = CraftItemStackClass.getMethod("asBukkitCopy", new Class[] { NMSItemStack.getClass() }).invoke(null, NMSItemStack);
            return (ItemStack)object1;
        } catch (IllegalAccessException|IllegalArgumentException|java.lang.reflect.InvocationTargetException|NoSuchMethodException|SecurityException|NoSuchFieldException|InstantiationException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getTagString(ItemStack item, String tag) {
        try {
            Object NMSItemStack = CraftItemStackClass.getMethod("asNMSCopy", new Class[] { ItemStack.class }).invoke(null, item);
            Field tagField = NMSItemStack.getClass().getDeclaredField("tag");
            tagField.setAccessible(true);
            Object NBTTagCompound = tagField.get(NMSItemStack);
            if (NBTTagCompound == null)
                return null;
            Method getStringMethod = NBTTagCompound.getClass().getMethod("getString", String.class);
            getStringMethod.setAccessible(true);
            return (String)getStringMethod.invoke(NBTTagCompound, new Object[] { tag });
        } catch (IllegalAccessException|IllegalArgumentException|java.lang.reflect.InvocationTargetException|NoSuchMethodException|SecurityException|NoSuchFieldException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean hasTagString(ItemStack item, String tag) {
        String data = getTagString(item, tag);
        return (data != null && data != "");
    }

    public static boolean hasTagString(ItemStack item, String tag, String searchData) {
        String data = getTagString(item, tag);
        return (data != null && data.equals(searchData));
    }

    public static String getTagString(Object compound, String tag) {
        try {
            if (compound == null)
                return null;
            Method getStringMethod = compound.getClass().getMethod("getString", String.class);
            getStringMethod.setAccessible(true);
            return (String)getStringMethod.invoke(compound, new Object[] { tag });
        } catch (IllegalAccessException|IllegalArgumentException|java.lang.reflect.InvocationTargetException|NoSuchMethodException|SecurityException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean hasTagString(Object compound, String tag) {
        String data = getTagString(compound, tag);
        return (data != null && data != "");
    }

    public static boolean hasTagString(Object compound, String tag, String searchData) {
        String data = getTagString(compound, tag);
        return (data != null && data.equals(searchData));
    }

    public static short getTagShort(Object compound, String tag) {
        try {
            if (compound == null)
                return 0;
            Method getShortMethod = compound.getClass().getMethod("getShort", String.class);
            getShortMethod.setAccessible(true);
            return ((Short)getShortMethod.invoke(compound, new Object[] { tag })).shortValue();
        } catch (IllegalAccessException|IllegalArgumentException|java.lang.reflect.InvocationTargetException|NoSuchMethodException|SecurityException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static boolean hasTagShort(Object compound, String tag) {
        return (getTagShort(compound, tag) != 0);
    }

    public static boolean hasTagShort(Object compound, String tag, short searchData) {
        return (getTagShort(compound, tag) == searchData);
    }

    public static Object getCompound(ItemStack item, String compound) {
        try {
            Object NMSItemStack = CraftItemStackClass.getMethod("asNMSCopy", new Class[] { ItemStack.class }).invoke(null, item);
            Field tagField = NMSItemStack.getClass().getDeclaredField("tag");
            tagField.setAccessible(true);
            Object NBTTagCompound = tagField.get(NMSItemStack);
            if (NBTTagCompound == null)
                return null;
            Method getCompoundMethod = NBTTagCompound.getClass().getMethod("getCompound", String.class);
            getCompoundMethod.setAccessible(true);
            return getCompoundMethod.invoke(NBTTagCompound, compound);
        } catch (IllegalAccessException|IllegalArgumentException|java.lang.reflect.InvocationTargetException|NoSuchMethodException|SecurityException|NoSuchFieldException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean hasCompound(ItemStack item, String compound) {
        return (getCompound(item, compound) != null);
    }
}
