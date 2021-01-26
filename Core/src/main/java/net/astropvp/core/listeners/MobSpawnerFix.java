package net.astropvp.core.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class MobSpawnerFix implements Listener {
    /*@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void MonsterFarm(CreatureSpawnEvent e) {
        if (e.getSpawnReason() != CreatureSpawnEvent.SpawnReason.SPAWNER) {
            return;
        }
        double doubleSpawnChance = 0.99D;
        if (Math.random() < doubleSpawnChance)
            e.getEntity().setMetadata("monsterAmount", new FixedMetadataValue(Bukkit.getPluginManager().getPlugin("Arkkit"), e.getEntity().hasMetadata("monsterAmount") ? (e.getEntity().getMetadata("monsterAmount").get(0).asInt() + 5) : 5));
    }*/
}
