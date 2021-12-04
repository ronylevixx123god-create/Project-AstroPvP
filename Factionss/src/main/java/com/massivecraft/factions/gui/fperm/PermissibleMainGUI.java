package com.massivecraft.factions.gui.fperm;

import com.google.common.collect.Lists;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.gui.GUI;
import com.massivecraft.factions.gui.SimpleItem;
import com.massivecraft.factions.perms.PermMenuType;
import com.massivecraft.factions.util.CC;
import com.massivecraft.factions.util.material.MaterialDb;
import org.bukkit.ChatColor;
import org.bukkit.event.inventory.ClickType;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class PermissibleMainGUI extends GUI<PermMenuType> {
    private static final Map<PermMenuType, SimpleItem> items;
    private static final SimpleItem helpItem;


    static {
        items = new LinkedHashMap<>();

        SimpleItem chunk = SimpleItem.builder().build();
        chunk.setName(CC.AquaB + "Current Chunk Permissions");
        chunk.setLore(Lists.newArrayList(CC.Gray + "Click to view all permissions", CC.Gray + "for players and factions."));
        chunk.setMaterial(MaterialDb.get("GRASS_BLOCK"));
        items.put(PermMenuType.CHUNK, chunk);

        SimpleItem def = SimpleItem.builder().build();
        def.setName(CC.AquaB + "Default Permissions");
        def.setMaterial(MaterialDb.get("BEACON"));
        def.setLore(Lists.newArrayList(CC.Gray + "Click to view all default permissions", CC.Gray + "for players and factions."));
        items.put(PermMenuType.DEFAULT, def);

        SimpleItem role = SimpleItem.builder().build();
        role.setName(CC.AquaB + "/f roles");
        role.setMaterial(MaterialDb.get("WRITABLE_BOOK"));
        role.setLore(Lists.newArrayList(CC.Gray + "Click to view your faction role permissions.", CC.Gray + "Role permissions allow you to permit different", CC.Gray + "ranking faction members to perform specific tasks."));
        items.put(PermMenuType.ROLES, role);

        SimpleItem help = SimpleItem.builder().build();
        def.setName(CC.AquaB + "/f perm Help");
        def.setMaterial(MaterialDb.get("BOOK"));
        def.setLore(Lists.newArrayList(ChatColor.GRAY + "You can use this menu to customize", ChatColor.GRAY + "what specific types of blocks your", ChatColor.GRAY + "/f access'd factions/players can modify", ChatColor.GRAY + "in your current chunk, and by default.", "", ChatColor.AQUA + "/f access is REQUIRED for /f perm", ChatColor.GRAY + "Players and Factions will not show up", ChatColor.GRAY + "in this menu until they have /f access", ChatColor.GRAY + "to atleast one of your faction's chunks.", "",
                ChatColor.AQUA + "Current Chunk Permissions", ChatColor.GRAY + "Edits the permissions of the specified", ChatColor.GRAY + "player or faction in the current chunk", ChatColor.GRAY + "you are standing in. These permissions", ChatColor.GRAY + ChatColor.UNDERLINE.toString() + "OVERRULE" + ChatColor.GRAY + " any Default Permissions.", "", ChatColor.AQUA + "Default Permissions", ChatColor.GRAY + "Edits the permissions for the", ChatColor.GRAY + "specified player or faction. These settings", ChatColor.GRAY + "are what the given entity will be able to",
                ChatColor.GRAY + "modify in chunks where no \"Current Chunk\""));
        helpItem = help;
    }

    public PermissibleMainGUI(FPlayer user) {
        super(user, 3);
        build();
    }

    @Override
    protected String getName() {
        return "Default Permissions";
    }

    @Override
    protected String parse(String toParse, PermMenuType permMenuType) {
        return toParse;
    }

    @Override
    protected void onClick(PermMenuType action, ClickType clickType) {
        switch (action) {
            case CHUNK:
                new PermissibleChunkGUI(this.user).open();
                break;
            case ROLES:
                new PermissibleRelationGUI(true, this.user);
                break;
            case DEFAULT:
                new PermissibleDefaultGUI(this.user);
                break;
        }
    }

    @Override
    protected Map<Integer, PermMenuType> createSlotMap() {
        Map<Integer, PermMenuType> map = new HashMap<>();
        map.put(12, PermMenuType.CHUNK);
        map.put(14, PermMenuType.DEFAULT);
        map.put(22, PermMenuType.ROLES);
        return map;
    }

    @Override
    protected SimpleItem getItem(PermMenuType permMenuType) {
        return items.get(permMenuType);
    }

    @Override
    protected Map<Integer, SimpleItem> createDummyItems() {
        Map<Integer, SimpleItem> map = new HashMap<>();
        map.put(4, helpItem);
        return map;
    }
}
