/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.compilex.CosmicOutposts.CosmicOutpostsAPI
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Sound
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityDeathEvent
 *  org.bukkit.event.player.PlayerCommandPreprocessEvent
 *  org.bukkit.event.player.PlayerQuitEvent
 *  org.bukkit.event.player.PlayerTeleportEvent$TeleportCause
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import com.compilex.CosmicOutposts.CosmicOutpostsAPI;
import java.util.HashMap;
import java.util.UUID;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.CombatListener;
import org.arkhamnetwork.Arkkit.patches.ExpBottlesCommand;
import org.arkhamnetwork.Arkkit.patches.ShopCommand;
import org.arkhamnetwork.Arkkit.patches.combatlog.CombatLog;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

final class ShopCommandListener
implements Listener {
    protected static HashMap<UUID, Integer> shop_teleport_task_ids = new HashMap();

    ShopCommandListener() {
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player pl = e.getPlayer();
        if (shop_teleport_task_ids.containsKey(pl.getUniqueId())) {
            int taskid = shop_teleport_task_ids.get(pl.getUniqueId());
            shop_teleport_task_ids.remove(pl.getUniqueId());
            Bukkit.getScheduler().cancelTask(taskid);
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void onEntityDeath(EntityDeathEvent e) {
        Player pl;
        if (e.getEntity() instanceof Player && shop_teleport_task_ids.containsKey((pl = (Player)e.getEntity()).getUniqueId())) {
            int taskid = shop_teleport_task_ids.get(pl.getUniqueId());
            shop_teleport_task_ids.remove(pl.getUniqueId());
            Bukkit.getScheduler().cancelTask(taskid);
        }
    }

    @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent e) {
        if (e.getMessage().equalsIgnoreCase("/shop tp") || e.getMessage().equalsIgnoreCase("/shop teleport") || e.getMessage().equalsIgnoreCase("/warp shop")) {
            final Player pl = e.getPlayer();
            e.setCancelled(true);
            e.setMessage("/null");
            if (CombatLog.inCombat(pl)) {
                pl.sendMessage((Object)ChatColor.RED + "You cannot use /shop while in combat!");
                int time_left_ms = CombatListener.getTimeLeftMSInCombat(pl);
                pl.sendMessage((Object)ChatColor.GRAY + "You will be out of combat in " + (Object)ChatColor.RED + time_left_ms + "ms!");
                return;
            }
            if (!ExpBottlesCommand.canMakeXPBottle(pl)) {
                boolean noLimit = false;
                if (Bukkit.getPluginManager().isPluginEnabled("CosmicOutposts") && CosmicOutpostsAPI.controlsHeroOutpost((Player)pl)) {
                    pl.sendMessage(ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.GREEN + "No XP Exhaustion " + (Object)ChatColor.GRAY + "[Hero Outpost Perk]");
                    noLimit = true;
                }
                if (!noLimit) {
                    pl.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.RED + "You cannot teleport for " + ExpBottlesCommand.getTimeString(((MetadataValue)pl.getMetadata("nextExpBottle").get(0)).asLong() - System.currentTimeMillis()) + " due to EXP exhaustion.");
                    if (!pl.hasPermission("playervaults.amount.15")) {
                        pl.sendMessage((Object)ChatColor.GRAY + "Upgrade your rank at " + (Object)ChatColor.UNDERLINE + "buy.cosmicpvp.com" + (Object)ChatColor.GRAY + " to decrease this delay.");
                    }
                    e.setCancelled(true);
                }
                return;
            }
            pl.sendMessage(ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + "<" + (Object)ChatColor.RED + "NPC" + ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + ">" + (Object)ChatColor.RED + " Mission_Control:" + (Object)ChatColor.WHITE + " We've recieved your request for an " + (Object)ChatColor.BOLD + "I.G.E." + (Object)ChatColor.WHITE + " transport, " + pl.getName() + ", please standby.");
            int taskID = Bukkit.getScheduler().runTaskTimerAsynchronously((Plugin)Arkkit.get(), new Runnable(){
                int i = 0;
                final Location cast_loc = pl.getLocation();

                @Override
                public void run() {
                    if (pl == null || !pl.isOnline() || !shop_teleport_task_ids.containsKey(pl.getUniqueId())) {
                        return;
                    }
                    if (CombatLog.inCombat(pl)) {
                        pl.sendMessage((Object)ChatColor.RED + "You took damage, your " + (Object)ChatColor.BOLD + "I.G.E" + (Object)ChatColor.RED + " transport was cancelled!");
                        if (shop_teleport_task_ids.containsKey(pl.getUniqueId())) {
                            int taskid = shop_teleport_task_ids.get(pl.getUniqueId());
                            shop_teleport_task_ids.remove(pl.getUniqueId());
                            Bukkit.getScheduler().cancelTask(taskid);
                            return;
                        }
                    }
                    if (!pl.getLocation().getWorld().equals((Object)this.cast_loc.getWorld()) || pl.getLocation().distanceSquared(this.cast_loc) > 4.0) {
                        pl.sendMessage((Object)ChatColor.RED + "You moved, your " + (Object)ChatColor.BOLD + "I.G.E" + (Object)ChatColor.RED + " transport is void!");
                        if (shop_teleport_task_ids.containsKey(pl.getUniqueId())) {
                            int taskid = shop_teleport_task_ids.get(pl.getUniqueId());
                            shop_teleport_task_ids.remove(pl.getUniqueId());
                            Bukkit.getScheduler().cancelTask(taskid);
                            return;
                        }
                    }
                    ++this.i;
                    if (this.i >= 6) {
                        Bukkit.getServer().getScheduler().runTask((Plugin)Arkkit.get(), new Runnable(){

                            @Override
                            public void run() {
                                if (ArkPlugins.TP_WARMUP.getArkPlugin().isRunning()) {
                                    pl.setMetadata("queuedTeleportPass", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)ShopCommand.shop_location));
                                }
                                pl.teleport(ShopCommand.shop_location, PlayerTeleportEvent.TeleportCause.UNKNOWN);
                                pl.sendMessage(ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + "Welcome to the Intergalactic Exchange!");
                                pl.sendMessage((Object)ChatColor.GRAY + "The finest market in all the galaxy.");
                                pl.playSound(pl.getLocation(), Sound.VILLAGER_HAGGLE, 1.0f, 1.0f);
                            }
                        });
                        int taskid = shop_teleport_task_ids.get(pl.getUniqueId());
                        shop_teleport_task_ids.remove(pl.getUniqueId());
                        Bukkit.getScheduler().cancelTask(taskid);
                        return;
                    }
                    pl.sendMessage((Object)ChatColor.RED + "Your transport to the " + (Object)ChatColor.BOLD + "I.G.E." + (Object)ChatColor.RED + " will arrive in " + (6 - this.i) + " second(s)!");
                }
            }, 20L, 20L).getTaskId();
            shop_teleport_task_ids.put(pl.getUniqueId(), taskID);
        }
    }
}

