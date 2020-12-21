/*
 * Decompiled with CFR 0.150.
 */
package com.sk89q.worldguard.chest;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public interface ChestProtection {
    public boolean isProtected(Block var1, Player var2);

    public boolean isProtectedPlacement(Block var1, Player var2);

    public boolean isAdjacentChestProtected(Block var1, Player var2);

    @Deprecated
    public boolean isChest(Material var1);

    public boolean isChest(int var1);
}

