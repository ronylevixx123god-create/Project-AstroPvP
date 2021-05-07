package me.astropvp.astrotournaments.struct;

import java.util.List;

import me.astropvp.astrotournaments.data.ActiveTournamentData;
import org.bukkit.entity.Player;

import me.galacticpvp.galacticpvputils.utils.CC;
import me.galacticpvp.galacticpvputils.utils.WarpUtils;

public class OpponentData {
	
	private List<TeamData> data;
	private List<TeamData> teamsLeft;
	
	public OpponentData(List<TeamData> data) {
		this.data = data;
		this.teamsLeft = data;
	}
	
	public List<TeamData> getTeams() {
		return this.data;
	}
	
	public List<TeamData> getTeamsLeft() {
		return this.teamsLeft;
	}
	
	public String toString() {
		return data.toString();
	}
	
	private void broadcastTeamRemoval(List<ActiveTournamentUser> winners, List<ActiveTournamentUser> losers) {
		StringBuilder sb = new StringBuilder(CC.Green);
		winners.stream().forEach(winner -> sb.append(winner.getPlayerName() + ", "));
		sb.substring(0, sb.lastIndexOf(","));
		sb.append(" have beaten the team members: " + CC.Red + " ");
		losers.stream().forEach(loser -> sb.append(loser.getPlayerName() + ", "));
		sb.substring(0, sb.lastIndexOf(","));
		sb.append(CC.Green + " in a 2v2 match!");
		String text = sb.get();
		for(ActiveTournamentUser user : ActiveTournamentData.getUsers()) {
			Player player = user.getPlayer();
			player.sendMessage(text);
		}
	}
	
	private class StringBuilder {
		
		private String string;
		
		public StringBuilder(String string) {
			this.string = string;
		}
		
		public StringBuilder substring(int from, int to) {
			this.string = this.string.substring(from, to);
			return this;
		}
		
		public StringBuilder append(String string) {
			this.string += this.string;
			return this;
		}
		
		public int lastIndexOf(String string) {
			return this.string.lastIndexOf(string);
		}
		
		public String get() {
			return this.string;
		}
	}
}

