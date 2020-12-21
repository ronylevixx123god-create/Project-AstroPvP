/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.sk89q.minecraft.util.commands.Command
 *  com.sk89q.minecraft.util.commands.CommandContext
 *  com.sk89q.minecraft.util.commands.CommandException
 *  com.sk89q.minecraft.util.commands.CommandPermissions
 *  com.sk89q.minecraft.util.commands.NestedCommand
 */
package com.sk89q.commandbook.commands;

import com.sk89q.commandbook.CommandBook;
import com.sk89q.commandbook.commands.PaginatedResult;
import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.minecraft.util.commands.CommandPermissions;
import com.sk89q.minecraft.util.commands.NestedCommand;
import com.zachsthings.libcomponents.AbstractComponent;
import com.zachsthings.libcomponents.ComponentInformation;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.logging.Level;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CommandBookCommands {
    @Command(aliases={"version"}, usage="", desc="CommandBook version information", min=0, max=0)
    public static void version(CommandContext args, CommandSender sender) throws CommandException {
        sender.sendMessage((Object)((Object)ChatColor.YELLOW) + "CommandBook " + CommandBook.inst().getDescription().getVersion());
        sender.sendMessage((Object)((Object)ChatColor.YELLOW) + "http://www.sk89q.com");
    }

    @Command(aliases={"reload"}, usage="", desc="Reload CommandBook's settings", min=0, max=0)
    @CommandPermissions(value={"commandbook.reload"})
    public static void reload(CommandContext args, CommandSender sender) throws CommandException {
        try {
            CommandBook.inst().getGlobalConfiguration().load();
        }
        catch (IOException e) {
            sender.sendMessage((Object)((Object)ChatColor.RED) + "Error reolading configuration: " + e.getMessage());
            sender.sendMessage((Object)((Object)ChatColor.RED) + "See console for details!");
            CommandBook.logger().log(Level.WARNING, "Error reloading configuration: " + e, e);
        }
        CommandBook.inst().loadConfiguration();
        CommandBook.inst().getComponentManager().reloadComponents();
        sender.sendMessage((Object)((Object)ChatColor.YELLOW) + "CommandBook's configuration has been reloaded.");
    }

    @Command(aliases={"save"}, usage="", desc="Save CommandBook's settings", min=0, max=0)
    @CommandPermissions(value={"commandbook.save"})
    public static void save(CommandContext args, CommandSender sender) throws CommandException {
        CommandBook.inst().getGlobalConfiguration().save();
        sender.sendMessage((Object)((Object)ChatColor.YELLOW) + "CommandBook's configuration has been reloaded.");
    }

    @Command(aliases={"help", "doc"}, usage="<component>", desc="Get documentation for a component", flags="p:", min=0, max=1)
    @CommandPermissions(value={"commandbook.component.help"})
    public static void help(CommandContext args, CommandSender sender) throws CommandException {
        if (args.argsLength() == 0) {
            new PaginatedResult<AbstractComponent>("Name - Description"){

                @Override
                public String format(AbstractComponent entry) {
                    return entry.getInformation().friendlyName() + " - " + entry.getInformation().desc();
                }
            }.display(sender, CommandBook.inst().getComponentManager().getComponents(), args.getFlagInteger('p', 1));
        } else {
            Map<String, String> commands;
            String componentName = args.getString(0).replaceAll(" ", "-").toLowerCase();
            AbstractComponent component = CommandBook.inst().getComponentManager().getComponent(componentName);
            if (component == null) {
                throw new CommandException("No such component: " + componentName);
            }
            ComponentInformation info = component.getInformation();
            sender.sendMessage((Object)((Object)ChatColor.YELLOW) + info.friendlyName() + " - " + info.desc());
            if (info.authors().length > 0 && info.authors()[0].length() > 0) {
                sender.sendMessage((Object)((Object)ChatColor.YELLOW) + "Authors: " + Arrays.toString(info.authors()).replaceAll("[(.*)]", "$1"));
            }
            if ((commands = component.getCommands()).size() > 0) {
                new PaginatedResult<Map.Entry<String, String>>("    Command - Description"){

                    @Override
                    public String format(Map.Entry<String, String> entry) {
                        return "    /" + entry.getKey() + " " + entry.getValue();
                    }
                }.display(sender, commands.entrySet(), args.getFlagInteger('p', 1));
            } else {
                sender.sendMessage((Object)((Object)ChatColor.YELLOW) + "No commands");
            }
        }
    }

    public static class CommandBookParentCommand {
        @Command(aliases={"cmdbook"}, desc="CommandBook commands", flags="d", min=1, max=3)
        @NestedCommand(value={CommandBookCommands.class})
        public static void cmdBook() {
        }
    }
}

