package org.arkhamnetwork.enchantments.enchantments.armor;

import com.rit.sucy.*;
import org.arkhamnetwork.enchantments.*;
import org.bukkit.event.*;
import org.bukkit.plugin.*;
import org.bukkit.event.entity.*;
import org.arkhamnetwork.enchantments.util.*;
import org.bukkit.metadata.*;
import com.massivecraft.factions.*;
import com.massivecraft.factions.iface.*;
import com.massivecraft.factions.struct.*;
import org.bukkit.entity.*;
import java.util.*;
import org.bukkit.*;

public class SelfDestruct extends CustomEnchantment
{
    public SelfDestruct() {
        super("Self Destruct", GeneralUtil.armor, 3);
        this.max = 3;
        this.base = 25.0;
        this.interval = 10.0;
        Enchantments.getInstance().getServer().getPluginManager().registerEvents((Listener)new SelfDestructListener(), (Plugin)Enchantments.getInstance());
    }
    
    public void applyDefenseEffect(final LivingEntity player, final LivingEntity attacker, final int level, final EntityDamageEvent e) {
        if (player instanceof Player) {
            final Player p = (Player)player;
            double dmg = e.getDamage(EntityDamageEvent.DamageModifier.BASE);
            dmg *= ArmorUtil.getArmorDamageNullificationPercent((float)ArmorUtil.getArmorValue(p));
            if (player.getHealth() - dmg <= 0.0) {
                if (p.hasMetadata("SelfDestructing") && System.currentTimeMillis() - p.getMetadata("SelfDestructing").get(0).asLong() <= 10000L) {
                    return;
                }
                p.setMetadata("SelfDestructing", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)System.currentTimeMillis()));
                final int radius = (int)(level * 2.5f);
                final FPlayer fp = (FPlayer)FPlayers.i.get((OfflinePlayer)p);
                final List<LivingEntity> victims = new ArrayList<LivingEntity>();
                for (final Entity ent : player.getNearbyEntities((double)radius, (double)radius, (double)radius)) {
                    if (ent instanceof LivingEntity) {
                        if (ent instanceof Player) {
                            final Player pEnt = (Player)ent;
                            final FPlayer fpEnt = (FPlayer)FPlayers.i.get((OfflinePlayer)pEnt);
                            if (fpEnt.getRelationTo((RelationParticipator)fp).isAtLeast(Relation.ALLY)) {
                                continue;
                            }
                        }
                        victims.add((LivingEntity)ent);
                    }
                }
                p.getWorld().playSound(p.getLocation(), Sound.EXPLODE, 2.0f, 0.75f);
                for (final LivingEntity victim : victims) {
                    if (victim instanceof Player) {
                        ((Player)victim).sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!) " + ChatColor.RED + p.getName() + "'s Self-Destruct was activated, RUN!");
                    }
                }
                int tntAmount = radius;
                final int fuseTicks = 120 - level * 20;
                while (tntAmount > 0) {
                    --tntAmount;
                    this.spawnExplosion(p, this.getNearbyLocation(player.getLocation(), 3, 1), fuseTicks);
                }
            }
        }
    }
    
    private void spawnExplosion(final Player exploder, final Location l, final int fuseTicks) {
        if (l.getBlock().getType().isSolid()) {
            return;
        }
        final TNTPrimed tnt = (TNTPrimed)l.getWorld().spawnEntity(l, EntityType.PRIMED_TNT);
        tnt.setFuseTicks(fuseTicks);
        tnt.setMetadata("SelfDestructTNT", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)exploder.getName()));
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
