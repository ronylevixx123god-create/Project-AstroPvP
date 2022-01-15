/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.net.astropvp.armorsets.utils.CC
 *  com.cosmicpvp.cosmicutils.net.astropvp.armorsets.utils.NumberUtils
 *  org.arkhamnetwork.Arkkit.libs.ParticleEffect
 *  org.bukkit.Location
 *  org.bukkit.Sound
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.entity.EntityDamageEvent
 */
package org.arkhamnetwork.enchantments.enchantments.heroic;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NumberUtils;
import de.inventivegames.particle.ParticleEffect;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

public class HeroicBlock
extends HeroicCustomEnchantment {
    public HeroicBlock() {
        super("Reflective Block", "Block", false);
        this.setNaturalMaterials(GeneralUtil.swords);
        this.max = 3;
        this.base = 17.0;
        this.interval = 4.0;
    }

    public void applyDefenseEffect(LivingEntity damaged, LivingEntity attacker, int level, EntityDamageEvent e) {
        if (damaged instanceof Player) {
            if(attacker != null && attacker.hasMetadata("overwhelm_effect"))
                return;
            double chance;
            Player player = (Player)damaged;
            double random = Math.random();
            if (random < (chance = (player.isBlocking() ? 0.16 : 0.7) * (double)level)) {
                double reduceDamage = 0.25 * (double)level;
                double finalDamage = Math.max(0.0, e.getFinalDamage());
                if (finalDamage <= 0.0) {
                    return;
                }
                double damageToReflect = finalDamage * reduceDamage;
                //e.setDamage(e.getDamage() * (1.0 - reduceDamage));
                double reflectChance = 0.08 * (double)level + (player.isBlocking() ? 0.05 : 0.0);
                if (Math.random() < reflectChance && attacker instanceof Player) {
                    player.sendMessage(CC.GreenB + "* REFLECTIVE BLOCKED [" + NumberUtils.formatSeconds((double)finalDamage) + " DMG] *");
                    try {
                        ParticleEffect.CRIT.sendToPlayers(Bukkit.getOnlinePlayers(), player.getLocation().add(0.0, 1.0, 0.0), 0.75f, 0.75f, 0.75f, 0.1f, 25);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                    if (damageToReflect > 0.0) {
                        Player attacking = (Player)attacker;
                        attacking.damage(damageToReflect);
                        player.getWorld().playSound(player.getLocation(), Sound.ZOMBIE_METAL, 10.0f, 2.0f);
                        try {
                            ParticleEffect.WITCH_MAGIC.sendToPlayers(Bukkit.getOnlinePlayers(), attacking.getLocation().add(0.0, 1.0, 0.0), 0.75f, 0.75f, 0.75f, 0.1f, 25);
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }

                        attacking.sendMessage(CC.RedB + "* " + player.getName() + " REFLECTIVE BLOCK [" + CC.Gray + (int)(reduceDamage * 100.0) + "% DMG" + CC.RedB + "]");
                    }
                }
            }
        }
    }
}

