package me.astropvp.factionlogs.struct.impl;

import lombok.Getter;
import me.astropvp.factionlogs.enums.LogType;
import me.astropvp.factionlogs.struct.FactionLog;
import me.astropvp.factionlogs.utils.LocationSerializer;
import org.bukkit.Location;

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

    public ChunkBust(Location location, UUID uuid, long time) {
        super(LogType.SPAWNER_BREAK);
        this.location = location;
        this.uuid = uuid;
        super.setTime(time);

    }

    @Override
    public String serialize() {
        return (super.getTime() + ";-;" + LocationSerializer.serialize(this.location) + ";-;" + uuid.toString());
    }

    public static FactionLog deserialize(String string) {
        String[] splitString = string.split(";-;");
        return new ChunkBust(LocationSerializer.deserialize(splitString[1]), UUID.fromString(splitString[2]), Long.parseLong(splitString[0]));
    }

    @Override
    public String toString() {
        return "ChunkBust{" +
                "location=" + location +
                ", uuid=" + uuid +
                '}';
    }
}
