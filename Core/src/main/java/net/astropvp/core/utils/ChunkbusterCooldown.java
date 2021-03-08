package net.astropvp.core.utils;

import com.cosmicpvp.cosmicutils.utils.CC;
import net.astropvp.core.AstroCore;
import org.arkhamnetwork.enchantments.Enchantments;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

public class ChunkbusterCooldown {
    public static void enterChunkbusterCooldown(Player player) {
        player.setMetadata("chunkbusterCooldown", new FixedMetadataValue(AstroCore.getInstance(), player.getName()));
        Bukkit.getScheduler().runTaskLaterAsynchronously(AstroCore.getInstance(), ()->{
            player.removeMetadata("chunkbusterCooldown", AstroCore.getInstance());
            player.sendMessage(CC.translate("&aYou can now place a chunk buster again!"));
        },300L);
    }

    public static boolean isOnChunkbusterCooldown(Player player) {
        return player.hasMetadata("chunkbusterCooldown");
    }
}
