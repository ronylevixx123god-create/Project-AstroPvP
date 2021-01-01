package org.arkhamnetwork.enchantments.mastery;

import org.arkhamnetwork.enchantments.util.*;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import org.arkhamnetwork.enchantments.*;
import org.bukkit.metadata.*;
import org.bukkit.plugin.*;

public class MasteryPoltergeist extends MasteryCustomEnchantment
{
    public MasteryPoltergeist() {
        super("Poltergeist", GeneralUtil.armor, 1);
        this.max = 3;
        this.armorSet = "GHOST";
    }
    
    public void applyDefenseEffect(final LivingEntity user, final LivingEntity target, final int enchantLevel, final EntityDamageEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
            event.setCancelled(true);
        }
    }
    
    public static double getChanceToProc(final Player player) {
        if (!player.hasMetadata("poltergeist")) {
            return 0.0;
        }
        return player.getMetadata("poltergeist").get(0).asInt() * 12.5;
    }
    
    public void applyEquipEffect(final Player player, final int enchantLevel) {
        player.setMetadata("poltergeist", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)enchantLevel));
    }
    
    public void applyUnequipEffect(final Player player, final int enchantLevel) {
        player.removeMetadata("poltergeist", (Plugin)Enchantments.getInstance());
    }
}
