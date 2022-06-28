package me.kingsavaqe.astrotournaments.listeners;

import me.kingsavaqe.astrotournaments.AstroTournamentsPlugin;
import me.kingsavaqe.astrotournaments.managers.TournamentManager;
import me.kingsavaqe.astrotournaments.objects.TournamentMatch;
import me.kingsavaqe.astrotournaments.objects.TournamentPlayer;
import me.kingsavaqe.astrotournaments.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffectType;

public class PlayerQuitListener implements Listener {

    private final AstroTournamentsPlugin astroTournamentsPlugin;
    private final TournamentManager tournamentManager;

    public PlayerQuitListener(AstroTournamentsPlugin astroTournamentsPlugin) {
        this.astroTournamentsPlugin = astroTournamentsPlugin;
        this.tournamentManager = astroTournamentsPlugin.getTournamentManager();
    }

    @EventHandler
    public void playerQuit(PlayerQuitEvent event) {
        if(event.getPlayer() == null) return;
        TournamentPlayer player1 = tournamentManager.getActiveTournamentPlayer(event.getPlayer().getUniqueId());
        TournamentPlayer player2;

        if(astroTournamentsPlugin.getTournamentManager().getTournamentMatchList().size() < 1) return;
        for(TournamentMatch tournamentMatch : astroTournamentsPlugin.getTournamentManager().getTournamentMatchList()) {
            if(tournamentMatch.getPlayerOne() == player1 || tournamentMatch.getPlayerTwo() == player1) {

                player1.getPlayer().teleport(Bukkit.getWorld("world").getSpawnLocation());

                if(tournamentMatch.getPlayerOne() == player1) {
                    player2 = tournamentMatch.getPlayerTwo();
                } else {
                    player1 = tournamentMatch.getPlayerTwo();
                    player2 = tournamentMatch.getPlayerOne();
                }

                for(TournamentPlayer player : astroTournamentsPlugin.getTournamentManager().getTournamentParticipants()) {
                    player.getPlayer().sendMessage(Utils.fixColor("&6Tournaments &8| &7" + player2.getPlayer().getName() + " &ehas beaten the player &7" + player1.getPlayer().getName() + " &ein a tournament match"));
                }

                tournamentMatch.getArena().reset();
                astroTournamentsPlugin.getTournamentManager().getTournamentMatchList().remove(tournamentMatch);

                if(astroTournamentsPlugin.getTournamentManager().getPlayerQueue().contains(player1))
                    astroTournamentsPlugin.getTournamentManager().getPlayerQueue().removeFromQueue(player1);
                astroTournamentsPlugin.getTournamentManager().getTournamentParticipants().remove(player1);

                player2.getPlayer().teleport(astroTournamentsPlugin.getTournamentManager().getWaitingRoomLocation());
                player1.getPlayer().sendMessage(Utils.fixColor("&cYou have been eliminated from the tournament"));

                if(astroTournamentsPlugin.getTournamentManager().getPlayerQueue().contains(player2))
                    astroTournamentsPlugin.getTournamentManager().getPlayerQueue().removeFromQueue(player2);
                astroTournamentsPlugin.getTournamentManager().getPlayerQueue().enqueue(player2);

                astroTournamentsPlugin.getServer().getScheduler().runTaskLater(astroTournamentsPlugin, () -> {
                    astroTournamentsPlugin.getTournamentManager().putNextPlayersInArenas();
                }, 20L);

                emptyInventory(player1.getPlayer());
                emptyInventory(player2.getPlayer());

                return;
            }
        }

        if(astroTournamentsPlugin.getTournamentManager().getTournamentParticipants().contains(player1)) {

            player1.getPlayer().teleport(Bukkit.getWorld("world").getSpawnLocation());
            if(astroTournamentsPlugin.getTournamentManager().getPlayerQueue().contains(player1))
                astroTournamentsPlugin.getTournamentManager().getPlayerQueue().removeFromQueue(player1);
            astroTournamentsPlugin.getTournamentManager().getTournamentParticipants().remove(player1);
        }
    }

    private void emptyInventory(Player player) {
        player.getInventory().clear();
        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);
        player.removePotionEffect(PotionEffectType.SPEED);
    }

}
