package org.arkhamnetwork.enchantments.enchantments.armor;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.rit.sucy.*;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import net.astropvp.damagehandler.AstroDamageHandler;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.*;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.entity.*;
import org.bukkit.event.entity.*;
import org.bukkit.*;
import org.bukkit.metadata.FixedMetadataValue;

public class Dodge extends CustomEnchantment
{
    public Dodge() {
        super("Dodge", GeneralUtil.armor, 2);
        this.max = 5;
        this.base = 20.0;
        this.interval = 5.0;
    }

    public void applyDefenseEffect(final LivingEntity player, final LivingEntity attacker, final int level, final EntityDamageEvent e) {
        if (player.getWorld().getName().toLowerCase().contains("dungeon_planet_nul")) {
            return;
        }
        if (player.hasMetadata("neutralized")) {
            return;
        }
        if (player.hasMetadata("noDefenseProcs")) {
            return;
        }
        if (attacker instanceof Player && AstroItemSkinsAPI.hasSkinEquipped((Player)attacker, SkinType.HALLOWEEN_AXE)) {
            return;
        }

        if (attacker instanceof Player && (AstroItemSkinsAPI.hasSkinEquipped((Player) attacker, SkinType.VALENTINES_2022_HELMET) || AstroItemSkinsAPI.hasSkinEquipped((Player) attacker, SkinType.VALENTINES_2022_MUTATED_HELMET)) && Math.random() < 0.15)
            return;
        if (e instanceof EntityDamageByEntityEvent) {
            double chance = 0.025;
            final boolean sneaking = ((Player) player).isSneaking();
            if (sneaking) {
                chance += 0.05;
            }


            if (Math.random() < chance) {
                if (Math.random() < 0.03 && !player.hasMetadata("carver_damage")) {
                    player.setMetadata("carver_damage", new FixedMetadataValue(Enchantments.getInstance(), true));
                    player.sendMessage(CC.translate("&e&l* CARVER DODGE BUFF [&7+50% DMG&e&l] *"));

                    Bukkit.getScheduler().runTaskLaterAsynchronously(Enchantments.getInstance(), () -> {
                        player.removeMetadata("carver_damage", Enchantments.getInstance());
                    }, 5*20);
                }
                if (attacker instanceof Player && !AstroItemSkinsAPI.hasSkinEquipped((Player) attacker, SkinType.SPRING_2021_SWORD)) {
                    e.setCancelled(true);
                    ((Player) player).sendMessage(ChatColor.YELLOW + "*DODGE*");
                    player.getWorld().playSound(player.getLocation(), Sound.BAT_TAKEOFF, 1.0f, 0.75f);
                }
            }
        }
    }
}
