/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Entity
 */
package me.ifamasssxd.cosmicbosses.struct;

import java.util.Set;
import org.bukkit.entity.Entity;

public interface EntityContainer {
    public Set<Entity> getEntities();

    public void clearEntities();
}

