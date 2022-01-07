/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.WorldUtil
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Player
 *  org.bukkit.scheduler.BukkitRunnable
 */
package org.arkhamnetwork.enchantments.enchantments.axes;

import com.cosmicpvp.cosmicutils.utils.WorldUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class CorruptDamageTask
extends BukkitRunnable {
    Player p;
    long durationTicks;
    double dmgValue;

    public CorruptDamageTask(Player p, long durationTicks, double dmgAmount) {
        this.p = p;
        this.durationTicks = durationTicks;
        this.dmgValue = dmgAmount;
    }

    public void run() {
        this.dmgPlayer(this.p, this.dmgValue);
    }

    private void dmgPlayer(Player p, double amount) {
        if (p.getHealth() <= 0.0 || p.isDead()) {
            return;
        }
        p.damage(amount);
    }
}

