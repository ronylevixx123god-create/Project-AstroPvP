package me.kingsavaqe.astroenvoy.menus;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.kingsavaqe.astroenvoy.AstroEnvoyPlugin;
import me.kingsavaqe.astroenvoy.envoy.Envoy;
import me.kingsavaqe.astroenvoy.playerdata.PlayerData;
import me.kingsavaqe.astroenvoy.stats.Stats;
import me.kingsavaqe.astroenvoy.stats.StatsManager;
import me.kingsavaqe.astroenvoy.utils.TimeUtil;
import me.kingsavaqe.astroenvoy.utils.inventory.nInventoryUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class EnvoyMenu {
    private static EnvoyMenu instance;

    public static EnvoyMenu getInstance() {
        if (instance == null)
            instance = new EnvoyMenu();
        return instance;
    }

    public void open(final Player player) {
        final AstroEnvoyPlugin instance = AstroEnvoyPlugin.getInstance();
        Bukkit.getServer().getScheduler().runTaskAsynchronously((Plugin)instance, new Runnable() {
            public void run() {
                StatsManager statsManager = instance.getStatsManager();
                Envoy envoy = instance.getEnvoyManager().getEnvoy();
                FileConfiguration configLoad = instance.getFileManager().getConfig(new File(instance.getDataFolder(), "config.yml")).getFileConfiguration();
                PlayerData playerData = instance.getPlayerDataManager().getPlayerData(player);
                final nInventoryUtil inv = new nInventoryUtil(player, new nInventoryUtil.ClickEventHandler() {
                    public void onClick(nInventoryUtil.ClickEvent event) {
                        ItemStack is = event.getItem();
                        if (is.getType() == Material.FENCE_GATE) {
                            player.playSound(player.getLocation(), Sound.ARROW_HIT, 1.0F, 1.0F);
                        } else if (is.getType() == Material.STAINED_GLASS_PANE) {
                            event.setWillClose(false);
                            event.setWillDestroy(false);
                            player.playSound(player.getLocation(), Sound.GLASS, 1.0F, 1.0F);
                        } else if (is.getType() == Material.PAINTING || is.getType() == Material.ITEM_FRAME) {
                            event.setWillClose(false);
                            event.setWillDestroy(false);
                            player.playSound(player.getLocation(), Sound.VILLAGER_YES, 1.0F, 1.0F);
                        } else if (is.getType() == Material.DOUBLE_PLANT) {
                            event.setWillClose(false);
                            event.setWillDestroy(false);
                            LootMenu.getInstance().open(player, 1);
                            player.playSound(player.getLocation(), Sound.NOTE_PLING, 1.0F, 1.0F);
                        }
                    }
                });
                inv.setTitle(ChatColor.translateAlternateColorCodes('&', configLoad.getString("Menus.Envoy.Title")));
                inv.setType(InventoryType.HOPPER);
                inv.addItem(inv.createItem(new ItemStack(Material.FENCE_GATE),
                        ChatColor.translateAlternateColorCodes('&', configLoad
                                .getString("Menus.Envoy.Items.Exit.Displayname")), null, null, null, null), new int[] { 0, 4 });
                List<String> itemLore = new ArrayList<>();
                if (envoy.hasSetup()) {
                    if (envoy.hasStarted()) {
                        for (String itemLoreLine : configLoad.getStringList("Menus.Envoy.Items.Event.Started.Lore"))
                            itemLore.add(ChatColor.translateAlternateColorCodes('&', itemLoreLine
                                    .replace("%crates", String.valueOf(envoy.getRandomCrateLocations().size()))
                                    .replace("%countdown", TimeUtil.getDuration(envoy.getCountdown()))));
                    } else {
                        for (String itemLoreLine : configLoad.getStringList("Menus.Envoy.Items.Event.Starting.Lore"))
                            itemLore.add(ChatColor.translateAlternateColorCodes('&', itemLoreLine.replace("%countdown",
                                    TimeUtil.getDuration(envoy.getCurrentStartTime()))));
                    }
                    inv.addItem(inv.createItem(new ItemStack(Material.PAINTING),
                            ChatColor.translateAlternateColorCodes('&', configLoad
                                    .getString("Menus.Envoy.Items.Event.Displayname")), itemLore, null, null, null), new int[] { 2 });
                    itemLore.clear();
                } else {
                    inv.addItem(inv
                            .createItem(new ItemStack(Material.PAINTING),
                                    ChatColor.translateAlternateColorCodes('&', configLoad
                                            .getString("Menus.Envoy.Items.Event.Displayname")), configLoad
                                            .getStringList("Menus.Envoy.Items.Event.Setup.Lore"), null, null, null), new int[] { 2 });
                }
                for (String itemLoreLine : configLoad.getStringList("Menus.Envoy.Items.Statistics.Lore")) {
                    if (itemLoreLine.contains("%leaderboard")) {
                        if (statsManager.getStats().size() == 0) {
                            itemLore.add(ChatColor.translateAlternateColorCodes('&', configLoad
                                    .getString("Menus.Envoy.Items.Statistics.Leaderboard.None")));
                            continue;
                        }
                        for (int i = 0; i < 10; i++) {
                            int position = i + 1;
                            if (statsManager.getStats().size() < position)
                                break;
                            Stats stats = statsManager.getStats().get(i);
                            itemLore.add(ChatColor.translateAlternateColorCodes('&', configLoad
                                    .getString("Menus.Envoy.Items.Statistics.Leaderboard.Player")
                                    .replace("%position", String.valueOf(position)).replace("%player", stats.getName())
                                    .replace("%opened", String.valueOf(stats.getOpened()))));
                        }
                        continue;
                    }
                    itemLore.add(ChatColor.translateAlternateColorCodes('&', itemLoreLine
                            .replace("%opened", String.valueOf(playerData.getOpened()))));
                }
                inv.addItem(inv.createItem(new ItemStack(Material.DOUBLE_PLANT),
                        ChatColor.translateAlternateColorCodes('&', configLoad
                                .getString("Menus.Envoy.Items.Loot.Displayname")), configLoad
                                .getStringList("Menus.Envoy.Items.Loot.Lore"), null, null, null), new int[] { 1 });
                inv.addItem(inv.createItem(new ItemStack(Material.ITEM_FRAME),
                        ChatColor.translateAlternateColorCodes('&', configLoad
                                .getString("Menus.Envoy.Items.Statistics.Displayname")), itemLore, null, null, null), new int[] { 3 });
                Bukkit.getServer().getScheduler().runTask((Plugin)instance, new Runnable() {
                    public void run() {
                        inv.open();
                    }
                });
            }
        });
    }
}
