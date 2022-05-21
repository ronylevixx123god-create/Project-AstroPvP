package me.astropvp.factionlogs.struct;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class LogManager {
    private final Map<String, FactionLogManager> logs;

    public LogManager() {
        this.logs = new HashMap<>();
    }

    public void log(String id, FactionLog factionLog) {
        FactionLogManager factionLogManager = getFactionLogManager(id);
        factionLogManager.addFactionLogToFaction(factionLog.getLogtype(), factionLog);
        //TODO: Check if this makes sense (I was tired)
    }

    public FactionLogManager getFactionLogManager(String id) {
        if (!logs.containsKey(id)) {
            this.logs.put(id, new FactionLogManager());
        }
        return logs.get(id);
    }

    public void load() {
        //Load data here
    }
}
