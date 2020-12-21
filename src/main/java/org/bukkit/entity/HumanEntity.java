/*
 * Decompiled with CFR 0.150.
 */
package org.bukkit.entity;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.permissions.Permissible;

public interface HumanEntity
extends LivingEntity,
AnimalTamer,
Permissible,
InventoryHolder {
    @Override
    public String getName();

    @Override
    public PlayerInventory getInventory();

    public Inventory getEnderChest();

    public boolean setWindowProperty(InventoryView.Property var1, int var2);

    public InventoryView getOpenInventory();

    public InventoryView openInventory(Inventory var1);

    public InventoryView openWorkbench(Location var1, boolean var2);

    public InventoryView openEnchanting(Location var1, boolean var2);

    public void openInventory(InventoryView var1);

    public void closeInventory();

    public ItemStack getItemInHand();

    public void setItemInHand(ItemStack var1);

    public ItemStack getItemOnCursor();

    public void setItemOnCursor(ItemStack var1);

    public boolean isSleeping();

    public int getSleepTicks();

    public GameMode getGameMode();

    public void setGameMode(GameMode var1);

    public boolean isBlocking();

    public int getExpToLevel();
}

