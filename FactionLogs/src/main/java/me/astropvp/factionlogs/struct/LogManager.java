package me.astropvp.factionlogs.struct;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class LogManager {
    private final Map<Long, FactionLogManager> logs;

    public LogManager() {
        this.logs = new HashMap<>();
    }

    public void log(Long id, FactionLog factionLog) {
        if (logs.containsKey(id)) {
            FactionLogManager factionLogManager = getFactionLogManager(id);
        } else {
            this.logs.put(id, new FactionLogManager());
        }
    }

    public FactionLogManager getFactionLogManager(Long id) {
       return logs.get(id);
    }
}
