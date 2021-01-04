/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.massivecraft.factions.Faction
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.entity.EntityDeathEvent
 */
package com.massivecraft.factions.quests.quests;

import com.massivecraft.factions.Faction;
import com.massivecraft.factions.quests.DimensionType;
import com.massivecraft.factions.quests.FactionQuests;
import com.massivecraft.factions.quests.QuestUtil;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;

public class KillEntityChallenge
extends IncrementableChallenge {
    private EntityType entityType;
    private String worldRequired;
    private String customDescription;
    private DimensionType dimensionType;

    public KillEntityChallenge(EntityType type, int amount) {
        this(type, amount, null);
    }

    public KillEntityChallenge(EntityType type, String description, int amount) {
        this(type, amount, null);
        this.customDescription = description;
    }

    public KillEntityChallenge(EntityType type, String worldName, String description, int amount) {
        this(type, amount, null);
        this.worldRequired = worldName;
        this.customDescription = description;
    }

    public KillEntityChallenge(EntityType type, int amount, DimensionType dimensionType) {
        super(amount);
        this.entityType = type;
        this.dimensionType = dimensionType;
    }

    @Override
    public void onQuestEvent(FactionQuests parent, Player player, Faction faction, Object object) {
        if (object instanceof EntityDeathEvent) {
            EntityDeathEvent event = (EntityDeathEvent)object;
            if (this.worldRequired != null && !this.worldRequired.equalsIgnoreCase(event.getEntity().getWorld().getName())) {
                return;
            }
            if (!(this.entityType != EntityType.UNKNOWN && event.getEntityType() != this.entityType || this.dimensionType != null && !this.dimensionType.isWorld(event.getEntity().getWorld()))) {
                if (parent == FactionQuests.KILL_CREEPER_I && !event.getEntity().hasMetadata("endMonsterType")) {
                    return;
                }
                LivingEntity ent = event.getEntity();
                if (parent == FactionQuests.KILL_COSMONAUT_OUTPOST_I && !event.getEntity().hasMetadata("boss") && !event.getEntity().hasMetadata("soulDemon")) {
                    return;
                }
                if (parent == FactionQuests.KILL_BLAZE_I && !QuestUtil.isInsideFactionClaim(faction, event.getEntity().getLocation())) {
                    return;
                }
                this.incrementChallenge(parent, player, faction, 1L, object);
            }
        }
    }

    @Override
    public String getDescription() {
        if (this.customDescription != null) {
            return String.format(this.customDescription, this.getCountNeeded());
        }
        return "Kill " + this.getCountNeeded() + "x " + QuestUtil.getEntityTypeName(this.entityType) + (this.dimensionType != null && this.dimensionType != DimensionType.OVERWORLD ? " in " + this.dimensionType.getName() : (this.worldRequired != null && this.worldRequired.contains("_end") ? " in the End" : ""));
    }

    @Override
    public String getDataKey() {
        return "kills";
    }

    public EntityType getEntityType() {
        return this.entityType;
    }
}

