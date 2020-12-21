/*
 * Decompiled with CFR 0.150.
 */
package org.bukkit.inventory.meta;

import org.bukkit.inventory.meta.ItemMeta;

public interface SkullMeta
extends ItemMeta {
    public String getOwner();

    public boolean hasOwner();

    public boolean setOwner(String var1);

    @Override
    public SkullMeta clone();
}

