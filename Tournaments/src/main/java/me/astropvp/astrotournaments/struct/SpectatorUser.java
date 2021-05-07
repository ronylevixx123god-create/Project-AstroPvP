package me.astropvp.astrotournaments.struct;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import com.google.common.collect.Lists;

public class SpectatorUser {
	
	private String player;
	private boolean invisible;
	private GameMode gamemode;
	private List<String> users;
	
	public SpectatorUser(Player player) {
		this.player = player.getName();
		this.users = Lists.newArrayList();
	}
	
	public Player getPlayer() {
		return Bukkit.getPlayer(this.getPlayerName());
	}
	
	public String getPlayerName() {
		return this.player;
	}
	
	public List<String> getUsers(){
		return this.users;
	}
	
	@Deprecated
	public void clearUsers() {
		this.users.clear();
	}
	
	public void toggleInvsibility(boolean invisible) {
		this.invisible = invisible;
	}
	
	public void toggleGamemode(GameMode gamemode) {
		this.gamemode = gamemode;
		this.getPlayer().setGameMode(this.gamemode);
	}
	
	public boolean isInvisible() {
		return this.invisible;
	}
	
	public GameMode getGamemode() {
		return this.gamemode;
	}
	
	public void addInvisibleUser(Player player) {
		this.users.add(player.getName());
		player.hidePlayer(this.getPlayer());
	}
	
	public void removeUser(Player player) {
		String name = player.getName();
		for(int i = 0; i < users.size(); i++) {
			if(users.get(i).equals(name)) {
				users.remove(i);
				Bukkit.getPlayer(name).showPlayer(this.getPlayer());
				return;
			}
		}
	}
	
}
