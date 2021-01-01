package org.arkhamnetwork.enchantments.enchantments.armor;

import org.bukkit.scheduler.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.arkhamnetwork.enchantments.*;
import org.bukkit.metadata.*;
import org.bukkit.plugin.*;
import java.util.*;

final class reducePhoenixCount extends BukkitRunnable
{
    public void run() {
        Bukkit.getLogger().info("[Enchantments (Phoenix)] Reducing all phoenixProcs by 1.");
        for (final Player p : Bukkit.getServer().getOnlinePlayers()) {
            if (p.hasMetadata("phoenixProcs")) {
                p.setMetadata("phoenixProcs", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)Math.max(0, p.getMetadata("phoenixProcs").get(0).asInt() - 1)));
            }
        }
    }
}
