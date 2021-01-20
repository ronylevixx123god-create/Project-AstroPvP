package me.ifamasssxd.cosmicbosses.listeners;

import com.gmail.nossr50.events.experience.McMMOPlayerXpGainEvent;
import me.ifamasssxd.cosmicbosses.CosmicBosses;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class McmmoListener implements Listener {
    @EventHandler
    public void onXPGain(final McMMOPlayerXpGainEvent e) {
        if (e.getPlayer().hasMetadata("no_exp_gain")) {
            e.setCancelled(true);
            e.setRawXpGained(0.0f);
            e.getPlayer().removeMetadata("no_exp_gain", (Plugin) CosmicBosses.getInstance());
        }
    }
}
