package org.arkhamnetwork.enchantments.enchantments.swords;

import com.bryside.astroattributes.enums.RuneType;
import com.bryside.astroattributes.objects.RuneCacher;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import org.arkhamnetwork.enchantments.Enchantments;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import java.util.Objects;

final class RageListener implements Listener {
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player && (event.getEntity().hasMetadata("pvp_rageHitCombo") || event.getEntity().hasMetadata("pve_rageHitCombo"))) {
            if (RuneCacher.isContained((Player) event.getEntity()) && Objects.requireNonNull(RuneCacher.getRuneCacher((Player) event.getEntity())).getRuneType() == RuneType.KRONOS)
                if (event.getEntity().hasMetadata("pvp_rageHitCombo")) {
                    int newRageNum = (int)(((MetadataValue)event.getEntity().getMetadata("pvp_rageHitCombo").get(0)).asInt() * Objects.requireNonNull(RuneCacher.getRuneCacher((Player) event.getEntity())).getLevel() * 0.05D);
                    event.getEntity().setMetadata("pvp_rageHitCombo", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), newRageNum));
                    event.getEntity().setMetadata("pve_rageHitCombo", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), newRageNum));
                    return;
                }
            }
            event.getEntity().removeMetadata("pvp_rageHitCombo", (Plugin)Enchantments.getInstance());
            event.getEntity().removeMetadata("pve_rageHitCombo", (Plugin)Enchantments.getInstance());
        }
    }

