/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.WorldUtil
 *  com.rit.sucy.CustomEnchantment
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.block.Block
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.enchantments.enchantments.armor;

import com.cosmicpvp.cosmicutils.utils.WorldUtil;
import com.rit.sucy.CustomEnchantment;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;

import java.util.concurrent.ConcurrentHashMap;

public class Cactus
extends CustomEnchantment {
    protected static ConcurrentHashMap<String, Integer> cactus;

    public Cactus() {
        super("Cactus", GeneralUtil.armor, 2);
        this.max = 2;
        this.base = 20.0;
        this.interval = 5.0;
        cactus = new ConcurrentHashMap();
        Bukkit.getPluginManager().registerEvents((Listener)new CactusListener(), (Plugin)Enchantments.getInstance());
    }

    public void applyEquipEffect(Player player, int enchantLevel) {
        cactus.put(player.getName(), enchantLevel);
    }

    public void applyUnequipEffect(Player player, int enchantLevel) {
        cactus.remove(player.getName());
    }

    public void applyDefenseEffect(LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {
        if(player.hasMetadata("noDefenseProcs"))
            return;
        Player pAttacker;
        Player p;
        if (player instanceof Player && cactus.containsKey((p = (Player)player).getName()) && attacker != null && attacker instanceof Player && (pAttacker = (Player)attacker) != null) {
            if(Math.random() < 0.2) {
                pAttacker.damage(0.5 * (double) level);
                Location l = p.getLocation();
                WorldUtil.sendWorldEffect((Block) l.getBlock(), (int) 81, (int) 2001);
            }
        }
    }
}

