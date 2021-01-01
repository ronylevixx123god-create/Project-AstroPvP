package org.arkhamnetwork.enchantments.enchantments.armor;

import com.cosmicpvp.cosmicutils.utils.ParticleEffect;
import com.rit.sucy.*;
import org.bukkit.event.entity.*;
import org.arkhamnetwork.enchantments.util.*;
import org.arkhamnetwork.enchantments.*;
import org.bukkit.metadata.*;
import org.bukkit.plugin.*;
import org.bukkit.entity.*;
import org.arkhamnetwork.Arkkit.libs.*;
import org.bukkit.*;
import org.bukkit.potion.*;
import java.util.*;

public class Lifebloom extends CustomEnchantment
{
    public Lifebloom() {
        super("Lifebloom", GeneralUtil.leggings, 3);
        this.max = 5;
        this.base = 25.0;
        this.interval = 10.0;
    }
    
    public void applyDefenseEffect(final LivingEntity player, final LivingEntity attacker, final int level, final EntityDamageEvent e) {
        if (player instanceof Player) {
            final Player p = (Player)player;
            double dmg = e.getDamage(EntityDamageEvent.DamageModifier.BASE);
            dmg *= ArmorUtil.getArmorDamageNullificationPercent((float)ArmorUtil.getArmorValue(p));
            if (player.getHealth() - dmg <= 0.0) {
                if (p.hasMetadata("Lifeblooming") && System.currentTimeMillis() - p.getMetadata("Lifeblooming").get(0).asLong() <= 10000L) {
                    return;
                }
                p.setMetadata("Lifeblooming", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)System.currentTimeMillis()));
                final int radius = (int)(level * 2.5f);
                final List<Player> allies = new ArrayList<Player>();
                for (final Entity ent : player.getNearbyEntities((double)radius, (double)radius, (double)radius)) {
                    if (ent instanceof Player) {
                        final Player pEnt = (Player)ent;
                        if (!this.isAlly(p, pEnt)) {
                            continue;
                        }
                        allies.add(pEnt);
                    }
                }
                p.getWorld().playSound(p.getLocation(), Sound.ORB_PICKUP, 2.0f, 0.75f);
                ParticleEffect.playEffect(ParticleEffect.RED_DUST, p.getEyeLocation().add(0.0, 1.0, 0.0), 1.4f, 65);
                for (final Player ally : allies) {
                    ally.sendMessage(ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + "(!) " + ChatColor.GREEN + p.getName() + "'s Lifebloom was activated, fight on!");
                    GeneralUtil.setPlayerHealth((LivingEntity)ally, ally.getMaxHealth());
                    ally.getWorld().playSound(ally.getLocation(), Sound.ORB_PICKUP, 1.5f, 0.75f);
                    if (level >= 4) {
                        ally.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 120, level - 4));
                    }
                    ParticleEffect.playEffect(ParticleEffect.HAPPY_VILLAGER, ally.getLocation().add(0.0, 1.0, 0.0), 2.0f, 16);
                }
            }
        }
    }
    
    private boolean isAlly(final Player p1, final Player p2) {
        return GeneralUtil.isAlly(p1, p2);
    }
}
