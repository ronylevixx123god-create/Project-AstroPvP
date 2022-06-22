package me.astropvp.factionlogs.struct.impl;

import lombok.Getter;
import me.astropvp.factionlogs.enums.LogType;
import me.astropvp.factionlogs.struct.FactionLog;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import javax.xml.stream.Location;
import java.util.UUID;

@Getter
public class SpawnerPlace extends FactionLog {
    private final int amount;
    private final Location location;
    private final EntityType entityType;
    private final UUID uuid;


    public SpawnerPlace(int amount, Location location, EntityType entityType, UUID uuid) {
        super(LogType.SPAWNER_PLACE);
        this.amount = amount;
        this.location = location;
        this.uuid = uuid;
        this.entityType = entityType;
    }
}
