package org.arkhamnetwork.enchantments.listeners;

import org.bukkit.event.entity.*;
import org.arkhamnetwork.enchantments.*;
import org.bukkit.plugin.*;
import org.bukkit.metadata.*;
import org.bukkit.event.*;
import org.bukkit.entity.*;

public class VanillaDamageListener implements Listener
{
    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDamage(final EntityDamageEvent e) {
        if ((e.getEntity() instanceof Player && e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) || e.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
            e.getEntity().setMetadata("lastDamageEventRawDamage", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)e.getDamage(EntityDamageEvent.DamageModifier.BASE)));
            e.getEntity().setMetadata("lastDamageEventFinalDamage", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)e.getFinalDamage()));
        }
    }
    
    public static double getLastRawDamage(final Entity e) {
        return e.hasMetadata("lastDamageEventRawDamage") ? e.getMetadata("lastDamageEventRawDamage").get(0).asDouble() : 0.0;
    }
    
    public static double getLastFinalDamage(final Entity e) {
        return e.hasMetadata("lastDamageEventFinalDamage") ? e.getMetadata("lastDamageEventFinalDamage").get(0).asDouble() : 0.0;
    }
}
