/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.sk89q.minecraft.util.commands.Command
 *  com.sk89q.minecraft.util.commands.CommandContext
 *  com.sk89q.minecraft.util.commands.NestedCommand
 */
package com.sk89q.worldguard.bukkit.commands;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.NestedCommand;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.bukkit.commands.RegionCommands;
import com.sk89q.worldguard.bukkit.commands.RegionMemberCommands;
import com.sk89q.worldguard.bukkit.commands.WorldGuardCommands;
import org.bukkit.command.CommandSender;

public class ProtectionCommands {
    private final WorldGuardPlugin plugin;

    public ProtectionCommands(WorldGuardPlugin plugin) {
        this.plugin = plugin;
    }

    @Command(aliases={"region", "regions", "rg"}, desc="Region management commands")
    @NestedCommand(value={RegionCommands.class, RegionMemberCommands.class})
    public void region(CommandContext args, CommandSender sender) {
    }

    @Command(aliases={"worldguard", "wg"}, desc="WorldGuard commands")
    @NestedCommand(value={WorldGuardCommands.class})
    public void worldGuard(CommandContext args, CommandSender sender) {
    }
}

