package com.massivecraft.factions.event;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.Faction;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class FactionUnFocusEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private Faction fsender;
    private FPlayer ftarget;

    public FactionUnFocusEvent(Faction sender, FPlayer target) {
        this.fsender = sender;
        this.ftarget = target;

    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Faction getFsender() {
        return fsender;
    }

    public FPlayer getFtarget() {
        return ftarget;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public Faction getFaction() {
        return this.fsender;
    }

}

