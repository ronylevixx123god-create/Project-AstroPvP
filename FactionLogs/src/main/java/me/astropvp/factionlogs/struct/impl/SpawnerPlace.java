package me.astropvp.factionlogs.struct.impl;

import lombok.Getter;
import me.astropvp.factionlogs.enums.LogType;
import me.astropvp.factionlogs.struct.FactionLog;

import javax.xml.stream.Location;

@Getter
public class SpawnerPlace extends FactionLog {
    private final int amount;
    private final Location location;

    public SpawnerPlace(int amount, Location location) {
        super(LogType.SPAWNER_PLACE);
        this.amount = amount;
        this.location = location;
    }
}
