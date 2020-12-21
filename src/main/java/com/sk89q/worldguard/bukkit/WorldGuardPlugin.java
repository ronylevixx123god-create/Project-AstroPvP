/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.sk89q.bukkit.util.CommandsManagerRegistration
 *  com.sk89q.minecraft.util.commands.CommandException
 *  com.sk89q.minecraft.util.commands.CommandPermissionsException
 *  com.sk89q.minecraft.util.commands.CommandUsageException
 *  com.sk89q.minecraft.util.commands.CommandsManager
 *  com.sk89q.minecraft.util.commands.Injector
 *  com.sk89q.minecraft.util.commands.MissingNestedCommandException
 *  com.sk89q.minecraft.util.commands.SimpleInjector
 *  com.sk89q.minecraft.util.commands.WrappedCommandException
 *  com.sk89q.wepif.PermissionsResolverManager
 *  com.sk89q.worldedit.bukkit.WorldEditPlugin
 */
package com.sk89q.worldguard.bukkit;

import com.sk89q.bukkit.util.CommandsManagerRegistration;
import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.minecraft.util.commands.CommandPermissionsException;
import com.sk89q.minecraft.util.commands.CommandUsageException;
import com.sk89q.minecraft.util.commands.CommandsManager;
import com.sk89q.minecraft.util.commands.Injector;
import com.sk89q.minecraft.util.commands.MissingNestedCommandException;
import com.sk89q.minecraft.util.commands.SimpleInjector;
import com.sk89q.minecraft.util.commands.WrappedCommandException;
import com.sk89q.wepif.PermissionsResolverManager;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.BukkitPlayer;
import com.sk89q.worldguard.bukkit.ConfigurationManager;
import com.sk89q.worldguard.bukkit.FlagStateManager;
import com.sk89q.worldguard.bukkit.LegacyWorldGuardMigration;
import com.sk89q.worldguard.bukkit.WorldGuardBlockListener;
import com.sk89q.worldguard.bukkit.WorldGuardCommandBookListener;
import com.sk89q.worldguard.bukkit.WorldGuardEntityListener;
import com.sk89q.worldguard.bukkit.WorldGuardHangingListener;
import com.sk89q.worldguard.bukkit.WorldGuardPlayerListener;
import com.sk89q.worldguard.bukkit.WorldGuardServerListener;
import com.sk89q.worldguard.bukkit.WorldGuardVehicleListener;
import com.sk89q.worldguard.bukkit.WorldGuardWeatherListener;
import com.sk89q.worldguard.bukkit.WorldGuardWorldListener;
import com.sk89q.worldguard.bukkit.commands.GeneralCommands;
import com.sk89q.worldguard.bukkit.commands.ProtectionCommands;
import com.sk89q.worldguard.bukkit.commands.ToggleCommands;
import com.sk89q.worldguard.protection.GlobalRegionManager;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.util.FatalConfigurationLoadingException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permissible;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class WorldGuardPlugin
extends JavaPlugin {
    private static WorldGuardPlugin inst;
    private final CommandsManager<CommandSender> commands;
    private final GlobalRegionManager globalRegionManager;
    private final ConfigurationManager configuration = new ConfigurationManager(this);
    private FlagStateManager flagStateManager;

    public WorldGuardPlugin() {
        this.globalRegionManager = new GlobalRegionManager(this);
        final WorldGuardPlugin plugin = inst = this;
        this.commands = new CommandsManager<CommandSender>(){

            public boolean hasPermission(CommandSender player, String perm) {
                return plugin.hasPermission(player, perm);
            }
        };
    }

    public static WorldGuardPlugin inst() {
        return inst;
    }

    @Override
    public void onEnable() {
        this.commands.setInjector((Injector)new SimpleInjector(new Object[]{this}));
        final CommandsManagerRegistration reg = new CommandsManagerRegistration((Plugin)this, this.commands);
        reg.register(ToggleCommands.class);
        reg.register(ProtectionCommands.class);
        this.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable(){

            @Override
            public void run() {
                if (!WorldGuardPlugin.this.getGlobalStateManager().hasCommandBookGodMode()) {
                    reg.register(GeneralCommands.class);
                }
            }
        }, 0L);
        this.getDataFolder().mkdirs();
        PermissionsResolverManager.initialize((Plugin)this);
        LegacyWorldGuardMigration.migrateBlacklist(this);
        try {
            this.configuration.load();
            this.globalRegionManager.preload();
        }
        catch (FatalConfigurationLoadingException e) {
            e.printStackTrace();
            this.getServer().shutdown();
        }
        LegacyWorldGuardMigration.migrateRegions(this);
        this.flagStateManager = new FlagStateManager(this);
        if (this.configuration.useRegionsScheduler) {
            this.getServer().getScheduler().scheduleSyncRepeatingTask(this, this.flagStateManager, 20L, 20L);
        }
        new WorldGuardPlayerListener(this).registerEvents();
        new WorldGuardBlockListener(this).registerEvents();
        new WorldGuardEntityListener(this).registerEvents();
        new WorldGuardWeatherListener(this).registerEvents();
        new WorldGuardVehicleListener(this).registerEvents();
        new WorldGuardServerListener(this).registerEvents();
        new WorldGuardHangingListener(this).registerEvents();
        this.configuration.updateCommandBookGodMode();
        if (this.getServer().getPluginManager().isPluginEnabled("CommandBook")) {
            this.getServer().getPluginManager().registerEvents(new WorldGuardCommandBookListener(this), this);
        }
        WorldGuardWorldListener worldListener = new WorldGuardWorldListener(this);
        for (World world : this.getServer().getWorlds()) {
            worldListener.initWorld(world);
        }
        worldListener.registerEvents();
        if (!this.configuration.hasCommandBookGodMode()) {
            for (Player player : this.getServer().getOnlinePlayers()) {
                if (!this.inGroup(player, "wg-invincible") && (!this.configuration.autoGodMode || !this.hasPermission(player, "worldguard.auto-invincible"))) continue;
                this.configuration.enableGodMode(player);
            }
        }
    }

    @Override
    public void onDisable() {
        this.globalRegionManager.unload();
        this.configuration.unload();
        this.getServer().getScheduler().cancelTasks(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        try {
            this.commands.execute(cmd.getName(), args, (Object)sender, new Object[]{sender});
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
        catch (CommandException e) {
            sender.sendMessage((Object)((Object)ChatColor.RED) + e.getMessage());
        }
        return true;
    }

    public GlobalRegionManager getGlobalRegionManager() {
        return this.globalRegionManager;
    }

    @Deprecated
    public ConfigurationManager getGlobalConfiguration() {
        return this.getGlobalStateManager();
    }

    public FlagStateManager getFlagStateManager() {
        return this.flagStateManager;
    }

    public ConfigurationManager getGlobalStateManager() {
        return this.configuration;
    }

    public boolean inGroup(Player player, String group) {
        try {
            return PermissionsResolverManager.getInstance().inGroup((OfflinePlayer)player, group);
        }
        catch (Throwable t) {
            t.printStackTrace();
            return false;
        }
    }

    public String[] getGroups(Player player) {
        try {
            return PermissionsResolverManager.getInstance().getGroups((OfflinePlayer)player);
        }
        catch (Throwable t) {
            t.printStackTrace();
            return new String[0];
        }
    }

    public String toUniqueName(CommandSender sender) {
        if (sender instanceof ConsoleCommandSender) {
            return "*Console*";
        }
        return sender.getName();
    }

    public String toName(CommandSender sender) {
        if (sender instanceof ConsoleCommandSender) {
            return "*Console*";
        }
        if (sender instanceof Player) {
            return ((Player)sender).getDisplayName();
        }
        return sender.getName();
    }

    public boolean hasPermission(CommandSender sender, String perm) {
        if (sender.isOp()) {
            if (sender instanceof Player) {
                if (this.getGlobalStateManager().get((World)((Player)sender).getWorld()).opPermissions) {
                    return true;
                }
            } else {
                return true;
            }
        }
        if (sender instanceof Player) {
            Player player = (Player)sender;
            return PermissionsResolverManager.getInstance().hasPermission(player.getWorld().getName(), player.getName(), perm);
        }
        return false;
    }

    public void checkPermission(CommandSender sender, String perm) throws CommandPermissionsException {
        if (!this.hasPermission(sender, perm)) {
            throw new CommandPermissionsException();
        }
    }

    public Player checkPlayer(CommandSender sender) throws CommandException {
        if (sender instanceof Player) {
            return (Player)sender;
        }
        throw new CommandException("A player is expected.");
    }

    public List<Player> matchPlayerNames(String filter) {
        Player[] players = this.getServer().getOnlinePlayers();
        if ((filter = filter.toLowerCase()).charAt(0) == '@' && filter.length() >= 2) {
            filter = filter.substring(1);
            for (Player player : players) {
                if (!player.getName().equalsIgnoreCase(filter)) continue;
                ArrayList<Player> list = new ArrayList<Player>();
                list.add(player);
                return list;
            }
            return new ArrayList<Player>();
        }
        if (filter.charAt(0) == '*' && filter.length() >= 2) {
            filter = filter.substring(1);
            ArrayList<Player> list = new ArrayList<Player>();
            for (Player player : players) {
                if (!player.getName().toLowerCase().contains(filter)) continue;
                list.add(player);
            }
            return list;
        }
        ArrayList<Player> list = new ArrayList<Player>();
        for (Player player : players) {
            if (!player.getName().toLowerCase().startsWith(filter)) continue;
            list.add(player);
        }
        return list;
    }

    protected Iterable<Player> checkPlayerMatch(List<Player> players) throws CommandException {
        if (players.size() == 0) {
            throw new CommandException("No players matched query.");
        }
        return players;
    }

    public Iterable<Player> matchPlayers(CommandSender source, String filter) throws CommandException {
        if (this.getServer().getOnlinePlayers().length == 0) {
            throw new CommandException("No players matched query.");
        }
        if (filter.equals("*")) {
            return this.checkPlayerMatch(Arrays.asList(this.getServer().getOnlinePlayers()));
        }
        if (filter.charAt(0) == '#') {
            if (filter.equalsIgnoreCase("#world")) {
                ArrayList<Player> players = new ArrayList<Player>();
                Player sourcePlayer = this.checkPlayer(source);
                World sourceWorld = sourcePlayer.getWorld();
                for (Player player : this.getServer().getOnlinePlayers()) {
                    if (!player.getWorld().equals(sourceWorld)) continue;
                    players.add(player);
                }
                return this.checkPlayerMatch(players);
            }
            if (filter.equalsIgnoreCase("#near")) {
                ArrayList<Player> players = new ArrayList<Player>();
                Player sourcePlayer = this.checkPlayer(source);
                World sourceWorld = sourcePlayer.getWorld();
                Vector sourceVector = sourcePlayer.getLocation().toVector();
                for (Player player : this.getServer().getOnlinePlayers()) {
                    if (!player.getWorld().equals(sourceWorld) || !(player.getLocation().toVector().distanceSquared(sourceVector) < 900.0)) continue;
                    players.add(player);
                }
                return this.checkPlayerMatch(players);
            }
            throw new CommandException("Invalid group '" + filter + "'.");
        }
        List<Player> players = this.matchPlayerNames(filter);
        return this.checkPlayerMatch(players);
    }

    public Player matchSinglePlayer(CommandSender sender, String filter) throws CommandException {
        Iterator<Player> players = this.matchPlayers(sender, filter).iterator();
        Player match = players.next();
        if (players.hasNext()) {
            throw new CommandException("More than one player found! Use @<name> for exact matching.");
        }
        return match;
    }

    public CommandSender matchPlayerOrConsole(CommandSender sender, String filter) throws CommandException {
        if (filter.equalsIgnoreCase("#console") || filter.equalsIgnoreCase("*console*") || filter.equalsIgnoreCase("!")) {
            return this.getServer().getConsoleSender();
        }
        return this.matchSinglePlayer(sender, filter);
    }

    public Iterable<Player> matchPlayers(Player player) {
        return Arrays.asList(player);
    }

    public World matchWorld(CommandSender sender, String filter) throws CommandException {
        List<World> worlds = this.getServer().getWorlds();
        if (filter.charAt(0) == '#') {
            if (filter.equalsIgnoreCase("#main")) {
                return worlds.get(0);
            }
            if (filter.equalsIgnoreCase("#normal")) {
                for (World world : worlds) {
                    if (world.getEnvironment() != World.Environment.NORMAL) continue;
                    return world;
                }
                throw new CommandException("No normal world found.");
            }
            if (filter.equalsIgnoreCase("#nether")) {
                for (World world : worlds) {
                    if (world.getEnvironment() != World.Environment.NETHER) continue;
                    return world;
                }
                throw new CommandException("No nether world found.");
            }
            if (filter.matches("^#player$")) {
                String[] parts = filter.split(":", 2);
                if (parts.length == 1) {
                    throw new CommandException("Argument expected for #player.");
                }
                return this.matchPlayers(sender, parts[1]).iterator().next().getWorld();
            }
            throw new CommandException("Invalid identifier '" + filter + "'.");
        }
        for (World world : worlds) {
            if (!world.getName().equals(filter)) continue;
            return world;
        }
        throw new CommandException("No world by that exact name found.");
    }

    public WorldEditPlugin getWorldEdit() throws CommandException {
        Plugin worldEdit = this.getServer().getPluginManager().getPlugin("WorldEdit");
        if (worldEdit == null) {
            throw new CommandException("WorldEdit does not appear to be installed.");
        }
        if (worldEdit instanceof WorldEditPlugin) {
            return (WorldEditPlugin)worldEdit;
        }
        throw new CommandException("WorldEdit detection failed (report error).");
    }

    public LocalPlayer wrapPlayer(Player player) {
        return new BukkitPlayer(this, player);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void createDefaultConfiguration(File actual, String defaultName) {
        File parent = actual.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }
        if (actual.exists()) {
            return;
        }
        InputStream input = null;
        try {
            JarFile file = new JarFile(this.getFile());
            ZipEntry copy = file.getEntry("defaults/" + defaultName);
            if (copy == null) {
                throw new FileNotFoundException();
            }
            input = file.getInputStream(copy);
        }
        catch (IOException e) {
            this.getLogger().severe("Unable to read default configuration: " + defaultName);
        }
        if (input != null) {
            FileOutputStream output = null;
            try {
                output = new FileOutputStream(actual);
                byte[] buf = new byte[8192];
                int length = 0;
                while ((length = input.read(buf)) > 0) {
                    output.write(buf, 0, length);
                }
                this.getLogger().info("Default configuration file written: " + actual.getAbsolutePath());
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    if (input != null) {
                        input.close();
                    }
                }
                catch (IOException iOException) {}
                try {
                    if (output != null) {
                        output.close();
                    }
                }
                catch (IOException iOException) {}
            }
        }
    }

    public void broadcastNotification(String msg) {
        this.getServer().broadcast(msg, "worldguard.notify");
        Set<Permissible> subs = this.getServer().getPluginManager().getPermissionSubscriptions("worldguard.notify");
        for (Player player : this.getServer().getOnlinePlayers()) {
            if (subs.contains(player) && player.hasPermission("worldguard.notify") || !this.hasPermission(player, "worldguard.notify")) continue;
            player.sendMessage(msg);
        }
        this.getLogger().info(msg);
    }

    public void forgetPlayer(Player player) {
        this.flagStateManager.forget(player);
    }

    public boolean canBuild(Player player, Location loc) {
        return this.getGlobalRegionManager().canBuild(player, loc);
    }

    public boolean canBuild(Player player, Block block) {
        return this.getGlobalRegionManager().canBuild(player, block);
    }

    public RegionManager getRegionManager(World world) {
        if (!this.getGlobalStateManager().get((World)world).useRegions) {
            return null;
        }
        return this.getGlobalRegionManager().get(world);
    }

    public String replaceMacros(CommandSender sender, String message) {
        Player[] online = this.getServer().getOnlinePlayers();
        message = message.replace("%name%", this.toName(sender));
        message = message.replace("%id%", this.toUniqueName(sender));
        message = message.replace("%online%", String.valueOf(online.length));
        if (sender instanceof Player) {
            Player player = (Player)sender;
            World world = player.getWorld();
            message = message.replace("%world%", world.getName());
            message = message.replace("%health%", String.valueOf(player.getHealth()));
        }
        return message;
    }
}

