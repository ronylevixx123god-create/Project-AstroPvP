package me.kingsavaqe.astrotournaments.listeners;

import me.kingsavaqe.astrotournaments.AstroTournamentsPlugin;
import me.kingsavaqe.astrotournaments.managers.TournamentManager;
import me.kingsavaqe.astrotournaments.objects.TournamentMatch;
import me.kingsavaqe.astrotournaments.objects.TournamentPlayer;
import me.kingsavaqe.astrotournaments.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffectType;

public class TournamentDeathListener implements Listener {

    private final AstroTournamentsPlugin astroTournamentsPlugin;
    private final TournamentManager tournamentManager;

    public TournamentDeathListener(AstroTournamentsPlugin astroTournamentsPlugin) {
        this.astroTournamentsPlugin = astroTournamentsPlugin;
        this.tournamentManager = astroTournamentsPlugin.getTournamentManager();
    }

    @EventHandler
    public void tournamentDeathListener(PlayerDeathEvent event) {
        if(event.getEntity() == null || event.getEntity().getKiller() == null) return;
        TournamentPlayer victim = tournamentManager.getActiveTournamentPlayer(event.getEntity().getUniqueId());
        TournamentPlayer attacker;

        if(astroTournamentsPlugin.getTournamentManager().getTournamentMatchList().size() < 1) return;
        for(TournamentMatch tournamentMatch : astroTournamentsPlugin.getTournamentManager().getTournamentMatchList()) {
            if(tournamentMatch.getPlayerOne() == victim || tournamentMatch.getPlayerTwo() == victim) {


                event.getDrops().clear();

                if(tournamentMatch.getPlayerOne() == victim) {
                    attacker = tournamentMatch.getPlayerTwo();
                } else {
                    victim = tournamentMatch.getPlayerTwo();
                    attacker = tournamentMatch.getPlayerOne();
                }

                for(TournamentPlayer player : astroTournamentsPlugin.getTournamentManager().getTournamentParticipants()) {
                    player.getPlayer().sendMessage(Utils.fixColor("&6Tournaments &8| &7" + attacker.getPlayer().getName() + " &ehas beaten the player &7" + victim.getPlayer().getName() + " &ein a tournament match"));
                };


                tournamentMatch.getArena().reset();
                astroTournamentsPlugin.getTournamentManager().getTournamentMatchList().remove(tournamentMatch);

                if(astroTournamentsPlugin.getTournamentManager().getPlayerQueue().contains(tournamentManager.getActiveTournamentPlayer(victim.getUuid())))
                    astroTournamentsPlugin.getTournamentManager().getPlayerQueue().removeFromQueue(victim);
                astroTournamentsPlugin.getTournamentManager().getTournamentParticipants().remove(victim);

                attacker.getPlayer().teleport(astroTournamentsPlugin.getTournamentManager().getWaitingRoomLocation());
                victim.getPlayer().sendMessage(Utils.fixColor("&cYou have been eliminated from the tournament"));

                if(astroTournamentsPlugin.getTournamentManager().getPlayerQueue().contains(attacker))
                    astroTournamentsPlugin.getTournamentManager().getPlayerQueue().removeFromQueue(attacker);
                astroTournamentsPlugin.getTournamentManager().getPlayerQueue().enqueue(attacker);

                astroTournamentsPlugin.getServer().getScheduler().runTaskLater(astroTournamentsPlugin, () -> {
                    astroTournamentsPlugin.getTournamentManager().putNextPlayersInArenas();
                }, 20L);

                emptyInventory(victim.getPlayer());
                emptyInventory(attacker.getPlayer());
            }
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
