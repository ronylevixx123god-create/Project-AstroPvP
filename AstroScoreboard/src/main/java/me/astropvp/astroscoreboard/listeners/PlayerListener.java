package me.astropvp.astroscoreboard.listeners;

import com.cosmicpvp.cosmicutils.utils.CC;
import me.bryside.WildPvPPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Criterias;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import me.astropvp.astroscoreboard.manager.TabListManager;

public class PlayerListener implements Listener {
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onJoin(PlayerJoinEvent event) {



		//board.registerNewObjective("astro", "dummy");
		
		//String objectiveName = ChatColor.RED + "\u2764";
		Player player = event.getPlayer();
		//player.setHealth(player.getHealth());
		//board.registerNewObjective(objectiveName, "health").setDisplaySlot(DisplaySlot.BELOW_NAME);
		//player.setScoreboard(board);
		TabListManager.sendTablist(player,
		CC.translate("\n&a&lASTRO &7⎜ &fFactions\n"),
		CC.translate("\n&a&lSTORE &fbuy.astropvp.net\n&a&lDISCORD &fdiscord.astropvp.net\n"));
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		Scoreboard board = player.getScoreboard();
		if(board != null) {
			board.getTeams().forEach(team -> team.unregister());
		}
		
	}
}
