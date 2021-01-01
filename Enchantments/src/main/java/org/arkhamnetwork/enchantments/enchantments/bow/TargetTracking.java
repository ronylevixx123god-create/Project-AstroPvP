package org.arkhamnetwork.enchantments.enchantments.bow;

import com.rit.sucy.*;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import com.massivecraft.factions.cmd.*;
import com.massivecraft.factions.*;

public class TargetTracking extends CustomEnchantment
{
    public TargetTracking() {
        super("Target Tracking", new Material[] { Material.BOW }, 3);
        this.max = 1;
        this.base = 17.0;
        this.interval = 4.0;
    }
    
    public void applyEffect(final LivingEntity player, final LivingEntity playerHit, final int level, final EntityDamageByEntityEvent e) {
        if (playerHit instanceof Player) {
            final FPlayer fPlayer = (FPlayer)FPlayers.i.get((OfflinePlayer)player);
            final Faction myFaction = fPlayer.getFaction();
            final FPlayer targetPlayer = (FPlayer)FPlayers.i.get((OfflinePlayer)playerHit);
            if (myFaction.isNormal()) {
                CmdFocus.focusPlayer(fPlayer, myFaction, targetPlayer, false);
            }
        }
    }
}
