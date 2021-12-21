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
import org.bukkit.scoreboard.*;
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
		CC.translate("\n&e&l●&6&l●&f&l● &f[ &6&lAstro&f&lPvP &f] &f&l●&6&l●&e&l●\n&7play.astropvp.net\n"),
		CC.translate("\n&6Store&7: &fbuy.astropvp.net\n" + "&6Discord&7: &fdiscord.astropvp.net" + "\n"));
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		Scoreboard board = player.getScoreboard();
		if(board != null) {
			board.getTeams().forEach(Team::unregister);
		}
		
	}
}
