package me.astropvp.factionlogs.struct.impl;

import lombok.Getter;
import me.astropvp.factionlogs.enums.LogType;
import me.astropvp.factionlogs.struct.FactionLog;

import javax.xml.stream.Location;
@Getter
public class SpawnerBreak extends FactionLog {
    private final int amount;
    private final Location location;

    public SpawnerBreak(int amount, Location location) {
        super(LogType.SPAWNER_BREAK);
        this.amount = amount;
        this.location = location;
    }
}