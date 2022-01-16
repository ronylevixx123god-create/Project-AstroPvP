package net.astropvp.armorsets.struct.heroicArmor;

import com.cosmicpvp.cosmicutils.itembuilder.ItemBuilder;
import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public enum HeroicArmor {
    HELMET(3, 1, Material.LEATHER_HELMET, CC.DarkRedB + "Heroic Helmet", false),
    CHESTPLATE(8, 1, Material.LEATHER_CHESTPLATE, CC.DarkRedB + "Heroic Chestplate", false),
    LEGGINGS(6, 1, Material.LEATHER_LEGGINGS, CC.DarkRedB + "Heroic Leggings", false),
    BOOTS(3, 1, Material.LEATHER_BOOTS, CC.DarkRedB + "Heroic Boots", false),
    SWORD(1, 4, Material.GOLD_SWORD, CC.DarkRedB + "Heroic Sword", true),
    AXE(1, 4, Material.GOLD_AXE, CC.DarkRedB + "Heroic Axe", true);

    private double reduction;
    private double increase;
    private Material type;
    private String display;
    private boolean isWeapon;

    HeroicArmor(double reduction, double increase, Material type, String display, boolean isWeapon) {
        this.reduction = reduction;
        this.increase = increase;
        this.type = type;
        this.display = display;
        this.isWeapon = isWeapon;
    }

    public static List<ItemStack> getHeroicItems() {
        List<ItemStack> items = new ArrayList<>();
        for (HeroicArmor armor : HeroicArmor.values()) {
            ItemStack item;
            if (armor.isWeapon) {
                item = new ItemBuilder(armor.type).setColor(Color.RED)
                        .setName(armor.display)
                        .addLoreLine("")
                        .addLoreLine(CC.Gray + "+ " + armor.increase + " Bonus Attack Damage")
                        .addLoreLine(CC.DarkRed + "This weapon is stronger than diamond.")
                        .addEnchantment(Enchantment.DAMAGE_ALL, 5)
                        .addEnchantment(Enchantment.DURABILITY, 3)
                        .build();
            } else {
                item = new ItemBuilder(armor.type).setColor(Color.RED)
                        .setName(armor.display)
                        .addLoreLine("")
                        .addLoreLine(CC.Gray + "+ " + armor.reduction + " Armor Value")
                        .addLoreLine(CC.DarkRed + "This armor is stronger than diamond.")
                        .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4)
                        .addEnchantment(Enchantment.DURABILITY, 3)
                        .build();
            }

            NBTItem wrapper = new NBTItem(item);
            NBTCompound cosmicData = wrapper.getOrCreateCompound("cosmicData");
            cosmicData.setBoolean("heroicArmor", true);
            items.add(wrapper.getItem());
        }
        return items;
    }
}
