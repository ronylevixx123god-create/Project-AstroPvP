package me.astropvp.factionlogs.struct.impl;

import com.massivecraft.factions.event.LandClaimEvent;
import com.massivecraft.factions.event.LandUnclaimAllEvent;
import com.massivecraft.factions.event.LandUnclaimEvent;
import lombok.Getter;
import me.astropvp.factionlogs.enums.LogType;
import me.astropvp.factionlogs.struct.FactionLog;
import org.bukkit.entity.EntityType;

import javax.xml.stream.Location;

@Getter
public class ClaimEdits extends FactionLog {
    private final LandClaimEvent landClaimEvent;
    private final LandUnclaimEvent landUnclaimEvent;
    private final LandUnclaimAllEvent landUnclaimAllEvent;

    public ClaimEdits(LandClaimEvent landClaimEvent, LandUnclaimEvent landUnclaimEvent, LandUnclaimAllEvent landUnclaimAllEvent) {
        super(LogType.CLAIM_EDITS);
        this.landClaimEvent = landClaimEvent;
        this.landUnclaimEvent = landUnclaimEvent;
        this.landUnclaimAllEvent = landUnclaimAllEvent;

    }
}
