/*
 * Decompiled with CFR 0.150.
 */
package me.ifamasssxd.cosmicbosses.utils;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.UUID;

public class MapUtils {
    public static LinkedHashMap<UUID, Integer> sortByComparator(HashMap<UUID, Integer> unsortMap, final boolean order, int amount) {
        if (unsortMap == null) {
            return null;
        }
        LinkedList<Map.Entry<UUID, Integer>> list = new LinkedList<Map.Entry<UUID, Integer>>(unsortMap.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<UUID, Integer>>(){

            @Override
            public int compare(Map.Entry<UUID, Integer> o1, Map.Entry<UUID, Integer> o2) {
                if (order) {
                    return o1.getValue().compareTo(o2.getValue());
                }
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        LinkedHashMap<UUID, Integer> sortedMap = new LinkedHashMap<UUID, Integer>();
        for (Map.Entry entry : list) {
            if (sortedMap.size() >= amount) break;
            sortedMap.put((UUID)entry.getKey(), (Integer)entry.getValue());
        }
        return sortedMap;
    }
}

