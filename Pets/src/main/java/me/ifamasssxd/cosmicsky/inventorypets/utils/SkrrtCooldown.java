package me.ifamasssxd.cosmicsky.inventorypets.utils;

import org.arkhamnetwork.enchantments.Enchantments;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

public class SkrrtCooldown {
    public static void enterSkrrtCooldown(Player player) {
        player.setMetadata("skrrtCooldown", new FixedMetadataValue(Enchantments.getInstance(), player.getName()));
        Bukkit.getScheduler().runTaskLaterAsynchronously(Enchantments.getInstance(), ()->{
            player.removeMetadata("skrrtCooldown", Enchantments.getInstance());
        },2400L);
    }

    public static boolean isOnSkrrtCooldown(Player player) {
        return player.hasMetadata("skrrtCooldown");
    }

}
