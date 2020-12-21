/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.core;

import java.util.HashMap;

public final class InternString {
    private static HashMap<String, String> map = new HashMap();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static String intern(String s) {
        if (s == null) {
            return null;
        }
        HashMap<String, String> hashMap = map;
        synchronized (hashMap) {
            String v = map.get(s);
            if (v != null) {
                return v;
            }
            map.put(s, s);
            return s;
        }
    }
}

