// 
// Decompiled by Procyon v0.5.36
// 

package net.astropvp.menu;

import net.astropvp.BlackMarketAuction;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.entity.Player;
import net.astropvp.util.Utils;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.ArrayList;

import org.bukkit.inventory.InventoryHolder;
import org.bukkit.Bukkit;
import java.util.List;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;

@Deprecated
public class AuctionLootMenu implements Menu
{
    private Inventory inventory;
    private FileConfiguration config;
    private List<String> loot;
    
    public AuctionLootMenu() {
        this.inventory = Bukkit.createInventory((InventoryHolder)this, 54, "BAH Loot Table");
        this.config = BlackMarketAuction.get().getConfig();
        this.loot = ((this.config.getList("items") == null) ? new ArrayList<String>() : this.config.getStringList("items"));
        final AuctionLootMenu instance = this;
        this.updateInventory();
        new BukkitRunnable() {
            public void run() {
                instance.saveLoot();
            }
        }.runTaskTimer((Plugin)BlackMarketAuction.get(), 0L, 1200L);
    }
    
    public void saveLoot() {
        this.config.set("items", (Object)this.getLoot());
        BlackMarketAuction.get().saveConfig();
    }
    
    public List<String> getLoot() {
        return this.loot;
    }
    
    public void updateInventory() {
        final List<String> loot = this.getLoot();
        if (loot == null) {
            return;
        }
        this.inventory.clear();
        for (int i = 0; i < loot.size(); ++i) {
            final String raw = loot.get(i);
            this.inventory.setItem(i, Utils.getItem(raw));
        }
    }
    
    @Override
    public void onClick(final Player player, final InventoryClickEvent event) {
        event.setCancelled(true);
        final ItemStack item = event.getCurrentItem();
        if (event.getClickedInventory().getType() == InventoryType.PLAYER) {
            if (item != null && item.getType() != Material.AIR) {
                this.loot.add(Utils.toBase64(new ItemStack[] { item }, 9));
                this.updateInventory();
            }
        }
        else if (item != null && item.getType() != Material.AIR) {
            this.loot.remove(event.getSlot());
            this.updateInventory();
        }
    }
    
    @Override
    public void onOpen(final Player player) {
    }
    
    @Override
    public void onClose(final Player player) {
    }
    
    public Inventory getInventory() {
        return this.inventory;
    }
}
