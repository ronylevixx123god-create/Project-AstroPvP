/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.util.Vector
 */
package org.arkhamnetwork.Arkkit.patches;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import java.util.Random;

final class DrunkEffects
        implements Runnable {
    private final Plugin plugin;
    private final Random random;
    private final int chanceToStagger = 30;
    private final int chanceToStaggerPerLevel = 10;
    private final int chanceToSober = 15;
    private final int staggerIntensity = 5;
    private final boolean soberOnLogout = true;

    public DrunkEffects(Plugin plugin) {
        this.plugin = plugin;
        this.random = new Random();
    }

    @Override
    public void run() {
        for (Object o : Drinks.getDrunks().keySet().toArray()) {
            String p = (String) o;
            Player player = this.plugin.getServer().getPlayer(p);
            if (player == null || !player.isOnline()) {
                if (!this.soberOnLogout) continue;
                Drinks.getDrunks().remove(p);
                continue;
            }
            int drunkLevel = Drinks.getDrunks().get(p);
            if (this.random.nextInt(100) < this.chanceToStagger + drunkLevel * this.chanceToStaggerPerLevel) {
                Vector v = new Vector((this.random.nextDouble() - 0.5) * ((double) this.staggerIntensity / 10.0) * (double) drunkLevel, 0.0, (this.random.nextDouble() - 0.5) * ((double) this.staggerIntensity / 10.0) * (double) drunkLevel);
                player.setVelocity(v);
            }
            if (this.random.nextInt(100) >= this.chanceToSober) continue;
            if (--drunkLevel == 0) {
                Drinks.getDrunks().remove(p);
                player.sendMessage(ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + "(!) " + ChatColor.GREEN + "You are now sober.");
                if (Drinks.getDrunks().size() != 0) continue;
                Drinks.stopEffects();
                continue;
            }
            Drinks.getDrunks().put(p, drunkLevel);
        }
    }
}

