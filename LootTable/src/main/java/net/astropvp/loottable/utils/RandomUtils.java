package net.astropvp.loottable.utils;

import com.google.common.util.concurrent.AtomicDouble;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class RandomUtils {
    public static ItemStack getRandomItem(Map<ItemStack, Double> itemsMap) {

        double maxValue = 0;
        for(Double value : itemsMap.values()) {
            maxValue = maxValue+value;
        }

        double currentValue = 0;
        double randomDouble = ThreadLocalRandom.current().nextDouble(maxValue);

        for(Map.Entry<ItemStack, Double> entry : sort(itemsMap).entrySet()) {
            if(randomDouble > currentValue && randomDouble < currentValue + entry.getValue()) {
                return entry.getKey();
            }
            currentValue = currentValue + entry.getValue();
        }
        return null;
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sort(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());
        Map<K, V> result = new LinkedHashMap<>();
        for (int i = list.size() - 1; i >= 0; i--) {
            Map.Entry<K, V> entry = list.get(i);
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
}
