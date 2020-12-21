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
package com.sk89q.commandbook.locations;

import com.sk89q.commandbook.CommandBook;
import com.sk89q.commandbook.commands.PaginatedResult;
import com.sk89q.commandbook.locations.LocationsComponent;
import com.sk89q.commandbook.locations.NamedLocation;
import com.sk89q.commandbook.util.LocationUtil;
import com.sk89q.commandbook.util.PlayerUtil;
import com.sk89q.commandbook.util.TeleportPlayerIterator;
import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.minecraft.util.commands.CommandPermissions;
import com.sk89q.minecraft.util.commands.NestedCommand;
import com.zachsthings.libcomponents.ComponentInformation;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@ComponentInformation(friendlyName="Homes", desc="Home management system")
public class HomesComponent
extends LocationsComponent {
    public HomesComponent() {
        super("Home");
    }

    @Override
    public void enable() {
        super.enable();
        this.registerCommands(Commands.class);
    }

    @Override
    public PaginatedResult<NamedLocation> getListResult() {
        final String defaultWorld = CommandBook.server().getWorlds().get(0).getName();
        return new PaginatedResult<NamedLocation>("Owner - World  - Location"){

            @Override
            public String format(NamedLocation entry) {
                return entry.getCreatorName() + " - " + (entry.getWorldName() == null ? defaultWorld : entry.getWorldName()) + " - " + entry.getLocation().getBlockX() + "," + entry.getLocation().getBlockY() + "," + entry.getLocation().getBlockZ();
            }
        };
    }

    public class ManagementCommands {
        @Command(aliases={"del", "delete", "remove", "rem"}, usage="[name] [world]", desc="Remove a home", min=0, max=2)
        @CommandPermissions(value={"commandbook.home.remove"})
        public void removeCmd(CommandContext args, CommandSender sender) throws CommandException {
            String name = sender.getName();
            World world = args.argsLength() == 2 ? LocationUtil.matchWorld(sender, args.getString(1)) : PlayerUtil.checkPlayer(sender).getWorld();
            if (args.argsLength() > 0) {
                name = args.getString(0);
            }
            HomesComponent.this.remove(name, world, sender);
        }

        @Command(aliases={"list", "show"}, usage="[-w world] [page]", desc="List homes", flags="w:", min=0, max=1)
        @CommandPermissions(value={"commandbook.home.list"})
        public void listCmd(CommandContext args, CommandSender sender) throws CommandException {
            HomesComponent.this.list(args, sender);
        }
    }

    public class Commands {
        @Command(aliases={"home"}, usage="[world] [target] [owner]", desc="Teleport to a home", min=0, max=3)
        @CommandPermissions(value={"commandbook.home.teleport"})
        public void home(CommandContext args, CommandSender sender) throws CommandException {
            Iterable<Player> targets = null;
            NamedLocation home = null;
            if (args.argsLength() == 0) {
                Player player = PlayerUtil.checkPlayer(sender);
                targets = PlayerUtil.matchPlayers(player);
                home = HomesComponent.this.getManager().get(player.getWorld(), player.getName());
            } else if (args.argsLength() == 1) {
                Player player = PlayerUtil.checkPlayer(sender);
                targets = PlayerUtil.matchPlayers(player);
                home = HomesComponent.this.getManager().get(player.getWorld(), args.getString(0));
            } else if (args.argsLength() == 2) {
                targets = PlayerUtil.matchPlayers(sender, args.getString(0));
                if (HomesComponent.this.getManager().isPerWorld()) {
                    Player player = PlayerUtil.checkPlayer(sender);
                    home = HomesComponent.this.getManager().get(player.getWorld(), args.getString(1));
                } else {
                    home = HomesComponent.this.getManager().get(null, args.getString(1));
                }
                for (Player target : targets) {
                    if (target == sender) continue;
                    CommandBook.inst().checkPermission(sender, "commandbook.home.teleport.other");
                    break;
                }
            } else if (args.argsLength() == 3) {
                targets = PlayerUtil.matchPlayers(sender, args.getString(1));
                home = HomesComponent.this.getManager().get(LocationUtil.matchWorld(sender, args.getString(0)), args.getString(2));
                for (Player target : targets) {
                    if (target == sender) continue;
                    CommandBook.inst().checkPermission(sender, "commandbook.home.teleport.other");
                    break;
                }
            }
            if (home != null) {
                if (!home.getCreatorName().equals(sender.getName())) {
                    CommandBook.inst().checkPermission(sender, "commandbook.home.other");
                }
            } else {
                throw new CommandException("A home for the given player does not exist.");
            }
            Location loc = home.getLocation();
            new TeleportPlayerIterator(sender, loc).iterate(targets);
        }

        @Command(aliases={"sethome"}, usage="[owner] [location]", desc="Set a home", min=0, max=2)
        @CommandPermissions(value={"commandbook.home.set"})
        public void setHome(CommandContext args, CommandSender sender) throws CommandException {
            Location loc;
            String homeName;
            Player player = null;
            if (args.argsLength() == 0) {
                player = PlayerUtil.checkPlayer(sender);
                homeName = player.getName();
                loc = player.getLocation();
            } else if (args.argsLength() == 1) {
                homeName = args.getString(0);
                player = PlayerUtil.checkPlayer(sender);
                loc = player.getLocation();
                if (!homeName.equals(sender.getName())) {
                    CommandBook.inst().checkPermission(sender, "commandbook.home.set.other");
                }
            } else {
                homeName = args.getString(1);
                loc = LocationUtil.matchLocation(sender, args.getString(0));
                if (!homeName.equals(sender.getName())) {
                    CommandBook.inst().checkPermission(sender, "commandbook.home.set.other");
                }
            }
            HomesComponent.this.getManager().create(homeName, loc, player);
            sender.sendMessage((Object)((Object)ChatColor.YELLOW) + "Home set.");
        }

        @Command(aliases={"homes"}, desc="Home management")
        @NestedCommand(value={ManagementCommands.class})
        public void homes() throws CommandException {
        }
    }
}

