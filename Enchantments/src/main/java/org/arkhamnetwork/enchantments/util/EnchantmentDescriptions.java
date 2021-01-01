package org.arkhamnetwork.enchantments.util;

import org.arkhamnetwork.enchantments.*;
import org.bukkit.*;
import java.util.*;
import com.rit.sucy.*;

public class EnchantmentDescriptions
{
    private static HashMap<String, String> descriptions;
    
    private static void loadConfig() {
        EnchantmentDescriptions.descriptions = new HashMap<String, String>();
        for (final String s : Enchantments.getInstance().getConfig().getStringList("descriptions")) {
            if (s != null && s.contains("=")) {
                final String key = s.split("=")[0].toLowerCase();
                final String value = s.split("=")[1];
                EnchantmentDescriptions.descriptions.put(key, value);
                Bukkit.getLogger().info("Loading " + key + ", description: " + value);
            }
        }
    }
    
    public static String getDescription(final CustomEnchantment ce) {
        if (EnchantmentDescriptions.descriptions == null) {
            loadConfig();
        }
        return EnchantmentDescriptions.descriptions.containsKey(ce.name().toLowerCase()) ? EnchantmentDescriptions.descriptions.get(ce.name().toLowerCase()) : "???";
    }
    
    static {
        EnchantmentDescriptions.descriptions = null;
    }
}
