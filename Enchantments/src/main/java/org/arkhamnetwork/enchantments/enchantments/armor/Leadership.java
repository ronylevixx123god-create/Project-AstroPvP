/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.rit.sucy.CustomEnchantment
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Listener
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.enchantments.enchantments.armor;

import com.rit.sucy.CustomEnchantment;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.concurrent.ConcurrentHashMap;

public class Leadership
extends CustomEnchantment {
    protected static ConcurrentHashMap<String, Integer> Leaderships;
    private LeadershipTask LeadershipTask;

    public Leadership() {
        super("Leadership", GeneralUtil.armor, 2);
        this.max = 5;
        this.base = 20.0;
        this.interval = 5.0;
        Leaderships = new ConcurrentHashMap();
        Bukkit.getPluginManager().registerEvents((Listener)new LeadershipListener(), (Plugin)Enchantments.getInstance());
        this.LeadershipTask = new LeadershipTask();
        this.LeadershipTask.runTaskTimer((Plugin)Enchantments.getInstance(), 20L, 120L);
    }

    public void applyEquipEffect(Player player, int enchantLevel) {
        Leaderships.put(player.getName(), enchantLevel);
    }

    public void applyUnequipEffect(Player player, int enchantLevel) {
        Leaderships.remove(player.getName());
    }
}

