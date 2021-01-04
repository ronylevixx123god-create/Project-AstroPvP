/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.FactionUtils
 *  com.massivecraft.factions.Faction
 *  net.ess3.api.events.PlayerSignSellEvent
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityDeathEvent
 */
package com.massivecraft.factions.listeners;

import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.P;
import com.massivecraft.factions.quests.FactionQuests;
import com.massivecraft.factions.quests.quests.KillBossChallenge;
import com.massivecraft.factions.quests.quests.KillEntityChallenge;
import com.massivecraft.factions.quests.quests.SellItemChallenge;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class QuestListener
implements Listener {
    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=true)
    public void onEntityDeath(EntityDeathEvent event) {
        Player killer = event.getEntity().getKiller();
        if (killer == null) {
            return;
        }
        Faction faction = FactionUtils.getFaction((Player)killer);
        if (faction == null || !faction.isNormal()) {
            return;
        }
        P.getP().getFactionQuestManager().getQuestsDoingByClass(faction, KillEntityChallenge.class, KillBossChallenge.class).forEach(quest -> quest.getQuest().onQuestEvent((FactionQuests)((Object)quest), killer, faction, (Object)event));
    }

    /*@EventHandler
    public void playerSellItemAtSign(PlayerSignSellEvent itemSelling) {
        Player pl = itemSelling.getPlayer();
        Faction fac = FactionUtils.getFaction((Player)pl);
        if (fac == null || !fac.isNormal()) {
            return;
        }
        P.getP().getFactionQuestManager().getQuestsDoingByClass(fac, SellItemChallenge.class).forEach(chal -> chal.getQuest().onQuestEvent((FactionQuests)((Object)chal), pl, fac, (Object)itemSelling));
    }*/
}

