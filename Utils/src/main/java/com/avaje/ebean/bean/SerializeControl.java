/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.bean;

public class SerializeControl {
    private static final String BEANS = "com.avaje.ebean.vanillabeans";
    private static final String COLLECTIONS = "com.avaje.ebean.vanillacollections";
    private static ThreadLocal<Boolean> vanillaBeans = new ThreadLocal<Boolean>(){

        @Override
        protected synchronized Boolean initialValue() {
            return SerializeControl.getDefault(SerializeControl.BEANS, Boolean.TRUE);
        }
    };
    private static ThreadLocal<Boolean> vanillaCollections = new ThreadLocal<Boolean>(){

        @Override
        protected synchronized Boolean initialValue() {
            return SerializeControl.getDefault(SerializeControl.COLLECTIONS, Boolean.TRUE);
        }
    };

    private static Boolean getDefault(String key, Boolean dflt) {
        String val = System.getProperty(key);
        if (val != null) {
            return val.equalsIgnoreCase("true");
        }
        return dflt;
    }

    public static void setDefaultForBeans(boolean vanillaOn) {
        Boolean b = vanillaOn;
        System.setProperty(BEANS, b.toString());
    }

    public static void setDefaultForCollections(boolean vanillaOn) {
        Boolean b = vanillaOn;
        System.setProperty(COLLECTIONS, b.toString());
    }

    public static void resetToDefault() {
        Boolean beans = SerializeControl.getDefault(BEANS, Boolean.FALSE);
        SerializeControl.setVanillaBeans(beans);
        Boolean coll = SerializeControl.getDefault(COLLECTIONS, Boolean.FALSE);
        SerializeControl.setVanillaCollections(coll);
    }

    public static void setVanilla(boolean vanillaOn) {
        if (vanillaOn) {
            vanillaBeans.set(Boolean.TRUE);
            vanillaCollections.set(Boolean.TRUE);
        } else {
            vanillaBeans.set(Boolean.FALSE);
            vanillaCollections.set(Boolean.FALSE);
        }
    }

    public static boolean isVanillaBeans() {
        return vanillaBeans.get();
    }

    public static void setVanillaBeans(boolean vanillaOn) {
        vanillaBeans.set(vanillaOn);
    }

    public static boolean isVanillaCollections() {
        return vanillaCollections.get();
    }

    public static void setVanillaCollections(boolean vanillaOn) {
        vanillaCollections.set(vanillaOn);
    }
}

