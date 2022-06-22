package me.astropvp.factionlogs.utils;


import com.massivecraft.factions.Faction;
import me.astropvp.factionlogs.FactionLogs;
import me.astropvp.factionlogs.enums.LogType;
import me.astropvp.factionlogs.struct.FactionLog;

import java.util.List;
import java.util.Map;

public class FactionsLogsAPI {
    public static void log(Faction faction, FactionLog factionLog) {
        FactionLogs.getInstance().getLogManager().log(faction.getId(), factionLog);
    }

    public static void log(String id, FactionLog factionLog) {
        FactionLogs.getInstance().getLogManager().log(id, factionLog);
    }

    public static List<FactionLog> getFactionLogs(String id, LogType logType) {
        return FactionLogs.getInstance().getLogManager().getFactionLogManager(id).getFactionLogs().get(logType);
    }

    public static List<FactionLog> getFactionLogs(Faction faction, LogType logType) {
        return FactionLogs.getInstance().getLogManager().getFactionLogManager(faction.getId()).getFactionLogs().get(logType);
    }

    public static Map<LogType, List<FactionLog>> getFactionLogs(Faction faction) {
        return FactionLogs.getInstance().getLogManager().getFactionLogManager(faction.getId()).getFactionLogs();
    }
}
