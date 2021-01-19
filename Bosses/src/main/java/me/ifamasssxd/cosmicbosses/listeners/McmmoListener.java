package me.ifamasssxd.cosmicbosses.listeners;

import com.gmail.nossr50.events.experience.*;
import me.ifamasssxd.cosmicbosses.*;
import org.bukkit.plugin.*;
import org.bukkit.event.*;

public class McmmoListener implements Listener
{
    @EventHandler
    public void onXPGain(final McMMOPlayerXpGainEvent e) {
        if (e.getPlayer().hasMetadata("no_exp_gain")) {
            e.setCancelled(true);
            e.setRawXpGained(0.0f);
            e.getPlayer().removeMetadata("no_exp_gain", (Plugin)CosmicBosses.getInstance());
        }
    }
}
