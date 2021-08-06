package me.astropvp.itemflip.tasks;

import me.astropvp.itemflip.AstroItemFlipPlugin;
import me.astropvp.itemflip.data.ItemFlipData;
import me.astropvp.itemflip.data.ItemFlipPlayerData;
import me.astropvp.itemflip.inventory.ItemFlipTradeInventory;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class TradeCountdownTask extends BukkitRunnable {
    private ItemFlipData data;

    public TradeCountdownTask(ItemFlipData data, Inventory inventory) {
        this.data = data;
        this.inventory = inventory;
    }

    private Inventory inventory;

    public void run() {
        if (!ItemFlipData.isInMemory(this.data)) {
            cancel();
            return;
        }
        if (!((ItemFlipPlayerData) this.data.getData().get(0)).isAccepted() || !((ItemFlipPlayerData) this.data.getData().get(1)).isAccepted()) {
            this.data.setCountdown(-1);
            cancel();
            return;
        }
        Inventory inventory = ItemFlipTradeInventory.getInventory(this.data);
        for (int i = 0; i < inventory.getSize(); i++) {
            this.inventory.setItem(i, inventory.getItem(i));
        }
        Player sender = ((ItemFlipPlayerData) this.data.getData().get(0)).getPlayer();
        Player receiver = ((ItemFlipPlayerData) this.data.getData().get(1)).getPlayer();
        sender.updateInventory();
        receiver.updateInventory();
        sender.playSound(sender.getLocation(), Sound.ORB_PICKUP, 25.0F, 25.0F);
        receiver.playSound(receiver.getLocation(), Sound.ORB_PICKUP, 25.0F, 25.0F);
        this.data.setCountdown(this.data.getCountdown() - 1);
        if (this.data.getCountdown() <= -1) {
            (new ItemFlipTask(this.data)).runTaskTimer((Plugin) AstroItemFlipPlugin.getInstance(), 5L, 5L);
            cancel();
            return;
        }
    }
}
