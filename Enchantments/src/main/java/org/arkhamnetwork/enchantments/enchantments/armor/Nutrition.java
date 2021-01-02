package org.arkhamnetwork.enchantments.enchantments.armor;

import com.rit.sucy.CustomEnchantment;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;

public class Nutrition extends CustomEnchantment {
    public Nutrition() {
        super("Nutrition", GeneralUtil.leggings, 9);
        this.max = 3;
        this.base = 15.0;
        this.interval = 5.0;
        Bukkit.getPluginManager().registerEvents((Listener) new NutritionEvents(), (Plugin) Enchantments.getInstance());
    }

    public void applyEquipEffect(final Player player, final int enchantLevel) {
        NutritionEvents.playerLevels.put(player.getUniqueId().toString(), enchantLevel);
        player.setMetadata("nutritionEnchantment", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), (Object) enchantLevel));
    }

    public void applyUnequipEffect(final Player player, final int enchantLevel) {
        NutritionEvents.playerLevels.remove(player.getUniqueId().toString());
        player.removeMetadata("nutritionEnchantment", (Plugin) Enchantments.getInstance());
    }

    private static class NutritionEvents implements Listener {
        private static HashMap<String, Integer> playerLevels;

        static {
            NutritionEvents.playerLevels = new HashMap<String, Integer>();
        }

        @EventHandler
        public void onPlayerFoodLevelChange(final FoodLevelChangeEvent e) {
            if (e.getEntity() instanceof Player) {
                final Player p = (Player) e.getEntity();
                if (e.getFoodLevel() > p.getFoodLevel() && NutritionEvents.playerLevels.containsKey(p.getUniqueId().toString())) {
                    final int nutritionLevel = NutritionEvents.playerLevels.get(p.getUniqueId().toString());
                    final double modifier = 1.1 + nutritionLevel * 0.3;
                    e.setFoodLevel((int) Math.round(e.getFoodLevel() * modifier));
                }
            }
        }

        @EventHandler
        public void onPlayerQuit(final PlayerQuitEvent e) {
            NutritionEvents.playerLevels.remove(e.getPlayer().getUniqueId().toString());
        }
    }
}
