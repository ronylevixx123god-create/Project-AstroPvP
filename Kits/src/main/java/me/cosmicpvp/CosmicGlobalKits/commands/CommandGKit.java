/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.customevents.CustomCosmicPlayerEvent
 *  com.cosmicpvp.cosmicutils.me.astropvp.cratekeys.utils.CC
 *  com.cosmicpvp.cosmicutils.me.astropvp.cratekeys.utils.JSONMessage
 *  org.apache.commons.lang.StringUtils
 *  org.bukkit.Bukkit
 *  org.bukkit.Sound
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.me.astropvp.cratekeys.inventory.Inventory
 *  org.bukkit.me.astropvp.cratekeys.inventory.ItemStack
 */
package me.cosmicpvp.CosmicGlobalKits.commands;

import com.cosmicpvp.cosmicutils.customevents.CustomCosmicPlayerEvent;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.JSONMessage;
import java.util.Arrays;
import java.util.Map;
import me.cosmicpvp.CosmicGlobalKits.CosmicGlobalKits;
import me.cosmicpvp.CosmicGlobalKits.CosmicGlobalKitsAPI;
import me.cosmicpvp.CosmicGlobalKits.libs.GlobalKits;
import me.cosmicpvp.CosmicGlobalKits.struct.GlobalKitData;
import me.cosmicpvp.CosmicGlobalKits.struct.GlobalKitUser;
import me.cosmicpvp.CosmicGlobalKits.utils.GlobalCooldowns;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CommandGKit
implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 1 && sender.isOp()) {
            String pname = args[0];
            if (pname.equalsIgnoreCase("settier")) {
                sender.sendMessage(CC.Red + "Usage: /gkit settier <player> <gkit> <level>");
                return true;
            }
            GlobalCooldowns.clearCooldowns(Bukkit.getPlayer((String)pname));
            sender.sendMessage("Done.");
            return true;
        }
        if (args.length == 4 && sender.isOp()) {
            if (args[0].equalsIgnoreCase("setlevel") || args[0].equalsIgnoreCase("settier")) {
                Player player = Bukkit.getPlayer((String)args[1]);
                if (player == null) {
                    sender.sendMessage(CC.Red + "Invalid player given!");
                    return true;
                }
                try {
                    GlobalKits kit = GlobalKits.valueOf(args[2]);
                    int val = Integer.parseInt(args[3]);
                    CosmicGlobalKitsAPI.setKitTier(player, kit, val);
                    sender.sendMessage(CC.Red + "Kit tier set to " + val + " for " + player.getName());
                    if (val != 0) {
                        player.sendMessage(CC.GreenB + "(!) " + CC.Green + "\"" + CC.GreenB + StringUtils.capitaliseAllWords((String)kit.getItemName()) + CC.Green + "\" /gkit updated to " + CC.GreenB + "Kit Tier " + JSONMessage.getNumber((int)val) + CC.Green + "!");
                        player.sendMessage(CC.Gray + "You will have an increased chance of receiving higher level enchantments!");
                        player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0f, 1.1f);
                    }
                    return true;
                }
                catch (Exception e) {
                    if (e instanceof NumberFormatException) {
                        sender.sendMessage(CC.Red + "Please enter a valid level between 0 - 3");
                        return true;
                    }
                    sender.sendMessage("Please enter a valid global kit: " + Arrays.toString(GlobalKits.values()));
                    return true;
                }
            }
        } else if (args.length == 3 && sender.isOp() && (args[0].equalsIgnoreCase("levelup") || args[0].equalsIgnoreCase("levelkit"))) {
            Player player = Bukkit.getPlayer((String)args[1]);
            if (player == null) {
                sender.sendMessage(CC.Red + "Invalid player given!");
                return true;
            }
            try {
                GlobalKits kit = GlobalKits.valueOf(args[2]);
                int kitTier = CosmicGlobalKitsAPI.getKitTier(player, kit);
                CosmicGlobalKitsAPI.setKitTier(player, kit, kitTier + 1);
                sender.sendMessage(CC.Red + "Kit tier set to " + (kitTier + 1) + " for " + player.getName());
                player.sendMessage(CC.GreenB + "(!) " + CC.Green + "\"" + CC.GreenB + StringUtils.capitaliseAllWords((String)kit.getItemName()) + CC.Green + "\" /gkit upgraded to " + CC.GreenB + "Kit Tier " + JSONMessage.getNumber((int)(kitTier + 1)) + CC.Green + "!");
                player.sendMessage(CC.Gray + "You will have an increased chance of receiving higher level enchantments!");
                player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0f, 1.1f);
                Bukkit.getPluginManager().callEvent((Event)new CustomCosmicPlayerEvent(player, "GKIT_LEVELUP", new Object[]{kit, kitTier + 1}));
                return true;
            }
            catch (Exception e) {
                sender.sendMessage("Please enter a valid global kit: " + Arrays.toString(GlobalKits.values()));
                return true;
            }
        }
        if (!(sender instanceof Player)) {
            sender.sendMessage(CC.Red + "Only players can view the gkit menu!");
            return true;
        }
        Player pl = (Player)sender;
        GlobalKitUser kitUser = CosmicGlobalKits.getPlugin().getGlobalKitUser(pl.getUniqueId());
        if (kitUser == null || kitUser.isLoadingData()) {
            pl.sendMessage(CC.RedB + "(!) " + CC.Red + "Please wait while your Global Kit me.astropvp.cratekeys.data is loaded!");
            pl.sendMessage(CC.Gray + "Allow up to 15 seconds before attempting to relog.");
            return true;
        }
        pl.openInventory(this.getKitMenu(pl));
        pl.playSound(pl.getLocation(), Sound.BAT_TAKEOFF, 1.0f, 0.65f);
        return true;
    }

    private Inventory getKitMenu(Player pl) {
        Inventory inv = Bukkit.createInventory(null, (int)27, (String)"Astro Kits");
        GlobalKitUser user = CosmicGlobalKits.getPlugin().getGlobalKitUser(pl.getUniqueId());
        Map<GlobalKits, GlobalKitData> kitData = user.getGlobalKits();
        for (GlobalKits gkit : GlobalKits.values()) {
            inv.addItem(new ItemStack[]{gkit.createMenuItem(kitData, pl, true)});
        }
        return inv;
    }
}

