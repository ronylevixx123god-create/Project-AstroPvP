/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import java.util.Calendar;
import java.util.TimeZone;
import org.arkhamnetwork.Arkkit.patches.EasterEggHunt;
import org.arkhamnetwork.Arkkit.patches.EasterEggHuntCommand;
import org.arkhamnetwork.Arkkit.patches.EasterListener;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.arkhamnetwork.Arkkit.utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class Easter
extends ArkPlugin {
    Calendar cal = Calendar.getInstance();
    TimeZone zone = TimeZone.getTimeZone("EST");
    private EasterEggHunt eggHuntTask;

    public Easter(Plugin p) {
        super(p);
    }

    public boolean isEaster() {
        this.cal.setTimeZone(this.zone);
        this.cal.setTimeInMillis(System.currentTimeMillis());
        int month = this.cal.get(2);
        int day = this.cal.get(7);
        int dayOfMonth = this.cal.get(5);
        return month == 3 && (day == 1 || day == 7 || day == 2 || day == 3 || day == 6) && dayOfMonth <= 22 && dayOfMonth >= 18;
    }

    @Override
    public void enable() {
        this.registerCommand(new EasterEggHuntCommand());
        this.registerListener(new EasterListener(this));
        if (this.getBukkitPlugin().getServer().getPluginManager().isPluginEnabled("Factions")) {
            this.eggHuntTask = new EasterEggHunt(this);
            this.registerTask(this.eggHuntTask.runTaskTimer(this.getBukkitPlugin(), 12000L, 12000L), Easter.class);
        }
        if (this.isEaster()) {
            Bukkit.getLogger().info("[(Arkkit) Easter] SEEMS TO BE EASTER OR SHORTLY AFTERWARDS!!");
        }
    }

    @Override
    public void disable() {
        this.unregisterTasks();
        this.unregisterListeners();
        this.unregisterCommands();
    }

    public static ItemStack getEasterEgg() {
        return ItemUtils.buildItem(Material.EGG, (short)0, (Object)ChatColor.YELLOW + "Easter Egg " + (Object)ChatColor.GRAY + "(Right Click)", (Object)ChatColor.GRAY + "A colored easter egg full of loot!");
    }

    public static boolean isEasterEgg(ItemStack i) {
        return i != null && i.hasItemMeta() && i.getItemMeta().hasDisplayName() && i.getItemMeta().hasLore() && i.getItemMeta().getDisplayName().equals((Object)ChatColor.YELLOW + "Easter Egg " + (Object)ChatColor.GRAY + "(Right Click)") && ((String)i.getItemMeta().getLore().get(0)).equals((Object)ChatColor.GRAY + "A colored easter egg full of loot!");
    }

    public EasterEggHunt getEggHuntTask() {
        return this.eggHuntTask;
    }
}

