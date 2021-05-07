package me.astropvp.astrotournaments.tasks;

import java.util.List;

import me.astropvp.astrotournaments.AstroTournamentsAPI;
import me.astropvp.astrotournaments.AstroTournamentsPlugin;
import me.astropvp.astrotournaments.data.ActiveTournamentData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.galacticpvp.galacticpvputils.utils.CC;
import me.galacticpvp.galacticpvputils.utils.JSONMessage;
import me.galacticpvp.galacticpvputils.utils.WarpUtils;
import me.astropvp.astrotournaments.struct.ActiveTournamentUser;

public class TournamentStartTask extends BukkitRunnable {
	
	private int ticks;
	
	public TournamentStartTask() {
		if(ActiveTournamentData.isActive()) {
			return;
		}
		ticks = 61;
		ActiveTournamentData.setActive(true);
		this.runTaskTimer(AstroTournamentsPlugin.getInstance(), 20, 20);
	}

	@Override
	public void run() {
		ticks--;
		if(ticks == 0) {
			List<ActiveTournamentUser> users = ActiveTournamentData.getUsers();
			if(users.size() <= 2) {
				for(ActiveTournamentUser user : users) {
					Player player = user.getPlayer();
					player.teleport(WarpUtils.getSpawnLocation());
					player.sendMessage(CC.DarkPurple + "There were not enough players for the tournament to begin!");
				}
				AstroTournamentsAPI.endTournament();
				this.cancel();
				return;
			}
			ActiveTournamentData.assignTeams();
			new NewMatchesTask();
			this.cancel();
			return;
		}
		if(ticks == 60 || ticks == 30 || ticks == 15 || ticks == 10 || ticks <= 5) {
			for(Player player : Bukkit.getOnlinePlayers()) {
				sendMessageToPlayer(player);
			}
			return;
		}
	}
	
	
	private int getSlotsAvailable() {
		return AstroTournamentsAPI.getTournamentSize();
	}
	
	private String getFormattedTime(int seconds) {
		int minutes = seconds/60;
		seconds %= 60;
		StringBuilder builder = new StringBuilder();
		if(minutes > 0) {
			builder.append(minutes + "m, ");
		}
		if(seconds > 0 || seconds == 0) {
			builder.append(seconds + "s, ");
		}
		return builder.substring(0, builder.toString().lastIndexOf(","));
	}
	
	private void sendMessageToPlayer(Player player) {
		JSONMessage msg = new JSONMessage("");
		msg.addText("\n");
		msg.addText(CC.translate("&5&l&nGALACTIC 2v2 TOURNAMENT\n"));
		msg.addText(CC.translate("&7&oJoin the Tournament! This event will occur once every 3 hours.\n"));
		msg.addText("\n");
		msg.addRunCommand(CC.translate("&5&l- TIME REMAINING &f" + getFormattedTime(ticks) + "\n"), 
		ChatColor.WHITE, "/tournament join", CC.translate(" &7(/tournament join)\n"));
		msg.addRunCommand(CC.translate("&5&l- SLOTS TAKEN &f" + ActiveTournamentData.getUsers().size() + "/" + 
		getSlotsAvailable()), ChatColor.WHITE, "/tournament join", CC.translate(" &7(/tournament join)\n"));
		msg.addText("\n");
		msg.sendToPlayer(player);
	}

}
