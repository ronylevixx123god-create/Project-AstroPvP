package net.crankedpvp.arcade.struct;

import com.cosmicpvp.cosmicutils.utils.CC;
import lombok.Getter;
import net.crankedpvp.arcade.ArcadeAPI;
import net.crankedpvp.arcade.language.Language;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class ArcadeManager {
    private List<ArcadeMatch> matches;

    public ArcadeManager() {
        this.matches = new ArrayList<>();
    }

    public void endMatch(UUID uuid) {
        ArcadeMatch arcadeMatch = getArcadeMatch(uuid);
        matches.remove(arcadeMatch);
        Player player1 = Bukkit.getPlayer(arcadeMatch.getPlayers().getLeft());
        Player player2 = Bukkit.getPlayer(arcadeMatch.getPlayers().getRight());
        if (player1.isOnline()) {
            Language.MATCH_ENDED.send(player1);
        }
        if (player2.isOnline()) {
            Language.MATCH_ENDED.send(player2);
        }

        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            if (!player.canSee(player1)) player.showPlayer(player1);
            if (!player.canSee(player2)) player.showPlayer(player2);
            if (!player1.canSee(player)) player1.showPlayer(player);
            if (!player2.canSee(player)) player2.showPlayer(player);
        }

    }

    public void startMatch(UUID uuid1, UUID uuid2) {
        matches.add(new ArcadeMatch(uuid1, uuid2));
        Player player1 = Bukkit.getPlayer(uuid1);
        Player player2 = Bukkit.getPlayer(uuid2);
        String msgKiller = Language.MATCH_STARTED_KILLER.getMessage().replace("{victim}", player2.getName());
        String msgVictim = Language.MATCH_STARTED_VICTIM.getMessage().replace("{killer}", player1.getName());
        player1.sendMessage(CC.translate(msgKiller));
        player2.sendMessage(CC.translate(msgVictim));
        for (Player player : Bukkit.getWorld(ArcadeAPI.WORLD_NAME).getPlayers()) {
            player.sendMessage(CC.translate(Language.MATCH_STARTED_BROADCAST.getMessage().replace("{player1}", player1.getName()).replace("{player2}", player2.getName())));
        }

    }

    public boolean inArcadeMatch(UUID uuid) {
        for (ArcadeMatch arcadeMatch : matches) {
            if (arcadeMatch.getPlayers().contains(uuid)) return true;
        }
        return false;
    }

    public boolean inSameArcadeMatch(UUID uuid1, UUID uuid2) {
        for (ArcadeMatch arcadeMatch : matches) {
            if (arcadeMatch.getPlayers().contains(uuid1)) {
                return arcadeMatch.getPlayers().contains(uuid2);
            }
        }
        return false;
    }

    public ArcadeMatch getArcadeMatch(UUID uuid) {
        for (ArcadeMatch arcadeMatch : matches) {
            if (arcadeMatch.getPlayers().contains(uuid)) return arcadeMatch;
        }
        return null;
    }

    public void onDisable() {
        for(ArcadeMatch arcadeMatch : matches) {
            endMatch(arcadeMatch.getPlayers().getLeft());
        }
        matches.clear();
    }


}
