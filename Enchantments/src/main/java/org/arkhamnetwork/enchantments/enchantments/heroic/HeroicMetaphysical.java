package org.arkhamnetwork.enchantments.enchantments.heroic;

import java.util.*;

import me.astropvp.astropvpskins.utils.DamageIndicatorUtils;
import org.bukkit.*;
import org.arkhamnetwork.enchantments.*;
import org.bukkit.plugin.*;
import org.bukkit.entity.*;
import org.bukkit.metadata.*;
import org.arkhamnetwork.enchantments.listeners.*;
import org.arkhamnetwork.enchantments.events.*;
import com.cosmicpvp.cosmicutils.utils.*;
import org.bukkit.event.*;

public class HeroicMetaphysical extends HeroicCustomEnchantment
{
    private Map<UUID, Integer> equippedMetaphysical;

    public HeroicMetaphysical() {
        super("Polymorphic Metaphysical", "Metaphysical", GeneralUtil.boots, 1);
        this.equippedMetaphysical = new HashMap<UUID, Integer>();
        this.max = 4;
        Bukkit.getPluginManager().registerEvents(new HeroicMetaphysicalListener(this), (Plugin)Enchantments.getInstance());
    }

    public void applyEquipEffect(final Player player, final int enchantLevel) {
        final Integer stored = this.equippedMetaphysical.get(player.getUniqueId());
        if (stored != null && stored >= enchantLevel) {
            return;
        }
        this.equippedMetaphysical.put(player.getUniqueId(), enchantLevel);
        player.setMetadata("metaphysicalEnchant", new FixedMetadataValue((Plugin)Enchantments.getInstance(), enchantLevel));
    }

    public void applyUnequipEffect(final Player player, final int enchantLevel) {
        final Integer stored = this.equippedMetaphysical.get(player.getUniqueId());
        if (stored != null && stored <= enchantLevel) {
            this.equippedMetaphysical.remove(player.getUniqueId());
            player.removeMetadata("metaphysicalEnchant", (Plugin)Enchantments.getInstance());
        }
    }

    public Map<UUID, Integer> getEquippedMetaphysical() {
        return this.equippedMetaphysical;
    }

    class HeroicMetaphysicalListener extends CustomEnchantListener<HeroicMetaphysical>
    {
        public HeroicMetaphysicalListener(final HeroicMetaphysical enchant) {
            super(enchant);
        }

        @EventHandler
        public void onIceAspectProc(final CustomEnchantProcOnEntityEvent event) {
            if (event.getEnchant() == Enchantments.getInstance().getIceAspectEnchant() && event.getTarget() != null) {
                final Integer damagedEquipped = ((HeroicMetaphysical)this.customEnchant).getEquippedMetaphysical().get(event.getTarget().getUniqueId());
                if (damagedEquipped != null) {
                    final double chance = 0.2 * damagedEquipped;
                    if (HeroicMetaphysical.this.random.nextDouble() <= chance) {
                        event.setCancelled(true);
                        if (event.getTarget() instanceof Player && !event.getTarget().hasMetadata("neutralized")) {
                            ((Player)event.getTarget()).sendMessage(CC.LightPurpleB + "** POLYMORPHIC METAPHYSICAL (" + CC.Gray + "Ice Aspect blocked!" + CC.LightPurpleB + ") **");
                            DamageIndicatorUtils.sendDamageIndicator((Player) event.getTarget(), CC.LightPurpleB + "* POLYMORPHIC METAPHYSICAL *", "METAPHYSICAL");

                        }
                    }
                }
            }
        }
    }
}
