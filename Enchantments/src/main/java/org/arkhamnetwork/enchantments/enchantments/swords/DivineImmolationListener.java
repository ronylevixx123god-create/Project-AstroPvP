package org.arkhamnetwork.enchantments.enchantments.swords;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.rit.sucy.EnchantmentAPI;
import de.inventivegames.particle.ParticleEffect;
import me.astropvp.astroparticles.utils.ParticleUtils;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import me.bryside.weaponrunes.enums.WeaponBonusType;
import me.bryside.weaponrunes.utils.WeaponBonusUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class DivineImmolationListener implements Listener {
    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) throws Exception {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }


        if (e.getEntity().hasMetadata("divineFire") && e.getCause() == EntityDamageEvent.DamageCause.WITHER) {


            if (AstroItemSkinsAPI.hasSkinEquipped((Player) e.getEntity(), SkinType.SUMMER_2021_AMULET) && Math.random() < 0.20) {
                e.getEntity().sendMessage(CC.translate("&c&l* LUAU LEI &7Blocked Divine Immolation &c&l*"));
                return;
            }

            if (AstroItemSkinsAPI.hasSkinEquipped((Player) e.getEntity(), SkinType.THANKSGIVING_SWORD_HARVESTER) && Math.random() < 0.5) {
                e.getEntity().sendMessage(CC.translate("&e&l* SOUL HARVESTER &fBlocked &cDivine Immolation &e&l*"));
                return;
            }
            final int enchantLevel = e.getEntity().getMetadata("divineFireLevel").get(0).asInt();
            if (System.currentTimeMillis() - e.getEntity().getMetadata("divineFire").get(0).asLong() < (3 + enchantLevel) * 1000) {
                final LivingEntity le = (LivingEntity) e.getEntity();
                if (le instanceof Player) {
                    if (((Player) e.getEntity()).getGameMode() == GameMode.CREATIVE || ((Player) e.getEntity()).getGameMode() == GameMode.SPECTATOR) {
                        return;
                    }
                }

                double damage = 3;


                if (!(le.getKiller() instanceof Player)) return;
                if (AstroItemSkinsAPI.hasSkinEquipped((Player) e.getEntity(), SkinType.THANKSGIVING_SWORD_HARVESTER) && !AstroItemSkinsAPI.hasSkinEquipped((Player) le.getKiller(), SkinType.VALENTINES_2021_HELMET)) {
                    if (WeaponBonusUtils.hasWeaponBonus(((Player) e.getEntity()).getItemInHand()) && WeaponBonusUtils.getWeaponBonusByItem(((Player) e.getEntity()).getItemInHand()) == WeaponBonusType.REFORGED && Math.random()<0.5) {
                        damage = 5;
                    } else {
                        damage = 4;
                    }
                }

                if (AstroItemSkinsAPI.hasSkinEquipped((Player) ((Player) e.getEntity()).getKiller(), SkinType.SUMMER_2021_SWORD)) {
                    damage = 4.5;
                }

                if (EnchantmentAPI.getEnchantments(((Player) e.getEntity()).getKiller().getEquipment().getItemInHand()).containsKey(EnchantmentAPI.getEnchantment("Soul Master"))) {
                    double solitudeLevel = EnchantmentAPI.getEnchantments(((Player) e.getEntity()).getKiller().getEquipment().getItemInHand()).get(EnchantmentAPI.getEnchantment("Soul Master"));
                    damage = 1.25 * (((2 * solitudeLevel)/100) + 1);
                }


                if (WeaponBonusUtils.hasWeaponBonus(((Player) e.getEntity()).getKiller().getItemInHand())) {
                    if (WeaponBonusUtils.getWeaponBonusByItem(((Player) ((Player) e.getEntity()).getKiller()).getItemInHand())==(WeaponBonusType.DEMON)) {
                        damage *= 1.35;
                    }
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
