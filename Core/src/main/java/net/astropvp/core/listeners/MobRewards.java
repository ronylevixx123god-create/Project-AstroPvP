package net.astropvp.core.listeners;

import com.cosmicpvp.cosmicutils.utils.CC;
import net.astropvp.core.utils.GrindingMessagesUtil;
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
                if (num >= 0 && num <= 0.020) {
                    if (GrindingMessagesUtil.isPlayerIndicating(e.getEntity().getKiller())) {
                        e.getEntity().getKiller().sendMessage(CC.translate("&b&lGrinding &f&l➥ &a+ Grinding Tier III Lootbox"));
                        e.getEntity().getKiller().playSound(e.getEntity().getKiller().getLocation(), Sound.VILLAGER_YES, 5, 5);
                    }
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mc give " + player + " Grinding3 1");
                    return;
                } else if (num >= 0 && num <= 0.15) {
                    if (GrindingMessagesUtil.isPlayerIndicating(e.getEntity().getKiller())) {
                        e.getEntity().getKiller().sendMessage(CC.translate("&b&lGrinding &f&l➥ &a+ Grinding Tier II Lootbox"));
                        e.getEntity().getKiller().playSound(e.getEntity().getKiller().getLocation(), Sound.VILLAGER_YES, 5, 5);
                    }
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mc give " + player + " Grinding2 1");
                    return;
                } else if (num >= 0 && num <= 0.25) {
                    if (GrindingMessagesUtil.isPlayerIndicating(e.getEntity().getKiller())) {
                        e.getEntity().getKiller().sendMessage(CC.translate("&b&lGrinding &f&l➥ &a+ Grinding Tier I Lootbox"));
                        e.getEntity().getKiller().playSound(e.getEntity().getKiller().getLocation(), Sound.VILLAGER_YES, 5, 5);
                    }
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mc give " + player + " Grinding1 1");
                    return;
                }

            } catch (IllegalArgumentException ex) {
            }
        }
    }
}
