package org.arkhamnetwork.enchantments.enchantments.heroic;

import com.cosmicpvp.cosmicutils.utils.WorldUtil;
import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class HeroicSniper extends CustomEnchantment {
    public HeroicSniper() {
        super("Lethal Sniper", new Material[]{Material.BOW}, 2);
        this.max = 5;
        this.base = 10.0;
        this.interval = 8.0;
        Bukkit.getScheduler().runTaskLater((Plugin) Enchantments.getInstance(), (Runnable) new Runnable() {
            @Override
            public void run() {
                HeroicSniper.this.heroicEnchantment = true;
                HeroicSniper.this.nonHeroicVersion = EnchantmentAPI.getEnchantment("Sniper");
            }
        }, 2L);
    }

    public void applyProjectileEffect(final LivingEntity user, final int enchantLevel, final ProjectileLaunchEvent event) {
        event.getEntity().setMetadata("lethalSniper", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), (Object) enchantLevel));
    }

    public void applyEffect(final LivingEntity user, final LivingEntity target, final int enchantLevel, final EntityDamageByEntityEvent event) {
        if (event.getCause() != EntityDamageEvent.DamageCause.PROJECTILE) {
            return;
        }
        final Projectile proj = (Projectile) event.getDamager();
        if (!(proj.getShooter() instanceof Player)) {
            return;
        }

        if (((Player) target).getPlayer().getInventory().getChestplate() != null && AstroItemSkinsAPI.hasSkinEquipped((Player) target, SkinType.VALENTINES_2021_BACKPACK)) {
            return;
        }

        final Entity victima = (Entity) target;
        final double y = proj.getLocation().getY();
        final double victimay = victima.getLocation().getY();
        final double level = enchantLevel;
        final boolean headshot = y - victimay > 1.9;
        if (headshot && Math.random() < 0.625 + level * 0.075) {
            if (GeneralUtil.isEffectedByRage(target)) {
                return;
            }
            final double multiplier = Math.min(4.0, level * 0.2 + 3.0);
            event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * multiplier);
            this.playBleedEffect(target.getEyeLocation(), enchantLevel);
            if (target instanceof Player) {
                ((Player) target).sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "*** HEADSHOT [+" + multiplier + "x DMG] ***");
                ((Player) target).playSound(target.getLocation(), Sound.HURT_FLESH, 2.0f, 0.3f);
            }
        }
    }

    private void playBleedEffect(final Location loc, final int bleedStack) {
        for (int i = Math.max(1, bleedStack / 2); i > 0; --i) {
            WorldUtil.sendWorldEffect(loc.getBlock(), Material.REDSTONE_BLOCK.getId(), 2001);
        }
    }
}
