/*
 * Decompiled with CFR 0.150.
 */
package org.bukkit.entity;

import org.bukkit.entity.Animals;
import org.bukkit.entity.Tameable;
import org.bukkit.entity.Vehicle;
import org.bukkit.inventory.HorseInventory;
import org.bukkit.inventory.InventoryHolder;

public interface Horse
extends Animals,
Vehicle,
InventoryHolder,
Tameable {
    public Variant getVariant();

    public void setVariant(Variant var1);

    public Color getColor();

    public void setColor(Color var1);

    public Style getStyle();

    public void setStyle(Style var1);

    public boolean isCarryingChest();

    public void setCarryingChest(boolean var1);

    public int getDomestication();

    public void setDomestication(int var1);

    public int getMaxDomestication();

    public void setMaxDomestication(int var1);

    public double getJumpStrength();

    public void setJumpStrength(double var1);

    @Override
    public HorseInventory getInventory();

    public static enum Style {
        NONE,
        WHITE,
        WHITEFIELD,
        WHITE_DOTS,
        BLACK_DOTS;

    }

    public static enum Color {
        WHITE,
        CREAMY,
        CHESTNUT,
        BROWN,
        BLACK,
        GRAY,
        DARK_BROWN;

    }

    public static enum Variant {
        HORSE,
        DONKEY,
        MULE,
        UNDEAD_HORSE,
        SKELETON_HORSE;

    }
}

