package me.bryside.astrorevive.Utils;

import me.bryside.astrorevive.inventories.DeathInventory;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class SortMapByValue {
    public static boolean ASC = true;
    public static boolean DESC = false;

    public static Map<DeathInventory, Long> sortByComparator(Map<DeathInventory, Long> unsortMap, final boolean order) {
        List<Map.Entry<DeathInventory, Long>> list = new LinkedList<>(unsortMap.entrySet());


        Collections.sort(list, new Comparator<Map.Entry<DeathInventory, Long>>() {
            public int compare(Map.Entry<DeathInventory, Long> o1, Map.Entry<DeathInventory, Long> o2) {
                if (order) {
                    return ((Long) o1.getValue()).compareTo(o2.getValue());
                }
                return ((Long) o2.getValue()).compareTo(o1.getValue());
            }
        });


        Map<DeathInventory, Long> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<DeathInventory, Long> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

    public static void printMap(Map<DeathInventory, Long> map) {
        for (Map.Entry<DeathInventory, Long> entry : map.entrySet())
            System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
    }
}