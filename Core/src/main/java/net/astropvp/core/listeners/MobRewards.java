package net.astropvp.core.listeners;

import com.cosmicpvp.cosmicutils.utils.CC;
import net.astropvp.core.utils.GrindingMessagesUtil;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class MobRewards implements Listener {
    public void onBlazeDeath(Entity e, Player p) {
        if (e instanceof Blaze && (p instanceof Player)) {
            String player = p.getName();
            double num = Math.random();
            try {
                if (num >= 0 && num <= 0.035) {
                    if (GrindingMessagesUtil.isPlayerIndicating(p)) {
                        p.sendMessage(CC.translate("&b&lGrinding &f&l➥ &a+ Grinding Tier III Lootbox"));
                        p.playSound(p.getLocation(), Sound.VILLAGER_YES, 5, 5);
                    }
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mc give " + player + " Grinding3 1");
                    return;
                } else if (num >= 0 && num <= 0.15) {
                    if (GrindingMessagesUtil.isPlayerIndicating(p)) {
                        p.sendMessage(CC.translate("&b&lGrinding &f&l➥ &a+ Grinding Tier II Lootbox"));
                        p.playSound(p.getLocation(), Sound.VILLAGER_YES, 5, 5);
                    }
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mc give " + player + " Grinding2 1");
                    return;
                } else if (num >= 0 && num <= 0.25) {
                    if (GrindingMessagesUtil.isPlayerIndicating(p)) {
                        p.sendMessage(CC.translate("&b&lGrinding &f&l➥ &a+ Grinding Tier I Lootbox"));
                        p.playSound(p.getLocation(), Sound.VILLAGER_YES, 5, 5);
                    }
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mc give " + player + " Grinding1 1");
                    return;
                }

            } catch (IllegalArgumentException ex) {
            }
        }
    }

}
