package net.astropvp.core.utils;

import net.astropvp.core.AstroCore;
import org.arkhamnetwork.enchantments.Enchantments;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

public class ChunkbusterCooldown {
    public static void enterChunkbusterCooldown(Player player) {
        player.setMetadata("chunkbusterCooldown", new FixedMetadataValue(AstroCore.getInstance(), player.getName()));
        Bukkit.getScheduler().runTaskLater(Enchantments.getInstance(), ()->{
            player.removeMetadata("chunkbusterCooldown", Enchantments.getInstance());
        },1200L);
    }

    public static boolean isOnChunkbusterCooldown(Player player) {
        return player.hasMetadata("chunkbusterCooldown");
    }
}
