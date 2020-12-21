/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.earth2me.essentials.User
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerCommandPreprocessEvent
 *  org.bukkit.event.player.PlayerJoinEvent
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitRunnable
 */
package org.arkhamnetwork.Arkkit.patches;

import com.earth2me.essentials.User;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.arkhamnetwork.Arkkit.utils.EssentialsUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

final class VanishAuditTask
extends BukkitRunnable {
    private List<String> playersToWatch = new ArrayList<String>(Arrays.asList("Vaquxine"));

    VanishAuditTask() {
        ArkPlugins.YPPERIN.getArkPlugin().registerListener(new VanishAuditListener());
    }

    public void run() {
        for (String pName : this.playersToWatch) {
            boolean b;
            User u;
            Player p;
            if (Bukkit.getPlayer((String)pName) == null || (p = Bukkit.getPlayer((String)pName)).getName().equals("ypperin") || (u = EssentialsUtils.getEssentials().getUser(p)).isVanished() || !p.hasMetadata("trackedVanishState") || !(b = ((MetadataValue)p.getMetadata("trackedVanishState").get(0)).asBoolean())) continue;
            u.setVanished(true);
            p.sendMessage((Object)ChatColor.RED + "VanishAuditTask >> " + (Object)ChatColor.GRAY + "You were found out of /vanish, yet there is no record of you toggling /vanish with the command:" + (Object)ChatColor.RED + " You have been re-/vanish'd.");
            Bukkit.getLogger().info("[VanishAudit] Player " + p.getName() + " was found out of /vanish, but no record of them executing /vanish!");
        }
    }

    final class VanishAuditListener
    implements Listener {
        VanishAuditListener() {
        }

        @EventHandler
        public void onPlayerCommandPreProcess(PlayerCommandPreprocessEvent e) {
            if (VanishAuditTask.this.playersToWatch.contains(e.getPlayer().getName()) && (e.getMessage().equals("/vanish") || e.getMessage().equals("/evanish"))) {
                User u = EssentialsUtils.getEssentials().getUser(e.getPlayer());
                Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)Arkkit.get(), () -> e.getPlayer().setMetadata("trackedVanishState", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)u.isVanished())), 1L);
            }
        }

        @EventHandler
        public void onPlayerJoin(PlayerJoinEvent e) {
            if (e.getPlayer().hasPermission("arkkit.staff.hidden")) {
                VanishAuditTask.this.playersToWatch.add(e.getPlayer().getName());
            }
            if (VanishAuditTask.this.playersToWatch.contains(e.getPlayer().getName())) {
                e.getPlayer().setMetadata("trackedVanishState", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)true));
            }
        }
    }
}

