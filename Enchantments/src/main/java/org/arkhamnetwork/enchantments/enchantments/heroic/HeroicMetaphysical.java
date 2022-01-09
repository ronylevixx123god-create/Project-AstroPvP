/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.enchantments.enchantments.heroic;

import com.cosmicpvp.cosmicutils.utils.CC;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.events.CustomEnchantProcOnEntityEvent;
import org.arkhamnetwork.enchantments.listeners.CustomEnchantListener;
import org.arkhamnetwork.enchantments.util.DamageIndicatorUtils;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HeroicMetaphysical
extends HeroicCustomEnchantment {
    private Map<UUID, Integer> equippedMetaphysical = new HashMap<UUID, Integer>();

    public HeroicMetaphysical() {
        super("Polymorphic Metaphysical", "Metaphysical", GeneralUtil.boots, 1);
        this.max = 4;
        Bukkit.getPluginManager().registerEvents((Listener)new HeroicMetaphysicalListener(this), (Plugin)Enchantments.getInstance());
    }

    public void applyEquipEffect(Player player, int enchantLevel) {
        Integer stored = this.equippedMetaphysical.get(player.getUniqueId());
        if (stored != null && stored >= enchantLevel) {
            return;
        }
        this.equippedMetaphysical.put(player.getUniqueId(), enchantLevel);
        player.setMetadata("metaphysicalEnchant", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)enchantLevel));
    }

    public void applyUnequipEffect(Player player, int enchantLevel) {
        Integer stored = this.equippedMetaphysical.get(player.getUniqueId());
        if (stored != null && stored <= enchantLevel) {
            this.equippedMetaphysical.remove(player.getUniqueId());
            player.removeMetadata("metaphysicalEnchant", (Plugin)Enchantments.getInstance());
        }
    }

    public Map<UUID, Integer> getEquippedMetaphysical() {
        return this.equippedMetaphysical;
    }

    class HeroicMetaphysicalListener
    extends CustomEnchantListener<HeroicMetaphysical> {
        public HeroicMetaphysicalListener(HeroicMetaphysical enchant) {
            super(enchant);
        }

        @EventHandler
        public void onIceAspectProc(CustomEnchantProcOnEntityEvent event) {
            Integer damagedEquipped;
            if (event.getEnchant() == Enchantments.getInstance().getIceAspectEnchant() && event.getTarget() != null && (damagedEquipped = ((HeroicMetaphysical)this.customEnchant).getEquippedMetaphysical().get(event.getTarget().getUniqueId())) != null) {
                    if (event.getTarget() instanceof Player) {
                        ((Player)event.getTarget()).sendMessage(CC.LightPurpleB + "** POLYMORPHIC METAPHYSICAL (" + CC.Gray + "Ice Aspect blocked!" + CC.LightPurpleB + ") **");
                        DamageIndicatorUtils.sendDamageIndicator((Player) event.getTarget(), CC.LightPurpleB + "* POLYMORPHIC METAPHYSICAL *", "METAPHYSICAL");

                    }
            }
        }
    }
}

