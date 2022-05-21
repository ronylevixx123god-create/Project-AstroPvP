package me.astropvp.factionlogs.struct;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.astropvp.factionlogs.enums.LogType;

@AllArgsConstructor
@Getter
public abstract class FactionLog {
    private final LogType logtype;
}
