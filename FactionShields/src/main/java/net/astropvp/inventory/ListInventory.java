// 
// Decompiled by Procyon v0.5.36
// 

package net.astropvp.inventory;

import com.cosmicpvp.cosmicutils.utils.CC;
import net.astropvp.shield.ShieldHandler;
import org.bukkit.DyeColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import net.astropvp.config.ShieldConfig;
import org.bukkit.Material;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.Bukkit;

import java.util.List;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.tuple.Pair;
import net.astropvp.util.ItemBuilder;
import java.util.Arrays;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.entity.Player;
import net.astropvp.FactionShieldsPlugin;
import java.util.HashMap;
import org.bukkit.inventory.ItemStack;
import net.astropvp.shield.FactionShield;
import java.util.Map;
import org.bukkit.inventory.Inventory;
import org.bukkit.event.Listener;

public class ListInventory extends ConfigInventory implements Listener
{
    private Inventory inventory;
    private Map<Integer, FactionShield> shields;
    private static final ItemStack placeholder;
    
    public ListInventory(final String configName) {
        super(configName);
        this.shields = new HashMap<>();
        FactionShieldsPlugin.getInstance().registerListener((Listener)this);
        this.update();
    }
    
    public void open(final Player player) {
        if (this.inventory == null) {
            player.openInventory(this.build());
        }
        else {
            player.openInventory(this.inventory);
        }
    }
    
    private void update() {
        new BukkitRunnable() {
            public void run() {
                ListInventory.this.updateSlots();
            }
        }.runTaskTimerAsynchronously((Plugin)FactionShieldsPlugin.getInstance(), 0L, 20L);
    }
    
    private void updateSlots() {
        if (this.inventory == null) {
            return;
        }
        final List<Integer> slots = Arrays.asList(this.shields.keySet().toArray(new Integer[0]));
        boolean removed = false;
        for (int i = slots.size() - 1; i >= 0; --i) {
            final FactionShield shield = this.shields.get(i);
            if (shield == null || shield.isDestroyed() || !shield.isEnabled()) {
                this.shields.remove(i);
                removed = true;
                break;
            }
            final ItemBuilder itemBuilder = new ItemBuilder(this.inventory.getItem(i));
            itemBuilder.setLore(this.getGlobalLore(), ImmutableList.of(Pair.of("%status%", shield.getLastElapsedString())));
            this.inventory.setItem(i, itemBuilder.build());
        }
        final ShieldHandler shieldHandler = FactionShieldsPlugin.getInstance().getShieldHandler();
        final boolean noMatch = this.shields.values().size() != shieldHandler.getFactionShields().stream().filter(FactionShield::isEnabled).count();
        if (removed || noMatch) {
            this.inventory.clear();
            this.shields.clear();
            this.populate();
        }
    }
    
    private Inventory build() {
        this.inventory = Bukkit.createInventory((InventoryHolder)null, this.size, this.title);
        this.populate();
        return this.inventory;
    }

    private void populate() {
        if (this.inventory == null)
            return;
        this.inventory.setItem(this.inventory.getSize() - 5, (new ItemBuilder(Material.BOOK))
                .setName(CC.GreenB + "Back")
                .setLore(CC.Gray + "to return to the previous page.")
                .build());
        String name;
        List<String> lore;
        if ((name = getGlobalName()) != null && (lore = getGlobalLore()) != null)
            FactionShieldsPlugin.getInstance().getShieldHandler().getFactionShields().forEach(shield -> {
                if (shield != null && !shield.isDestroyed() && shield.isEnabled()) {
                    int emptySlot = 0;
                    int lastRow = this.inventory.getSize() - 8;
                    while (emptySlot < lastRow && this.inventory.getItem(emptySlot) != null)
                        emptySlot++;
                    if (shield.getLastElapsedString() != null && emptySlot < this.inventory.getSize() - 1) {
                        ItemBuilder item = (new ItemBuilder(Material.getMaterial(ShieldConfig.ITEM_TYPES))).setName(name.replaceAll("%faction%", shield.getFaction().getTag())).setLore(lore, ImmutableList.of(Pair.of("%status%", shield.getLastElapsedString())));
                        this.inventory.setItem(emptySlot, item.build());
                        this.shields.put(emptySlot, shield);
                    }
                }
            });
        if (this.fill)
            fillBackground();
    }
    
    private void fillBackground() {
        for (int i = 0; i < this.inventory.getSize(); ++i) {
            final ItemStack item = this.inventory.getItem(i);
            if (item == null || item.getType() == Material.AIR) {
                this.inventory.setItem(i, ListInventory.placeholder);
            }
        }
    }
    
    @EventHandler
    public void onInventoryClick(final InventoryClickEvent event) {
        if (event.getClickedInventory() == null) {
            return;
        }
        if (event.getClickedInventory().getTitle().equalsIgnoreCase(this.title)) {
            event.setCancelled(true);
            final FactionShieldsPlugin plugin = FactionShieldsPlugin.getInstance();
            final Player player = (Player)event.getWhoClicked();
            final FactionShield shield = plugin.getShieldHandler().getShield(player);
            if (event.getSlot() == this.inventory.getSize() - 5) {
                Inventory returnInventory;
                if (shield != null) {
                    returnInventory = shield.getInventory();
                }
                else {
                    returnInventory = plugin.getInventoryHandler().getDefaultInventory();
                }
                player.openInventory(returnInventory);
            }
        }
    }
    
    static {
        placeholder = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)DyeColor.BLACK.ordinal());
    }
}
