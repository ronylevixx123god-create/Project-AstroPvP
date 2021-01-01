package org.arkhamnetwork.enchantments.enchantments.heroic;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class HeroicImplants extends HeroicCustomEnchantment {
    private Set<UUID> implantedPlayers;

    public HeroicImplants() {
        super("Alien Implants", "Implants", GeneralUtil.helmets, 2);
        this.implantedPlayers = new HashSet<>();
        //this.healthTickedPlayers = new HashSet<>();
        //this.heroicImplantPlayers = HashMultimap.create();
        this.max = 3;
        /*Bukkit.getPluginManager().registerEvents(new HeroicImplantsListener(), (Plugin)Enchantments.getInstance());
        int healthIncrease = 2;
        for (int i = 1; i <= this.max; i++) {
            final int finalI = i;
            BukkitTask existing = heroicImplantTasks.get(Integer.valueOf(i));
            if (existing != null)
                existing.cancel();
            BukkitTask task = (new BukkitRunnable() {
                public void run() {
                    Collection<Player> leveledPlayers = HeroicImplants.this.heroicImplantPlayers.get(Integer.valueOf(finalI));
                    if (leveledPlayers == null || leveledPlayers.isEmpty())
                        return;
                    leveledPlayers.forEach(player -> {
                        int val$healthIncrease = 2;
                        if (HeroicImplants.this.healthTickedPlayers.remove(player.getUniqueId())) {
                            if (player.getHealth() + val$healthIncrease < player.getMaxHealth() && player.getHealth() > 0.0D && !player.isDead() && !player.hasMetadata("neutralized"))
                                player.setHealth(player.getHealth() + val$healthIncrease);
                        } else {
                            HeroicImplants.this.healthTickedPlayers.add(player.getUniqueId());
                        }
                        if (player.getFoodLevel() < 20 && player.getHealth() > 0.0D && !player.isDead())
                            player.setFoodLevel(player.getFoodLevel() + 1);
                    });
                }
            }).runTaskTimer((Plugin)Enchantments.getInstance(), (40 * 3 / i), (40 * 3 / i));
            heroicImplantTasks.put(Integer.valueOf(i), task);
        }
    }

    public void applyEquipEffect(Player player, int level) {
        player.setMetadata(name() + "_enchant", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), Integer.valueOf(level)));
        this.heroicImplantPlayers.put(Integer.valueOf(level), player);
        this.implantedPlayers.add(player.getUniqueId());
    }

    public void applyUnequipEffect(Player player, int level) {
        this.implantedPlayers.remove(player.getUniqueId());
        this.heroicImplantPlayers.remove(Integer.valueOf(level), player);
        player.removeMetadata(name() + "_enchant", (Plugin)Enchantments.getInstance());
    }

    private static HashMap<Integer, BukkitTask> heroicImplantTasks = new HashMap<>();

    private Set<UUID> healthTickedPlayers;

    private Multimap<Integer, Player> heroicImplantPlayers;

    class HeroicImplantsListener implements Listener {
        @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
        public void onFoodLoss(FoodLevelChangeEvent event) {
            if (event.getEntity() instanceof Player) {
                Player player = (Player)event.getEntity();
                if (player.getFoodLevel() > event.getFoodLevel() && HeroicImplants.this.implantedPlayers.contains(player.getUniqueId()))
                    event.setCancelled(true);
            }
        }*/
    }
}
