package me.astropvp.factionlogs.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum LogType {

    SPAWNER_PLACE("Spawner Place"),
    SPAWNER_BREAK("Spawner Break"),
    RELATION_EDITS("Relation Edits"),
    CLAIM_EDITS("Claim Edits");


    private final String displayName;

}
