/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.gmail.nossr50.datatypes.player.McMMOPlayer
 *  com.gmail.nossr50.util.player.UserManager
 *  com.rit.sucy.CustomEnchantment
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Horse
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.util.Vector
 */
package org.arkhamnetwork.enchantments.enchantments.axes;

import com.rit.sucy.CustomEnchantment;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import java.util.List;

public class Cleave
extends CustomEnchantment {
    boolean mcmmo = false;

    public Cleave() {
        super("Cleave", GeneralUtil.axe, 2);
        this.max = 7;
        this.base = 15.0;
        this.interval = 5.0;
        this.mcmmo = Bukkit.getPluginManager().isPluginEnabled("mcMMO");
        Bukkit.getLogger().info("[Enchantments (Cleave)] mcMMO support = " + this.mcmmo);
    }

    public void applyEffect(LivingEntity player, LivingEntity playerHit, int level, EntityDamageByEntityEvent e) {
        double radius = 0.45 * (double)level;
        double damage = level <= 3 ? 1.0 : (level <= 6 ? 2.0 : (level == 7 ? 3.0 : 0.0));
        if (player.hasMetadata("previous_cleave_use") && System.currentTimeMillis() - ((MetadataValue)player.getMetadata("previous_cleave_use").get(0)).asLong() <= 1500L) {
            return;
        }
        if (player.hasMetadata("skullSplitterProc") && System.currentTimeMillis() - ((MetadataValue)player.getMetadata("skullSplitterProc").get(0)).asLong() <= 1000L) {
            return;
        }
        player.setMetadata("previous_cleave_use", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)System.currentTimeMillis()));
        if (this.mcmmo) {
            Player p = (Player)player;
        }
        if (damage > 0.0 && e.getDamage() > 0.0) {
            List<Entity> aoe = e.getEntity().getNearbyEntities(radius, radius, radius);
            for (Entity aoe_ent : aoe) {
                LivingEntity le_ent;
                if (aoe_ent.equals((Object)player) || aoe_ent.hasMetadata("spectator") || !(aoe_ent instanceof LivingEntity) || aoe_ent instanceof Player && this.isAlly((Player)player, (Player)aoe_ent) || (le_ent = (LivingEntity)aoe_ent).hasMetadata("lastCleaveDamage") && System.currentTimeMillis() - ((MetadataValue)le_ent.getMetadata("lastCleaveDamage").get(0)).asLong() < 1000L) continue;
                le_ent.setMetadata("lastCleaveDamage", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)System.currentTimeMillis()));
                le_ent.damage(damage);
            }
        }
    }

    private boolean isAlly(Player p1, Player p2) {
        return GeneralUtil.isAlly(p1, p2);
    }

    public void pushAwayEntity(Entity pusher, Entity entity, double speed) {
        if (entity instanceof Horse) {
            return;
        }
        Vector unitVector = entity.getLocation().toVector().subtract(pusher.getLocation().toVector()).normalize();
        entity.setVelocity(unitVector.multiply(speed));
    }
}

