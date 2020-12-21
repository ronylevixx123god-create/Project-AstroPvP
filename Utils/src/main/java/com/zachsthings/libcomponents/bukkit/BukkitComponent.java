/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.sk89q.bukkit.util.CommandsManagerRegistration
 *  com.sk89q.minecraft.util.commands.CommandException
 *  com.sk89q.minecraft.util.commands.CommandPermissionsException
 *  com.sk89q.minecraft.util.commands.CommandUsageException
 *  com.sk89q.minecraft.util.commands.CommandsManager
 *  com.sk89q.minecraft.util.commands.MissingNestedCommandException
 *  com.sk89q.minecraft.util.commands.UnhandledCommandException
 *  com.sk89q.minecraft.util.commands.WrappedCommandException
 */
package com.zachsthings.libcomponents.bukkit;

import com.sk89q.bukkit.util.CommandsManagerRegistration;
import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.minecraft.util.commands.CommandPermissionsException;
import com.sk89q.minecraft.util.commands.CommandUsageException;
import com.sk89q.minecraft.util.commands.CommandsManager;
import com.sk89q.minecraft.util.commands.MissingNestedCommandException;
import com.sk89q.minecraft.util.commands.UnhandledCommandException;
import com.sk89q.minecraft.util.commands.WrappedCommandException;
import com.zachsthings.libcomponents.AbstractComponent;
import com.zachsthings.libcomponents.bukkit.BasePlugin;
import java.util.Collections;
import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public abstract class BukkitComponent
extends AbstractComponent
implements CommandExecutor {
    private CommandsManagerRegistration commandRegistration;
    protected CommandsManager<CommandSender> commands;
    private BasePlugin plugin;

    public void setUp(BasePlugin plugin, CommandsManager<CommandSender> commands) {
        this.commands = commands;
        this.plugin = plugin;
        this.commandRegistration = new CommandsManagerRegistration((Plugin)plugin, (CommandExecutor)this, commands);
    }

    @Override
    public void disable() {
        this.unregisterCommands();
    }

    public void registerCommands(final Class<?> clazz) {
        if (this.plugin.lowPriorityCommandRegistration) {
            BasePlugin.server().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable(){

                public void run() {
                    BukkitComponent.this.commandRegistration.register(clazz);
                }
            }, 1L);
        } else {
            this.commandRegistration.register(clazz);
        }
    }

    public void unregisterCommands() {
        this.commandRegistration.unregisterCommands();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        try {
            this.commands.execute(command.getName(), args, (Object)sender, new Object[]{sender});
            return true;
        }
        catch (CommandPermissionsException e) {
            sender.sendMessage((Object)((Object)ChatColor.RED) + "You don't have permission.");
        }
        catch (MissingNestedCommandException e) {
            sender.sendMessage((Object)((Object)ChatColor.RED) + e.getUsage());
        }
        catch (CommandUsageException e) {
            sender.sendMessage((Object)((Object)ChatColor.RED) + e.getMessage());
            sender.sendMessage((Object)((Object)ChatColor.RED) + e.getUsage());
        }
        catch (WrappedCommandException e) {
            if (e.getCause() instanceof NumberFormatException) {
                sender.sendMessage((Object)((Object)ChatColor.RED) + "Number expected, string received instead.");
            } else {
                sender.sendMessage((Object)((Object)ChatColor.RED) + "An error has occurred. See console.");
                e.printStackTrace();
            }
        }
        catch (UnhandledCommandException e) {
            sender.sendMessage("Unknown command: " + command.getName() + "! This should never be happening!");
        }
        catch (CommandException e) {
            sender.sendMessage((Object)((Object)ChatColor.RED) + e.getMessage());
        }
        return false;
    }

    @Override
    public Map<String, String> getCommands() {
        if (this.commands == null) {
            return Collections.emptyMap();
        }
        return this.commands.getCommands();
    }
}

