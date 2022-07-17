package me.astropvp.factionlogs.struct.impl;

import com.massivecraft.factions.Faction;
import com.massivecraft.factions.Factions;
import com.massivecraft.factions.perms.Relation;
import lombok.Getter;
import me.astropvp.factionlogs.enums.LogType;
import me.astropvp.factionlogs.struct.FactionLog;

@Getter
public class RelationEdits extends FactionLog {
    private final Faction sender;
    private final Faction target;
    private final Relation oldRelation;
    private final Relation newRelation;

    public RelationEdits(Faction sender, Faction target, Relation oldRelation, Relation newRelation) {
        super(LogType.RELATION_EDITS);
        this.sender = sender;
        this.target = target;
        this.oldRelation = oldRelation;
        this.newRelation = newRelation;
    }

    public RelationEdits(Faction sender, Faction target, Relation oldRelation, Relation newRelation, long time) {
        super(LogType.RELATION_EDITS);
        this.sender = sender;
        this.target = target;
        this.oldRelation = oldRelation;
        this.newRelation = newRelation;
        super.setTime(time);
    }

    @Override
    public String serialize() {
        return (this.getTime() + ";-;" + sender.getId() + ";-;" + target.getId() + ";-;" + oldRelation.name() + ";-;" + newRelation.name());
    }

    public static FactionLog deserialize(String string) {
        String[] splitString = string.split(";-;");
        Faction sender = Factions.getInstance().getFactionById(splitString[1]);
        Faction target = Factions.getInstance().getFactionById(splitString[2]);
        Relation oldRel = Relation.fromString(splitString[3]);
        Relation newRel = Relation.fromString(splitString[4]);
        return new RelationEdits(sender, target, oldRel, newRel, Long.parseLong(splitString[0]));
    }

    @Override
    public String toString() {
        return "RelationEdits{" +
                "sender=" + sender +
                ", target=" + target +
                ", oldRelation=" + oldRelation +
                ", newRelation=" + newRelation +
                '}';
    }
}
