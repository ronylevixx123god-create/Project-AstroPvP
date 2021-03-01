package me.ifamasssxd.cosmicsky.inventorypets.utils;

import org.arkhamnetwork.enchantments.Enchantments;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

public class WitcherCooldown {
    public static void enterWitcherCooldown(Player player) {
        player.setMetadata("witcherCooldown", new FixedMetadataValue(Enchantments.getInstance(), player.getName()));
        Bukkit.getScheduler().runTaskLaterAsynchronously(Enchantments.getInstance(), ()->{
            player.removeMetadata("witcherCooldown", Enchantments.getInstance());
        },1200L);
    }

    public static boolean isOnWitcherCooldown(Player player) {
        return player.hasMetadata("witcherCooldown");
    }
}
