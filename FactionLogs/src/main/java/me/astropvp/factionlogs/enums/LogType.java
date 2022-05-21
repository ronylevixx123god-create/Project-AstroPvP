package me.astropvp.factionlogs.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.astropvp.factionlogs.struct.FactionLog;
import me.astropvp.factionlogs.struct.impl.ClaimEdits;
import me.astropvp.factionlogs.struct.impl.RelationEdits;
import me.astropvp.factionlogs.struct.impl.SpawnerBreak;
import me.astropvp.factionlogs.struct.impl.SpawnerPlace;

@AllArgsConstructor
@Getter
public enum LogType {

    SPAWNER_PLACE("Spawner Place", new SpawnerPlace()),
    SPAWNER_BREAK("Spawner Break", new SpawnerBreak()),
    RELATION_EDITS("Relation Edits", new RelationEdits()),
    CLAIM_EDITS("Claim Edits", new ClaimEdits());


    private final String displayName;
    private final FactionLog factionLog;

}
