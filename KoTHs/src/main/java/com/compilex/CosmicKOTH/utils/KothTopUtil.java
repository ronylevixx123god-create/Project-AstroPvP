package com.compilex.CosmicKOTH.utils;

import com.compilex.CosmicKOTH.CosmicKOTHPlugin;
import com.compilex.CosmicKOTH.customfiles.CustomConfig;
import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class KothTopUtil {
    public static Map<String, Integer> getKothTop() {

        Map<String, Integer> kothTop = new HashMap<>();
            for (Map.Entry<String, Integer> entry : CosmicKOTHPlugin.get_().getKothWins().entrySet()) {
                String facID = String.valueOf(entry.getKey());
                int wins = Integer.parseInt(String.valueOf(entry.getValue()));
                if (FactionUtils.getFactionById(facID) == null) continue;
                String facname = FactionUtils.getFactionById(facID).getTag();
                if (facname != null) {
                    kothTop.put(facname, wins);
                }

        }

        Map<String, Integer> sortedKoth = kothTop.entrySet().stream().sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (entry1, entry2) -> entry2, LinkedHashMap::new));

        return sortedKoth.entrySet().stream()
                .limit(10)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    }
}
