package me.astropvp.factionlogs.struct.impl;

import lombok.Getter;
import me.astropvp.factionlogs.enums.LogType;
import me.astropvp.factionlogs.struct.FactionLog;
import me.astropvp.factionlogs.utils.LocationSerializer;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

@Getter
public class FactionRename extends FactionLog {
    private final UUID player;
    private final String oldTag;
    private final String newTag;

    public FactionRename(UUID playerUUID, String oldTag, String newTag) {
        super(LogType.FACTION_RENAME);
        this.player = playerUUID;
        this.oldTag = oldTag;
        this.newTag = newTag;
    }

    public FactionRename(UUID playerUUID, String oldTag, String newTag, long time) {
        super(LogType.FACTION_RENAME);
        this.player = playerUUID;
        this.oldTag = oldTag;
        this.newTag = newTag;
        super.setTime(time);
    }

    @Override
    public String serialize() {
        return (this.getTime() + ";-;" + player + ";-;" + oldTag + ";-;" + newTag);
    }

    public static FactionLog deserialize(String string) {
        String[] splitString = string.split(";-;");
        return new FactionRename(UUID.fromString(splitString[1]), splitString[2], splitString[3], Long.parseLong(splitString[0]));
    }
}
