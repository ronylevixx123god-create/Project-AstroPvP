/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.earth2me.essentials.User
 *  com.massivecraft.factions.Board
 *  com.massivecraft.factions.FLocation
 *  me.ifamasssxd.cosmicfactionbanners.CosmicFactionBanners
 *  net.minecraft.util.org.apache.commons.io.FileUtils
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.World
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.event.player.PlayerTeleportEvent$TeleportCause
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.earth2me.essentials.User;
import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import me.ifamasssxd.cosmicfactionbanners.CosmicFactionBanners;
import net.minecraft.util.org.apache.commons.io.FileUtils;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.Plugin;

final class CommandWorldEvent
extends AbstractCommand {
    public CommandWorldEvent(String command, String usage, String description, List<String> aliases) {
        super(command, usage, description, aliases);
    }

    @Override
    public boolean onCommand(final CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            if (sender.isOp()) {
                sender.sendMessage("/worldevent <upload/download/start/stop [now]/copyflags/copyworld/purge/testspawn/toggleclaim/url/help/maxclaims/togglebanners>");
            }
            return true;
        }
        String opperation = args[0];
        if (!sender.isOp() && !opperation.equalsIgnoreCase("help")) {
            return true;
        }
        if (opperation.equalsIgnoreCase("upload")) {
            sender.sendMessage("");
            sender.sendMessage(ChatColor.YELLOW.toString() + (Object)ChatColor.BOLD + "** WORLD EVENT UPLOAD **");
            if (AdminEvents.isAdminEventRunning) {
                sender.sendMessage((Object)ChatColor.RED + "ERROR: Admin Event is currently running. Use " + (Object)ChatColor.UNDERLINE + "/worldevent stop" + (Object)ChatColor.RED + " before attempting to upload/download.");
                return true;
            }
            if (!new File("world_event").exists()) {
                sender.sendMessage((Object)ChatColor.RED + "ERROR: No world_event world data found on local server.");
                return true;
            }
            World w = Bukkit.getWorld((String)"world_event");
            if (w != null) {
                sender.sendMessage((Object)ChatColor.YELLOW + "world_event is loaded into memory, unloading...");
                for (Player p : w.getPlayers()) {
                    p.teleport(Bukkit.getWorld((String)"world").getSpawnLocation(), PlayerTeleportEvent.TeleportCause.UNKNOWN);
                }
                w.save();
                if (!Bukkit.unloadWorld((World)w, (boolean)true)) {
                    sender.sendMessage((Object)ChatColor.RED + "ERROR: Could not unload world_event from memory.");
                    return true;
                }
            }
            if (new File("world_event.zip").exists()) {
                sender.sendMessage((Object)ChatColor.YELLOW + "deleting old local world_event.zip archive...");
                if (!new File("world_event.zip").delete()) {
                    sender.sendMessage((Object)ChatColor.RED + "ERROR: Could not delete old world_event.zip file.");
                    return true;
                }
            }
            sender.sendMessage((Object)ChatColor.YELLOW + "creating local world_event.zip file...");
            new File("world_event/session.lock").delete();
            new File("world_event/uid.dat").delete();
            AdminEvents.zipFile("world_event", "world_event.zip");
            sender.sendMessage((Object)ChatColor.YELLOW + "uploading world_event regions.yml file...");
            AdminEvents.uploadFile(new File("plugins/WorldGuard/worlds/world_event/regions.yml"), "world_event-regions.yml", new UploadFinishCallback(){

                @Override
                public void onFinish() {
                    sender.sendMessage((Object)ChatColor.YELLOW + "local world_event regions.yml file uploaded!");
                }
            });
            sender.sendMessage((Object)ChatColor.YELLOW + "uploading world_event.zip file...");
            AdminEvents.uploadFile(new File("world_event.zip"), "world_event.zip", new UploadFinishCallback(){

                @Override
                public void onFinish() {
                    sender.sendMessage((Object)ChatColor.YELLOW + "local world_event world folder uploaded!");
                    sender.sendMessage(ChatColor.GREEN.toString() + (Object)ChatColor.BOLD + "WORLD EVENT UPLOAD COMPLETE!");
                }
            });
        } else if (opperation.equalsIgnoreCase("download")) {
            sender.sendMessage("");
            sender.sendMessage(ChatColor.YELLOW.toString() + (Object)ChatColor.BOLD + "** WORLD EVENT DOWNLOAD **");
            if (AdminEvents.isAdminEventRunning) {
                sender.sendMessage((Object)ChatColor.RED + "ERROR: Admin Event is currently running. Use " + (Object)ChatColor.UNDERLINE + "/worldevent stop" + (Object)ChatColor.RED + " before attempting to upload/download.");
                return true;
            }
            if (new File("world_event").exists()) {
                World w = Bukkit.getWorld((String)"world_event");
                if (w != null) {
                    sender.sendMessage((Object)ChatColor.YELLOW + "world_event is loaded into memory, unloading...");
                    for (Player p : w.getPlayers()) {
                        p.teleport(Bukkit.getWorld((String)"world").getSpawnLocation(), PlayerTeleportEvent.TeleportCause.UNKNOWN);
                    }
                    if (!Bukkit.unloadWorld((World)w, (boolean)false)) {
                        sender.sendMessage((Object)ChatColor.RED + "ERROR: Could not unload world_event from memory.");
                        return true;
                    }
                }
                sender.sendMessage((Object)ChatColor.YELLOW + "deleting local world_event world folder...");
                try {
                    FileUtils.deleteDirectory((File)new File("world_event"));
                }
                catch (IOException e) {
                    sender.sendMessage((Object)ChatColor.RED + "ERROR: Could not delete local world_event world folder.");
                    e.printStackTrace();
                    return true;
                }
            }
            sender.sendMessage((Object)ChatColor.YELLOW + "downloading world_event regions.yml file...");
            AdminEvents.downloadFile(new File("plugins/WorldGuard/worlds/world_event/regions.yml"), "world_event-regions.yml", true, new DownloadFinishCallback(){

                @Override
                public void onFinish() {
                    sender.sendMessage((Object)ChatColor.YELLOW + "local world_event regions.yml file downloaded...");
                }
            });
            sender.sendMessage((Object)ChatColor.YELLOW + "downloading world_event.zip file...");
            AdminEvents.downloadFile(new File("world_event.zip"), "world_event.zip", true, new DownloadFinishCallback(){

                @Override
                public void onFinish() {
                    sender.sendMessage((Object)ChatColor.YELLOW + "extracting local world_event.zip file...");
                    Bukkit.getScheduler().runTaskAsynchronously((Plugin)Arkkit.get(), new Runnable(){

                        @Override
                        public void run() {
                            AdminEvents.unzip("world_event.zip", new File("").getAbsolutePath());
                            sender.sendMessage((Object)ChatColor.YELLOW + "local world_event.zip file extracted...");
                            sender.sendMessage(ChatColor.GREEN.toString() + (Object)ChatColor.BOLD + "WORLD EVENT DOWNLOAD COMPLETE!");
                        }
                    });
                }
            });
        } else if (opperation.equalsIgnoreCase("start")) {
            if (AdminEvents.isAdminEventRunning) {
                sender.sendMessage((Object)ChatColor.RED + "ERROR: Admin Event is already running! Use /worldevent stop");
                return true;
            }
            if (args.length != 2) {
                sender.sendMessage((Object)ChatColor.RED + "ERROR: Please specify the name of admin event you are starting!");
                sender.sendMessage((Object)ChatColor.WHITE + "/worldevent start The_Name_of_your_cool_EVENT!");
                sender.sendMessage((Object)ChatColor.GRAY + "(Colors codes are supported via &)");
                return true;
            }
            AdminEvents.eventName = ChatColor.translateAlternateColorCodes((char)'&', (String)args[1].replace("_", " "));
            World w = Bukkit.getWorld((String)"world_event");
            if (w == null) {
                if (new File("world_event").exists()) {
                    sender.sendMessage((Object)ChatColor.RED + "ERROR: Please load world_event into memory with /mv import or /mv load before running /worldevent start");
                    return true;
                }
                sender.sendMessage((Object)ChatColor.RED + "ERROR: No local world_event data found. Use /worldevent download");
                return true;
            }
            Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)"arkreload tnt_limiter");
            AdminEvents.isAdminEventRunning = true;
            sender.sendMessage((Object)ChatColor.GREEN + "" + (Object)ChatColor.BOLD + "ADMIN EVENT ENABLED!");
            Bukkit.broadcastMessage((String)"");
            Bukkit.broadcastMessage((String)((Object)ChatColor.DARK_RED + "" + (Object)ChatColor.BOLD + "** WORLD EVENT: " + (Object)ChatColor.RESET + AdminEvents.eventName + (Object)ChatColor.DARK_RED + (Object)ChatColor.BOLD + " **"));
            Bukkit.broadcastMessage((String)((Object)ChatColor.RED + "The " + AdminEvents.eventName + (Object)ChatColor.RED + " admin event is starting! Enter the event portal at /spawn to participate!"));
            Bukkit.broadcastMessage((String)((Object)ChatColor.RED + "Use " + (Object)ChatColor.DARK_RED + "/worldevent help" + (Object)ChatColor.RED + " for more information!"));
            Bukkit.broadcastMessage((String)"");
        } else {
            if (opperation.equalsIgnoreCase("stop")) {
                if (!AdminEvents.isAdminEventRunning) {
                    sender.sendMessage((Object)ChatColor.RED + "ERROR: No Admin Event is running! Use /worldevent start");
                    return true;
                }
                boolean skipCountdown = args.length == 2 && args[1].equalsIgnoreCase("now");
                final long countdownTicks = !skipCountdown && Bukkit.getWorld((String)"world_event") != null && Bukkit.getWorld((String)"world_event").getPlayers().size() > 0 ? 200L : 1L;
                final int taskID = Bukkit.getScheduler().runTaskTimer((Plugin)Arkkit.get(), new Runnable(){
                    int secondsLeft;
                    {
                        this.secondsLeft = (int)(countdownTicks / 20L);
                    }

                    @Override
                    public void run() {
                        if (this.secondsLeft <= 0) {
                            return;
                        }
                        for (Player p : Bukkit.getWorld((String)"world_event").getPlayers()) {
                            if (this.secondsLeft == 10) {
                                p.sendMessage("");
                            }
                            p.sendMessage(ChatColor.RED.toString() + (Object)ChatColor.BOLD + "** WORLD EVENT ENDING... **");
                            p.sendMessage((Object)ChatColor.GRAY + "You will be " + (Object)ChatColor.RED + "teleported to /spawn" + (Object)ChatColor.GRAY + " in " + (Object)ChatColor.UNDERLINE + this.secondsLeft + " second(s)");
                            if (this.secondsLeft != 10) continue;
                            p.sendMessage("");
                        }
                        --this.secondsLeft;
                    }
                }, 0L, 20L).getTaskId();
                Bukkit.getScheduler().runTaskLater((Plugin)Arkkit.get(), new Runnable(){

                    @Override
                    public void run() {
                        Bukkit.getScheduler().cancelTask(taskID);
                        World w = Bukkit.getWorld((String)"world_event");
                        if (w != null) {
                            sender.sendMessage((Object)ChatColor.YELLOW + "unloading local world_event map...");
                            for (Player p : w.getPlayers()) {
                                p.teleport(Bukkit.getWorld((String)"world").getSpawnLocation(), PlayerTeleportEvent.TeleportCause.UNKNOWN);
                                p.sendMessage("");
                                p.sendMessage(ChatColor.GREEN.toString() + (Object)ChatColor.BOLD + "** WORLD EVENT COMPLETE **");
                                p.sendMessage((Object)ChatColor.GRAY + "You have been " + (Object)ChatColor.RED + "teleported to /spawn" + (Object)ChatColor.GRAY + " because the world event is over!");
                                p.sendMessage("");
                            }
                            for (String homeString : AdminEventsListener.eventWorldHomes) {
                                String playerName = homeString.split(":")[0];
                                String homeName = homeString.split(":")[1];
                                try {
                                    User u = SmarterEssentials.ess3.getUser(playerName);
                                    if (u == null || u.getHome(homeName) == null || !u.getHome(homeName).getWorld().getName().equals("world_event")) continue;
                                    Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)("delhome " + playerName + ":" + homeName));
                                    if (Bukkit.getPlayer((String)playerName) != null) {
                                        Bukkit.getPlayer((String)playerName).sendMessage((Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.RED + "Your /sethome: " + (Object)ChatColor.GRAY + homeName + (Object)ChatColor.RED + " has been removed as the World Event has ended.");
                                    }
                                    Bukkit.getLogger().info("[AdminEvents] Removed /sethome in world_event: " + homeString);
                                }
                                catch (Exception e) {
                                    e.printStackTrace();
                                    Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)("delhome " + playerName + ":" + homeName));
                                }
                            }
                            ArrayList<FLocation> claimsToRemove = new ArrayList<FLocation>();
                            for (FLocation fL : Board.flocationIds.keySet()) {
                                if (!fL.getWorld().equals((Object)w)) continue;
                                claimsToRemove.add(fL);
                            }
                            for (FLocation fL : claimsToRemove) {
                                Bukkit.getLogger().info("[AdminEvents] Removing faction claim data at: " + fL.toString());
                                Board.removeAt((FLocation)fL);
                            }
                            w.save();
                            if (!Bukkit.unloadWorld((World)w, (boolean)true)) {
                                sender.sendMessage((Object)ChatColor.RED + "ERROR: Could not unload world_event from memory.");
                                return;
                            }
                        }
                        Bukkit.broadcastMessage((String)"");
                        Bukkit.broadcastMessage((String)((Object)ChatColor.DARK_RED + "" + (Object)ChatColor.BOLD + "** WORLD EVENT: " + (Object)ChatColor.RESET + AdminEvents.eventName + (Object)ChatColor.DARK_RED + (Object)ChatColor.BOLD + " **"));
                        Bukkit.broadcastMessage((String)((Object)ChatColor.RED + "The " + AdminEvents.eventName + (Object)ChatColor.RED + " admin event has ended!"));
                        Bukkit.broadcastMessage((String)"");
                        Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)"mv remove world_event");
                        AdminEvents.isAdminEventRunning = false;
                        AdminEventsListener.canClaimInEventWorld = false;
                        sender.sendMessage((Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + "ADMIN EVENT DISABLED!");
                    }
                }, countdownTicks);
                return true;
            }
            if (opperation.equalsIgnoreCase("copyflags")) {
                if (args.length != 3) {
                    sender.sendMessage("/worldevent copyflags <worldFrom> <worldTo>");
                    return true;
                }
                String worldFrom = args[1];
                String worldTo = args[2];
                if (!new File("plugins/WorldGuard/worlds/" + worldFrom + "/regions.yml").exists()) {
                    sender.sendMessage((Object)ChatColor.RED + "ERROR: " + worldFrom + " does not have a valid regions.yml file to copy!");
                    return true;
                }
                try {
                    new File("plugins/WorldGuard/worlds/" + worldTo).mkdirs();
                    FileUtils.copyFile((File)new File("plugins/WorldGuard/worlds/" + worldFrom + "/regions.yml"), (File)new File("plugins/WorldGuard/worlds/" + worldTo + "/regions.yml"));
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                sender.sendMessage((Object)ChatColor.GREEN + "Copied " + worldFrom + "'s region.yml file to " + worldTo + "'s region file. You may need to run /wg reload to refresh loaded region map.");
            } else if (opperation.equalsIgnoreCase("purge")) {
                if (args.length != 1) {
                    sender.sendMessage("/worldevent purge");
                    return true;
                }
                World w = Bukkit.getWorld((String)"world_event");
                if (w != null) {
                    for (Player p : w.getPlayers()) {
                        p.teleport(Bukkit.getWorld((String)"world").getSpawnLocation(), PlayerTeleportEvent.TeleportCause.UNKNOWN);
                    }
                    w.save();
                    if (!Bukkit.unloadWorld((World)w, (boolean)false)) {
                        sender.sendMessage((Object)ChatColor.RED + "ERROR: Could not unload world_event from memory.");
                        return true;
                    }
                }
                try {
                    FileUtils.deleteDirectory((File)new File("world_event"));
                }
                catch (IOException e) {
                    sender.sendMessage((Object)ChatColor.RED + "ERROR: Could not delete world_event folder.");
                    e.printStackTrace();
                }
                sender.sendMessage((Object)ChatColor.GREEN + "Deleted all local world_event map files.");
            } else if (opperation.equalsIgnoreCase("copyworld")) {
                if (args.length != 3) {
                    sender.sendMessage("/worldevent copyworld <worldFrom> <worldTo>");
                    return true;
                }
                String worldFrom = args[1];
                String worldTo = args[2];
                World w = Bukkit.getWorld((String)worldTo);
                if (w != null) {
                    for (Player p : w.getPlayers()) {
                        p.teleport(Bukkit.getWorld((String)"world").getSpawnLocation(), PlayerTeleportEvent.TeleportCause.UNKNOWN);
                    }
                    w.save();
                    if (!Bukkit.unloadWorld((World)w, (boolean)false)) {
                        sender.sendMessage((Object)ChatColor.RED + "ERROR: Could not unload world_event from memory.");
                        return true;
                    }
                }
                try {
                    FileUtils.deleteDirectory((File)new File(worldTo));
                }
                catch (IOException e) {
                    sender.sendMessage((Object)ChatColor.RED + "ERROR: Could not delete world_event folder.");
                    e.printStackTrace();
                }
                try {
                    FileUtils.copyDirectory((File)new File(worldFrom), (File)new File(worldTo));
                }
                catch (IOException e) {
                    sender.sendMessage((Object)ChatColor.RED + "ERROR: Could not copy worldFrom to worldTo folder.");
                    e.printStackTrace();
                }
                new File(worldTo + "/uid.dat").delete();
                new File(worldTo + "/session.lock").delete();
                sender.sendMessage((Object)ChatColor.GREEN + "Copied " + worldFrom + " map data to " + worldTo + "! You can now load it with /mv load " + worldTo + "!");
            } else if (opperation.equalsIgnoreCase("testspawn")) {
                if (args.length != 1) {
                    sender.sendMessage("/worldevent testspawn");
                    return true;
                }
                if (sender instanceof Player) {
                    ((Player)sender).teleport(Bukkit.getWorld((String)"world_event").getSpawnLocation(), PlayerTeleportEvent.TeleportCause.UNKNOWN);
                    sender.sendMessage((Object)ChatColor.GREEN + "This is where players will be teleported to when they enter a world_event region in overworld.");
                } else {
                    sender.sendMessage((Object)ChatColor.RED + "This command only works for players!");
                }
            } else if (opperation.equalsIgnoreCase("toggleclaim")) {
                if (args.length != 1) {
                    sender.sendMessage("/worldevent toggleclaim");
                    return true;
                }
                AdminEventsListener.canClaimInEventWorld = !AdminEventsListener.canClaimInEventWorld;
                sender.sendMessage((Object)(AdminEventsListener.canClaimInEventWorld ? ChatColor.GREEN : ChatColor.RED) + "" + (Object)ChatColor.BOLD + "(!) Claiming allowed in world_event: " + AdminEventsListener.canClaimInEventWorld);
            } else if (opperation.equalsIgnoreCase("url")) {
                if (args.length != 2) {
                    sender.sendMessage("/worldevent url <URL>");
                    return true;
                }
                AdminEvents.eventURL = args[1];
                sender.sendMessage((Object)ChatColor.GREEN + "/worldevent help URL: " + (Object)ChatColor.WHITE + AdminEvents.eventURL);
            } else if (opperation.equalsIgnoreCase("help")) {
                sender.sendMessage("");
                sender.sendMessage((Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + "** " + (Object)ChatColor.DARK_RED + "" + (Object)ChatColor.BOLD + "WORLD EVENT " + (Object)ChatColor.RED + (Object)ChatColor.BOLD + "**");
                if (AdminEvents.isAdminEventRunning) {
                    sender.sendMessage((Object)ChatColor.DARK_RED + "EVENT: " + (Object)ChatColor.RESET + AdminEvents.eventName);
                    sender.sendMessage((Object)ChatColor.DARK_RED + "CLAIMING: " + (Object)ChatColor.RED + (AdminEventsListener.canClaimInEventWorld ? "ON" : "OFF"));
                    sender.sendMessage((Object)ChatColor.DARK_RED + "OBJECTIVE: " + (Object)ChatColor.RED + (AdminEvents.eventURL != null ? AdminEvents.eventURL : "???"));
                    sender.sendMessage("");
                }
                sender.sendMessage((Object)ChatColor.GRAY + "World Events are custom, specialized events run by the server admins! Every event has " + (Object)ChatColor.DARK_RED + "different rules, objectives, and rewards!");
                sender.sendMessage("");
                sender.sendMessage((Object)ChatColor.GRAY + "You can participate in World Events by finding the Event Portal, usually located at " + (Object)ChatColor.DARK_RED + "/spawn" + (Object)ChatColor.GRAY + " whenever an event is running!");
                sender.sendMessage("");
            } else if (opperation.equalsIgnoreCase("togglebanners")) {
                if (CosmicFactionBanners.get().getBannerListener().getBannerAllowedWorlds().contains("world_event")) {
                    CosmicFactionBanners.get().getBannerListener().getBannerAllowedWorlds().remove("world_event");
                    sender.sendMessage(CC.RedB + "DISABLED: Banner placement in world_event");
                } else {
                    CosmicFactionBanners.get().getBannerListener().getBannerAllowedWorlds().add("world_event");
                    sender.sendMessage(CC.GreenB + "ENABLED: Banner placement in world_event");
                }
            } else if (opperation.equalsIgnoreCase("maxclaims")) {
                if (args.length != 2) {
                    sender.sendMessage("/worldevent maxclaims <maxClaimsPerFaction>");
                    return true;
                }
                AdminEvents.maxClaims = Integer.parseInt(args[1]);
                sender.sendMessage((Object)ChatColor.GREEN + "/worldevent maxclaims: " + (Object)ChatColor.WHITE + AdminEvents.maxClaims);
            }
        }
        return true;
    }
}

