/*
 * Decompiled with CFR 0.150.
 */
package org.bukkit.scoreboard;

import org.bukkit.OfflinePlayer;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public interface Score {
    public OfflinePlayer getPlayer();

    public Objective getObjective();

    public int getScore() throws IllegalStateException;

    public void setScore(int var1) throws IllegalStateException;

    public Scoreboard getScoreboard();
}

