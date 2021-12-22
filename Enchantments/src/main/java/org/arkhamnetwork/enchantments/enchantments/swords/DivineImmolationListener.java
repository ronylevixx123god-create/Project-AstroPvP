package org.arkhamnetwork.enchantments.enchantments.swords;

import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import com.compilex.CosmicArmorSets.enums.ArmorSet;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.rit.sucy.EnchantmentAPI;
import de.inventivegames.particle.ParticleEffect;
import me.astropvp.astroparticles.utils.ParticleUtils;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import me.bryside.weaponrunes.enums.WeaponBonusType;
import me.bryside.weaponrunes.utils.WeaponBonusUtils;
import org.arkhamnetwork.enchantments.Enchantments;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.MetadataValue;

public final class DivineImmolationListener implements Listener {
    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        if (e.getEntity().hasMetadata("divineFire") && e.getCause() == EntityDamageEvent.DamageCause.WITHER && e.getEntity() instanceof LivingEntity) {
            int enchantLevel = ((MetadataValue) e.getEntity().getMetadata("divineFireLevel").get(0)).asInt();
            if (System.currentTimeMillis() - ((MetadataValue) e.getEntity().getMetadata("divineFire").get(0)).asLong() < (long) ((4 + enchantLevel) * 1000L)) {
                e.setCancelled(true);
                LivingEntity le = (LivingEntity) e.getEntity();
                le.damage((double) Math.min(7, 4 + enchantLevel));
                if (le instanceof Player) {
                    String divineColor = ChatColor.RED.toString();
                    if (e.getEntity().hasMetadata("divineColor")) {
                        divineColor = ((MetadataValue) e.getEntity().getMetadata("divineColor").get(0)).asString();
                        e.getEntity().removeMetadata("divineColor", Enchantments.getInstance());
                    }

                    ((Player) le).sendMessage(divineColor + ChatColor.BOLD.toString() + "** DIVINE IMMOLATION **");
                    ((Player) le).playSound(le.getLocation(), Sound.ZOMBIE_PIG_ANGRY, 0.6F, 0.8F);
                }
            }
        }

    }
}