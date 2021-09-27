// 
// Decompiled by Procyon v0.5.36
// 

package net.astropvp.commands;

import com.cosmicpvp.cosmicutils.utils.CC;
import net.astropvp.items.SandBotItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import net.astropvp.tasks.BlockSpawnTask;
import org.bukkit.Bukkit;
import net.astropvp.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import net.astropvp.SandBots;
import org.bukkit.command.CommandExecutor;

public class CommandSandbot implements CommandExecutor
{
    private String sandbot;
    private SandBots plugin;
    
    public CommandSandbot(final SandBots plugin) {
        this.sandbot = "sandbot";
        this.plugin = plugin;
        plugin.getCommand(this.sandbot).setExecutor((CommandExecutor)this);
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (cmd.getName().equalsIgnoreCase(this.sandbot)) {
            if (args.length < 1) {
                sender.sendMessage(CC.translate("&8&l[&2&l!&8&l] &7Usage: &5/sandbot give &d<player> <amount>"));
                return true;
            }
            if (args[0].equalsIgnoreCase("reload")) {
                if (sender.hasPermission("sandbot.reload")) {
                    this.plugin.reloadConfig();
                    Utils.saveDataOfSandbots(this.plugin.getManager());
                    Utils.setupConfigFiles(this.plugin.getManager());
                    SandBots.sandBots.clear();
                    SandBots.botGuis.clear();
                    Utils.loadDataOfSandbots(this.plugin.getManager());
                    if (SandBots.spawnSandBlocks != null) {
                        SandBots.spawnSandBlocks.cancel();
                    }
                    SandBots.spawnSandBlocks = Bukkit.getScheduler().runTaskTimerAsynchronously((Plugin)this.plugin, () -> Bukkit.getScheduler().runTask((Plugin)this.plugin, (Runnable)new BlockSpawnTask(this.plugin)), 0L, 5L);
                    sender.sendMessage(CC.translate("&8&l[&5&l!&8&l] &7Plugin config and data reloaded successfully!"));
                    return true;
                }
                sender.sendMessage(CC.translate("&8&l[&c&l!&8&l] &7Insufficient permission!"));
            }
            if (args[0].equalsIgnoreCase("give")) {
                if (sender.hasPermission("sandbot.give")) {
                    if (args.length < 2) {
                        sender.sendMessage(CC.translate("&8&l[&2&l!&8&l] &7Usage: &5/sandbot give &d<player> <amount>"));
                        return true;
                    }
                    final Player p = Bukkit.getPlayer(args[1]);
                    if (p == null) {
                        sender.sendMessage(CC.translate("&8&l[&c&l!&8&l] &7Cant find player."));
                        return true;
                    }
                    if (args.length < 3) {
                        sender.sendMessage(CC.translate("&8&l[&2&l!&8&l] &7Usage: &5/sandbot give &d<player> <amount>"));
                        return true;
                    }
                    try {
                        final Integer howmany = Integer.parseInt(args[2]);
                            final ItemStack bot = SandBotItem.get();
                            bot.setAmount((int)howmany);
                            p.getInventory().addItem(bot);
                            p.sendMessage(CC.translate("&8&l[&2&l!&8&l] &7You successfully gave &5%player% &ex%amount% &7sandbot item.").replace("%player%", args[1]).replace("%amount%", howmany.toString()));

                    }
                    catch (NumberFormatException e) {
                        sender.sendMessage(CC.translate("&8&l[&c&l!&8&l] &7Please use numerical values."));
                    }
                }
                else {
                    sender.sendMessage(CC.translate("&8&l[&c&l!&8&l] &7Insufficient permission!"));
                }
            }
            else {
                    sender.sendMessage(CC.translate("&8&l[&c&l!&8&l] &7Insufficient permission!"));
            }
        }
        return true;
    }
}
