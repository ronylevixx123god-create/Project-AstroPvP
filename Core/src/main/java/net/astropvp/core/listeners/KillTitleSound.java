package net.astropvp.core.listeners;

import com.cosmicpvp.cosmicutils.utils.CC;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.github.paperspigot.Title;

public class KillTitleSound implements Listener {

    @EventHandler
    public void onPlayerKiller(EntityDeathEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        if (!(e.getEntity().getKiller() instanceof Player)) return;

        Player killer = e.getEntity().getKiller();
        Player killed = (Player) e.getEntity();

        killer.sendTitle(new Title("", CC.translate("&a&lKILLED &f") + killed.getName(), 20, 50, 20));
        killer.getWorld().playSound(e.getEntity().getLocation(), Sound.WITHER_SHOOT, 3.0F, 10.0F);


    }

}

