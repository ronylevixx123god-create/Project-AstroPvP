package me.astropvp.factionlogs.struct.impl;

import com.massivecraft.factions.event.FPlayerLeaveEvent;
import lombok.Getter;
import me.astropvp.factionlogs.enums.LogType;
import me.astropvp.factionlogs.struct.FactionLog;

@Getter
public class PlayerLeave extends FactionLog {
    private final FPlayerLeaveEvent fPlayerLeaveEvent;

    public PlayerLeave(FPlayerLeaveEvent fPlayerLeaveEvent) {
        super(LogType.PLAYER_LEAVE);
        this.fPlayerLeaveEvent = fPlayerLeaveEvent;
    }
}
