package me.astropvp.factionlogs.struct.impl;

import lombok.Getter;
import me.astropvp.factionlogs.enums.LogType;
import me.astropvp.factionlogs.struct.FactionLog;
import org.bukkit.entity.EntityType;

import javax.xml.stream.Location;
import java.util.UUID;

@Getter
public class ChunkBust extends FactionLog {
    private final Location location;
    private final UUID uuid;

    public ChunkBust(Location location, UUID uuid) {
        super(LogType.SPAWNER_BREAK);
        this.location = location;
        this.uuid = uuid;

    }
}
