package me.astropvp.astrospawners.ftop;

import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import com.google.common.collect.Maps;
import com.massivecraft.factions.Faction;
import java.util.Map;

import me.astropvp.astrospawners.AstroSpawners;
import me.astropvp.astrospawners.utils.SortUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class FactionsTopAPI {
    public static void recalculateFactionsTop() {
        FactionCacher.getData().clear();
        Bukkit.getScheduler().runTaskAsynchronously((Plugin) AstroSpawners.getInstance(), (Runnable)new BukkitRunnable() {
            public void run() {
                Map<FactionData, Long> factions = Maps.newHashMap();
                for (Faction faction : FactionUtils.getAllFactions()) {
                    if (faction.isSafeZone() || faction.isWarZone() || faction.isWilderness())
                        continue;
                    FactionData factionData = new FactionData(faction);
                    factions.put(factionData, Long.valueOf(factionData.getSpawnerValue()));
                }
                factions = SortUtils.sort(factions);
                Map<Integer, FactionData> data = Maps.newLinkedHashMap();
                int pos = 0;
                for (Map.Entry<FactionData, Long> entry : factions.entrySet()) {
                    data.put(Integer.valueOf(pos), entry.getKey());
                    pos++;
                }
                FactionCacher.setFactionData(data);
            }
        });
    }

    public static Map<Integer, FactionData> getTopFactions(int from, int to) {
        Map<Integer, FactionData> data = Maps.newHashMap();
        for (Map.Entry<Integer, FactionData> entry : (Iterable<Map.Entry<Integer, FactionData>>)FactionCacher.getData().entrySet()) {
            int value = ((Integer)entry.getKey()).intValue();
            if (from <= value && to >= value)
                data.put(Integer.valueOf(value), entry.getValue());
        }
        return data;
    }

    public static long getValueOfSpawner(EntityType type) {
        if (type == EntityType.BLAZE)
            return 750000L;
        if (type == EntityType.IRON_GOLEM)
            return 5000000L;
        if (type == EntityType.VILLAGER)
            return 15000000L;
        if (type == EntityType.ZOMBIE)
            return 85000L;
        if (type == EntityType.SKELETON)
            return 85000L;
        if (type == EntityType.CREEPER)
            return 390000L;
        if (type == EntityType.WITCH)
            return 10000000L;
        return 50000L;
    }
}
