/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.sk89q.minecraft.util.commands.Command
 *  com.sk89q.minecraft.util.commands.CommandContext
 *  com.sk89q.minecraft.util.commands.CommandException
 */
package com.sk89q.worldguard.bukkit.commands;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.databases.ProtectionDatabaseException;
import com.sk89q.worldguard.protection.databases.RegionDBUtil;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RegionMemberCommands {
    private final WorldGuardPlugin plugin;

    public RegionMemberCommands(WorldGuardPlugin plugin) {
        this.plugin = plugin;
    }

    @Command(aliases={"addmember", "addmember"}, usage="<id> <members...>", flags="w:", desc="Add a member to a region", min=2)
    public void addMember(CommandContext args, CommandSender sender) throws CommandException {
        World world;
        Player player = null;
        LocalPlayer localPlayer = null;
        if (sender instanceof Player) {
            player = (Player)sender;
            localPlayer = this.plugin.wrapPlayer(player);
        }
        if (args.hasFlag('w')) {
            world = this.plugin.matchWorld(sender, args.getFlag('w'));
        } else if (player != null) {
            world = player.getWorld();
        } else {
            throw new CommandException("No world specified. Use -w <worldname>.");
        }
        String id = args.getString(0);
        RegionManager mgr = this.plugin.getGlobalRegionManager().get(world);
        ProtectedRegion region = mgr.getRegion(id);
        if (region == null) {
            throw new CommandException("Could not find a region by that ID.");
        }
        id = region.getId();
        if (localPlayer != null) {
            if (region.isOwner(localPlayer)) {
                this.plugin.checkPermission(sender, "worldguard.region.addmember.own." + id.toLowerCase());
            } else if (region.isMember(localPlayer)) {
                this.plugin.checkPermission(sender, "worldguard.region.addmember.member." + id.toLowerCase());
            } else {
                this.plugin.checkPermission(sender, "worldguard.region.addmember." + id.toLowerCase());
            }
        }
        RegionDBUtil.addToDomain(region.getMembers(), args.getParsedPaddedSlice(1, 0), 0);
        sender.sendMessage((Object)((Object)ChatColor.YELLOW) + "Region '" + id + "' updated.");
        try {
            mgr.save();
        }
        catch (ProtectionDatabaseException e) {
            throw new CommandException("Failed to write regions: " + e.getMessage());
        }
    }

    @Command(aliases={"addowner", "addowner"}, usage="<id> <owners...>", flags="w:", desc="Add an owner to a region", min=2)
    public void addOwner(CommandContext args, CommandSender sender) throws CommandException {
        World world;
        Player player = null;
        LocalPlayer localPlayer = null;
        if (sender instanceof Player) {
            player = (Player)sender;
            localPlayer = this.plugin.wrapPlayer(player);
        }
        if (args.hasFlag('w')) {
            world = this.plugin.matchWorld(sender, args.getFlag('w'));
        } else if (player != null) {
            world = player.getWorld();
        } else {
            throw new CommandException("No world specified. Use -w <worldname>.");
        }
        String id = args.getString(0);
        RegionManager mgr = this.plugin.getGlobalRegionManager().get(world);
        ProtectedRegion region = mgr.getRegion(id);
        if (region == null) {
            throw new CommandException("Could not find a region by that ID.");
        }
        id = region.getId();
        Boolean flag = (Boolean)region.getFlag(DefaultFlag.BUYABLE);
        DefaultDomain owners = region.getOwners();
        if (localPlayer != null) {
            if (flag != null && flag.booleanValue() && owners != null && owners.size() == 0) {
                int maxRegionCount;
                if (!this.plugin.hasPermission(player, "worldguard.region.unlimited") && (maxRegionCount = this.plugin.getGlobalStateManager().get(world).getMaxRegionCount(player)) >= 0 && mgr.getRegionCountOfPlayer(localPlayer) >= maxRegionCount) {
                    throw new CommandException("You already own the maximum allowed amount of regions.");
                }
                this.plugin.checkPermission(sender, "worldguard.region.addowner.unclaimed." + id.toLowerCase());
            } else if (region.isOwner(localPlayer)) {
                this.plugin.checkPermission(sender, "worldguard.region.addowner.own." + id.toLowerCase());
            } else if (region.isMember(localPlayer)) {
                this.plugin.checkPermission(sender, "worldguard.region.addowner.member." + id.toLowerCase());
            } else {
                this.plugin.checkPermission(sender, "worldguard.region.addowner." + id.toLowerCase());
            }
        }
        RegionDBUtil.addToDomain(region.getOwners(), args.getParsedPaddedSlice(1, 0), 0);
        sender.sendMessage((Object)((Object)ChatColor.YELLOW) + "Region '" + id + "' updated.");
        try {
            mgr.save();
        }
        catch (ProtectionDatabaseException e) {
            throw new CommandException("Failed to write regions: " + e.getMessage());
        }
    }

    @Command(aliases={"removemember", "remmember", "removemem", "remmem"}, usage="<id> <owners...>", flags="aw:", desc="Remove an owner to a region", min=1)
    public void removeMember(CommandContext args, CommandSender sender) throws CommandException {
        World world;
        Player player = null;
        LocalPlayer localPlayer = null;
        if (sender instanceof Player) {
            player = (Player)sender;
            localPlayer = this.plugin.wrapPlayer(player);
        }
        if (args.hasFlag('w')) {
            world = this.plugin.matchWorld(sender, args.getFlag('w'));
        } else if (player != null) {
            world = player.getWorld();
        } else {
            throw new CommandException("No world specified. Use -w <worldname>.");
        }
        String id = args.getString(0);
        RegionManager mgr = this.plugin.getGlobalRegionManager().get(world);
        ProtectedRegion region = mgr.getRegion(id);
        if (region == null) {
            throw new CommandException("Could not find a region by that ID.");
        }
        id = region.getId();
        if (localPlayer != null) {
            if (region.isOwner(localPlayer)) {
                this.plugin.checkPermission(sender, "worldguard.region.removemember.own." + id.toLowerCase());
            } else if (region.isMember(localPlayer)) {
                this.plugin.checkPermission(sender, "worldguard.region.removemember.member." + id.toLowerCase());
            } else {
                this.plugin.checkPermission(sender, "worldguard.region.removemember." + id.toLowerCase());
            }
        }
        if (args.hasFlag('a')) {
            region.getMembers().removeAll();
        } else {
            if (args.argsLength() < 2) {
                throw new CommandException("List some names to remove, or use -a to remove all.");
            }
            RegionDBUtil.removeFromDomain(region.getMembers(), args.getParsedPaddedSlice(1, 0), 0);
        }
        sender.sendMessage((Object)((Object)ChatColor.YELLOW) + "Region '" + id + "' updated.");
        try {
            mgr.save();
        }
        catch (ProtectionDatabaseException e) {
            throw new CommandException("Failed to write regions: " + e.getMessage());
        }
    }

    @Command(aliases={"removeowner", "remowner"}, usage="<id> <owners...>", flags="aw:", desc="Remove an owner to a region", min=1)
    public void removeOwner(CommandContext args, CommandSender sender) throws CommandException {
        World world;
        Player player = null;
        LocalPlayer localPlayer = null;
        if (sender instanceof Player) {
            player = (Player)sender;
            localPlayer = this.plugin.wrapPlayer(player);
        }
        if (args.hasFlag('w')) {
            world = this.plugin.matchWorld(sender, args.getFlag('w'));
        } else if (player != null) {
            world = player.getWorld();
        } else {
            throw new CommandException("No world specified. Use -w <worldname>.");
        }
        String id = args.getString(0);
        RegionManager mgr = this.plugin.getGlobalRegionManager().get(world);
        ProtectedRegion region = mgr.getRegion(id);
        if (region == null) {
            throw new CommandException("Could not find a region by that ID.");
        }
        id = region.getId();
        if (localPlayer != null) {
            if (region.isOwner(localPlayer)) {
                this.plugin.checkPermission(sender, "worldguard.region.removeowner.own." + id.toLowerCase());
            } else if (region.isMember(localPlayer)) {
                this.plugin.checkPermission(sender, "worldguard.region.removeowner.member." + id.toLowerCase());
            } else {
                this.plugin.checkPermission(sender, "worldguard.region.removeowner." + id.toLowerCase());
            }
        }
        if (args.hasFlag('a')) {
            region.getOwners().removeAll();
        } else {
            if (args.argsLength() < 2) {
                throw new CommandException("List some names to remove, or use -a to remove all.");
            }
            RegionDBUtil.removeFromDomain(region.getOwners(), args.getParsedPaddedSlice(1, 0), 0);
        }
        sender.sendMessage((Object)((Object)ChatColor.YELLOW) + "Region '" + id + "' updated.");
        try {
            mgr.save();
        }
        catch (ProtectionDatabaseException e) {
            throw new CommandException("Failed to write regions: " + e.getMessage());
        }
    }
}

