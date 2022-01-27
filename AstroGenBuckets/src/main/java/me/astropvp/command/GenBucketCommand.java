package me.astropvp.command;

import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import com.google.common.collect.Lists;
import java.util.List;

import me.astropvp.GenBucketPlugin;
import me.astropvp.gui.GenBucketShopGui;
import me.astropvp.struct.GenBucket;
import me.astropvp.struct.GenBucketDirection;
import me.astropvp.utils.KrizzUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GenBucketCommand implements CommandExecutor {
    private static final String NBT_IS_GENBUCKET = "genbucket-is";
    private GenBucketPlugin plugin;

    public GenBucketCommand(GenBucketPlugin plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("genbuckets.admin")) {
            if (sender instanceof Player) {
                GenBucketShopGui genBucketShopGui = new GenBucketShopGui(((Player) sender).getPlayer());
                genBucketShopGui.getExpShopUI().open(((Player) sender).getPlayer());
            }

            return true;
        } else if (args.length == 0) {
            this.sendHelp(sender);
            return true;
        } else if (args[0].equalsIgnoreCase("give")) {
            if (args.length < 4) {
                KrizzUtil.sendMessage(sender, "&c/genbucket give <player> <type> <direction> <price> [amount]");
                return true;
            } else {
                Player target = Bukkit.getPlayer(args[1]);
                GenBucket bucket = this.plugin.getBucketHandler().getGenBucket(args[2]);
                GenBucketDirection direction = this.matchDirection(args[3]);
                Integer amount = 1;
                if (bucket == null) {
                    KrizzUtil.sendMessage(sender, "&c&l(!)&c Could not find that gen bucket.");
                    return true;
                } else if (direction == null) {
                    KrizzUtil.sendMessage(sender, "&c&l(!)&c Could not find that direction.");
                    return true;
                } else {
                    double price;
                    try {
                        price = Double.parseDouble(args[4]);
                    } catch (NumberFormatException var14) {
                        KrizzUtil.sendMessage(sender, "&c&l(!)&c Invalid price.");
                        return true;
                    }

                    try {
                        amount = Integer.parseInt(args[5]);
                    } catch (Exception var13) {
                    }

                    ItemStack item = bucket.getItem(direction, price);
                    item.setAmount(amount);
                    NBTWrapper nbtItem = new NBTWrapper(item);
                    nbtItem.setBoolean("genbucket-is", true);
                    target.getInventory().addItem(new ItemStack[]{nbtItem.build()});
                    KrizzUtil.sendMessage((LivingEntity)target, (String)("&a&l(!)&a You've been given x" + amount + " &f&l" + bucket.getDisplayName() + " Gen Buckets&a."));
                    return true;
                }
            }
        } else {
            if (args[0].equalsIgnoreCase("gui") && sender instanceof Player) {
                GenBucketShopGui genBucketShopGui = new GenBucketShopGui(((Player) sender).getPlayer());
                genBucketShopGui.getExpShopUI().open(((Player) sender).getPlayer());
            }

            if (args[0].equalsIgnoreCase("list")) {
                StringBuilder sb = new StringBuilder();
                this.plugin.getBucketHandler().getGenBuckets().forEach((b) -> {
                    sb.append(b.getName()).append(", ");
                });
                KrizzUtil.sendMessage(sender, "&a&l(!) Available Gen Buckets (!)");
                KrizzUtil.sendMessage(sender, "&7" + sb.substring(0, sb.length() - 2));
                return true;
            } else {
                return true;
            }
        }
    }

    public void sendHelp(CommandSender sender) {
        List<String> messages = Lists.newArrayList();
        messages.add("&a/genbucket give <player> <type> <direction> [amount]");
        messages.add("&a/genbucket list");
        messages.add("&a/genbucket gui");
        KrizzUtil.sendMessage((CommandSender)sender, (List)messages);
    }

    public GenBucketDirection matchDirection(String name) {
        try {
            return GenBucketDirection.valueOf(name.toUpperCase());
        } catch (Exception var3) {
            return null;
        }
    }
}
