package me.astropvp.astrotournaments.struct;

import java.util.List;
import java.util.UUID;

import me.astropvp.astrotournaments.AstroTournamentsAPI;
import me.astropvp.astrotournaments.data.ActiveTournamentData;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.potion.PotionEffectType;

import com.exoticpvp.exoticarmorsets.armor.enums.ArmorSetType;
import com.google.common.collect.Lists;

import me.galacticpvp.galacticenchantments.enchantments.struct.managers.PlayerPotionTrackerManager;
import me.galacticpvp.galacticpvputils.utils.CC;
import me.galacticpvp.galacticpvputils.utils.ConfigUtils;
import me.galacticpvp.galacticpvputils.utils.LocationUtils;
import me.galacticpvp.galacticpvputils.utils.PlayerUtils;
import me.astropvp.astrotournaments.inventories.ChooseSetInventory;

public class ActiveTournamentUser {
	
	private String player;
	private boolean excluded;
	private String serializedInventory;
	private String serializedArmorContents;
	private SpectatorUser spectatorUser;
	private String previousLocation;
	private List<UUID> trackedPotionEffects;
	private int experience;
	private ArmorSetType armorset;
	
	public ActiveTournamentUser(Player player) {
		this.player = player.getName();
		this.armorset = ArmorSetType.PHANTOM;
		this.serializedInventory = ConfigUtils.toBase64(player.getInventory().getContents(), player.getInventory().getContents().length);
		this.serializedArmorContents = ConfigUtils.toBase64(player.getInventory().getArmorContents(), 9);
		PlayerUtils.clearContents(player);
		this.previousLocation = LocationUtils.toStringLocation(player.getLocation());
		player.setHealth(player.getMaxHealth());
		this.trackedPotionEffects = Lists.newArrayList();
		this.experience = player.getTotalExperience();
		PlayerUtils.subtractExpFromPlayer(player, player.getTotalExperience());
	}
	
	public Player getPlayer() {
		return Bukkit.getPlayer(this.getPlayerName());
	}
	
	public ArmorSetType getArmorSet() {
		return this.armorset;
	}
	
	public String getPlayerName() {
		return this.player;
	}
	
	public void setExcluded(boolean excluded) {
		this.excluded = excluded;
	}
	
	public boolean isSpectating() {
		return this.spectatorUser != null;
	}
	
	public SpectatorUser getSpectatorInstance() {
		return this.spectatorUser;
	}
	
	public void setSpectating(boolean spectating) {
		if(spectating) {
			this.spectatorUser = new SpectatorUser(this.getPlayer());
			for(ActiveTournamentUser user : ActiveTournamentData.getUsers()) {
				if(user == this) {
					continue;
				}
				this.spectatorUser.addInvisibleUser(user.getPlayer());
			}
			this.spectatorUser.toggleGamemode(GameMode.CREATIVE);
			PlayerUtils.clearContents(this.getPlayer());
			return;
		}
		if(this.isSpectating()) {
			while(this.getSpectatorInstance().getUsers().size() != 0) {
				this.getSpectatorInstance().removeUser(Bukkit.getPlayer(this.getSpectatorInstance().getUsers().get(0)));
			}
			this.spectatorUser.toggleGamemode(GameMode.SURVIVAL);
		}
		this.spectatorUser = null;
	}
	
	public void restore() {
		Player player = this.getPlayer();
		player.setGameMode(GameMode.SURVIVAL);
		player.setHealth(player.getMaxHealth());
		this.setSpectating(false);
		this.restoreInventory();
		this.restoreLocation();
		this.removePotionEffects();
		player.giveExp(this.experience);
	}
	
	public void setArmorSet(ArmorSetType armorset) {
		this.armorset = armorset;
	}
	
	public void restoreLocation() {
		this.getPlayer().teleport(LocationUtils.fromStringLocation(this.previousLocation));
	}
	
	public void restoreInventory() {
		Player player = this.getPlayer();
		Inventory inventory = ConfigUtils.fromBase64(this.serializedInventory);
		int size = inventory.getSize();
		for(int i = 0; i < size; i++) {
			player.getInventory().setItem(i, inventory.getItem(i));
		}
		inventory = ConfigUtils.fromBase64(this.serializedArmorContents);
		for(int i = size; i < size + 4; i++) {
			player.getInventory().setItem(i, inventory.getItem(i - 36));
		}
	}
	
	public void sendBackToLobby() {
		Player player = this.getPlayer();
		PlayerUtils.clearContents(player);
		player.teleport(AstroTournamentsAPI.getLobbyLocation());
		player.openInventory(ChooseSetInventory.getInventory());
		player.setHealth(player.getMaxHealth());
		player.sendMessage(CC.Yellow + "(!) " + CC.Yellow + "Get ready to get queued up for your next match!");
		PlayerPotionTrackerManager manager = PlayerPotionTrackerManager.getPotionTracker(player);
		trackedPotionEffects.add(manager.trackPotionEffect(PotionEffectType.JUMP, 3, -1));
		trackedPotionEffects.add(manager.trackPotionEffect(PotionEffectType.SPEED, 5, -1));
	}
	
	public void removePotionEffects() {
		PlayerPotionTrackerManager manager = PlayerPotionTrackerManager.getPotionTracker(this.getPlayer());
		for(UUID uuid : trackedPotionEffects) {
			manager.untrackPotionEffect(manager.findPotionEffect(uuid));
		}
	}
	
	public void remove() {
		this.restore();
		this.remove();
		this.removePotionEffects();
		TeamData team = ActiveTournamentData.findTeam(this);
		if(team == null) {
			return;
		}
		for(ActiveTournamentUser user : ActiveTournamentData.getUsers()) {
			if(user == null) {
				continue;
			}
			if(!user.isSpectating()) {
				continue;
			}
			if(user.getSpectatorInstance().getUsers().contains(this.getPlayerName())) {
				user.getSpectatorInstance().removeUser(this.getPlayer());
			}
		}
		team.getUsers().remove(this);
		if(team.getUsers().size() > 0) {
			return;
		}
		ActiveTournamentData.removeTeam(team);
		OpponentData oppData = ActiveTournamentData.findOpponentMatch(team);
		if(oppData == null) {
			return;
		}
		oppData.getTeamsLeft().remove(team);
		if(oppData.getTeamsLeft().size() > 1) {
			return;
		}
		ActiveTournamentData.removeOpponentData(oppData);
		TeamData winningTeam = oppData.getTeamsLeft().get(0);
		for(ActiveTournamentUser user : winningTeam.getUsers()) {
			Player pl = user.getPlayer();
			pl.sendMessage(CC.GreenB + "(!) " + CC.Green + "You won the tournament match!");
			user.setSpectating(false);
			user.sendBackToLobby();
		}
		return;
	}
}
