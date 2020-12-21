/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.sk89q.minecraft.util.commands.CommandContext
 *  com.sk89q.minecraft.util.commands.CommandException
 */
package com.sk89q.commandbook.locations;

import com.sk89q.commandbook.CommandBook;
import com.sk89q.commandbook.commands.PaginatedResult;
import com.sk89q.commandbook.locations.FlatFileLocationsManager;
import com.sk89q.commandbook.locations.NamedLocation;
import com.sk89q.commandbook.locations.RootLocationManager;
import com.sk89q.commandbook.util.LocationUtil;
import com.sk89q.commandbook.util.PlayerUtil;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;
import com.zachsthings.libcomponents.bukkit.BukkitComponent;
import com.zachsthings.libcomponents.config.ConfigurationBase;
import com.zachsthings.libcomponents.config.Setting;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.event.world.WorldUnloadEvent;

public abstract class LocationsComponent
extends BukkitComponent {
    private final String name;
    private RootLocationManager<NamedLocation> manager;

    protected LocationsComponent(String name) {
        this.name = name;
    }

    @Override
    public void enable() {
        LocalConfiguration config = this.configure(new LocalConfiguration());
        FlatFileLocationsManager.LocationsFactory warpsFactory = new FlatFileLocationsManager.LocationsFactory(CommandBook.inst().getDataFolder(), this.name + "s");
        this.manager = new RootLocationManager<NamedLocation>(warpsFactory, config.perWorld);
        CommandBook.registerEvents(new WorldListener());
    }

    public RootLocationManager<NamedLocation> getManager() {
        return this.manager;
    }

    public void remove(String name, World world, CommandSender sender) throws CommandException {
        NamedLocation loc = this.getManager().get(world, name);
        if (loc == null) {
            throw new CommandException("No " + name.toLowerCase() + " found for " + name + " in world " + world.getName());
        }
        if (!loc.getCreatorName().equals(sender.getName())) {
            CommandBook.inst().checkPermission(sender, "commandbook." + this.name.toLowerCase() + ".remove.other");
        }
        this.getManager().remove(world, name);
        sender.sendMessage((Object)((Object)ChatColor.YELLOW) + name + " for " + name + " removed.");
    }

    public void list(CommandContext args, CommandSender sender) throws CommandException {
        World world = null;
        if (this.getManager().isPerWorld() && (world = args.hasFlag('w') ? LocationUtil.matchWorld(sender, args.getFlag('w')) : PlayerUtil.checkPlayer(sender).getWorld()) == null) {
            throw new CommandException("Error finding world to use!");
        }
        this.getListResult().display(sender, this.getManager().getLocations(world), args.getInteger(0, 1));
    }

    public abstract PaginatedResult<NamedLocation> getListResult();

    private class WorldListener
    implements Listener {
        private WorldListener() {
        }

        @EventHandler
        public void loadWorld(WorldLoadEvent event) {
            LocationsComponent.this.manager.updateWorlds(event.getWorld());
        }

        @EventHandler
        public void unloadWorld(WorldUnloadEvent event) {
            LocationsComponent.this.manager.updateWorlds(event.getWorld());
        }
    }

    private static class LocalConfiguration
    extends ConfigurationBase {
        @Setting(value="per-world")
        public boolean perWorld;

        private LocalConfiguration() {
        }
    }
}

