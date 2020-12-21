/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.v1_7_R4.EntityPlayer
 *  net.minecraft.server.v1_7_R4.ScoreboardServer
 *  org.bukkit.Bukkit
 *  org.bukkit.craftbukkit.v1_7_R4.scoreboard.CraftScoreboard
 *  org.bukkit.craftbukkit.v1_7_R4.scoreboard.CraftScoreboardManager
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import java.util.ArrayList;
import net.minecraft.server.v1_7_R4.EntityPlayer;
import net.minecraft.server.v1_7_R4.ScoreboardServer;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.AntiScoreboardLeakListener;
import org.arkhamnetwork.Arkkit.patches.AntiScoreboardLeakTask;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_7_R4.scoreboard.CraftScoreboard;
import org.bukkit.craftbukkit.v1_7_R4.scoreboard.CraftScoreboardManager;
import org.bukkit.plugin.Plugin;

public class AntiScoreboardLeak
extends ArkPlugin {
    public AntiScoreboardLeak(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        try {
            AntiScoreboardLeak.auditScoreboardList();
        }
        catch (Exception err) {
            err.printStackTrace();
        }
        this.registerListener(new AntiScoreboardLeakListener());
        this.registerTask(new AntiScoreboardLeakTask().runTaskTimer((Plugin)Arkkit.get(), 6000L, 6000L), AntiScoreboardLeak.class);
    }

    @Override
    public void disable() {
    }

    protected static void auditScoreboardList() {
        ArrayList<CraftScoreboard> toRemove = new ArrayList<CraftScoreboard>();
        for (CraftScoreboard sb : CraftScoreboardManager.get().scoreboards) {
            ScoreboardServer ss = (ScoreboardServer)sb.getHandle();
            if (ss.mainScoreboard) continue;
            boolean playersOffline = true;
            for (EntityPlayer ep : ss.entityList) {
                if (ep == null || Bukkit.getPlayer((String)ep.getName()) == null) continue;
                playersOffline = false;
                break;
            }
            if (!playersOffline) continue;
            toRemove.add(sb);
        }
        for (CraftScoreboard sb : toRemove) {
            CraftScoreboardManager.get().scoreboards.remove((Object)sb);
        }
        if (toRemove.size() > 0) {
            Bukkit.getLogger().info("[AntiScoreboardLeak] Removed " + toRemove.size() + " empty scoreboard entries, new size => " + CraftScoreboardManager.get().scoreboards.size());
        }
    }
}

