package org.arkhamnetwork.enchantments.enchantments.axes;

import com.cosmicpvp.cosmicutils.utils.GeneralUtil;
import com.cosmicpvp.cosmicutils.utils.WorldUtil;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.iface.RelationParticipator;
import com.massivecraft.factions.struct.Relation;
import com.rit.sucy.CustomEnchantment;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Pummel extends CustomEnchantment {
    public Pummel() {
        super("Pummel", GeneralUtil.axe, 7);
        this.max = 3;
        this.base = 20.0;
        this.interval = 5.0;
    }

    public void applyEffect(final LivingEntity player, final LivingEntity playerHit, final int level, final EntityDamageByEntityEvent e) {
        final double random = Math.random();
        double chance = 0.06 * level;
        final double duration = level * 1.5;
        final int radius = level;
        if (random < chance) {
            final FPlayer fplayer = (FPlayer) FPlayers.i.get((OfflinePlayer) player);

            for (final Entity ent : playerHit.getNearbyEntities((double) radius, (double) radius, (double) radius)) {
                if (ent.equals(player)) {
                    continue;
                }
                if (!(ent instanceof LivingEntity)) {
                    continue;
                }
                final LivingEntity le = (LivingEntity) ent;
                if (le instanceof Player) {
                    final Player p = (Player) le;

                    if (p.hasMetadata("metaphysicalEnchant")) {
                        final int metaphysicalLevel = p.getMetadata("metaphysicalEnchant").get(0).asInt();
                        chance -= metaphysicalLevel * 0.04;
                        if (random > chance) {
                            p.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "** METAPHYSICAL (" + ChatColor.DARK_GRAY + "Pummel blocked!" + ChatColor.BOLD + ") **");
                            continue;
                        }
                    }
                    final FPlayer fp = (FPlayer) FPlayers.i.get((OfflinePlayer) p);
                    if (fplayer.getRelationTo((RelationParticipator) fp).isAtLeast(Relation.TRUCE)) {
                        continue;
                    }
                }
                le.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, (int) duration * 20, level - 1));
                final Location l = playerHit.getLocation().add(0.0, 0.5, 0.0);
                final int blockId = playerHit.getLocation().getBlock().getTypeId();
            }
        }
    }
}
