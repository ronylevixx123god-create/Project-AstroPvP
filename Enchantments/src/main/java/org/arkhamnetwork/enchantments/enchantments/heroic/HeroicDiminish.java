package org.arkhamnetwork.enchantments.enchantments.heroic;

import java.text.*;

import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import org.arkhamnetwork.enchantments.util.*;
import org.arkhamnetwork.enchantments.*;
import com.rit.sucy.*;
import org.bukkit.plugin.*;
import org.bukkit.event.entity.*;
import org.bukkit.metadata.*;
import org.bukkit.entity.*;
import org.bukkit.*;

public class HeroicDiminish extends CustomEnchantment
{
    private DecimalFormat df;

    public HeroicDiminish() {
        super("Vengeful Diminish", GeneralUtil.chestplates, 2);
        this.df = new DecimalFormat("#.##");
        this.max = 6;
        this.base = 15.0;
        this.interval = 3.0;
        Bukkit.getScheduler().runTaskLater((Plugin)Enchantments.getInstance(), new Runnable() {
            @Override
            public void run() {
                HeroicDiminish.this.heroicEnchantment = true;
                HeroicDiminish.this.nonHeroicVersion = EnchantmentAPI.getEnchantment("Diminish");
            }
        }, 2L);
    }

    public void applyDefenseEffect(final LivingEntity player, final LivingEntity attacker, final int level, final EntityDamageEvent e) {
        if (player.hasMetadata("neutralized")) {
            return;
        }
        if (player.hasMetadata("lastDiminsh") && System.currentTimeMillis() - player.getMetadata("lastDiminsh").get(0).asLong() <= 10000L) {
            return;
        }
        player.setMetadata("lastDiminsh", new FixedMetadataValue((Plugin)Enchantments.getInstance(), System.currentTimeMillis()));
        if (attacker instanceof Player && AstroItemSkinsAPI.hasSkinEquipped((Player)attacker, SkinType.VALENTINES_2021_SWORD)) {
            return;
        }
        if (player.hasMetadata("diminishEffect")) {
            final double maxDmg;
            final double dmgToDeal = maxDmg = player.getMetadata("diminishEffect").get(0).asDouble();
            if (dmgToDeal > 0.0 && attacker instanceof Player) {
                final Player attackerPlayer = (Player)attacker;
                Bukkit.getLogger().info("[Heroic Diminish] Damaging attacker " + attackerPlayer.getName() + " for " + dmgToDeal + " due to being then diminishEffect: " + maxDmg + " maxDmg: " + e.getFinalDamage());
            }
            if (e.getFinalDamage() > maxDmg) {
                player.removeMetadata("diminishEffect", (Plugin)Enchantments.getInstance());
                return;
            }
        }
        final double random = Math.random();
        final double procChance = 0.01 * level;
        if (random < procChance) {
            player.setMetadata("diminishEffect", new FixedMetadataValue((Plugin)Enchantments.getInstance(), e.getFinalDamage() / 2.0));
            ((Player)player).sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "* DIMINISH [" + ChatColor.YELLOW + "MAX DMG: " + this.df.format(e.getFinalDamage() / 2.0) + ChatColor.BOLD + "] *");
        }
    }
}
