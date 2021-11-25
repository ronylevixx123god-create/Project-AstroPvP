// 
// Decompiled by Procyon v0.5.36
// 

package net.astropvp.inventory;

import net.astropvp.config.ShieldConfig;
import net.astropvp.shield.FactionShield;
import com.massivecraft.factions.FPlayers;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.ItemStack;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import java.util.List;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import net.astropvp.FactionShieldsPlugin;
import java.util.HashMap;
import java.util.UUID;
import java.util.Map;
import org.bukkit.inventory.Inventory;
import org.bukkit.event.Listener;

public class ConfirmInventory extends ConfigInventory implements Listener
{
    private Inventory inventory;
    private Map<UUID, ItemData> confirmData;
    
    public ConfirmInventory(final String configName) {
        super(configName);
        this.confirmData = new HashMap<UUID, ItemData>();
        FactionShieldsPlugin.getInstance().registerListener((Listener)this);
    }
    
    public void buildInventory(final Player player, final String time, final ItemData data) {
        this.inventory = Bukkit.createInventory((InventoryHolder)null, this.size, this.title);
        this.items.keySet().forEach(slot -> {
            ItemStack converted = this.items.get(slot).getItem().clone();
            ItemMeta meta = converted.getItemMeta();
            meta.setLore((List)this.convertPlaceholder(meta.getLore(), "%time%", time));
            converted.setItemMeta(meta);
            this.inventory.setItem((int)slot, converted);
            return;
        });
        if (this.fill) {
            for (int i = 0; i < this.inventory.getSize(); ++i) {
                final ItemStack item = this.inventory.getItem(i);
                if (item == null || item.getType() == Material.AIR) {
                    this.inventory.setItem(i, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)DyeColor.BLACK.ordinal()));
                }
            }
        }
        this.confirmData.put(player.getUniqueId(), data);
        player.openInventory(this.inventory);
    }
    
    @EventHandler
    public void onInventoryClick(final InventoryClickEvent event) {
        final FactionShieldsPlugin plugin = FactionShieldsPlugin.getInstance();
        if (event.getClickedInventory() == null) {
            return;
        }
        if (event.getClickedInventory().getTitle().equalsIgnoreCase(this.title)) {
            event.setCancelled(true);
            final Player player = (Player)event.getWhoClicked();
            if (this.confirmData.containsKey(player.getUniqueId())) {
                final ItemData itemData = this.confirmData.get(player.getUniqueId());
                final ItemData buttonData = this.items.get(event.getSlot());
                final Action action;
                if (buttonData != null && (action = buttonData.getAction()) != null) {
                    switch (action) {
                        case CONFIRM: {
                            this.confirmShield((Player)event.getWhoClicked(), itemData);
                            break;
                        }
                        case CANCEL: {
                            event.getWhoClicked().openInventory(plugin.getInventoryHandler().getDefaultInventory());
                            break;
                        }
                        default: {
                            System.out.println("Not confirm or cancel?");
                            break;
                        }
                    }
                    this.confirmData.remove(player.getUniqueId());
                }
            }
        }
    }
    
    private void confirmShield(final Player player, final ItemData data) {
        final FactionShield factionShield = new FactionShield(FPlayers.i.get(player).getFactionId(), data);
        FactionShieldsPlugin.getInstance().getShieldHandler().addShield(factionShield);
        player.closeInventory();
        ShieldConfig.REGION_REQUIRED.forEach(player::sendMessage);
    }
    
    public Map<Integer, ItemData> getMenuItems() {
        return this.items;
    }
}
