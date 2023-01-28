package net.crankedpvp.arcade.struct;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.TimeUtils;
import lombok.Getter;
import lombok.Setter;
import net.crankedpvp.arcade.ArcadeAPI;
import net.crankedpvp.arcade.ArcadePlugin;
import net.crankedpvp.arcade.language.Language;
import net.crankedpvp.arcade.tasks.ArcadeTimerChecker;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@Getter
public class ArcadeManager {
    private List<ArcadeMatch> matches;
    @Setter
    private boolean enabled;

    public ArcadeManager() {
        this.matches = new ArrayList<>();
        this.enabled = false;
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
        for (ArcadeMatch arcadeMatch : matches) {
            endMatch(arcadeMatch.getPlayers().getLeft());
        }
        matches.clear();
    }

    public String timeUntilArcadeEnabled() {
        Calendar now = Calendar.getInstance();
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int minutes = now.get(Calendar.MINUTE);
        int seconds = now.get(Calendar.SECOND);
        int nextOpenHour = Integer.MAX_VALUE;
        for (int openHour : ArcadeTimerChecker.openhours) {
            if (openHour > hour) {
                nextOpenHour = openHour;
                break;
            }
        }
        if (nextOpenHour == Integer.MAX_VALUE) {
            nextOpenHour = ArcadeTimerChecker.openhours.get(0);
        }
        int hoursUntilOpen = nextOpenHour - hour;
        int minutesUntilOpen = (60 - minutes) + (60 * hoursUntilOpen);
        int secondsUntilOpen = (60 - seconds) + (60 * (minutesUntilOpen));
        return TimeUtils.formatSeconds(secondsUntilOpen);
    }

    public void closeArcade() {
        for (ArcadeMatch arcadeMatch : matches) {
            endMatch(arcadeMatch.getPlayers().getLeft());
        }
        for (Player player : Bukkit.getWorld(ArcadeAPI.WORLD_NAME).getPlayers()) {
            player.teleport(Bukkit.getWorld("world").getSpawnLocation(), PlayerTeleportEvent.TeleportCause.UNKNOWN);
            player.sendMessage(CC.translate("&c&l(!)&c The arcade warp has closed, check /arcade to see when it opens again!"));
        }
        matches.clear();
    }


}
