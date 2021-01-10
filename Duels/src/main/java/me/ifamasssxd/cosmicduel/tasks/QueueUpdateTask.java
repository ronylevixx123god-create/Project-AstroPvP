/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.bukkit.ChatColor
 *  org.bukkit.Sound
 *  org.bukkit.entity.Player
 */
package me.ifamasssxd.cosmicduel.tasks;

import com.google.common.collect.Lists;
import me.ifamasssxd.cosmicduel.DuelAPI;
import me.ifamasssxd.cosmicduel.struct.enums.DuelCreateState;
import me.ifamasssxd.cosmicduel.struct.enums.DuelState;
import me.ifamasssxd.cosmicduel.struct.newduel.NewDuel;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class QueueUpdateTask
implements Runnable {
    @Override
    public void run() {
        for (NewDuel d : Lists.newArrayList(DuelAPI.duels)) {
            if (d.getCreateState() != DuelCreateState.WAITING || d.getDuelState() != DuelState.IN_QUEUE || !DuelAPI.isDuelQueued(d)) continue;
            for (Player p : d.getPlayers()) {
                p.sendMessage(ChatColor.YELLOW + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.YELLOW + ChatColor.UNDERLINE.toString() + "Duel Queue Update");
                if (d.getPlayers().size() < 2) continue;
                p.sendMessage((Object)ChatColor.YELLOW + "You are #" + DuelAPI.getDuelPosition(d) + (Object)ChatColor.YELLOW + " in Queue for the " + d.getArena().getName() + " Arena " + (Object)ChatColor.YELLOW + "vs " + (Object)ChatColor.YELLOW + ChatColor.BOLD.toString() + d.getOppositePlayer(p).getName() + ".");
                p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1.0f, 0.8f);
            }
        }
    }
}

