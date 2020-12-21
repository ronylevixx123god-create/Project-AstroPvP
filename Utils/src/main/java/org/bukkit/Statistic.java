/*
 * Decompiled with CFR 0.150.
 */
package org.bukkit;

public enum Statistic {
    DAMAGE_DEALT,
    DAMAGE_TAKEN,
    DEATHS,
    MOB_KILLS,
    PLAYER_KILLS,
    FISH_CAUGHT,
    ANIMALS_BRED,
    TREASURE_FISHED,
    JUNK_FISHED,
    LEAVE_GAME,
    JUMP,
    DROP,
    PLAY_ONE_TICK,
    WALK_ONE_CM,
    SWIM_ONE_CM,
    FALL_ONE_CM,
    CLIMB_ONE_CM,
    FLY_ONE_CM,
    DIVE_ONE_CM,
    MINECART_ONE_CM,
    BOAT_ONE_CM,
    PIG_ONE_CM,
    HORSE_ONE_CM,
    MINE_BLOCK(Type.BLOCK),
    USE_ITEM(Type.ITEM),
    BREAK_ITEM(Type.ITEM),
    CRAFT_ITEM(Type.ITEM),
    KILL_ENTITY(Type.ENTITY),
    ENTITY_KILLED_BY(Type.ENTITY);

    private final Type type;

    private Statistic() {
        this(Type.UNTYPED);
    }

    private Statistic(Type type) {
        this.type = type;
    }

    public Type getType() {
        return this.type;
    }

    public boolean isSubstatistic() {
        return this.type != Type.UNTYPED;
    }

    public boolean isBlock() {
        return this.type == Type.BLOCK;
    }

    public static enum Type {
        UNTYPED,
        ITEM,
        BLOCK,
        ENTITY;

    }
}

