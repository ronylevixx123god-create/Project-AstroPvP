package com.massivecraft.factions.gui.faccess;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.gui.GUI;
import com.massivecraft.factions.gui.SimpleItem;
import org.bukkit.event.inventory.ClickType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FAccessChunkListGUI extends GUI<FPlayer> {
    private static final Map<FPlayer, SimpleItem> items = new ConcurrentHashMap<>();

    static {
        SimpleItem item = SimpleItem.builder().build();
    }

    public FAccessChunkListGUI(FPlayer user, int rows) {
        super(user, getRows(user.getFaction()));
    }

    @Override
    protected String getName() {
        return "/f access: Players";
    }

    @Override
    protected String parse(String toParse, FPlayer fPlayer) {
        return toParse;
    }

    @Override
    protected void onClick(FPlayer action, ClickType clickType) {

    }

    @Override
    protected Map<Integer, FPlayer> createSlotMap() {
        return null;
    }

    @Override
    protected SimpleItem getItem(FPlayer fPlayer) {
        return null;
    }

    @Override
    protected Map<Integer, SimpleItem> createDummyItems() {
        return null;
    }

    private static int getRows(Faction faction) {
        int warpCount = faction.getWarps().size();
        if (warpCount == 0) {
            return 1;
        }
        if (warpCount > (5 * 9)) {
            return 6;
        }
        return (int) Math.ceil(((double) warpCount) / 9D);
    }
}
