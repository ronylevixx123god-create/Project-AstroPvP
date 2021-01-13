/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.compilex.CompilexPermissions.CompilexPermissions
 *  com.compilex.CompilexPermissions.structure.PermissionGroup
 *  com.harry5574.utils.hologram.Hologram
 *  me.rohan576.ArkhamChatColors.sql.DataManager
 *  me.rohan576.ArkhamTitleAPI.TitleAPI
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.AsyncPlayerChatEvent
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import com.compilex.CompilexPermissions.CompilexPermissions;
import com.compilex.CompilexPermissions.structure.PermissionGroup;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;

final class ChatBubblesListener
        implements Listener {
    ChatBubblesListener() {
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent e) {
        String color_code;
        Player pl = e.getPlayer();
        PermissionGroup pg = null;
        try {
            pg = CompilexPermissions.get().getPermissionManager().getCachedLoadedGroups().get(CompilexPermissions.get().getDatabaseManager().getGlobalRank(pl.getUniqueId()).toLowerCase());
        } catch (Exception exception) {
            // empty catch block
        }
        if (!pl.hasPermission("arkhamchat.vip") && !pl.hasPermission("arkkit.chatbubbles")) {
            return;
        }
        String title = null;
        ChatColor cc = null;
        final Location loc = pl.getLocation();
        Player fpl = pl;
        String fmsg = (title != null ? ChatColor.DARK_GRAY + "[" + ChatColor.RESET + ChatColor.translateAlternateColorCodes('&', title) + ChatColor.DARK_GRAY + "] " : "") + (pg != null ? ChatColor.translateAlternateColorCodes('&', pg.getPrefix()) + " " : "") + ChatColor.WHITE.toString() + pl.getName() + ": " + cc + e.getMessage();
        final String[] bubble_msg = this.convertStringToBubbleChat(fmsg, cc);
        final double y_boost = (double) bubble_msg.length * 0.2;
        final int length = fmsg.length();
        if (fpl.hasMetadata("chatbubble_hologram")) {
            //Hologram h = (Hologram)((MetadataValue)fpl.getMetadata("chatbubble_hologram").get(0)).value();
            // h.destroy();
            fpl.removeMetadata("chatbubble_hologram", Arkkit.get());
        }
        Bukkit.getScheduler().runTask(Arkkit.get(), new Runnable() {

            @Override
            public void run() {
                //final Hologram hg = new Hologram((Plugin)Arkkit.get(), loc.add(0.0, 1.5 + y_boost, 0.0), bubble_msg);
                Bukkit.getScheduler().runTaskLater(Arkkit.get(), new Runnable() {

                    @Override
                    public void run() {
                        //hg.destroy();
                    }
                }, 80L + (long) length);
            }
        });
    }

    private String[] convertStringToBubbleChat(String msg, ChatColor body_color) {
        ArrayList<String> lmsg = new ArrayList<String>();
        String header = msg.split(":")[0];
        lmsg.add(header);
        msg = msg.replace(header + ": ", "");
        if (msg.length() <= 33) {
            lmsg.add(msg);
            return lmsg.toArray(new String[lmsg.size()]);
        }
        String word_batch = "";
        for (String word : msg.split(" ")) {
            if (word.length() <= 0 || word.length() > 16) continue;
            if (word_batch.length() >= 33) {
                if (word_batch.endsWith(" ")) {
                    word_batch = word_batch.substring(0, word_batch.length() - 1);
                }
                lmsg.add(word_batch);
                word_batch = body_color + word + " ";
                continue;
            }
            word_batch = word_batch + word + " ";
        }
        if (word_batch.length() > 0) {
            lmsg.add(word_batch);
        }
        return lmsg.toArray(new String[lmsg.size()]);
    }
}

