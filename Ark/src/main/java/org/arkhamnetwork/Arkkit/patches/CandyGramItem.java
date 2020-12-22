/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  org.bukkit.Material
 *  org.bukkit.material.MaterialData
 */
package org.arkhamnetwork.Arkkit.patches;

import com.cosmicpvp.cosmicutils.utils.CC;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;

enum CandyGramItem {
    FLOWER(new MaterialData(Material.RED_ROSE), CC.RedB + "Rose", 5000, 0),
    YELLOW_FLOWER(new MaterialData(Material.YELLOW_FLOWER), CC.Yellow + "Dandelion", 5000, 1),
    RED_TULIP(new MaterialData(Material.RED_ROSE, (byte) 4), CC.Red + "Red Tulip", 5000, 2),
    ORANGE_TULIP(new MaterialData(Material.RED_ROSE, (byte) 5), CC.Gold + "Orange Tulip", 5000, 3),
    WHITE_TULIP(new MaterialData(Material.RED_ROSE, (byte) 6), CC.Aqua + "White Tulip", 5000, 4),
    PINK_TULIP(new MaterialData(Material.RED_ROSE, (byte) 7), CC.Red + "Red Tulip", 5000, 5),
    GRASS(new MaterialData(Material.LONG_GRASS, (byte) 1), CC.Green + "Grass", 5000, 6),
    BROWN_MUSHROOM(new MaterialData(Material.BROWN_MUSHROOM), CC.Yellow + "Brown Mushroom", 5000, 7),
    COBWEB(new MaterialData(Material.WEB), CC.Yellow + "Cobweb", 5000, 8),
    BLUE_ORCHID(new MaterialData(Material.RED_ROSE, (byte) 1), CC.Aqua + "Blue Orchid", 50000, 18),
    ALLIUM(new MaterialData(Material.RED_ROSE, (byte) 2), CC.Aqua + "Allium", 50000, 19),
    AZURE_BLUET(new MaterialData(Material.RED_ROSE, (byte) 3), CC.LightPurple + "Azure Bluet", 50000, 20),
    OXEYE(new MaterialData(Material.RED_ROSE, (byte) 8), CC.LightPurple + "Oxeye Daisy", 50000, 21),
    CACTUS(new MaterialData(Material.CACTUS), CC.Green + "Cactus", 50000, 22),
    RED_MUSHROOM(new MaterialData(Material.RED_MUSHROOM), CC.Red + "Red Mushroom", 50000, 23),
    FERN(new MaterialData(Material.LONG_GRASS, (byte) 2), CC.Green + "Fern", 50000, 24),
    IRON_INGOT(new MaterialData(Material.IRON_INGOT), CC.Green + "Iron Ingot", 50000, 25),
    GOLD_INGOT(new MaterialData(Material.GOLD_INGOT), CC.Green + "Gold Ingot", 50000, 26),
    SUNFLOWER(new MaterialData(Material.DOUBLE_PLANT, (byte) 0), CC.Yellow + "Sunflower", 100000, 36),
    LILAC(new MaterialData(Material.DOUBLE_PLANT, (byte) 1), CC.Yellow + "Lilac", 100000, 37),
    LARGE_FERN(new MaterialData(Material.DOUBLE_PLANT, (byte) 3), CC.Green + "Large Fern", 100000, 38),
    ROSE_BUSH(new MaterialData(Material.DOUBLE_PLANT, (byte) 4), CC.Red + "Rose Bush", 100000, 39),
    PEONY(new MaterialData(Material.DOUBLE_PLANT, (byte) 5), CC.LightPurple + "Peony", 100000, 40),
    DIAMOND(new MaterialData(Material.DIAMOND), CC.Aqua + "Diamond", 100000, 41),
    EMERALD(new MaterialData(Material.EMERALD), CC.Green + "Emerald", 100000, 42),
    DEAD_BUSH(new MaterialData(Material.DEAD_BUSH), CC.Yellow + "Dead Bush", 100000, 43),
    ROTTEN_FLESH(new MaterialData(Material.ROTTEN_FLESH), CC.RedB + "Rotten Flesh", 100000, 44);

    private MaterialData material;
    private String name;
    private int cost;
    private int slot;

    private CandyGramItem(MaterialData material, String name, int cost, int slot) {
        this.material = material;
        this.name = name;
        this.cost = cost;
        this.slot = slot;
    }

    public MaterialData getMaterial() {
        return this.material;
    }

    public String getName() {
        return this.name;
    }

    public int getCost() {
        return this.cost;
    }

    public int getSlot() {
        return this.slot;
    }
}

