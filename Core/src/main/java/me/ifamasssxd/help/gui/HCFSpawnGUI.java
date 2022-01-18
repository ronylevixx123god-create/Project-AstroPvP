/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.net.astropvp.armorsets.gui.CosmicGUI
 *  com.cosmicpvp.cosmicutils.me.astropvp.cratekeys.net.astropvp.armorsets.utils.CC
 *  com.cosmicpvp.cosmicutils.me.astropvp.cratekeys.net.astropvp.armorsets.utils.ItemBuilder
 *  com.cosmicpvp.cosmicutils.me.astropvp.cratekeys.net.astropvp.armorsets.utils.LocationUtils
 *  com.cosmicpvp.cosmicutils.me.astropvp.cratekeys.net.astropvp.armorsets.utils.WorldGuardUtils
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.World
 *  org.bukkit.entity.Player
 *  org.bukkit.event.player.PlayerTeleportEvent$TeleportCause
 *  org.bukkit.plugin.Plugin
 */
package me.ifamasssxd.help.gui;

import com.cosmicpvp.cosmicutils.gui.CosmicGUI;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import com.cosmicpvp.cosmicutils.utils.LocationUtils;
import com.cosmicpvp.cosmicutils.utils.WorldGuardUtils;
import net.astropvp.core.AstroCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.Plugin;

public class HCFSpawnGUI
extends CosmicGUI {
    boolean canTeleport = false;

    public HCFSpawnGUI(Player player) {
        super(player, "Spawn Teleports", 45);
        this.canTeleport = WorldGuardUtils.isInRegionsFast((Location)player.getLocation(), (String[])new String[]{"spawn_region"});
    }

    public void redraw() {
        World world = Bukkit.getWorld((String)"world");
        String teleString = this.canTeleport ? CC.Gray + "Click to teleport to this location!" : CC.Red + "Visit the spawn area to use this shortcut!";
        Location shop = new Location(world, -50.0, 72.0, 45.0, 148.2f, 5.1f);
        Location spawn = world.getSpawnLocation();
        this.setItem(11, new ItemBuilder(Material.DIAMOND, CC.GoldB + "Shop", CC.Gray + "Earn money by selling items to the Shop!", "", CC.GoldB + "Coordinates", LocationUtils.printPretty((Location)shop, (ChatColor)ChatColor.GOLD, (boolean)false), "", teleString).build(), this.getTele(shop));
        Location fishingLocation = new Location(world, 32.0, 68.0, -26.0, -87.2f, 5.9f);
        this.setItem(15, new ItemBuilder(Material.FISHING_ROD, CC.AquaB + "Fishing Lake", CC.Gray + "A quiet place to fish for food and items!", "", CC.AquaB + "Coordinates", LocationUtils.printPretty((Location)fishingLocation, (ChatColor)ChatColor.AQUA, (boolean)false), "", teleString).build(), this.getTele(fishingLocation));
        this.setItem(22, new ItemBuilder(Material.BED, CC.GreenB + "Spawn", CC.Gray + "The center of the world!", "", CC.GreenB + "Coordinates", CC.Green + "x0, z0", "", teleString).build(), this.getTele(spawn));
        Location nether = new Location(world, -43.0, 74.0, -52.0, 91.7f, 2.9f);
        this.setItem(this.size - 16, new ItemBuilder(Material.NETHER_BRICK, CC.LightPurpleB + "Nether Portal", CC.Gray + "A dark and scary place full of treasures.", "", CC.LightPurpleB + "Coordinates", LocationUtils.printPretty((Location)nether, (ChatColor)ChatColor.LIGHT_PURPLE, (boolean)false), "", teleString).build(), this.getTele(nether));
        Location cows = new Location(world, 45.0, 61.0, 55.0, -98.6f, 4.9f);
        this.setItem(this.size - 12, new ItemBuilder(Material.COOKED_BEEF, CC.YellowB + "Cows", CC.Gray + "Starving? Slay some cows for food and leather!", "", CC.YellowB + "Coordinates", LocationUtils.printPretty((Location)cows, (ChatColor)ChatColor.YELLOW, (boolean)false), "", teleString).build(), this.getTele(cows));
    }

    private Runnable getTele(Location location) {
        return () -> {
            Location spawn = location.getWorld().getSpawnLocation();
            if (!this.canTeleport) {
                Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin) AstroCore.getInstance(), () -> this.player.closeInventory());
                this.player.playSound(this.player.getLocation(), Sound.GHAST_MOAN, 1.0f, 1.4f);
                this.player.sendMessage(CC.RedB + "(!) " + CC.Red + "You can only use this menu in the spawn area at " + LocationUtils.printPretty((Location)spawn, (ChatColor)ChatColor.RED, (boolean)true) + CC.Red + "!");
                return;
            }
            this.closeAndTeleport(this.player, location);
        };
    }

    private void closeAndTeleport(Player player, Location location) {
        Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin) AstroCore.getInstance(), () -> {
            player.closeInventory();
            player.teleport(location, PlayerTeleportEvent.TeleportCause.UNKNOWN);
            player.playSound(player.getLocation(), Sound.WITHER_SHOOT, 1.1f, 1.1f);
        }, 1L);
    }
}

