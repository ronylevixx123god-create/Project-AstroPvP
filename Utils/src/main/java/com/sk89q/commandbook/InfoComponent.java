/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.sk89q.minecraft.util.commands.Command
 *  com.sk89q.minecraft.util.commands.CommandContext
 *  com.sk89q.minecraft.util.commands.CommandException
 *  com.sk89q.minecraft.util.commands.CommandPermissions
 */
package com.sk89q.commandbook;

import com.sk89q.commandbook.CommandBook;
import com.sk89q.commandbook.CommandBookUtil;
import com.sk89q.commandbook.commands.PaginatedResult;
import com.sk89q.commandbook.util.LocationUtil;
import com.sk89q.commandbook.util.PlayerUtil;
import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.minecraft.util.commands.CommandPermissions;
import com.zachsthings.libcomponents.ComponentInformation;
import com.zachsthings.libcomponents.bukkit.BukkitComponent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@ComponentInformation(friendlyName="Info", desc="Info contains commands that allow users to gather information about the world, without being able to make changes.")
public class InfoComponent
extends BukkitComponent {
    @Override
    public void enable() {
        this.registerCommands(Commands.class);
    }

    public class Commands {
        private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        @Command(aliases={"whereami", "getpos", "pos", "where"}, usage="[player]", desc="Show your current location", flags="", min=0, max=1)
        @CommandPermissions(value={"commandbook.whereami"})
        public void whereAmI(CommandContext args, CommandSender sender) throws CommandException {
            Player player;
            if (args.argsLength() == 0) {
                player = PlayerUtil.checkPlayer(sender);
            } else {
                player = PlayerUtil.matchSinglePlayer(sender, args.getString(0));
                if (player != sender) {
                    CommandBook.inst().checkPermission(sender, "commandbook.whereami.other");
                }
            }
            Location pos = player.getLocation();
            sender.sendMessage((Object)((Object)ChatColor.YELLOW) + "Player: " + player.getName() + (player == sender ? "(That's you!)" : ""));
            sender.sendMessage((Object)((Object)ChatColor.YELLOW) + "World: " + player.getWorld().getName());
            sender.sendMessage((Object)((Object)ChatColor.YELLOW) + String.format("Location: (%.4f, %.4f, %.4f)", pos.getX(), pos.getY(), pos.getZ()));
            sender.sendMessage((Object)((Object)ChatColor.YELLOW) + "Depth: " + (int)Math.floor(pos.getY()));
            if (CommandBook.inst().hasPermission(sender, "commandbook.whereami.compass")) {
                sender.sendMessage((Object)((Object)ChatColor.YELLOW) + String.format("Direction: %s", CommandBookUtil.getCardinalDirection(player)));
            }
        }

        @Command(aliases={"whois"}, usage="[-p page] [player]", desc="Tell information about a player", flags="op:", min=0, max=1)
        @CommandPermissions(value={"commandbook.whois"})
        public void whois(CommandContext args, CommandSender sender) throws CommandException {
            Location bedSpawn;
            OfflinePlayer offline;
            if (args.argsLength() == 0) {
                offline = PlayerUtil.checkPlayer(sender);
            } else {
                try {
                    offline = PlayerUtil.matchSinglePlayer(sender, args.getString(0));
                }
                catch (CommandException e) {
                    if (args.hasFlag('o')) {
                        offline = CommandBook.server().getOfflinePlayer(args.getString(0));
                    }
                    throw e;
                }
                if (offline != sender) {
                    CommandBook.inst().checkPermission(sender, "commandbook.whois.other");
                }
            }
            PlayerWhoisEvent event = new PlayerWhoisEvent(offline, sender);
            if (offline instanceof Player) {
                OfflinePlayer player = offline;
                event.addWhoisInformation("Display name", player.getDisplayName());
                event.addWhoisInformation("Entity ID #", player.getEntityId());
                event.addWhoisInformation("Current vehicle", player.getVehicle());
                if (CommandBook.inst().hasPermission(sender, "commandbook.ip-address")) {
                    event.addWhoisInformation("Address", player.getAddress().toString());
                }
                event.addWhoisInformation("Game mode", (Object)player.getGameMode());
            }
            if ((bedSpawn = offline.getBedSpawnLocation()) != null) {
                event.addWhoisInformation("Bed spawn location", LocationUtil.toFriendlyString(bedSpawn));
            } else {
                event.addWhoisInformation(null, "No bed spawn location");
            }
            if (offline.hasPlayedBefore()) {
                event.addWhoisInformation(null, "First joined: " + this.dateFormat.format(offline.getFirstPlayed()) + "; Last joined: " + this.dateFormat.format(offline.getLastPlayed()));
            }
            CommandBook.callEvent(event);
            ArrayList<String> results = new ArrayList<String>(event.getTaglessWhoisInformation());
            for (Map.Entry<String, String> entry : event.getTaggedWhoisInformation().entrySet()) {
                results.add(entry.getKey() + ": " + entry.getValue());
            }
            new PaginatedResult<String>("Name: " + offline.getName()){

                @Override
                public String format(String entry) {
                    return entry;
                }
            }.display(sender, (List<String>)results, args.getFlagInteger('p', 1));
        }

        @Command(aliases={"compass"}, usage="[player]", desc="Show your current compass direction", flags="", min=0, max=1)
        @CommandPermissions(value={"commandbook.whereami.compass"})
        public void compass(CommandContext args, CommandSender sender) throws CommandException {
            Player player;
            if (args.argsLength() == 0) {
                player = PlayerUtil.checkPlayer(sender);
            } else {
                player = PlayerUtil.matchSinglePlayer(sender, args.getString(0));
                if (player != sender) {
                    CommandBook.inst().checkPermission(sender, "commandbook.whereami.compass.other");
                }
            }
            sender.sendMessage((Object)((Object)ChatColor.YELLOW) + String.format("Your direction: %s", CommandBookUtil.getCardinalDirection(player)));
        }

        @Command(aliases={"biome"}, usage="[player]", desc="Get your current biome", flags="", min=0, max=1)
        @CommandPermissions(value={"commandbook.biome"})
        public void biome(CommandContext args, CommandSender sender) throws CommandException {
            Player player;
            if (args.argsLength() == 0) {
                player = PlayerUtil.checkPlayer(sender);
            } else {
                player = PlayerUtil.matchSinglePlayer(sender, args.getString(0));
                if (player != sender) {
                    CommandBook.inst().checkPermission(sender, "commandbook.biome.other");
                }
            }
            Location loc = player.getLocation();
            String biomeName = loc.getWorld().getBiome(loc.getBlockX(), loc.getBlockZ()).name().toLowerCase().replace("_", " ");
            if (player.equals(sender)) {
                sender.sendMessage((Object)((Object)ChatColor.YELLOW) + "You are in the " + biomeName + " biome.");
            } else {
                sender.sendMessage((Object)((Object)ChatColor.YELLOW) + PlayerUtil.toColoredName(player, ChatColor.YELLOW) + " is in the " + biomeName + " biome.");
            }
        }
    }

    public static class PlayerWhoisEvent
    extends Event {
        private final OfflinePlayer player;
        private final CommandSender source;
        private final Map<String, String> taggedWhoisInformation = new LinkedHashMap<String, String>();
        private final List<String> taglessWhoisInformation = new ArrayList<String>();
        private static final HandlerList handlers = new HandlerList();

        public PlayerWhoisEvent(OfflinePlayer player, CommandSender source) {
            this.player = player;
            this.source = source;
        }

        public OfflinePlayer getPlayer() {
            return this.player;
        }

        public CommandSender getSource() {
            return this.source;
        }

        public void addWhoisInformation(String key, Object value) {
            if (value == null) {
                this.addWhoisInformation(key, null);
            } else {
                this.addWhoisInformation(key, String.valueOf(value));
            }
        }

        public void addWhoisInformation(String key, String value) {
            if (key == null) {
                this.taglessWhoisInformation.add(value);
            } else if (value == null) {
                this.taggedWhoisInformation.remove(key);
            } else {
                this.taggedWhoisInformation.put(key, value);
            }
        }

        public Map<String, String> getTaggedWhoisInformation() {
            return Collections.unmodifiableMap(this.taggedWhoisInformation);
        }

        public List<String> getTaglessWhoisInformation() {
            return Collections.unmodifiableList(this.taglessWhoisInformation);
        }

        @Override
        public HandlerList getHandlers() {
            return handlers;
        }

        public static HandlerList getHandlerList() {
            return handlers;
        }
    }
}

