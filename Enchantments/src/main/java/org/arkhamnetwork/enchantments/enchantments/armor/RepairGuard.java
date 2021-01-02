package org.arkhamnetwork.enchantments.enchantments.armor;

import com.cosmicpvp.cosmicutils.nms.MinecraftServerUtils;
import com.rit.sucy.CustomEnchantment;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;

public class RepairGuard extends CustomEnchantment {
    HashMap<String, BukkitTask> playerTasks;

    public RepairGuard() {
        super("Repair Guard", GeneralUtil.armor, 2);
        this.playerTasks = new HashMap<String, BukkitTask>();
        this.max = 3;
        this.base = 10.0;
        this.interval = 5.0;
    }

    public void applyUnequipEffect(final Player player, final int level, final ItemStack item) {
        double duraPercent = 0.15;
        if (level == 2) {
            duraPercent = 0.2;
        }
        if (level == 3) {
            duraPercent = 0.25;
        }
        if (this.getDurabilityPercent(item) <= duraPercent) {
            final int cooldownTicks = 600;
            if (player.hasMetadata("lastRepairGuardProc") && MinecraftServerUtils.getCurrentServerTick() - player.getMetadata("lastRepairGuardProc").get(0).asLong() < cooldownTicks) {
                return;
            }
            player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, (2 + level) * 20, 2 + level), true);
            player.setMetadata("lastRepairGuardProc", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), (Object) MinecraftServerUtils.getCurrentServerTick()));
        }
    }

    private float getDurabilityPercent(final ItemStack i) {
        if (i.getDurability() == 0 || i.getType().getMaxDurability() == 0) {
            return 1.0f;
        }
        return (float) (i.getDurability() / i.getType().getMaxDurability());
    }
}
