/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 */
package me.ifamasssxd.cosmicduel.godset;

import org.bukkit.ChatColor;

public enum EnchantSlots {
    COMMON(1, "Simple", 1, ChatColor.WHITE),
    UNCOMMON(2, "Unique", 1, ChatColor.GREEN),
    RARE(3, "Elite", 1, ChatColor.AQUA),
    ULTRA_RARE(4, "Ultimate", 2, ChatColor.YELLOW),
    LEGENDARY(5, "Legendary", 3, ChatColor.GOLD);

    int enchantIndex;
    String displayName;
    int slotCost;
    ChatColor nameColor;

    public static int getSlots(int slot) {
        for (EnchantSlots enchant : EnchantSlots.values()) {
            if (enchant.getEnchantIndex() != slot) continue;
            return enchant.getSlotCost();
        }
        return 0;
    }

    public static EnchantSlots fromName(String name) {
        for (EnchantSlots slots : EnchantSlots.values()) {
            if (!slots.getDisplayName().equals(name)) continue;
            return slots;
        }
        return null;
    }

    public static EnchantSlots getEnchantRarity(int tier) {
        for (EnchantSlots enchant : EnchantSlots.values()) {
            if (enchant.getEnchantIndex() != tier) continue;
            return enchant;
        }
        return null;
    }

    private EnchantSlots(int enchantIndex, String displayName, int slotCost, ChatColor nameColor) {
        this.enchantIndex = enchantIndex;
        this.displayName = displayName;
        this.slotCost = slotCost;
        this.nameColor = nameColor;
    }

    public int getEnchantIndex() {
        return this.enchantIndex;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public int getSlotCost() {
        return this.slotCost;
    }

    public ChatColor getNameColor() {
        return this.nameColor;
    }
}

