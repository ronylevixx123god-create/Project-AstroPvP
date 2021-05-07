package me.astropvp.astrotournaments.data;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.cosmicpvp.cosmicutils.utils.CC;
import me.astropvp.astrotournaments.AstroTournamentsAPI;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.google.common.collect.Lists;

import me.astropvp.astrotournaments.exceptions.ActiveMatchException;
import me.astropvp.astrotournaments.struct.ActiveTournamentUser;
import me.astropvp.astrotournaments.struct.OpponentData;
import me.astropvp.astrotournaments.struct.TeamData;
import me.astropvp.astrotournaments.tasks.NewMatchesTask;

public class ActiveTournamentData {
	
	private static boolean active;
	private static List<ActiveTournamentUser> users = Lists.newArrayList();
	private static List<TeamData> teams = Lists.newArrayList();
	private static List<OpponentData> opponents = Lists.newArrayList();
	private static int teamSize = 2;
	private static int opponentSize = 2;
	
	public static boolean isActive() {
		return active;
	}
	
	public static void shuffleTeams() {
		for(int rep = 0; rep < 1; rep++) {
			for(int i = 0; i < teams.size(); i++) {
				int index = ThreadLocalRandom.current().nextInt(teams.size());
				TeamData random = teams.get(index);
				TeamData replace = teams.set(i, random);
				teams.set(index, replace);
			}
		}
	}
	
	
	public static void shuffleUsers() {
		for(int rep = 0; rep < 1; rep++) {
			for(int i = 0; i < users.size(); i++) {
				int index = ThreadLocalRandom.current().nextInt(users.size());
				ActiveTournamentUser random = users.get(index);
				ActiveTournamentUser replace = users.set(i, random);
				users.set(index, replace);
			}
		}
	}
	
	public static void assignTeams() {
		int pos =  users.size() % teamSize * opponentSize;
		while(pos > 0) {
			ActiveTournamentUser user = users.get(users.size() - 1);
			Player player = user.getPlayer();
			player.sendMessage(CC.DarkPurple + "You could not be matched up. You have been teleported back to spawn!");
			user.remove();
			pos--;
		}
		shuffleUsers();
		for(int i = 0; i < users.size() - (teamSize - 1); i += teamSize) {
			List<ActiveTournamentUser> users = Lists.newArrayList();
			for(pos = 0; pos < teamSize; pos++) {
				users.add(users.get(pos));
			}
			teams.add(new TeamData(users));
		}
	}
	
	public static void assignOpponents() {
		if(opponents.size() > 0) {
			for(OpponentData data : opponents) {
				Bukkit.getLogger().info(data.toString());
			}
			new ActiveMatchException("ActiveTournamentData#assignOpponents() - There is an active match!").printStackTrace();
		}
		shuffleTeams();
		List<TeamData> teams = ActiveTournamentData.getTeams();
		int i;
		for(i = 0; i < teams.size() - (opponentSize - 1); i+= opponentSize) {
			List<TeamData> teamSetup = Lists.newArrayList();
			for(int pos = i; pos < i + opponentSize; pos++) {
				teamSetup.add(teams.get(pos));
			}
			opponents.add(new OpponentData(teamSetup));
		}
		for(int pos = i; pos < teams.size(); pos++) {
			TeamData data = teams.get(i);
			for(ActiveTournamentUser user : data.getUsers()) {
				user.getPlayer().sendMessage(CC.YellowB + "(!) " + CC.Yellow + "Your team has been chosen to sit out this round!");
			}
		}
	}
	
	public static void startMatches() {
		for(OpponentData data : ActiveTournamentData.getOpponents()) {
			int pos = 0;
			for(TeamData team: data.getTeams()) {
				StringBuilder sb = new StringBuilder(CC.Green);
				for(ActiveTournamentUser user : team.getUsers()) {
					sb.append(user.getPlayerName() + ", ");
				}
				if(sb.indexOf(",") != -1) {
					sb = new StringBuilder(sb.substring(0, sb.lastIndexOf(",")));
					sb.append(" ");
				}
				for(TeamData otherTeam : data.getTeams()) {
					if(otherTeam == team) {
						continue;
					}
					sb.append(CC.RedU + "VS" + CC.Reset + " ");
					for(ActiveTournamentUser user : otherTeam.getUsers()) {
						sb.append(user.getPlayerName() + ", ");
					}
					sb = new StringBuilder(sb.substring(0, sb.lastIndexOf(",")) + " ");
				}
				for(ActiveTournamentUser user : team.getUsers()) {
					Player player = user.getPlayer();
					player.teleport(AstroTournamentsAPI.getSpawnLocation(pos));
					player.playSound(player.getLocation(), Sound.ANVIL_LAND, 2F, 2F);
					pos++;
				}
			}
		}
	}
	
	
	public static void setActive(boolean active) {
		ActiveTournamentData.active = active;
	}
	
	public static void addUser(ActiveTournamentUser user) {
		users.add(user);
	}
	
	public static void addTeam(TeamData data) {
		teams.add(data);
	}
	
	public static void removeUser(ActiveTournamentUser user) {
		users.remove(user);
	}
	
	public static void removeTeam(TeamData data) {
		teams.remove(data);
	}
	
	public static void removeOpponentData(OpponentData data) {
		opponents.remove(data);
		if(opponents.size() == 0 && teams.size() > 1) {
			new NewMatchesTask();
			return;
		}
		if(teams.size() == 1) {
			AstroTournamentsAPI.endTournament();
		}
	}
	
	public static OpponentData findOpponentMatch(TeamData team) {
		for(OpponentData opponent : opponents) {
			if(opponent.getTeams().contains(team)) {
				return opponent;
			}
		}
		return null;
	}
	
	public static ActiveTournamentUser findUser(Player player) {
		String name = player.getName();
		for(ActiveTournamentUser user : users) {
			if(user.getPlayerName().equals(name)) {
				return user;
			}
		}
		//could not find
		return null;
	}
	
	public static TeamData findTeam(ActiveTournamentUser user) {
		for(TeamData data : teams) {
			if(data.getUsers().contains(user)) {
				return data;
			}
		}
		//could not find
		return null;
	}
	
	public static List<ActiveTournamentUser> getUsers(){
		return users;
	}
	
	public static List<TeamData> getTeams(){
		return teams;
	}
	
	public static List<OpponentData> getOpponents() {
		return opponents;
	}
	
	public static int getRemainingTeams() {
		return teams.size();
	}
}
