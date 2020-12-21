/*
 * Decompiled with CFR 0.150.
 */
package org.bukkit.scoreboard;

import java.util.Set;
import org.bukkit.OfflinePlayer;
import org.bukkit.scoreboard.Scoreboard;

public interface Team {
    public String getName() throws IllegalStateException;

    public String getDisplayName() throws IllegalStateException;

    public void setDisplayName(String var1) throws IllegalStateException, IllegalArgumentException;

    public String getPrefix() throws IllegalStateException;

    public void setPrefix(String var1) throws IllegalStateException, IllegalArgumentException;

    public String getSuffix() throws IllegalStateException;

    public void setSuffix(String var1) throws IllegalStateException, IllegalArgumentException;

    public boolean allowFriendlyFire() throws IllegalStateException;

    public void setAllowFriendlyFire(boolean var1) throws IllegalStateException;

    public boolean canSeeFriendlyInvisibles() throws IllegalStateException;

    public void setCanSeeFriendlyInvisibles(boolean var1) throws IllegalStateException;

    public Set<OfflinePlayer> getPlayers() throws IllegalStateException;

    public int getSize() throws IllegalStateException;

    public Scoreboard getScoreboard();

    public void addPlayer(OfflinePlayer var1) throws IllegalStateException, IllegalArgumentException;

    public boolean removePlayer(OfflinePlayer var1) throws IllegalStateException, IllegalArgumentException;

    public void unregister() throws IllegalStateException;

    public boolean hasPlayer(OfflinePlayer var1) throws IllegalArgumentException, IllegalStateException;
}

