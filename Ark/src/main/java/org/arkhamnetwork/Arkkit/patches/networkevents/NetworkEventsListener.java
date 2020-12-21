/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 *  org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.player.AsyncPlayerChatEvent
 *  org.bukkit.event.player.PlayerCommandPreprocessEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.event.player.PlayerJoinEvent
 *  org.bukkit.event.player.PlayerQuitEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches.networkevents;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.networkevents.IgnoreUtils;
import org.arkhamnetwork.Arkkit.patches.networkevents.NE_SQLConnection;
import org.arkhamnetwork.Arkkit.patches.networkevents.NE_SQLManager;
import org.arkhamnetwork.Arkkit.patches.networkevents.NetworkEvents;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class NetworkEventsListener
implements Listener {
    public static List<String> staffMembers = new ArrayList<String>();
    public static List<UUID> msgToggled = new ArrayList<UUID>();
    private Set<UUID> loadingData = new HashSet<UUID>();

    @EventHandler(priority=EventPriority.LOWEST)
    public void onPlayerCommandPreProcessServer(PlayerCommandPreprocessEvent e) {
        String cmd = e.getMessage().toLowerCase();
        if ((cmd.startsWith("/server h") && !cmd.startsWith("/server hc") || cmd.startsWith("/worlds h") || cmd.startsWith("/instance h")) && !e.getPlayer().hasPermission("staff.sc")) {
            e.setCancelled(true);
            e.setMessage("/null");
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        boolean shouldBeHidden = e.getPlayer().hasPermission("arkkit.staff.hidden");
        if (!staffMembers.contains(e.getPlayer().getName()) && e.getPlayer().hasPermission("bm.command.warn")) {
            if (!shouldBeHidden) {
                staffMembers.add(e.getPlayer().getName());
            }
        } else if (!shouldBeHidden) {
            staffMembers.remove(e.getPlayer().getName());
        }
        UUID id = e.getPlayer().getUniqueId();
        String uuid = id.toString();
        this.loadingData.add(id);
        Bukkit.getScheduler().scheduleAsyncDelayedTask((Plugin)Arkkit.get(), () -> {
            ArrayList<String> ignore_list = new ArrayList<String>();
            Statement statement = null;
            try {
                statement = NE_SQLConnection.getConnection().prepareStatement("SELECT ignore_list FROM arkham_chat WHERE player_uuid='" + uuid + "'");
                ResultSet rs = statement.executeQuery();
                if (rs.next()) {
                    String ignored = rs.getString("ignore_list");
                    if (ignored.contains(",")) {
                        for (String s : ignored.split(",")) {
                            ignore_list.add(s.toLowerCase());
                        }
                    } else {
                        ignore_list.add(ignored.toLowerCase());
                    }
                }
                statement.close();
            }
            catch (SQLException e1) {
                e1.printStackTrace();
            }
            finally {
                if (statement != null) {
                    try {
                        statement.close();
                    }
                    catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }
            this.loadingData.remove(id);
            NetworkEvents.ignored_players.put(uuid, ignore_list);
        });
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        staffMembers.remove(e.getPlayer().getName());
        if (this.loadingData.remove(e.getPlayer().getUniqueId())) {
            return;
        }
        if (NetworkEvents.ignored_players.containsKey(e.getPlayer().getUniqueId().toString()) || msgToggled.contains(e.getPlayer().getUniqueId())) {
            String ignored = this.convertToString(NetworkEvents.ignored_players.get(e.getPlayer().getUniqueId().toString()), ",");
            boolean msgToggle = msgToggled.contains(e.getPlayer().getUniqueId());
            if (ignored.length() > 0 || msgToggle) {
                if (msgToggle) {
                    NE_SQLManager.sql_query.add("REPLACE INTO arkham_chat(player_uuid, ignore_list, msgToggled) VALUES ('" + e.getPlayer().getUniqueId().toString() + "', '" + ignored + "', 1);");
                } else {
                    NE_SQLManager.sql_query.add("REPLACE INTO arkham_chat(player_uuid, ignore_list) VALUES('" + e.getPlayer().getUniqueId().toString() + "', '" + ignored + "');");
                }
            } else {
                NE_SQLManager.sql_query.add("UPDATE arkham_chat SET ignore_list='" + ignored + "', msgToggled = null WHERE player_uuid='" + e.getPlayer().getUniqueId().toString() + "'");
            }
            msgToggled.remove(e.getPlayer().getUniqueId());
            NetworkEvents.ignored_players.remove(e.getPlayer().getUniqueId().toString());
        }
    }

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent e) {
        Player pl = e.getPlayer();
        for (Player ply : new ArrayList(e.getRecipients())) {
            if (!IgnoreUtils.isIgnored(ply, pl.getName())) continue;
            e.getRecipients().remove((Object)ply);
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void onPlayerCommandPreProcess(PlayerCommandPreprocessEvent e) {
        if (!e.getMessage().contains(" ")) {
            return;
        }
        String lowerMsg = e.getMessage().toLowerCase();
        if (e.getPlayer().hasPermission("bm.command.bminfo") || lowerMsg.startsWith("/unblock") || lowerMsg.startsWith("/block") || lowerMsg.startsWith("/ignore") || lowerMsg.startsWith("/unignore") || lowerMsg.startsWith("/f kick") || lowerMsg.startsWith("/f access") || lowerMsg.startsWith("/f ban") || lowerMsg.startsWith("/f f") || lowerMsg.startsWith("/inspect") || lowerMsg.startsWith("/f who") || lowerMsg.startsWith("/f enemy") || lowerMsg.startsWith("/f truce") || lowerMsg.startsWith("/f ally") || lowerMsg.startsWith("/report") || lowerMsg.startsWith("/f t") || lowerMsg.startsWith("/f mod") || lowerMsg.startsWith("/f member") || lowerMsg.startsWith("/f coleader")) {
            return;
        }
        Player bully = e.getPlayer();
        for (String possible_name : e.getMessage().split(" ")) {
            if (Bukkit.getPlayerExact((String)possible_name) == null || !IgnoreUtils.isIgnored(Bukkit.getPlayerExact((String)possible_name), bully.getName())) continue;
            bully.sendMessage((Object)ChatColor.RED + "You cannot execute this command because the player '" + possible_name + "' has blocked you.");
            e.setCancelled(true);
            return;
        }
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onPlayerLeftClickPackedIce(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        if (event.getAction() == Action.LEFT_CLICK_BLOCK && item != null && item.containsEnchantment(Enchantment.DIG_SPEED) && item.getEnchantmentLevel(Enchantment.DIG_SPEED) >= 3 && event.getClickedBlock().getType() == Material.PACKED_ICE) {
            CraftPlayer craftPlayer = (CraftPlayer)event.getPlayer();
            if (craftPlayer.getHandle().playerConnection.networkManager.getVersion() >= 47) {
                Block block = event.getClickedBlock();
                craftPlayer.getHandle().playerInteractManager.breakBlock(block.getX(), block.getY(), block.getZ());
            }
        }
    }

    private String convertToString(List<String> list, String delim) {
        String ss = "";
        for (String s : list) {
            ss = ss + s + delim;
        }
        if (ss.endsWith(delim)) {
            ss = ss.substring(0, ss.length() - 1);
        }
        return ss;
    }
}

