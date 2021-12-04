package com.massivecraft.factions.gui.fperm;

import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.gui.GUI;
import com.massivecraft.factions.gui.SimpleItem;
import org.bukkit.event.inventory.ClickType;

import java.util.Map;

public class PermissibleChunkGUI extends GUI<FLocation> {
    public PermissibleChunkGUI(FPlayer user) {
        super(user, 3);
    }

    @Override
    protected String getName() {
        return null;
    }

    @Override
    protected String parse(String toParse, FLocation fLocation) {
        return null;
    }

    @Override
    protected void onClick(FLocation action, ClickType clickType) {

    }

    @Override
    protected Map<Integer, FLocation> createSlotMap() {
        return null;
    }

    @Override
    protected SimpleItem getItem(FLocation fLocation) {
        return null;
    }

    @Override
    protected Map<Integer, SimpleItem> createDummyItems() {
        return null;
    }
}
