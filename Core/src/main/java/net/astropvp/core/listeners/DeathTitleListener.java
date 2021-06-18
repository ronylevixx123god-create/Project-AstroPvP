package net.astropvp.core.listeners;

import com.cosmicpvp.cosmicutils.utils.CC;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.github.paperspigot.Title;

public class DeathTitleListener implements Listener {
    @EventHandler
    public void onDeath(EntityDeathEvent e) {
        if (e.getEntity() == null) return;
        if (!(e.getEntity() instanceof Player)) return;


        Player player = ((Player) e.getEntity()).getPlayer();

        Player killer = e.getEntity().getKiller();

        if (!(killer instanceof Player)) return;

        player.getLocation().getWorld().strikeLightningEffect(player.getLocation());
        killer.sendTitle(new Title(CC.translate("&a&lKILLED &f") + player.getName(), "", 10, 20, 10));

    }
}
