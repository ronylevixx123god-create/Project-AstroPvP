/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Player
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitScheduler
 */
package me.ifamasssxd.cosmicmasks.tasks;

import me.ifamasssxd.cosmicmasks.CosmicMasks;
import me.ifamasssxd.cosmicmasks.MaskType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

public class ThanosMaskTask {
    public static void checkThanos() {
        BukkitScheduler scheduler = Bukkit.getScheduler();
        scheduler.scheduleSyncRepeatingTask((Plugin)CosmicMasks.get(), () -> {
            for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                if (MaskType.THANOS.hasEquipped(p)) {
                    if (!p.hasMetadata("webWalkerEnchant")) {
                        p.setMetadata("webWalkerEnchant", (MetadataValue)new FixedMetadataValue((Plugin)CosmicMasks.get(), (Object)1));
                    }
                    if (p.hasMetadata("lavaStriderEnchant")) continue;
                    p.setMetadata("lavaStriderEnchant", (MetadataValue)new FixedMetadataValue((Plugin)CosmicMasks.get(), (Object)1));
                    continue;
                }
                if (p.hasMetadata("webWalkerEnchant")) {
                    p.removeMetadata("webWalkerEnchant", (Plugin)CosmicMasks.get());
                }
                if (!p.hasMetadata("lavaStriderEnchant")) continue;
                p.removeMetadata("lavaStriderEnchant", (Plugin)CosmicMasks.get());
            }
        }, 100L, 100L);
    }
}

