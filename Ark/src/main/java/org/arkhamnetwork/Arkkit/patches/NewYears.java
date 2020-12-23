/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.arkhamnetwork.Arkkit.utils.ItemUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class NewYears
        extends ArkPlugin {
    private static final ItemStack explosiveCake = ItemUtils.buildItem(Material.CAKE, (short) 0, ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + "Explosive Cake " + ChatColor.GRAY + "(Right Click)", ChatColor.GRAY + "What? Have you not seen an", ChatColor.GRAY.toString() + ChatColor.UNDERLINE.toString() + "EXPLODING" + ChatColor.GRAY + " cake before?!");

    public NewYears(Plugin p) {
        super(p);
    }

    public static ItemStack getExplosiveCake() {
        return NewYears.getExplosiveCake(1);
    }

    public static ItemStack getExplosiveCake(int cake) {
        ItemStack item = ItemUtils.buildItem(Material.CAKE, (short) 0, ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + "Explosive Cake " + ChatColor.GRAY + "(Right Click)", ChatColor.GRAY + "What? Have you not seen an", ChatColor.GRAY.toString() + ChatColor.UNDERLINE.toString() + "EXPLODING" + ChatColor.GRAY + " cake before?!");
        item.setAmount(cake);
        return item;
    }

    protected static boolean isExplosiveCake(ItemStack i) {
        return i.isSimilar(explosiveCake);
    }

    public static ItemStack getCosmicCracker() {
        return NewYears.getCosmicCracker(1);
    }

    public static ItemStack getCosmicCracker(int amount) {
        ItemStack retr = ItemUtils.buildItem(Material.FIREBALL, (short) 0, ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + "Space Firework " + ChatColor.GRAY + "(Right Click)", ChatColor.GRAY + "A firework specially designed to", ChatColor.GRAY + "detonate in intergalactic space.");
        retr.setAmount(amount);
        return retr;
    }

    protected static boolean isCosmicCracker(ItemStack i) {
        return i.isSimilar(NewYears.getCosmicCracker());
    }

    @Override
    public void enable() {
        this.registerListener(new NewYearsListener());
        this.registerCommand(new NewYearsCommand("newyears", "/<command>", "Unique new years item spawning."));
    }

    @Override
    public void disable() {
    }
}

