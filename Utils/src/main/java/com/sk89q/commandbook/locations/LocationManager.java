/*
 * Decompiled with CFR 0.150.
 */
package com.sk89q.commandbook.locations;

import java.io.IOException;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public interface LocationManager<T> {
    public void castWorld(World var1);

    public void load() throws IOException;

    public void save() throws IOException;

    public T get(String var1);

    public void updateWorlds();

    public T create(String var1, Location var2, Player var3);

    public boolean remove(String var1);

    public List<T> getLocations();
}

