package me.astropvp.itemflip.commands;

import com.cosmicpvp.cosmicutils.utils.CC;
import me.astropvp.itemflip.AstroItemFlipPlugin;
import me.astropvp.itemflip.data.ItemFlipData;
import me.astropvp.itemflip.data.ItemFlipPlayerData;
import me.astropvp.itemflip.enums.Color;
import me.astropvp.itemflip.inventory.ItemFlipTradeInventory;
import me.astropvp.itemflip.struct.ItemFlipRequest;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class CommandItemFlip
        implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }

        if (!((Player) sender).getLocation().getWorld().getName().equals("world") && !((Player) sender).getLocation().getWorld().getName().equals("nebula")) {
            sender.sendMessage(CC.translate("&cYou cannot icf in this world."));
            return false;
        }
        final Player player = (Player) sender;
        if (args.length >= 1) {
            Player target = Bukkit.getPlayer(args[0]);
            if (!target.getLocation().getWorld().getName().equals("world")) {
                sender.sendMessage(CC.translate("&cYou cannot icf this person. This person is in a different world."));
                return false;
            }
            if (target == null || !target.isOnline()) {
                player.sendMessage(CC.RedB + "(!) " + CC.Red + "That player is not online!");
                return false;
            }
            if (target == player) {
                player.sendMessage(CC.RedB + "(!) " + CC.Red + "You " + CC.Underline + "cannot" + CC.Red + " /itemflip yourself!");
                return false;
            }
            ItemFlipRequest request = ItemFlipRequest.findInstance(player);
            if (request != null && request.getSender() == player) {
                player.sendMessage(CC.RedB + "(!) " + CC.Red + "You currently have a pending /itemflip!");
                return false;
            }
            if (request != null && request.getReceiver() == player) {
                if (args[0].equalsIgnoreCase(request.getSender().getName())) {
                    final Player original_sender = request.getSender();
                    original_sender.closeInventory();
                    request.remove();
                    Bukkit.getScheduler().runTaskLater((Plugin) AstroItemFlipPlugin.getInstance(), (Runnable) new BukkitRunnable() {
                        public void run() {
                            if (!original_sender.isOnline() || !player.isOnline()) {
                                return;
                            }
                            ItemFlipPlayerData data_one = new ItemFlipPlayerData(player, Color.values()[0]);
                            ItemFlipPlayerData data_two = new ItemFlipPlayerData(original_sender, Color.values()[(Color.values()).length - 1]);
                            ItemFlipData data = new ItemFlipData(data_one, data_two);
                            Inventory inventory = ItemFlipTradeInventory.getInventory(data);
                            player.openInventory(inventory);
                            original_sender.openInventory(inventory);
                            player.playSound(player.getLocation(), Sound.CHEST_OPEN, 0.75F, 2.0F);
                            original_sender.playSound(player.getLocation(), Sound.CHEST_OPEN, 0.75F, 2.0F);
                        }
                    }, 1L);
                }
                return false;
            }
            request = new ItemFlipRequest(player, target);
            request.send();
            return false;
        }
        player.sendMessage(CC.GoldB + CC.Underline + "Item Flip Commands");
        player.sendMessage(CC.Gold + "/itemflip <player>");
        player.sendMessage(CC.Gray + "Sends an item flip request to the specified <player>.");
        player.sendMessage(CC.Gray + "Item Flips are similar to Coin Flips (/cf) but instead of money,");
        player.sendMessage(CC.Gray + "you're wagering items.");
        
        return false;
    }
}
