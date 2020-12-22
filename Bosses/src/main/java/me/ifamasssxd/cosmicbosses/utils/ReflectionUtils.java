/*
 * Decompiled with CFR 0.150.
 */
package me.ifamasssxd.cosmicbosses.utils;

import java.lang.reflect.Field;

public class ReflectionUtils {
    public static void setNull(Object object, String fieldName) {
        try {
            Field f = object.getClass().getDeclaredField(fieldName);
            f.setAccessible(true);
            System.out.println("Value Before: " + f.get(object));
            f.set(object, null);
            System.out.println("Value: " + f.get(object));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setObject(Class<?> clazz, Object instance, Object objectToSet, String fieldName) {
        try {
            Field f = clazz.getDeclaredField(fieldName);
            f.setAccessible(true);
            f.set(instance, objectToSet);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object getObject(Class<?> clazz, Object instance, String fieldName) {
        try {
            Field f = clazz.getDeclaredField(fieldName);
            f.setAccessible(true);
            return f.get(instance);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object getObject(Class<?> clazz, String fieldName) {
        try {
            Field f = clazz.getDeclaredField(fieldName);
            f.setAccessible(true);
            return f.get(null);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

