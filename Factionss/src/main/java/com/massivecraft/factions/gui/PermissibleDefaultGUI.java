package com.massivecraft.factions.gui;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.Faction;
import org.bukkit.event.inventory.ClickType;

import java.util.Map;

public class PermissibleDefaultGUI extends GUI<Faction> {
    public PermissibleDefaultGUI(FPlayer user) {
        super(user, rows);
    }

    @Override
    protected String getName() {
        return null;
    }

    @Override
    protected String parse(String toParse, Faction faction) {
        return null;
    }

    @Override
    protected void onClick(Faction action, ClickType clickType) {

    }

    @Override
    protected Map<Integer, Faction> createSlotMap() {
        return null;
    }

    @Override
    protected SimpleItem getItem(Faction faction) {
        return null;
    }

    @Override
    protected Map<Integer, SimpleItem> createDummyItems() {
        return null;
    }
}
