package org.arkhamnetwork.enchantments.enchantments.heroic;

import com.cosmicpvp.cosmicutils.utils.ParticleEffect;
import com.cosmicpvp.cosmicutils.utils.PlayerUtil;
import com.cosmicpvp.cosmicutils.utils.WorldUtil;
import lombok.SneakyThrows;
import me.astropvp.astroparticles.utils.ParticleUtils;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.enchantments.armor.Cactus;
import org.arkhamnetwork.enchantments.util.GeneralCooldown;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.ChatColor;
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

import java.text.DecimalFormat;

public class HeroicBleed extends HeroicCustomEnchantment {
    private DecimalFormat df;

    public HeroicBleed() {
        super("Deep Bleed", "Bleed", GeneralUtil.axe, 2);
        this.df = new DecimalFormat("#.##");
        this.max = 6;
    }

    public static int getBleedStack(LivingEntity le) {
        return le.hasMetadata("bleedStack") ? ((MetadataValue) le.getMetadata("bleedStack").get(0)).asInt() : 0;
    }

    public static void clearBleedEffect(LivingEntity le) {
        le.removeMetadata("bleedStack", (Plugin) Enchantments.getInstance());
        if (le instanceof Player) {
            //((Player) le).setWalkSpeed(0.2F);
        } else {
            le.removePotionEffect(PotionEffectType.SLOW);
        }
    }

    @SneakyThrows
    public void applyEffect(LivingEntity user, LivingEntity target, int enchantLevel, EntityDamageByEntityEvent event) {
        if (GeneralCooldown.isOnGeneralCooldown((Player) user)) {
            return;
        }
        if (!event.isCancelled() && event.getDamage(EntityDamageEvent.DamageModifier.BASE) > 0.0D && user instanceof Player) {
            double bleedChance = 0.1D * enchantLevel + 0.4D;
            if (Math.random() < bleedChance && getBleedStack(target) < getMaxBleedStack(enchantLevel))
                GeneralCooldown.enterCooldown((Player) user);
            try {
                addBleedStack(user, target);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    private int getMaxBleedStack(int level) {
        return 20;
    }

    private void addBleedStack(LivingEntity leDamager, LivingEntity le) throws Exception {
        le.setMetadata("bleedStack", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), Integer.valueOf(getBleedStack(le) + 1)));
        int bleedStack = Math.max(1, getBleedStack(le) / 2);
        if (le instanceof Player) {
            Player p = (Player) le;
            //p.setWalkSpeed((float) (0.20000000298023224D - 0.0075D * bleedStack));
            if (bleedStack >= 5 && bleedStack % 5 == 0)
                p.sendMessage(ChatColor.RED + "** DEEP BLEED [" + ChatColor.DARK_RED + "-" + this.df.format(bleedStack * 0.0075 / 0.20000000298023224 * 100.0) + ChatColor.RED + "% Speed] **");
            for (Entity ent : p.getNearbyEntities(7.0D, 7.0D, 7.0D)) {
                if (ent.getEntityId() == leDamager.getEntityId())
                    continue;
                if (!(ent instanceof Player) || !ent.hasMetadata("bloodLust"))
                    continue;
                Player pEnt = (Player) ent;
                if (!GeneralUtil.isAlly(p, pEnt))
                    continue;
                int bloodLustLevel = ((MetadataValue) pEnt.getMetadata("bloodLust").get(0)).asInt();
                if (Math.random() >= 0.2D + bloodLustLevel * 0.05D)
                    continue;
                double amountToHeal = Math.max(4.0D, bleedStack * 0.1D * bloodLustLevel);
                GeneralUtil.healPlayer((LivingEntity) pEnt, amountToHeal);
                playBleedEffect(pEnt.getLocation(), bleedStack);
                ParticleEffect.playEffect(ParticleEffect.DRIP_LAVA, pEnt.getLocation(), 0.075F, 10);
                de.inventivegames.particle.ParticleEffect.DRIP_LAVA.sendToPlayer(pEnt.getPlayer(), pEnt.getLocation(),0,0,0,0.075F, 10);
                pEnt.playSound(pEnt.getLocation(), Sound.EAT, 0.4F, 0.6F);
            }
        } else {
            le.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, bleedStack * 10, Math.max(1, bleedStack / 3)));
        }

        /*if (Math.random() < 0.1) {
            try {
                if (ParticleUtils.isPlayerIndicating(le.getKiller())) {
                    playBleedEffect(le.getLocation().add(0.0D, 1.0D, 0.0D), bleedStack);
                }
            } catch (Exception e) {

            }
        }*/
    }

    private void playBleedEffect(Location loc, int bleedStack) {
        for (int i = Math.max(1, bleedStack / 2); i > 0; i--) {
            WorldUtil.sendWorldEffect(loc.getBlock(), Material.BEDROCK.getId(), 2001);
        }
    }
}
