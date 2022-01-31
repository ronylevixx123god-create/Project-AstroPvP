package org.arkhamnetwork.enchantments.enchantments.armor;

import com.rit.sucy.*;
import java.util.*;
import org.bukkit.scheduler.*;
import org.arkhamnetwork.enchantments.util.*;
import org.bukkit.event.entity.*;
import java.util.concurrent.*;
import org.bukkit.entity.*;
import org.arkhamnetwork.enchantments.*;
import org.bukkit.plugin.*;
import org.bukkit.*;
import org.bukkit.metadata.*;

public class Angelic extends CustomEnchantment
{
    HashMap<String, BukkitTask> playerTasks;

    public Angelic() {
        super("Angelic", GeneralUtil.armor, 2);
        this.playerTasks = new HashMap<String, BukkitTask>();
        this.max = 5;
        this.base = 15.0;
        this.interval = 3.0;
    }

    public void applyDefenseEffect(final LivingEntity player, final LivingEntity attacker, final int level, final EntityDamageEvent e) {
        if (player.hasMetadata("neutralized")) {
            return;
        }
        if (player.hasMetadata("noDefenseProcs")) {
            return;
        }
        final double random = Math.random();
        final double chance = 0.005 * level;
        if (random < chance) {
            long healDelay = 250L;
            if (level <= 1) {
                healDelay = 225L;
            }
            if (level == 2) {
                healDelay = 200L;
            }
            if (level == 3) {
                healDelay = 175L;
            }
            if (level == 4) {
                healDelay = 150L;
            }
            if (level >= 5) {
                healDelay = 125L;
            }
            final long healDuration = Math.max(healDelay + 2L, level * 20L + (10L + ThreadLocalRandom.current().nextInt(16)));
            final double healAmount = 0.5;
            if (player.hasMetadata("angelicTaskID")) {
                return;
            }
            final int taskID = new RegenerationTask((Player)player, healDuration, healDelay, 0.5).runTaskTimer((Plugin)Enchantments.getInstance(), 0L, healDelay).getTaskId();
            Bukkit.getScheduler().runTaskLaterAsynchronously((Plugin)Enchantments.getInstance(), new Runnable() {
                @Override
                public void run() {
                    Bukkit.getScheduler().cancelTask(taskID);
                    Bukkit.getScheduler().runTaskAsynchronously((Plugin)Enchantments.getInstance(), () -> player.removeMetadata("angelicTaskID", (Plugin)Enchantments.getInstance()));
                }
            }, healDuration);
            player.setMetadata("angelicTaskID", new FixedMetadataValue((Plugin)Enchantments.getInstance(), taskID));
        }
    }
}
