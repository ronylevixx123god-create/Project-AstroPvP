/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.earth2me.essentials.Essentials
 *  com.earth2me.essentials.User
 *  com.google.common.collect.Lists
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Player
 */
package org.arkhamnetwork.Arkkit.patches;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

class FakeVanishTask
implements Runnable {
    private List<String> namesToVanish = Lists.newArrayList("TBNRfrags", "Woofless", "Vikkstar123", "Lachlan", "BOWP", "Kipes", "Vikaiscool_");
    private Essentials ess;
    private final int RADIUS = 600;

    public FakeVanishTask(Essentials ess) {
        this.ess = ess;
    }

    @Override
    public void run() {
        ArrayList<Player> toCheck = Lists.newArrayList();
        for (String name : this.namesToVanish) {
            User user;
            Player player = Bukkit.getPlayer((String)name);
            if (player == null || (user = this.ess.getUser(player)) != null && user.isVanished()) continue;
            toCheck.add(player);
        }
        if (toCheck.size() > 0) {
            for (Player pl : Bukkit.getOnlinePlayers()) {
                for (Player player : toCheck) {
                    if (pl.getWorld().getName().equals(player.getWorld().getName()) && pl.getLocation().distanceSquared(player.getLocation()) <= 600.0) {
                        if (pl.canSee(player)) continue;
                        pl.showPlayer(player);
                        continue;
                    }
                    if (!pl.canSee(player) || pl.isOp()) continue;
                    pl.hidePlayer(player);
                }
            }
            toCheck.clear();
        }
    }
}

