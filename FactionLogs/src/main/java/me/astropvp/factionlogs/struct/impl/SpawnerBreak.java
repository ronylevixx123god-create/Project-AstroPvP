package me.astropvp.factionlogs.struct.impl;

import lombok.Getter;
import me.astropvp.factionlogs.enums.LogType;
import me.astropvp.factionlogs.struct.FactionLog;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import javax.xml.stream.Location;
import java.util.UUID;

@Getter
public class SpawnerBreak extends FactionLog {
    private final int amount;
    private final Location location;
    private final EntityType entityType;
    private final UUID uuid;

    public SpawnerBreak(int amount, Location location, EntityType entityType, UUID uuid) {
        super(LogType.SPAWNER_BREAK);
        this.amount = amount;
        this.location = location;
        this.entityType = entityType;
        this.uuid = uuid;

    }
}
