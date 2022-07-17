package me.astropvp.factionlogs.struct.impl;

import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.event.LandUnclaimAllEvent;
import com.massivecraft.factions.event.LandUnclaimEvent;
import lombok.Getter;
import me.astropvp.factionlogs.enums.LogType;
import me.astropvp.factionlogs.struct.FactionLog;
import me.astropvp.factionlogs.utils.LocationSerializer;
import org.bukkit.Bukkit;

import java.util.UUID;

@Getter
public class LandUnclaimAll extends FactionLog {
    private final UUID uuid;

    public LandUnclaimAll(UUID uuid) {
        super(LogType.UNCLAIMALL);
        this.uuid = uuid;

    }

    public LandUnclaimAll(UUID uuid, long time) {
        super(LogType.UNCLAIMALL);
        this.uuid = uuid;
        super.setTime(time);
    }

    @Override
    public String serialize() {
        return (super.getTime() + ";-;" + uuid);
    }

    public static FactionLog deserialize(String string) {
        String[] splitString = string.split(";-;");
        UUID uuid = UUID.fromString(splitString[1]);
        return new LandUnclaimAll(uuid, Long.parseLong(splitString[0]));
    }

}
