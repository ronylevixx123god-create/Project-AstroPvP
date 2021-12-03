package com.massivecraft.factions.gui.faccess;

import com.google.common.collect.Lists;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.access.AccessType;
import com.massivecraft.factions.gui.GUI;
import com.massivecraft.factions.gui.SimpleItem;
import com.massivecraft.factions.util.CC;
import com.massivecraft.factions.util.TextUtil;
import com.massivecraft.factions.util.material.MaterialDb;
import org.bukkit.ChatColor;
import org.bukkit.event.inventory.ClickType;
import sun.nio.cs.ext.MacCentralEurope;

import java.util.LinkedHashMap;
import java.util.Map;

public class FAccessMainGUI extends GUI<AccessType> {
    private static final Map<AccessType, SimpleItem> items = new LinkedHashMap<>();

    static {
        SimpleItem player = SimpleItem.builder().build();
        player.setName(CC.AquaB + "View Players");
        player.setLore(Lists.newArrayList(CC.Gray + "Click to view all players", CC.Gray + "that have explicit chunk access."));
        player.setMaterial(MaterialDb.get("SKULL_ITEM"));
        items.put(AccessType.PLAYER, player);

        SimpleItem faction = SimpleItem.builder().build();
        faction.setName(CC.AquaB + "View Factions");
        faction.setLore(Lists.newArrayList(CC.Gray + "Click to view all factions", CC.Gray + "that have explicit chunk access."));
        faction.setMaterial(MaterialDb.get("BEACON"));
        items.put(AccessType.FACTION, faction);

    }
    public FAccessMainGUI(FPlayer user) {
        super(user, 2);
        build();
    }

    @Override
    protected String getName() {
        return "/f access: Select Type";
    }

    @Override
    protected String parse(String toParse, AccessType accessType) {
        return toParse;
    }

    @Override
    protected void onClick(AccessType action, ClickType clickType) {
        switch (action) {
            case PLAYER:
//                new FAccessPlayerGUI().open();
                break;
            case FACTION:
//                new FAccessFactionGUI().open();
                break;
        }
    }

    @Override
    protected Map<Integer, AccessType> createSlotMap() {
        Map<Integer, AccessType> map = new LinkedHashMap<>();
        map.put(12, AccessType.PLAYER);
        map.put(14, AccessType.FACTION);
        return map;
    }

    @Override
    protected SimpleItem getItem(AccessType accessType) {
        return items.get(accessType);
    }

    @Override
    protected Map<Integer, SimpleItem> createDummyItems() {
        Map<Integer, SimpleItem> items = new LinkedHashMap<>();
        SimpleItem.Builder stained_glass_pane = SimpleItem.builder()
                .setData((short) 7)
                .setMaterial(MaterialDb.get("STAINED_GLASS_PANE"))
                .setName(TextUtil.parseColor("&7")
                );
        for(int i = 0; i < this.inventory.getSize(); i++)
            items.put(i, stained_glass_pane.build());
        SimpleItem helpItem = SimpleItem.builder().build();
        helpItem.setMaterial(MaterialDb.get("BOOK"));
        helpItem.setName(CC.AquaB + "/f access Help");
        helpItem.setLore(Lists.newArrayList(ChatColor.GRAY + "You can use this menu to customize", ChatColor.GRAY + "both per-player and per-faction access", ChatColor.GRAY + "to your faction's claims.", "", ChatColor.AQUA + "/f access <p/f> <name> <yes/no/none/all>", ChatColor.GRAY + "Give a player (p) or faction (f) with <name>", ChatColor.GRAY + "access to the current chunk you are in.", "", ChatColor.AQUA + "yes" + ChatColor.GRAY + " = Give Access", ChatColor.AQUA + "no" + ChatColor.GRAY + " = Revoke Access",
                ChatColor.AQUA + "none" + ChatColor.GRAY + " = Revoke Access to ALL chunks", ChatColor.AQUA + "all" + ChatColor.GRAY + " = Give Access to ALL chunks", "", ChatColor.AQUA + "/f access list", ChatColor.GRAY + "List all entities with access to", ChatColor.GRAY + "the current chunk you are in.", "", ChatColor.AQUA + "/f access clear", ChatColor.GRAY + "Revokes access from all entities to", ChatColor.GRAY + "the current chunk you are in."));
        items.put(4, helpItem);
        return items;
    }
}
