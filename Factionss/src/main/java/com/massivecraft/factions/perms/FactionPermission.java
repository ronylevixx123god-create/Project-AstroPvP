package com.massivecraft.factions.perms;

import com.google.common.collect.Lists;
import java.util.List;

import com.massivecraft.factions.util.material.MaterialDb;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.material.MaterialData;

public enum FactionPermission {
    FULL(MaterialDb.get("BEACON"), "Full Access", "Everything", 12, new String[] { ChatColor.GRAY + "Access to all faction permissions.", "", ChatColor.GRAY + "If this is " + ChatColor.GRAY + ChatColor.UNDERLINE + "ALLOWED" + ChatColor.GRAY + ", no other", ChatColor.GRAY + "permissions will apply. The", ChatColor.GRAY + "entity will have full access to", ChatColor.GRAY + "your chunk." }),
    CHEST(MaterialDb.get("CHEST"), "Chests", 13),
    CLAIMING(MaterialDb.get("DIAMOND_AXE"), "Claim/Unclaim Land", 14, new String[] { ChatColor.GRAY + "Grant the ability to claim or", ChatColor.GRAY + "unclaim any chunks for your faction." }),
    OTHER(MaterialDb.get("GRASS"), "All Other Blocks", 19, new String[] { ChatColor.GRAY + "Grant the ability to break and place other blocks." }),
    SPONGE(MaterialDb.get("SPONGE"), "Sponges", 20),
    DISPENSE(MaterialDb.get("DISPENSER"), "Dispensers", 21),
    DROPPER(MaterialDb.get("DROPPER"), "Droppers", 22),
    CREEPER(MaterialDb.get("MONSTER_EGG"), EntityType.CREEPER.getTypeId(), "Can Place Creeper Eggs", "Creeper Eggs", 23, new String[] { ChatColor.GRAY + "Grant the ability to place Creeper Eggs." }),
    TNT(MaterialDb.get("TNT"), "TNT", 24),
    OBBY(MaterialDb.get("OBSIDIAN"), "Obsidian", 25),
    SPAWNER(MaterialDb.get("MOB_SPAWNER"), "Mob Spawners", 28),
    HOPPER(MaterialDb.get("HOPPER"), "Hoppers", 29),
    DOOR(MaterialDb.get("WOOD_DOOR"), "Doors & Gates", 30),
    PRESSURE(MaterialDb.get("WOOD_PLATE"), "Pressure Plates", 31),
    LEVER(MaterialDb.get("LEVER"), "Levers", 32),
    BUTTON(MaterialDb.get("STONE_BUTTON"), "Buttons", 33),
    TRAP_DOOR(MaterialDb.get("TRAP_DOOR"), "Trap Doors", 34);

    static {
        containers = Lists.newArrayList(new Material[] { MaterialDb.get("BEACON"), MaterialDb.get("BREWING_STAND"), MaterialDb.get("ENCHANTMENT_TABLE"), MaterialDb.get("FURNACE"), MaterialDb.get("BURNING_FURNACE"), MaterialDb.get("ANVIL") });
    }

    private short durability = 0;

    private static List<Material> containers;

    private String bareName;

    private Material displayMaterial;

    private String name;

    private List<String> description;

    private int guiSlot;

    FactionPermission(Material dis, short durability, String name, String barename, int slot, String... desc) {
        this.bareName = barename;
    }

    FactionPermission(Material dis, short durability, String name, int slot, String... desc) {
        this.displayMaterial = dis;
        this.name = name;
        this.durability = durability;
        this.guiSlot = slot;
        this.description = Lists.newArrayList(desc);
    }

    FactionPermission(Material dis, String name, int slot, String... desc) {
        this.bareName = name;
    }

    FactionPermission(Material dis, String name, String barename, int slot, String... desc) {
        this.bareName = barename;
    }

    FactionPermission(String bareName, Material displayMaterial, short durability, String name, List<String> description, int guiSlot) {
        this.bareName = bareName;
        this.displayMaterial = displayMaterial;
        this.durability = durability;
        this.name = name;
        this.description = description;
        this.guiSlot = guiSlot;
    }

    public static FactionPermission getFromMaterial(MaterialData item) {
        FactionPermission[] arrayOfFactionPermission;
        int i;
        byte b;
        for (arrayOfFactionPermission = values(), i = arrayOfFactionPermission.length, b = 0; b < i; ) {
            FactionPermission perm = arrayOfFactionPermission[b];
            if (perm.getDisplayMaterial() != item.getItemType() || perm.getDurability() != item.getData()) {
                b++;
                continue;
            }
            return perm;
        }
        return getFromBlock(item);
    }

    public static FactionPermission getFromItem(MaterialData item) {
        FactionPermission[] arrayOfFactionPermission;
        int i;
        byte b;
        for (arrayOfFactionPermission = values(), i = arrayOfFactionPermission.length, b = 0; b < i; ) {
            FactionPermission perm = arrayOfFactionPermission[b];
            if (perm.getDisplayMaterial() != item.getItemType() || perm.getDurability() != item.getData()) {
                b++;
                continue;
            }
            return perm;
        }
        return null;
    }

    public static boolean isAlwaysOpennableContainer(Material material) {
        return containers.contains(material);
    }

    public static boolean isConsumable(Material material) {
        return (material == MaterialDb.get("POTION") || material.isEdible() || material == MaterialDb.get("ENDER_PEARL"));
    }

    public static FactionPermission getFromBlock(MaterialData block) {
        Material itemType = block.getItemType();
        if (itemType == MaterialDb.get("WOOD_BUTTON"))
            return BUTTON;
        if (itemType == MaterialDb.get("FENCE_GATE"))
            return DOOR;
        if (itemType == MaterialDb.get("STONE_PLATE") || itemType == MaterialDb.get("IRON_PLATE") || itemType == MaterialDb.get("GOLD_PLATE"))
            return PRESSURE;
        if (itemType == MaterialDb.get("IRON_DOOR_BLOCK") || itemType == MaterialDb.get("WOODEN_DOOR"))
            return DOOR;
        if (itemType == MaterialDb.get("MOB_SPAWNER"))
            return SPAWNER;
        if (itemType == MaterialDb.get("SPONGE"))
            return SPONGE;
        if (itemType == MaterialDb.get("TNT"))
            return TNT;
        if (itemType == MaterialDb.get("TRAP_DOOR"))
            return TRAP_DOOR;
        if (itemType == MaterialDb.get("LEVER"))
            return LEVER;
        if (itemType == MaterialDb.get("HOPPER"))
            return HOPPER;
        if (itemType == MaterialDb.get("CHEST") || itemType == MaterialDb.get("TRAPPED_CHEST"))
            return CHEST;
        FactionPermission[] arrayOfFactionPermission;
        int i;
        byte b;
        for (arrayOfFactionPermission = values(), i = arrayOfFactionPermission.length, b = 0; b < i; ) {
            FactionPermission perm = arrayOfFactionPermission[b];
            if (perm.getDisplayMaterial() != itemType || (perm.getDurability() != 0 && perm.getDurability() != block.getData())) {
                b++;
                continue;
            }
            return perm;
        }
        return OTHER;
    }

    public boolean isPlaceableOn() {
        return (this == SPONGE || this == SPAWNER || this == TNT || this == OBBY);
    }

    public String getBareName() {
        return this.bareName;
    }

    public Material getDisplayMaterial() {
        return this.displayMaterial;
    }

    public short getDurability() {
        return this.durability;
    }

    public String getName() {
        return this.name;
    }

    public List<String> getDescription() {
        return this.description;
    }

    public int getGuiSlot() {
        return this.guiSlot;
    }
}

