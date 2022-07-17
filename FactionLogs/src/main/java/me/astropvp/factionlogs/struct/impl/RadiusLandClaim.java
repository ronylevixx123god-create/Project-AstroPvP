package me.astropvp.factionlogs.struct.impl;

import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import lombok.Getter;
import me.astropvp.factionlogs.enums.LogType;
import me.astropvp.factionlogs.struct.FactionLog;
import org.bukkit.Bukkit;

import java.util.UUID;

@Getter
public class RadiusLandClaim extends FactionLog {
    private final UUID uuid;
    private final FLocation fLocation;
    private final int radius;

    public RadiusLandClaim(UUID uuid, FLocation fLocation, int radius) {
        super(LogType.RADIUSCLAIM);
        this.uuid = uuid;
        this.fLocation = fLocation;
        this.radius = radius;
    }

    public RadiusLandClaim(UUID uuid, FLocation fLocation,int radius, long time) {
        super(LogType.RADIUSCLAIM);
        this.uuid = uuid;
        this.fLocation = fLocation;
        this.radius = radius;
        super.setTime(time);
    }

    @Override
    public String serialize() {
        return (this.getTime() + ";-;" + uuid + ";-;" + fLocation.toString() + ";-;" + radius);
    }

    public static FactionLog deserialize(String string) {
        String[] splitString = string.split(";-;");
        UUID uuid = UUID.fromString(splitString[1]);
        return new RadiusLandClaim(uuid, FLocation.fromString(splitString[2]), Integer.parseInt(splitString[3]), Long.parseLong(splitString[0]));
    }
}
