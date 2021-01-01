package org.arkhamnetwork.enchantments.enchantments.armor;

import com.cosmicpvp.cosmicutils.utils.GeneralUtil;
import org.bukkit.scheduler.*;
import org.bukkit.entity.*;
import org.bukkit.potion.*;
import com.cosmicpvp.cosmicutils.utils.*;
import java.util.*;
import org.bukkit.*;
import org.arkhamnetwork.enchantments.util.*;

final class CommanderTask extends BukkitRunnable
{
    public void run() {
        for (final Map.Entry<String, Integer> commanderData : Commander.commanders.entrySet()) {
            final String playerName = commanderData.getKey();
            final int level = commanderData.getValue();
            if (Bukkit.getPlayer(playerName) == null) {
                Commander.commanders.remove(playerName);
            }
            else {
                final Player p = Bukkit.getPlayer(playerName);
                final Location l = p.getEyeLocation().add(0.0, 0.5, 0.0);
                int x = 0;
                for (final Entity ent : p.getNearbyEntities((double)(level * 2 + 3), (double)(level * 2 + 3), (double)(level * 2 + 3))) {
                    if (ent instanceof Player && this.isAlly(p, (Player)ent)) {
                        ++x;
                        final Player pAlly = (Player)ent;
                        pAlly.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, ((level > 3) ? 30 : 15) * 20, (int)((level > 4) ? 1 : 0)));
                        if (level >= 2 && pAlly.hasPotionEffect(PotionEffectType.WEAKNESS)) {
                            pAlly.removePotionEffect(PotionEffectType.WEAKNESS);
                        }
                        if (level >= 3 && pAlly.hasPotionEffect(PotionEffectType.CONFUSION)) {
                            pAlly.removePotionEffect(PotionEffectType.CONFUSION);
                        }
                        if (level >= 4) {
                            pAlly.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 300, (int)((level != 4) ? 1 : 0)));
                        }
                        if (level != 5 || !pAlly.hasPotionEffect(PotionEffectType.POISON)) {
                            continue;
                        }
                        pAlly.removePotionEffect(PotionEffectType.POISON);
                    }
                }
                if (x <= 0 || p.getWorld().getName().equals("world_duels") || p.getWorld().getName().equals("world_duels2") || p.getWorld().getName().equals("cosmic-pvparenas")) {
                    continue;
                }
                WorldUtil.sendWorldEffect(l.getBlock(), 57, 2001);
            }
        }
    }
    
    private boolean isAlly(final Player p1, final Player p2) {
        return GeneralUtil.isAlly(p1, p2);
    }
}
