package me.astropvp.astroendgrinding.data;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import me.astropvp.astroendgrinding.enums.MonsterType;

public class EndPlayerData {
	
	private static Set<EndPlayerData> data = Sets.newHashSet();
	
	private String player;
	private Map<MonsterType, Integer> kills;
	private long when_created;
	
	public EndPlayerData(Player player) {
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
		return kills.containsKey(type) ? kills.get(type) : 0;
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
	
	public int getTimeInEnd() {
		return (int) ((System.currentTimeMillis() - when_created) / 1000);
	}
	
	public void remove() {
		remove(this.getPlayer());
	}
	
	public static void remove(Player player) {
		Iterator<EndPlayerData> iterator = data.iterator();
		String name = player.getName();
		while(iterator.hasNext()) {
			EndPlayerData pdata = iterator.next();
			if(pdata.getPlayerName().equals(name)) {
				iterator.remove();
				return;
			}
		}
	}
	
	public static EndPlayerData findPlayer(Player player) {
		String name = player.getName();
		for(EndPlayerData pdata: data) {
			if(pdata.getPlayerName().equals(name)) {
				return pdata;
			}
		}
		return new EndPlayerData(player);
	}
}
