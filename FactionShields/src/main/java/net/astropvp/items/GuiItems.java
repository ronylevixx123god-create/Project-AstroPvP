package net.astropvp.items;

import net.astropvp.FactionShield;
import org.bukkit.configuration.file.*;
import org.bukkit.inventory.*;
import org.bukkit.*;
import org.bukkit.inventory.meta.*;
import java.util.*;

public class GuiItems {
    private static FileConfiguration config;

    static {
        GuiItems.config = FactionShield.getPlugin().getConfig();
    }

    public static ItemStack getHourOnMaterial(final int hour) {
        final ItemStack hourOnMaterial = new ItemStack(GuiItems.config.getInt("net.astropvp.armorsets.gui.items.protected-material.id"), 1, (short)GuiItems.config.getInt("net.astropvp.armorsets.gui.items.protected-material.subid"));
        final ItemMeta hourOnMeta = hourOnMaterial.getItemMeta();
        final ArrayList<String> hourOnLore = new ArrayList<String>();
        for (final String line : GuiItems.config.getStringList("net.astropvp.armorsets.gui.items.protected-material.lore")) {
            hourOnLore.add(ChatColor.translateAlternateColorCodes('&', line));
        }
        hourOnMeta.setLore((List)hourOnLore);
        hourOnMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', GuiItems.config.getString("net.astropvp.armorsets.gui.items.protected-material.name").replace("%hour%", String.valueOf(hour))));
        hourOnMaterial.setItemMeta(hourOnMeta);
        return hourOnMaterial;
    }

    public static ItemStack getHourOffMaterial(final int hour) {
        final ItemStack hourOffMaterial = new ItemStack(GuiItems.config.getInt("net.astropvp.armorsets.gui.items.not-protected-material.id"), 1, (short)GuiItems.config.getInt("net.astropvp.armorsets.gui.items.not-protected-material.subid"));
        final ItemMeta hourOffMeta = hourOffMaterial.getItemMeta();
        final ArrayList<String> hourOffLore = new ArrayList<String>();
        for (final String line : GuiItems.config.getStringList("net.astropvp.armorsets.gui.items.not-protected-material.lore")) {
            hourOffLore.add(ChatColor.translateAlternateColorCodes('&', line));
        }
        hourOffMeta.setLore((List)hourOffLore);
        hourOffMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', GuiItems.config.getString("net.astropvp.armorsets.gui.items.not-protected-material.name").replace("%hour%", String.valueOf(hour))));
        hourOffMaterial.setItemMeta(hourOffMeta);
        return hourOffMaterial;
    }
}
