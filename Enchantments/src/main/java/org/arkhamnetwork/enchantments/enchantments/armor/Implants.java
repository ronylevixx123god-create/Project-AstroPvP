package org.arkhamnetwork.enchantments.enchantments.armor;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.rit.sucy.CustomEnchantment;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

public class Implants extends CustomEnchantment {
    private static HashMap<Integer, BukkitTask> implantTasks;

    static {
        Implants.implantTasks = new HashMap<Integer, BukkitTask>();
    }

    private Set<UUID> healthTickedPlayers;
    private Multimap<Integer, Player> implantedPlayers;

    public Implants() {
        super("Implants", GeneralUtil.helmets, 2);
        this.healthTickedPlayers = new HashSet<UUID>();
        this.implantedPlayers = HashMultimap.create();
        this.max = 3;
        this.base = 10.0;
        this.interval = 5.0;
        for (int i = 1; i <= this.max; ++i) {
            final int finalI = i;
            final BukkitTask existing = Implants.implantTasks.get(i);
            if (existing != null) {
                existing.cancel();
            }
            final BukkitTask task = new BukkitRunnable() {
                public void run() {
                    final Collection<Player> leveledPlayers = (Collection<Player>) Implants.this.implantedPlayers.get(finalI);
                    if (leveledPlayers == null || leveledPlayers.isEmpty()) {
                        return;
                    }
                    leveledPlayers.forEach(player -> {
                        if (Implants.this.healthTickedPlayers.remove(player.getUniqueId())) {
                            if (player.getHealth() + 1.0 < player.getMaxHealth() && player.getHealth() > 0.0 && !player.isDead()) {
                                GeneralUtil.healPlayer(player, 1);
                            }
                        } else {
                            Implants.this.healthTickedPlayers.add(player.getUniqueId());
                        }
                        if (player.getFoodLevel() < 20 && player.getHealth() > 0.0 && !player.isDead()) {
                            player.setFoodLevel(player.getFoodLevel() + 1);
                        }
                    });
                }
            }.runTaskTimer((Plugin) Enchantments.getInstance(), (long) (40 * (3 / i)), (long) (40 * (3 / i)));
            Implants.implantTasks.put(i, task);
        }
    }

    public void applyEquipEffect(final Player player, final int level) {
        player.setMetadata(this.name() + "_enchant", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), (Object) level));
        this.implantedPlayers.put(level, player);
    }

    public void applyUnequipEffect(final Player player, final int level) {
        player.removeMetadata(this.name() + "_enchant", (Plugin) Enchantments.getInstance());
        this.implantedPlayers.remove((Object) level, (Object) player);
        this.healthTickedPlayers.remove(player.getUniqueId());
    }
}
