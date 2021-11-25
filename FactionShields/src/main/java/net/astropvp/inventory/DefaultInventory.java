// 
// Decompiled by Procyon v0.5.36
// 

package net.astropvp.inventory;

import com.massivecraft.factions.struct.Role;
import org.bukkit.event.EventHandler;
import java.util.List;
import com.massivecraft.factions.FPlayer;
import org.bukkit.inventory.Inventory;
import com.massivecraft.factions.FPlayers;
import org.bukkit.entity.Player;
import net.astropvp.config.ShieldConfig;
import org.bukkit.event.inventory.InventoryClickEvent;
import net.astropvp.FactionShieldsPlugin;
import org.bukkit.event.Listener;

public class DefaultInventory implements Listener
{
    private ConfirmInventory confirmInventory;
    private ListInventory listInventory;
    
    public DefaultInventory() {
        this.confirmInventory = new ConfirmInventory("confirm-menu");
        this.listInventory = new ListInventory("list-menu");
        FactionShieldsPlugin.getInstance().registerListener((Listener)this);
    }
    
    @EventHandler
    public void onInventoryClick(final InventoryClickEvent event) {
        final Inventory inventory = event.getClickedInventory();
        if (inventory != null && inventory.getTitle() != null && inventory.getTitle().equalsIgnoreCase(ShieldConfig.INVENTORY_TITLE)) {
            event.setCancelled(true);
            if (event.getCurrentItem() == null) {
                return;
            }
            final Player player = (Player)event.getWhoClicked();
            final FactionShieldsPlugin plugin = FactionShieldsPlugin.getInstance();
            final FPlayer fplayer = FPlayers.i.get(player);
            if (plugin.getShieldHandler().hasShield(player) && plugin.getInventoryHandler().getTimeButtons().stream().anyMatch(itemData -> itemData.getSlot() == event.getSlot())) {
                return;
            }
            final InventoryHandler inventoryHandler = FactionShieldsPlugin.getInstance().getInventoryHandler();
            if (inventoryHandler.getShieldsButtonSlot() == event.getSlot()) {
                this.listInventory.open(player);
                return;
            }
            final List<ItemData> buttons = inventoryHandler.getTimeButtons();
            final FPlayer fPlayer = FPlayers.i.get(player);
            buttons.forEach(item -> {
                if (item.getSlot() == event.getSlot()) {
                    if (fPlayer.getRole() != Role.ADMIN && fPlayer.getRole() != Role.COLEADER) {
                        player.sendMessage(ShieldConfig.NO_FACTION_PERMISSION);
                        player.closeInventory();
                    }
                    else {
                        this.confirmInventory.buildInventory((Player)event.getWhoClicked(), item.getTime(), item);
                    }
                }
            });
        }
    }
}
