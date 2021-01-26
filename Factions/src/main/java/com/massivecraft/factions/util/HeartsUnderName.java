package com.massivecraft.factions.util;


import com.massivecraft.factions.P;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class HeartsUnderName {
    public static void updateNametag(Player player, boolean hearts) {

        if (player.hasMetadata("NPC")) {
            return;
        }

        Bukkit.getScheduler().runTaskAsynchronously(P.getP(), () -> {
            com.massivecraft.factions.board.Board board = P.getBoardManager().getPlayerBoards().get(player.getUniqueId());
            if (board == null) {
                return;C
            }

            Scoreboard scoreboard = board.getScoreboard();

            if (hearts) {
                Objective objective = scoreboard.getObjective("showhealth");
                if (objective == null) {
                    objective = scoreboard.registerNewObjective("showhealth", "health");
                    objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
                    objective.setDisplayName(ChatColor.GREEN + "\u2764");
                }
            } else {
                Objective objective = board.getScoreboard().getObjective(DisplaySlot.BELOW_NAME);

                if (objective != null) {
                    objective.unregister();
                }
            }

        });
    }
}
