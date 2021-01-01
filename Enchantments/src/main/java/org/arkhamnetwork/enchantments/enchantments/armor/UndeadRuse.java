package org.arkhamnetwork.enchantments.enchantments.armor;

import com.cosmicpvp.cosmicutils.utils.ParticleEffect;
import com.rit.sucy.*;
import org.arkhamnetwork.enchantments.util.*;
import org.arkhamnetwork.enchantments.*;
import org.bukkit.event.*;
import org.bukkit.plugin.*;
import org.bukkit.event.entity.*;
import java.util.*;
import org.arkhamnetwork.Arkkit.libs.*;
import org.bukkit.entity.*;
import org.bukkit.metadata.*;
import org.bukkit.potion.*;
import org.bukkit.*;

public class UndeadRuse extends CustomEnchantment
{
    public UndeadRuse() {
        super("Undead Ruse", GeneralUtil.armor, 2);
        this.max = 10;
        this.base = 20.0;
        this.interval = 10.0;
        Bukkit.getPluginManager().registerEvents((Listener)new UndeadRuseListener(), (Plugin)Enchantments.getInstance());
    }
    
    public void applyDefenseEffect(final LivingEntity player, final LivingEntity attacker, final int level, final EntityDamageEvent e) {
        if (e instanceof EntityDamageByEntityEvent && ((EntityDamageByEntityEvent)e).getDamager() instanceof Player) {
            double chance = 0.01 * level;
            if (chance > 0.04) {
                chance = 0.04;
            }
            if (Math.random() < chance) {
                int zombie_count = (int)(Math.random() / 2.0 * level) + 1;
                final Player p = (Player)player;
                final Location spawnLoc = p.getTargetBlock((HashSet)null, 5).getLocation().add(0.0, 1.0, 0.0);
                ParticleEffect.sendToLocation(ParticleEffect.WITCH_MAGIC, spawnLoc, (float)Math.random(), (float)Math.random(), (float)Math.random(), 0.5f, 20);
                while (zombie_count > 0) {
                    --zombie_count;
                    if (spawnLoc.getChunk().getEntities().length < 50) {
                        final Zombie z = (Zombie)player.getWorld().spawnEntity(spawnLoc, EntityType.ZOMBIE);
                        z.setMetadata("undeadruseSummoner", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)p.getName()));
                        z.setCustomName(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + p.getName() + "'s Undead Minion");
                        z.setCustomNameVisible(true);
                        z.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, (level > 6) ? 2 : ((level > 3) ? 1 : 0)));
                        if (level > 4) {
                            z.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 2));
                        }
                        if (level <= 7) {
                            continue;
                        }
                        z.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 2));
                    }
                }
                if (attacker instanceof Player) {
                    final Player pAttacker = (Player)attacker;
                    pAttacker.hidePlayer(p);
                    ParticleEffect.playEffect(ParticleEffect.WITCH_MAGIC, p.getLocation(), 0.75f, 35);
                    Bukkit.getScheduler().runTaskLater((Plugin)Enchantments.getInstance(), (Runnable)new Runnable() {
                        @Override
                        public void run() {
                            pAttacker.showPlayer(p);
                            ParticleEffect.playEffect(ParticleEffect.WITCH_MAGIC, p.getLocation(), 0.75f, 35);
                        }
                    }, zombie_count * 20L + 20L);
                }
            }
        }
    }
}
