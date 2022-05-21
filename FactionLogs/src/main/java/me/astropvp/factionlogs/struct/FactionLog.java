package me.astropvp.factionlogs.struct;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.astropvp.factionlogs.enums.LogType;

@AllArgsConstructor
@Getter
public abstract class FactionLog {
    private final LogType logtype;
    private final Long time = System.currentTimeMillis();
    //TODO Does this make sense? (I was still tired)
}
