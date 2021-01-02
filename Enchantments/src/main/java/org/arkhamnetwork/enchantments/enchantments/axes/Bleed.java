package org.arkhamnetwork.enchantments.enchantments.axes;

import com.cosmicpvp.cosmicutils.utils.ParticleEffect;
import com.cosmicpvp.cosmicutils.utils.WorldUtil;
import com.rit.sucy.CustomEnchantment;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Bleed extends CustomEnchantment {
    public Bleed() {
        super("Bleed", GeneralUtil.axe, 2);
        this.max = 6;
        this.base = 10.0;
        this.interval = 8.0;
    }

    public static int getBleedStack(final LivingEntity le) {
        return le.hasMetadata("bleedStack") ? le.getMetadata("bleedStack").get(0).asInt() : 0;
    }

    public static void clearBleedEffect(final LivingEntity le) {
        le.removeMetadata("bleedStack", (Plugin) Enchantments.getInstance());
        if (le instanceof Player) {
            ((Player) le).setWalkSpeed(0.2f);
        } else {
            le.removePotionEffect(PotionEffectType.SLOW);
        }
    }

    public void applyEffect(final LivingEntity user, final LivingEntity target, final int enchantLevel, final EntityDamageByEntityEvent event) {
        if (!event.isCancelled() && event.getDamage(EntityDamageEvent.DamageModifier.BASE) > 0.0 && user instanceof Player) {
            final double bleedChance = 0.08 * enchantLevel + 0.4;
            if (Math.random() < bleedChance && getBleedStack(target) < this.getMaxBleedStack(enchantLevel)) {
                this.addBleedStack(user, target);
            }
        }
    }

    private int getMaxBleedStack(final int level) {
        return 20;
    }

    private void addBleedStack(final LivingEntity leDamager, final LivingEntity le) {
        le.setMetadata("bleedStack", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), (Object) (getBleedStack(le) + 1)));
        final int bleedStack = getBleedStack(le) / 2;
        if (le instanceof Player) {
            final Player p = (Player) le;
            p.setWalkSpeed((float) (0.20000000298023224 - 0.005 * bleedStack));
            for (final Entity ent : p.getNearbyEntities(7.0, 7.0, 7.0)) {
                if (ent.getEntityId() == leDamager.getEntityId()) {
                    continue;
                }
                if (!(ent instanceof Player) || !ent.hasMetadata("bloodLust")) {
                    continue;
                }
                final Player pEnt = (Player) ent;
                if (!GeneralUtil.isAlly(p, pEnt)) {
                    continue;
                }
                final int bloodLustLevel = pEnt.getMetadata("bloodLust").get(0).asInt();
                if (Math.random() >= 0.2 + bloodLustLevel * 0.05) {
                    continue;
                }
                final double amountToHeal = Math.max(2.0, bleedStack * 0.05 * bloodLustLevel);
                GeneralUtil.healPlayer((LivingEntity) pEnt, amountToHeal);
                ParticleEffect.playEffect(ParticleEffect.DRIP_LAVA, pEnt.getLocation(), 0.075f, 10);
                pEnt.playSound(pEnt.getLocation(), Sound.EAT, 0.4f, 0.6f);
            }
        } else {
            le.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, bleedStack * 20, Math.max(1, bleedStack / 3)));
        }
        this.playBleedEffect(le.getLocation().add(0.0, 1.0, 0.0), bleedStack);
    }

    private void playBleedEffect(final Location loc, final int bleedStack) {
        for (int i = Math.max(1, bleedStack / 2); i > 0; --i) {
            WorldUtil.sendWorldEffect(loc.getBlock(), Material.REDSTONE_BLOCK.getId(), 2001);
        }
    }
}
