/*
 * Decompiled with CFR 0.150.
 */
package org.bukkit.entity;

import org.bukkit.Art;
import org.bukkit.entity.Hanging;

public interface Painting
extends Hanging {
    public Art getArt();

    public boolean setArt(Art var1);

    public boolean setArt(Art var1, boolean var2);
}

