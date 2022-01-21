/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.massivecraft.factions.Board
 *  com.massivecraft.factions.FLocation
 *  com.massivecraft.factions.Faction
 *  org.arkhamnetwork.Arkkit.Arkkit
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.CreatureSpawnEvent
 *  org.bukkit.event.entity.EntityDeathEvent
 *  org.bukkit.event.entity.FoodLevelChangeEvent
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package me.ifamasssxd.cosmichcf.listeners;

import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.Faction;
import me.ifamasssxd.cosmichcf.api.HCFOutpostAPI;
import me.ifamasssxd.cosmichcf.struct.OutpostType;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class OutpostPerkVanillaListener
implements Listener {
    @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
    public void onEntitySpawn(CreatureSpawnEvent event) {
        Faction spawningIn;
        if (event.getEntity().getWorld().getName().equals("world") && (spawningIn = FactionUtils.getFactionAt((FLocation)new FLocation(event.getEntity().getLocation()))) != null && spawningIn.isNormal()) {
            boolean greatHall = HCFOutpostAPI.isControllingOutpost(spawningIn, OutpostType.THE_GREAT_HALL);
            boolean courtyard = HCFOutpostAPI.isControllingOutpost(spawningIn, OutpostType.COURTYARD);
            if ((greatHall || courtyard) && (greatHall || Math.random() <= 0.5)) {
                event.getEntity().setMetadata("monsterAmount", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)(event.getEntity().hasMetadata("monsterAmount") ? ((MetadataValue)event.getEntity().getMetadata("monsterAmount").get(0)).asInt() + 2 : 2)));
            }
        }
    }

    @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
    public void onSaturationLoss(FoodLevelChangeEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        Player player = (Player)event.getEntity();
        if (event.getFoodLevel() < player.getFoodLevel() && Math.random() <= 0.35 && HCFOutpostAPI.isControllingOutpost(player, OutpostType.COURTYARD)) {
            player.setSaturation(player.getSaturation() + 0.5f);
        }
    }

    @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
    public void onEntityDeath(EntityDeathEvent event) {
        Faction in;
        if (event.getEntity().getWorld().getName().equals("world") && (in = FactionUtils.getFactionAt((FLocation)new FLocation(event.getEntity().getLocation()))) != null && in.isNormal()) {
            if (HCFOutpostAPI.isControllingOutpost(in, OutpostType.THE_GREAT_HALL)) {
                event.setDroppedExp((int)((double)event.getDroppedExp() * 1.25));
            } else if (HCFOutpostAPI.isControllingOutpost(in, OutpostType.COURTYARD)) {
                event.setDroppedExp((int)((double)event.getDroppedExp() * 1.75));
            }
        }
    }
}

