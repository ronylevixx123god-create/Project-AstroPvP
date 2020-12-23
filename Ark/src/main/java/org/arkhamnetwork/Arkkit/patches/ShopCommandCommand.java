/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Sound
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.event.player.PlayerTeleportEvent$TeleportCause
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.arkhamnetwork.Arkkit.patches.combatlog.CombatLog;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.arkhamnetwork.Arkkit.utils.LocationUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import java.util.List;

final class ShopCommandCommand
        extends AbstractCommand {
    public ShopCommandCommand(String command, String usage, String description, List<String> aliases) {
        super(command, usage, description, aliases);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            final Player pl = (Player) sender;
            if (CombatLog.inCombat(pl)) {
                pl.sendMessage(ChatColor.RED + "You cannot use /shop while in combat!");
                int time_left_ms = CombatListener.getTimeLeftMSInCombat(pl);
                pl.sendMessage(ChatColor.GRAY + "You will be out of combat in " + ChatColor.RED + time_left_ms + "ms!");
                return true;
            }
            if (args.length == 0) {
                pl.sendMessage(ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + "<" + ChatColor.RED + "NPC" + ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ">" + ChatColor.RED + " Mission_Control:" + ChatColor.WHITE + " We've recieved your request for an " + ChatColor.BOLD + "I.G.E." + ChatColor.WHITE + " transport, " + pl.getName() + ", please standby.");
                int taskID = Bukkit.getScheduler().runTaskTimerAsynchronously(Arkkit.get(), new Runnable() {
                    final Location cast_loc = pl.getLocation();
                    int i = 0;

                    @Override
                    public void run() {
                        if (pl == null || !pl.isOnline() || !ShopCommandListener.shop_teleport_task_ids.containsKey(pl.getUniqueId())) {
                            return;
                        }
                        if (CombatLog.inCombat(pl)) {
                            pl.sendMessage(ChatColor.RED + "You took damage, your " + ChatColor.BOLD + "I.G.E" + ChatColor.RED + " transport was cancelled!");
                            if (ShopCommandListener.shop_teleport_task_ids.containsKey(pl.getUniqueId())) {
                                int taskid = ShopCommandListener.shop_teleport_task_ids.get(pl.getUniqueId());
                                ShopCommandListener.shop_teleport_task_ids.remove(pl.getUniqueId());
                                Bukkit.getScheduler().cancelTask(taskid);
                                return;
                            }
                        }
                        if (!pl.getLocation().getWorld().equals(this.cast_loc.getWorld()) || pl.getLocation().distanceSquared(this.cast_loc) > 4.0) {
                            pl.sendMessage(ChatColor.RED + "You moved, your " + ChatColor.BOLD + "I.G.E" + ChatColor.RED + " transport is void!");
                            if (ShopCommandListener.shop_teleport_task_ids.containsKey(pl.getUniqueId())) {
                                int taskid = ShopCommandListener.shop_teleport_task_ids.get(pl.getUniqueId());
                                ShopCommandListener.shop_teleport_task_ids.remove(pl.getUniqueId());
                                Bukkit.getScheduler().cancelTask(taskid);
                                return;
                            }
                        }
                        ++this.i;
                        if (this.i >= 6) {
                            Bukkit.getServer().getScheduler().runTask(Arkkit.get(), new Runnable() {

                                @Override
                                public void run() {
                                    if (ArkPlugins.TP_WARMUP.getArkPlugin().isRunning()) {
                                        pl.setMetadata("queuedTeleportPass", new FixedMetadataValue(Arkkit.get(), ShopCommand.shop_location));
                                    }
                                    pl.teleport(ShopCommand.shop_location, PlayerTeleportEvent.TeleportCause.UNKNOWN);
                                    pl.sendMessage(ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + "Welcome to the Intergalactic Exchange!");
                                    pl.sendMessage(ChatColor.GRAY + "The finest market in all the galaxy.");
                                    pl.playSound(pl.getLocation(), Sound.VILLAGER_HAGGLE, 1.0f, 1.0f);
                                }
                            });
                            int taskid = ShopCommandListener.shop_teleport_task_ids.get(pl.getUniqueId());
                            ShopCommandListener.shop_teleport_task_ids.remove(pl.getUniqueId());
                            Bukkit.getScheduler().cancelTask(taskid);
                            return;
                        }
                        pl.sendMessage(ChatColor.RED + "Your transport to the " + ChatColor.BOLD + "I.G.E." + ChatColor.RED + " will arrive in " + (6 - this.i) + " second(s)!");
                    }
                }, 20L, 20L).getTaskId();
                ShopCommandListener.shop_teleport_task_ids.put(pl.getUniqueId(), taskID);
            } else if (args.length >= 1 && pl.isOp()) {
                if (args[0].equals("setshop")) {
                    String shopLocation = LocationUtils.convertLocationToString(pl.getLocation().add(0.0, 1.0, 0.0));
                    ShopCommand.shop_location = pl.getLocation().add(0.0, 1.0, 0.0);
                    Arkkit.get().getConfig().set("settings.shop_command.shop-location", shopLocation);
                    pl.sendMessage(ChatColor.GREEN + "Shop set to " + shopLocation);
                }
                return true;
            }
        }
        return true;
    }
}

