/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 */
package com.compilex.CosmicOutposts.outpost.tasks;

import com.compilex.CosmicOutposts.outpost.Outpost;
import com.compilex.CosmicOutposts.utils.ScoreboardUtils;
import org.bukkit.entity.Player;

public class ScoreboardTask
implements Runnable {
    private Outpost outpost;

    public ScoreboardTask(Outpost outpost) {
        this.outpost = outpost;
    }

    @Override
    public void run() {
        for (Player p : this.outpost.getWorld().getPlayers()) {
            try {
                ScoreboardUtils.updateScoreboard(p);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

