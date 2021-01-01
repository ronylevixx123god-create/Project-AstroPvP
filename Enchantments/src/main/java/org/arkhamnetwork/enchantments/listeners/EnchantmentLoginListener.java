package org.arkhamnetwork.enchantments.listeners;

import org.bukkit.event.player.*;
import org.bukkit.*;
import org.arkhamnetwork.enchantments.*;
import org.bukkit.potion.*;
import com.google.common.collect.*;
import com.rit.sucy.enchanting.*;
import org.bukkit.entity.*;
import com.rit.sucy.*;
import java.util.*;
import org.bukkit.plugin.*;
import org.bukkit.event.*;

public class EnchantmentLoginListener implements Listener
{
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerJoin(final PlayerJoinEvent e) {
        final Player p = e.getPlayer();
        Bukkit.getScheduler().runTaskLater((Plugin)Enchantments.getInstance(), (Runnable)new Runnable() {
            @Override
            public void run() {
                if (!p.isOnline()) {
                    return;
                }
                for (final PotionEffect e : p.getActivePotionEffects()) {
                    p.removePotionEffect(e.getType());
                }
                for (final Map.Entry<CustomEnchantment, Integer> data : EListener.getValidEnchantments((LivingEntity)p, Lists.newArrayList(p.getInventory().getArmorContents())).entries()) {
                    data.getKey().applyUnequipEffect(p, (int)data.getValue());
                }
                Bukkit.getScheduler().runTaskLater((Plugin)Enchantments.getInstance(), (Runnable)new Runnable() {
                    @Override
                    public void run() {
                        for (final Map.Entry<CustomEnchantment, Integer> data : EListener.getValidEnchantments((LivingEntity)p, Lists.newArrayList(p.getInventory().getArmorContents())).entries()) {
                            data.getKey().applyEquipEffect(p, (int)data.getValue());
                        }
                    }
                }, 8L);
            }
        }, 2L);
    }
}
