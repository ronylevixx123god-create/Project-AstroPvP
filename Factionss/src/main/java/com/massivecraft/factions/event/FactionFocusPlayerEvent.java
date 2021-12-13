package com.massivecraft.factions.event;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.Faction;

public class FactionFocusPlayerEvent extends FactionEvent {
    private FPlayer focusedPlayer;

    public FactionFocusPlayerEvent(Faction faction, FPlayer focusedPlayer) {
        super(faction);
        this.focusedPlayer = focusedPlayer;
    }

    public FPlayer getFocusedPlayer() {
        return this.focusedPlayer;
    }
}
