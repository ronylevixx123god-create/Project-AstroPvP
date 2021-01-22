package org.arkhamnetwork.enchantments.enchantments.bow;

import com.rit.sucy.CustomEnchantment;
import lombok.SneakyThrows;
import org.arkhamnetwork.Arkkit.libs.ParticleEffect;
import org.arkhamnetwork.enchantments.enchantments.armor.Guardians;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class Hijack extends CustomEnchantment {
    public Hijack() {
        super("Hijack", new Material[]{Material.BOW}, 2);
        this.max = 4;
        this.base = 10.0;
        this.interval = 8.0;
    }

    @SneakyThrows
    public void applyEffect(final LivingEntity user, final LivingEntity target, final int enchantLevel, final EntityDamageByEntityEvent event) {
        if (event.getCause() != EntityDamageEvent.DamageCause.PROJECTILE) {
            return;
        }
        final double random = Math.random();
        final double chance = enchantLevel * 0.08;
        if (random < chance) {
            final Player pUser = (Player) user;
            if (target.getType() == EntityType.IRON_GOLEM && target.hasMetadata("guardianSummoner")) {
                final String oldSummoner = target.getMetadata("guardianSummoner").get(0).asString();
                target.remove();
                final IronGolem ig = Guardians.spawnGuardianEntity(target.getLocation().add(0.0, 2.0, 0.0), pUser, enchantLevel * 2);
                de.inventivegames.particle.ParticleEffect.WITCH_MAGIC.sendToPlayers(Bukkit.getOnlinePlayers(),ig.getLocation(), 0,1,0,1.2f,50);

                for (final Entity ent : ig.getNearbyEntities(24.0, 24.0, 24.0)) {
                    if (ent instanceof Player) {
                        ((Player) ent).sendMessage(ChatColor.DARK_PURPLE.toString() + ChatColor.BOLD.toString() + "*** HIJACK (" + ChatColor.GRAY + ChatColor.STRIKETHROUGH + oldSummoner + ChatColor.DARK_PURPLE + ChatColor.BOLD + " -> " + ChatColor.WHITE + pUser.getName() + ChatColor.DARK_PURPLE + ChatColor.BOLD + ") ***");
                        ((Player) ent).playSound(ent.getLocation(), Sound.IRONGOLEM_DEATH, 0.8f, 1.2f);
                    }
                }
            }
        }
    }
}
