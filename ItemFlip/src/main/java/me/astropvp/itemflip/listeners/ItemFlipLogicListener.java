package me.astropvp.itemflip.listeners;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import me.astropvp.itemflip.AstroItemFlipPlugin;
import me.astropvp.itemflip.data.ItemFlipData;
import me.astropvp.itemflip.data.ItemFlipPlayerData;
import me.astropvp.itemflip.enums.Color;
import me.astropvp.itemflip.inventory.ItemFlipTradeInventory;
import me.astropvp.itemflip.struct.ItemFlipRequest;
import me.astropvp.itemflip.tasks.TradeCountdownTask;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class ItemFlipLogicListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onClick(InventoryClickEvent event) {
        List<Integer> slots;
        List<ItemStack> items;
        if (event.getInventory().getTitle().equals(CC.Gray + "Active Item Flip")) {
            event.setCancelled(true);
            return;
        }
        if (event.getInventory().getTitle().contentEquals(CC.Gray + "Item Flip Inventory"))
            event.setCancelled(true);
        Player player = (Player)event.getWhoClicked();
        ItemFlipData data = ItemFlipData.findItemFlipData(player);
        if (data == null)
            return;
        event.setCancelled(true);
        if (data.isActive())
            return;
        int slot = event.getSlot();
        Inventory inventory = event.getInventory();
        ItemStack item = event.getCurrentItem();
        if (((ItemFlipPlayerData)data.getData().get(0)).getPlayer() == player) {
            slots = Arrays.asList(new Integer[] {
                    Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3), Integer.valueOf(9), Integer.valueOf(10), Integer.valueOf(11), Integer.valueOf(12), Integer.valueOf(18), Integer.valueOf(19), Integer.valueOf(20),
                    Integer.valueOf(21), Integer.valueOf(27), Integer.valueOf(28), Integer.valueOf(29), Integer.valueOf(30) });
            items = ((ItemFlipPlayerData)data.getData().get(0)).getItems();
        } else {
            slots = Arrays.asList(new Integer[] {
                    Integer.valueOf(5), Integer.valueOf(6), Integer.valueOf(7), Integer.valueOf(14), Integer.valueOf(15), Integer.valueOf(16), Integer.valueOf(17), Integer.valueOf(23), Integer.valueOf(24), Integer.valueOf(25),
                    Integer.valueOf(26), Integer.valueOf(32), Integer.valueOf(33), Integer.valueOf(34), Integer.valueOf(35) });
            items = ((ItemFlipPlayerData)data.getData().get(1)).getItems();
        }
        if (inventory != event.getClickedInventory()) {
            if (item == null || item.getType() == Material.AIR)
                return;
            for (Iterator<Integer> iterator = slots.iterator(); iterator.hasNext(); ) {
                int pos = ((Integer)iterator.next()).intValue();
                if (inventory.getItem(pos) == null || inventory.getItem(pos).getType() == Material.AIR) {
                    player.playSound(player.getLocation(), Sound.ORB_PICKUP, 50.0F, 50.0F);
                    items.add(event.getCurrentItem().clone());
                    event.setCurrentItem(null);
                    ItemFlipPlayerData flip_data = (player == ((ItemFlipPlayerData)data.getData().get(0)).getPlayer()) ? data.getData().get(0) : data.getData().get(1);
                    flip_data.setAccepted(false);
                    data.setCountdown(-1);
                    Inventory refresh_inventory = ItemFlipTradeInventory.getInventory(data);
                    for (int i = 0; i < refresh_inventory.getSize(); i++) {
                        ItemStack new_item = refresh_inventory.getItem(i);
                        inventory.setItem(i, new_item);
                    }
                    ((ItemFlipPlayerData)data.getData().get(0)).getPlayer().updateInventory();
                    ((ItemFlipPlayerData)data.getData().get(1)).getPlayer().updateInventory();
                    return;
                }
            }
            return;
        }
        if (item != null && item.getType() != Material.AIR && slots.contains(Integer.valueOf(slot))) {
            items.remove(item);
            event.setCurrentItem(null);
            Inventory refresh_inventory = ItemFlipTradeInventory.getInventory(data);
            Bukkit.getScheduler().runTaskLater((Plugin)AstroItemFlipPlugin.getInstance(), () -> player.getInventory().addItem(new ItemStack[] { item }), 20L);
            ItemFlipPlayerData flip_data = (player == ((ItemFlipPlayerData)data.getData().get(0)).getPlayer()) ? data.getData().get(0) : data.getData().get(1);
            flip_data.setAccepted(false);
            data.setCountdown(-1);
            player.playSound(player.getLocation(), Sound.ORB_PICKUP, 50.0F, 50.0F);
            for (int i = 0; i < refresh_inventory.getSize(); i++) {
                ItemStack new_item = refresh_inventory.getItem(i);
                inventory.setItem(i, new_item);
            }
            ((ItemFlipPlayerData)data.getData().get(0)).getPlayer().updateInventory();
            ((ItemFlipPlayerData)data.getData().get(1)).getPlayer().updateInventory();
            return;
        }
        if (item != null && item.getType() != Material.AIR && (new NBTWrapper(item)).hasKey("item_flip_color")) {
            Color color = Color.valueOf((new NBTWrapper(item)).getString("item_flip_color"));
            ItemFlipPlayerData flip_data = (player == ((ItemFlipPlayerData)data.getData().get(0)).getPlayer()) ? data.getData().get(0) : data.getData().get(1);
            if (color == ((ItemFlipPlayerData)data.getData().get(0)).getColor() || color == ((ItemFlipPlayerData)data.getData().get(1)).getColor())
                return;
            flip_data.setColor(color);
            Inventory refresh_inventory = ItemFlipTradeInventory.getInventory(data);
            for (int i = 0; i < refresh_inventory.getSize(); i++) {
                ItemStack new_item = refresh_inventory.getItem(i);
                inventory.setItem(i, new_item);
            }
            ((ItemFlipPlayerData)data.getData().get(0)).getPlayer().updateInventory();
            ((ItemFlipPlayerData)data.getData().get(1)).getPlayer().updateInventory();
            return;
        }
        if ((slot == 0 && player == ((ItemFlipPlayerData)data.getData().get(0)).getPlayer()) || (slot == 8 && player == ((ItemFlipPlayerData)data.getData().get(1)).getPlayer())) {
            if (data.getCountdown() != -1 && (!((ItemFlipPlayerData)data.getData().get(0)).isAccepted() || !((ItemFlipPlayerData)data.getData().get(1)).isAccepted()))
                return;
            ItemFlipPlayerData flip_data = (player == ((ItemFlipPlayerData)data.getData().get(0)).getPlayer()) ? data.getData().get(0) : data.getData().get(1);
            if (flip_data.getItems().size() == 0) {
                player.playSound(player.getLocation(), Sound.ITEM_BREAK, 0.75F, 2.0F);
                player.sendMessage(CC.RedB + "(!) " + CC.Red + "You must wager atleast one item!");
                return;
            }
            if (((ItemFlipPlayerData)data.getData().get(0)).isAccepted() && ((ItemFlipPlayerData)data.getData().get(1)).isAccepted())
                data.setCountdown(-1);
            flip_data.setAccepted(!flip_data.isAccepted());
            player.playSound(player.getLocation(), Sound.ORB_PICKUP, 25.0F, 25.0F);
            Inventory refresh_inventory = ItemFlipTradeInventory.getInventory(data);
            for (int i = 0; i < refresh_inventory.getSize(); i++) {
                ItemStack new_item = refresh_inventory.getItem(i);
                inventory.setItem(i, new_item);
            }
            ((ItemFlipPlayerData)data.getData().get(0)).getPlayer().updateInventory();
            ((ItemFlipPlayerData)data.getData().get(1)).getPlayer().updateInventory();
            if (((ItemFlipPlayerData)data.getData().get(0)).isAccepted() && ((ItemFlipPlayerData)data.getData().get(1)).isAccepted()) {
                data.setCountdown(10);
                (new TradeCountdownTask(data, inventory)).runTaskTimer((Plugin)AstroItemFlipPlugin.getInstance(), 20L, 20L);
            }
            return;
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        ItemFlipData data = ItemFlipData.findItemFlipData(player);
        if (data != null && data.isActive())
            data.remove();
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = (Player)event.getPlayer();
        ItemFlipData data = ItemFlipData.findItemFlipData(player);
        if (data == null || data.isActive())
            return;
        Player other = (player == ((ItemFlipPlayerData)data.getData().get(0)).getPlayer()) ? ((ItemFlipPlayerData)data.getData().get(1)).getPlayer() : ((ItemFlipPlayerData)data.getData().get(0)).getPlayer();
        data.remove();
        other.sendMessage(CC.RedB + "(!) " + CC.Red + player.getName() + " has closed out of the Item Flip Inventory!");
        player.sendMessage(CC.RedB + "(!) " + CC.Red + "You have closed out of the item flip inventory!");
        other.closeInventory();
        ((ItemFlipPlayerData)data.getData().get(0)).getItems().stream().forEach(item -> ((ItemFlipPlayerData)data.getData().get(0)).getPlayer().getInventory().addItem(new ItemStack[] { item }));
        ((ItemFlipPlayerData)data.getData().get(1)).getItems().stream().forEach(item -> ((ItemFlipPlayerData)data.getData().get(1)).getPlayer().getInventory().addItem(new ItemStack[] { item }));
    }

    @EventHandler
    public void onFlipRequestLogout(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        ItemFlipRequest request = ItemFlipRequest.findInstance(player);
        if (request != null)
            request.remove();
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent e) {
        if (e.getTo().getWorld().getName().equals("world") || e.getTo().getWorld().getName().equals("flat"))

        return;
        Player player = e.getPlayer();
        ItemFlipRequest request = ItemFlipRequest.findInstance(player);
        if (request == null) return;
        Player sender = request.getSender();
        Player receiver = request.getReceiver();
        request.remove();
        if (sender.isOnline())
            sender.sendMessage(CC.RedB + "(/ITEMFLIP) " + CC.Red + "Your Item Flip Request to " + receiver.getName() + " has expired!");
        if (receiver.isOnline())
            receiver.sendMessage(CC.RedB + "(/ITEMFLIP) " + CC.Red + sender.getName() + "'s Item Flip Request to you has expired!");
    }
}
