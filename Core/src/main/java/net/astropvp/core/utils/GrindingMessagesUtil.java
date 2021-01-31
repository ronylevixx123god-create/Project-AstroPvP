package net.astropvp.core.utils;

import com.google.common.collect.Lists;
import net.astropvp.core.AstroCore;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class GrindingMessagesUtil {

    public static boolean isPlayerIndicating(Player player) {
        return player.hasMetadata("grindingMessages");
    }

    public static void addPlayer(Player player) {
        List<String> players = Lists.newArrayList();
        if (AstroCore.getInstance().getConfig().contains("players-indicating"))
            players = AstroCore.getInstance().getConfig().getStringList("players-indicating");
        players.add(player.getUniqueId().toString());
        AstroCore.getInstance().getConfig().set("players-indicating", players);
        AstroCore.getInstance().saveConfig();
        AstroCore.getInstance().reloadConfig();
        player.setMetadata("grindingMessages", (MetadataValue)new FixedMetadataValue((Plugin)AstroCore.getInstance(), null));
    }

    public static void removePlayer(Player player) {
        List<String> players = Lists.newArrayList();
        if (AstroCore.getInstance().getConfig().contains("players-indicating"))
            players = AstroCore.getInstance().getConfig().getStringList("players-indicating");
        players.remove(player.getUniqueId().toString());
        AstroCore.getInstance().getConfig().set("players-indicating", players);
        AstroCore.getInstance().saveConfig();
        AstroCore.getInstance().reloadConfig();
        if (player.hasMetadata("grindingMessages"))
            player.removeMetadata("grindingMessages", (Plugin)AstroCore.getInstance());
    }

    public static boolean isPlayerContained(Player player) {
        List<String> players = Lists.newArrayList();
        if (AstroCore.getInstance().getConfig().contains("players-indicating"))
            players = AstroCore.getInstance().getConfig().getStringList("players-indicating");
        return players.contains(player.getUniqueId().toString());
    }

}
