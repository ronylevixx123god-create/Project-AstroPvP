package net.crankedpvp.hb.utils;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public final class ItemUtils {

    public static Material[] swords;
    public static Material[] swords_and_bow;
    public static Material[] armor;
    public static Material[] helmets;
    public static Material[] helmets_and_boots;
    public static Material[] chestplates;
    public static Material[] leggings;
    public static Material[] boots;
    public static Material[] pickaxe;
    public static Material[] tools;
    public static Material[] axe;
    public static Material[] weapons;
    public static Material[] weapons_and_tools;

    static {
        swords = new Material[]{Material.WOOD_SWORD, Material.STONE_SWORD, Material.IRON_SWORD, Material.GOLD_SWORD, Material.DIAMOND_SWORD};
        swords_and_bow = new Material[]{Material.WOOD_SWORD, Material.STONE_SWORD, Material.IRON_SWORD, Material.GOLD_SWORD, Material.DIAMOND_SWORD, Material.BOW};
        armor = new Material[]{Material.LEATHER_HELMET, Material.LEATHER_CHESTPLATE, Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS, Material.GOLD_HELMET, Material.GOLD_CHESTPLATE, Material.GOLD_LEGGINGS, Material.GOLD_BOOTS, Material.CHAINMAIL_HELMET, Material.CHAINMAIL_CHESTPLATE, Material.CHAINMAIL_LEGGINGS, Material.CHAINMAIL_BOOTS, Material.IRON_HELMET, Material.IRON_CHESTPLATE, Material.IRON_LEGGINGS, Material.IRON_BOOTS, Material.DIAMOND_HELMET, Material.DIAMOND_CHESTPLATE, Material.DIAMOND_LEGGINGS, Material.DIAMOND_BOOTS};
        helmets = new Material[]{Material.LEATHER_HELMET, Material.GOLD_HELMET, Material.IRON_HELMET, Material.CHAINMAIL_HELMET, Material.DIAMOND_HELMET};
        helmets_and_boots = new Material[]{Material.LEATHER_HELMET, Material.GOLD_HELMET, Material.IRON_HELMET, Material.CHAINMAIL_HELMET, Material.DIAMOND_HELMET, Material.LEATHER_BOOTS, Material.GOLD_BOOTS, Material.CHAINMAIL_BOOTS, Material.IRON_BOOTS, Material.DIAMOND_BOOTS};
        chestplates = new Material[]{Material.LEATHER_CHESTPLATE, Material.GOLD_CHESTPLATE, Material.IRON_CHESTPLATE, Material.CHAINMAIL_CHESTPLATE, Material.DIAMOND_CHESTPLATE};
        leggings = new Material[]{Material.LEATHER_LEGGINGS, Material.GOLD_LEGGINGS, Material.IRON_LEGGINGS, Material.CHAINMAIL_LEGGINGS, Material.DIAMOND_LEGGINGS};
        boots = new Material[]{Material.LEATHER_BOOTS, Material.GOLD_BOOTS, Material.CHAINMAIL_BOOTS, Material.IRON_BOOTS, Material.DIAMOND_BOOTS};
        pickaxe = new Material[]{Material.WOOD_PICKAXE, Material.STONE_PICKAXE, Material.IRON_PICKAXE, Material.GOLD_PICKAXE, Material.DIAMOND_PICKAXE};
        tools = new Material[]{Material.WOOD_PICKAXE, Material.STONE_PICKAXE, Material.IRON_PICKAXE, Material.GOLD_PICKAXE, Material.DIAMOND_PICKAXE, Material.WOOD_HOE, Material.STONE_HOE, Material.IRON_HOE, Material.GOLD_HOE, Material.DIAMOND_HOE, Material.WOOD_SPADE, Material.STONE_SPADE, Material.IRON_SPADE, Material.GOLD_SPADE, Material.DIAMOND_SPADE, Material.WOOD_AXE, Material.STONE_AXE, Material.IRON_AXE, Material.GOLD_AXE, Material.DIAMOND_AXE, Material.FISHING_ROD};
        axe = new Material[]{Material.WOOD_AXE, Material.STONE_AXE, Material.IRON_AXE, Material.GOLD_AXE, Material.DIAMOND_AXE};
        weapons = new Material[]{Material.WOOD_SWORD, Material.STONE_SWORD, Material.IRON_SWORD, Material.GOLD_SWORD, Material.DIAMOND_SWORD, Material.WOOD_AXE, Material.STONE_AXE, Material.IRON_AXE, Material.GOLD_AXE, Material.DIAMOND_AXE, Material.BOW};
        weapons_and_tools = new Material[]{Material.WOOD_SWORD, Material.STONE_SWORD, Material.IRON_SWORD, Material.GOLD_SWORD, Material.DIAMOND_SWORD, Material.WOOD_AXE, Material.STONE_AXE, Material.IRON_AXE, Material.GOLD_AXE, Material.DIAMOND_AXE, Material.BOW, Material.WOOD_PICKAXE, Material.STONE_PICKAXE, Material.IRON_PICKAXE, Material.GOLD_PICKAXE, Material.DIAMOND_PICKAXE, Material.WOOD_HOE, Material.STONE_HOE, Material.IRON_HOE, Material.GOLD_HOE, Material.DIAMOND_HOE, Material.WOOD_SPADE, Material.STONE_SPADE, Material.IRON_SPADE, Material.GOLD_SPADE, Material.DIAMOND_SPADE, Material.WOOD_AXE, Material.STONE_AXE, Material.IRON_AXE, Material.GOLD_AXE, Material.DIAMOND_AXE};
    }

    private ItemUtils() {

    }

    public static String getDisplayName(ItemStack is) {
        return is.hasItemMeta() && is.getItemMeta().hasDisplayName() ? is.getItemMeta().getDisplayName() : StringUtils.capitaliseAllWords(is.getType().name().toLowerCase().replace("_", " "));
    }
    public static boolean isBoots(ItemStack itemStack) {
        return (!isAirOrNull(itemStack) && (itemStack.getType().name().contains("_BOOTS")));
    }

    public static boolean isLeggings(ItemStack itemStack) {
        return (!isAirOrNull(itemStack) && (itemStack.getType().name().contains("_LEGGINGS")));
    }

    public static boolean isChestplate(ItemStack itemStack) {
        return (!isAirOrNull(itemStack) && (itemStack.getType().name().contains("_CHESTPLATE")));
    }

    public static boolean isHelmet(ItemStack itemStack) {
        return (!isAirOrNull(itemStack) && (itemStack.getType().name().contains("_HELMET")));
    }

    public static boolean isArmor(ItemStack itemStack) {
        return (!isAirOrNull(itemStack) && (itemStack
                .getType().name().contains("_HELMET") || itemStack
                .getType().name().contains("_CHESTPLATE") || itemStack
                .getType().name().contains("_LEGGINGS") || itemStack
                .getType().name().contains("_BOOTS")));
    }

    public static boolean isTool(ItemStack itemStack) {
        return (!isAirOrNull(itemStack) && (itemStack
                .getType().name().contains("_SHOVEL") || itemStack
                .getType().name().contains("_HOE") || itemStack
                .getType().name().contains("_PICKAXE")));
    }

    public static boolean isWeapon(ItemStack itemStack) {
        return (!isAirOrNull(itemStack) && (itemStack
                .getType().name().endsWith("_SWORD") || itemStack
                .getType().name().endsWith("_AXE") || itemStack
                .getType().name().endsWith("_HOE")
        ));
    }

    public static boolean isSword(ItemStack itemStack) {
        return (!isAirOrNull(itemStack) && itemStack
                .getType().name().contains("_SWORD") || itemStack.getType().name().endsWith("_HOE"));
    }

    public static boolean isAxe(ItemStack itemStack) {
        return (!isAirOrNull(itemStack) && itemStack
                .getType().name().contains("_AXE") || itemStack.getType().name().endsWith("_HOE"));
    }

    public static boolean isEnchantable(ItemStack itemStack) {
        if (isAirOrNull(itemStack)) return false;
        return (isTool(itemStack) ||
                isArmor(itemStack) ||
                isWeapon(itemStack) ||
                itemStack.getType() == Material.BOW);
    }

    public static boolean isSkull(ItemStack itemStack) {
        return (!isAirOrNull(itemStack) && itemStack.getType() == Material.SKULL_ITEM);
    }

    public static boolean isAirOrNull(ItemStack item){
        return item == null || item.getType().equals(Material.AIR);
    }

    public static ItemStack decreaseItemStackAmountByOne(ItemStack itemStack) {
        if (itemStack.getAmount() == 1) return null;

        itemStack.setAmount(itemStack.getAmount() - 1);
        return itemStack;
    }


}
