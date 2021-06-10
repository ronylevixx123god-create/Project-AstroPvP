package com.drtshock.playervaults.vaultmanagement;

import org.bukkit.inventory.*;

public class VaultHolder implements InventoryHolder
{
    private Inventory inventory;
    private int vaultNumber;
    
    public VaultHolder(final int vaultNumber) {
        this.vaultNumber = 0;
        this.vaultNumber = vaultNumber;
    }
    
    public int getVaultNumber() {
        return this.vaultNumber;
    }
    
    public void setInventory(final Inventory inventory) {
        this.inventory = inventory;
    }
    
    public Inventory getInventory() {
        return this.inventory;
    }
}
