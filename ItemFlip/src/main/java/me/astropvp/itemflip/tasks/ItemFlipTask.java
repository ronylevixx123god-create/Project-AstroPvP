 package me.astropvp.itemflip.tasks;

 import me.astropvp.itemflip.AstroItemFlipPlugin;
 import me.astropvp.itemflip.data.ItemFlipData;
 import me.astropvp.itemflip.data.ItemFlipPlayerData;
 import me.astropvp.itemflip.enums.Color;
 import me.astropvp.itemflip.inventory.ItemFlipActiveInventory;
 import org.bukkit.Bukkit;
 import org.bukkit.Sound;
 import org.bukkit.entity.Player;
 import org.bukkit.inventory.Inventory;
 import org.bukkit.inventory.ItemStack;
 import org.bukkit.plugin.Plugin;
 import org.bukkit.scheduler.BukkitRunnable;

 public class ItemFlipTask
   extends BukkitRunnable {
   private ItemFlipData data;

   public ItemFlipTask(final ItemFlipData data) {
     this.data = data;
     this.ticks = 0;
     this.inventory = ItemFlipActiveInventory.getInventory(this.data);
     this.data.setActive(true);
     ((ItemFlipPlayerData)this.data.getData().get(0)).getPlayer().closeInventory();
     ((ItemFlipPlayerData)this.data.getData().get(1)).getPlayer().closeInventory();
     Bukkit.getScheduler().runTaskLater((Plugin)AstroItemFlipPlugin.getInstance(), (Runnable)new BukkitRunnable()
         {
           public void run() {
             if (!ItemFlipData.isInMemory(data)) {
               return;
             }
             ((ItemFlipPlayerData)data.getData().get(0)).getPlayer().openInventory(ItemFlipTask.this.inventory);
             ((ItemFlipPlayerData)data.getData().get(1)).getPlayer().openInventory(ItemFlipTask.this.inventory);
           }
         },  1L);
   }
   private int ticks; private Inventory inventory;

   public void run() {
     if (!ItemFlipData.isInMemory(this.data)) {
       return;
     }
     Color color_one = ((ItemFlipPlayerData)this.data.getData().get(0)).getColor();
     Color color_two = ((ItemFlipPlayerData)this.data.getData().get(1)).getColor();
     Color new_color = (Math.random() < 0.5D) ? color_one : color_two;
     this.data.setActiveColor(new_color);
     Player sender = ((ItemFlipPlayerData)this.data.getData().get(0)).getPlayer();
     Player receiver = ((ItemFlipPlayerData)this.data.getData().get(1)).getPlayer();
     sender.playSound(sender.getLocation(), Sound.ORB_PICKUP, 25.0F, 25.0F);
     receiver.playSound(receiver.getLocation(), Sound.ORB_PICKUP, 25.0F, 25.0F);
     Inventory refresh_inventory = ItemFlipActiveInventory.getInventory(this.data);
     for (int i = 0; i < refresh_inventory.getSize(); i++) {
       ItemStack new_item = refresh_inventory.getItem(i);
       this.inventory.setItem(i, new_item);
     }
     sender.updateInventory();
     receiver.updateInventory();
     this.ticks++;
     if (this.ticks >= 16) {
       cancel();
       this.data.remove();
       return;
     }
   }
 }
