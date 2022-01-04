/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.WorldUtil
 *  com.massivecraft.factions.FPlayer
 *  com.massivecraft.factions.FPlayers
 *  com.massivecraft.factions.iface.RelationParticipator
 *  com.massivecraft.factions.struct.Relation
 *  com.rit.sucy.CustomEnchantment
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 */
package org.arkhamnetwork.enchantments.enchantments.axes;

import com.cosmicpvp.cosmicutils.utils.WorldUtil;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.iface.RelationParticipator;
import com.massivecraft.factions.struct.Relation;
import com.rit.sucy.CustomEnchantment;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Pummel
extends CustomEnchantment {
    public Pummel() {
        super("Pummel", GeneralUtil.axe, 7);
        this.max = 3;
        this.base = 20.0;
        this.interval = 5.0;
    }

    public void applyEffect(LivingEntity player, LivingEntity playerHit, int level, EntityDamageByEntityEvent e) {
        double random = Math.random();
        double chance = 0.06 * (double)level;
        double duration = (double)level * 1.5;
        int radius = level;
        if (random < chance) {
            FPlayer fplayer = (FPlayer)FPlayers.i.get((OfflinePlayer)((Player)player));
            for (Entity ent : playerHit.getNearbyEntities((double)radius, (double)radius, (double)radius)) {
                if (ent.equals((Object)player) || !(ent instanceof LivingEntity)) continue;
                LivingEntity le = (LivingEntity)ent;
                if (le instanceof Player) {
                    int metaphysicalLevel;
                    Player p = (Player)le;
                    if (p.hasMetadata("metaphysicalEnchant") && random > (chance -= (double)(metaphysicalLevel = ((MetadataValue)p.getMetadata("metaphysicalEnchant").get(0)).asInt()) * 0.04)) {
                        p.sendMessage((Object)ChatColor.DARK_GRAY + "" + (Object)ChatColor.BOLD + "** METAPHYSICAL (" + (Object)ChatColor.DARK_GRAY + "Pummel blocked!" + (Object)ChatColor.BOLD + ") **");
                        continue;
                    }
                    FPlayer fp = (FPlayer)FPlayers.i.get((OfflinePlayer)p);
                    if (fplayer.getRelationTo((RelationParticipator)fp).isAtLeast(Relation.TRUCE)) continue;
                }
                le.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, (int)duration * 20, level - 1));
                Location l = playerHit.getLocation().add(0.0, 0.5, 0.0);
                int blockId = playerHit.getLocation().getBlock().getTypeId();
            }
        }
    }
}

