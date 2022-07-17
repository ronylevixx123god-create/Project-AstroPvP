package me.astropvp.factionlogs.struct.impl;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.event.FPlayerJoinEvent;
import com.massivecraft.factions.event.FPlayerLeaveEvent;
import lombok.Getter;
import me.astropvp.factionlogs.enums.LogType;
import me.astropvp.factionlogs.struct.FactionLog;
import org.bukkit.Bukkit;

import java.util.UUID;

@Getter
public class PlayerLeave extends FactionLog {
    private final UUID uuid;
    private final FPlayerLeaveEvent.PlayerLeaveReason playerLeaveReason;

    public PlayerLeave(UUID uuid, FPlayerLeaveEvent.PlayerLeaveReason playerLeaveReason) {
        super(LogType.PLAYER_LEAVE);
        this.uuid = uuid;
        this.playerLeaveReason = playerLeaveReason;
    }

    public PlayerLeave(UUID uuid, FPlayerLeaveEvent.PlayerLeaveReason playerLeaveReason, long time) {
        super(LogType.PLAYER_LEAVE);
        this.uuid = uuid;
        this.playerLeaveReason = playerLeaveReason;
        super.setTime(time);
    }

    @Override
    public String serialize() {
        return (this.getTime() + ";-;" + uuid + ";-;" + playerLeaveReason.name());
    }

    public static FactionLog deserialize(String string) {
        String[] splitString = string.split(";-;");
        UUID uuid = UUID.fromString(splitString[1]);
        return new PlayerLeave(uuid, FPlayerLeaveEvent.PlayerLeaveReason.valueOf(splitString[2]), Long.parseLong(splitString[0]));
    }
}
