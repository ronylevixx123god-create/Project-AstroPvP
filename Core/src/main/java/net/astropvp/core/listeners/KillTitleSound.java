package net.astropvp.core.listeners;

import com.cosmicpvp.cosmicutils.utils.CC;
import net.astropvp.core.commands.ToggleKillTitleCommand;
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

        if (ToggleKillTitleCommand.hasEnabled(killer.getUniqueId())) {
            killer.sendTitle(new Title("", CC.translate("&a&lKILLED &f") + killed.getName(), 20, 50, 20));
        }


    }

}

