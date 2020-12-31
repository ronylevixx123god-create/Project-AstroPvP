package me.ily2bb.slotbot.managers;

import org.bukkit.inventory.*;
import org.bukkit.entity.*;
import me.ily2bb.slotbot.*;
import me.ily2bb.slotbot.objects.*;
import org.bukkit.enchantments.*;
import me.ily2bb.slotbot.utils.*;
import org.bukkit.*;
import org.bukkit.plugin.*;
import org.bukkit.scheduler.*;

public class SlotBotManager
{
    public static int getSlotBotTickets(final Inventory inventory) {
        int total = 0;
        final ItemStack item = LootTable.createRoller(0);
        for (int i = 0; i < inventory.getSize(); ++i) {
            final ItemStack x = inventory.getItem(i);
            if (x != null && x.equals((Object)item)) {
                ++total;
            }
        }
        return total;
    }
    
    public static void startRolling(final Player player, final Inventory inventory, final int slots) {
        if (slots >= 1) {
            Inventories.startRollingInColumn(player, inventory, 2);
        }
        if (slots >= 2) {
            Inventories.startRollingInColumn(player, inventory, 3);
        }
        if (slots >= 3) {
            Inventories.startRollingInColumn(player, inventory, 4);
        }
        if (slots >= 4) {
            Inventories.startRollingInColumn(player, inventory, 5);
        }
        if (slots >= 5) {
            Inventories.startRollingInColumn(player, inventory, 6);
        }
        final BukkitScheduler scheduler = Bukkit.getScheduler();
        scheduler.scheduleSyncDelayedTask((Plugin)SlotBotPlugin.getInstance(), (Runnable)new BukkitRunnable() {
            int time = 10;
            
            public void run() {
                for (int i = 18; i <= 26; ++i) {
                    ItemStack x = inventory.getItem(i);
                    if (x.getType() == Material.STAINED_GLASS_PANE) {
                        if (!x.containsEnchantment((Enchantment)new Glow(70))) {
                            x = Utils.addGlow(x);
                        }
                        else {
                            x = Utils.removeGlow(x);
                        }
                        inventory.setItem(i, x);
                    }
                }
                if (slots <= 4) {
                    inventory.setItem(51, LootTable.createPane(7));
                }
                if (slots <= 2) {
                    inventory.setItem(49, LootTable.createPane(7));
                }
                player.playSound(player.getLocation(), Sound.ORB_PICKUP, 100.0f, 100.0f);
                player.updateInventory();
                --this.time;
                if (this.time <= 0) {
                    Bukkit.getScheduler().runTaskLater((Plugin)SlotBotPlugin.getInstance(), () -> player.closeInventory(), 30L);
                    this.cancel();
                }
                int x2 = 0;
                for (int j = 2; j <= 6; ++j) {
                    if (player.hasMetadata("slot_rolling_" + j)) {
                        ++x2;
                    }
                }
                if (x2 == 0) {
                    this.cancel();
                }
            }
        }.runTaskTimer((Plugin)SlotBotPlugin.getInstance(), 10L, 10L));
    }
}
