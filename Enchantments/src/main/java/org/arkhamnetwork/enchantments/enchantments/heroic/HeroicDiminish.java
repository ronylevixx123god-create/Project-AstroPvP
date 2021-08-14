package org.arkhamnetwork.enchantments.enchantments.heroic;

import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import java.text.DecimalFormat;

public class HeroicDiminish extends CustomEnchantment {
    private DecimalFormat df;

    public HeroicDiminish() {
        super("Vengeful Diminish", GeneralUtil.chestplates, 2);
        this.df = new DecimalFormat("#.##");
        this.max = 6;
        this.base = 15.0;
        this.interval = 3.0;
        Bukkit.getScheduler().runTaskLater((Plugin) Enchantments.getInstance(), (Runnable) new Runnable() {
            @Override
            public void run() {
                HeroicDiminish.this.heroicEnchantment = true;
                HeroicDiminish.this.nonHeroicVersion = EnchantmentAPI.getEnchantment("Diminish");
            }
        }, 2L);
    }

    public void applyDefenseEffect(final LivingEntity player, final LivingEntity attacker, final int level, final EntityDamageEvent e) {

        if (attacker instanceof Player && AstroItemSkinsAPI.hasSkinEquipped((Player) attacker, SkinType.VALENTINES_2021_SWORD)) {
            return;
        }
        if (player.hasMetadata("neutralized"))
            return;
        if (player.hasMetadata("diminishEffect")) {
            double maxDmg = ((MetadataValue)player.getMetadata("diminishEffect").get(0)).asDouble();
            double dmgToDeal = maxDmg;
            if (dmgToDeal > 0.0D && attacker instanceof Player) {
                attacker.damage(dmgToDeal);
                Player attackerPlayer = (Player)attacker;
                Bukkit.getLogger().info("[Heroic Diminish] Damaging attacker " + attackerPlayer.getName() + " for " + dmgToDeal + " due to being then diminishEffect: " + maxDmg + " maxDmg: " + e.getFinalDamage());
            }
            if (e.getFinalDamage() > maxDmg) {
                e.setDamage(maxDmg);
                player.removeMetadata("diminishEffect", (Plugin)Enchantments.getInstance());
                return;
            }
        }
        double random = Math.random();
        double procChance = 0.02D * level;
        if (random < procChance) {
            e.setDamage(e.getDamage() / 2.0D);
            player.setMetadata("diminishEffect", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), e.getFinalDamage() / 2.0D));
            ((Player)player).sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "* DIMINISH [" + ChatColor.YELLOW + "MAX DMG: " + this.df.format(e.getFinalDamage() / 2.0D) + ChatColor.BOLD + "] *");
        }
    }
}
