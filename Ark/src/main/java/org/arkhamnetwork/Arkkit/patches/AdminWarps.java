/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.JSONUtils
 *  com.google.common.collect.Lists
 *  net.minecraft.util.com.google.gson.reflect.TypeToken
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.event.player.PlayerTeleportEvent$TeleportCause
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.JSONUtils;
import com.google.common.collect.Lists;
import com.google.common.reflect.TypeToken;
import lombok.SneakyThrows;
import org.arkhamnetwork.Arkkit.libs.ArkPluginCommand;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdminWarps
        extends ArkPlugin {
    private Map warps = new HashMap<String, WarpLocation>();
    private File warpFile;
    private final Type warpMapToken = new TypeToken<HashMap<String, WarpLocation>>() {
    }.getType();

    public AdminWarps(Plugin p) {
        super(p);
    }

    @Override
    public void enable() throws IOException {
        this.warpFile = JSONUtils.getOrCreateFile(this.getBukkitPlugin().getDataFolder(), "adminWarps.json");
        this.warps = (Map) JSONUtils.fromJson(this.warpFile, this.warpMapToken, new HashMap());
        this.registerCommand(new AdminWarpCommand(this));
        this.registerCommand(new DelAdminWarpCommand(this));
        this.registerCommand(new SetAdminWarpCommand(this));
    }

    @Override
    public void disable() {
        this.unregisterCommands();
        this.unregisterListeners();
    }

    private void addWarp(String name, WarpLocation loc) throws IOException {
        this.warps.put(name.toLowerCase(), loc);
        this.saveWarps();
    }

    private boolean removeWarp(String warpName) throws IOException {
        boolean removed;
        boolean bl = removed = this.warps.remove(warpName.toLowerCase()) != null;
        if (removed) {
            this.saveWarps();
        }
        return removed;
    }

    private void saveWarps() throws IOException {
        JSONUtils.saveJSONToFile(this.warpFile, this.warps, this.warpMapToken);
    }

    public Map<String, WarpLocation> getWarps() {
        return this.warps;
    }

    class WarpLocation {
        private final String world;
        private final double x;
        private final double y;
        private final double z;

        public WarpLocation(Location loc) {
            this.world = loc.getWorld().getName();
            this.x = loc.getX();
            this.y = loc.getY();
            this.z = loc.getZ();
        }

        public WarpLocation(String world, double x, double y, double z) {
            this.world = world;
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public Location toBukkitLocation() {
            return new Location(Bukkit.getWorld(this.world), this.x, this.y, this.z);
        }

        public String getWorld() {
            return this.world;
        }

        public double getX() {
            return this.x;
        }

        public double getY() {
            return this.y;
        }

        public double getZ() {
            return this.z;
        }
    }

    class AdminWarpCommand
            extends ArkPluginCommand<AdminWarps> {
        public AdminWarpCommand(AdminWarps patch) {
            super(patch, "adminwarp", "/<command>", "", Lists.newArrayList("awarp", "awarps", "adminwarps"));
        }

        @Override
        public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("list")) {
                    sender.sendMessage(CC.Red + "Listing " + (this.patch).getWarps().size() + " /adminwarp.");
                    ArrayList<String> keys = Lists.newArrayList((this.patch).getWarps().keySet());
                    keys.sort(String::compareTo);
                    keys.forEach(name -> sender.sendMessage(CC.Red + name));
                    return true;
                }
                if (!(sender instanceof Player)) {
                    sender.sendMessage(CC.Red + "Requires Player sender!");
                    return true;
                }
                if (!sender.hasPermission("arkkit.adminwarp." + args[0].toLowerCase())) {
                    this.patch.getBukkitPlugin().getLogger().info(sender.getName() + " didnt have permission to use " + args[0] + "!");
                    return true;
                }
                WarpLocation location = this.patch.getWarps().get(args[0].toLowerCase());
                if (location == null) {
                    sender.sendMessage(CC.Red + "No Admin Warp found with that name!");
                    return true;
                }
                Player player = (Player) sender;
                Location bukkit = location.toBukkitLocation();
                if (bukkit == null || bukkit.getWorld() == null) {
                    sender.sendMessage(CC.Red + "Invalid Bukkit World for warp! World no longer exists?");
                    return true;
                }
                sender.sendMessage(CC.Red + "Teleporting to admin warp...");
                player.teleport(bukkit, PlayerTeleportEvent.TeleportCause.UNKNOWN);
                return true;
            }
            if (!sender.hasPermission("arkkit.adminwarp")) {
                return true;
            }
            sender.sendMessage(CC.Red + "Usage: /adminwarp <warpName>/list");
            return false;
        }
    }

    class SetAdminWarpCommand
            extends ArkPluginCommand<AdminWarps> {
        public SetAdminWarpCommand(AdminWarps patch) {
            super(patch, "setadminwarp");
        }

        @SneakyThrows
        @Override
        public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
            if (!sender.hasPermission("arkkit.adminwarp.set")) {
                return true;
            }
            if (args.length == 1) {
                String warpName = args[0];
                this.patch.addWarp(warpName, new WarpLocation(((Player) sender).getLocation()));
                sender.sendMessage(CC.GreenB + "(!) " + CC.Green + "Set admin warp " + args[0] + "!");
                return true;
            }
            sender.sendMessage(CC.Red + "Usage: /setadminwarp <name>");
            return false;
        }
    }

    class DelAdminWarpCommand
            extends ArkPluginCommand<AdminWarps> {
        public DelAdminWarpCommand(AdminWarps patch) {
            super(patch, "deladminwarp");
        }

        @SneakyThrows
        @Override
        public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
            if (!sender.hasPermission("arkkit.adminwarp.set")) {
                return true;
            }
            if (args.length == 1) {
                if (this.patch.removeWarp(args[0])) {
                    sender.sendMessage(CC.Red + "Admin warp removed!");
                    return true;
                }
                sender.sendMessage(CC.Red + "Admin Warp removed!");
                return false;
            }
            sender.sendMessage(CC.Red + "Usage: /deladminwarp <name>");
            return false;
        }
    }
}

