package org.arkhamnetwork.enchantments.enchantments.swords;

import com.cosmicpvp.cosmicutils.utils.CC;
import de.inventivegames.particle.ParticleEffect;
import me.astropvp.astroparticles.utils.ParticleUtils;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import me.bryside.weaponrunes.enums.WeaponBonusType;
import me.bryside.weaponrunes.utils.WeaponBonusUtils;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

final class DivineImmolationListener implements Listener {
    @EventHandler
    public void onEntityDamage(final EntityDamageEvent e) throws Exception {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }
        if (AstroItemSkinsAPI.hasSkinEquipped((Player) e.getEntity(), SkinType.LUAU_LEI) && Math.random() < 0.20) {
            e.getEntity().sendMessage(CC.translate("&c&l* LUAU LEI &7Blocked Divine Immolation &c&l*"));
            return;
        }

        if (AstroItemSkinsAPI.hasSkinEquipped((Player) e.getEntity(), SkinType.SOUL_HARVESTER) && Math.random() < 0.4) {
            if (WeaponBonusUtils.getWeaponBonusByItem(((Player) e.getEntity()).getItemInHand()) == WeaponBonusType.REFORGED && Math.random()<0.5) {
                return;
            }
            e.getEntity().sendMessage(CC.translate("&e&l* SOUL HARVESTER &fBlocked &cDivine Immolation &e&l*"));
            return;
        }

        if (e.getEntity().hasMetadata("divineFire") && e.getCause() == EntityDamageEvent.DamageCause.WITHER && e.getEntity() instanceof LivingEntity) {
            final int enchantLevel = e.getEntity().getMetadata("divineFireLevel").get(0).asInt();
            if (System.currentTimeMillis() - e.getEntity().getMetadata("divineFire").get(0).asLong() < (3 + enchantLevel) * 1000) {
                final LivingEntity le = (LivingEntity) e.getEntity();
                if (le instanceof Player) {
                    if (((Player) e.getEntity()).getGameMode() == GameMode.CREATIVE || ((Player) e.getEntity()).getGameMode() == GameMode.SPECTATOR) {
                        return;
                    }
                }

                double damage = 2;

                if (AstroItemSkinsAPI.isInHand((Player) e.getEntity(), SkinType.SOUL_HARVESTER) && !AstroItemSkinsAPI.hasSkinEquipped((Player) le, SkinType.HEART_HALO)) {
                    if (WeaponBonusUtils.getWeaponBonusByItem(((Player) e.getEntity()).getItemInHand()) == WeaponBonusType.REFORGED && Math.random()<0.5) {
                        damage = 2;
                    } else {
                        damage = 3.5;
                    }
                }

                if (AstroItemSkinsAPI.isInHand((Player) e.getEntity(), SkinType.MAUIS_HOOK)) {
                    damage = 3.5;
                }



                le.damage((double) damage);


                if (le instanceof Player) {
                    ((Player) le).sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "** DIVINE IMMOLATION **");
                    ((Player) le).playSound(le.getLocation(), Sound.ZOMBIE_PIG_ANGRY, 0.6f, 0.8f);
                }


            }
        }
    }
}
