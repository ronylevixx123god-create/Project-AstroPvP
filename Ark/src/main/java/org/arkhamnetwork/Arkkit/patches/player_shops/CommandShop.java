/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.harry5574.utils.hologram.Hologram
 *  me.gtacraft.plugins.safezone.util.SafezoneUtil
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.HumanEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches.player_shops;

import com.harry5574.utils.hologram.Hologram;
import java.util.ArrayList;
import java.util.List;
import me.gtacraft.plugins.safezone.util.SafezoneUtil;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.arkhamnetwork.Arkkit.patches.player_shops.PlayerShops;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class CommandShop
extends AbstractCommand {
    public CommandShop(String command, String usage, String description, List<String> aliases) {
        super(command, usage, description, aliases);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("shop") && sender instanceof Player) {
            Player pl = (Player)sender;
            if (args.length == 0) {
                pl.sendMessage(ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + "Player Shops Guide");
                pl.sendMessage((Object)ChatColor.GRAY + "Use " + (Object)ChatColor.YELLOW + "/shop <message> <price>" + (Object)ChatColor.GRAY + " to open a shop selling the item in your hand.");
                pl.playSound(pl.getLocation(), Sound.VILLAGER_HAGGLE, 1.0f, 1.0f);
                return true;
            }
            if (args.length >= 2) {
                int price;
                String msg = "";
                String last_arg = args[args.length - 1];
                for (String s : args) {
                    if (s.equals(last_arg)) continue;
                    msg = msg + s + " ";
                }
                if (msg.endsWith(" ")) {
                    msg = msg.substring(0, msg.length() - 1);
                }
                try {
                    price = Integer.parseInt(last_arg);
                }
                catch (NumberFormatException nfe) {
                    pl.sendMessage((Object)ChatColor.RED + "'" + last_arg + "' is not a valid price.");
                    return true;
                }
                if (msg.length() > 48) {
                    pl.sendMessage((Object)ChatColor.RED + "Your <message> is too long, please remove " + (msg.length() - 48) + " characters.");
                    return true;
                }
                ItemStack item = pl.getItemInHand();
                if (item == null || item.getType() == Material.AIR) {
                    pl.sendMessage((Object)ChatColor.RED + "Please place the item you wish to sell in your hand.");
                    return true;
                }
                if (Bukkit.getPluginManager().isPluginEnabled("Safezone")) {
                    if (!SafezoneUtil.isInSafeZone((Location)pl.getLocation())) {
                        pl.sendMessage((Object)ChatColor.RED + "You cannot setup a shop at this location.");
                        pl.sendMessage((Object)ChatColor.GRAY + "Type /spawn to teleport to a shop enabled zone.");
                        return true;
                    }
                } else if (PlayerShops.wg != null && !PlayerShops.getRegionName(pl.getLocation()).startsWith("spawn_") && !PlayerShops.getRegionName(pl.getLocation()).equals("spawn")) {
                    pl.sendMessage((Object)ChatColor.RED + "You cannot setup a shop at this location.");
                    pl.sendMessage((Object)ChatColor.GRAY + "Locate a PVP-disabled area.");
                    return true;
                }
                if (PlayerShops.isShopNear(pl.getLocation(), 4)) {
                    pl.sendMessage((Object)ChatColor.RED + "You are too close to another shop! Move away!");
                    return true;
                }
                Inventory shop_inv = PlayerShops.buildShop(pl.getName(), item, price);
                if (shop_inv == null) {
                    pl.sendMessage((Object)ChatColor.RED + "An error occured, sorry!");
                    return true;
                }
                pl.setItemInHand(null);
                pl.updateInventory();
                Hologram hg = new Hologram((Plugin)Arkkit.get(), pl.getLocation().add(0.0, 2.25, 0.0), new String[]{(Object)ChatColor.GREEN + ChatColor.stripColor((String)msg), ChatColor.YELLOW.toString() + price + " " + PlayerShops.currency_name, (Object)ChatColor.GRAY + "(Right Click)"});
                PlayerShops.player_shop_locations.put(pl, pl.getLocation());
                PlayerShops.player_shops.put(pl, shop_inv);
                PlayerShops.player_shops_inverse.put(shop_inv, pl);
                PlayerShops.player_holograms.put(pl, hg);
                pl.sendMessage(ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + "SHOP OPEN!");
                pl.sendMessage((Object)ChatColor.GRAY + "Selling " + (Object)ChatColor.GREEN + PlayerShops.getItemName(item) + (Object)ChatColor.GRAY + " for " + (Object)ChatColor.GREEN + price + " " + PlayerShops.currency_name + "!");
                pl.playSound(pl.getLocation(), Sound.VILLAGER_YES, 1.0f, 1.0f);
            } else {
                if (args.length == 1 && args[0].equalsIgnoreCase("close")) {
                    Inventory shop_inv = PlayerShops.player_shops.get((Object)pl);
                    if (shop_inv != null) {
                        for (HumanEntity he : new ArrayList(shop_inv.getViewers())) {
                            ((Player)he).closeInventory();
                            ((Player)he).sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "SHOP CLOSED!");
                        }
                    }
                    ItemStack is = PlayerShops.shop_listener.getItem(shop_inv, 0);
                    if (pl.getInventory().firstEmpty() == -1) {
                        return true;
                    }
                    PlayerShops.player_shops.remove((Object)pl);
                    PlayerShops.player_shops_inverse.remove((Object)shop_inv);
                    PlayerShops.player_shop_locations.remove((Object)pl);
                    Hologram hg = PlayerShops.player_holograms.get((Object)pl);
                    hg.destroy();
                    if (is != null) {
                        pl.getInventory().addItem(new ItemStack[]{is});
                    }
                    PlayerShops.player_holograms.remove((Object)pl);
                    pl.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "SHOP CLOSED!");
                    return true;
                }
                pl.sendMessage((Object)ChatColor.RED + "Use /shop <message> <price> to open a shop selling the item in your hand.");
            }
        }
        return true;
    }
}

