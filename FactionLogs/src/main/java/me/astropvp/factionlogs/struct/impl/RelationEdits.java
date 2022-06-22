package me.astropvp.factionlogs.struct.impl;

import com.massivecraft.factions.event.FactionRelationEvent;
import com.massivecraft.factions.event.FactionRelationWishEvent;
import lombok.Getter;
import me.astropvp.factionlogs.enums.LogType;
import me.astropvp.factionlogs.struct.FactionLog;
import org.bukkit.entity.Player;

import javax.xml.stream.Location;
import java.util.UUID;

@Getter
public class RelationEdits extends FactionLog {
    private final FactionRelationEvent factionRelationEvent;
    private final FactionRelationWishEvent factionRelationWishEvent;



    public RelationEdits(FactionRelationEvent factionRelationEvent, FactionRelationWishEvent factionRelationWishEvent) {
        super(LogType.RELATION_EDITS);
        this.factionRelationEvent = factionRelationEvent;
        this.factionRelationWishEvent = factionRelationWishEvent;

    }
}
