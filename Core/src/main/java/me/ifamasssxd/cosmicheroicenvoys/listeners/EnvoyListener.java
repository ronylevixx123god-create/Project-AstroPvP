/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  me.ifamasssxd.cosmicconquest.CosmicConquestAPI
 *  org.bukkit.Bukkit
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityDeathEvent
 */
package me.ifamasssxd.cosmicheroicenvoys.listeners;

import java.util.concurrent.ThreadLocalRandom;
import me.ifamasssxd.cosmicconquest.CosmicConquestAPI;
import me.ifamasssxd.cosmicheroicenvoys.managers.EnvoyManager;
import net.astropvp.core.AstroCore;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EnvoyListener
implements Listener {
    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        EnvoyManager manager = AstroCore.getInstance().getEnvoyManager();
        if (manager.getBossUUID() != null && manager.getBossUUID().equals(event.getEntity().getUniqueId())) {
            manager.spawnEnvoyBlocks(AstroCore.getInstance().getEnvoyManager().getBossLocation());
            if (Bukkit.getPluginManager().isPluginEnabled("CosmicConquests")) {
                event.getEntity().getWorld().dropItem(event.getEntity().getLocation(), CosmicConquestAPI.createFactionToken((int)(ThreadLocalRandom.current().nextInt(5) + 2)));
            }
        }
    }
}

