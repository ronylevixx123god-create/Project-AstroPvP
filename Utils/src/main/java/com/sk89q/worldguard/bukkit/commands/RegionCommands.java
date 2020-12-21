/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.sk89q.minecraft.util.commands.Command
 *  com.sk89q.minecraft.util.commands.CommandContext
 *  com.sk89q.minecraft.util.commands.CommandException
 *  com.sk89q.minecraft.util.commands.CommandPermissionsException
 *  com.sk89q.worldedit.BlockVector
 *  com.sk89q.worldedit.Location
 *  com.sk89q.worldedit.Vector
 *  com.sk89q.worldedit.bukkit.BukkitUtil
 *  com.sk89q.worldedit.bukkit.WorldEditPlugin
 *  com.sk89q.worldedit.bukkit.selections.CuboidSelection
 *  com.sk89q.worldedit.bukkit.selections.Polygonal2DSelection
 *  com.sk89q.worldedit.bukkit.selections.Selection
 */
package com.sk89q.worldguard.bukkit.commands;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.minecraft.util.commands.CommandPermissionsException;
import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.Location;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitUtil;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.CuboidSelection;
import com.sk89q.worldedit.bukkit.selections.Polygonal2DSelection;
import com.sk89q.worldedit.bukkit.selections.Selection;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.RegionPermissionModel;
import com.sk89q.worldguard.bukkit.WorldConfiguration;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.bukkit.commands.RegionListEntry;
import com.sk89q.worldguard.bukkit.commands.RegionPrintoutBuilder;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.databases.ProtectionDatabaseException;
import com.sk89q.worldguard.protection.databases.RegionDBUtil;
import com.sk89q.worldguard.protection.databases.migrators.AbstractDatabaseMigrator;
import com.sk89q.worldguard.protection.databases.migrators.MigrationException;
import com.sk89q.worldguard.protection.databases.migrators.MigratorKey;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.InvalidFlagFormat;
import com.sk89q.worldguard.protection.flags.RegionGroup;
import com.sk89q.worldguard.protection.flags.RegionGroupFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.GlobalProtectedRegion;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedPolygonalRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class RegionCommands {
    private final WorldGuardPlugin plugin;
    private MigratorKey migrateDBRequest;
    private Date migrateDBRequestDate;

    public RegionCommands(WorldGuardPlugin plugin) {
        this.plugin = plugin;
    }

    private static RegionPermissionModel getPermissionModel(CommandSender sender) {
        return new RegionPermissionModel(WorldGuardPlugin.inst(), sender);
    }

    private static World getWorld(CommandContext args, CommandSender sender, char flag) throws CommandException {
        if (args.hasFlag(flag)) {
            return WorldGuardPlugin.inst().matchWorld(sender, args.getFlag(flag));
        }
        if (sender instanceof Player) {
            return WorldGuardPlugin.inst().checkPlayer(sender).getWorld();
        }
        throw new CommandException("Please specify the world with -" + flag + " world_name.");
    }

    private static String validateRegionId(String id, boolean allowGlobal) throws CommandException {
        if (!ProtectedRegion.isValidId(id)) {
            throw new CommandException("The region name of '" + id + "' contains characters that are not allowed.");
        }
        if (!allowGlobal && id.equalsIgnoreCase("__global__")) {
            throw new CommandException("Sorry, you can't use __global__ here.");
        }
        return id;
    }

    private static ProtectedRegion findExistingRegion(RegionManager regionManager, String id, boolean allowGlobal) throws CommandException {
        RegionCommands.validateRegionId(id, allowGlobal);
        ProtectedRegion region = regionManager.getRegionExact(id);
        if (region == null) {
            if (id.equalsIgnoreCase("__global__")) {
                region = new GlobalProtectedRegion(id);
                regionManager.addRegion(region);
                return region;
            }
            throw new CommandException("No region could be found with the name of '" + id + "'.");
        }
        return region;
    }

    private static ProtectedRegion findRegionStandingIn(RegionManager regionManager, Player player) throws CommandException {
        return RegionCommands.findRegionStandingIn(regionManager, player, false);
    }

    private static ProtectedRegion findRegionStandingIn(RegionManager regionManager, Player player, boolean allowGlobal) throws CommandException {
        ApplicableRegionSet set = regionManager.getApplicableRegions(player.getLocation());
        if (set.size() == 0) {
            if (allowGlobal) {
                ProtectedRegion global = RegionCommands.findExistingRegion(regionManager, "__global__", true);
                player.sendMessage((Object)((Object)ChatColor.GRAY) + "You're not standing in any regions. Using the global region for this world instead.");
                return global;
            }
            throw new CommandException("You're not standing in a region.Specify an ID if you want to select a specific region.");
        }
        if (set.size() > 1) {
            StringBuilder builder = new StringBuilder();
            boolean first = true;
            for (ProtectedRegion region : set) {
                if (!first) {
                    builder.append(", ");
                }
                first = false;
                builder.append(region.getId());
            }
            throw new CommandException("You're standing in several regions, and WorldGuard is not sure what you want.\nYou're in: " + builder.toString());
        }
        return set.iterator().next();
    }

    private static Selection getSelection(Player player) throws CommandException {
        WorldEditPlugin worldEdit = WorldGuardPlugin.inst().getWorldEdit();
        Selection selection = worldEdit.getSelection(player);
        if (selection == null) {
            throw new CommandException("Please select an area first. Use WorldEdit to make a selection! (wiki: http://wiki.sk89q.com/wiki/WorldEdit).");
        }
        return selection;
    }

    private static ProtectedRegion createRegionFromSelection(Player player, String id) throws CommandException {
        Selection selection = RegionCommands.getSelection(player);
        if (selection instanceof Polygonal2DSelection) {
            Polygonal2DSelection polySel = (Polygonal2DSelection)selection;
            int minY = polySel.getNativeMinimumPoint().getBlockY();
            int maxY = polySel.getNativeMaximumPoint().getBlockY();
            return new ProtectedPolygonalRegion(id, polySel.getNativePoints(), minY, maxY);
        }
        if (selection instanceof CuboidSelection) {
            BlockVector min = selection.getNativeMinimumPoint().toBlockVector();
            BlockVector max = selection.getNativeMaximumPoint().toBlockVector();
            return new ProtectedCuboidRegion(id, min, max);
        }
        throw new CommandException("Sorry, you can only use cuboids and polygons for WorldGuard regions.");
    }

    private static void commitChanges(CommandSender sender, RegionManager regionManager) throws CommandException {
        RegionCommands.commitChanges(sender, regionManager, false);
    }

    private static void reloadChanges(CommandSender sender, RegionManager regionManager) throws CommandException {
        RegionCommands.reloadChanges(sender, regionManager, false);
    }

    private static void commitChanges(CommandSender sender, RegionManager regionManager, boolean silent) throws CommandException {
        try {
            if (!silent && regionManager.getRegions().size() >= 500) {
                sender.sendMessage((Object)((Object)ChatColor.GRAY) + "Now saving region list to disk... (Taking too long? We're fixing it)");
            }
            regionManager.save();
        }
        catch (ProtectionDatabaseException e) {
            throw new CommandException("Uh oh, regions did not save: " + e.getMessage());
        }
    }

    private static void reloadChanges(CommandSender sender, RegionManager regionManager, boolean silent) throws CommandException {
        try {
            if (!silent && regionManager.getRegions().size() >= 500) {
                sender.sendMessage((Object)((Object)ChatColor.GRAY) + "Now loading region list from disk... (Taking too long? We're fixing it)");
            }
            regionManager.load();
        }
        catch (ProtectionDatabaseException e) {
            throw new CommandException("Uh oh, regions did not load: " + e.getMessage());
        }
    }

    private static void setPlayerSelection(Player player, ProtectedRegion region) throws CommandException {
        WorldEditPlugin worldEdit = WorldGuardPlugin.inst().getWorldEdit();
        World world = player.getWorld();
        if (region instanceof ProtectedCuboidRegion) {
            ProtectedCuboidRegion cuboid = (ProtectedCuboidRegion)region;
            BlockVector pt1 = cuboid.getMinimumPoint();
            BlockVector pt2 = cuboid.getMaximumPoint();
            CuboidSelection selection = new CuboidSelection(world, (Vector)pt1, (Vector)pt2);
            worldEdit.setSelection(player, (Selection)selection);
            player.sendMessage((Object)((Object)ChatColor.YELLOW) + "Region selected as a cuboid.");
        } else if (region instanceof ProtectedPolygonalRegion) {
            ProtectedPolygonalRegion poly2d = (ProtectedPolygonalRegion)region;
            Polygonal2DSelection selection = new Polygonal2DSelection(world, poly2d.getPoints(), poly2d.getMinimumPoint().getBlockY(), poly2d.getMaximumPoint().getBlockY());
            worldEdit.setSelection(player, (Selection)selection);
            player.sendMessage((Object)((Object)ChatColor.YELLOW) + "Region selected as a polygon.");
        } else {
            if (region instanceof GlobalProtectedRegion) {
                throw new CommandException("Can't select global regions! That would cover the entire world.");
            }
            throw new CommandException("Unknown region type: " + region.getClass().getCanonicalName());
        }
    }

    private static <V> void setFlag(ProtectedRegion region, Flag<V> flag, CommandSender sender, String value) throws InvalidFlagFormat {
        region.setFlag(flag, flag.parseInput(WorldGuardPlugin.inst(), sender, value));
    }

    @Command(aliases={"define", "def", "d", "create"}, usage="<id> [<owner1> [<owner2> [<owners...>]]]", desc="Defines a region", min=1)
    public void define(CommandContext args, CommandSender sender) throws CommandException {
        Player player = this.plugin.checkPlayer(sender);
        if (!RegionCommands.getPermissionModel(sender).mayDefine()) {
            throw new CommandPermissionsException();
        }
        String id = RegionCommands.validateRegionId(args.getString(0), false);
        RegionManager regionManager = this.plugin.getGlobalRegionManager().get(player.getWorld());
        if (regionManager.hasRegion(id)) {
            throw new CommandException("That region is already defined. To change the shape, use /region redefine " + id);
        }
        ProtectedRegion region = RegionCommands.createRegionFromSelection(player, id);
        if (args.argsLength() > 1) {
            region.setOwners(RegionDBUtil.parseDomainString(args.getSlice(1), 1));
        }
        regionManager.addRegion(region);
        RegionCommands.commitChanges(sender, regionManager);
        int height = region.getMaximumPoint().getBlockY() - region.getMinimumPoint().getBlockY();
        if (height <= 2) {
            sender.sendMessage((Object)((Object)ChatColor.GOLD) + "(Warning: The height of the region was " + (height + 1) + " block(s).)");
        }
        if (regionManager.getRegions().size() <= 2) {
            sender.sendMessage((Object)((Object)ChatColor.GRAY) + "(This region is NOW PROTECTED from modification from others. Don't want that? Use " + (Object)((Object)ChatColor.AQUA) + "/rg flag " + id + " passthrough allow" + (Object)((Object)ChatColor.GRAY) + ")");
        }
        sender.sendMessage((Object)((Object)ChatColor.YELLOW) + "A new region has been made named '" + id + "'.");
    }

    @Command(aliases={"redefine", "update", "move"}, usage="<id>", desc="Re-defines the shape of a region", min=1, max=1)
    public void redefine(CommandContext args, CommandSender sender) throws CommandException {
        Player player = this.plugin.checkPlayer(sender);
        World world = player.getWorld();
        String id = RegionCommands.validateRegionId(args.getString(0), false);
        RegionManager regionManager = this.plugin.getGlobalRegionManager().get(world);
        ProtectedRegion existing = RegionCommands.findExistingRegion(regionManager, id, false);
        if (!RegionCommands.getPermissionModel(sender).mayRedefine(existing)) {
            throw new CommandPermissionsException();
        }
        ProtectedRegion region = RegionCommands.createRegionFromSelection(player, id);
        region.setMembers(existing.getMembers());
        region.setOwners(existing.getOwners());
        region.setFlags(existing.getFlags());
        region.setPriority(existing.getPriority());
        try {
            region.setParent(existing.getParent());
        }
        catch (ProtectedRegion.CircularInheritanceException circularInheritanceException) {
            // empty catch block
        }
        regionManager.addRegion(region);
        RegionCommands.commitChanges(sender, regionManager);
        int height = region.getMaximumPoint().getBlockY() - region.getMinimumPoint().getBlockY();
        if (height <= 2) {
            sender.sendMessage((Object)((Object)ChatColor.GOLD) + "(Warning: The height of the region was " + (height + 1) + " block(s).)");
        }
        sender.sendMessage((Object)((Object)ChatColor.YELLOW) + "Region '" + id + "' updated with new area.");
    }

    @Command(aliases={"claim"}, usage="<id> [<owner1> [<owner2> [<owners...>]]]", desc="Claim a region", min=1)
    public void claim(CommandContext args, CommandSender sender) throws CommandException {
        int maxRegionCount;
        Player player = this.plugin.checkPlayer(sender);
        LocalPlayer localPlayer = this.plugin.wrapPlayer(player);
        RegionPermissionModel permModel = RegionCommands.getPermissionModel(sender);
        if (!permModel.mayClaim()) {
            throw new CommandPermissionsException();
        }
        String id = RegionCommands.validateRegionId(args.getString(0), false);
        RegionManager mgr = this.plugin.getGlobalRegionManager().get(player.getWorld());
        if (mgr.hasRegion(id)) {
            throw new CommandException("That region already exists. Please choose a different name.");
        }
        ProtectedRegion region = RegionCommands.createRegionFromSelection(player, id);
        if (args.argsLength() > 1) {
            region.setOwners(RegionDBUtil.parseDomainString(args.getSlice(1), 1));
        }
        WorldConfiguration wcfg = this.plugin.getGlobalStateManager().get(player.getWorld());
        if (!permModel.mayClaimRegionsUnbounded() && (maxRegionCount = wcfg.getMaxRegionCount(player)) >= 0 && mgr.getRegionCountOfPlayer(localPlayer) >= maxRegionCount) {
            throw new CommandException("You own too many regions, delete one first to claim a new one.");
        }
        ProtectedRegion existing = mgr.getRegionExact(id);
        if (existing != null && !existing.getOwners().contains(localPlayer)) {
            throw new CommandException("This region already exists and you don't own it.");
        }
        ApplicableRegionSet regions = mgr.getApplicableRegions(region);
        if (regions.size() > 0) {
            if (!regions.isOwnerOfAll(localPlayer)) {
                throw new CommandException("This region overlaps with someone else's region.");
            }
        } else if (wcfg.claimOnlyInsideExistingRegions) {
            throw new CommandException("You may only claim regions inside existing regions that you or your group own.");
        }
        if (!permModel.mayClaimRegionsUnbounded() && region.volume() > wcfg.maxClaimVolume) {
            player.sendMessage((Object)((Object)ChatColor.RED) + "This region is too large to claim.");
            player.sendMessage((Object)((Object)ChatColor.RED) + "Max. volume: " + wcfg.maxClaimVolume + ", your volume: " + region.volume());
            return;
        }
        region.getOwners().addPlayer(player.getName());
        mgr.addRegion(region);
        RegionCommands.commitChanges(sender, mgr);
        sender.sendMessage((Object)((Object)ChatColor.YELLOW) + "Region '" + id + "' updated with new area.");
    }

    @Command(aliases={"select", "sel", "s"}, usage="[id]", desc="Load a region as a WorldEdit selection", min=0, max=1)
    public void select(CommandContext args, CommandSender sender) throws CommandException {
        Player player = this.plugin.checkPlayer(sender);
        World world = player.getWorld();
        RegionManager regionManager = this.plugin.getGlobalRegionManager().get(world);
        ProtectedRegion existing = args.argsLength() == 0 ? RegionCommands.findRegionStandingIn(regionManager, player) : RegionCommands.findExistingRegion(regionManager, args.getString(0), false);
        if (!RegionCommands.getPermissionModel(sender).maySelect(existing)) {
            throw new CommandPermissionsException();
        }
        RegionCommands.setPlayerSelection(player, existing);
    }

    @Command(aliases={"info", "i"}, usage="[id]", flags="sw:", desc="Get information about a region", min=0, max=1)
    public void info(CommandContext args, CommandSender sender) throws CommandException {
        ProtectedRegion existing;
        World world = RegionCommands.getWorld(args, sender, 'w');
        RegionPermissionModel permModel = RegionCommands.getPermissionModel(sender);
        RegionManager regionManager = this.plugin.getGlobalRegionManager().get(world);
        if (args.argsLength() == 0) {
            if (!(sender instanceof Player)) {
                throw new CommandException("Please specify the region with /region info -w world_name region_name.");
            }
            existing = RegionCommands.findRegionStandingIn(regionManager, (Player)sender, true);
        } else {
            existing = RegionCommands.findExistingRegion(regionManager, args.getString(0), true);
        }
        if (!permModel.mayLookup(existing)) {
            throw new CommandPermissionsException();
        }
        RegionPrintoutBuilder printout = new RegionPrintoutBuilder(existing);
        printout.appendRegionInfo();
        printout.send(sender);
        if (args.hasFlag('s')) {
            if (!permModel.maySelect(existing)) {
                throw new CommandPermissionsException();
            }
            RegionCommands.setPlayerSelection(this.plugin.checkPlayer(sender), existing);
        }
    }

    @Command(aliases={"list"}, usage="[page]", desc="Get a list of regions", flags="p:w:", max=1)
    public void list(CommandContext args, CommandSender sender) throws CommandException {
        World world = RegionCommands.getWorld(args, sender, 'w');
        int page = args.getInteger(0, 1) - 1;
        if (page < 0) {
            page = 0;
        }
        String ownedBy = args.hasFlag('p') ? args.getFlag('p') : null;
        if (!RegionCommands.getPermissionModel(sender).mayList(ownedBy)) {
            ownedBy = sender.getName();
            if (!RegionCommands.getPermissionModel(sender).mayList(ownedBy)) {
                throw new CommandPermissionsException();
            }
        }
        RegionManager mgr = this.plugin.getGlobalRegionManager().get(world);
        Map<String, ProtectedRegion> regions = mgr.getRegions();
        ArrayList<RegionListEntry> entries = new ArrayList<RegionListEntry>();
        int index = 0;
        for (String id : regions.keySet()) {
            RegionListEntry entry = new RegionListEntry(id, index++);
            if (ownedBy != null) {
                entry.isOwner = regions.get(id).isOwner(ownedBy);
                entry.isMember = regions.get(id).isMember(ownedBy);
                if (!entry.isOwner && !entry.isMember) continue;
            }
            entries.add(entry);
        }
        Collections.sort(entries);
        int totalSize = entries.size();
        int pageSize = 10;
        int pages = (int)Math.ceil((float)totalSize / 10.0f);
        sender.sendMessage((Object)((Object)ChatColor.RED) + (ownedBy == null ? "Regions (page " : "Regions for " + ownedBy + " (page ") + (page + 1) + " of " + pages + "):");
        if (page < pages) {
            for (int i = page * 10; i < page * 10 + 10 && i < totalSize; ++i) {
                sender.sendMessage(ChatColor.YELLOW.toString() + entries.get(i));
            }
        }
    }

    @Command(aliases={"flag", "f"}, usage="<id> <flag> [-w world] [-g group] [value]", flags="g:w:", desc="Set flags", min=2)
    public void flag(CommandContext args, CommandSender sender) throws CommandException {
        RegionGroupFlag groupFlag;
        RegionManager regionManager;
        ProtectedRegion existing;
        World world = RegionCommands.getWorld(args, sender, 'w');
        String flagName = args.getString(1);
        String value = args.argsLength() >= 3 ? args.getJoinedStrings(2) : null;
        RegionGroup groupValue = null;
        RegionPermissionModel permModel = RegionCommands.getPermissionModel(sender);
        if (!permModel.maySetFlag(existing = RegionCommands.findExistingRegion(regionManager = this.plugin.getGlobalRegionManager().get(world), args.getString(0), true))) {
            throw new CommandPermissionsException();
        }
        Flag<?> foundFlag = DefaultFlag.fuzzyMatchFlag(flagName);
        if (foundFlag == null) {
            StringBuilder list = new StringBuilder();
            for (Flag<?> flag : DefaultFlag.getFlags()) {
                if (!permModel.maySetFlag(existing, flag)) continue;
                if (list.length() > 0) {
                    list.append(", ");
                }
                list.append(flag.getName());
            }
            sender.sendMessage((Object)((Object)ChatColor.RED) + "Unknown flag specified: " + flagName);
            sender.sendMessage((Object)((Object)ChatColor.RED) + "Available flags: " + list);
            return;
        }
        if (!permModel.maySetFlag(existing, foundFlag)) {
            throw new CommandPermissionsException();
        }
        if (args.hasFlag('g')) {
            String group = args.getFlag('g');
            RegionGroupFlag groupFlag2 = foundFlag.getRegionGroupFlag();
            if (groupFlag2 == null) {
                throw new CommandException("Region flag '" + foundFlag.getName() + "' does not have a group flag!");
            }
            try {
                groupValue = (RegionGroup)((Object)groupFlag2.parseInput(this.plugin, sender, group));
            }
            catch (InvalidFlagFormat e) {
                throw new CommandException(e.getMessage());
            }
        }
        if (value != null) {
            try {
                RegionCommands.setFlag(existing, foundFlag, sender, value);
            }
            catch (InvalidFlagFormat e) {
                throw new CommandException(e.getMessage());
            }
            sender.sendMessage((Object)((Object)ChatColor.YELLOW) + "Region flag " + foundFlag.getName() + " set on '" + existing.getId() + "' to '" + value + "'.");
        } else if (!args.hasFlag('g')) {
            existing.setFlag(foundFlag, null);
            groupFlag = foundFlag.getRegionGroupFlag();
            if (groupFlag != null) {
                existing.setFlag(groupFlag, null);
            }
            sender.sendMessage((Object)((Object)ChatColor.YELLOW) + "Region flag " + foundFlag.getName() + " removed from '" + existing.getId() + "'. (Any -g(roups) were also removed.)");
        }
        if (groupValue != null) {
            groupFlag = foundFlag.getRegionGroupFlag();
            if (groupValue == groupFlag.getDefault()) {
                existing.setFlag(groupFlag, null);
                sender.sendMessage((Object)((Object)ChatColor.YELLOW) + "Region group flag for '" + foundFlag.getName() + "' reset to default.");
            } else {
                existing.setFlag(groupFlag, groupValue);
                sender.sendMessage((Object)((Object)ChatColor.YELLOW) + "Region group flag for '" + foundFlag.getName() + "' set.");
            }
        }
        RegionCommands.commitChanges(sender, regionManager);
        RegionPrintoutBuilder printout = new RegionPrintoutBuilder(existing);
        printout.append((Object)ChatColor.GRAY);
        printout.append("(Current flags: ");
        printout.appendFlagsList(false);
        printout.append(")");
        printout.send(sender);
    }

    @Command(aliases={"setpriority", "priority", "pri"}, usage="<id> <priority>", flags="w:", desc="Set the priority of a region", min=2, max=2)
    public void setPriority(CommandContext args, CommandSender sender) throws CommandException {
        World world = RegionCommands.getWorld(args, sender, 'w');
        int priority = args.getInteger(1);
        RegionManager regionManager = this.plugin.getGlobalRegionManager().get(world);
        ProtectedRegion existing = RegionCommands.findExistingRegion(regionManager, args.getString(0), false);
        if (!RegionCommands.getPermissionModel(sender).maySetPriority(existing)) {
            throw new CommandPermissionsException();
        }
        existing.setPriority(priority);
        RegionCommands.commitChanges(sender, regionManager);
        sender.sendMessage((Object)((Object)ChatColor.YELLOW) + "Priority of '" + existing.getId() + "' set to " + priority + " (higher numbers override).");
    }

    @Command(aliases={"setparent", "parent", "par"}, usage="<id> [parent-id]", flags="w:", desc="Set the parent of a region", min=1, max=2)
    public void setParent(CommandContext args, CommandSender sender) throws CommandException {
        World world = RegionCommands.getWorld(args, sender, 'w');
        RegionManager regionManager = this.plugin.getGlobalRegionManager().get(world);
        ProtectedRegion child = RegionCommands.findExistingRegion(regionManager, args.getString(0), false);
        ProtectedRegion parent = args.argsLength() == 2 ? RegionCommands.findExistingRegion(regionManager, args.getString(1), false) : null;
        if (!RegionCommands.getPermissionModel(sender).maySetParent(child, parent)) {
            throw new CommandPermissionsException();
        }
        try {
            child.setParent(parent);
        }
        catch (ProtectedRegion.CircularInheritanceException e) {
            RegionPrintoutBuilder printout = new RegionPrintoutBuilder(parent);
            printout.append((Object)ChatColor.RED);
            printout.append("Uh oh! Setting '" + parent.getId() + "' to be the parent of '" + child.getId() + "' would cause circular inheritance.\n");
            printout.append((Object)ChatColor.GRAY);
            printout.append("(Current inheritance on '" + parent.getId() + "':\n");
            printout.appendParentTree(true);
            printout.append((Object)ChatColor.GRAY);
            printout.append(")");
            printout.send(sender);
            return;
        }
        RegionCommands.commitChanges(sender, regionManager);
        RegionPrintoutBuilder printout = new RegionPrintoutBuilder(child);
        printout.append((Object)ChatColor.YELLOW);
        printout.append("Inheritance set for region '" + child.getId() + "'.\n");
        if (parent != null) {
            printout.append((Object)ChatColor.GRAY);
            printout.append("(Current inheritance:\n");
            printout.appendParentTree(true);
            printout.append((Object)ChatColor.GRAY);
            printout.append(")");
        }
        printout.send(sender);
    }

    @Command(aliases={"remove", "delete", "del", "rem"}, usage="<id>", flags="w:", desc="Remove a region", min=1, max=1)
    public void remove(CommandContext args, CommandSender sender) throws CommandException {
        World world = RegionCommands.getWorld(args, sender, 'w');
        RegionManager regionManager = this.plugin.getGlobalRegionManager().get(world);
        ProtectedRegion existing = RegionCommands.findExistingRegion(regionManager, args.getString(0), true);
        if (!RegionCommands.getPermissionModel(sender).mayDelete(existing)) {
            throw new CommandPermissionsException();
        }
        regionManager.removeRegion(existing.getId());
        RegionCommands.commitChanges(sender, regionManager);
        sender.sendMessage((Object)((Object)ChatColor.YELLOW) + "Region '" + existing.getId() + "' removed.");
    }

    @Command(aliases={"load", "reload"}, usage="[world]", desc="Reload regions from file", flags="w:")
    public void load(CommandContext args, CommandSender sender) throws CommandException {
        World world = null;
        try {
            world = RegionCommands.getWorld(args, sender, 'w');
        }
        catch (CommandException commandException) {
            // empty catch block
        }
        if (!RegionCommands.getPermissionModel(sender).mayForceLoadRegions()) {
            throw new CommandPermissionsException();
        }
        if (world != null) {
            RegionManager regionManager = this.plugin.getGlobalRegionManager().get(world);
            if (regionManager == null) {
                throw new CommandException("No region manager exists for world '" + world.getName() + "'.");
            }
            RegionCommands.reloadChanges(sender, regionManager);
        } else {
            sender.sendMessage((Object)((Object)ChatColor.YELLOW) + "Loading all region databases... This might take a bit.");
            for (World w : this.plugin.getServer().getWorlds()) {
                RegionManager regionManager = this.plugin.getGlobalRegionManager().get(w);
                if (regionManager == null) continue;
                RegionCommands.reloadChanges(sender, regionManager, true);
            }
        }
        sender.sendMessage((Object)((Object)ChatColor.YELLOW) + "Region databases loaded.");
    }

    @Command(aliases={"save", "write"}, usage="[world]", desc="Re-save regions to file", flags="w:")
    public void save(CommandContext args, CommandSender sender) throws CommandException {
        World world = null;
        try {
            world = RegionCommands.getWorld(args, sender, 'w');
        }
        catch (CommandException commandException) {
            // empty catch block
        }
        if (!RegionCommands.getPermissionModel(sender).mayForceSaveRegions()) {
            throw new CommandPermissionsException();
        }
        if (world != null) {
            RegionManager regionManager = this.plugin.getGlobalRegionManager().get(world);
            if (regionManager == null) {
                throw new CommandException("No region manager exists for world '" + world.getName() + "'.");
            }
            RegionCommands.commitChanges(sender, regionManager);
        } else {
            sender.sendMessage((Object)((Object)ChatColor.YELLOW) + "Saving all region databases... This might take a bit.");
            for (World w : this.plugin.getServer().getWorlds()) {
                RegionManager regionManager = this.plugin.getGlobalRegionManager().get(w);
                if (regionManager == null) continue;
                RegionCommands.commitChanges(sender, regionManager, true);
            }
        }
        sender.sendMessage((Object)((Object)ChatColor.YELLOW) + "Region databases saved.");
    }

    @Command(aliases={"migratedb"}, usage="<from> <to>", desc="Migrate from one Protection Database to another.", min=2, max=2)
    public void migrateDB(CommandContext args, CommandSender sender) throws CommandException {
        MigratorKey key;
        String to;
        if (!RegionCommands.getPermissionModel(sender).mayMigrateRegionStore()) {
            throw new CommandPermissionsException();
        }
        String from = args.getString(0).toLowerCase().trim();
        if (from.equals(to = args.getString(1).toLowerCase().trim())) {
            throw new CommandException("Will not migrate with common source and target.");
        }
        Map<MigratorKey, Class<? extends AbstractDatabaseMigrator>> migrators = AbstractDatabaseMigrator.getMigrators();
        if (!migrators.containsKey(key = new MigratorKey(from, to))) {
            throw new CommandException("No migrator found for that combination and direction.");
        }
        long lastRequest = 10000000L;
        if (this.migrateDBRequestDate != null) {
            lastRequest = new Date().getTime() - this.migrateDBRequestDate.getTime();
        }
        if (this.migrateDBRequest == null || lastRequest > 60000L) {
            this.migrateDBRequest = key;
            this.migrateDBRequestDate = new Date();
            throw new CommandException("This command is potentially dangerous.\nPlease ensure you have made a backup of your data, and then re-enter the command exactly to procede.");
        }
        Class<? extends AbstractDatabaseMigrator> cls = migrators.get(key);
        try {
            AbstractDatabaseMigrator migrator = cls.getConstructor(WorldGuardPlugin.class).newInstance(this.plugin);
            migrator.migrate();
        }
        catch (IllegalArgumentException migrator) {
        }
        catch (SecurityException migrator) {
        }
        catch (InstantiationException migrator) {
        }
        catch (IllegalAccessException migrator) {
        }
        catch (InvocationTargetException migrator) {
        }
        catch (NoSuchMethodException migrator) {
        }
        catch (MigrationException e) {
            throw new CommandException("Error migrating database: " + e.getMessage());
        }
        sender.sendMessage((Object)((Object)ChatColor.YELLOW) + "Regions have been migrated successfully.\nIf you wish to use the destination format as your new backend, please update your config and reload WorldGuard.");
    }

    @Command(aliases={"teleport", "tp"}, usage="<id>", flags="s", desc="Teleports you to the location associated with the region.", min=1, max=1)
    public void teleport(CommandContext args, CommandSender sender) throws CommandException {
        Location teleportLocation;
        Player player = this.plugin.checkPlayer(sender);
        RegionManager regionManager = this.plugin.getGlobalRegionManager().get(player.getWorld());
        ProtectedRegion existing = RegionCommands.findExistingRegion(regionManager, args.getString(0), false);
        if (!RegionCommands.getPermissionModel(sender).mayTeleportTo(existing)) {
            throw new CommandPermissionsException();
        }
        if (args.hasFlag('s')) {
            teleportLocation = (Location)existing.getFlag(DefaultFlag.SPAWN_LOC);
            if (teleportLocation == null) {
                throw new CommandException("The region has no spawn point associated.");
            }
        } else {
            teleportLocation = (Location)existing.getFlag(DefaultFlag.TELE_LOC);
            if (teleportLocation == null) {
                throw new CommandException("The region has no teleport point associated.");
            }
        }
        player.teleport(BukkitUtil.toLocation((Location)teleportLocation));
        sender.sendMessage("Teleported you to the region '" + existing.getId() + "'.");
    }
}

