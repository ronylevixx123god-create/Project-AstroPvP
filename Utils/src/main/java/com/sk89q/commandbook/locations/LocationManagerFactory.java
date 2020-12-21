/*
 * Decompiled with CFR 0.150.
 */
package com.sk89q.commandbook.locations;

import org.bukkit.World;

public interface LocationManagerFactory<T> {
    public T createManager();

    public T createManager(World var1);
}

