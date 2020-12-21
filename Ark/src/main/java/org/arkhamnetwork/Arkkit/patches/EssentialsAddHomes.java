/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.earth2me.essentials.Essentials
 *  net.milkbowl.vault.permission.Permission
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.RegisteredServiceProvider
 */
package org.arkhamnetwork.Arkkit.patches;

import com.earth2me.essentials.Essentials;
import java.io.IOException;
import java.util.Arrays;
import net.milkbowl.vault.permission.Permission;
import org.arkhamnetwork.Arkkit.patches.CommandAddHomes;
import org.arkhamnetwork.Arkkit.patches.CommandEssentialsAddHomeItem;
import org.arkhamnetwork.Arkkit.patches.EssentialsAddHomeItemListener;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.arkhamnetwork.Arkkit.utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;

public class EssentialsAddHomes
extends ArkPlugin {
    private static Permission permission = null;
    private static Essentials ess = null;

    public EssentialsAddHomes(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        if (!this.setupPermissions()) {
            Bukkit.getLogger().warning("Vault permissions not hooked, disabling patch.");
            this.kill();
            return;
        }
        ess = (Essentials)Bukkit.getServer().getPluginManager().getPlugin("Essentials");
        this.populateExtraHomeGroups();
        this.registerListener(new EssentialsAddHomeItemListener());
        this.registerCommand(new CommandAddHomes("addhomes", "/<command>", "Adds extra homes to player."));
        this.registerCommand(new CommandEssentialsAddHomeItem("addhomeitem", "/<command>", "Adds an item to incr. a players max homes.", Arrays.asList("ahi")));
    }

    @Override
    public void disable() {
    }

    protected static Permission getPermissions() {
        return permission;
    }

    protected static Essentials getEssentials() {
        return ess;
    }

    protected static String getEssentialsGroup(int home_limit) {
        return "addhome_" + String.valueOf(home_limit);
    }

    private void populateExtraHomeGroups() {
        FileConfiguration fg = EssentialsAddHomes.getEssentials().getConfig();
        for (int i = 0; i < 100; ++i) {
            if (EssentialsAddHomes.getEssentials().getConfig().getConfigurationSection("sethome-multiple").contains(EssentialsAddHomes.getEssentialsGroup(i))) continue;
            Bukkit.getLogger().info("Creating new sethome-multiple group, " + EssentialsAddHomes.getEssentialsGroup(i) + " ...");
            fg.getConfigurationSection("sethome-multiple").set(EssentialsAddHomes.getEssentialsGroup(i), (Object)i);
        }
        try {
            fg.save("plugins/Essentials/config.yml");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider permissionProvider = Bukkit.getServer().getServicesManager().getRegistration(Permission.class);
        if (permissionProvider != null) {
            permission = (Permission)permissionProvider.getProvider();
        }
        return permission != null;
    }

    public static ItemStack getAddHomeItem() {
        return ItemUtils.buildItem(Material.BED, (short)0, (Object)ChatColor.YELLOW + "" + (Object)ChatColor.BOLD + "Max Home Increase " + (Object)ChatColor.GRAY + "(Right Click)", (Object)ChatColor.GRAY + "Adds +1 " + (Object)ChatColor.YELLOW + "/home" + (Object)ChatColor.GRAY + " slots to your player.");
    }

    protected static boolean isAddHomeItem(ItemStack i) {
        return i.isSimilar(EssentialsAddHomes.getAddHomeItem());
    }
}

