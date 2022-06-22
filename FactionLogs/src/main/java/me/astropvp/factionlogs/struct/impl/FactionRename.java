package me.astropvp.factionlogs.struct.impl;

import com.massivecraft.factions.event.FactionRenameEvent;
import com.massivecraft.factions.event.LandClaimEvent;
import com.massivecraft.factions.event.LandUnclaimAllEvent;
import com.massivecraft.factions.event.LandUnclaimEvent;
import lombok.Getter;
import me.astropvp.factionlogs.enums.LogType;
import me.astropvp.factionlogs.struct.FactionLog;

@Getter
public class FactionRename extends FactionLog {
    private final FactionRenameEvent factionRenameEvent;

    public FactionRename(FactionRenameEvent factionRenameEvent) {
        super(LogType.FACTION_RENAME);
        this.factionRenameEvent = factionRenameEvent;

    }
}
