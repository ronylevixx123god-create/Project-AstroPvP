package com.cosmicpvp.titles.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class HeartsListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (event.getPlayer() != null) {
            //updateNametag(event.getPlayer(), true);
        }
    }

    /*public static void updateNametag(Player player, boolean hearts) {

        if (player.hasMetadata("NPC")) {
            return;
        }

        Bukkit.getScheduler().runTaskAsynchronously(AstroCore.getInstance(), () -> {
            Board board = P.getP().getBoardManager().getPlayerBoards().get(player.getUniqueId());
            if (board == null) {
                return;
            }

            Scoreboard scoreboard = board.getScoreboard();

            if (hearts) {
                Objective objective = scoreboard.getObjective("showhealth");
                if (objective == null) {
                    objective = scoreboard.registerNewObjective("showhealth", "health");
                    objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
                    objective.setDisplayName(ChatColor.DARK_RED + "\u2764");
                }
            } else {
                Objective objective = board.getScoreboard().getObjective(DisplaySlot.BELOW_NAME);

                if (objective != null) {
                    objective.unregister();
                }
            }

        });
    }*/
}
