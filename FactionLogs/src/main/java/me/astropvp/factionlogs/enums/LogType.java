package me.astropvp.factionlogs.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.astropvp.factionlogs.struct.FactionLog;
import me.astropvp.factionlogs.struct.impl.SpawnerPlace;

@AllArgsConstructor
@Getter
public enum LogType {

    SPAWNER_PLACE("Spawner Place", new SpawnerPlace());

    private final String displayName;
    private final FactionLog factionLog;

}
