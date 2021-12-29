/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.WorldUtil
 *  org.arkhamnetwork.Arkkit.libs.ParticleEffect
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 */
package org.arkhamnetwork.enchantments.enchantments.heroic;

import com.cosmicpvp.cosmicutils.utils.WorldUtil;
import me.ifamasssxd.cosmicmasks.MaskType;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.text.DecimalFormat;

public class HeroicBleed
extends HeroicCustomEnchantment {
    private DecimalFormat df = new DecimalFormat("#.##");

    public HeroicBleed() {
        super("Deep Bleed", "Bleed", GeneralUtil.axe, 2);
        this.max = 6;
    }

    public void applyEffect(LivingEntity user, LivingEntity target, int enchantLevel, EntityDamageByEntityEvent event) {
        if (!event.isCancelled() && event.getDamage() > 0.0 && user instanceof Player) {
            double bleedChance = 0.08 * (double)enchantLevel + 0.4;
            if (Math.random() < bleedChance && HeroicBleed.getBleedStack(target) < this.getMaxBleedStack(enchantLevel)) {
                this.addBleedStack(user, target);
            }
        }
    }

    private int getMaxBleedStack(int level) {
        return 20;
    }

    public static int getBleedStack(LivingEntity le) {
        return le.hasMetadata("bleedStack") ? ((MetadataValue)le.getMetadata("bleedStack").get(0)).asInt() : 0;
    }

    public static void clearBleedEffect(LivingEntity le) {
        le.removeMetadata("bleedStack", (Plugin)Enchantments.getInstance());
        if (le instanceof Player) {
            ((Player)le).setWalkSpeed(0.2f);
        } else {
            le.removePotionEffect(PotionEffectType.SLOW);
        }
    }

    private void addBleedStack(LivingEntity leDamager, LivingEntity le) {
        le.setMetadata("bleedStack", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)(HeroicBleed.getBleedStack(le) + 1)));
        int bleedStack = Math.max(1, HeroicBleed.getBleedStack(le) / 2);
        if (le instanceof Player) {
            Player p = (Player)le;
            if(!MaskType.hasEquipped(p, "HOLY")) {
//                p.setWalkSpeed((float) ((double) 0.2f - 0.0075 * (double) bleedStack));
                if (bleedStack >= 5 && bleedStack % 5 == 0) {
                    p.sendMessage((Object) ChatColor.RED + "** DEEP BLEED [" + (Object) ChatColor.DARK_RED + "-" + this.df.format((double) bleedStack * 0.0075 / (double) 0.2f * 100.0) + (Object) ChatColor.RED + "% Speed] **");
                }
            }
            for (Entity ent : p.getNearbyEntities(7.0, 7.0, 7.0)) {
                Player pEnt;
                if (ent.getEntityId() == leDamager.getEntityId() || !(ent instanceof Player) || !ent.hasMetadata("bloodLust") || !GeneralUtil.isAlly(p, pEnt = (Player)ent)) continue;
                int bloodLustLevel = ((MetadataValue)pEnt.getMetadata("bloodLust").get(0)).asInt();
                if (!(Math.random() < 0.2 + (double)bloodLustLevel * 0.05)) continue;
                double amountToHeal = Math.max(4.0, (double)bleedStack * 0.1 * (double)bloodLustLevel);
                GeneralUtil.healPlayer((LivingEntity)pEnt, amountToHeal);
                try {
                    de.inventivegames.particle.ParticleEffect.DRIP_LAVA.sendToPlayers(Bukkit.getOnlinePlayers(), pEnt.getLocation(), 0.075f, 0.075f, 0.075f, 0.1f, 10);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                pEnt.playSound(pEnt.getLocation(), Sound.EAT, 0.4f, 0.6f);
            }
        } else {
            le.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, bleedStack * 20, Math.max(1, bleedStack / 3)));
        }
        this.playBleedEffect(le.getLocation().add(0.0, 1.0, 0.0), bleedStack);
    }

    private void playBleedEffect(Location loc, int bleedStack) {
        for (int i = Math.max(1, bleedStack / 2); i > 0; --i) {
            WorldUtil.sendWorldEffect((Block)loc.getBlock(), (int)Material.REDSTONE_BLOCK.getId(), (int)2001);
            WorldUtil.sendWorldEffect((Block)loc.getBlock(), (int)Material.BEDROCK.getId(), (int)2001);
        }
    }
}

