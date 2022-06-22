package me.astropvp.factionlogs.listeners;

import com.massivecraft.factions.event.*;
import me.astropvp.factionlogs.struct.impl.*;
import me.astropvp.factionlogs.utils.FactionsLogsAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class FactionListeners implements Listener {
    @EventHandler
    public void onLandClaim(LandClaimEvent event) {
        if (event.isCancelled()) return;
        FactionsLogsAPI.log(event.getFactionId(), new LandClaim(event));
    }

    @EventHandler
    public void onLandUnClaim(LandUnclaimEvent event) {
        if (event.isCancelled()) return;
        FactionsLogsAPI.log(event.getFactionId(), new LandUnclaim(event));
    }

    @EventHandler
    public void onLandUnClaimAll(LandUnclaimAllEvent event) {
        if (event.isCancelled()) return;
        FactionsLogsAPI.log(event.getFactionId(), new LandUnclaimAll(event));
    }


    @EventHandler
    public void onPlayerJoinFaction(FPlayerJoinEvent event) {
        if (event.isCancelled()) return;
        FactionsLogsAPI.log(event.getFaction().getId(), new PlayerJoin(event));
    }

    @EventHandler
    public void onPlayerLeaveFaction(FPlayerLeaveEvent event) {
        if (event.isCancelled()) return;
        FactionsLogsAPI.log(event.getFaction().getId(), new PlayerLeave(event));
    }

    @EventHandler
    public void onRelationChange(FactionRelationEvent event) {
        FactionsLogsAPI.log(event.getFaction().getId(), new RelationEdits(event));
    }

    @EventHandler
    public void onFactionRename(FactionRenameEvent event) {
        if (event.isCancelled()) return;
        FactionsLogsAPI.log(event.getFaction().getId(), new FactionRename(event));
    }
}
