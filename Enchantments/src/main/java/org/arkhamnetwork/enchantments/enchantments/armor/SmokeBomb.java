package org.arkhamnetwork.enchantments.enchantments.armor;

import com.cosmicpvp.cosmicutils.utils.ParticleEffect;
import com.rit.sucy.*;
import org.bukkit.event.entity.*;
import org.arkhamnetwork.enchantments.util.*;
import org.arkhamnetwork.enchantments.*;
import org.bukkit.metadata.*;
import org.bukkit.plugin.*;
import com.massivecraft.factions.*;
import org.bukkit.entity.*;
import com.massivecraft.factions.iface.*;
import com.massivecraft.factions.struct.*;
import org.arkhamnetwork.Arkkit.libs.*;
import org.bukkit.potion.*;
import java.util.*;
import org.bukkit.*;

public class SmokeBomb extends CustomEnchantment
{
    public SmokeBomb() {
        super("Smoke Bomb", GeneralUtil.helmets, 3);
        this.max = 8;
        this.base = 25.0;
        this.interval = 10.0;
    }
    
    public void applyDefenseEffect(final LivingEntity player, final LivingEntity attacker, final int level, final EntityDamageEvent e) {
        if (player instanceof Player) {
            final Player p = (Player)player;
            double dmg = e.getDamage(EntityDamageEvent.DamageModifier.BASE);
            dmg *= ArmorUtil.getArmorDamageNullificationPercent((float)ArmorUtil.getArmorValue(p));
            if (player.getHealth() - dmg <= 0.0) {
                if (p.hasMetadata("SmokeBomb") && System.currentTimeMillis() - p.getMetadata("SmokeBomb").get(0).asLong() <= 15000L) {
                    return;
                }
                p.setMetadata("SmokeBomb", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)System.currentTimeMillis()));
                final int radius = Math.round(level * 1.5f);
                final List<LivingEntity> victims = new ArrayList<LivingEntity>();
                final FPlayer fp = (FPlayer)FPlayers.i.get((OfflinePlayer)p);
                for (final Entity ent : player.getNearbyEntities((double)radius, (double)radius, (double)radius)) {
                    if (ent instanceof LivingEntity) {
                        if (ent instanceof Player) {
                            final FPlayer fpEnt = (FPlayer)FPlayers.i.get((OfflinePlayer)ent);
                            if (fp.getRelationTo((RelationParticipator)fpEnt).isAtLeast(Relation.TRUCE)) {
                                continue;
                            }
                        }
                        victims.add((LivingEntity)ent);
                    }
                }
                ParticleEffect.playEffect(ParticleEffect.CLOUD, p.getLocation().add(0.0, 1.0, 0.0), 1.0f, 75);
                p.getWorld().playSound(p.getLocation(), Sound.FIREWORK_TWINKLE2, 2.0f, 0.75f);
                for (final LivingEntity victim : victims) {
                    if (victim instanceof Player) {
                        ((Player)victim).sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!) " + ChatColor.RED + p.getName() + " has thrown a Smoke Bomb in an attempt to escape!");
                        ((Player)victim).getWorld().playSound(victim.getLocation(), Sound.FIREWORK_TWINKLE2, 1.6f, 0.75f);
                        ((Player)victim).addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Math.max(level, 3) * 20, Math.min(level, 3)));
                        ((Player)victim).addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, Math.max(level, 3) * 20, Math.min(level, 3)));
                    }
                }
                e.setCancelled(true);
                e.setDamage(0.0);
            }
        }
    }
    
    private Location getNearbyLocation(final Location l, final int radius, final int y_boost) {
        final Random rand = new Random();
        Location rand_loc = l.clone();
        rand_loc.add((double)((rand.nextBoolean() ? 1 : -1) * (rand.nextInt(radius) + 1)), 0.0, (double)((rand.nextBoolean() ? 1 : -1) * (rand.nextInt(radius) + 1)));
        rand_loc.add(0.5, (double)y_boost, 0.5);
        while ((rand_loc.getBlock().getType() != Material.AIR || rand_loc.getBlock().getLocation().subtract(0.0, 1.0, 0.0).getBlock().getType() != Material.AIR) && rand_loc.getY() < 255.0) {
            rand_loc = rand_loc.add(0.0, 1.0, 0.0);
        }
        return rand_loc;
    }
}
