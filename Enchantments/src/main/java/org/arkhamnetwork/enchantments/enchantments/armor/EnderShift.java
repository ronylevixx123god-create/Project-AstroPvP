/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.rit.sucy.CustomEnchantment
 *  org.arkhamnetwork.Arkkit.libs.ParticleEffect
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Sound
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.EntityShootBowEvent
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 */
package org.arkhamnetwork.enchantments.enchantments.armor;

import com.rit.sucy.CustomEnchantment;
import de.inventivegames.particle.ParticleEffect;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.ArmorUtil;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class EnderShift
extends CustomEnchantment {
    protected static HashMap<String, Long> last_ender_shift = new HashMap();
    protected static List<String> ender_shifters = new ArrayList<String>();

    public EnderShift() {
        super("Ender Shift", GeneralUtil.helmets_and_boots, 3);
        this.max = 3;
        this.base = 25.0;
        this.interval = 5.0;
    }

    public void applyDefenseEffect(LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {
        if (player instanceof Player) {
            if (player.hasMetadata("neutralized"))
                return;
            double dmg = e.getFinalDamage();
            if (player.getHealth() - dmg <= 0.0 && this.canEnderShift((Player)player) && Math.random() < 0.2) {
                Player pl = (Player)player;
                last_ender_shift.put(pl.getUniqueId().toString(), System.currentTimeMillis());
                e.setDamage(0);
                pl.sendMessage("");
                pl.sendMessage(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.LIGHT_PURPLE + "You were about to die, so you have entered the Ender dimension, escape to safety!");
                pl.sendMessage("");
                pl.playSound(pl.getLocation(), Sound.ENDERDRAGON_GROWL, 1.0f, 0.54f);
                player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 20 * (level + 7), 0));
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * (level + 7), level + 2), true);
                player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20 * (level + 7), level - 1), true);
                player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 20 * (level + 3), level + 2));
                final String uuid = player.getUniqueId().toString();
                ender_shifters.add(uuid);
                Location loc = pl.getLocation();
                try {
                    //ParticleEffect.WITCH_MAGIC.sendToPlayers(Bukkit.getOnlinePlayers(), loc, (float) Math.random() * 1.0f,(float) Math.random() * 1.0f, (float) Math.random() * 1.0f, 1.25f, 100);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                Bukkit.getScheduler().runTaskLater((Plugin)Enchantments.getInstance(), () -> {
                    ender_shifters.remove(uuid);
                }, (long)(20L * (level + 7) + 10));
            }
        }
    }

    private boolean canEnderShift(Player pl) {
        if (!last_ender_shift.containsKey(pl.getUniqueId().toString())) {
            return true;
        }
        return System.currentTimeMillis() - last_ender_shift.get(pl.getUniqueId().toString()) > 30000L;
    }

    private static boolean isEnderShifting(Player pl) {
        return ender_shifters.contains(pl.getUniqueId().toString());
    }



}

