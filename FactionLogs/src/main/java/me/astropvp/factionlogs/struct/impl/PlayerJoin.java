package me.astropvp.factionlogs.struct.impl;

import com.massivecraft.factions.event.FPlayerJoinEvent;
import com.massivecraft.factions.event.FPlayerLeaveEvent;
import lombok.Getter;
import me.astropvp.factionlogs.enums.LogType;
import me.astropvp.factionlogs.struct.FactionLog;

@Getter
public class PlayerJoin extends FactionLog {
    private final FPlayerJoinEvent fPlayerJoinEvent;

    public PlayerJoin(FPlayerJoinEvent fPlayerJoinEvent) {
        super(LogType.PLAYER_JOIN);
        this.fPlayerJoinEvent = fPlayerJoinEvent;

    }
}
