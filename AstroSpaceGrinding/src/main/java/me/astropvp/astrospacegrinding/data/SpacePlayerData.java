package me.astropvp.astrospacegrinding.data;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import me.astropvp.astrospacegrinding.enums.MonsterType;

public class SpacePlayerData {
	
	private static final Set<SpacePlayerData> data = Sets.newHashSet();
	
	private final String player;
	private final Map<MonsterType, Integer> kills;
	private final long when_created;
	
	public SpacePlayerData(Player player) {
		this.player = player.getName();
		this.kills = Maps.newHashMap();
		this.when_created = System.currentTimeMillis();
		data.add(this);
	}
	
	public String getPlayerName() {
		return this.player;
	}
	
	public Player getPlayer() {
		return Bukkit.getPlayer(this.player);
	}
	
	public int getKills(MonsterType type) {
		return kills.getOrDefault(type, 0);
	}
	
	public void increment(MonsterType type) {
		setKills(type, getKills(type) + 1);
	}
	
	public void clearKills(MonsterType type) {
		setKills(type, 0);
	}
	
	public void setKills(MonsterType type, int value) {
		kills.put(type, value);
	}
	
	public int getTimeInSpace() {
		return (int) ((System.currentTimeMillis() - when_created) / 1000);
	}
	
	public void remove() {
		remove(this.getPlayer());
	}
	
	public static void remove(Player player) {
		Iterator<SpacePlayerData> iterator = data.iterator();
		String name = player.getName();
		while(iterator.hasNext()) {
			SpacePlayerData pdata = iterator.next();
			if(pdata.getPlayerName().equals(name)) {
				iterator.remove();
				return;
			}
		}
	}
	
	public static SpacePlayerData findPlayer(Player player) {
		String name = player.getName();
		for(SpacePlayerData pdata: data) {
			if(pdata.getPlayerName().equals(name)) {
				return pdata;
			}
		}
		return new SpacePlayerData(player);
	}
}
