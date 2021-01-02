package org.arkhamnetwork.enchantments.enchantments.armor;

import org.arkhamnetwork.enchantments.Enchantments;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

final class reducePhoenixCount extends BukkitRunnable {
    public void run() {
        Bukkit.getLogger().info("[Enchantments (Phoenix)] Reducing all phoenixProcs by 1.");
        for (final Player p : Bukkit.getServer().getOnlinePlayers()) {
            if (p.hasMetadata("phoenixProcs")) {
                p.setMetadata("phoenixProcs", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), (Object) Math.max(0, p.getMetadata("phoenixProcs").get(0).asInt() - 1)));
            }
        }
    }
}
