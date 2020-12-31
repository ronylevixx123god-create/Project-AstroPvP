package net.astropvp.core.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class MobRewards implements Listener {
	@EventHandler
	public void onBlazeDeath(EntityDeathEvent e) {
		if (e.getEntity() instanceof Blaze && (e.getEntity().getKiller() instanceof Player)) {
			String player = e.getEntity().getKiller().getName();
			double num = Math.random();
			try {
				if (num >= 0 && num <= 0.10) {
					e.getEntity().getKiller().sendMessage("");
					e.getEntity().getKiller().sendMessage("§d§l** TIER III REWARD **");
					e.getEntity().getKiller().sendMessage("§d+ Tier III Grinding Lootbox");
					e.getEntity().getKiller().sendMessage("");
					e.getEntity().getKiller().playSound(e.getEntity().getKiller().getLocation(), Sound.VILLAGER_YES, 5, 5);
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mc give " + player + " Grinding3 1");
					throw new IllegalArgumentException("");
				} else if (num >= 0 && num <= 0.35) {
					e.getEntity().getKiller().sendMessage("");
					e.getEntity().getKiller().sendMessage("§e§l** TIER II REWARD **");
					e.getEntity().getKiller().sendMessage("§e+ Tier II Grinding Lootbox");
					e.getEntity().getKiller().sendMessage("");
					e.getEntity().getKiller().playSound(e.getEntity().getKiller().getLocation(), Sound.VILLAGER_YES, 5, 5);
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mc give " + player + " Grinding2 1");
					throw new IllegalArgumentException("");
				} else if (num >= 0 && num <= 0.50) {
					e.getEntity().getKiller().sendMessage("");
					e.getEntity().getKiller().sendMessage("§b§l** TIER I REWARD **");
					e.getEntity().getKiller().sendMessage("§b+ Tier I Grinding Lootbox");
					e.getEntity().getKiller().sendMessage("");
					e.getEntity().getKiller().playSound(e.getEntity().getKiller().getLocation(), Sound.VILLAGER_YES, 5, 5);
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mc give " + player + " Grinding1 1");
					throw new IllegalArgumentException("");
				}

			} catch	(IllegalArgumentException ex) {}
		}
	}
}
