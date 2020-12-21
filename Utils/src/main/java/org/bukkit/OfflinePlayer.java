/*
 * Decompiled with CFR 0.150.
 */
package org.bukkit;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Player;
import org.bukkit.permissions.ServerOperator;

public interface OfflinePlayer
extends ServerOperator,
AnimalTamer,
ConfigurationSerializable {
    public boolean isOnline();

    @Override
    public String getName();

    public boolean isBanned();

    @Deprecated
    public void setBanned(boolean var1);

    public boolean isWhitelisted();

    public void setWhitelisted(boolean var1);

    public Player getPlayer();

    public long getFirstPlayed();

    public long getLastPlayed();

    public boolean hasPlayedBefore();

    public Location getBedSpawnLocation();
}

