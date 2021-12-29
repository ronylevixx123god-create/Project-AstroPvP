/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.massivecraft.factions.FPlayer
 *  com.massivecraft.factions.FPlayers
 *  com.massivecraft.factions.Faction
 *  com.massivecraft.factions.cmd.CmdFocus
 *  com.rit.sucy.CustomEnchantment
 *  org.bukkit.Material
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 */
package org.arkhamnetwork.enchantments.enchantments.bow;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.cmd.CmdFocus;
import com.rit.sucy.CustomEnchantment;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class TargetTracking
extends CustomEnchantment {
    public TargetTracking() {
        super("Target Tracking", new Material[]{Material.BOW}, 3);
        this.max = 1;
        this.base = 17.0;
        this.interval = 4.0;
    }

    public void applyEffect(LivingEntity player, LivingEntity playerHit, int level, EntityDamageByEntityEvent e) {
        if (playerHit instanceof Player) {
            FPlayer fPlayer = (FPlayer)FPlayers.i.get((OfflinePlayer)((Player)player));
            Faction myFaction = fPlayer.getFaction();
            FPlayer targetPlayer = (FPlayer)FPlayers.i.get((OfflinePlayer)((Player)playerHit));
            if (myFaction.isNormal()) {
                CmdFocus.focusPlayer((FPlayer)fPlayer, (Faction)myFaction, (FPlayer)targetPlayer, (boolean)false);
            }
        }
    }
}

