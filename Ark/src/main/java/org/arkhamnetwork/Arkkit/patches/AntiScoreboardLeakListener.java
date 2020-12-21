/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.v1_7_R4.ScoreboardServer
 *  org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer
 *  org.bukkit.craftbukkit.v1_7_R4.scoreboard.CraftScoreboard
 *  org.bukkit.craftbukkit.v1_7_R4.scoreboard.CraftScoreboardManager
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerQuitEvent
 */
package org.arkhamnetwork.Arkkit.patches;

import net.minecraft.server.v1_7_R4.ScoreboardServer;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_7_R4.scoreboard.CraftScoreboard;
import org.bukkit.craftbukkit.v1_7_R4.scoreboard.CraftScoreboardManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

final class AntiScoreboardLeakListener
implements Listener {
    AntiScoreboardLeakListener() {
    }

    @EventHandler(priority=EventPriority.MONITOR)
    public void onPlayerQuit(PlayerQuitEvent e) {
        CraftPlayer cp = (CraftPlayer)e.getPlayer();
        CraftScoreboard sb = CraftScoreboardManager.get().getPlayerBoard(cp);
        ScoreboardServer ss = (ScoreboardServer)sb.getHandle();
        if (!ss.mainScoreboard && ss.entityList.size() <= 1) {
            CraftScoreboardManager.get().scoreboards.remove((Object)sb);
            CraftScoreboardManager.get().playerBoards.remove((Object)cp);
        }
    }
}

