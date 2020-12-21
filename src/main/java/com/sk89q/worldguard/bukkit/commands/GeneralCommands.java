/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.sk89q.minecraft.util.commands.Command
 *  com.sk89q.minecraft.util.commands.CommandContext
 *  com.sk89q.minecraft.util.commands.CommandException
 *  com.sk89q.minecraft.util.commands.CommandPermissions
 *  com.sk89q.worldedit.blocks.ItemType
 */
package com.sk89q.worldguard.bukkit.commands;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.minecraft.util.commands.CommandPermissions;
import com.sk89q.worldedit.blocks.ItemType;
import com.sk89q.worldguard.bukkit.ConfigurationManager;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GeneralCommands {
    private final WorldGuardPlugin plugin;

    public GeneralCommands(WorldGuardPlugin plugin) {
        this.plugin = plugin;
    }

    @Command(aliases={"god"}, usage="[player]", desc="Enable godmode on a player", flags="s", max=1)
    public void god(CommandContext args, CommandSender sender) throws CommandException {
        ConfigurationManager config = this.plugin.getGlobalStateManager();
        Iterable<Player> targets = null;
        boolean included = false;
        if (args.argsLength() == 0) {
            targets = this.plugin.matchPlayers(this.plugin.checkPlayer(sender));
            this.plugin.checkPermission(sender, "worldguard.god");
        } else if (args.argsLength() == 1) {
            targets = this.plugin.matchPlayers(sender, args.getString(0));
            this.plugin.checkPermission(sender, "worldguard.god.other");
        }
        for (Player player : targets) {
            config.enableGodMode(player);
            player.setFireTicks(0);
            if (player.equals(sender)) {
                player.sendMessage((Object)((Object)ChatColor.YELLOW) + "God mode enabled! Use /ungod to disable.");
                included = true;
                continue;
            }
            player.sendMessage((Object)((Object)ChatColor.YELLOW) + "God enabled by " + this.plugin.toName(sender) + ".");
        }
        if (!included && args.hasFlag('s')) {
            sender.sendMessage(ChatColor.YELLOW.toString() + "Players now have god mode.");
        }
    }

    @Command(aliases={"ungod"}, usage="[player]", desc="Disable godmode on a player", flags="s", max=1)
    public void ungod(CommandContext args, CommandSender sender) throws CommandException {
        ConfigurationManager config = this.plugin.getGlobalStateManager();
        Iterable<Player> targets = null;
        boolean included = false;
        if (args.argsLength() == 0) {
            targets = this.plugin.matchPlayers(this.plugin.checkPlayer(sender));
            this.plugin.checkPermission(sender, "worldguard.god");
        } else if (args.argsLength() == 1) {
            targets = this.plugin.matchPlayers(sender, args.getString(0));
            this.plugin.checkPermission(sender, "worldguard.god.other");
        }
        for (Player player : targets) {
            config.disableGodMode(player);
            if (player.equals(sender)) {
                player.sendMessage((Object)((Object)ChatColor.YELLOW) + "God mode disabled!");
                included = true;
                continue;
            }
            player.sendMessage((Object)((Object)ChatColor.YELLOW) + "God disabled by " + this.plugin.toName(sender) + ".");
        }
        if (!included && args.hasFlag('s')) {
            sender.sendMessage(ChatColor.YELLOW.toString() + "Players no longer have god mode.");
        }
    }

    @Command(aliases={"heal"}, usage="[player]", desc="Heal a player", flags="s", max=1)
    public void heal(CommandContext args, CommandSender sender) throws CommandException {
        Iterable<Player> targets = null;
        boolean included = false;
        if (args.argsLength() == 0) {
            targets = this.plugin.matchPlayers(this.plugin.checkPlayer(sender));
            this.plugin.checkPermission(sender, "worldguard.heal");
        } else if (args.argsLength() == 1) {
            targets = this.plugin.matchPlayers(sender, args.getString(0));
            this.plugin.checkPermission(sender, "worldguard.heal.other");
        }
        for (Player player : targets) {
            player.setHealth(20.0);
            player.setFoodLevel(20);
            if (player.equals(sender)) {
                player.sendMessage((Object)((Object)ChatColor.YELLOW) + "Healed!");
                included = true;
                continue;
            }
            player.sendMessage((Object)((Object)ChatColor.YELLOW) + "Healed by " + this.plugin.toName(sender) + ".");
        }
        if (!included && args.hasFlag('s')) {
            sender.sendMessage(ChatColor.YELLOW.toString() + "Players healed.");
        }
    }

    @Command(aliases={"slay"}, usage="[player]", desc="Slay a player", flags="s", max=1)
    public void slay(CommandContext args, CommandSender sender) throws CommandException {
        Iterable<Player> targets = null;
        boolean included = false;
        if (args.argsLength() == 0) {
            targets = this.plugin.matchPlayers(this.plugin.checkPlayer(sender));
            this.plugin.checkPermission(sender, "worldguard.slay");
        } else if (args.argsLength() == 1) {
            targets = this.plugin.matchPlayers(sender, args.getString(0));
            this.plugin.checkPermission(sender, "worldguard.slay.other");
        }
        for (Player player : targets) {
            player.setHealth(0.0);
            if (player.equals(sender)) {
                player.sendMessage((Object)((Object)ChatColor.YELLOW) + "Slain!");
                included = true;
                continue;
            }
            player.sendMessage((Object)((Object)ChatColor.YELLOW) + "Slain by " + this.plugin.toName(sender) + ".");
        }
        if (!included && args.hasFlag('s')) {
            sender.sendMessage(ChatColor.YELLOW.toString() + "Players slain.");
        }
    }

    @Command(aliases={"locate"}, usage="[player]", desc="Locate a player", max=1)
    @CommandPermissions(value={"worldguard.locate"})
    public void locate(CommandContext args, CommandSender sender) throws CommandException {
        Player player = this.plugin.checkPlayer(sender);
        if (args.argsLength() == 0) {
            player.setCompassTarget(player.getWorld().getSpawnLocation());
            sender.sendMessage(ChatColor.YELLOW.toString() + "Compass reset to spawn.");
        } else {
            Player target = this.plugin.matchSinglePlayer(sender, args.getString(0));
            player.setCompassTarget(target.getLocation());
            sender.sendMessage(ChatColor.YELLOW.toString() + "Compass repointed.");
        }
    }

    @Command(aliases={"stack", ";"}, usage="", desc="Stack items", max=0)
    @CommandPermissions(value={"worldguard.stack"})
    public void stack(CommandContext args, CommandSender sender) throws CommandException {
        Player player = this.plugin.checkPlayer(sender);
        boolean ignoreMax = this.plugin.hasPermission(player, "worldguard.stack.illegitimate");
        boolean ignoreDamaged = this.plugin.hasPermission(player, "worldguard.stack.damaged");
        ItemStack[] items = player.getInventory().getContents();
        int len = items.length;
        int affected = 0;
        block0: for (int i = 0; i < len; ++i) {
            int max;
            ItemStack item = items[i];
            if (item == null || item.getAmount() <= 0 || !ignoreMax && item.getMaxStackSize() == 1) continue;
            int n = max = ignoreMax ? 64 : item.getMaxStackSize();
            if (item.getAmount() >= max) continue;
            int needed = max - item.getAmount();
            for (int j = i + 1; j < len; ++j) {
                ItemStack item2 = items[j];
                if (item2 == null || item2.getAmount() <= 0 || !ignoreMax && item.getMaxStackSize() == 1 || item2.getTypeId() != item.getTypeId() || (ItemType.usesDamageValue((int)item.getTypeId()) || !ignoreDamaged) && item.getDurability() != item2.getDurability() || (item.getItemMeta() != null || item2.getItemMeta() != null) && (item.getItemMeta() == null || !item.getItemMeta().equals(item2.getItemMeta()))) continue;
                if (item2.getAmount() > needed) {
                    item.setAmount(max);
                    item2.setAmount(item2.getAmount() - needed);
                    continue block0;
                }
                items[j] = null;
                item.setAmount(item.getAmount() + item2.getAmount());
                needed = max - item.getAmount();
                ++affected;
            }
        }
        if (affected > 0) {
            player.getInventory().setContents(items);
        }
        player.sendMessage((Object)((Object)ChatColor.YELLOW) + "Items compacted into stacks!");
    }
}

