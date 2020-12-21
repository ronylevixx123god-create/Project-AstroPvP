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
package com.sk89q.commandbook;

import com.sk89q.commandbook.CommandBook;
import com.sk89q.commandbook.CommandBookUtil;
import com.sk89q.commandbook.commands.PaginatedResult;
import com.sk89q.commandbook.util.PlayerUtil;
import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.minecraft.util.commands.CommandPermissions;
import com.sk89q.worldedit.blocks.ItemType;
import com.zachsthings.libcomponents.ComponentInformation;
import com.zachsthings.libcomponents.bukkit.BukkitComponent;
import com.zachsthings.libcomponents.config.ConfigurationBase;
import com.zachsthings.libcomponents.config.Setting;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

@ComponentInformation(friendlyName="Inventory", desc="Inventory-related commands, such as /give and /clear, are handled in this component.")
public class InventoryComponent
extends BukkitComponent {
    protected LocalConfiguration config;

    @Override
    public void enable() {
        this.config = this.configure(new LocalConfiguration());
        this.registerCommands(Commands.class);
    }

    @Override
    public void reload() {
        super.reload();
        this.configure(this.config);
    }

    public void checkAllowedItem(CommandSender sender, int id, int damage) throws CommandException {
        boolean hasPermissions;
        if (Material.getMaterial(id) == null || id == 0) {
            throw new CommandException("Non-existent item specified.");
        }
        if (CommandBook.inst().hasPermission(sender, "commandbook.override.any-item")) {
            return;
        }
        boolean bl = hasPermissions = CommandBook.inst().hasPermission(sender, "commandbook.items." + id) || CommandBook.inst().hasPermission(sender, "commandbook.items." + id + "." + damage);
        if (hasPermissions) {
            return;
        }
        if (this.config.useItemPermissionsOnly) {
            throw new CommandException("That item is not allowed.");
        }
        if (this.config.allowedItems.size() > 0 && !this.config.allowedItems.contains(id)) {
            throw new CommandException("That item is not allowed.");
        }
        if (this.config.disallowedItems.contains(id)) {
            throw new CommandException("That item is disallowed.");
        }
    }

    private ItemStack matchItem(String name) throws CommandException {
        return CommandBook.inst().getCommandItem(name);
    }

    public class Commands {
        @Command(aliases={"item", "i"}, usage="[target] <item[:data][|enchantment:level]> [amount]", desc="Give an item", flags="do", min=1, max=3)
        @CommandPermissions(value={"commandbook.give"})
        public void item(CommandContext args, CommandSender sender) throws CommandException {
            ItemStack item = null;
            int amt = InventoryComponent.this.config.defaultItemStackSize;
            Iterable<Player> targets = null;
            if (args.argsLength() == 1) {
                item = InventoryComponent.this.matchItem(args.getString(0));
                targets = PlayerUtil.matchPlayers(PlayerUtil.checkPlayer(sender));
            } else if (args.argsLength() == 2) {
                item = InventoryComponent.this.matchItem(args.getString(0));
                amt = args.getInteger(1);
                targets = PlayerUtil.matchPlayers(PlayerUtil.checkPlayer(sender));
            } else if (args.argsLength() == 3) {
                item = InventoryComponent.this.matchItem(args.getString(1));
                amt = args.getInteger(2);
                targets = PlayerUtil.matchPlayers(sender, args.getString(0));
                for (Player player : targets) {
                    if (player == sender) continue;
                    CommandBook.inst().checkPermission(sender, "commandbook.give.other");
                }
            }
            if (item == null) {
                throw new CommandException("Something went wrong parsing the item info!");
            }
            CommandBookUtil.giveItem(sender, item, amt, targets, InventoryComponent.this, args.hasFlag('d'), args.hasFlag('o'));
        }

        @Command(aliases={"give"}, usage="[-d] <target> <item[:data]> [amount]", desc="Give an item", flags="do", min=2, max=3)
        @CommandPermissions(value={"commandbook.give", "commandbook.give.other"})
        public void give(CommandContext args, CommandSender sender) throws CommandException {
            ItemStack item = null;
            int amt = InventoryComponent.this.config.defaultItemStackSize;
            Iterable<Player> targets = null;
            if (args.argsLength() == 2) {
                targets = PlayerUtil.matchPlayers(sender, args.getString(0));
                item = InventoryComponent.this.matchItem(args.getString(1));
            } else if (args.argsLength() == 3) {
                targets = PlayerUtil.matchPlayers(sender, args.getString(0));
                item = InventoryComponent.this.matchItem(args.getString(1));
                amt = args.getInteger(2);
            }
            for (Player target : targets) {
                if (target != sender) {
                    CommandBook.inst().checkPermission(sender, "commandbook.give.other");
                    continue;
                }
                CommandBook.inst().checkPermission(sender, "commandbook.give");
            }
            if (item == null) {
                throw new CommandException("Something went wrong parsing the item info!");
            }
            CommandBookUtil.giveItem(sender, item, amt, targets, InventoryComponent.this, args.hasFlag('d'), args.hasFlag('o'));
        }

        @Command(aliases={"clear"}, usage="[target]", desc="Clear your inventory", flags="as", min=0, max=1)
        @CommandPermissions(value={"commandbook.clear"})
        public void clear(CommandContext args, CommandSender sender) throws CommandException {
            boolean clearAll = args.hasFlag('a');
            boolean clearSingle = args.hasFlag('s');
            boolean included = false;
            Iterable<Player> targets = args.argsLength() == 0 ? PlayerUtil.matchPlayers(PlayerUtil.checkPlayer(sender)) : PlayerUtil.matchPlayers(sender, args.getString(0));
            for (Player player : targets) {
                if (sender == player) continue;
                CommandBook.inst().checkPermission(sender, "commandbook.clear.other");
                break;
            }
            for (Player player : targets) {
                PlayerInventory inventory = player.getInventory();
                if (clearSingle) {
                    player.setItemInHand(null);
                } else {
                    int i;
                    int n = i = clearAll ? 0 : 9;
                    while (i < 36) {
                        inventory.setItem(i, null);
                        ++i;
                    }
                    if (clearAll) {
                        for (i = 36; i <= 39; ++i) {
                            inventory.setItem(i, null);
                        }
                    }
                }
                if (player.equals(sender)) {
                    if (clearAll) {
                        player.sendMessage((Object)((Object)ChatColor.YELLOW) + "Your inventory has been cleared.");
                    } else {
                        player.sendMessage((Object)((Object)ChatColor.YELLOW) + "Your inventory has been cleared. Use -a to clear ALL.");
                    }
                    included = true;
                    continue;
                }
                player.sendMessage((Object)((Object)ChatColor.YELLOW) + "Your inventory has been cleared by " + PlayerUtil.toColoredName(sender, ChatColor.YELLOW));
            }
            if (!included) {
                sender.sendMessage((Object)((Object)ChatColor.YELLOW) + "Inventories cleared.");
            }
        }

        @Command(aliases={"more"}, usage="[player]", desc="Gets more of an item", flags="aio", min=0, max=1)
        @CommandPermissions(value={"commandbook.more"})
        public void more(CommandContext args, CommandSender sender) throws CommandException {
            boolean moreAll = args.hasFlag('a');
            boolean infinite = args.hasFlag('i');
            boolean overrideStackSize = args.hasFlag('o');
            if (infinite) {
                CommandBook.inst().checkPermission(sender, "commandbook.more.infinite");
            } else if (overrideStackSize) {
                CommandBook.inst().checkPermission(sender, "commandbook.override.maxstacksize");
            }
            boolean included = false;
            Iterable<Player> targets = args.argsLength() == 0 ? PlayerUtil.matchPlayers(PlayerUtil.checkPlayer(sender)) : PlayerUtil.matchPlayers(sender, args.getString(0));
            for (Player player : targets) {
                if (player != sender) {
                    CommandBook.inst().checkPermission(sender, "commandbook.more.other");
                    continue;
                }
                CommandBook.inst().checkPermission(sender, "commandbook.more");
            }
            for (Player player : targets) {
                PlayerInventory inventory = player.getInventory();
                if (moreAll) {
                    for (int i = 0; i < 39; ++i) {
                        CommandBookUtil.expandStack(inventory.getItem(i), infinite, overrideStackSize);
                    }
                } else {
                    CommandBookUtil.expandStack(player.getItemInHand(), infinite, overrideStackSize);
                }
                if (player.equals(sender)) {
                    player.sendMessage((Object)((Object)ChatColor.YELLOW) + "Your item(s) has been expanded in stack size.");
                    included = true;
                    continue;
                }
                player.sendMessage((Object)((Object)ChatColor.YELLOW) + "Your item(s) has been expanded in stack size by " + PlayerUtil.toColoredName(sender, ChatColor.YELLOW));
            }
            if (!included) {
                sender.sendMessage((Object)((Object)ChatColor.YELLOW) + "Stack sizes increased.");
            }
        }

        @Command(aliases={"take"}, usage="<target> <item[:data]> [amount]", desc="Take an item", flags="", min=2, max=3)
        @CommandPermissions(value={"commandbook.take"})
        public void take(CommandContext args, CommandSender sender) throws CommandException {
            ItemStack item = null;
            int amt = InventoryComponent.this.config.defaultItemStackSize;
            Player target = null;
            if (args.argsLength() == 2) {
                target = PlayerUtil.matchSinglePlayer(sender, args.getString(0));
                item = InventoryComponent.this.matchItem(args.getString(1));
            } else if (args.argsLength() == 3) {
                target = PlayerUtil.matchSinglePlayer(sender, args.getString(0));
                item = InventoryComponent.this.matchItem(args.getString(1));
                amt = args.getInteger(2);
            }
            if (target != sender) {
                CommandBook.inst().checkPermission(sender, "commandbook.take.other");
            } else {
                CommandBook.inst().checkPermission(sender, "commandbook.take");
            }
            if (item == null) {
                throw new CommandException("Something went wrong parsing the item info!");
            }
            CommandBookUtil.takeItem(sender, item, amt, target);
        }

        @Command(aliases={"stack"}, usage="", desc="Stack items", max=0)
        @CommandPermissions(value={"commandbook.stack"})
        public void stack(CommandContext args, CommandSender sender) throws CommandException {
            Player player = PlayerUtil.checkPlayer(sender);
            boolean ignoreMax = CommandBook.inst().hasPermission(player, "commandbook.stack.illegitimate");
            boolean ignoreDamaged = CommandBook.inst().hasPermission(player, "commandbook.stack.damaged");
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
                        item.setAmount(64);
                        item2.setAmount(item2.getAmount() - needed);
                        continue block0;
                    }
                    items[j] = null;
                    item.setAmount(item.getAmount() + item2.getAmount());
                    needed = 64 - item.getAmount();
                    ++affected;
                }
            }
            if (affected > 0) {
                player.getInventory().setContents(items);
            }
            player.sendMessage((Object)((Object)ChatColor.YELLOW) + "Items compacted into stacks!");
        }

        @Command(aliases={"enchantments", "listenchant", "lsenchant"}, desc="List available enchantments", usage="[-p page]", flags="p:")
        public void enchantments(CommandContext args, CommandSender sender) throws CommandException {
            new PaginatedResult<Enchantment>("Name - ID - Max Level"){

                @Override
                public String format(Enchantment entry) {
                    return entry.getName() + " - " + entry.getId() + " - " + entry.getMaxLevel();
                }
            }.display(sender, Arrays.asList(Enchantment.values()), args.getFlagInteger('p', 1));
        }
    }

    private static class LocalConfiguration
    extends ConfigurationBase {
        @Setting(value="item-permissions-only")
        public boolean useItemPermissionsOnly;
        @Setting(value="allowed-items")
        public Set<Integer> allowedItems = Collections.emptySet();
        @Setting(value="disllowed-items")
        public Set<Integer> disallowedItems = Collections.emptySet();
        @Setting(value="default-item-stack-size")
        public int defaultItemStackSize = 1;

        private LocalConfiguration() {
        }
    }
}

