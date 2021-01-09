/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.FactionUtils
 *  com.gmail.nossr50.events.experience.McMMOPlayerXpGainEvent
 *  com.massivecraft.factions.Faction
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 */
package me.ifamasssxd.cosmichcf.listeners;

import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import com.gmail.nossr50.events.experience.McMMOPlayerXpGainEvent;
import com.massivecraft.factions.Faction;
import me.ifamasssxd.cosmichcf.api.HCFOutpostAPI;
import me.ifamasssxd.cosmichcf.struct.OutpostType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class OutpostMcmmoPerkListener
implements Listener {
    @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
    public void onMcMMOEXP(McMMOPlayerXpGainEvent event) {
        if ((double)event.getRawXpGained() > 0.0) {
            Faction faction = FactionUtils.getFaction((Player)event.getPlayer(), (boolean)false);
            if (faction == null) {
                return;
            }
            if (HCFOutpostAPI.isControllingOutpost(faction, OutpostType.THE_GREAT_HALL)) {
                event.setRawXpGained(event.getRawXpGained() * 1.25f);
            } else if (HCFOutpostAPI.isControllingOutpost(faction, OutpostType.COURTYARD)) {
                event.setRawXpGained(event.getRawXpGained() * 1.75f);
            }
        }
    }
}

