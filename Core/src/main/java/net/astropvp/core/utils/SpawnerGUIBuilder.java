package net.astropvp.core.utils;

import com.earth2me.essentials.api.NoLoanPermittedException;
import com.earth2me.essentials.api.UserDoesNotExistException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public abstract class SpawnerGUIBuilder implements InventoryHolder {
    protected Inventory inventory;
    protected int amount;

    protected Player player;

    public SpawnerGUIBuilder(Player player, int amount) {
        this.player = player;
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    protected abstract String getName();

    protected abstract int getSlots();

    protected abstract void setItems();

    protected abstract ItemStack getItems(int paramInt);

    public abstract void click(InventoryClickEvent paramInventoryClickEvent) throws UserDoesNotExistException, NoLoanPermittedException;

    public void open() {
        this.inventory = Bukkit.createInventory(this, getSlots(), getName());
        setItems();
        this.player.openInventory(this.inventory);
    }
}