package org.arkhamnetwork.enchantments.enchantments.heroic;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NumberUtils;
import de.inventivegames.particle.ParticleEffect;
import lombok.SneakyThrows;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

public class HeroicBlock extends HeroicCustomEnchantment {
    public HeroicBlock() {
        super("Reflective Block", "Block", false);
        setNaturalMaterials(GeneralUtil.swords);
        this.max = 3;
        this.base = 17.0D;
        this.interval = 4.0D;
    }

    @SneakyThrows
    public void applyDefenseEffect(LivingEntity damaged, LivingEntity attacker, int level, EntityDamageEvent e) {
        if (damaged instanceof Player) {
            if (damaged.hasMetadata("neutralized"))
                return;
            Player player = (Player) damaged;
            double random = Math.random();
            double chance = (player.isBlocking() ? 0.001D : 0.001D) * level;
            if (random < chance) {
                double reduceDamage = 0.1D * level;
                double finalDamage = Math.max(0.0D, e.getFinalDamage());
                if (finalDamage <= 0.0D)
                    return;
                double damageToReflect = reduceDamage;
                e.setDamage(e.getDamage(EntityDamageEvent.DamageModifier.BASE) * reduceDamage);
                double reflectChance = 0.001D * level + (player.isBlocking() ? 0.001D : 0.001D);
                if (Math.random() < reflectChance && attacker instanceof Player) {
                    player.sendMessage(CC.GreenB + CC.translate("* BLOCKED &7[") + NumberUtils.formatSeconds(finalDamage) + CC.translate("]&a&l *"));
                    de.inventivegames.particle.ParticleEffect.CRIT.sendToPlayer(player,player.getLocation().add(0.0D, 1.0D, 0.0D),0,0,0, 0.75F, 25);
                    if (damageToReflect > 0.0D) {
                        Player attacking = (Player) attacker;
                        attacking.damage(damageToReflect);
                        player.getWorld().playSound(player.getLocation(), Sound.ZOMBIE_METAL, 10.0F, 2.0F);
                        ParticleEffect.WITCH_MAGIC.sendToPlayer(attacking,attacking.getLocation().add(0.0D, 1.0D, 0.0D),0,0,0, 0.75F, 25);
                        attacking.sendMessage(CC.RedB + "* " + player.getName() + " REFLECTIVE BLOCK [" + CC.Gray + (int) (reduceDamage * 100.0D) + "% DMG" + CC.RedB + "]");
                    }
                }
            }
        }
    }
}
