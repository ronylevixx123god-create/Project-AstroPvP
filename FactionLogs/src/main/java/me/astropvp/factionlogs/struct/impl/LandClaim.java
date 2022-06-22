package me.astropvp.factionlogs.struct.impl;

import com.massivecraft.factions.event.LandClaimEvent;
import com.mysql.jdbc.log.Log;
import lombok.Getter;
import me.astropvp.factionlogs.enums.LogType;
import me.astropvp.factionlogs.struct.FactionLog;

@Getter
public class LandClaim extends FactionLog {
    private final LandClaimEvent landClaimEvent;

    public LandClaim(LandClaimEvent landClaimEvent) {
        super(LogType.CLAIM);
        this.landClaimEvent = landClaimEvent;
    }
}
