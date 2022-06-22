package me.astropvp.factionlogs.struct.impl;

import com.massivecraft.factions.event.LandUnclaimEvent;
import lombok.Getter;
import me.astropvp.factionlogs.enums.LogType;
import me.astropvp.factionlogs.struct.FactionLog;

@Getter
public class LandUnclaim extends FactionLog {
    private final LandUnclaimEvent landUnclaimEvent;

    public LandUnclaim(LandUnclaimEvent landUnclaimEvent) {
        super(LogType.UNCLAIM);
        this.landUnclaimEvent = landUnclaimEvent;
    }
}
