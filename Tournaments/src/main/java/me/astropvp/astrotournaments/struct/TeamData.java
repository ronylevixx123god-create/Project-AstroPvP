package me.astropvp.astrotournaments.struct;

import java.util.List;

public class TeamData {
	
	private List<ActiveTournamentUser> users;
	
	public TeamData(List<ActiveTournamentUser> users) {
		this.users = users;
	}
	
	public List<ActiveTournamentUser> getUsers(){
		return this.users;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder("TEAM: {");
		for(ActiveTournamentUser user : users) {
			sb.append(user.getPlayerName() + ", ");
		}
		return sb.substring(0, sb.lastIndexOf(",")) + "}";
	}
}
