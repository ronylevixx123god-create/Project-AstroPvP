package org.arkhamnetwork.enchantments.enchantments.armor;

import com.rit.sucy.*;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import net.astropvp.damagehandler.AstroDamageHandler;
import org.arkhamnetwork.enchantments.util.*;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.entity.*;
import org.bukkit.event.entity.*;
import org.bukkit.*;

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
        if (e instanceof EntityDamageByEntityEvent) {
            double chance = 0.025;
            final boolean sneaking = ((Player)player).isSneaking();
            if (sneaking) {
                chance += 0.05;
            }
            if (Math.random() < chance) {
                if (attacker instanceof Player && !AstroItemSkinsAPI.hasSkinEquipped((Player)attacker, SkinType.SPRING_2021_SWORD)) {
                    e.setCancelled(true);
                }
                e.setDamage(0);
                ((Player)player).sendMessage(ChatColor.YELLOW + "*DODGE*");
                player.getWorld().playSound(player.getLocation(), Sound.BAT_TAKEOFF, 1.0f, 0.75f);
            }
        }
    }
}
