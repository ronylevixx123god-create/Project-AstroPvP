/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.net.astropvp.armorsets.gui.CosmicGUI
 *  com.cosmicpvp.cosmicutils.me.astropvp.cratekeys.net.astropvp.armorsets.utils.CC
 *  com.cosmicpvp.cosmicutils.me.astropvp.cratekeys.net.astropvp.armorsets.utils.ItemBuilder
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 */
package me.ifamasssxd.help.gui;

import com.cosmicpvp.cosmicutils.gui.CosmicGUI;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class HCFClassGui
extends CosmicGUI {
    public HCFClassGui(Player player) {
        super(player, "Classes", 9);
    }

    public void redraw() {
        this.setItem(3, new ItemBuilder(Material.GOLD_CHESTPLATE, CC.YellowB + "Bard", "", CC.GoldB + "Requirements", CC.GoldB + " * " + CC.White + "Equip Full Gold Armor", "", CC.GreenB + "Effects", CC.GreenB + " * " + CC.White + "Items grant passive and active abilities", CC.White + "    for you and nearby faction members", CC.GreenB + " * " + CC.White + "Passive Speed II", CC.GreenB + " * " + CC.White + "Passive Resistance II", CC.GreenB + " * " + CC.White + "Passive Regeneration I", CC.GreenB + " * " + CC.White + "Passive Fire Resistance I", "", CC.YellowB + "Ability Items", CC.YellowB + " * " + CC.White + "Sugar (Speed)", CC.YellowB + " * " + CC.White + "Blaze Powder (Strength)", CC.YellowB + " * " + CC.White + "Magma Cream (Fire Resistance)", CC.YellowB + " * " + CC.White + "Ghast Tear (Regeneration)", CC.YellowB + " * " + CC.White + "Iron Ingot (Resistance)", CC.YellowB + " * " + CC.White + "Spider Eye (Wither)", CC.YellowB + " * " + CC.White + "Glistering Melon (Healing)", CC.YellowB + " * " + CC.White + "Feather (Jump Boost)").build(), null);
        this.setItem(4, new ItemBuilder(Material.LEATHER_CHESTPLATE, CC.AquaB + "Archer", "", CC.GoldB + "Requirements", CC.GoldB + " * " + CC.White + "Equip Full Leather Armor", "", CC.GreenB + "Effects", CC.GreenB + " * " + CC.White + "Items grant active abilities", CC.GreenB + " * " + CC.White + "Mark enemies with a Bow to", CC.White + "    make them receive 25% more damage.", CC.GreenB + " * " + CC.White + "Passive Speed II", CC.GreenB + " * " + CC.White + "Passive Resistance I", "", CC.YellowB + "Ability Items", CC.YellowB + " * " + CC.White + "Sugar (Speed)", CC.YellowB + " * " + CC.White + "Feather (Jump Boost)").build(), null);
        this.setItem(5, new ItemBuilder(Material.IRON_CHESTPLATE, CC.YellowB + "Miner", "", CC.GoldB + "Requirements", CC.GoldB + " * " + CC.White + "Equip Full Iron Armor", "", CC.GreenB + "Effects", CC.GreenB + " * " + CC.White + "Gain Invisibility when Y level <= 20", CC.GreenB + " * " + CC.White + "Passive Haste II", CC.GreenB + " * " + CC.White + "Passive Fire Resistance I").build(), null);
    }
}

