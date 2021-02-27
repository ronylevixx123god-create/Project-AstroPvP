package me.ifamasssxd.cosmicsky.inventorypets.utils;

import org.arkhamnetwork.enchantments.Enchantments;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

public class CageCooldown {
    public static void enterCageCooldown(Player player) {
        player.setMetadata("cageCooldown", new FixedMetadataValue(Enchantments.getInstance(), player.getName()));
        Bukkit.getScheduler().runTaskLaterAsynchronously(Enchantments.getInstance(), ()->{
            player.removeMetadata("cageCooldown", Enchantments.getInstance());
        },400L);
    }

    public static boolean isOnCageCooldown(Player player) {
        return player.hasMetadata("cageCooldown");
    }

}
