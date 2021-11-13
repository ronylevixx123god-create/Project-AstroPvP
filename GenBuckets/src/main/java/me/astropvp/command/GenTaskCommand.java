package me.astropvp.command;

import java.util.List;

import me.astropvp.GenBucketPlugin;
import me.astropvp.struct.GenBucketTask;
import me.astropvp.utils.KrizzUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.scheduler.BukkitRunnable;

public class GenTaskCommand implements CommandExecutor {
    private GenBucketPlugin plugin;

    public GenTaskCommand(GenBucketPlugin plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player)sender;
        List<GenBucketTask> tasks = GenBucketTask.getTasks(player);
        if (tasks.isEmpty()) {
            KrizzUtil.sendMessage((LivingEntity)player, (String)"&c&l(!)&c You don't have any gen buckets placed.");
            return true;
        } else {
            this.openGenTaskInventory(player, tasks);
            return true;
        }
    }

    public void openGenTaskInventory(Player player) {
        List<GenBucketTask> tasks = GenBucketTask.getTasks(player);
        this.openGenTaskInventory(player, tasks);
    }

    public void openGenTaskInventory(final Player player, List<GenBucketTask> tasks) {
        Inventory inventory = Bukkit.createInventory((InventoryHolder)null, KrizzUtil.roundInventorySize(tasks.size()), ChatColor.GREEN + "Your GenBuckets:");
        tasks.forEach((task) -> {
            inventory.addItem(task.getItem());
        });
        player.openInventory(inventory);
        (new BukkitRunnable() {
            public void run() {
                if (GenTaskCommand.this.hasInventoryOpen(player)) {
                    Inventory updatedInventory = GenTaskCommand.this.getGenTaskInventory(player);
                    if (updatedInventory.getContents().length == 0) {
                        player.closeInventory();
                        KrizzUtil.sendMessage((LivingEntity)player, (String)"&c&l(!)&c All genbuckets have finished.");
                    } else {
                        player.getOpenInventory().getTopInventory().setContents(updatedInventory.getContents());
                        player.updateInventory();
                    }
                } else {
                    this.cancel();
                }

            }
        }).runTaskTimer(this.plugin, 20L, 40L);
    }

    public Inventory getGenTaskInventory(Player player) {
        List<GenBucketTask> tasks = GenBucketTask.getTasks(player);
        Inventory inventory = Bukkit.createInventory((InventoryHolder)null, KrizzUtil.roundInventorySize(tasks.size()), ChatColor.GREEN + "Your GenBuckets:");
        tasks.forEach((task) -> {
            inventory.addItem(task.getItem());
        });
        return inventory;
    }

    public boolean hasInventoryOpen(Player player) {
        return player.getOpenInventory().getTopInventory().getTitle().equalsIgnoreCase(ChatColor.GREEN + "Your GenBuckets:");
    }
}
