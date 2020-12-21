/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.sk89q.minecraft.util.commands.Command
 *  com.sk89q.minecraft.util.commands.CommandContext
 *  com.sk89q.minecraft.util.commands.CommandException
 *  com.sk89q.minecraft.util.commands.CommandPermissions
 *  com.sk89q.minecraft.util.commands.CommandPermissionsException
 *  com.sk89q.minecraft.util.commands.NestedCommand
 */
package com.sk89q.commandbook.locations;

import com.sk89q.commandbook.CommandBook;
import com.sk89q.commandbook.commands.PaginatedResult;
import com.sk89q.commandbook.locations.LocationsComponent;
import com.sk89q.commandbook.locations.NamedLocation;
import com.sk89q.commandbook.session.SessionComponent;
import com.sk89q.commandbook.util.LocationUtil;
import com.sk89q.commandbook.util.PlayerUtil;
import com.sk89q.commandbook.util.TeleportPlayerIterator;
import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.minecraft.util.commands.CommandPermissions;
import com.sk89q.minecraft.util.commands.CommandPermissionsException;
import com.sk89q.minecraft.util.commands.NestedCommand;
import com.zachsthings.libcomponents.ComponentInformation;
import com.zachsthings.libcomponents.InjectComponent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@ComponentInformation(friendlyName="Warps", desc="Provides warps functionality")
public class WarpsComponent
extends LocationsComponent {
    @InjectComponent
    private SessionComponent sessions;

    public WarpsComponent() {
        super("Warp");
    }

    @Override
    public void enable() {
        super.enable();
        this.registerCommands(Commands.class);
    }

    @Override
    public PaginatedResult<NamedLocation> getListResult() {
        final String defaultWorld = CommandBook.server().getWorlds().get(0).getName();
        return new PaginatedResult<NamedLocation>("Name - Owner - World  - Location"){

            @Override
            public String format(NamedLocation entry) {
                return entry.getName() + " - " + entry.getCreatorName() + " - " + (entry.getWorldName() == null ? defaultWorld : entry.getWorldName()) + " - " + entry.getLocation().getBlockX() + "," + entry.getLocation().getBlockY() + "," + entry.getLocation().getBlockZ();
            }
        };
    }

    public class ManagementCommands {
        @Command(aliases={"del", "delete", "remove", "rem"}, usage="<warpname> [world]", desc="Remove a warp", min=1, max=2)
        @CommandPermissions(value={"commandbook.warp.remove"})
        public void removeCmd(CommandContext args, CommandSender sender) throws CommandException {
            String warpName = args.getString(0);
            World world = args.argsLength() == 2 ? LocationUtil.matchWorld(sender, args.getString(1)) : PlayerUtil.checkPlayer(sender).getWorld();
            WarpsComponent.this.remove(warpName, world, sender);
        }

        @Command(aliases={"list", "show"}, usage="[ -p owner] [-w world] [page]", desc="List warps", flags="p:w:", min=0, max=1)
        @CommandPermissions(value={"commandbook.warp.list"})
        public void listCmd(CommandContext args, CommandSender sender) throws CommandException {
            WarpsComponent.this.list(args, sender);
        }
    }

    public class Commands {
        @Command(aliases={"warp"}, usage="[world] [target] <warp>", desc="Teleport to a warp", flags="s", min=1, max=3)
        public void warp(CommandContext args, CommandSender sender) throws CommandException {
            Player player;
            Iterable<Player> targets = null;
            NamedLocation warp = null;
            if (args.argsLength() == 1) {
                player = PlayerUtil.checkPlayer(sender);
                targets = PlayerUtil.matchPlayers(player);
                warp = WarpsComponent.this.getManager().get(player.getWorld(), args.getString(0));
            } else if (args.argsLength() == 2) {
                targets = PlayerUtil.matchPlayers(sender, args.getString(0));
                if (WarpsComponent.this.getManager().isPerWorld()) {
                    player = PlayerUtil.checkPlayer(sender);
                    warp = WarpsComponent.this.getManager().get(player.getWorld(), args.getString(1));
                } else {
                    warp = WarpsComponent.this.getManager().get(null, args.getString(1));
                }
            } else if (args.argsLength() == 3) {
                targets = PlayerUtil.matchPlayers(sender, args.getString(1));
                warp = WarpsComponent.this.getManager().get(LocationUtil.matchWorld(sender, args.getString(0)), args.getString(2));
            }
            for (Player target : targets) {
                if (target == sender) continue;
                CommandBook.inst().checkPermission(sender, "commandbook.warp.teleport.other");
                break;
            }
            if (warp != null) {
                try {
                    CommandBook.inst().checkPermission(sender, "commandbook.warp.teleport");
                }
                catch (CommandPermissionsException e) {
                    CommandBook.inst().checkPermission(sender, "commandbook.warp.warp." + warp.getName());
                }
            } else {
                throw new CommandException("A warp by the given name does not exist.");
            }
            Location loc = warp.getLocation();
            new TeleportPlayerIterator(sender, loc, args.hasFlag('s')).iterate(targets);
        }

        @Command(aliases={"setwarp"}, usage="<warp> [location]", desc="Set a warp", min=1, max=2)
        @CommandPermissions(value={"commandbook.warp.set"})
        public void setWarp(CommandContext args, CommandSender sender) throws CommandException {
            Location loc;
            String warpName = args.getString(0);
            Player player = null;
            if (args.argsLength() == 1) {
                player = PlayerUtil.checkPlayer(sender);
                loc = player.getLocation();
            } else {
                loc = LocationUtil.matchLocation(sender, args.getString(1));
            }
            NamedLocation existing = WarpsComponent.this.getManager().get(loc.getWorld(), warpName);
            if (existing != null) {
                if (!existing.getCreatorName().equals(sender.getName())) {
                    CommandBook.inst().checkPermission(sender, "commandbook.warp.set.override");
                }
                if (!WarpsComponent.this.sessions.getSession(sender).checkOrQueueConfirmed(args.getCommand() + " " + args.getJoinedStrings(0))) {
                    throw new CommandException("Warp already exists! Type /confirm to confirm overwriting");
                }
            }
            WarpsComponent.this.getManager().create(warpName, loc, player);
            sender.sendMessage((Object)((Object)ChatColor.YELLOW) + "Warp '" + warpName + "' created.");
        }

        @Command(aliases={"warps"}, desc="Warp management")
        @NestedCommand(value={ManagementCommands.class})
        public void warps(CommandContext args, CommandSender sender) throws CommandException {
        }
    }
}

