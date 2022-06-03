package org.arkhamnetwork.enchantments.enchantments.swords;

import com.bryside.astroattributes.DragonTitanAttributesAPI;
import com.bryside.astroattributes.enums.RuneType;
import com.bryside.astroattributes.objects.RuneCacher;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import org.arkhamnetwork.enchantments.Enchantments;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import java.util.Objects;

final class RageListener implements Listener {
    @EventHandler(
            priority = EventPriority.HIGH,
            ignoreCancelled = true
    )
    public void onEntityDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player && (e.getEntity().hasMetadata("pvp_rageHitCombo")) && ((Player) e.getEntity()).getItemInHand() != null) {
            if (DragonTitanAttributesAPI.getRuneType(((Player)e.getEntity()).getItemInHand()) != null) {
                RuneType titanAttribute = DragonTitanAttributesAPI.getRuneType(((Player)e.getEntity()).getItemInHand());
                if (titanAttribute == RuneType.KRONOS && e.getEntity().hasMetadata("pvp_rageHitCombo")) {
                    int newRageNum = (int)((double)((MetadataValue)e.getEntity().getMetadata("pvp_rageHitCombo").get(0)).asInt() * DragonTitanAttributesAPI.getLevelOfRune(((Player) e.getEntity()).getItemInHand()) * 0.05D);
                    e.getEntity().setMetadata("pvp_rageHitCombo", new FixedMetadataValue(Enchantments.getInstance(), newRageNum));
                    e.getEntity().setMetadata("pve_rageHitCombo", new FixedMetadataValue(Enchantments.getInstance(), newRageNum));
                    return;
                }
            }

            e.getEntity().removeMetadata("pvp_rageHitCombo", Enchantments.getInstance());
            e.getEntity().removeMetadata("pve_rageHitCombo", Enchantments.getInstance());
        }

    }
}
