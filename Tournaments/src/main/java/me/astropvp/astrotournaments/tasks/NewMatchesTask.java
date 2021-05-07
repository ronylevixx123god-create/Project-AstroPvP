package me.astropvp.astrotournaments.tasks;

import me.astropvp.astrotournaments.AstroTournamentsPlugin;
import me.astropvp.astrotournaments.data.ActiveTournamentData;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.galacticpvp.galacticpvputils.utils.CC;
import me.astropvp.astrotournaments.struct.ActiveTournamentUser;
import me.astropvp.astrotournaments.struct.TeamData;

public class NewMatchesTask extends BukkitRunnable {

	private int i;
	
	public NewMatchesTask() {
		this.i = 30;
		this.runTaskTimer(AstroTournamentsPlugin.getInstance(), 20, 20);
	}
	
	@Override
	public void run() {
		if(i == 0) {
			new ActiveMatchTask();
			ActiveTournamentData.assignOpponents();
			ActiveTournamentData.startMatches();
			for(TeamData team : ActiveTournamentData.getTeams()) {
				for(ActiveTournamentUser user : team.getUsers()) {
					user.removePotionEffects();
				}
			}
			this.cancel();
			return;
		}
		if(i % 5 == 0 || i <= 5) {
			this.sendMessage();
		}
		i--;
	}
	
	private void sendMessage() {
		for(ActiveTournamentUser user : ActiveTournamentData.getUsers()) {
			Player player = user.getPlayer();
			player.sendMessage(CC.YellowB + "(!) " + CC.Yellow + "The tournament matchups will begin in " + i + " seconds!");
		}
	}

}
