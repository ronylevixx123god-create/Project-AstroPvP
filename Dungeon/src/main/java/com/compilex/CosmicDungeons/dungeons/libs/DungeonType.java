/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.struct.factions.points.FactionPointUtils
 *  org.bukkit.ChatColor
 */
package com.compilex.CosmicDungeons.dungeons.libs;

import com.compilex.CosmicDungeons.CosmicDungeonsPlugin;
import com.compilex.CosmicDungeons.dungeons.events.AbandonedShipEvents;
import com.compilex.CosmicDungeons.dungeons.events.DestroyedOutpostEvents;
import com.compilex.CosmicDungeons.dungeons.events.DungeonEvents;
import org.bukkit.ChatColor;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.TimeUnit;

public enum DungeonType {
    ABANDONED_SHIP("dungeon_yijki_a", "Abandoned Ship", null, 0, AbandonedShipEvents.class, 2500000.0, -1.0, ChatColor.DARK_PURPLE, TimeUnit.DAYS.toMillis(7L), null, 5000000L),
    DESTROYED_OUTPOST("dungeon_destroyed_outpost", "Destroyed Outpost", null, 1, DestroyedOutpostEvents.class, 5000000.0, -1.0, ChatColor.DARK_RED, TimeUnit.DAYS.toMillis(14L), null, 25000000L);

    private double calculatedPrice = -1.0;
    private Integer lastDaysPassed = null;
    private long MAX_KEY_COST = 500000000000L;
    private String internalName;
    private String dungeonName;
    private Dungeon dungeonInstance;
    private int dungeonID;
    private Class<? extends DungeonEvents> listenerClass;
    private double portalCost;
    private ChatColor color;
    private long dungeonStartOffset;

    private DungeonType(String internalName, String dungeonName, Dungeon dungeonInstance, int dungeonID, Class<? extends DungeonEvents> listenerClass, double portalCost, double calculatedPrice, ChatColor color, long dungeonStartOffset, Integer lastDaysPassed, long MAX_KEY_COST) {
        this.internalName = internalName;
        this.dungeonName = dungeonName;
        this.dungeonInstance = dungeonInstance;
        this.dungeonID = dungeonID;
        this.listenerClass = listenerClass;
        this.portalCost = portalCost;
        this.calculatedPrice = calculatedPrice;
        this.color = color;
        this.dungeonStartOffset = dungeonStartOffset;
        this.lastDaysPassed = lastDaysPassed;
        this.MAX_KEY_COST = MAX_KEY_COST;
    }

    public String getInternalName() {
        return this.internalName;
    }

    public String getDungeonName() {
        return this.dungeonName;
    }

    public void setDungeonInstance(Dungeon dungeonInstance) {
        this.dungeonInstance = dungeonInstance;
    }

    public Dungeon getDungeonInstance() {
        return this.dungeonInstance;
    }

    public int getDungeonID() {
        return this.dungeonID;
    }

    public Class<? extends DungeonEvents> getListenerClass() {
        return this.listenerClass;
    }

    public double getCalculatedPrice() {
        return this.calculatedPrice;
    }

    public ChatColor getColor() {
        return this.color;
    }

    public long getDungeonStartOffset() {
        return this.dungeonStartOffset;
    }

    public Integer getLastDaysPassed() {
        return this.lastDaysPassed;
    }

    public long getMAX_KEY_COST() {
        return this.MAX_KEY_COST;
    }

    public double getPortalCost() {
       // int daysSinceDungeonWasAvailable = (int)Math.floor(TimeUnit.MILLISECONDS.toDays(FactionPointUtils.getTimeSinceStartOfMapWithOffset((long)this.dungeonStartOffset)));
        int daysSinceDungeonWasAvailable = (int)Math.floor(TimeUnit.MILLISECONDS.toDays(1));
        if (this.lastDaysPassed == null || daysSinceDungeonWasAvailable != this.lastDaysPassed || this.calculatedPrice == -1.0) {
            this.lastDaysPassed = daysSinceDungeonWasAvailable;
            this.calculatePortalCost();
        }
        double cost = this.calculatedPrice;
        if (CosmicDungeonsPlugin.get().isLowCostKeys()) {
            cost *= 0.1;
        }
        return cost;
    }

    public int getDaysSinceDungeonWasAvailable() {
        return (int)Math.floor(TimeUnit.MILLISECONDS.toDays(1));
    }

    public double calculatePortalCost() {
        double newCost = this.portalCost;
        for (int i = 0; i < this.lastDaysPassed && newCost < (double)this.MAX_KEY_COST; newCost *= 1.02, ++i) {
        }
        this.calculatedPrice = Math.min((double)this.MAX_KEY_COST, newCost);
        CosmicDungeonsPlugin.get().getLogger().info("Calculated new Portal Cost for " + this.name() + " from " + this.portalCost + " to -> " + newCost + " days passed: " + this.lastDaysPassed);
        return newCost;
    }

    public DungeonEvents createNewListenerInstance(Dungeon dungeon) {
        try {
            return this.listenerClass.getConstructor(Dungeon.class).newInstance(dungeon);
        }
        catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException ex2) {
            ex2.printStackTrace();
            return null;
        }
    }

    public static DungeonType getFromName(String name) {
        for (DungeonType type : DungeonType.values()) {
            if (!type.name().equals(name)) continue;
            return type;
        }
        return null;
    }
}

