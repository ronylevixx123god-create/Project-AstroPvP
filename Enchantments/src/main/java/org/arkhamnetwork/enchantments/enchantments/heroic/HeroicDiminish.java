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

    public void applyDefenseEffect(LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {
        if (player.hasMetadata("diminishEffect")) {
            double dmgToDeal;
            double maxDmg = dmgToDeal = ((MetadataValue)player.getMetadata("diminishEffect").get(0)).asDouble();
            if (dmgToDeal > 0.0 && attacker instanceof Player) {
                attacker.damage(Math.min(15.0, dmgToDeal));
                Player attackerPlayer = (Player)attacker;
                Bukkit.getLogger().info("[Heroic Diminish] Damaging attacker " + attackerPlayer.getName() + " for " + dmgToDeal + " due to being then diminishEffect: " + maxDmg + " maxDmg: " + e.getDamage());
            }
            if (e.getDamage() > maxDmg) {
                e.setDamage(maxDmg);
                player.removeMetadata("diminishEffect", (Plugin)Enchantments.getInstance());
                return;
            }
        }
        if (Math.random() < 0.001 * (double)level) {
            e.setDamage(e.getDamage() / 2.0);
            player.setMetadata("diminishEffect", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)(e.getDamage() / 2.0)));
            ((Player)player).sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "* DIMINISH [" + ChatColor.YELLOW + "MAX DMG: " + this.df.format(e.getDamage() / 2.0) + ChatColor.BOLD + "] *");
        }
    }

}
