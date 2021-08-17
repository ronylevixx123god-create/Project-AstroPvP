package org.arkhamnetwork.enchantments.enchantments.armor;

import com.cosmicpvp.cosmicutils.utils.GeneralUtil;
import com.cosmicpvp.cosmicutils.utils.WorldUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;

final class CommanderTask extends BukkitRunnable {
    public void run() {
        for (final Map.Entry<String, Integer> commanderData : Commander.commanders.entrySet()) {
            final String playerName = commanderData.getKey();
            final int level = commanderData.getValue();
            if (Bukkit.getPlayer(playerName) == null) {
                Commander.commanders.remove(playerName);
            } else {
                final Player p = Bukkit.getPlayer(playerName);
                final Location l = p.getEyeLocation().add(0.0, 0.5, 0.0);
                int x = 0;
                for (final Entity ent : p.getNearbyEntities((double) (level * 2 + 3), (double) (level * 2 + 3), (double) (level * 2 + 3))) {
                    if (ent instanceof Player && this.isAlly(p, (Player) ent)) {
                        ++x;
                        final Player pAlly = (Player) ent;
                        pAlly.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, ((level > 3) ? 30 : 15) * 20, (int) ((level > 4) ? 1 : 0)));
                        if (level >= 2 && pAlly.hasPotionEffect(PotionEffectType.WEAKNESS)) {
                            pAlly.removePotionEffect(PotionEffectType.WEAKNESS);
                        }
                        if (level >= 3 && pAlly.hasPotionEffect(PotionEffectType.CONFUSION)) {
                            pAlly.removePotionEffect(PotionEffectType.CONFUSION);
                        }
                        if (level >= 4) {
                            pAlly.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 300, (int) ((level != 4) ? 1 : 0)));
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
            }
        }
    }

    private boolean isAlly(final Player p1, final Player p2) {
        return GeneralUtil.isAlly(p1, p2);
    }
}
