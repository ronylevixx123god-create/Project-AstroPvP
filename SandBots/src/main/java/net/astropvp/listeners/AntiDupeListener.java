// 
// Decompiled by Procyon v0.5.36
// 

package net.astropvp.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.entity.Entity;
import org.bukkit.Chunk;
import net.astropvp.managers.SandBotManager;
import org.bukkit.entity.EntityType;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.Bukkit;
import net.astropvp.SandBots;
import org.bukkit.event.Listener;

public class AntiDupeListener implements Listener
{
    private SandBots plugin;
    
    public AntiDupeListener(final SandBots plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents((Listener)this, (Plugin)plugin);
    }
    
    @EventHandler
    public void OnChunkUnload(final ChunkUnloadEvent e) {
        final Chunk sandbtChunk = e.getChunk();
    Label_0115:
        for (final Entity tmp : sandbtChunk.getEntities()) {
            if (tmp.getType().equals((Object)EntityType.ARMOR_STAND)) {
                for (final SandBotManager bot : SandBots.sandBots) {
                    if (bot.getSandbotUUID().equals(tmp.getUniqueId())) {
                        e.setCancelled(true);
                        break Label_0115;
                    }
                }
            }
        }
    }
}
