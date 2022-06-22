package me.astropvp.factionlogs.struct.impl;

import com.massivecraft.factions.event.*;
import lombok.Getter;
import me.astropvp.factionlogs.enums.LogType;
import me.astropvp.factionlogs.struct.FactionLog;

@Getter
public class PlayerLeaveKickJoin extends FactionLog {
    private final FPlayerLeaveEvent fPlayerLeaveEvent;
    private final FPlayerJoinEvent fPlayerJoinEvent;

    public PlayerLeaveKickJoin(FPlayerJoinEvent fPlayerJoinEvent, FPlayerLeaveEvent fPlayerLeaveEvent) {
        super(LogType.CLAIM_EDITS);
        this.fPlayerLeaveEvent = fPlayerLeaveEvent;
        this.fPlayerJoinEvent = fPlayerJoinEvent;
    }
    }
