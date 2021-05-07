package me.astropvp.astrotournaments.struct;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.Inventory;

import com.google.common.collect.Lists;

import me.galacticpvp.galacticpvputils.utils.ConfigUtils;

public class TournamentUser {
	
	private int wins;
	private int losses;
	private UUID uuid;
	private List<String> rewards;
	
	public TournamentUser(UUID uuid) {
		this.uuid = uuid;
		this.rewards = Lists.newArrayList();
	}
	
	public UUID getUUID() {
		return this.uuid;
	}
	
	public int getWins() {
		return this.wins;
	}
	
	public int getLosses() {
		return this.losses;
	}
	
	public OfflinePlayer getOfflinePlayer() {
		return Bukkit.getOfflinePlayer(this.uuid);
	}
	
	public List<String> getRewardsSerialized(){
		return this.rewards;
	}
	
	public void setWins(int wins) {
		this.wins = wins;
	}
	
	public void setLosses(int losses) {
		this.losses = losses;
	}
	
	public void addRewards(Inventory inventory) {
		this.rewards.add(ConfigUtils.toBase64(inventory, inventory.getSize()));
	}
	
	public void setRewardsSerialized(List<String> rewards) {
		this.rewards = rewards;
	}
}
