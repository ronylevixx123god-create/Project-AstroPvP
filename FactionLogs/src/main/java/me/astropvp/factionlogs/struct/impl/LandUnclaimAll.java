package me.astropvp.factionlogs.struct.impl;

import com.massivecraft.factions.event.LandUnclaimAllEvent;
import com.massivecraft.factions.event.LandUnclaimEvent;
import lombok.Getter;
import me.astropvp.factionlogs.enums.LogType;
import me.astropvp.factionlogs.struct.FactionLog;

@Getter
public class LandUnclaimAll extends FactionLog {
    private final LandUnclaimAllEvent landUnclaimAllEvent;

    public LandUnclaimAll(LandUnclaimAllEvent landUnclaimAllEvent) {
        super(LogType.UNCLAIMALL);
        this.landUnclaimAllEvent = landUnclaimAllEvent;

    }
}
