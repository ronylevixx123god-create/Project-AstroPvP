package me.ily2bb.slotbot.commands;

import com.cosmicpvp.cosmicutils.utils.CC;
import me.ily2bb.slotbot.CosmicSlotBotAPI;
import me.ily2bb.slotbot.SlotBotPlugin;
import me.ily2bb.slotbot.utils.Inventories;
import me.ily2bb.slotbot.utils.LootTable;
import me.ily2bb.slotbot.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CommandSlotBot implements CommandExecutor {
    public boolean onCommand(final CommandSender sender, final Command arg1, final String arg2, final String[] args) {
        if (args.length >= 1 && sender.hasPermission("slotbot.setitem")) {
            if (args.length >= 2 && args[0].contentEquals("additem")) {
                final Player player = (Player) sender;
                final ItemStack item = player.getItemInHand();
                if (item == null || item.getType() == Material.AIR) {
                    player.sendMessage(String.valueOf(CC.Red) + "Item can not be null.");
                    return false;
                }
                final List<String> list = (List<String>) SlotBotPlugin.getInstance().getConfig().getList("items");
                for (final String l : list) {
                    final ItemStack x = Utils.getItem(l);
                    if (item.equals((Object) x)) {
                        player.sendMessage(String.valueOf(CC.Red) + "That item is already in the Slot Bot.");
                        return false;
                    }
                }
                list.add(Utils.toBase64(new ItemStack[]{item}, 9));
                if ((args.length >= 3 && args[2].contentEquals("show")) || (args.length >= 4 && args[3].contentEquals("show"))) {
                    String i = list.get(list.size() - 1);
                    i = String.valueOf(i) + ":show";
                    list.set(list.size() - 1, i);
                }
                if ((args.length >= 4 && args[3].contentEquals("broadcast")) || (args.length >= 3 && args[2].contentEquals("broadcast"))) {
                    String i = list.get(list.size() - 1);
                    i = String.valueOf(i) + ":broadcast";
                    list.set(list.size() - 1, i);
                }
                String i = list.get(list.size() - 1);
                double percentage = 0;
                try {
                    percentage = Double.parseDouble(args[1]);
                } catch (NumberFormatException e) {
                    percentage = 1;
                }
                i = String.valueOf(i) + ":" + percentage;
                list.set(list.size() - 1, i);
                SlotBotPlugin.getInstance().getConfig().set("items", (Object) list);
                SlotBotPlugin.getInstance().saveConfig();
                SlotBotPlugin.getInstance().loadConfig();
                player.sendMessage(String.valueOf(CC.Green) + "Added item... " + item.getAmount() + "x " + Utils.getDisplayName(item) + ", " + CC.White + percentage + "%");
                return false;
            } else if (args[0].contentEquals("removeitem")) {
                final Player player = (Player) sender;
                final ItemStack item = player.getItemInHand();
                if (item == null || item.getType() == Material.AIR) {
                    player.sendMessage(String.valueOf(CC.Red) + "Item can not be null.");
                    return false;
                }
                final String display = Utils.getDisplayName(item);
                final ItemStack[] items = LootTable.getLootTableItems();
                final List<String> list2 = (List<String>) SlotBotPlugin.getInstance().getConfig().getList("items");
                for (int j = list2.size() - 1; j >= 0; --j) {
                    final ItemStack x2 = items[j];
                    if (x2 == null || x2.getType() == Material.AIR) {
                        list2.remove(j);
                        SlotBotPlugin.getInstance().getConfig().set("items", (Object) list2);
                        SlotBotPlugin.getInstance().saveConfig();
                        SlotBotPlugin.getInstance().loadConfig();
                        player.sendMessage(String.valueOf(CC.Green) + "Found null item... DELETING");
                    } else if (x2.equals((Object) item)) {
                        list2.remove(j);
                        SlotBotPlugin.getInstance().getConfig().set("items", (Object) list2);
                        SlotBotPlugin.getInstance().saveConfig();
                        SlotBotPlugin.getInstance().loadConfig();
                        player.sendMessage(String.valueOf(CC.Green) + "Found item... Deleting " + item.getAmount() + " " + display);
                        return false;
                    }
                }
                player.sendMessage(String.valueOf(CC.Red) + "That item was not found... Are you sure it's a reward?");
                return false;
            } else {
                if (args[0].contentEquals("clearitems")) {
                    final List<String> list3 = new ArrayList<String>();
                    SlotBotPlugin.getInstance().getConfig().set("items", (Object) list3);
                    SlotBotPlugin.getInstance().saveConfig();
                    SlotBotPlugin.getInstance().loadConfig();
                    ((Player) sender).sendMessage(String.valueOf(CC.Green) + "Cleared all slot bot rewards...");
                    return false;
                }
                if (args[0].contentEquals("enable")) {
                    SlotBotPlugin.getInstance().getConfig().set("slot-bot-enabled", (Object) true);
                    SlotBotPlugin.getInstance().saveConfig();
                    SlotBotPlugin.getInstance().loadConfig();
                    sender.sendMessage(String.valueOf(CC.Green) + "Slot bots enabled.");
                    return false;
                }
                if (args[0].contentEquals("disable")) {
                    SlotBotPlugin.getInstance().getConfig().set("slot-bot-enabled", (Object) false);
                    SlotBotPlugin.getInstance().saveConfig();
                    SlotBotPlugin.getInstance().loadConfig();
                    sender.sendMessage(String.valueOf(CC.Red) + "Slot bots disabled.");
                    return false;
                }
                if (args.length >= 3 && args[0].contentEquals("give")) {
                    final Player player = Bukkit.getPlayer(args[1]);
                    final int amount = Integer.parseInt(args[2]);
                    final ItemStack item2 = CosmicSlotBotAPI.createSlotBotItem();
                    item2.setAmount(amount);

                    if (Utils.isInventoryFull(player)) {
                        player.getWorld().dropItem(player.getLocation(), item2);
                        sender.sendMessage(String.valueOf(CC.Green) + "Dropped " + amount + " slot bot tickets at " + player.getName() + "'s location.");
                        return false;
                    }
                    player.getInventory().addItem(new ItemStack[]{item2});
                    sender.sendMessage(String.valueOf(CC.Green) + "Gave " + player.getName() + " " + amount + " slot bot tickets");
                    return false;
                }
            }
        }
        final Player player = (Player) sender;
        if (!SlotBotPlugin.getInstance().getConfig().getBoolean("slot-bot-enabled") && !player.isOp()) {
            player.sendMessage(String.valueOf(CC.Red) + "Slot bots are currently disabled.");
            return false;
        }
        player.openInventory(Inventories.createInventory(0));
        return false;
    }
}
