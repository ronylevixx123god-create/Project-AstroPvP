/*
 * Decompiled with CFR 0.150.
 */
package com.massivecraft.factions.quests;

public enum QuestType {
    KILL("Kill %s"),
    INCREMENTABLE("");

    private String message;

    private QuestType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}

