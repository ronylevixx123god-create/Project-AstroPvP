package me.ifamasssxd.cosmicsky.inventorypets.utils;

import org.arkhamnetwork.enchantments.Enchantments;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

public class SnowmanCooldown {
    public static void enterSnowCooldown(Player player) {
        player.setMetadata("snowCooldown", new FixedMetadataValue(Enchantments.getInstance(), player.getName()));
        Bukkit.getScheduler().runTaskLaterAsynchronously(Enchantments.getInstance(), ()->{
            player.removeMetadata("snowCooldown", Enchantments.getInstance());
        },1200);
    }

    public static boolean isOnSnowCooldown(Player player) {
        return player.hasMetadata("snowCooldown");
    }

}
