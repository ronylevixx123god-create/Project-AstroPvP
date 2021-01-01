package org.arkhamnetwork.enchantments.enchantments.heroic;

import org.arkhamnetwork.enchantments.util.*;
import org.bukkit.*;
import org.bukkit.event.entity.*;
import org.arkhamnetwork.enchantments.*;
import org.bukkit.metadata.*;
import org.bukkit.plugin.*;
import com.gmail.nossr50.util.player.*;
import com.gmail.nossr50.datatypes.player.*;
import java.util.*;
import org.bukkit.entity.*;
import org.bukkit.util.*;
import org.bukkit.util.Vector;

public class HeroicCleave extends HeroicCustomEnchantment
{
    boolean mcmmo;
    
    public HeroicCleave() {
        super("Mighty Cleave", "Cleave", GeneralUtil.axe, 5);
        this.mcmmo = false;
        this.max = 5;
        this.mcmmo = Bukkit.getPluginManager().isPluginEnabled("mcMMO");
        Bukkit.getLogger().info("[Enchantments (Mighty Cleave)] mcMMO support = " + this.mcmmo);
    }
    
    public void applyEffect(final LivingEntity player, final LivingEntity playerHit, final int level, final EntityDamageByEntityEvent e) {
        final double radius = 3.0 + 0.25 * level;
        final double damage = (level <= 2) ? 5.0 : ((level <= 3) ? 6.0 : ((level >= 4) ? 7.0 : 0.0));
        if (player.hasMetadata("previous_cleave_use") && System.currentTimeMillis() - player.getMetadata("previous_cleave_use").get(0).asLong() <= 1500L) {
            return;
        }
        if (player.hasMetadata("skullSplitterProc") && System.currentTimeMillis() - player.getMetadata("skullSplitterProc").get(0).asLong() <= 1000L) {
            return;
        }
        player.setMetadata("previous_cleave_use", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)System.currentTimeMillis()));
        if (this.mcmmo) {
            final Player p = (Player)player;
            try {
                if (UserManager.getPlayer(p) != null) {
                    final McMMOPlayer mcMMOPlayer = UserManager.getPlayer(p);
                    if (mcMMOPlayer.getAxesManager() != null && mcMMOPlayer.getAxesManager().canUseSkullSplitter(playerHit)) {
                        return;
                    }
                }
            }
            catch (IndexOutOfBoundsException err) {
                Bukkit.getLogger().info("[Enchantments (Cleave)] Player " + p.getName() + " has null mcMMO profile? Assuming skullSplitter=true!");
                return;
            }
        }
        if (damage > 0.0 && e.getDamage(EntityDamageEvent.DamageModifier.BASE) > 0.0) {
            final List<Entity> aoe = (List<Entity>)e.getEntity().getNearbyEntities(radius, radius, radius);
            for (final Entity aoe_ent : aoe) {
                if (aoe_ent.equals(player)) {
                    continue;
                }
                if (aoe_ent.hasMetadata("spectator")) {
                    continue;
                }
                if (!(aoe_ent instanceof LivingEntity)) {
                    continue;
                }
                if (aoe_ent instanceof Player && this.isAlly((Player)player, (Player)aoe_ent)) {
                    continue;
                }
                final LivingEntity le_ent = (LivingEntity)aoe_ent;
                if (le_ent.hasMetadata("lastCleaveDamage") && System.currentTimeMillis() - le_ent.getMetadata("lastCleaveDamage").get(0).asLong() < 1000L) {
                    continue;
                }
                le_ent.setMetadata("lastCleaveDamage", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)System.currentTimeMillis()));
                le_ent.damage(damage);
            }
        }
    }
    
    private boolean isAlly(final Player p1, final Player p2) {
        return GeneralUtil.isAlly(p1, p2);
    }
    
    public void pushAwayEntity(final Entity pusher, final Entity entity, final double speed) {
        if (entity instanceof Horse) {
            return;
        }
        final Vector unitVector = entity.getLocation().toVector().subtract(pusher.getLocation().toVector()).normalize();
        entity.setVelocity(unitVector.multiply(speed));
    }
}
