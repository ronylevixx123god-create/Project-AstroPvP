package com.massivecraft.factions.gui.fperm;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FactionsPlugin;
import com.massivecraft.factions.gui.GUI;
import com.massivecraft.factions.gui.SimpleItem;
import com.massivecraft.factions.perms.Permissible;
import com.massivecraft.factions.perms.Role;
import com.massivecraft.factions.util.CC;
import com.massivecraft.factions.util.TL;
import com.massivecraft.factions.util.material.MaterialDb;
import org.bukkit.event.inventory.ClickType;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class PermissibleRelationGUI extends GUI<Permissible> {
    private static final Map<Permissible, SimpleItem> items;
    public static SimpleItem offlineSwitch = SimpleItem.builder().setName(TL.GUI_PERMS_TOGGLE.toString()).setMaterial(MaterialDb.get("LEVER")).build();

    static {
        items = new LinkedHashMap<>();

        SimpleItem recruit = SimpleItem.builder().build();
        recruit.setName(CC.WhiteB + "Recruit");
        recruit.setMaterial(MaterialDb.get("WHITE_STAINED_GLASS_PANE"));
        items.put(Role.RECRUIT, recruit);

        SimpleItem normal = SimpleItem.builder().build();
        normal.setName(CC.Green + "Member");
        normal.setMaterial(MaterialDb.get("GREEN_STAINED_GLASS_PANE"));
        items.put(Role.NORMAL, normal);

        SimpleItem moderator = SimpleItem.builder().build();
        moderator.setName(CC.AquaB + "Moderator");
        moderator.setMaterial(MaterialDb.get("LIGHT_BLUE_STAINED_GLASS_PANE"));
        items.put(Role.MODERATOR, moderator);

        SimpleItem coleader = SimpleItem.builder().build();
        coleader.setName(CC.YellowB + "Co-Leader");
        coleader.setMaterial(MaterialDb.get("YELLOW_STAINED_GLASS_PANE"));
        items.put(Role.COLEADER, coleader);
    }

    private final boolean online;

    public PermissibleRelationGUI(boolean online, FPlayer user) {
        super(user, FactionsPlugin.getInstance().conf().factions().other().isSeparateOfflinePerms() ? 2 : 1);
        this.online = online;
        build();
    }

    @Override
    protected String getName() {
        return "Faction Roles";
    }

    @Override
    protected String parse(String toParse, Permissible permissible) {
        // Uppercase the first letter
        String name = permissible.toString().substring(0, 1).toUpperCase() + permissible.toString().substring(1);

        toParse = toParse.replace("{relation-color}", permissible.getColor().toString());
        toParse = toParse.replace("{relation}", name);
        return toParse;
    }

    @Override
    public void click(int slot, ClickType clickType) {
        if (FactionsPlugin.getInstance().conf().factions().other().isSeparateOfflinePerms() && slot == 13) {
            new PermissibleRelationGUI(!online, user).open();
        } else {
            super.click(slot, clickType);
        }
    }

    @Override
    protected void onClick(Permissible permissible, ClickType clickType) {
        new PermissibleActionGUI(online, user, permissible).open();
    }

    @Override
    protected Map<Integer, Permissible> createSlotMap() {
        Map<Integer, Permissible> map = new HashMap<>();
        if (online) {
            map.put(1, Role.RECRUIT);
            map.put(3, Role.NORMAL);
            map.put(5, Role.MODERATOR);
            map.put(7, Role.COLEADER);
        }
        return map;
    }

    @Override
    protected SimpleItem getItem(Permissible permissible) {
        return items.get(permissible);
    }

    @Override
    protected Map<Integer, SimpleItem> createDummyItems() {
        return FactionsPlugin.getInstance().conf().factions().other().isSeparateOfflinePerms() ? Collections.singletonMap(13, offlineSwitch) : Collections.emptyMap();
    }
}
