/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.arkhamnetwork.Arkkit.utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;

public class Drinks
        extends ArkPlugin {
    protected static int tipsyLevel;
    protected static String tipsyStr;
    protected static int smashedLevel;
    protected static String smashedStr;
    protected static int poisoningLevel;
    protected static String poisoningStr;
    protected static int chanceToSlurS;
    protected static int chanceToSlurSPerLevel;
    protected static int chanceToHic;
    protected static int chanceToHicPerLevel;
    private static ItemStack drink;
    private static ItemStack splashDrink;
    private static ItemStack christmasDrink;
    private static DrunkEffects effects;
    private static int taskId;
    private static HashMap<String, Integer> drunks;

    static {
        tipsyLevel = 1;
        tipsyStr = ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + "(!) " + ChatColor.YELLOW + "You are feeling tipsy, time to party, drink up!";
        smashedLevel = 5;
        smashedStr = ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + "(!) " + ChatColor.YELLOW + "You are completely smashed. What a party!";
        poisoningLevel = 10;
        poisoningStr = ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!) " + ChatColor.RED + "You are getting space drink poisoning, sober up!";
        chanceToSlurS = 50;
        chanceToSlurSPerLevel = 5;
        chanceToHic = 15;
        chanceToHicPerLevel = 5;
    }

    public Drinks(Plugin p) {
        super(p);
        drunks = new HashMap();
        drink = ItemUtils.buildItem(Material.POTION, (short) 0, ChatColor.AQUA + "Space Drink", ChatColor.GRAY + "Smuggled in from a mysterious galaxy,", ChatColor.GRAY + "it is the Cosmic partying drink of choice!");
        splashDrink = ItemUtils.buildItem(Material.POTION, (short) 16429, ChatColor.LIGHT_PURPLE + "Splash Space Drink", ChatColor.GRAY + "Smuggled in from a mysterious galaxy,", ChatColor.GRAY + "it is the Cosmic partying drink of choice", ChatColor.GRAY + "... now designed for group consumption!");
        christmasDrink = ItemUtils.buildItem(Material.POTION, (short) 0, ChatColor.YELLOW + "Christmas Eggnog", ChatColor.GRAY + "Also known as \"egg milk punch\",", ChatColor.GRAY + "a chilled, sweetened, alcoholic", ChatColor.GRAY + "beverage perfect for the holidays!");
    }

    public static ItemStack getDrink() {
        return drink.clone();
    }

    public static ItemStack getSplashDrink() {
        return splashDrink.clone();
    }

    protected static boolean isDrink(ItemStack i) {
        return i.isSimilar(drink) || i.isSimilar(christmasDrink);
    }

    protected static boolean isSplashDrink(ItemStack i) {
        return i.isSimilar(splashDrink);
    }

    public static ItemStack getChristmasDrink() {
        return christmasDrink.clone();
    }

    protected static void startEffects() {
        effects = new DrunkEffects(Arkkit.get());
        taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(Arkkit.get(), effects, 100L, 100L);
    }

    protected static void stopEffects() {
        Bukkit.getScheduler().cancelTask(taskId);
        effects = null;
    }

    protected static int moreDrunk(Player player) {
        int lvl;
        int n = lvl = drunks.containsKey(player.getName()) ? drunks.get(player.getName()) : -1;
        if (lvl == -1) {
            drunks.put(player.getName(), 1);
            lvl = 1;
        } else {
            drunks.put(player.getName(), lvl + 1);
            ++lvl;
        }
        if (effects == null) {
            Drinks.startEffects();
        }
        return lvl;
    }

    protected static HashMap<String, Integer> getDrunks() {
        return drunks;
    }

    protected static int getDrunkLevel(Player player) {
        Integer lvl = drunks.get(player.getName());
        if (lvl == null) {
            return 0;
        }
        return lvl;
    }

    @Override
    public void enable() {
        this.registerListener(new DrinkListener());
    }

    @Override
    public void disable() {
    }
}

